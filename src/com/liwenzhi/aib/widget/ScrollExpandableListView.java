package com.liwenzhi.aib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * 可滚动的ExpandableListView，这里是因为这个ExpandableListView被包裹在一个ScrollView中才需要设置纵向拉伸
 */

public class ScrollExpandableListView extends ExpandableListView {
    public ScrollExpandableListView(Context context) {
        super(context);
    }

    public ScrollExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}