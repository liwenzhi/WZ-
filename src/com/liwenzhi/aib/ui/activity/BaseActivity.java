package com.liwenzhi.aib.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.liwenzhi.aib.app.App;
import com.liwenzhi.aib.rxpermissions.OnRequestPermissionListener;
import com.liwenzhi.aib.ui.mvp.presenter.BasePresenter;
import com.liwenzhi.aib.util.CommonUtils;
import com.liwenzhi.aib.widget.swipebacklayout.app.SwipeBackActivity;
import com.liwenzhi.aib.rxpermissions.Permission;
import com.liwenzhi.aib.rxpermissions.RxPermissions;
import com.liwenzhi.aib.util.SharedPreferencesUtils;
import io.reactivex.functions.Consumer;


/**
 * activity 基类，默认实现以下功能
 * 1 初始化 presenter
 * 2 在相应的生命周期对 indicatorview 的绑定和解绑
 */

public abstract class BaseActivity<V, T extends BasePresenter<V>> extends SwipeBackActivity {

    public T mPresenter;
    protected SharedPreferencesUtils spUtils; //用来保存和获取数据的
    private InputMethodManager mInputMethodManager;
    private RxPermissions mRxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //添加内存泄漏框架
//        App.getInstance().getRefWatcher().watch(this);
        spUtils = new SharedPreferencesUtils(App.getInstance(), "setting");
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        // 初始化 presenter
        mPresenter = initPresenter();
        // 绑定 indicatorview
        if (mPresenter != null) {
            mPresenter.onCreate((V) this);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 解绑 indicatorview
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    /**
     * 初始化 presenter
     */
    protected abstract T initPresenter();

    /**
     * 回退 activity
     */
    public void back() {
        finish();
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode
                != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (mInputMethodManager == null) {
                mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            }
            if (mInputMethodManager.isActive() && getCurrentFocus() != null) {
                try {
                    mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * 显示网络不可用
     */
    public void showNetworkError() {
        CommonUtils.showToast(this, "网络不可用");
    }

    /**
     * 两次点击关闭退出页面
     */
    long currentTime = 0;

    public void finishForDoubleTimes() {

        if ((System.currentTimeMillis() - currentTime) < 3000) {
            finish();
        } else {
            // CommonUtils.showToast(this, "再次点击关闭程序");
            currentTime = System.currentTimeMillis();
        }
    }

    /**
     * 显示对话框
     */
    public Toast mToast;

    public void showToast(final Object msg) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(getBaseContext(), "" + msg, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText("" + msg);
                }
                mToast.show();
            }
        });

    }

    /**
     * 注册权限监听
     * 必须在 onCreate 处注册
     */
    public void registerPermissions() {
        mRxPermissions = new RxPermissions(this);
        mRxPermissions.setLogging(true);

    }

    /**
     * 请求相关条件
     *
     * @param permissions
     */
    public void requestPermissions(final OnRequestPermissionListener onRequestPermissionListener,
                                   final String deniedNotify,
                                   String... permissions) {
        if (mRxPermissions != null) {// 必须先注册 registerPermissions();
            mRxPermissions.requestEach(permissions)
                    .subscribe(new Consumer<Permission>() {
                                   @Override
                                   public void accept(Permission permission) throws Exception {
                                       if (permission.granted) {
                                           // 同意
                                           onRequestPermissionListener.onGrantedPermission();
                                       } else if (permission.shouldShowRequestPermissionRationale) {
                                           // 拒绝
                                           onRequestPermissionListener.onDeniedPermission(BaseActivity.this, deniedNotify);
                                       } else {
                                           // 不再询问
                                           onRequestPermissionListener.onDeniedPermissionWithAskNeverAgain(BaseActivity.this, deniedNotify);
                                       }
                                   }
                               }, new Consumer<Throwable>() {
                                   @Override
                                   public void accept(Throwable throwable) throws Exception {
                                       onRequestPermissionListener.onError();
                                   }
                               }
                    );
        }
    }


}