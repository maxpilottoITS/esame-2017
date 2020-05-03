package com.maxpilotto.esame2017.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.maxpilotto.esame2017.modules.Product;
import com.maxpilotto.esame2017.persistance.tables.OrderProductsTable;
import com.maxpilotto.esame2017.persistance.tables.OrderTable;
import com.maxpilotto.esame2017.persistance.tables.ProductTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Esame2017_Pizzeria", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductTable.CREATE);
        db.execSQL(OrderTable.CREATE);
        db.execSQL(OrderProductsTable.CREATE);

        Database database = Database.get();

        database.insert(new Product(1,"Pizza",8.0),ProductTable.TABLE_NAME);
        database.insert(new Product(1,"Panino",6.0),ProductTable.TABLE_NAME);
        database.insert(new Product(1,"Bibita",3.0),ProductTable.TABLE_NAME);
        database.insert(new Product(1,"Gelato",3.0),ProductTable.TABLE_NAME);
        database.insert(new Product(1,"Caffè",1.0),ProductTable.TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
