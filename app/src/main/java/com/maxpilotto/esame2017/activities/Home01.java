package com.maxpilotto.esame2017.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.maxpilotto.esame2017.R;
import com.maxpilotto.esame2017.persistance.Database;
import com.maxpilotto.esame2017.persistance.OrderProvider;

public class Home01 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final Integer ID = 1000;
    private TextView total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home01);

        total = findViewById(R.id.total);

        getSupportLoaderManager().initLoader(ID, null, this);

        findViewById(R.id.insert).setOnClickListener(v -> {
            startActivity(new Intent(this, Ord01.class));
        });
        findViewById(R.id.update).setOnClickListener(v -> {
            startActivity(new Intent(this, List01.class));
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, OrderProvider.URI_ORDERS, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        total.setText(getString(R.string.totalOrders, data.getCount()));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
