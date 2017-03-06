package com.ghf.eshop.feature.goods.info;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ghf.eshop.R;
import com.ghf.eshop.network.entity.home.Picture;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情适配器
 */

public abstract class GoodsPictureAdapter extends PagerAdapter {

    private List<Picture> pictureList = new ArrayList<>();

    public GoodsPictureAdapter(List<Picture> pictureList) {
        this.pictureList = pictureList;
    }

    @Override
    public int getCount() {
        return pictureList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) LayoutInflater.from(container.getContext()).inflate(R.layout.item_goods_picture, container, false);
        container.addView(imageView);
        final Picture picture = pictureList.get(position);
        Picasso.with(container.getContext()).load(picture.getLarge()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onImageClicked(picture);
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    protected abstract void onImageClicked(Picture picture);

}
