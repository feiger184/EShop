package com.ghf.eshop.network.entity.goodsinfo;


import com.google.gson.annotations.SerializedName;
//商品规格
public class GoodsSpec {

    /*{
             "attr_type"         : "<规格类型>",      // 规格类型
            "name"                : "颜色",            // 规格名称
            "value"               : [{商品价值对象}]   // 商品价值
    }*/
    public static final int ATTR_TYPE_UNIQUE = 0;
    public static final int ATTR_TYPE_SINGLE = 1;
    public static final int ATTR_TYPE_MULTIPLE = 2;

    @SerializedName("attr_type")
    private int mAttrType;

    @SerializedName("name")
    private String mAttrName;


    //商品价值
    public static class GoodsValue {
        /*{
                "id"            : 212,          // 价值id
                "label"          : "白色",       // 价值标签
                "price"          : 0,            // 价格
                "format_price"   : "￥0.00元"    // 格式化价格
        }*/
        @SerializedName("id")
        private int mId;

        @SerializedName("label")
        private String mLabel;

        @SerializedName("price")
        private float mPrice;

        @SerializedName("format_price")
        private String mFormatPrice;
    }
}
