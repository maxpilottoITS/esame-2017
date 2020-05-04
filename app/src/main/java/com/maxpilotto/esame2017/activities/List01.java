package com.maxpilotto.esame2017.activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxpilotto.esame2017.R;
import com.maxpilotto.esame2017.adapters.OrderAdapter;
import com.maxpilotto.esame2017.models.Order;
import com.maxpilotto.esame2017.persistance.Database;

import java.util.List;

public class List01 extends AppCompatActivity {
    private ListView listView;
    private OrderAdapter adapter;
    private List<Order> orders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list01);

        orders = Database.get().getOrders();
        adapter = new OrderAdapter(this,orders);

        listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
