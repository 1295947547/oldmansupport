package com.example.oldmansupport.Desktop.fragment;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.oldmansupport.R;
import com.example.oldmansupport.li.Map_getPosition;

import java.util.List;

import static com.example.oldmansupport.MainActivity.location;

public class fragmentManInfoActivity extends Fragment {

    LinearLayout linearLayout;
    FragmentManager fragmentManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.activity_fragment_man_info, container, false);
        //TextView txt_content = (TextView) View.findViewById(R.id.tvInfo4);
        //txt_content.setText("第一个Fragment");
       linearLayout=View.findViewById(R.id.llayout_fragment_maninfo); //通过getActivity()获取activity_main.xml文件中id号为line1的LinearLayout布局

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(getActivity().getWindowManager().getDefaultDisplay().getWidth(),
                getActivity().getWindowManager().getDefaultDisplay().getHeight()));
//        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)View.getLayoutParams();
//        int hei=getActivity().getWindowManager().getDefaultDisplay().getHeight();
//        String height=String.valueOf(hei);
//        Log.i("test999",height);
//        params.height=getActivity().getWindowManager().getDefaultDisplay().getHeight();
//        View.setLayoutParams(params);
//        FragmentTransaction transaction=fragmentManager.beginTransaction();


        Button bt_health=(Button)View.findViewById(R.id.bt_top_health_reminder);
        Drawable da_health=getResources().getDrawable(R.drawable.health);
        da_health.setBounds(0,60,300,300);
        bt_health.setTextColor(Color.parseColor("#FFFFFF"));
        bt_health.setTextSize(25);
        bt_health.setCompoundDrawables(null,da_health,null,null);

        //修改图标的大小
        Button bt_emergency=(Button)View.findViewById(R.id.bt_top_emergency_call);
        Drawable da_emergencyt=getResources().getDrawable(R.drawable.emergency);
        da_emergencyt.setBounds(0,40,280,300);
        bt_emergency.setTextColor(Color.parseColor("#FFFFFF"));
        bt_emergency.setTextSize(25);
        bt_emergency.setCompoundDrawables(null,da_emergencyt,null,null);

        //修改图标的大小
        Button bt_phonefinder=(Button)View.findViewById(R.id.bt_top_click_position);
        Drawable da_phonefinder=getResources().getDrawable(R.drawable.oneclick);
        da_phonefinder.setBounds(0,60,250,300);
        bt_phonefinder.setTextColor(Color.parseColor("#FFFFFF"));
        bt_phonefinder.setTextSize(25);
        bt_phonefinder.setCompoundDrawables(null,da_phonefinder,null,null);

        //修改图标的大小
        Button bt_fall=(Button)View.findViewById(R.id.bt_top_fall_detection);
        Drawable da_fall=getResources().getDrawable(R.drawable.fall);
        da_fall.setBounds(0,60,280,300);
        bt_fall.setTextColor(Color.parseColor("#FFFFFF"));
        bt_fall.setTextSize(25);
        bt_fall.setCompoundDrawables(null,da_fall,null,null);

        //紧急联系功能与按钮绑定
        Button bt_top_emergency_call=View.findViewById(R.id.bt_top_emergency_call);
        bt_top_emergency_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+"18166139071"));
                startActivity(intent);
                sendSMS("18166139071",location,getActivity());
            }
        });

        //定位功能与按钮绑定
        Button bt_top_click_position=View.findViewById(R.id.bt_top_click_position);
        bt_top_click_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Map_getPosition.class);
                intent.putExtra("location",location);
                startActivity(intent);
            }
        });

        return View;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.llayout_fragment_maninfo);
//        layout.setMinimumHeight(720);
//
//    }


    //直接调用短信接口发短信
    public void sendSMS(String phoneNumber, String message, final Context context){
        //处理返回的发送状态
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent,
                0);
        // register the Broadcast Receivers
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context,
                                "短信发送成功", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));

        //处理返回的接收状态
        String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
        // create the deilverIntent parameter
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0,
                deliverIntent, 0);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                Toast.makeText(context,
                        "收信人已经成功接收", Toast.LENGTH_SHORT)
                        .show();
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));

        //获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, deliverPI);
        }
    }
}
