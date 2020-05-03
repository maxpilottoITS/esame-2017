package com.maxpilotto.esame2017.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maxpilotto.esame2017.R;
import com.maxpilotto.esame2017.persistance.Database;

public class Home01 extends AppCompatActivity {
    private TextView total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home01);

        total = findViewById(R.id.total);

        findViewById(R.id.insert).setOnClickListener(v -> {
            startActivity(new Intent(this, Ord01.class));
        });
        findViewById(R.id.update).setOnClickListener(v -> {
            startActivity(new Intent(this, List01.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        total.setText(getString(R.string.totalOrders,Database.get().getOrderCount()));
    }
}
