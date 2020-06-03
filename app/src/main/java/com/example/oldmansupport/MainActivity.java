package com.example.oldmansupport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.oldmansupport.Desktop.DesktopSlideAdapter;
import com.example.oldmansupport.Desktop.fragment.fragmentExtraToolsActivity;
import com.example.oldmansupport.Desktop.fragment.fragmentMainDesktopActivity;
import com.example.oldmansupport.Desktop.fragment.fragmentManInfoActivity;
import com.example.oldmansupport.Desktop.fragment.fragmentToolsActivity;
import com.example.oldmansupport.li.CallActivity;
import com.example.oldmansupport.sms.SMSListShowActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    ImageView[] mImageViews;
    List<Fragment> mFragments;
    ViewPager2 mViewPager2;
    public static String location;//定位得到的具体地址
    public double lat;//定位得到的维度
    public double lon;//定位得到的经度

    //public Map<String, String> map = new HashMap<String,String>();
    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    public static final int REQ_CODE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLocaion();//开始定位
        mViewPager2 = findViewById(R.id.pager2);
        LinearLayout layoutDots = findViewById(R.id.llayout_dots);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        //禁止横屏声明
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



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

        //联系人功能与按钮绑定
        bt_linkman=findViewById(R.id.bt_top_linkman);
        bt_linkman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CallActivity.class);
                startActivity(intent);
            }
        });

        //拨号功能与按钮绑定
        bt_call=findViewById(R.id.bt_top_dial);
        bt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"));
                startActivity(intent);
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

    //程序启动则开始定位
    public void startLocaion(){

        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation !=null ) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    lat=amapLocation.getLatitude();
                    lon=amapLocation.getLongitude();
                    location=amapLocation.getAddress();
//                    map.put("当前定位结果来源",String.valueOf(amapLocation.getLocationType()));//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                    map.put("纬度",String.valueOf(amapLocation.getLatitude()));//获取纬度
//                    map.put("经度",String.valueOf(amapLocation.getLongitude()));//获取经度
//                    map.put("精度信息",String.valueOf(amapLocation.getAccuracy()));//获取精度信息
//                    map.put("地址",String.valueOf(amapLocation.getAddress()));//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    map.put("国家信息",String.valueOf(amapLocation.getCountry()));//国家信息
//                    map.put("省信息",String.valueOf(amapLocation.getProvince()));//省信息
//                    map.put("城市信息",String.valueOf(amapLocation.getCity()));//城市信息
//                    map.put("城区信息",String.valueOf(amapLocation.getDistrict()));//城区信息
//                    map.put("街道信息",String.valueOf(amapLocation.getStreet()));//街道信息
//                    map.put("街道门牌号信息",String.valueOf(amapLocation.getStreetNum()));//街道门牌号信息
//                    map.put("城市编码",String.valueOf(amapLocation.getCityCode()));//城市编码
//                    map.put("地区编码",String.valueOf(amapLocation.getAdCode()));//地区编码
//                    map.put("当前定位点的信息",String.valueOf(amapLocation.getAoiName()));//获取当前定位点的AOI信息
                }
            }
        }
    };
}
