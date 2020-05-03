package com.maxpilotto.esame2017.modules;

import android.content.ContentValues;

import com.maxpilotto.esame2017.Storable;
import com.maxpilotto.esame2017.persistance.tables.ProductTable;

public class Product implements Storable {
    private Integer id;
    private String name;
    private Double price;

    public Product(ContentValues values) {
        this.id = values.getAsInteger(ProductTable._ID);
        this.name = values.getAsString(ProductTable.COLUMN_NAME);
        this.price = values.getAsDouble(ProductTable.COLUMN_PRICE);
    }

    public Product(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public ContentValues values() {
        ContentValues values = new ContentValues();

        values.put(ProductTable._ID,id);
        values.put(ProductTable.COLUMN_NAME,name);
        values.put(ProductTable.COLUMN_PRICE,price);

        return values;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
