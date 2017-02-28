package com.ghf.eshop.network.entity.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 商品分类响应体
 */
public class CategoryRsp {

    @SerializedName("data")
    private List<CategoryPrimary> mData;

    @SerializedName("status")
    private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }

    public List<CategoryPrimary> getData() {
        return mData;
    }
}
