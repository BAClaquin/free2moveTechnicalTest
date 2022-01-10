package model.column;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrdersColumn {

    ORDER_ID("order_id"),
    CUSTOMER_ID("customer_id"),
    ORDER_STATUS("order_status"),
    ORDER_PURCHASE_TIMESTAMP("order_purchase_timestamp"),
    ORDER_APPROVED_AT("order_approved_at"),
    ORDER_DELIVERED_CARRIER_DATE("order_delivered_carrier_date"),
    ORDER_DELIVERED_CUSTOMER_DATE("order_delivered_customer_date"),
    ORDER_ESTIMATED_DELIVERY_DATE("order_estimated_delivery_date");

    public final String value;

}
