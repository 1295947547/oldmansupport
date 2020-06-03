package com.example.oldmansupport.sms;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.oldmansupport.MainActivity;
import com.example.oldmansupport.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SMSListShowActivity extends AppCompatActivity {



    private ListView myListView;
    private  SimpleAdapter adapter;

    private List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smslist_show);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }

        //初始化
        initView();

        Button bt_back = (Button) findViewById(R.id.bt_back);
        Button bt_sentsms = (Button) findViewById(R.id.bt_sent_SMS);
        final Button bt_menu = (Button) findViewById(R.id.bt_menu);
        //返回按钮
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMSListShowActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition( R.layout.push_right_out,R.layout.push_left_in);

            }
        });
        //转到发送短信页面
        bt_sentsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SMSListShowActivity.this, SMSSentActivity.class);
                startActivity(intent);
            }
        });


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //把item的内容传到下一个活动种
                Bundle bundle = new Bundle();
                bundle.putString("_id", (String ) data.get(position).get("_id"));
                bundle.putString("names", (String) data.get(position).get("names"));
                bundle.putString("message", (String) data.get(position).get("message"));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(SMSListShowActivity.this, SMSContentActivity.class);
                Log.i("test", (String) data.get(position).get("message"));
                startActivity(intent);

            }
        });
    }






    private void initView(){
        myListView=(ListView)findViewById(R.id.listView);
        data=new ArrayList<Map<String,Object>>();
        query();
        //适配器，这里strings的数组要和map中的数组名称一样，不然运行报错
        adapter=new SimpleAdapter(this, data,R.layout.listview_itemstyle_sms_show,
                new String[]{"names", "datetime","message"}, new int[]{R.id.text_name,
                R.id.text_date,R.id.text_body});
        myListView.setAdapter(adapter);


    }

    private void query() {

        //读取所有短信
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{"_id", "address", "body", "date", "type"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            int _id;
            String address;
            String body;
            long time;
            int type;
            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                _id = cursor.getInt(0);
                address = cursor.getString(1);
                body = cursor.getString(2);
                time = cursor.getLong(3);
                type = cursor.getInt(4);
                //日期转换，但没有成功
//                Log.i("test", "ok");
                Date date = new Date(time);
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                String dateTime = format.format(date);
//                Log.i("test", "ok");
                String id=String.valueOf(_id);
                map.put("_id",id);
                map.put("names",address);
                map.put("datetime",dateTime);
                map.put("message", body);

                //日志打印，可以检测是否正常运行
                Log.i("test", "_id=" + _id + " address=" + address + " body=" + body + " date=" + time + " type=" + type);
                data.add(map);
                //通知适配器发生改变
//                adapter.notifyDataSetChanged();
//                Log.i("test", "ok");

            }
        }


    }

}
