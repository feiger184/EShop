package com.ghf.eshop.feature.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseListAdapter;
import com.ghf.eshop.network.entity.home.CategoryHome;
import com.ghf.eshop.network.entity.home.Picture;
import com.ghf.eshop.network.entity.home.SimpleGoods;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * 推荐商品的适配器
 */

public class HomeGoodsAdapter extends BaseListAdapter<CategoryHome, HomeGoodsAdapter.ViewHolder> {


    @Override
    protected int getItemViewLayout() {
        return R.layout.item_home_goods;
    }

    @Override
    protected ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder {
        @BindView(R.id.text_category)
        TextView tvCategory;
        @BindViews({R.id.image_goods_01,
                R.id.image_goods_02,
                R.id.image_goods_03,
                R.id.image_goods_04})
        ImageView[] imageViews;
        private CategoryHome categoryHome;


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            categoryHome = getItem(position);
            tvCategory.setText(categoryHome.getName());
            final List<SimpleGoods> goodsList = categoryHome.getHotGoodsList();
            for (int i = 0; i < imageViews.length; i++) {
                Picture picture = goodsList.get(i).getImg();
                //图片加载待实现
                Picasso.with(getContext()).load(picture.getLarge()).into(imageViews[i]);

                final int index = i;
                imageViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), goodsList.get(index).getName(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }

        @OnClick(R.id.text_category)
        void onclick() {
            Toast.makeText(getContext(), categoryHome.getName(), Toast.LENGTH_SHORT).show();
        }
    }


}
