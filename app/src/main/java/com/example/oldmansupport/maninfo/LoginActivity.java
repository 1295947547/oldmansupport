package com.example.oldmansupport.maninfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oldmansupport.MainActivity;
import com.example.oldmansupport.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences prefs;
    int idman=0;
    private DBOpenHelper mDBOpenHelper;
    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;
    private ImageView iv_loginactivity_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        prefs = getSharedPreferences("userlogin", MODE_PRIVATE);

        String prefsphonenumber=prefs.getString("account","");
        String prefpassword=prefs.getString("password","");
        //Log.i("test999login","temp"+prefsphonenumber+prefpassword);
        if(!(prefsphonenumber.equals("")||prefpassword.equals(""))){
            login(prefsphonenumber,prefpassword);
        }

    }



    private void initView() {
        // 初始化控件
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);
        iv_loginactivity_back=findViewById(R.id.iv_loginactivity_back);

        // 设置点击事件监听器
        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
        iv_loginactivity_back.setOnClickListener(this);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            // 跳转到注册界面
            case R.id.tv_loginactivity_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            /**
             * 登录验证：
             *
             * 从EditText的对象上获取文本编辑框输入的数据，并把左右两边的空格去掉
             *  String name = mEtLoginactivityUsername.getText().toString().trim();
             *  String password = mEtLoginactivityPassword.getText().toString().trim();
             *  进行匹配验证,先判断一下用户名密码是否为空，
             *  if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password))
             *  再进而for循环判断是否与数据库中的数据相匹配
             *  if (name.equals(user.getName()) && password.equals(user.getPassword()))
             *  一旦匹配，立即将match = true；break；
             *  否则 一直匹配到结束 match = false；
             *
             *  登录成功之后，进行页面跳转：
             *
             *  Intent intent = new Intent(this, MainActivity.class);
             *  startActivity(intent);
             *  finish();//销毁此Activity
             */
            case R.id.bt_loginactivity_login:
                String phonenumber = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                login(phonenumber,password);
                break;
            case R.id.iv_loginactivity_back:
//                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
        }
    }


    void login(String phonenumber,String password){
        if (!TextUtils.isEmpty(phonenumber) && !TextUtils.isEmpty(password)) {
            ArrayList<User> data = mDBOpenHelper.getAllData();
            boolean match = false;
            for (int i = 0; i < data.size(); i++) {
                User user = data.get(i);
                if (phonenumber.equals(user.getPhonenumber()) && password.equals(user.getPassword())) {
                    match = true;
                    savePreferences(phonenumber,password,user.getName(),user.getSex());
                    idman=user.get_id();
                    break;
                } else {
                    match = false;
                }
            }
            if (match) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ManinfoshowActivity.class);
                intent.putExtra("idman",idman);
                startActivity(intent);
                finish();//销毁此Activity
            } else {
                Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
        }

    }


    void savePreferences(String phonenumber,String password,String name,String sex){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("account", phonenumber);
        editor.putString("password", password);
        editor.putString("name", name);
        editor.putString("sex", sex);
        editor.apply();

    }




}
