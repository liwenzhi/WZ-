package com.liwenzhi.aib.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.liwenzhi.aib.rxpermissions.OnRequestPermissionListener;
import com.liwenzhi.aib.rxpermissions.Permission;
import com.liwenzhi.aib.ui.mvp.presenter.BasePresenter;
import com.liwenzhi.aib.util.CommonUtils;
import com.liwenzhi.aib.util.SharedPreferencesUtils;
import com.liwenzhi.aib.rxpermissions.RxPermissions;
import io.reactivex.functions.Consumer;

/**
 * fragment 基类，默认实现以下功能
 * 1 初始化 presenter
 * 2 在相应的生命周期对 indicatorview 的绑定和解绑
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends LazyBaseFragment {

    public T mPresenter;
    protected SharedPreferencesUtils spUtils; //用来保存和获取数据的
    private InputMethodManager mInputMethodManager;
    private RxPermissions mRxPermissions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spUtils = new SharedPreferencesUtils(getActivity(), "setting");
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        // 初始化 presenter
        mPresenter = initPresenter();
        // 绑定 indicatorview
        if (mPresenter != null) {
            mPresenter.onCreate((V) this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 解绑 indicatorview
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onStop() {
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
     * 隐藏键盘
     */
    public void hideKeyboard() {
        FragmentActivity activity = getActivity();
        if (activity.getWindow().getAttributes().softInputMode
                != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (mInputMethodManager == null) {
                mInputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
            }
            if (mInputMethodManager.isActive() && activity.getCurrentFocus() != null) {
                try {
                    mInputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
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
        CommonUtils.showToast(getActivity(), "网络连接异常");
    }

    /**
     * 显示对话框
     */
    public Toast mToast;

    public void showToast(final Object msg) {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT);
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
        mRxPermissions = new RxPermissions(getActivity());
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
                                           onRequestPermissionListener.onDeniedPermission(getActivity(), deniedNotify);
                                       } else {
                                           // 不再询问
                                           onRequestPermissionListener.onDeniedPermissionWithAskNeverAgain(getActivity(), deniedNotify);
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

    @Override
    public void onDestroyView() {
        //添加内存泄漏框架
//        App.getInstance().getRefWatcher().watch(this);
        super.onDestroyView();
    }

}