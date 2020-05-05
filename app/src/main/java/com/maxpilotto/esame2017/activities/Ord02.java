package com.maxpilotto.esame2017.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
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
import com.maxpilotto.esame2017.dialogs.DeleteDialog;
import com.maxpilotto.esame2017.models.OrderDetail;
import com.maxpilotto.esame2017.models.Product;
import com.maxpilotto.esame2017.persistance.OrderProvider;
import com.maxpilotto.esame2017.persistance.tables.OrderDetailTable;
import com.maxpilotto.esame2017.persistance.tables.OrderTable;

import java.util.ArrayList;
import java.util.List;

public class Ord02 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final Integer ID = 3000;
    public static final String ORDER_KEY = "order_key";

    private Integer orderId;
    private ListView listView;
    private TextView total;
    private List<OrderDetail> details;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord02);

        if (getIntent() != null) {
            orderId = getIntent().getIntExtra(ORDER_KEY, -1);
        }

        details = new ArrayList<>();
        adapter = new ProductAdapter(this, details);

        total = findViewById(R.id.total);
        listView = findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            details.get(position).incrementCount();
            adapter.notifyDataSetChanged();
            setTotal();
        });
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Product p = details.get(position).getProduct();
            DeleteDialog dialog = new DeleteDialog(
                    getString(R.string.resetTitle, p.getName()),
                    getString(R.string.resetMsg, p.getName())
            );
            dialog.setCallback(new DeleteDialog.Callback() {
                @Override
                public void onConfirm(DialogInterface dialog) {
                    details.get(position).setCount(0);
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

        findViewById(R.id.confirm).setOnClickListener(v -> {
            for (OrderDetail detail : details) {
                ContentValues values = detail.values();

                getContentResolver().update(
                        OrderProvider.URI_ORDER_DETAILS,
                        values,
                        OrderDetailTable.COLUMN_ORDER + "=? AND " + OrderDetailTable.COLUMN_PRODUCT + "=?",
                        new String[]{
                                orderId.toString(),
                                detail.getProduct().getId().toString()
                        }
                );
            }

            finish();
        });
        findViewById(R.id.cancel).setOnClickListener(v -> {
            finish();
        });

        getSupportLoaderManager().initLoader(ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(
                this,
                OrderProvider.URI_ORDER_DETAILS,
                null,
                OrderDetailTable.COLUMN_ORDER + "=? AND " + OrderDetailTable.COLUMN_COUNT + " > 0",
                new String[]{
                        orderId.toString()
                },
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        details.clear();
        details.addAll(OrderDetail.parseList(data));
        adapter.notifyDataSetChanged();

        setTotal();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        details.clear();
        adapter.notifyDataSetChanged();

        setTotal();
    }

    private void setTotal() {
        Double totalPrice = 0.0;

        for (OrderDetail detail : details) {
            totalPrice += detail.getTotalePrice();
        }

        total.setText(getString(R.string.totalPrice, totalPrice));
    }
}
