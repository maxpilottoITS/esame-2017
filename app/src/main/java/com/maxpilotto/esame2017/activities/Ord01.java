package com.maxpilotto.esame2017.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.maxpilotto.esame2017.R;
import com.maxpilotto.esame2017.adapters.ProductAdapter;
import com.maxpilotto.esame2017.modules.Product;
import com.maxpilotto.esame2017.persistance.OrderProvider;

import java.util.ArrayList;
import java.util.List;

import static com.maxpilotto.esame2017.util.Util.*;

public class Ord01 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final Integer ID = 2000;

    private TextView today;
    private ListView listView;
    private List<Product> products;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord01);

        today = findViewById(R.id.today);
        listView = findViewById(R.id.list);

        products = new ArrayList<>();
        adapter = new ProductAdapter(this, products);

        today.setText(printDate(today()));
        listView.setAdapter(adapter);

        findViewById(R.id.cancel).setOnClickListener(v -> {

        });
        findViewById(R.id.confirm).setOnClickListener(v -> {

        });

        getSupportLoaderManager().initLoader(ID, null, this);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, OrderProvider.URI_PRODUCTS, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor data) {
        Log.d("KAAA","Data size: " + data.getCount());

        products.clear();
        products.addAll(Product.parseList(data));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        products.clear();
        adapter.notifyDataSetChanged();
    }
}
