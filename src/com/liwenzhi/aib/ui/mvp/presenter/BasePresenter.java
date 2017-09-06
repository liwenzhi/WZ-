package com.liwenzhi.aib.ui.mvp.presenter;


import com.liwenzhi.aib.ui.mvp.model.BaseModel;
import com.liwenzhi.aib.util.CommonUtils;
import com.liwenzhi.aib.app.App;

/**
 * 界面控制器基类
 */

public class BasePresenter<V> {

    public V mView;

    private BaseModel mModule;

    public BasePresenter() {
        //
    }


    /**
     * 设置 Rx 模块
     */
    protected void setModule(BaseModel module) {
        this.mModule = module;
        onResume();
    }

    /**
     * 绑定View
     */
    public void onCreate(V view) {
        this.mView = view;
    }

    /**
     * 将 indicatorview 的引用清空
     * 当 presenter 有耗时任务未结束，而 activity 要销毁时，
     * 如果 presenter 持有 view的引用，而 indicatorview 持有 activity 的引用，将导致 activity 不能被回收
     * 所以在监测到 activity 的 onDestory 方法时，
     * 将 presenter 中的 indicatorview 引用清空，让 activity 得以回收
     * 将 Rx 任务清空
     */
    public void onDestroy() {
        this.mView = null;
        if (mModule != null) {
            mModule.onDestroy();
        }
    }

    /**
     * activity 生命周期
     */
    public void onResume() {
        if (mModule != null) {
            mModule.onResume();
        }
    }

    /**
     * activity 生命周期
     */
    public void onStop() {
        if (mModule != null) {
            mModule.onStop();
        }
    }

    /**
     * 检测网络是否可用
     */
    public boolean checkNetwork() {
        return CommonUtils.isNetWorkConnected(App.getInstance().getApplicationContext());
    }

}