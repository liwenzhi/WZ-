package com.liwenzhi.aib.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单封装了BaseAdapter的适配器类
 *
 * 这是一个简化BaseAdapter适配器的工具类
 * 这是使用的是定义一个泛型T，使用时传入什么数据，T就是什么数据
 * 实际设计中除了getVIew方法外，其他的方法基本是差不多的
 * 所以继承这个工具类后只要重写getView方法，就可以使用BaseAdapter了
 */
public abstract  class BaseOfAdapter<T> extends BaseAdapter {
    //定义集合数据
    List<T> list = new ArrayList<T>();
    //上下文
    Context context;

    //传入的是一个集合的数据的情况
    public BaseOfAdapter(Context context, List<T> list) {

        this.context = context;
        this.list = list;
    }

    //传入的是一个数组数据的情况
    //其实数组也是要转换为集合的数据，因为适配器只接受集合的数据
    public BaseOfAdapter(Context context, T[] list) {
        this.context = context;
        for (T t : list) {
            this.list.add(t);
        }
    }

    //返回数据的总数
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    //返回集合中某个游标值的对象
    @Override
    public T getItem(int position) {
        return list == null ? null : list.get(position);
    }

    //返回选中的条目的游标值
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 设置是否可以侧滑
     *
     * @param position
     * @return
     */
    public boolean getSwipEnableByPosition(int position) {
        return true;
    }

}

