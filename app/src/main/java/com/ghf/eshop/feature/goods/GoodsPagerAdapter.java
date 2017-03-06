package com.ghf.eshop.feature.goods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ghf.eshop.feature.TestFragment;
import com.ghf.eshop.feature.goods.info.GoodsInfoFragment;
import com.ghf.eshop.network.entity.goodsinfo.GoodsInfo;

/**
 * 商品详情页面 适配器
 */

public class GoodsPagerAdapter extends FragmentPagerAdapter {

    private GoodsInfo goodsInfo;

    public GoodsPagerAdapter(FragmentManager fm, GoodsInfo goodsInfo) {
        super(fm);
        this.goodsInfo = goodsInfo;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return GoodsInfoFragment.newInstance(goodsInfo);
            case 1:
                return TestFragment.newInstance("goods details");
            case 2:
                return TestFragment.newInstance("goods comments");
            default:
                throw new UnsupportedOperationException("Position" + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
