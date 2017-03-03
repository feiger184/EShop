package com.ghf.eshop.network.core;

import android.os.Handler;
import android.os.Looper;

import com.ghf.eshop.R;
import com.ghf.eshop.base.utils.LogUtils;
import com.ghf.eshop.base.wrapper.ToastWrapper;
import com.ghf.eshop.network.EShopClient;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 为了统一处理OkHttp的Callback不能更新UI的问题
 */

public abstract class UICallback implements Callback {

    private Class<? extends ResponseEntity> mResponseType;

    public UICallback() {

    }


    // 创建一个运行在主线程的Handler
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // 添加到消息队列里，和handler运行在同一个线程
                onFailureInUI(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    onResponseInUI(call, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //对成功和失败的处理
    public void onFailureInUI(Call call, IOException e) {
        ToastWrapper.show(R.string.error_network);
        LogUtils.error("onFailureInUI", e);
        onBusinessResponse(false, null);
    }

    public void onResponseInUI(Call call, Response response) throws IOException {

        if (response.isSuccessful()) {
            ResponseEntity responseEntity = EShopClient.getInstance().getResponseEntity(response, mResponseType);

            //判断类为空
            if (responseEntity == null || responseEntity.getStatus() == null) {
                throw new RuntimeException("Fatal Api Error");
            }

            if (responseEntity.getStatus().isSucceed()) {
                onBusinessResponse(true, responseEntity);
            } else {
                ToastWrapper.show(responseEntity.getStatus().getErrorDesc());
                onBusinessResponse(false, responseEntity);
            }
        }

    }

    //告诉我们要转换的实际的实体类型
    public void setResponseType(Class<? extends ResponseEntity> responseType) {
        mResponseType = responseType;
    }
//    // 对外提供两个必须实现的方法：将onFailure、onResponse的数据传递出去。
//    public abstract void onFailureInUI(Call call, IOException e);
//
//    public abstract void onResponseInUI(Call call, Response response) throws IOException;

    // 给使用者实现的一个方法：处理拿到数据
    public abstract void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity);

}
