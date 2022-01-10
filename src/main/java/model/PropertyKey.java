package model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PropertyKey {
    CUSTOMER_FILE_PATH("dir.customer"),
    ORDER_FILE_PATH("dir.orders"),
    PRODUCT_FILE_PATH("dir.products"),
    ITEM_FILE_PATH("dir.items"),
    OUTPUT_FILE_PATH("dir.output"),
    MASTER("master"),
    PARTITIONS("spark.sql.shuffle.partitions"),
    COALESCE("coalesce");

    public final String value;
}
