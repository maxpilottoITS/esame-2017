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

public class ProductAdapter extends ArrayAdapter<OrderDetail> {
    public ProductAdapter(@NonNull Context context, List<OrderDetail> details) {
        super(context, 0, details);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        OrderDetail o = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.cell_product, parent, false);
        }

        TextView count = view.findViewById(R.id.count);
        TextView name = view.findViewById(R.id.name);

        name.setText(o.getProduct().getId().toString());
        count.setText(o.getCount().toString());

        return view;
    }
}
