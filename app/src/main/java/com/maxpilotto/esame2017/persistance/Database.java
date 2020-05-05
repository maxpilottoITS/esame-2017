package com.maxpilotto.esame2017.persistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.maxpilotto.esame2017.Storable;
import com.maxpilotto.esame2017.models.Order;
import com.maxpilotto.esame2017.models.OrderDetail;
import com.maxpilotto.esame2017.models.Product;
import com.maxpilotto.esame2017.persistance.tables.OrderDetailTable;
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

    public long insert(ContentValues values, String table) {
        return database.insert(table, null, values);
    }

    public Product getProduct(Integer id) {
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + ProductTable.TABLE_NAME + " WHERE " + ProductTable._ID + "=?",
                new String[]{id.toString()}
        );
        Product product = null;

        if (cursor.moveToNext()) {
            product = new Product(cursor);
        }

        cursor.close();
        return product;
    }

    public List<Order> getOrders() {
        List<Order> list = new ArrayList<>();
        Cursor orderCursor = database.rawQuery(
                "SELECT * FROM " + OrderTable.TABLE_NAME,
                null
        );

        while (orderCursor.moveToNext()) {
            Order order = new Order(orderCursor);
            Cursor productCursor = database.rawQuery("SELECT * FROM " + ProductTable.TABLE_NAME, null);

            while (productCursor.moveToNext()) {
                Product product = new Product(productCursor);
                OrderDetail detail = null;
                Cursor orderDetailCursor = database.query(
                        OrderDetailTable.TABLE_NAME,
                        null,
                        OrderDetailTable.COLUMN_PRODUCT + "=? AND " + OrderDetailTable.COLUMN_ORDER + "=?",
                        new String[]{
                                product.getId().toString(),
                                order.getId().toString()
                        },
                        null,
                        null,
                        null
                );

                if (orderDetailCursor.moveToNext()) {
                    detail = new OrderDetail(orderDetailCursor);
                } else {
                    detail = new OrderDetail(product, 0);
                }

                order.getProducts().add(detail);
                orderDetailCursor.close();
            }

            productCursor.close();
            list.add(order);
        }

        orderCursor.close();
        return list;
    }
}
