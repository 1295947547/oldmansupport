package com.example.oldmansupport.maninfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldmansupport.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AlterInfoActivity extends AppCompatActivity {


    int idman=0;
    private DBOpenHelper mDBOpenHelper;
    private EditText et_maninfoshow_name;
    private Spinner sp_maninfoshow_sex;
    private TextView et_maninfoshow_birth;
    private EditText et_maninfoshow_healthstate;
    private EditText et_maninfoshow_address;
    private ImageView maninfoshow_back;
    private Button maninfoshow_check;

    private String sexselected;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_info);

        mDBOpenHelper = new DBOpenHelper(this);

        et_maninfoshow_address=findViewById(R.id.et_maninfoshow_address);
        maninfoshow_back=findViewById(R.id.maninfoshow_back);
        et_maninfoshow_birth=findViewById(R.id.et_maninfoshow_birth);
        et_maninfoshow_birth.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        maninfoshow_check=findViewById(R.id.maninfoshow_check);
        et_maninfoshow_healthstate=findViewById(R.id.et_maninfoshow_healthstate);
        et_maninfoshow_name=findViewById(R.id.et_maninfoshow_name);
        sp_maninfoshow_sex=findViewById(R.id.sp_maninfoshow_sex);

        sp_maninfoshow_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexselected=(String)sp_maninfoshow_sex.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_maninfoshow_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPick();
            }
        });

        maninfoshow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AlterInfoActivity.this,ManinfoshowActivity.class);
                startActivity(intent);
            }
        });

        maninfoshow_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifiedinfo();
            }
        });



    }

    public void onResume() {
        super.onResume();
        initview();
    }

    void showDialogPick(){
        final StringBuffer time = new StringBuffer();
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(AlterInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                time.append(year + "-" + (month+1) + "-" + dayOfMonth);
                et_maninfoshow_birth.setText(time);
            }
        },year,month,day);
        datePickerDialog.show();

    }


    void initview(){
        Intent intent=getIntent();
        idman=intent.getIntExtra("idman",0);
        ArrayList<User> data=mDBOpenHelper.getAllData();
        for (int i = 0; i < data.size(); i++) {
            User usertp = data.get(i);
            if(idman==usertp.get_id()) {
                user=usertp;
                et_maninfoshow_name.setHint(user.getName());
                if(user.getSex().equals("男")){
                    sp_maninfoshow_sex.setSelection(0);
                }
                else{
                    sp_maninfoshow_sex.setSelection(1);
                }
                et_maninfoshow_birth.setHint(user.getBirth());
                et_maninfoshow_address.setHint(user.getAddress());
                et_maninfoshow_healthstate.setHint(user.getHealthstate());
                break;
            }
        }

    }


    void modifiedinfo(){
        String name = et_maninfoshow_name.getText().toString().trim();
        String birth = et_maninfoshow_birth.getText().toString().trim();
        String sex = sexselected;
        String healthstate = et_maninfoshow_healthstate.getText().toString().trim();
        String address = et_maninfoshow_address.getText().toString().trim();

        if(name.equals("")){
            name=user.getName();
        }
        if(sex.equals("")){
            sex=user.getSex();
        }
        if(birth.equals("")){
            birth=user.getBirth();
        }
        if(healthstate.equals("")){
            healthstate=user.getHealthstate();
        }
        if(address.equals("")){
            address=user.getAddress();
        }

        mDBOpenHelper.updateinfo(name,sex,birth,healthstate,address,user.get_id());

        Toast toast =Toast.makeText(AlterInfoActivity.this,"修改成功",Toast.LENGTH_SHORT);
        toast.show();
        et_maninfoshow_address.setCursorVisible(false);
        et_maninfoshow_birth.setCursorVisible(false);
        et_maninfoshow_healthstate.setCursorVisible(false);
        et_maninfoshow_name.setCursorVisible(false);
        Intent intent=new Intent(AlterInfoActivity.this,ManinfoshowActivity.class);
        intent.putExtra("idman",idman);
        startActivity(intent);


    }


}

