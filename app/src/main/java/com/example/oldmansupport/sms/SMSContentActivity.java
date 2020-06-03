package com.example.oldmansupport.sms;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldmansupport.R;

public class SMSContentActivity extends AppCompatActivity {


    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smscontent);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        final String names=bundle.getString("names");

        TextView tv_iphonenum = (TextView) findViewById(R.id.tv_title);
        tv_iphonenum.setText(names);
        TextView tv_content = (TextView) findViewById(R.id.tv_smscontent);
        tv_content.setText(message);

        Button bt_back = (Button) findViewById(R.id.bt_back);
        Button bt_sentsms = (Button) findViewById(R.id.bt_sent_SMS);
        final Button bt_menu = (Button) findViewById(R.id.bt_menu);
        //返回按钮
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMSContentActivity.this, SMSListShowActivity.class);
                startActivity(intent);
                overridePendingTransition( R.layout.push_right_out,R.layout.push_left_in);
            }
        });
        //转到发送短信页面
        bt_sentsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("names", names);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(SMSContentActivity.this, SMSSentActivity.class);
                startActivity(intent);
            }
        });
        //弹出菜单
        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(bt_menu);
            }
        });

    }

    //菜单选择和响应
    private void showPopupMenu(final View view){
        final PopupMenu popupMenu = new PopupMenu(this,view);
        //menu 布局
        popupMenu.getMenuInflater().inflate(R.menu.main,popupMenu.getMenu());
        //点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_linkman:
                        Toast.makeText(view.getContext(),"挑转添加联系人界面", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.delete_sms:
//                            Intent intent = new Intent(SMSContentActivity.this, deleteSMSActivity.class);
//                            startActivity(intent);
                        AlertDialog.Builder builder=new AlertDialog.Builder(SMSContentActivity.this);

                        builder.setTitle("删除短信");
                        builder.setMessage("确定删除该短信？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //删除短信
                                try {
                                    ContentResolver cr = getContentResolver();

                                    String id=bundle.getString("_id");
                                    int _id=Integer.parseInt(id);
                                    cr.delete(Uri.parse("content://sms/"), "_id=?",new String[]{id});
                                    Toast.makeText(view.getContext(),"短信已删除", Toast.LENGTH_SHORT).show();

                                    finish();


                                }
                                catch (Exception e) {
                                    Log.d("deleteSMS", "Exception:: " + e);
                                }

                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();



                        break;
                    default:
                        break;
                }
                return false;
            }
        });


        //菜单关闭时的操作
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
//                    Toast.makeText(view.getContext(),"菜单关闭", Toast.LENGTH_SHORT).show();
            }
        });
        //显示菜单，不要少了这一步
        popupMenu.show();


    }

}
