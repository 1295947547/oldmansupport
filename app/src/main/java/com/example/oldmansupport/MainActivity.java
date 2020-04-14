package com.example.oldmansupport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldmansupport.map.MapActivity;
import com.example.oldmansupport.phone.CallActivity;
import com.example.oldmansupport.sms.SMSListShowActivity;
import com.example.oldmansupport.weather.weather_main;

import java.time.Instant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public static final int REQ_CODE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_calendar = (Button) findViewById(R.id.btn_calendar);
        Button btn_caculator = (Button) findViewById(R.id.btn_caculator);
        Button btn_weather = (Button) findViewById((R.id.btn_weather));
        Button btn_sms = (Button) findViewById((R.id.btn_sms));
        Button btn_help=findViewById(R.id.btn_help);
        Button btn_phone=findViewById(R.id.btn_phone);
        Button btn_position=findViewById(R.id.btn_position);
        btn_caculator.setOnClickListener(this);
        btn_calendar.setOnClickListener(this);
        btn_sms.setOnClickListener(this);
        btn_weather.setOnClickListener(this);
        btn_help.setOnClickListener(this);
        btn_phone.setOnClickListener(this);
        btn_position.setOnClickListener(this);
    }

        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btn_calendar:
                    Intent intent1=new Intent(MainActivity.this,ScheduleActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.btn_caculator:
                    Intent intent2=new Intent(MainActivity.this,Calculator.class);
                    startActivity(intent2);
                    break;
                case R.id.btn_weather:
                    Intent intent3=new Intent(this, weather_main.class);
                    startActivity(intent3);
                    break;
                case R.id.btn_sms:
                    Log.i("test","ok");
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                            permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS},1);
                    }
                    else{
                        Intent intent4=new Intent(this, SMSListShowActivity.class);
                        startActivity(intent4);
                    }
                    break;
                case R.id.btn_help:
                    Intent intenyhelp = new Intent();
                    intenyhelp.setAction(Intent.ACTION_CALL);
                    intenyhelp.setData(Uri.parse("tel:"+110));
                    startActivity(intenyhelp);
                    break;
                case R.id.btn_phone:
                    Intent intentphone=new Intent(MainActivity.this, CallActivity.class);
                    startActivity(intentphone);
                    break;
                case R.id.btn_position:
                    Intent intentposition=new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intentposition);
                    break;
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        //判断用户是否，同意 获取短信授权
        if (requestCode == REQ_CODE_CONTACT && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //获取到读取短信权限
            Intent intent=new Intent(MainActivity.this,SMSListShowActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "未获取到短信权限", Toast.LENGTH_SHORT).show();
        }
    }

    }
