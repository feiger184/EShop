package com.ghf.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ghf.eshop.network.core.ApiInterface;
import com.ghf.eshop.network.core.ApiPath;
import com.ghf.eshop.network.core.RequestParam;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.entity.goodsinfo.GoodsInfoReq;
import com.ghf.eshop.network.entity.goodsinfo.GoodsInfoRsp;

/**
 * 服务器接口：商品详情的请求
 */

public class ApiGoodsInfo implements ApiInterface {

    private GoodsInfoReq goodsInfoReq;

    public ApiGoodsInfo(int goodsId) {
        goodsInfoReq = new GoodsInfoReq();
        goodsInfoReq.setGoodsId(goodsId);
    }

    @NonNull
    @Override
    public String getPath() {
        return ApiPath.GOODS_INFO;
    }

    @Nullable
    @Override
    public RequestParam getRequestParam() {
        return goodsInfoReq;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return GoodsInfoRsp.class;
    }

}
