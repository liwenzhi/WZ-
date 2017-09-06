package com.liwenzhi.aib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.bean.DataBean;
import com.liwenzhi.aib.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpandableListView的适配器
 */
public class CommonExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<DataBean> dataBeans = new ArrayList<DataBean>();

    Context context;

    // 通过构造方法传入上下文
    public CommonExpandableListViewAdapter(Context content, List<DataBean> dataBeans) {
        this.context = content;
//        this.listTreeNode.clear();
//        this.listTreeNode.addAll(listTreeNode);
        this.dataBeans = dataBeans;
    }

    // 显示的组的数量
    @Override
    public int getGroupCount() {
        // 返回的是页面显示多少个组的信息
        CommonUtils.e("getGroupCount()" + dataBeans.size());
        return dataBeans.size();
    }

    // 显示的是组里面的用户的个数，这里要根据组的变化而变化
    // 参数就是所在组的游标值
    @Override
    public int getChildrenCount(int groupPosition) {
        // 先获得集合中对应的对象，再通过对象的方法获取里面的组的人数
        return 1;
    }

    // 获取对应组的对象
    @Override
    public String getGroup(int groupPosition) {
        // 通过组的集合的序列值就可以拿到
        return dataBeans.get(groupPosition).getProblem();
    }

    // 获取对应组里面的对应用户（里面包含用户的信息）
    // 第一个参数组的游标值，
    // 第二个参数数用户在该组的游标值
    @Override
    public String getChild(int groupPosition, int childPosition) {
        // 先找到该组，然后通过该组的用户列表集合的对应序列，来获取用户的信息
        return dataBeans.get(groupPosition).getAnswer();
    }

    // 组的序列号
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 用户的序列号
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 是否是稳定的ID，这里一般选择的是true
    @Override
    public boolean hasStableIds() {
        return true;
    }

    // 用户条目是否可点击，这里一般也是选择返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // 下面两个方法是最麻烦和最重要的方法

    // 返回的是组的视图界面
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        // 返回的数具有View数据的缓冲数据
        if (convertView == null) {
            // 加载组的布局页面
            convertView = View.inflate(context, R.layout.item_group, null);
        }
        TextView tv_group = (TextView) convertView.findViewById(R.id.tv_group);
        tv_group.setText("" + getGroup(groupPosition));
        return convertView;
    }

    // 返回的是每一个用户的视图界面
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
//        // 返回的数具有View数据的缓冲数据
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_child, null);
        }
        TextView tv_temple_child = (TextView) convertView.findViewById(R.id.tv_detail_info);
        tv_temple_child.setText("" + getChild(groupPosition, childPosition));
        return convertView;
    }

}
