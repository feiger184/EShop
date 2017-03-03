package com.ghf.eshop.network.entity.search;


import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.entity.home.SimpleGoods;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// 搜索商品的响应体
public class SearchRsp extends ResponseEntity {


    @SerializedName("data")
    private List<SimpleGoods> mData;

    @SerializedName("paginated")
    private Paginated mPaginated;

    public List<SimpleGoods> getData() {
        return mData;
    }

    public Paginated getPaginated() {
        return mPaginated;
    }

}
