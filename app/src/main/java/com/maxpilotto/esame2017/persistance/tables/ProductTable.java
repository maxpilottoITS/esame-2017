package com.maxpilotto.esame2017.persistance.tables;

import android.provider.BaseColumns;

public class ProductTable implements BaseColumns {
    public static final String TABLE_NAME = "products";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " integer primary key autoincrement," +
            COLUMN_NAME + " text," +
            COLUMN_PRICE + " real" +
            ")";

}
