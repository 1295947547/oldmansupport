package com.example.oldmansupport.Tools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oldmansupport.R;

public class EmergencyContactActivity extends AppCompatActivity {

    EditText et_mobile;
    private Button bt_cancel;
    private Button bt_saveinfo;
    SharedPreferences prefs;
    String etmobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);
        et_mobile=findViewById(R.id.et_mobile);
        bt_cancel=findViewById(R.id.bt_cancel);
        bt_saveinfo=findViewById(R.id.bt_save_info);

        prefs = getSharedPreferences("emergencycontact", MODE_PRIVATE);
        final String prefcontact=prefs.getString("contact","");
        if(prefcontact.isEmpty()){
            et_mobile.setHint("请设置紧急联系人");
        }
        else{
            et_mobile.setHint(prefcontact);
        }
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        bt_saveinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etmobile=et_mobile.getText().toString().trim();
                if(etmobile.equals("")){
                    finish();
                }
                else if(etmobile.length()!=11){
                    Toast.makeText(EmergencyContactActivity.this,"电话格式不符合",Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences.Editor editor=prefs.edit();
                    editor.putString("contact",et_mobile.getText().toString().trim());
                    editor.apply();
                    Toast.makeText(EmergencyContactActivity.this,"您已经修改紧急联系人",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });


    }
}
