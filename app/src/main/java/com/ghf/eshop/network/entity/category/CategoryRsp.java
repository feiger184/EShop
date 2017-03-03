package com.ghf.eshop.network.entity.category;

import com.ghf.eshop.network.core.ResponseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 商品分类响应体
 */
public class CategoryRsp extends ResponseEntity {

    @SerializedName("data")
    private List<CategoryPrimary> mData;

    public List<CategoryPrimary> getData() {
        return mData;
    }
}
