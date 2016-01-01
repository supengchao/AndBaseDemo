package com.andbase.im.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ab.activity.AbActivity;
import com.ab.view.sliding.AbSlidingSmoothFixTabView;
import com.ab.view.titlebar.AbTitleBar;
import com.andbase.R;
import com.andbase.demo.activity.Fragment1;
import com.andbase.demo.activity.Fragment4;

public class ContacterActivity extends AbActivity {
	
    private AbSlidingSmoothFixTabView mAbSlidingTabView = null;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAbContentView(R.layout.im_contact);
        
        AbTitleBar mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.contact_name);
        mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleBarBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
       
        mAbSlidingTabView = (AbSlidingSmoothFixTabView)findViewById(R.id.mAbSlidingTabView);
        
        //缓存数量
        mAbSlidingTabView.getViewPager().setOffscreenPageLimit(3);
        
        //禁止滑动
        /*mAbSlidingTabView.getViewPager().setOnTouchListener(new OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
            
        });*/

        Fragment1 page1 = new Fragment1();
        ContacterFragment mContacterFragment = new ContacterFragment();
        Fragment4 page3 = new Fragment4();
        
        List<Fragment> mFragments = new ArrayList<Fragment>();
        mFragments.add(page1);
        mFragments.add(mContacterFragment);
        mFragments.add(page3);
        
        List<String> tabTexts = new ArrayList<String>();
        tabTexts.add("会话");
        tabTexts.add("好友");
        tabTexts.add("群组");
        //设置样式
        mAbSlidingTabView.setTabTextSize(15);
        mAbSlidingTabView.setTabColor(Color.BLACK);
        mAbSlidingTabView.setTabSelectedColor(Color.rgb(30, 168, 131));
        mAbSlidingTabView.setTabLayoutBackgroundResource(R.drawable.slide_top);
        //演示增加一组
        mAbSlidingTabView.addItemViews(tabTexts, mFragments);
        
        mAbSlidingTabView.setTabPadding(20, 8, 20, 8);
		
  	    //showProgressDialog();
        
        
    }
    
    @Override
    protected void onResume() {
	   super.onResume();
    }
	
    public void onPause() {
	   super.onPause();

    }
   

}


