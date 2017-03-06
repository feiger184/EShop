package com.ghf.eshop.feature.goods.info;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseFragment;
import com.ghf.eshop.base.wrapper.ToastWrapper;
import com.ghf.eshop.feature.goods.GoodsActivity;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.entity.goodsinfo.GoodsInfo;
import com.ghf.eshop.network.entity.home.Picture;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class GoodsInfoFragment extends BaseFragment {

    @BindView(R.id.pager_goods_pictures)
    ViewPager picturePager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.button_favorite)
    ImageButton btnFavorite;
    @BindView(R.id.text_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.text_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.text_market_price)
    TextView tvMarketPrice;

    private static final String ARGUMENT_GOODS_INFO = "ARGUMENT_GOODS_INFO";
    private GoodsInfo goodsInfo;


    public static GoodsInfoFragment newInstance(GoodsInfo goodsInfo) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_GOODS_INFO, new Gson().toJson(goodsInfo));

        GoodsInfoFragment fragment = new GoodsInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int geContentViewLayout() {
        return R.layout.fragment_goods_info;
    }

    @Override
    protected void initView() {

        //取数据
        String string = getArguments().getString(ARGUMENT_GOODS_INFO);
        goodsInfo = new Gson().fromJson(string, GoodsInfo.class);

        GoodsPictureAdapter adapter = new GoodsPictureAdapter(goodsInfo.getPictures()) {
            @Override
            protected void onImageClicked(Picture picture) {
                // TODO: 2017/3/6 0006 点击商品图
                ToastWrapper.show(picture.getLarge());
            }
        };
        picturePager.setAdapter(adapter);
        indicator.setViewPager(picturePager);

        //商品信息
        // 设置商品名称, 价格等信息
        tvGoodsName.setText(goodsInfo.getName());
        tvGoodsPrice.setText(goodsInfo.getShopPrice());

        String marketPrice = goodsInfo.getMarketPrice();
        SpannableString spannableString = new SpannableString(marketPrice);
        spannableString.setSpan(new StrikethroughSpan(), 0, marketPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvMarketPrice.setText(spannableString);

        btnFavorite.setSelected(goodsInfo.isCollected());
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSucces, ResponseEntity responseEntity) {

    }


    @OnClick(R.id.text_goods_comments)
    void changeToComments() {
        // 切换到商品评价Fragment
        GoodsActivity activity = (GoodsActivity) getActivity();
        activity.selectPage(2);
    }

    @OnClick(R.id.text_goods_details)
    void changeToDetails() {
        // 切换到商品详情Fragment
        GoodsActivity activity = (GoodsActivity) getActivity();
        activity.selectPage(1);
    }

    @OnClick(R.id.button_favorite)
    void collectGoods() {
        // 收藏商品
        ToastWrapper.show(goodsInfo.getName());
    }
}
