package com.ghf.eshop.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 通用的ListView的适配器的基类
 * 数据不一样
 * item布局不一样
 * 用泛型来表示
 * T 表示数据
 * V 表示ViewHolder
 */

public abstract class BaseListAdapter<T, V extends BaseListAdapter.ViewHolder> extends BaseAdapter {

    private List<T> dataSet = new ArrayList<>();


    //数据重置
    public void reset(List<T> data) {
        dataSet.clear();
        if (data != null) dataSet.addAll(data);
        notifyDataSetChanged();
    }

    //数据添加
    public void addAll(List<T> data) {
        if (data!=null) dataSet.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public T getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView==null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getItemViewLayout(),parent,false);

            convertView.setTag(getItemViewHolder(convertView));

        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bind(position);
        return convertView;
    }

    //拿到Item布局
    @LayoutRes protected abstract int getItemViewLayout();

    protected abstract V getItemViewHolder(View view);



    public abstract class ViewHolder {
        View mItemView;
        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            mItemView = itemView;
        }

        //绑定视图和数据
        protected abstract void bind(int position);

        protected final Context getContext() {
            return mItemView.getContext();
        }
    }


}
