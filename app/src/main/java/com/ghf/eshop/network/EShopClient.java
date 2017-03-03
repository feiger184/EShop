package com.ghf.eshop.network;

import com.ghf.eshop.network.core.ApiInterface;
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


    /*
    * 同步
    * */
    public <T extends ResponseEntity> T execute(ApiInterface apiInterface) throws IOException {
        Response response = newApiCall(apiInterface, null).execute();
        Class<T> clazz = (Class<T>) apiInterface.getResponseEntity();
        return getResponseEntity(response, clazz);

    }

    /*
    * 异步
    * */
    public Call enqueue(ApiInterface apiInterface,
                        UICallback uiCallBack, String tag) {

        Call call = newApiCall(apiInterface, tag);
        uiCallBack.setResponseType(apiInterface.getResponseEntity());
        call.enqueue(uiCallBack);
        return call;
    }


    //根据参数构建请求
    private Call newApiCall(ApiInterface apiInterface, String tag) {
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + apiInterface.getPath());

        //如果有请求体
        if (apiInterface.getRequestParam() != null) {
            String json = gson.toJson(apiInterface.getRequestParam());
            RequestBody requestBody = new FormBody.Builder()
                    .add("json", json)
                    .build();
            builder.post(requestBody);
        }

        builder.tag(tag); //设置Tag

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

    //给请求设置Tag 取消请求时 通过判断Tag 来取消请求
    public void CancelByTag(String tag) {

       /* 1.调度器中等待执行的*/
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
        /*2.调度器中正在执行的*/
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag)) {
                call.cancel();
            }
        }
    }

}
