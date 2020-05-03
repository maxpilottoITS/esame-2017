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
import com.maxpilotto.esame2017.modules.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Map<Product, Integer> counts = new HashMap<>();

    public ProductAdapter(@NonNull Context context, List<Product> list) {
        super(context, 0, list);

        for (Product p : list) {
            counts.put(p, 0);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Product p = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.cell_product, parent, false);
        }

        TextView count = view.findViewById(R.id.count);
        TextView name = view.findViewById(R.id.name);

        name.setText(p.getName());
        count.setText(counts.get(p) + "");

        view.setOnClickListener(v -> {
            counts.put(p, counts.get(p) + 1);
        });

        return view;
    }

    public Map<Product, Integer> getProducts() {
        return counts;
    }
}
