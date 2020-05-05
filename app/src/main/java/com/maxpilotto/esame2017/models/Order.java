package com.maxpilotto.esame2017.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.maxpilotto.esame2017.Storable;
import com.maxpilotto.esame2017.persistance.tables.OrderTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order implements Storable {
    private Integer id;
    private List<OrderDetail> products;
    private Date date;

    public Order(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(OrderTable._ID));
        this.date = new Date(cursor.getLong(cursor.getColumnIndex(OrderTable.COLUMN_DATE)));
        this.products = new ArrayList<>();
    }

    public Order(List<OrderDetail> products, Date date) {
        this.products = products;
        this.date = date;
    }

    public Order(Integer id, List<OrderDetail> products, Date date) {
        this.id = id;
        this.products = products;
        this.date = date;
    }

    @Override
    public ContentValues values(boolean includeId) {
        ContentValues values = new ContentValues();

        if (includeId) {
            values.put(OrderTable._ID, id);
        }

        values.put(OrderTable.COLUMN_DATE, date.getTime());

        return values;
    }

    public Integer totalProducts() {
        Integer count = 0;

        for (OrderDetail detail : products) {
            count += detail.getCount();
        }

        return count;
    }

    public Double totalPrice() {
        Double total = 0.0;

        for (OrderDetail detail : products) {
            total += detail.getTotalePrice();
        }

        return total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrderDetail> getProducts() {
        return products;
    }

    public void setProducts(List<OrderDetail> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
