package com.ghf.eshop.base.widgets;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ghf.eshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 轮播图适配器
 */

public abstract class BannerAdapter<T> extends PagerAdapter {

    private List<T> dataSet = new ArrayList<>();

    public void reset(List<T> data ) {
        dataSet.clear();
        if (data!=null) dataSet.addAll(data);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        ViewHolder viewHolder = (ViewHolder) object;
        return view == viewHolder.itemView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_banner, container, false);
        container.addView(view);
        ViewHolder viewHolder = new ViewHolder(view);
        bind(viewHolder, dataSet.get(position));
        return viewHolder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewHolder viewHolder = (ViewHolder) object;
        container.removeView(viewHolder.itemView);
    }

    public static class ViewHolder {

        @BindView(R.id.image_banner_item)
        ImageView imageView;
        private View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

    }

    protected abstract void bind(ViewHolder holder, T data);


}
