package com.example.oldmansupport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView hello = findViewById(R.id.hello);
        hello.setText("老年人助手");
        hello.setTextColor(Color.RED);
        hello.setTextSize(30);
        Button btn1 = (Button) findViewById(R.id.btnOne);
        Button btn2 = (Button) findViewById(R.id.btnTwo);
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ScheduleActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Calculator.class);
                startActivity(intent);
            }
        });
    }
}
