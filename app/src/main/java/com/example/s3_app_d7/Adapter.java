package com.example.s3_app_d7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter extends ArrayAdapter<User> {

    private int resourceId;

    public Adapter(@NonNull Context context, int textViewResourceId, @NonNull List<User> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView username = (TextView) view.findViewById(R.id.name);
        TextView number = (TextView) view.findViewById(R.id.number);
        username.setText(user.getName());
        number.setText(user.getNumber());
        return view;
    }
}
