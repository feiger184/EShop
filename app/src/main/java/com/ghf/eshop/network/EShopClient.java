package com.ghf.eshop.network;

import com.ghf.eshop.network.core.RequestParam;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.core.UICallback;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 网络请求客户端
 */

public class EShopClient {

    public static final String BASE_URL = "http://106.14.32.204/eshop/emobile/?url=";

    private static EShopClient shopClient;
    private final OkHttpClient okHttpClient;
    private Gson gson;

    public static EShopClient getInstance() {
        if (shopClient == null) {
            shopClient = new EShopClient();
        }
        return shopClient;
    }

    private EShopClient() {
        gson = new Gson();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }
//
//    //构建商品分类的接口请求
//    public Call getCategory() {
//        Request request = new Request.Builder()
//                .get()
//                .url(BASE_URL + "/category")
//                .build();
//        return okHttpClient.newCall(request);
//    }
//
//
//    /*
//    * 首页 banner请求接口
//    * */
//    public Call getHomeBanner() {
//
//        Request request = new Request.Builder()
//                .get()
//                .url(BASE_URL + "/home/data")
//                .build();
//
//        return okHttpClient.newCall(request);
//    }
//
//    /*
//    * 首页 分类 和 推荐商品
//    * */
//    public Call getHomeCategory() {
//
//        Request request = new Request.Builder()
//                .get()
//                .url(BASE_URL + "/home/category")
//                .build();
//        return okHttpClient.newCall(request);
//    }
//
//    /*
//    * 搜索： 搜索商品
//    * */
//    public Call getSearch(SearchReq searchReq) {
//        String param = new Gson().toJson(searchReq);
//
//        RequestBody requestBody = new FormBody.Builder()
//                .add("json", param)
//                .build();
//
//        Request request = new Request.Builder()
//                .post(requestBody)
//                .url(BASE_URL + "/search")
//                .build();
//        return okHttpClient.newCall(request);
//    }

    /*
    * 同步
    * */
    public <T extends ResponseEntity> T execute(String path,
                                                RequestParam requestParam,
                                                Class<T> clazz) throws IOException {
        Response response = newApiCall(path, requestParam).execute();

        return getResponseEntity(response, clazz);

    }

    /*
    * 异步
    * */
    public Call enqueue(String path,
                        RequestParam requestParam,
                        Class<? extends ResponseEntity> clazz,
                        UICallback uiCallBack) {

        Call call = newApiCall(path, requestParam);
        uiCallBack.setResponseType(clazz);
        call.enqueue(uiCallBack);
        return call;
    }


    //根据参数构建请求
    private Call newApiCall(String path, RequestParam requestParam) {
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + path);

        //如果有请求体
        if (requestParam != null) {
            String json = gson.toJson(requestParam);
            RequestBody requestBody = new FormBody.Builder()
                    .add("json", json)
                    .build();
            builder.post(requestBody);
        }

        Request request = builder.build();
        return okHttpClient.newCall(request);
    }

    // 根据响应Response，将响应体转换成响应的实体类
    public <T extends ResponseEntity> T getResponseEntity(Response response, Class<T> clazz) throws IOException {

        //没有成功
        if (!response.isSuccessful()) {
            throw new IOException("Response code is" + response.code());
        }

        //成功
        return gson.fromJson(response.body().string(), clazz);
    }
}
