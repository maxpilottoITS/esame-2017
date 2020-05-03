package com.maxpilotto.esame2017.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxpilotto.esame2017.R;
import com.maxpilotto.esame2017.adapters.ProductAdapter;
import com.maxpilotto.esame2017.persistance.Database;

import static com.maxpilotto.esame2017.util.Util.*;

public class Ord01 extends AppCompatActivity {
    private TextView today;
    private ListView products;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ord01);

        today = findViewById(R.id.today);
        products = findViewById(R.id.list);

        adapter = new ProductAdapter(this, Database.get().getProducts());
        today.setText(printDate(today()));

        findViewById(R.id.cancel).setOnClickListener(v -> {

        });
        findViewById(R.id.confirm).setOnClickListener(v -> {

        });
    }
}
