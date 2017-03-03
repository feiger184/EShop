package com.ghf.eshop.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghf.eshop.network.EShopClient;
import com.ghf.eshop.network.core.ApiInterface;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.core.UICallback;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 通用Fragment的基类
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(geContentViewLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @LayoutRes protected abstract int geContentViewLayout();

    protected abstract void initView();

    // 提供一个请求方法
    protected Call enqueue(final ApiInterface apiInterface) {
        UICallback uiCallback = new UICallback() {
            @Override
            public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {
                BaseFragment.this.onBusinessResponse(apiInterface.getPath(), isSucces, responseEntity);
            }
        };
        return EShopClient.getInstance().enqueue(apiInterface, uiCallback, getClass().getSimpleName());
    }
    protected abstract void onBusinessResponse(String path,boolean isSucces, ResponseEntity responseEntity);

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EShopClient.getInstance().CancelByTag(getClass().getSimpleName());

        unbinder.unbind();
        unbinder = null;
    }

}
