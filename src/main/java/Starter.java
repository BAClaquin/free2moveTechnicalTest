import filter.DateUtils;
import handler.FilesHandler;
import model.PropertyKey;
import model.exception.MissingArgumentException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import handler.PropertyHandler;

public class Starter {

    public static void main(String[] args) {

        if (args.length == 0) {
            throw new MissingArgumentException("Missing command line argument : no property file specified");
        }

        String propertyFilePath = args[0];
        PropertyHandler propertyHandler = PropertyHandler.getPropertiesHandlerFromFile(propertyFilePath);

        SparkSession sparkSession = getSparkSession(propertyHandler);
        FilesHandler filesHandler = getFilesHandler(propertyHandler, sparkSession);

        Dataset<Row> orders = filesHandler.getOrders();
        Dataset<Row> products = filesHandler.getProducts();
        Dataset<Row> items = filesHandler.getItems();
        Dataset<Row> customers = filesHandler.getCustomers();

        String date = DateUtils.getDate(args);

        Dataset<Row> summary = ProcessingJob
                .builder()
                .customers(customers)
                .products(products)
                .items(items)
                .orders(orders)
                .date(date)
                .build()
                .getSummaryDataset();

        filesHandler.writeOutputFile(summary,date);
    }

    private static FilesHandler getFilesHandler(PropertyHandler propertyHandler, SparkSession sparkSession) {
        return FilesHandler
                .builder()
                .propertyHandler(propertyHandler)
                .sparkSession(sparkSession)
                .build();
    }

    private static SparkSession getSparkSession(PropertyHandler propertyHandler) {
        return SparkSession
                .builder()
                .master(propertyHandler.getProperty(PropertyKey.MASTER))
                .config("spark.sql.shuffle.partitions",propertyHandler.getProperty(PropertyKey.PARTITIONS))
                .getOrCreate();
    }


}
