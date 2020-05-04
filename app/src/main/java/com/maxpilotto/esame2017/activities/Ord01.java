package com.maxpilotto.esame2017.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import com.maxpilotto.esame2017.models.OrderDetail;
import com.maxpilotto.esame2017.models.Product;
import com.maxpilotto.esame2017.persistance.OrderProvider;
import com.maxpilotto.esame2017.persistance.tables.OrderDetailTable;
import com.maxpilotto.esame2017.persistance.tables.OrderTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.maxpilotto.esame2017.util.Util.*;

public class Ord01 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final Integer ID = 2000;

    private TextView today;
    private ListView listView;
    private List<Product> products;
    private ProductAdapter adapter;
    private Date now = new Date();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord01);

        today = findViewById(R.id.today);
        listView = findViewById(R.id.list);

        products = new ArrayList<>();
        adapter = new ProductAdapter(this, products);

        today.setText(printDate(now));
        listView.setAdapter(adapter);

        findViewById(R.id.cancel).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.confirm).setOnClickListener(v -> {
            ContentValues order = new ContentValues();
            order.put(OrderTable.COLUMN_DATE, now.getTime());

            long orderId = Long.parseLong(getContentResolver().insert(OrderProvider.URI_ORDERS, order).getLastPathSegment());

            for (OrderDetail detail : adapter.getProducts()) {
                ContentValues values = detail.values();

                values.put(OrderDetailTable.COLUMN_ORDER,orderId);

                Log.d("KYAAA",values.toString());

                long id = Long.parseLong((getContentResolver().insert(OrderProvider.URI_ORDER_DETAILS,values).getLastPathSegment()));

                Log.d("KYAAA",id + "");
            }

            finish();
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
