package model.column;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ItemsColumn {

    ORDER_ID("order_id"),
    ORDER_ITEM_ID("order_item_id"),
    PRODUCT_ID("product_id"),
    SELLER_ID("seller_id"),
    SHIPPING_LIMIT_DATE("shipping_limit_date"),
    PRICE("price"),
    FREIGHT_VALUE("freight_value");

    public final String value;

}
