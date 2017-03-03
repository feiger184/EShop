package com.ghf.eshop.feature.serach;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseListAdapter;
import com.ghf.eshop.network.entity.home.Picture;
import com.ghf.eshop.network.entity.home.SimpleGoods;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * 搜索界面适配器
 */

public class SearchGoodsAdapter extends BaseListAdapter<SimpleGoods, SearchGoodsAdapter.ViewHolder> {



    @Override
    protected int getItemViewLayout() {
        return R.layout.item_search_goods;
    }

    @Override
    protected ViewHolder getItemViewHolder(View view) {
        return new ViewHolder(view);
    }


    class ViewHolder extends BaseListAdapter.ViewHolder {
        @BindView(R.id.image_goods)
        ImageView imageGoods;
        @BindView(R.id.text_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.text_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.text_market_price)
        TextView tvMarketPrice;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bind(int position) {
            SimpleGoods simpleGoods = getItem(position);
            tvGoodsName.setText(simpleGoods.getName());
            tvGoodsPrice.setText(simpleGoods.getShopPrice());

            //设置商场价格  在原有字符串上画横线
            String marketPrice = simpleGoods.getMarketPrice();
            SpannableString spannableString = new SpannableString(marketPrice);
            spannableString.setSpan(new StrikethroughSpan(), 0, marketPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvMarketPrice.setText(spannableString);

            //商品图片加载
            Picture picture = simpleGoods.getImg();
            Picasso.with(getContext()).load(picture.getLarge()).into(imageGoods);



        }
    }

}
