package com.maxpilotto.esame2017;

import android.app.Application;

import com.maxpilotto.esame2017.modules.Product;
import com.maxpilotto.esame2017.persistance.Database;
import com.maxpilotto.esame2017.persistance.tables.ProductTable;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Database.init(this);
    }
}
