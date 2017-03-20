package com.bogoslovov.kaloqn.drawing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by kaloqn on 3/20/17.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context ctx;
    private String[] contentArray;

    public SpinnerAdapter(Context context, int resource, String[] objects) {
        super(context,  R.layout.spinner_row, R.id.save_image_spinner, objects);
        this.ctx = context;
        this.contentArray = objects;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_row, parent, false);

        TextView textView = (TextView) row.findViewById(R.id.spinner_text_view);
        textView.setText(contentArray[position]);

        return row;
    }
}