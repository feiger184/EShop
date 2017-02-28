package com.ghf.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23 0023.
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
