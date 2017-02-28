package com.ghf.eshop.network;

import com.ghf.eshop.network.entity.CategoryRsp;
import com.google.gson.Gson;

import org.junit.Test;

import okhttp3.Call;
import okhttp3.Response;

import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2017/2/23 0023.
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

}