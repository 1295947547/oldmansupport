package com.example.oldmansupport.maninfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldmansupport.MainActivity;
import com.example.oldmansupport.R;

import java.util.ArrayList;
public class ManinfoshowActivity extends AppCompatActivity {


    private DBOpenHelper mDBOpenHelper;
    private ImageView maninfoshow_image;
    private TextView maninfoshow_name;
    private TextView maninfoshow_phonenumber;
    private TextView maninfoshow_sex;
    private TextView maninfoshow_birth;
    private TextView maninfoshow_healthstate;
    private TextView maninfoshow_address;
    private TextView maninfoshow_signout;
    SharedPreferences prefs;
    private int idman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maninfoshow);
        mDBOpenHelper = new DBOpenHelper(this);

        maninfoshow_address=findViewById(R.id.maninfoshow_address);
        ImageView maninfoshow_back=findViewById(R.id.maninfoshow_back);
        maninfoshow_birth=findViewById(R.id.maninfoshow_birth);
        Button maninfoshow_edit=(Button)findViewById(R.id.maninfoshow_edit);
        maninfoshow_healthstate=findViewById(R.id.maninfoshow_healthstate);
        maninfoshow_image=findViewById(R.id.maninfoshow_image);
        maninfoshow_name=findViewById(R.id.maninfoshow_name);
        maninfoshow_phonenumber=findViewById(R.id.maninfoshow_phonenumber);
        maninfoshow_sex=findViewById(R.id.maninfoshow_sex);
        maninfoshow_signout=findViewById(R.id.maninfoshow_signout);

        prefs = getSharedPreferences("userlogin", MODE_PRIVATE);



        maninfoshow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(ManinfoshowActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        maninfoshow_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManinfoshowActivity.this,AlterInfoActivity.class);
                intent.putExtra("idman",idman);
                startActivity(intent);

            }
        });

        maninfoshow_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ManinfoshowActivity.this,LoginActivity.class);
                //将缓存置空
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("account", "");
                editor.putString("password", "");
                editor.putString("name","");
                editor.commit();
                startActivity(intent);
                finish();
                Toast toast=Toast.makeText(ManinfoshowActivity.this,"您已退出登录",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }


    public void onResume() {
        super.onResume();
     //   Log.i("test999Maninfo","onresume is runing");
        initview();
    }


    void initview(){
        Intent intent=getIntent();
        idman=intent.getIntExtra("idman",0);
        ArrayList<User> data=mDBOpenHelper.getAllData();
        Log.i("test999Maninfo","backManinfoshow");
        for (int i = 0; i < data.size(); i++) {
            User user = data.get(i);
            if(idman==user.get_id()) {
                Log.i("test999Maninfo",user.getName());
                maninfoshow_name.setText(user.getName());
                maninfoshow_sex.setText(user.getSex());
                if(user.getSex().equals("女")){
                    maninfoshow_image.setImageResource(R.drawable.womanavatar);
                }
                else{
                    maninfoshow_image.setImageResource(R.drawable.manavatar);
                }
                maninfoshow_phonenumber.setText(user.getPhonenumber());
                maninfoshow_birth.setText(user.getBirth());
                maninfoshow_address.setText(user.getAddress());
                maninfoshow_healthstate.setText(user.getHealthstate());
                break;
            }
        }


    }
}

