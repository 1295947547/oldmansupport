package com.example.oldmansupport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.oldmansupport.map.MapActivity;
//import com.example.oldmansupport.phone.CallActivity;
import com.example.oldmansupport.Desktop.DesktopSlideAdapter;
import com.example.oldmansupport.Desktop.fragment.fragmentExtraToolsActivity;
import com.example.oldmansupport.Desktop.fragment.fragmentMainDesktopActivity;
import com.example.oldmansupport.Desktop.fragment.fragmentManInfoActivity;
import com.example.oldmansupport.Desktop.fragment.fragmentToolsActivity;
import com.example.oldmansupport.sms.SMSContentActivity;
import com.example.oldmansupport.sms.SMSListShowActivity;
import com.example.oldmansupport.weather.weather_main;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    ImageView[] mImageViews;
    List<Fragment> mFragments;
    ViewPager2 mViewPager2;


    public static final int REQ_CODE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager2 = findViewById(R.id.pager2);
        LinearLayout layoutDots = findViewById(R.id.llayout_dots);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }



        //修改图标的大小
        Button bt_linkman=(Button)findViewById(R.id.bt_top_linkman);
        Drawable da_linkman=getResources().getDrawable(R.drawable.linkman);
        da_linkman.setBounds(0,35,180,210);
        bt_linkman.setTextColor(Color.parseColor("#FFFFFF"));
        bt_linkman.setTextSize(16);
        bt_linkman.setCompoundDrawables(null,da_linkman,null,null);

        //修改图标的大小
        Button bt_call=(Button)findViewById(R.id.bt_top_dial);
        Drawable da_call=getResources().getDrawable(R.drawable.call);
        da_call.setBounds(0,35,180,210);
        bt_call.setTextColor(Color.parseColor("#FFFFFF"));
        bt_call.setTextSize(16);
        bt_call.setCompoundDrawables(null,da_call,null,null);

        //修改图标的大小
        Button bt_sms=(Button)findViewById(R.id.bt_top_sms);
        Drawable da_sms=getResources().getDrawable(R.drawable.sms);
        da_sms.setBounds(0,35,200,210);
        bt_sms.setTextColor(Color.parseColor("#FFFFFF"));
        bt_sms.setTextSize(16);
        bt_sms.setCompoundDrawables(null,da_sms,null,null);





        bt_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.
                        permission.READ_SMS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS},1);
                }
                else {
                    Intent intent2 = new Intent(MainActivity.this, SMSListShowActivity.class);
                    startActivity(intent2);
                }
            }
        });



        //region 设置ViewPager2和页面滑动时的小圆点动态
        mFragments = new ArrayList<>();
        mFragments.add(new fragmentManInfoActivity());
        mFragments.add(new fragmentMainDesktopActivity());
        mFragments.add(new fragmentToolsActivity());
        mFragments.add(new fragmentExtraToolsActivity());
        DesktopSlideAdapter mAdapter = new DesktopSlideAdapter(this, mFragments);
        mViewPager2.setAdapter(mAdapter);
        mViewPager2.setCurrentItem(1);

        mImageViews = new ImageView[mFragments.size()];
        for (int i = 0; i < mFragments.size(); i++) {
            mImageViews[i] = new ImageView(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置间距
            params.setMargins(0, 3, 0, 3);
            mImageViews[i].setLayoutParams(params);
            if (i == 1) {
                mImageViews[i].setImageResource(R.mipmap.blue_dot);
            } else {
                mImageViews[i].setImageResource(R.mipmap.grey_dot);
            }
            layoutDots.addView(mImageViews[i]);
        }
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            //页面正在滑动的时候会调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面改变以后会调用
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                LinearLayout layout_dots = findViewById(R.id.llayout_dots);
                LinearLayout layout_residentBottom = findViewById(R.id.llayout_residentBottom);
                for (int i = 0; i < mFragments.size(); i++) {
                    if (position == i) {
                        mImageViews[i].setImageResource(R.mipmap.blue_dot);
                    } else {
                        mImageViews[i].setImageResource(R.mipmap.grey_dot);
                    }
                    if (position == 0) {

                        layout_dots.setVisibility(View.GONE);
                        layout_residentBottom.setVisibility(View.GONE);
                    } else {
                        layout_dots.setVisibility(View.VISIBLE);
                        layout_residentBottom.setVisibility(View.VISIBLE);
                    }
                }
            }

            //页面状态改变时被调用
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //endregionhe

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
