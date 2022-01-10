import filter.DateUtils;
import lombok.Builder;
import lombok.NonNull;
import model.column.CustomerColumn;
import model.column.ItemsColumn;
import model.column.OrdersColumn;
import model.column.ProductsColumn;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.*;

@Builder
public class ProcessingJob {

    public static final String COUNT = "count";
    public static final String REPEATING_CUSTOMER_UNIQ_ID_COLUMN = "repeating_customer_uniq_id";

    @NonNull
    private Dataset<Row> orders;
    @NonNull
    private Dataset<Row> customers;
    @NonNull
    private Dataset<Row> products;
    @NonNull
    private Dataset<Row> items;
    @NonNull
    private String date;

    public  Dataset<Row>  getSummaryDataset() {

        Dataset<Row> filteredOrders = DateUtils.ordersToDate(orders, date);

        Dataset<Row> joinedWithItems = filteredOrders
                .join(items, OrdersColumn.ORDER_ID.value);

        Dataset<Row> joinedWithProducts = joinedWithItems
                .join(products, ItemsColumn.PRODUCT_ID.value);

        Dataset<Row> joinedWithCustomers = joinedWithProducts
                .join(customers, CustomerColumn.CUSTOMER_ID.value);

        Dataset<Row> summary = joinedWithCustomers
                .groupBy(CustomerColumn.CUSTOMER_UNIQ_ID.value)
                .agg(countDistinct(OrdersColumn.ORDER_ID.value).as("# of orders made"),
                        count(ItemsColumn.ORDER_ITEM_ID.value).as("# of products ordered"),
                        sum(ItemsColumn.PRICE.value).as("total amount spent"),
                        sum(ItemsColumn.FREIGHT_VALUE.value).as("total freight value"),
                        collect_set(ProductsColumn.PRODUCT_CATEGORY_NAME.value).as("product categories"))
                .orderBy(desc("total amount spent"));
        Dataset<Row> withRepeaters = getRepeaters(summary);

        return withRepeaters;

    }

    private Dataset<Row> getRepeaters(Dataset<Row> summary) {
        Dataset<Row> customersBeforeDate = DateUtils.ordersBeforeDate(orders, date)
                .select(OrdersColumn.CUSTOMER_ID.value)
                .distinct()
                .join(customers, CustomerColumn.CUSTOMER_ID.value)
                .select(col(CustomerColumn.CUSTOMER_UNIQ_ID.value).as(REPEATING_CUSTOMER_UNIQ_ID_COLUMN));

        Column joinCondition = summary
                .col(CustomerColumn.CUSTOMER_UNIQ_ID.value)
                .equalTo(customersBeforeDate.col(REPEATING_CUSTOMER_UNIQ_ID_COLUMN));

        Dataset<Row> summaryWithCustomerUniqId = summary.join(customersBeforeDate, joinCondition, "left_outer");

        return summaryWithCustomerUniqId.withColumn("is repeater", col(REPEATING_CUSTOMER_UNIQ_ID_COLUMN).isNotNull()).drop(REPEATING_CUSTOMER_UNIQ_ID_COLUMN);

    }
}
