package com.liwenzhi.aib.ui.activity;

import android.os.Bundle;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.adapter.CommonExpandableListViewAdapter;
import com.liwenzhi.aib.bean.DataBean;
import com.liwenzhi.aib.ui.fragment.NormalTitleBarFragment;
import com.liwenzhi.aib.ui.mvp.presenter.QuestionsActivityPresenter;
import com.liwenzhi.aib.ui.mvp.view.QuestionsActivityView;
import com.liwenzhi.aib.util.ParseXmlFromAssets;
import com.liwenzhi.aib.widget.ScrollExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 答题页面
 */
public class QuestionsActivity extends BaseActivity<QuestionsActivityView, QuestionsActivityPresenter> {

    private ScrollExpandableListView el_common;
    private CommonExpandableListViewAdapter adapter;
    private List<DataBean> questionInfo = new ArrayList<DataBean>();
    private NormalTitleBarFragment fragment_titleBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);
        initView();
        initData();
        initEvent();
    }

    @Override
    protected QuestionsActivityPresenter initPresenter() {
        return new QuestionsActivityPresenter();
    }

    private void initView() {
        el_common = (ScrollExpandableListView) findViewById(R.id.el_common);
        fragment_titleBar = (NormalTitleBarFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_titleBar);
    }

    private void initData() {
        String title = getIntent().getStringExtra("title");
        if (title.isEmpty()) {
            title = "面试题目";
        }
        fragment_titleBar.getTitleBar().setTitle(title);

        String xmlFileName = "android_data.xml";
        if (title.equals("Java面试题目")) {
            xmlFileName = "java_data.xml";
        }
        ParseXmlFromAssets parseXmlFromAssets = new ParseXmlFromAssets(this, xmlFileName);
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
