package com.ghf.eshop.network;

import com.ghf.eshop.network.api.ApiCategory;
import com.ghf.eshop.network.api.ApiHomeBanner;
import com.ghf.eshop.network.api.ApiHomeCategory;
import com.ghf.eshop.network.api.ApiSearch;
import com.ghf.eshop.network.entity.category.CategoryRsp;
import com.ghf.eshop.network.entity.home.HomeBannerRsp;
import com.ghf.eshop.network.entity.home.HomeCategoryRsp;
import com.ghf.eshop.network.entity.search.SearchRsp;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 客户端网络请求
 */
public class EShopClientTest {
    @Test
    public void getCategory() throws Exception {

        CategoryRsp categoryRsp = EShopClient.getInstance().execute(new ApiCategory());
        // 断言方法：为我们做一个判断
        assertTrue(categoryRsp.getStatus().isSucceed());
    }

    @Test
    public void getHomeBanner() throws Exception {
        HomeBannerRsp bannerRsp = EShopClient.getInstance().execute(new ApiHomeBanner());
        assertTrue(bannerRsp.getStatus().isSucceed());
    }

    @Test
    public void getHomeCategory() throws Exception {
        HomeCategoryRsp categoryRsp = EShopClient.getInstance().execute(new ApiHomeCategory());
        assertTrue(categoryRsp.getStatus().isSucceed());
    }

    @Test
    public void getSearch() throws Exception {
        ApiSearch apiSearch = new ApiSearch(null, null);
        SearchRsp searchRsp = EShopClient.getInstance().execute(apiSearch);
        assertTrue(searchRsp.getStatus().isSucceed());
    }
}