package filter;

import lombok.experimental.UtilityClass;
import model.column.OrdersColumn;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.spark.sql.functions.to_date;

@UtilityClass
public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_COLUMN_NAME = "date";

    public static String getDate(String[] args) {
        if (args.length >= 2) {
            return args[1];
        }
        Date date = org.apache.commons.lang.time.DateUtils.addDays(new Date(), -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);

    }

    public static Dataset<Row> ordersToDate(Dataset<Row> orders, String date) {
        orders = getDateColumn(orders);
        return orders.filter(orders.col(DATE_COLUMN_NAME).like(date));
    }

    public static Dataset<Row> ordersBeforeDate(Dataset<Row> orders, String date) {
        orders = getDateColumn(orders);
        return orders.filter(orders.col(DATE_COLUMN_NAME).lt(date));
    }

    private static Dataset<Row> getDateColumn(Dataset<Row> orders) {
        String dateColumn = OrdersColumn.ORDER_PURCHASE_TIMESTAMP.value;
        return orders.withColumn(DATE_COLUMN_NAME, to_date(orders.col(dateColumn), DATE_FORMAT));
    }

}
