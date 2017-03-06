package com.ghf.eshop.feature.goods;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseActivity;
import com.ghf.eshop.base.widgets.GoodsPopupWindow;
import com.ghf.eshop.base.wrapper.ToastWrapper;
import com.ghf.eshop.base.wrapper.ToolbarWrapper;
import com.ghf.eshop.network.api.ApiGoodsInfo;
import com.ghf.eshop.network.core.ApiPath;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.entity.goodsinfo.GoodsInfo;
import com.ghf.eshop.network.entity.goodsinfo.GoodsInfoRsp;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class GoodsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String EXTRA_GOODS_ID = "EXTRA_GOOD_ID";

    @BindView(R.id.pager_goods)
    ViewPager goodsPager;

    @BindViews({R.id.text_tab_goods, R.id.text_tab_details, R.id.text_tab_comments})
    List<TextView> tvTabList;
    private int i;
    private GoodsInfo goodsInfo;
    private GoodsPopupWindow goodsPopupWindow;

    //对外提供一个方法 跳转到本页面
    public static Intent getStartIntent(Context context, int goodsId) {

        Intent intent = new Intent(context, GoodsActivity.class);
        intent.putExtra(EXTRA_GOODS_ID, goodsId);
        return intent;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_goods;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_goods, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_share) {
            ToastWrapper.show(R.string.action_share);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void initView() {
        //toolbar
        new ToolbarWrapper(this);

        //ViewPager 监听
        goodsPager.addOnPageChangeListener(this);

        // 拿到传递的数据
        int goodsId = getIntent().getIntExtra(EXTRA_GOODS_ID, 0);
        enqueue(new ApiGoodsInfo(goodsId));

    }

    //三个TextView标题的切换事件
    @OnClick({R.id.text_tab_goods, R.id.text_tab_details, R.id.text_tab_comments})
    public void onClickTab(TextView textView) {
        int position = tvTabList.indexOf(textView);
        goodsPager.setCurrentItem(position, false);
        chooseTab(position);
    }

    /// 主要是改变选中的Tab的样式：颜色和字体大小
    private void chooseTab(int position) {
        Resources resources = getResources();
        for (int i = 0; i < tvTabList.size(); i++) {
            tvTabList.get(i).setSelected(i == position);
            float textSize = i == position ? resources.getDimension(R.dimen.font_large) : resources.getDimension(R.dimen.font_normal);
            tvTabList.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }


    @OnClick({R.id.button_show_cart, R.id.button_add_cart, R.id.button_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_show_cart:
                break;
            case R.id.button_add_cart:
                break;
            case R.id.button_buy:

                if (goodsInfo==null) return;

                if (goodsPopupWindow == null) {
                    goodsPopupWindow = new GoodsPopupWindow(this, goodsInfo);
                }
                goodsPopupWindow.show(new GoodsPopupWindow.OnConfirmListener() {
                    @Override
                    public void onConfirm(int number) {
                        ToastWrapper.show("Confirm" + number);
                        goodsPopupWindow.dismiss();
                    }
                });
                break;
            default:
                throw new UnsupportedOperationException("Unsupported View");
        }
    }

    public void selectPage(int position) {
        goodsPager.setCurrentItem(position, false);
        chooseTab(position);
    }


    //拿到数据处理
    @Override
    protected void onBusinessResponse(String path, boolean isSucces, ResponseEntity responseEntity) {

        switch (path) {
            case ApiPath.GOODS_INFO:
                if (isSucces) {

                    GoodsInfoRsp goodsInfoRsp = (GoodsInfoRsp) responseEntity;
                    goodsInfo = goodsInfoRsp.getData();
                    goodsPager.setAdapter(new GoodsPagerAdapter(getSupportFragmentManager(), goodsInfo));

                    //默认选择第一项
                    chooseTab(0);
                }

                break;
            default:
                throw new UnsupportedOperationException(path);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        chooseTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
