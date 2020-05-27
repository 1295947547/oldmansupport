package com.example.oldmansupport.li;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.example.oldmansupport.R;

public class Map_getPosition extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //获取地址
        Intent intent=getIntent();
        String location=intent.getStringExtra("location");
        TextView dataTextView=findViewById(R.id.text_map);
        dataTextView.setText(location);

    }

}
