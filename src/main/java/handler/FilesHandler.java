package handler;

import lombok.Builder;
import lombok.NonNull;
import model.PropertyKey;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

@Builder
public class FilesHandler {
    @NonNull
    private PropertyHandler propertyHandler;
    @NonNull
    private SparkSession sparkSession;

    public Dataset<Row> getOrders() {
        return getDataset(PropertyKey.ORDER_FILE_PATH);
    }

    public Dataset<Row> getProducts() {
        return getDataset(PropertyKey.PRODUCT_FILE_PATH);
    }

    public Dataset<Row> getItems() {
        return getDataset(PropertyKey.ITEM_FILE_PATH);
    }

    public Dataset<Row> getCustomers() {
        return getDataset(PropertyKey.CUSTOMER_FILE_PATH);
    }

    private Dataset<Row> getDataset(PropertyKey propertyKey) {
        String filePath = propertyHandler.getProperty(propertyKey);
        return readFile(sparkSession, filePath);
    }

    private static Dataset<Row> readFile(SparkSession sparkSession, String path) {
        return sparkSession.read()
                .format("csv")
                .option("delimiter", ",")
                .option("header", "true")
                .option("inferSchema", "true")
                .load(path);
    }

    public void writeOutputFile(Dataset<Row> summary, String date) {
        String filePath = propertyHandler.getProperty(PropertyKey.OUTPUT_FILE_PATH);
        String fileName = "summary_statistics_" + date + ".csv";
        summary
                .coalesce(Integer.valueOf(propertyHandler.getProperty(PropertyKey.COALESCE)))
                .write()
                .format("json")
                .option("header", "true")
                .save(filePath + "/" + fileName);
    }
}
