package com.maxpilotto.esame2017.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.maxpilotto.esame2017.Storable;
import com.maxpilotto.esame2017.persistance.Database;
import com.maxpilotto.esame2017.persistance.tables.OrderDetailTable;

public class OrderDetail implements Storable {
    private Integer id;
    private Product product;
    private Integer count;

    public OrderDetail(Cursor cursor) {
        this(
                cursor.getInt(cursor.getColumnIndex(OrderDetailTable._ID)),
                Database.get().getProduct(cursor.getInt(cursor.getColumnIndex(OrderDetailTable.COLUMN_PRODUCT))),
                cursor.getInt(cursor.getColumnIndex(OrderDetailTable.COLUMN_COUNT))
        );
    }

    public OrderDetail(Integer id, Product product, Integer count) {
        this.id = id;
        this.product = product;
        this.count = count;
    }

    public OrderDetail(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }

    @Override
    public ContentValues values(boolean includeId) {
        ContentValues values = new ContentValues();

        if (includeId) {
            values.put(OrderDetailTable._ID,id);
        }

        values.put(OrderDetailTable.COLUMN_PRODUCT,product.getId());
        values.put(OrderDetailTable.COLUMN_COUNT,count);

        return values;
    }

    public void incrementCount(){
        count++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
