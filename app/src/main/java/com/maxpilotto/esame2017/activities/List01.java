package com.maxpilotto.esame2017.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxpilotto.esame2017.R;
import com.maxpilotto.esame2017.adapters.OrderAdapter;
import com.maxpilotto.esame2017.dialogs.DeleteDialog;
import com.maxpilotto.esame2017.models.Order;
import com.maxpilotto.esame2017.persistance.Database;
import com.maxpilotto.esame2017.persistance.OrderProvider;
import com.maxpilotto.esame2017.persistance.tables.OrderDetailTable;
import com.maxpilotto.esame2017.persistance.tables.OrderTable;

import java.util.List;

import static com.maxpilotto.esame2017.util.Util.printDate;

public class List01 extends AppCompatActivity {
    private ListView listView;
    private OrderAdapter adapter;
    private List<Order> orders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list01);

        orders = Database.get().getOrders();
        adapter = new OrderAdapter(this, orders);

        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Order order = orders.get(position);
            DeleteDialog dialog = new DeleteDialog(
                    getString(R.string.deleteTitle, printDate(order.getDate())),
                    getString(R.string.deleteMsg, printDate(order.getDate()))
            );
            dialog.setCallback(new DeleteDialog.Callback() {
                @Override
                public void onConfirm(DialogInterface dialog) {
                    getContentResolver().delete(OrderProvider.URI_ORDERS, OrderTable._ID + "=" + order.getId(), null);
                    getContentResolver().delete(OrderProvider.URI_ORDER_DETAILS, OrderDetailTable.COLUMN_ORDER + "=" + order.getId(), null);

                    orders.clear();
                    orders.addAll(Database.get().getOrders());
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
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, Ord02.class);

            i.putExtra(Ord02.ORDER_KEY, orders.get(position).getId());

            startActivity(i);
        });

        findViewById(R.id.back).setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        orders.clear();
        orders.addAll(Database.get().getOrders());
        adapter.notifyDataSetChanged();
    }
}
