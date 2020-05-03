package com.maxpilotto.esame2017.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maxpilotto.esame2017.Storable;
import com.maxpilotto.esame2017.modules.Product;
import com.maxpilotto.esame2017.persistance.tables.OrderTable;
import com.maxpilotto.esame2017.persistance.tables.ProductTable;

import java.util.ArrayList;
import java.util.List;

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

    public long insert(Storable storable, String table) {
        return database.insert(table, null, storable.values());
    }

    public long insert(ContentValues values, String table) {
        return database.insert(table, null, values);
    }

//    public Integer getOrderCount() {
//        Cursor cursor = database.rawQuery("SELECT COUNT(" + OrderTable._ID + ") as total FROM " + OrderTable.TABLE_NAME, null);
//        Integer total = 0;
//
//        if (cursor.moveToNext()) {
//            total = cursor.getInt(cursor.getColumnIndex("total"));
//        }
//
//        cursor.close();
//
//        return total;
//    }
//
//    public List<Product> getProducts() {
//        Cursor cursor = database.rawQuery("SELECT * FROM " + ProductTable.TABLE_NAME, null);
//        List<Product> products = new ArrayList<>();
//
//        while (cursor.moveToNext()) {
//            products.add(new Product(cursor));
//        }
//
//        return products;
//    }
}
