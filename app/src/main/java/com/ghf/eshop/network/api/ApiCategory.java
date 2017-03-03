package com.ghf.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ghf.eshop.network.core.ApiInterface;
import com.ghf.eshop.network.core.ApiPath;
import com.ghf.eshop.network.core.RequestParam;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.entity.category.CategoryRsp;


/**
 * 请求类型: HTTP
 * 接口地址: /category
 * 请求方式: GET
 * 数据类型: X-WWW-FORM-URLENCODED
 * 响应类型: JSON
 */

// 服务器接口：获取商品的分类信息
public class ApiCategory implements ApiInterface {
    @NonNull
    @Override
    public String getPath() {
        return ApiPath.CATEGORY;
    }

    @Nullable
    @Override
    public RequestParam getRequestParam() {
        return null;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return CategoryRsp.class;
    }
}
