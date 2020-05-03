package com.maxpilotto.esame2017.modules;

import android.content.ContentValues;
import android.database.Cursor;

import com.maxpilotto.esame2017.Storable;
import com.maxpilotto.esame2017.persistance.tables.OrderTable;

import java.util.Date;
import java.util.Map;

public class Order implements Storable {
    private Integer id;
    private Map<Product, Integer> products;
    private Date date;

    public Order(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(OrderTable._ID));
        this.date = new Date(cursor.getInt(cursor.getColumnIndex(OrderTable.COLUMN_DATE)));
    }

    public Order(Integer id, Map<Product, Integer> products, Date date) {
        this.id = id;
        this.products = products;
        this.date = date;
    }

    @Override
    public ContentValues values() {
        ContentValues values = new ContentValues();

        values.put(OrderTable._ID, id);
        values.put(OrderTable.COLUMN_DATE, date.getTime());

        return values;
    }

    public Integer totalProducts() {
        Integer count = 0;

        for (Map.Entry<Product,Integer> p : products.entrySet()) {
            count += p.getValue();
        }

        return count;
    }

    public Double totalPrice() {
        Double total = 0.0;

        for (Map.Entry<Product,Integer> p : products.entrySet()) {
            total += p.getKey().getPrice() * p.getValue();
        }

        return total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
