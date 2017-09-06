package com.liwenzhi.aib.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 切换碎片：病人的一天血压列表数据和折线图的适配器
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    //数据源的集合
    List<Fragment> list = new ArrayList<Fragment>();

    public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list.clear();
        this.list.addAll(list);

    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
