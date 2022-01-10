package model.column;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductsColumn {

    PRODUCT_ID("product_id"),
    PRODUCT_CATEGORY_NAME("product_category_name"),
    PRODUCT_NAME_LENGHT("product_name_lenght"),
    PRODUCT_DESCRIPTION_LENGHT("product_description_lenght"),
    PRODUCT_PHOTOS_QTY("product_photos_qty"),
    PRODUCT_WEIGHT_G("product_weight_g"),
    PRODUCT_LENGTH_CM("product_length_cm"),
    PRODUCT_HEIGHT_CM("product_height_cm"),
    PRODUCT_WIDTH_CM("product_width_cm"),
    PRODUCT_CATEGORY_NAME_ENGLISH("product_category_name_english");

    public final String value;

}
