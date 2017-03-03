package com.ghf.eshop.network;

import com.ghf.eshop.network.entity.category.CategoryRsp;
import com.ghf.eshop.network.entity.home.HomeBannerRsp;
import com.ghf.eshop.network.entity.search.SearchReq;
import com.ghf.eshop.network.entity.search.SearchRsp;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 客户端网络请求
 */
public class EShopClientTest {
    @Test
    public void getCategory() throws Exception {

        CategoryRsp categoryRsp = EShopClient.getInstance().execute("/category", null, CategoryRsp.class);
        // 断言方法：为我们做一个判断
        assertTrue(categoryRsp.getStatus().isSucceed());
    }

    @Test
    public void getHomeBanner() throws Exception{
        HomeBannerRsp bannerRsp = EShopClient.getInstance().execute("/home/data", null, HomeBannerRsp.class);
        assertTrue(bannerRsp.getStatus().isSucceed());
    }

    @Test
    public void getHomeCategory() throws Exception{
        CategoryRsp categoryRsp = EShopClient.getInstance().execute("/home/category", null, CategoryRsp.class);
        assertTrue(categoryRsp.getStatus().isSucceed());
    }

    @Test
    public void getSearch() throws Exception{
        SearchReq searchReq = new SearchReq();
        SearchRsp searchRsp = EShopClient.getInstance().execute("/search", searchReq, SearchRsp.class);
        assertTrue(searchRsp.getStatus().isSucceed());
    }
}