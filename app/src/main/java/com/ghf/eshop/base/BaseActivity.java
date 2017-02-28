package com.ghf.eshop.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        unbinder = null;
    }

    protected abstract void initView();

    //让子类告诉我们布局
    @LayoutRes protected abstract int getContentViewLayout();
}
