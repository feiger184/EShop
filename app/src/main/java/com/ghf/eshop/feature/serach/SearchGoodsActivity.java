package com.ghf.eshop.feature.serach;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseActivity;
import com.ghf.eshop.base.widgets.SimpleSearchView;
import com.ghf.eshop.base.wrapper.PtrWrapper;
import com.ghf.eshop.base.wrapper.ToastWrapper;
import com.ghf.eshop.base.wrapper.ToolbarWrapper;
import com.ghf.eshop.feature.goods.GoodsActivity;
import com.ghf.eshop.network.api.ApiSearch;
import com.ghf.eshop.network.core.ApiPath;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.entity.home.SimpleGoods;
import com.ghf.eshop.network.entity.search.Filter;
import com.ghf.eshop.network.entity.search.Paginated;
import com.ghf.eshop.network.entity.search.Pagination;
import com.ghf.eshop.network.entity.search.SearchRsp;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.Call;

/**
 * 搜索界面
 */

public class SearchGoodsActivity extends BaseActivity {

    @BindView(R.id.search_view)
    SimpleSearchView searchView;
    @BindView(R.id.list_goods)
    ListView goodsListView;
    @BindViews({R.id.text_is_hot, R.id.text_most_expensive, R.id.text_cheapest})
    List<TextView> tvOrderList;

    private Pagination pagination = new Pagination();

    private static final String EXTRA_SEARCH_FILTER = "EXTRA_SEARCH_FILTER";
    private Filter filter;
    private SearchGoodsAdapter searchgoodsAdapter;
    private Call searchCall;
    private PtrWrapper ptrWrapper;
    private Paginated paginated;//  分页结果
    private long lastRefreshTime;


    public static Intent getStartIntent(Context context, Filter filter) {
        Intent intent = new Intent(context, SearchGoodsActivity.class);
        intent.putExtra(EXTRA_SEARCH_FILTER, new Gson().toJson(filter));
        return intent;
    }

    //添加布局
    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_search_goods;
    }

    //初始化
    @Override
    protected void initView() {
        //设置Toolbar
        new ToolbarWrapper(this);

        tvOrderList.get(0).setActivated(true);

        final String filterStr = getIntent().getStringExtra(EXTRA_SEARCH_FILTER);
        filter = new Gson().fromJson(filterStr, Filter.class);

        //刷新加载
        ptrWrapper = new PtrWrapper(this, true) {

            @Override
            protected void onRefresh() {
                searchGoods(true);
            }

            @Override
            protected void onLoadMore() {

                if (paginated.hasMore()) {
                    searchGoods(false);
                } else {
                    ptrWrapper.stopRefresh();
                    ToastWrapper.show(R.string.msg_load_more_complete);
                }
            }
        };

        //搜索控件监听
        searchView.setOnSearchListener(new SimpleSearchView.OnSearchListener() {
            @Override
            public void search(String query) {
                filter.setKeywords(query);
                ptrWrapper.autoRefresh();
            }
        });


        //处理ListView 设置适配器
        searchgoodsAdapter = new SearchGoodsAdapter();
        goodsListView.setAdapter(searchgoodsAdapter);

        ptrWrapper.postRefreshDelayed(50);//延时自动刷新
    }

    @OnItemClick({R.id.list_goods})
    public void goodsItemClick(int position) {
        //跳转到商品详情页
        int id = searchgoodsAdapter.getItem(position).getId();
        Intent intent = GoodsActivity.getStartIntent(this, id);
        startActivity(intent);

    }

    @OnClick({R.id.text_is_hot, R.id.text_most_expensive, R.id.text_cheapest})
    public void chooseGoodsOrder(View view) {

        if (view.isActivated()) return;
        //如果正在刷新， 不去切换
        if (ptrWrapper.isRefreshing()) return;

        //三个都不选
        for (TextView sortView : tvOrderList) {
            sortView.setActivated(false);
        }

        //选择的
        view.setActivated(true);

        //排序字段
        String sortBy;
        switch (view.getId()) {
            case R.id.text_is_hot:
                sortBy = Filter.SORT_IS_HOT;
                break;
            case R.id.text_most_expensive:
                sortBy = Filter.SORT_PRICE_DESC;

                break;
            case R.id.text_cheapest:
                sortBy = Filter.SORT_PRICE_ASC;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        filter.setSortBy(sortBy);
        // 简单解决切换过快的问题
        long time = 2000 + lastRefreshTime - System.currentTimeMillis();
        time = time < 0 ? 0 : time;
        ptrWrapper.postRefreshDelayed(time);

    }

    //网络请求获取数据
    private void searchGoods(boolean isRefresh) {

        if (searchCall != null) {
            searchCall.cancel();
        }

        if (isRefresh) {
            lastRefreshTime = System.currentTimeMillis();

            pagination.reset();//刷新 页数从1开始
            goodsListView.setSelection(0);//将ListView定义到第一条数据
        } else {
            //加载时 页数加一
            pagination.next();
        }
//        //请求体
//        SearchReq searchReq = new SearchReq();
//        searchReq.setFilter(filter);
//        searchReq.setPagination(pagination);

        ApiSearch apiSearch = new ApiSearch(filter, pagination);
        searchCall = enqueue(apiSearch);

    }

    // 拿到数据处理
    @Override
    protected void onBusinessResponse(String path, boolean isSucces, ResponseEntity responseEntity) {
        if (!ApiPath.SEARCH.equals(path)) {
            throw new UnsupportedOperationException(path);
        }

        ptrWrapper.stopRefresh();
        searchCall = null;
        if (isSucces) {
            SearchRsp searchRsp = (SearchRsp) responseEntity;
            paginated = searchRsp.getPaginated();
            List<SimpleGoods> simpleGoodsList = searchRsp.getData();
            if (pagination.isFirst()) {
                //刷新
                searchgoodsAdapter.reset(simpleGoodsList);
            } else {
                //加载
                searchgoodsAdapter.addAll(simpleGoodsList);
            }
        }
    }

}
