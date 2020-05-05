package com.example.oldmansupport.Desktop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.oldmansupport.R;

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
                1794));
//        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)View.getLayoutParams();
//        int hei=getActivity().getWindowManager().getDefaultDisplay().getHeight();
//        String height=String.valueOf(hei);
//        Log.i("test999",height);
//        params.height=getActivity().getWindowManager().getDefaultDisplay().getHeight();
//        View.setLayoutParams(params);
//        FragmentTransaction transaction=fragmentManager.beginTransaction();




        return View;
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.llayout_fragment_maninfo);
//        layout.setMinimumHeight(720);
//
//    }
}
