package com.liwenzhi.aib.ui.activity;

import android.os.Bundle;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.adapter.CommonExpandableListViewAdapter;
import com.liwenzhi.aib.bean.DataBean;
import com.liwenzhi.aib.ui.fragment.NormalTitleBarFragment;
import com.liwenzhi.aib.ui.mvp.presenter.CommonActivityPresenter;
import com.liwenzhi.aib.ui.mvp.view.CommonActivityView;
import com.liwenzhi.aib.util.ParseXmlFromAssets;
import com.liwenzhi.aib.widget.ScrollExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 答题页面
 */
public class CommonActivity extends BaseActivity<CommonActivityView, CommonActivityPresenter> {

    private List<DataBean> questionInfo = new ArrayList<DataBean>();
    private ScrollExpandableListView el_common;
    private CommonExpandableListViewAdapter adapter;
  private  NormalTitleBarFragment fragment_titleBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        initView();
        initData();
        initEvent();

    }

    @Override
    protected CommonActivityPresenter initPresenter() {
        return new CommonActivityPresenter();
    }

    private void initView() {
        el_common = (ScrollExpandableListView) findViewById(R.id.el_common);
        fragment_titleBar = (NormalTitleBarFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_titleBar);
    }

    private void initData() {

        fragment_titleBar.getTitleBar().setTitle("Android面试常识");

        ParseXmlFromAssets parseXmlFromAssets = new ParseXmlFromAssets(this, "comment_data.xml");
        List<DataBean> dataBeans = parseXmlFromAssets.saxXmlToList();
        if (dataBeans.size() > 0) {
            questionInfo.addAll(dataBeans);
        }
        adapter = new CommonExpandableListViewAdapter(this, questionInfo);
        el_common.setAdapter(adapter);
    }

    private void initEvent() {

    }


}
