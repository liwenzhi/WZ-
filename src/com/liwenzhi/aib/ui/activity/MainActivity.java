package com.liwenzhi.aib.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.adapter.FragmentAdapter;
import com.liwenzhi.aib.ui.fragment.KnowledgeFragment;
import com.liwenzhi.aib.ui.fragment.MyInfoFragment;
import com.liwenzhi.aib.ui.fragment.NormalTitleBarFragment;
import com.liwenzhi.aib.ui.mvp.presenter.MainActivityPresenter;
import com.liwenzhi.aib.ui.mvp.view.MainActivityView;
import com.liwenzhi.aib.widget.bottomnavigation.BottomNavigationBar;
import com.liwenzhi.aib.widget.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */
public class MainActivity extends BaseActivity<MainActivityView, MainActivityPresenter> implements MainActivityView {

    @Override
    protected MainActivityPresenter initPresenter() {
        return new MainActivityPresenter();
    }

    ViewPager vp_main;
    BottomNavigationBar bottom_navigation_bar;
    List<Fragment> listFragment = new ArrayList<Fragment>();
    NormalTitleBarFragment fragment_titleBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SplashActivity.class));
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        fragment_titleBar = (NormalTitleBarFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_titleBar);
        vp_main = (ViewPager) findViewById(R.id.vp_main);
        bottom_navigation_bar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        //要先设计模式后再添加图标！
        //设置按钮转移模式
        bottom_navigation_bar.setMode(BottomNavigationBar.MODE_DEFAULT);
        //设置背景
        bottom_navigation_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //设置选中时的图标的颜色
        bottom_navigation_bar.setActiveColor(R.color.blue_main);

        //设置图标、图标的颜色和文字
        bottom_navigation_bar
                .addItem(new BottomNavigationItem(R.drawable.icon_knowledge, "面试知识"))
                .addItem(new BottomNavigationItem(R.drawable.icon_myinfo, "相关介绍"))
                .initialise();
        bottom_navigation_bar.selectTab(0, false);
        vp_main.setCurrentItem(0);

    }

    private void initData() {

        fragment_titleBar.getTitleBar().setTitle("Android面试宝典");
        fragment_titleBar.getTitleBar().setLeftLayoutVisibility(View.GONE);
        listFragment.add(new KnowledgeFragment());
        listFragment.add(new MyInfoFragment());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), listFragment);
        vp_main.setAdapter(adapter);
    }

    private void initEvent() {
        //设置不可滑动关闭
        setSwipeBackEnable(false);

        //监听ViewPager的滑动
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {  //页面滑动后，按钮改变
                bottom_navigation_bar.selectTab(i, false);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        bottom_navigation_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                vp_main.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        homeForDoubleTimes();
    }

    /**
     * 两次点击关闭退出页面
     */
    long currentTime = 0;

    public void homeForDoubleTimes() {

        if ((System.currentTimeMillis() - currentTime) < 3000) {
            jumpToHomeActivity();
        } else {
            currentTime = System.currentTimeMillis();
        }
    }

    /**
     * 跳转到Home页面，而不退出程序
     */
    private void jumpToHomeActivity() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}
