package com.maxpilotto.esame2017.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maxpilotto.esame2017.R;
import com.maxpilotto.esame2017.models.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends ArrayAdapter<Product> {
    private int[] counters;

    public ProductAdapter(@NonNull Context context, List<Product> list) {
        super(context, 0, list);

        counters = new int[list.size()];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final int pos = position;
        View view = convertView;
        Product p = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.cell_product, parent, false);
        }

        TextView count = view.findViewById(R.id.count);
        TextView name = view.findViewById(R.id.name);

        name.setText(p.getName());
        count.setText(counters[position] + "");

        view.setOnClickListener(v -> {
            counters[pos] = counters[pos] + 1;
            count.setText(counters[pos] + "");
        });

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        counters = new int[getCount()];
    }

    public Map<Product, Integer> getProducts() {
        Map<Product, Integer> products = new HashMap<>();

        for (int i = 0; i < getCount(); i++) {
            products.put(getItem(i), counters[i]);
        }

        return products;
    }
}
