package com.example.oldmansupport.Desktop.fragment;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.SharedPreferences;

import com.example.oldmansupport.R;
import com.example.oldmansupport.Tools.EmergencyContactActivity;
import com.example.oldmansupport.li.Map_getPosition;
import com.example.oldmansupport.maninfo.LoginActivity;
import com.example.oldmansupport.sms.SMSContentActivity;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.oldmansupport.MainActivity.location;

public class fragmentManInfoActivity extends Fragment {

    LinearLayout linearLayout;
    FragmentManager fragmentManager;
    RelativeLayout rllt_maninfoshow;
    SharedPreferences prefs;

    TextView fragmaninfoname;
    TextView fragmaninfotip;
    ImageView imag_head;
    String etcontact;



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.activity_fragment_man_info, container, false);
        //TextView txt_content = (TextView) View.findViewById(R.id.tvInfo4);
        //txt_content.setText("第一个Fragment");

       linearLayout=View.findViewById(R.id.llayout_fragment_maninfo); //通过getActivity()获取activity_main.xml文件中id号为line1的LinearLayout布局
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(getActivity().getWindowManager().getDefaultDisplay().getWidth(),
                getActivity().getWindowManager().getDefaultDisplay().getHeight()));


        rllt_maninfoshow=View.findViewById(R.id.rllt_maninfoshow);
        rllt_maninfoshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        fragmaninfoname=View.findViewById(R.id.frag_maninfo_name);
        fragmaninfotip=View.findViewById(R.id.frag_maninfo_tip);
        imag_head= View.findViewById(R.id.imag_head);


        prefs = getActivity().getSharedPreferences("emergencycontact", MODE_PRIVATE);
        etcontact=prefs.getString("contact","");


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


        bt_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent=new Intent(getActivity(), EmergencyContactActivity.class);
                startActivity(intent);

            }
        });
        bt_emergency.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(android.view.View v) {


                if (etcontact.isEmpty()){
                    Toast.makeText(getActivity(),"您还未设置紧急联系人",Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());

                    builder.setTitle("紧急联系");
                    builder.setMessage("确认拨打该紧急联系人电话"+etcontact+"?");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {

                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:"+etcontact));
                                startActivity(intent);
                                sendSMS(etcontact,location,getActivity());

                            }
                            catch (Exception e) {

                            }

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }




                return false;
            }
        });



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



    public void onResume() {
        super.onResume();
        Log.i("test999Maninfo","onresume is runing");
        prefs = getActivity().getSharedPreferences("userlogin", MODE_PRIVATE);
        String prefsphonenumber=prefs.getString("account","");
        String prefpassword=prefs.getString("password","");
        Log.i("test999login","temp"+prefsphonenumber+prefpassword);
        if(!(prefsphonenumber.equals("")||prefpassword.equals(""))){
            String prefname=prefs.getString("name","");
            String prefsex=prefs.getString("sex","");
            fragmaninfoname.setText(prefname);
            fragmaninfotip.setText("查看更多信息 >");
            if(prefsex.equals("女")){
                imag_head.setImageResource(R.drawable.womanavatar);
            }
            else{
                imag_head.setImageResource(R.drawable.manavatar);
            }
        }
        else{
            imag_head.setImageResource(R.drawable.userhead);
            fragmaninfoname.setText("无用户");
            fragmaninfotip.setText("点击登录");
        }
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
