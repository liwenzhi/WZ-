package com.liwenzhi.aib.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.ui.activity.CSDNWebActivity;
import com.liwenzhi.aib.ui.mvp.presenter.MyInfoFragmentPresenter;
import com.liwenzhi.aib.ui.mvp.view.MyInfoFragmentView;
import com.liwenzhi.aib.util.CommonUtils;

/**
 * 我的基本信息介绍
 */
public class MyInfoFragment extends BaseFragment<MyInfoFragmentView, MyInfoFragmentPresenter> implements View.OnClickListener {

    TextView tv_scanCSDN;
    TextView tv_scanWeb;

    @Override
    protected MyInfoFragmentPresenter initPresenter() {
        return new MyInfoFragmentPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myinfo, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_scanCSDN = (TextView) view.findViewById(R.id.tv_scanCSDN);
        tv_scanWeb = (TextView) view.findViewById(R.id.tv_scanWeb);
        initEvent();
    }

    private void initEvent() {
        tv_scanCSDN.setOnClickListener(this);
        tv_scanWeb.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_scanCSDN:
                startActivity(new Intent(getActivity(), CSDNWebActivity.class));
                break;
            case R.id.tv_scanWeb:
                showWebDialog();
                break;
        }

    }

    /**
     * 跳转到其他网页的条目选择
     */
    //数据源
    String[] array = new String[]{"百度首页","常见人事面试问题", "wenzhi博客java高编面试", "java经典IO练习题", "wenzhi博客Android面试题目", "其他博客常见面试题"
            , "java面试全集上"
            , "java面试全集下"
            , "张鸿洋的面试精华总结"
            , "Android高级面试题及答案"
            , "给Android程序员的一些面试建议"
            , "Android常见的网络编程面试题"
            , "哪些问题能试出一个 Android 应用开发者真正的水平？"
    };
    String[] webUrl = new String[]{
            "https://www.baidu.com/"
            , "http://blog.csdn.net/sbvfhp/article/details/44814127"
            , "http://blog.csdn.net/wenzhi20102321/article/details/52653112"
            , "http://blog.csdn.net/wenzhi20102321/article/details/52582705"
            , "http://blog.csdn.net/wenzhi20102321/article/details/54912796"
            , "http://blog.csdn.net/ericpengjun/article/details/50902665"
            , "http://www.importnew.com/22083.html"
            , "http://www.importnew.com/22087.html"
            , "http://blog.csdn.net/lmj623565791/article/details/24015867/"
            , "http://www.cnblogs.com/deman/p/5860976.html"
            , "http://blog.csdn.net/singwhatiwanna/article/details/49230997"
            , "http://blog.csdn.net/windows_nt/article/details/28670519"
            , "https://www.zhihu.com/question/19765032"
    };

    private void showWebDialog() {
        CommonUtils.e("showWebDialog：");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CommonUtils.e("builder：");
        // 设置标题
        builder.setTitle("请选择你要跳转的页面").
                // 设置可选择的内容，并添加点击事件
                        setItems(array, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // which代表的是选择的标签的序列号
                        choiceWebActivity(which);
                        dialog.dismiss();
                    }
                }).
                // 产生对话框，并显示出来
                        create().show();

    }

    private void choiceWebActivity(int which) {
        startActivity(new Intent(getActivity(), CSDNWebActivity.class).putExtra("webUrl", webUrl[which]).putExtra("title", array[which]));
    }

}
