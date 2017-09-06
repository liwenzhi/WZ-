package com.liwenzhi.aib.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.widget.NormalTitleBar;

/**
 * 标题碎片
 */
public class NormalTitleBarFragment extends Fragment implements View.OnClickListener {

    NormalTitleBar titleBar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new NormalTitleBar(getActivity());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        initEvnet();

    }

    private void initView(View view) {
        titleBar = (NormalTitleBar) view;
    }

    private void initData() {


    }

    private void initEvnet() {
        titleBar.setLeftLayoutClickListener(this);
        titleBar.setRightLayoutClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left:
                getActivity().finish();
                break;
        }
    }

    public NormalTitleBar getTitleBar() {
        return titleBar;
    }
}
