package com.ghf.eshop.base.wrapper;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.ghf.eshop.R;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 下拉刷新的封装类
 */

public abstract class PtrWrapper {
    private PtrFrameLayout refreshLayout;
    /*
    *  1.找到刷新控件
    *  2.设置刷新头布局的样式 尾部加载的布局样式
    *  3. 刷新处理 获取数据
    *  4. 自动刷新 延时刷新
    *  5. 停止刷新
    *  6. 判断是不是在刷新
    * */

    public PtrWrapper(Activity activity,boolean isNeedLoad) {
        refreshLayout = ButterKnife.findById(activity, R.id.standard_refresh_layout);
        initPtr(isNeedLoad);

    }

    public PtrWrapper(Fragment fragment,boolean isNeedLoad) {
        refreshLayout = ButterKnife.findById(fragment.getView(), R.id.standard_refresh_layout);
        initPtr(isNeedLoad);

    }

    public PtrWrapper(PtrFrameLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    //初始化刷新 加载
    private void initPtr(boolean isNeedLoad) {
        if (refreshLayout != null) {
            refreshLayout.disableWhenHorizontalMove(true);
        }
        //设置头布局
        initPtrHeader();

        //设置尾部布局
        if (isNeedLoad) {
            initPtrFooter();
        }

        refreshLayout.setPtrHandler(ptrHandler);
    }

    private PtrDefaultHandler2 ptrHandler = new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
            //加载
            onLoadMore();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            //刷新
            onRefresh();
        }
    };
    //设置尾部布局
    private void initPtrFooter() {
        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(refreshLayout.getContext());
        refreshLayout.setFooterView(footer);
        refreshLayout.addPtrUIHandler(footer);
    }

    //设置头布局
    private void initPtrHeader() {
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(refreshLayout.getContext());
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
    }

    //刷新数据
    protected abstract void onRefresh();
    //加载更多
    protected abstract void onLoadMore();

    //自动刷新
    public void autoRefresh() {
        refreshLayout.autoRefresh();
    }

    //延时刷新
    public void postRefreshDelayed(long delay) {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();
            }
        }, delay);
    }

    //停止刷新
    public void stopRefresh() {
        if (isRefreshing()) {
            refreshLayout.refreshComplete();
        }
    }

    //是不是在刷新
    public boolean isRefreshing() {
        return refreshLayout.isRefreshing();
    }


}
