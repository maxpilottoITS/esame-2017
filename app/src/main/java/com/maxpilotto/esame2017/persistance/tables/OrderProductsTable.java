package com.maxpilotto.esame2017.persistance.tables;

import android.provider.BaseColumns;

public class OrderProductsTable implements BaseColumns {
    public static final String TABLE_NAME = "order_products";
    public static final String COLUMN_ORDER = "ord";
    public static final String COLUMN_PRODUCT = "product";
    public static final String COLUMN_COUNT = "count";
    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " integer primary key autoincrement," +
            COLUMN_ORDER + " integer," +
            COLUMN_COUNT + " integer," +
            COLUMN_PRODUCT + " integer" +
            ")";
}
