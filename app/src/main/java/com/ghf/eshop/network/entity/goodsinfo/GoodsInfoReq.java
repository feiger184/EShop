package com.ghf.eshop.network.entity.goodsinfo;

import com.ghf.eshop.network.core.RequestParam;
import com.google.gson.annotations.SerializedName;

/**
 *  商品详情请求体
 */

public class GoodsInfoReq extends RequestParam{

    @SerializedName("goods_id")
    private int mGoodsId;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        mGoodsId = goodsId;
    }
}
