package com.example.user.firebasepicasso2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddPostActivity extends AppCompatActivity {

    EditText mTitleEt, mDescrEt;
    ImageView mPostIv;
    Button mUploadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
    }
}
