package com.ghf.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ghf.eshop.network.core.ApiInterface;
import com.ghf.eshop.network.core.ApiPath;
import com.ghf.eshop.network.core.RequestParam;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.entity.search.Filter;
import com.ghf.eshop.network.entity.search.Pagination;
import com.ghf.eshop.network.entity.search.SearchReq;
import com.ghf.eshop.network.entity.search.SearchRsp;

/**
 * 服务器接口 搜索商品
 */

public class ApiSearch implements ApiInterface {

    private SearchReq searchReq;

    public ApiSearch(Filter filter, Pagination pagination) {
        searchReq = new SearchReq();
        this.searchReq.setFilter(filter);
        this.searchReq.setPagination(pagination);

    }

    @NonNull
    @Override
    public String getPath() {
        return ApiPath.SEARCH;
    }

    @Nullable
    @Override
    public RequestParam getRequestParam() {
        return searchReq;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return SearchRsp.class;
    }
}
