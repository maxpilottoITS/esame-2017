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
import com.maxpilotto.esame2017.models.OrderDetail;
import com.maxpilotto.esame2017.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends ArrayAdapter<Product> {
    private List<OrderDetail> details;

    public ProductAdapter(@NonNull Context context, List<Product> products) {
        super(context, 0, products);

        details = new ArrayList<>();

        for (Product p : products) {
            details.add(new OrderDetail(p,0));
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
        count.setText(details.get(position).getCount().toString());

        view.setOnClickListener(v -> {
            details.get(position).incrementCount();
            count.setText(details.get(position).getCount().toString());
        });

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        details.clear();

        for (int i=0; i<getCount(); i++){
            details.add(new OrderDetail(getItem(i),0));
        }
    }

    public List<OrderDetail> getProducts() {
        return details;
    }
}
