package com.liwenzhi.aib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.bean.DataBean;

import java.util.List;

/**
 * 问题的列表的适配器
 */
public class QuestionAdapter extends BaseOfAdapter<DataBean> {
    public QuestionAdapter(Context context, List<DataBean> list) {
        super(context, list);
    }

    public QuestionAdapter(Context context, DataBean[] list) {
        super(context, list);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_question, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_question.setText("" + getItem(position).getProblem());
        viewHolder.tv_answer.setText("" + getItem(position).getAnswer());
        return convertView;
    }

    class ViewHolder {
//        ImageView iv_info;
        TextView tv_question;
        TextView tv_answer;

        ViewHolder(View convertView) {
//            iv_info = (ImageView) convertView.findViewById(R.id.iv_info);
            tv_question = (TextView) convertView.findViewById(R.id.tv_question);
            tv_answer = (TextView) convertView.findViewById(R.id.tv_answer);
        }
    }

}
