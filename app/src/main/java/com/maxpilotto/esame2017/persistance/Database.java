package com.maxpilotto.esame2017.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maxpilotto.esame2017.Storable;
import com.maxpilotto.esame2017.persistance.tables.OrderTable;

public class Database {
    private static Database instance;
    private SQLiteDatabase database;

    public static Database get() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Database();
        instance.database = new DatabaseHelper(context).getWritableDatabase();
    }

    private Database() {
    }

    public void insert(Storable storable, String table) {
        database.insert(table, null, storable.values());
    }

    public void insert(ContentValues values, String table) {
        database.insert(table, null, values);
    }

    public Integer getOrderCount() {
        Cursor cursor = database.rawQuery(
                "SELECT COUNT(?) as total FROM ?",
                new String[]{
                        OrderTable._ID,
                        OrderTable.TABLE_NAME
                }
        );
        Integer total = 0;

        if (cursor.moveToNext()) {
            total = cursor.getInt(cursor.getColumnIndex("total"));
        }

        cursor.close();

        return total;
    }
}
