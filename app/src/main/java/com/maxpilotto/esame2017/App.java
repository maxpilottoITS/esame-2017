package com.maxpilotto.esame2017;

import android.app.Application;

import com.maxpilotto.esame2017.persistance.Database;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Database.init(this);
    }
}
