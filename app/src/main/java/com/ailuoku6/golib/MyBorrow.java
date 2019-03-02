package com.ailuoku6.golib;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ailuoku6.golib.Adapter.MyBoFragmentAdapter;
import com.ailuoku6.golib.Fragment.Book_histFragment;
import com.ailuoku6.golib.Fragment.Book_lstFragment;
import com.jude.swipbackhelper.SwipeBackHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MyBorrow extends AppCompatActivity {

    private final String[] titles = new String[]{"当前借阅","借阅历史"};

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(100)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.1f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setClosePercent(0.8f);//触发关闭Activity百分比

        setContentView(R.layout.activity_my_borrow2);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("我的借阅");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.mTablayout);

        fragments = new ArrayList<>();

        for (String title:titles){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);

        fragments.add(Book_lstFragment.newInstance());
        fragments.add(Book_histFragment.newInstance());

        MyBoFragmentAdapter myBoFragmentAdapter = new MyBoFragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(titles));

        viewPager.setAdapter(myBoFragmentAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


//        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
//        viewPager.setAdapter(adapter);
//
//        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout) findViewById(R.id.recycler_tab_layout);
//        recyclerTabLayout.setUpWithAdapter(new CustomRecyclerViewAdapter(viewPager));



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

}
