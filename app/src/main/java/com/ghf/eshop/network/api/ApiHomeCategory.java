package com.ghf.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ghf.eshop.network.core.ApiInterface;
import com.ghf.eshop.network.core.ApiPath;
import com.ghf.eshop.network.core.RequestParam;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.entity.home.HomeCategoryRsp;

/**
 * 首页 推荐商品
 */

public class ApiHomeCategory implements ApiInterface {
    @NonNull
    @Override
    public String getPath() {
        return ApiPath.HOME_CATEGORY;
    }

    @Nullable
    @Override
    public RequestParam getRequestParam() {
        return null;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return HomeCategoryRsp.class;
    }
}
