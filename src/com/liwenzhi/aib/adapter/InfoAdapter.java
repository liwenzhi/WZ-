package com.liwenzhi.aib.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.liwenzhi.aib.R;
import com.liwenzhi.aib.bean.MainInfoBean;

import java.util.List;

/**
 * 用户数据的列表的适配器
 */
public class InfoAdapter extends BaseOfAdapter<MainInfoBean> {
    public InfoAdapter(Context context, List<MainInfoBean> list) {
        super(context, list);
    }

    public InfoAdapter(Context context, MainInfoBean[] list) {
        super(context, list);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_info, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.iv_user_info.setImageResource(getItem(position).getPictureId());
        viewHolder.tv_user_info.setText("" + getItem(position).getInfo());
//        viewHolder.rl_ripple.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItemClickListener != null) {
//                    onItemClickListener.onClick(position);
//                }
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv_user_info;
        TextView tv_user_info;
//        RippleBackground rl_ripple;

        ViewHolder(View convertView) {
            iv_user_info = (ImageView) convertView.findViewById(R.id.iv_user_info);
            tv_user_info = (TextView) convertView.findViewById(R.id.tv_user_info);
//            rl_ripple = (RippleBackground) convertView.findViewById(R.id.rl_ripple);
        }
    }

    public interface OnItemClickListener {
        void onClick(int index);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
