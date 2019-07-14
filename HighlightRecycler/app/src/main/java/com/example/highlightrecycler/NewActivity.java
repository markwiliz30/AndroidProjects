package com.example.highlightrecycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.highlightrecycler.Common.Common;

public class NewActivity extends AppCompatActivity {
    public ImageView imageView;
    public TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_item);

        textView = (TextView)findViewById(R.id.text_view);
        imageView = (ImageView)findViewById(R.id.image_view);

        textView.setText(Common.currentItem.getName());

        if(!Common.currentItem.isChecked()){
            imageView.setImageResource(R.drawable.ic_clear_black_24dp);
        }else
        {
            imageView.setImageResource(R.drawable.ic_check_black_24dp);
        }
    }
}
