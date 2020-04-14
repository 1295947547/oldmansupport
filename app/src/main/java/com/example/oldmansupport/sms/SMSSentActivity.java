package com.example.oldmansupport.sms;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.oldmansupport.R;

import java.util.ArrayList;

public class SMSSentActivity extends AppCompatActivity {

    private EditText etContent;
    private EditText etPhone;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smssent);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String names=bundle.getString("names");
            EditText et_iphone=(EditText)findViewById(R.id.et_phone);
            //回短信，将短信发件人号码填入收件人框
            et_iphone.setText(names);
            //光标定位
            et_iphone.setSelection(names.length());
        }

        Button bt_back = (Button) findViewById(R.id.bt_back);
        Button bt_linkman=(Button)findViewById(R.id.bt_linkman);
        Button bt_sent=(Button)findViewById(R.id.bt_sentSMSConfirm);

        etPhone=(EditText)findViewById(R.id.et_phone);
        etContent=(EditText)findViewById(R.id.et_content);





        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMSSentActivity.this, SMSListShowActivity.class);
                startActivity(intent);
            }
        });

        bt_linkman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bt_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();

            }
        });

    }

    //发送短信
    private void sendSMS() {
        String content = etContent.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(phone)) {
            SmsManager manager = SmsManager.getDefault();
            ArrayList<String> strings = manager.divideMessage(content);
            for (int i = 0; i < strings.size(); i++) {
                manager.sendTextMessage(phone, null, content, null, null);
            }
            Toast.makeText(SMSSentActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "手机号或内容不能为空", Toast.LENGTH_SHORT).show();
            return;


        }
    }


}
