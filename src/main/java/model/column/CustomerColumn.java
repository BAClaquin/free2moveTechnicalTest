package model.column;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CustomerColumn {

    CUSTOMER_ID("customer_id"),
    CUSTOMER_UNIQ_ID("customer_unique_id"),
    CUSTOMER_ZIP_CODE_PREFIX("customer_zip_code_prefix"),
    CUSTOMER_CITY("customer_id"),
    CUSTOMER_STATE("customer_state");

    public final String value;

}
