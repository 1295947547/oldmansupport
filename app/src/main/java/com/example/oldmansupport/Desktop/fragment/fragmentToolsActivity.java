package com.example.oldmansupport.Desktop.fragment;


import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.example.oldmansupport.R;
import com.example.oldmansupport.Tools.FlashLightActivity;

public class fragmentToolsActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View View=inflater.inflate(R.layout.activity_fragment_tools,container,false);
        //TextView txt_content = (TextView) View.findViewById(R.id.tvInfo4);
        //txt_content.setText("第一个Fragment");

        Button bt_calculator=(Button)View.findViewById(R.id.bt_top_calculator);
        Drawable da_calculator=getResources().getDrawable(R.drawable.calculator);
        da_calculator.setBounds(0,60,300,300);
        bt_calculator.setTextColor(Color.parseColor("#FFFFFF"));
        bt_calculator.setTextSize(25);
        bt_calculator.setCompoundDrawables(null,da_calculator,null,null);

        //修改图标的大小
        Button bt_memorandum=(Button)View.findViewById(R.id.bt_top_memorandum);
        Drawable da_memorandum=getResources().getDrawable(R.drawable.memorandum);
        da_memorandum.setBounds(0,40,280,300);
        bt_memorandum.setTextColor(Color.parseColor("#FFFFFF"));
        bt_memorandum.setTextSize(25);
        bt_memorandum.setCompoundDrawables(null,da_memorandum,null,null);

        //修改图标的大小
        Button bt_flashlight=(Button)View.findViewById(R.id.bt_top_flashlight);
        Drawable da_flashlight=getResources().getDrawable(R.drawable.flashlight);
        da_flashlight.setBounds(0,60,250,300);
        bt_flashlight.setTextColor(Color.parseColor("#FFFFFF"));
        bt_flashlight.setTextSize(25);
        bt_flashlight.setCompoundDrawables(null,da_flashlight,null,null);
        //修改图标的大小
        Button bt_gps=(Button)View.findViewById(R.id.bt_top_gps);
        Drawable da_gps=getResources().getDrawable(R.drawable.gps);
        da_gps.setBounds(0,60,280,300);
        bt_gps.setTextColor(Color.parseColor("#FFFFFF"));
        bt_gps.setTextSize(25);
        bt_gps.setCompoundDrawables(null,da_gps,null,null);

        //修改图标的大小
        Button bt_trash=(Button)View.findViewById(R.id.bt_top_trash_cleanup);
        Drawable da_trash=getResources().getDrawable(R.drawable.trashcleanup);
        da_trash.setBounds(0,60,250,300);
        bt_trash.setTextColor(Color.parseColor("#FFFFFF"));
        bt_trash.setTextSize(25);
        bt_trash.setCompoundDrawables(null,da_trash,null,null);
        //修改图标的大小
        Button bt_music=(Button)View.findViewById(R.id.bt_top_music);
        Drawable da_music=getResources().getDrawable(R.drawable.music);
        da_music.setBounds(0,60,280,300);
        bt_music.setTextColor(Color.parseColor("#FFFFFF"));
        bt_music.setTextSize(25);
        bt_music.setCompoundDrawables(null,da_music,null,null);


        bt_flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                    Intent intent1=new Intent(getActivity(), FlashLightActivity.class);
                    startActivity(intent1);

            }
        });

        bt_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                //TODO:实现跳转系统播放器，目前还没找到方法
            }
        });

        //地图导航与按钮绑定bt_top_gps
        Button bt_top_gps=View.findViewById(R.id.bt_top_gps);
        bt_top_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmapNaviParams params = new AmapNaviParams(null, null,null, AmapNaviType.DRIVER);
                params.setUseInnerVoice(true);
                params.setMultipleRouteNaviMode(true);
                params.setNeedDestroyDriveManagerInstanceWhenNaviExit(true);
                //发起导航
                AmapNaviPage.getInstance().showRouteActivity(getActivity(), params, null);
            }
        });






        return View;
    }
}