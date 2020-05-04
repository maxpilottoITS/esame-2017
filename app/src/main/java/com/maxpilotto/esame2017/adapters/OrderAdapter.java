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
import com.maxpilotto.esame2017.models.Order;

import java.util.List;

import static com.maxpilotto.esame2017.util.Util.printDate;

public class OrderAdapter extends ArrayAdapter<Order> {
    public OrderAdapter(@NonNull Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Order order = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.cell_order,parent,false);
        }

        ((TextView)view.findViewById(R.id.date)).setText(printDate(order.getDate()));
        ((TextView)view.findViewById(R.id.total)).setText(order.totalProducts().toString());
        ((TextView)view.findViewById(R.id.price)).setText(order.totalPrice().toString());

        return view;
    }
}
