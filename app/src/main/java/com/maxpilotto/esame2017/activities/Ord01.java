package com.maxpilotto.esame2017.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
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
import com.maxpilotto.esame2017.dialogs.DeleteDialog;
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
    private List<OrderDetail> orderDetails;
    private ProductAdapter adapter;
    private Date now = new Date();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord01);

        today = findViewById(R.id.today);
        listView = findViewById(R.id.list);

        orderDetails = new ArrayList<>();
        adapter = new ProductAdapter(this, orderDetails);

        today.setText(printDate(now));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            orderDetails.get(position).incrementCount();
            adapter.notifyDataSetChanged();
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Product p = orderDetails.get(position).getProduct();
            DeleteDialog dialog = new DeleteDialog(
                    getString(R.string.resetTitle, p.getName()),
                    getString(R.string.resetMsg, p.getName())
            );
            dialog.setCallback(new DeleteDialog.Callback() {
                @Override
                public void onConfirm(DialogInterface dialog) {
                    orderDetails.get(position).setCount(0);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.dismiss();
                }
            });
            dialog.show(getSupportFragmentManager(), null);

            return true;
        });

        findViewById(R.id.cancel).setOnClickListener(v -> {
            finish();
        });
        findViewById(R.id.confirm).setOnClickListener(v -> {
            ContentValues order = new ContentValues();
            order.put(OrderTable.COLUMN_DATE, now.getTime());

            long orderId = Long.parseLong(getContentResolver().insert(OrderProvider.URI_ORDERS, order).getLastPathSegment());

            for (OrderDetail detail : this.orderDetails) {
                ContentValues values = detail.values();

                values.put(OrderDetailTable.COLUMN_ORDER, orderId);

                getContentResolver().insert(OrderProvider.URI_ORDER_DETAILS, values);
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
        orderDetails.clear();

        for (Product p : Product.parseList(data)) {
            orderDetails.add(new OrderDetail(p,0));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        orderDetails.clear();
        adapter.notifyDataSetChanged();
    }
}
