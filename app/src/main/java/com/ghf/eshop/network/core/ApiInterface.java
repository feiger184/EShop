package com.ghf.eshop.network.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 将请求path 请求体  响应体 做一个整理管理
 */

public interface ApiInterface {

    @NonNull
    String getPath();

    @Nullable
    RequestParam getRequestParam();

    @NonNull
    Class<? extends ResponseEntity> getResponseEntity();

}
