package com.liwenzhi.aib.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.adapter.InfoAdapter;
import com.liwenzhi.aib.bean.MainInfoBean;
import com.liwenzhi.aib.ui.activity.CommonActivity;
import com.liwenzhi.aib.ui.activity.QuestionsActivity;
import com.liwenzhi.aib.ui.mvp.presenter.KnowledgeFragmentPresenter;
import com.liwenzhi.aib.ui.mvp.view.KnowledgeFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识点碎片页面
 */
public class KnowledgeFragment extends BaseFragment<KnowledgeFragmentView, KnowledgeFragmentPresenter> implements AdapterView.OnItemClickListener {

    ListView lv_main;
    private InfoAdapter infoAdapter;
    private List<MainInfoBean> listInfo = new ArrayList<MainInfoBean>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_knowledge, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        initEvent();
    }

    @Override
    protected KnowledgeFragmentPresenter initPresenter() {
        return new KnowledgeFragmentPresenter();
    }

    private void initView(View view) {
        lv_main = (ListView) view.findViewById(R.id.lv_main);

    }

    private void initData() {
        //用户基本信息的列表数据
        String[] userInfo = {"面试常识", "java知识", "android知识"};
        int[] pictureId = {R.drawable.interview, R.drawable.java, R.drawable.android};

        for (int i = 0; i < userInfo.length; i++) {
            listInfo.add(new MainInfoBean(userInfo[i], pictureId[i]));
        }

        infoAdapter = new InfoAdapter(getActivity(), listInfo);
        lv_main.setAdapter(infoAdapter);

    }

    private void initEvent() {
        lv_main.setOnItemClickListener(this);

    }

    /**
     * ListView的条目的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), CommonActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), QuestionsActivity.class).putExtra("title", "Java面试题目"));
                break;
            case 2:
                startActivity(new Intent(getActivity(), QuestionsActivity.class).putExtra("title", "Android面试题目"));
                break;
        }

    }



}
