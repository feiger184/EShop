package com.ghf.eshop.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.ghf.eshop.network.EShopClient;
import com.ghf.eshop.network.core.ApiInterface;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.core.UICallback;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 通用Activity基类
 */

public abstract class BaseActivity extends TransitionActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewLayout());
        unbinder = ButterKnife.bind(this);
        initView();
    }


    protected abstract void initView();

    //让子类告诉我们布局
    @LayoutRes
    protected abstract int getContentViewLayout();

    //基类中提供一个请求的方法
    protected Call enqueue(final ApiInterface apiInterface) {
        UICallback uiCallback=new UICallback() {
            @Override
            public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {
                BaseActivity.this.onBusinessResponse(apiInterface.getPath(), isSucces, responseEntity);
            }
        };
        return EShopClient.getInstance().enqueue(apiInterface, uiCallback, getClass().getSimpleName());
    }
    protected abstract void onBusinessResponse(String path,boolean isSucces, ResponseEntity responseEntity);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EShopClient.getInstance().CancelByTag(getClass().getSimpleName());

        unbinder.unbind();
        unbinder = null;
    }
}
