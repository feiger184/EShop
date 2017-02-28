package com.ghf.eshop.network;

import com.ghf.eshop.network.entity.category.CategoryRsp;
import com.ghf.eshop.network.entity.home.HomeBannerRsp;
import com.ghf.eshop.network.entity.home.HomeCategoryRsp;
import com.google.gson.Gson;

import org.junit.Test;

import okhttp3.Call;
import okhttp3.Response;

import static org.junit.Assert.assertTrue;

/**
 * 客户端网络请求
 */
public class EShopClientTest {
    @Test
    public void getCategory() throws Exception {
        Call call = EShopClient.getInstance().getCategory();
        Response response = call.execute();
        String string = response.body().string();
        CategoryRsp categoryRsp = new Gson().fromJson(string, CategoryRsp.class);
        // 断言方法：为我们做一个判断
        assertTrue(categoryRsp.getStatus().isSucceed());
    }

    @Test
    public void getHomeBanner() throws Exception {
        Call call = EShopClient.getInstance().getHomeBanner();
        Response response = call.execute();
        String string = response.body().string();
        HomeBannerRsp homeBannerRsp = new Gson().fromJson(string, HomeBannerRsp.class);
        assertTrue(homeBannerRsp.getStatus().isSucceed());
    }

    @Test
    public void getHomeCategory() throws Exception {
        Call call = EShopClient.getInstance().getHomeCategory();
        Response response = call.execute();
        String string = response.body().string();
        HomeCategoryRsp homeCategoryRsp = new Gson().fromJson(string, HomeCategoryRsp.class);
        assertTrue(homeCategoryRsp.getStatus().isSucceed());
    }


}