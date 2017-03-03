package com.ghf.eshop.feature.home;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseFragment;
import com.ghf.eshop.base.widgets.BannerAdapter;
import com.ghf.eshop.base.widgets.BannerLayout;
import com.ghf.eshop.base.wrapper.PtrWrapper;
import com.ghf.eshop.base.wrapper.ToolbarWrapper;
import com.ghf.eshop.feature.goods.GoodsActivity;
import com.ghf.eshop.network.api.ApiHomeBanner;
import com.ghf.eshop.network.api.ApiHomeCategory;
import com.ghf.eshop.network.core.ApiPath;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.entity.home.Banner;
import com.ghf.eshop.network.entity.home.HomeBannerRsp;
import com.ghf.eshop.network.entity.home.HomeCategoryRsp;
import com.ghf.eshop.network.entity.home.Picture;
import com.ghf.eshop.network.entity.home.SimpleGoods;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;

/**
 * 首页Fragment
 **/
public class HomeFragment extends BaseFragment {

    @BindView(R.id.standard_toolbar)
    Toolbar toolbar;
    @BindView(R.id.standard_toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.list_home_goods)
    ListView listView;
    @BindView(R.id.standard_refresh_layout)
    PtrFrameLayout refreshLayout;

    private ImageView[] ivPromotes = new ImageView[4];
    private TextView tvPromoteGoods;
    private BannerAdapter<Banner> bannerAdapter;
    private HomeGoodsAdapter goodsAdapter;
    private PtrWrapper ptrWrapper;

    private boolean bannerRefreshed = false;
    private boolean categoryRefreshed = false;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int geContentViewLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        //初始化toolbar
        new ToolbarWrapper(this).setCustomTitle(R.string.home_title);

        //刷新
        ptrWrapper = new PtrWrapper(this, false) {

            @Override
            protected void onRefresh() {

                bannerRefreshed = false;
                categoryRefreshed = false;

                // 轮播图的数据获取
                enqueue(new ApiHomeBanner());
                // 分类及推荐商品数据
                enqueue(new ApiHomeCategory());
            }

            @Override
            protected void onLoadMore() {

            }
        };

        ptrWrapper.postRefreshDelayed(50);


        //添加头布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.partial_home_header, listView, false);

        //找到头布局的控件
        BannerLayout bannerLayout = ButterKnife.findById(view, R.id.layout_banner);
        //数据和视图的绑定

        bannerAdapter = new BannerAdapter<Banner>() {
            @Override
            protected void bind(ViewHolder holder, Banner data) {
                //数据和视图的绑定
                // 图片展示 实现
                Picasso.with(getContext()).load(data.getPicture().getLarge()).into(holder.imageView);

            }
        };
        bannerLayout.setAdapter(bannerAdapter);

        //促销商品
        ivPromotes[0] = ButterKnife.findById(view, R.id.image_promote_one);
        ivPromotes[1] = ButterKnife.findById(view, R.id.image_promote_two);
        ivPromotes[2] = ButterKnife.findById(view, R.id.image_promote_three);
        ivPromotes[3] = ButterKnife.findById(view, R.id.image_promote_four);

        //促销单品的textview
        tvPromoteGoods = ButterKnife.findById(view, R.id.text_promote_goods);
        listView.addHeaderView(view);

        //设置适配器
        goodsAdapter = new HomeGoodsAdapter();
        listView.setAdapter(goodsAdapter);
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSucces, ResponseEntity responseEntity) {
        switch (path) {
            case ApiPath.HOME_DATA:
                bannerRefreshed = true;
                if (isSucces) {
                    HomeBannerRsp bannerRsp = (HomeBannerRsp) responseEntity;
                    bannerAdapter.reset(bannerRsp.getData().getBanners());
                    setPromoteGoods(bannerRsp.getData().getGoodsList());
                }
                break;
            case ApiPath.HOME_CATEGORY:
                categoryRefreshed = true;
                if (isSucces) {
                    HomeCategoryRsp categoryRsp = (HomeCategoryRsp) responseEntity;
                    goodsAdapter.reset(categoryRsp.getData());
                }
                break;
            default:
                throw new UnsupportedOperationException(path);

        }

        if (bannerRefreshed && categoryRefreshed) {
            //两个接口都拿到数据之后，停止刷新
            ptrWrapper.stopRefresh();
        }
    }

    //设置促销商品展示
    private void setPromoteGoods(List<SimpleGoods> goodsList) {
        tvPromoteGoods.setVisibility(View.VISIBLE);
        for (int i = 0; i < ivPromotes.length; i++) {
            ivPromotes[i].setVisibility(View.VISIBLE);
            final SimpleGoods simpleGoods = goodsList.get(i);
            Picture picture = simpleGoods.getImg();
            //  设置图片
            Picasso.with(getContext()).load(picture.getLarge())
                    .transform(new CropCircleTransformation())//圆形
                    .transform(new GrayscaleTransformation())//灰度
                    .into(ivPromotes[i]);

            ivPromotes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = simpleGoods.getId();
                    Intent intent = GoodsActivity.getStartIntent(getContext(), id);
                    getActivity().startActivity(intent);

                }
            });
        }
    }
}
