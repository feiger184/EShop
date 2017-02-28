package com.ghf.eshop.network;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 网络请求客户端
 */

public class EShopClient {

    public static final String BASE_URL = "http://106.14.32.204/eshop/emobile/?url=";

    private static EShopClient shopClient;
    private final OkHttpClient okHttpClient;

    public static EShopClient getInstance() {
        if (shopClient == null) {
            shopClient = new EShopClient();
        }
        return shopClient;
    }

    private EShopClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    //构建商品分类的接口请求
    public Call getCategory() {
        Request request= new Request.Builder()
                .get()
                .url(BASE_URL+"/category")
                .build();
        return okHttpClient.newCall(request);
    }

}
