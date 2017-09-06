package com.liwenzhi.aib.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.rxpermissions.OnRequestPermissionListener;
import com.liwenzhi.aib.ui.mvp.presenter.SplashActivityPresenter;
import com.liwenzhi.aib.ui.mvp.view.SplashActivityView;
import com.liwenzhi.aib.util.CommonUtils;
import com.liwenzhi.aib.widget.RoundProgressBar;
import com.liwenzhi.aib.widget.dialog.LoadingDialog;

/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity<SplashActivityView, SplashActivityPresenter> implements SplashActivityView, View.OnClickListener {

    private RoundProgressBar mRpb_skip_splash;

    private long progressingTime = 5000; // splash界 面显示时长
    private MyHandler mHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermissions();
        setContentView(R.layout.activity_splash);
        initView();
        initListener();
        initData();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        // 跳过Splash按钮
        mRpb_skip_splash = (RoundProgressBar) findViewById(R.id.rpb_skip_splash);
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        if (mRpb_skip_splash != null && this != null)
            mRpb_skip_splash.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mHandler = new MyHandler();
        mHandler.sendEmptyMessageDelayed(111, progressingTime);
        mRpb_skip_splash.setProgressingTime(progressingTime);
        mRpb_skip_splash.start();
    }

    private void initPermissions() {
        registerPermissions();
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET};
        requestPermissions(new OnRequestPermissionListener() {
            @Override
            public void onGrantedPermission() {

            }
        }, "拒绝权限", permissions);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHandler != null) {

        }
    }


    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CommonUtils.e("handleMessage");
            if (msg.what == 111) {
                CommonUtils.e("msg.what == 111");
                gotoMainActivity();
            }
        }
    }


    @Override
    protected SplashActivityPresenter initPresenter() {
        return new SplashActivityPresenter();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rpb_skip_splash:
                // 直接跳转主界面
                mHandler.removeMessages(111);
                mRpb_skip_splash.stop();
                gotoMainActivity();
                break;
        }
    }

    private LoadingDialog mLoadingDialog; //显示正在加载的对话框

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, getString(R.string.loading), false);
        }

        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.hide();
        }
    }


    @Override
    public Context getActivityContext() {
        return this;
    }

    /**
     * 跳转到主界面
     */
    @Override
    public void gotoMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void setRemindString(String s) {
        showToast("" + s);
        finish();
    }

    @Override
    public void finishActivity() {
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mRpb_skip_splash.stop();
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
            mRpb_skip_splash = null;
        }

        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;
        }

    }
}
