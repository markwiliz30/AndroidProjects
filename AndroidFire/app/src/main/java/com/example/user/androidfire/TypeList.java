package com.example.user.androidfire;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TypeList extends ArrayAdapter<Types> {
    private Activity context;
    private List<Types> types;

    public TypeList(Activity context, List<Types> types){
        super(context, R.layout.type_list_layout, types);
        this.context = context;
        this.types = types;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.type_list_layout, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.textViewRating);

        Types type = types.get(position);

        textViewName.setText(type.getTypeName());
        textViewRating.setText(String.valueOf(type.getTypeRating()));
        return  listViewItem;
    }
}
