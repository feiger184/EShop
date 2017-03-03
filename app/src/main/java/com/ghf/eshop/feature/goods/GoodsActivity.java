package com.ghf.eshop.feature.goods;

import android.content.Context;
import android.content.Intent;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseActivity;
import com.ghf.eshop.base.wrapper.ToolbarWrapper;
import com.ghf.eshop.network.core.ApiPath;
import com.ghf.eshop.network.core.ResponseEntity;

public class GoodsActivity extends BaseActivity {

    private static final String EXTRA_GOODS_ID = "EXTRA_GOOD_ID";

    //对外提供一个方法 跳转到本页面
    public static Intent getStartIntent(Context context, int goodsId) {

        Intent intent = new Intent(context, GoodsActivity.class);
        intent.putExtra(EXTRA_GOODS_ID, goodsId);
        return intent;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_goods;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSucces, ResponseEntity responseEntity) {
        if (!ApiPath.GOODS_INFO.equals(path)) {
            throw new UnsupportedOperationException(path);
        }



    }

    @Override
    protected void initView() {

        new ToolbarWrapper(this);
    }

}
