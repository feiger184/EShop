package com.ghf.eshop.feature.category;

import android.view.View;
import android.widget.TextView;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseListAdapter;
import com.ghf.eshop.network.entity.category.CategoryPrimary;

import butterknife.BindView;

/**
 * 一级分类适配器
 */

public class CategoryAdapter extends BaseListAdapter<CategoryPrimary,CategoryAdapter.ViewHolder> {


    @Override
    protected int getItemViewLayout() {
        return R.layout.item_primary_category;
    }

    @Override
    protected ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder {
        @BindView(R.id.text_category)
        TextView textCategory;
        public ViewHolder(View view) {
            super(view);
        }
        @Override
        protected void bind(int position) {
            textCategory.setText(getItem(position).getName());
        }
    }
}
