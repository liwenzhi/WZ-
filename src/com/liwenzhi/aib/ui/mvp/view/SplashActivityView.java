package com.liwenzhi.aib.ui.mvp.view;

import android.content.Context;
import com.liwenzhi.aib.ui.mvp.view.common.BaseView;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 5/15/17
 * Time: 3:29 PM
 * 欢迎界面视图层
 */
public interface SplashActivityView extends BaseView {
    Context getActivityContext();//获取上下文

    void gotoMainActivity();

    void setRemindString(String s);//提示信息

    void finishActivity();//关闭页面
}
