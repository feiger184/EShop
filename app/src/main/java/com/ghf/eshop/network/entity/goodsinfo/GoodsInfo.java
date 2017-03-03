package com.ghf.eshop.network.entity.goodsinfo;

import com.ghf.eshop.network.entity.home.Picture;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情实体.
 */
public class GoodsInfo {

   /* {
        "id"                    :  32,              // 商品id
            "collected"             : 0,                // 是否收藏(1 - 是, 0 - 否)
            "cat_id"                : 0,                // 所属分类id
            "goods_sn"              : "ECS000000",      // 商品序列号
            "goods_name"            : "KD876"           // 商品名称
        "market_price"          : "￥1786元",       // 商场价格
            "shop_price"            : "￥1488.00元",    // 商店价格
            "integral"              : 1300,             // 积分
            "click_count"           : 64,               // 点击次数
            "brand_id"              : null,             // 品牌id
            "goods_number"          : "1",              // 商品数量
            "goods_weight"          : "110克",          // 商品重量
            "promote_price"         : "1455.00",        // 促销价格
            "formated_promote_price": "￥1455.00元",    // 格式化促销价格
            "promote_start_date"    : "2016/11/13 16:00:00 +0800", // 促销开始时间
            "promote_end_date"      : "2016/11/22 16:00:00 +0800", // 促销结束时间
            "is_shipping"           : 0,
            "img"                   : {图片对象},
        "rank_prices"           : [{会员价格对象}],
        "pictures"              : [{图片对象}],
        "properties"            : [{商品属性对象}],
        "specification"         : [{商品规格对象}]
    }*/
    @SerializedName("id")
    private int mId;

    @SerializedName("goods_name")
    private String mName;

    @SerializedName("pictures")
    private List<Picture> mPictures = new ArrayList<>();

    @SerializedName("shop_price")
    private String mShopPrice;

    @SerializedName("market_price")
    private String mMarketPrice;

    @SerializedName("specification")
    private List<GoodsSpec> mSpecs;

    @SerializedName("img")
    private Picture mImg;

    @SerializedName("goods_number")
    private int mNumber;

    @SerializedName("collected")
    private int mCollected;

    public String getMarketPrice() {
        return mMarketPrice;
    }

    public List<Picture> getPictures() {
        return mPictures;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getShopPrice() {
        return mShopPrice;
    }

    public List<GoodsSpec> getSpecs() {
        return mSpecs;
    }

    public Picture getImg() {
        return mImg;
    }

    public int getNumber() {
        return mNumber;
    }

    public boolean isCollected() {
        return mCollected == 1;
    }
}
