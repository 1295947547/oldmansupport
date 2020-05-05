package com.example.oldmansupport.Desktop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.oldmansupport.Desktop.fragment.fragmentExtraToolsActivity;
import com.example.oldmansupport.Desktop.fragment.fragmentMainDesktopActivity;
import com.example.oldmansupport.Desktop.fragment.fragmentManInfoActivity;
import com.example.oldmansupport.Desktop.fragment.fragmentToolsActivity;
import com.example.oldmansupport.R;
import com.example.oldmansupport.ScheduleActivity;

import java.util.ArrayList;
import java.util.List;

public class DesktopSlideActivity extends AppCompatActivity {


//    ImageView[] mImageViews;
//    List<Fragment> mFragments;
//    ViewPager2 mViewPager2;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop_slide);
//
//        mViewPager2=findViewById(R.id.pager2);
//        LinearLayout layoutDots=findViewById(R.id.llayout_dots);
//
//        //动态修改fragment_man_info的宽高
////        linearLayout=
//
//
//        mFragments = new ArrayList<>();
//        mFragments.add(new fragmentManInfoActivity());
//        mFragments.add(new fragmentMainDesktopActivity());
//        mFragments.add(new fragmentToolsActivity());
//        mFragments.add(new fragmentExtraToolsActivity());
//        DesktopSlideAdapter mAdapter=new DesktopSlideAdapter(this,mFragments);
//        mViewPager2.setAdapter(mAdapter);
//        mViewPager2.setCurrentItem(1);
//
//        mImageViews=new ImageView[mFragments.size()];
//        for(int i=0;i<mFragments.size();i++){
//            mImageViews[i]=new ImageView(DesktopSlideActivity.this);
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            //设置间距
//            params.setMargins(0,3,0,3);
//            mImageViews[i].setLayoutParams(params);
//            if(i==1){
//                mImageViews[i].setImageResource(R.mipmap.blue_dot);
//            }
//            else {
//                mImageViews[i].setImageResource(R.mipmap.grey_dot);
//            }
//            layoutDots.addView(mImageViews[i]);
//        }
//        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            //页面正在滑动的时候会调用
//            @Override
//            public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels){
//            }
//            //页面改变以后会调用
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                LinearLayout layout_dots=findViewById(R.id.llayout_dots);
//                LinearLayout layout_residentBottom=findViewById(R.id.llayout_residentBottom);
//                for(int i=0;i<mFragments.size();i++){
//                    if(position==i){
//                        mImageViews[i].setImageResource(R.mipmap.blue_dot);
//                    }
//                    else{
//                        mImageViews[i].setImageResource(R.mipmap.grey_dot);
//                    }
//                    if(position==0){
//
//                        layout_dots.setVisibility(View.GONE);
//                        layout_residentBottom.setVisibility(View.GONE);
//                    }
//                    else{
//                        layout_dots.setVisibility(View.VISIBLE);
//                        layout_residentBottom.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//            //页面状态改变时被调用
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });






    }




    /*
    监听ViewPager滑动效果
     */

//    mViewPager2.registerOnPageChangeCallback()


//    private class onPageChangeListener extends ViewPager2.OnPageChangeCallback{
//        @Override
//        public void onPageSelected(int position){
//            for(int i=0;i<mFragments.size();i++){
//                if(position==i){
//                    mImageViews[i].setImageResource(R.mipmap.blue_dot);
//                }
//                else{
//                    mImageViews[i].setImageResource(R.mipmap.grey_dot);
//                }
//            }
//
//        }


}

