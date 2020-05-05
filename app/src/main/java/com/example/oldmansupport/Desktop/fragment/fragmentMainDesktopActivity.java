package com.example.oldmansupport.Desktop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oldmansupport.MainActivity;
import com.example.oldmansupport.R;
import com.example.oldmansupport.ScheduleActivity;

public class fragmentMainDesktopActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.activity_fragment_main_desktop, container, false);
        //TextView txt_content = (TextView) View.findViewById(R.id.tvInfo4);
        //txt_content.setText("第一个Fragment");
        Button bt_calendar=(Button)View.findViewById(R.id.bt_top_calendar);
        bt_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent1=new Intent();
                intent1.setClass(getActivity(),ScheduleActivity.class);
                startActivity(intent1);

            }
        });
        return View;
    }


//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Button button = (Button) getActivity().findViewById(R.id.bt_top_camera);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//            }
//        });
//
//    }

}
