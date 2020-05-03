package com.maxpilotto.esame2017.persistance.tables;

import android.provider.BaseColumns;

public class OrderTable implements BaseColumns {
    public static final String TABLE_NAME = "orders";
    public static final String COLUMN_DATE = "date";
    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " integer primary key autoincrement," +
            COLUMN_DATE + " integer" +
            ")";
}
