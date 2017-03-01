package com.ghf.eshop.base.wrapper;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseActivity;
import com.ghf.eshop.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Toolbar的包装类
 */

public class ToolbarWrapper {

    private BaseActivity baseActivity;
    private TextView tvTitle;

    public ToolbarWrapper(BaseActivity activity) {
        baseActivity = activity;
        Toolbar toolbar = ButterKnife.findById(activity, R.id.standard_toolbar);
        init(toolbar);

        //标题不设置(TextView展示)  返回箭头有
        setShowBack(true);//设置返回箭头有
        setShowTitle(false);//设置默认标题不显示
    }

    public ToolbarWrapper(BaseFragment fragment) {
        baseActivity = (BaseActivity) fragment.getActivity();
        Toolbar toolbar = ButterKnife.findById(fragment.getView(), R.id.standard_toolbar);
        init(toolbar);
        //标题不设置(TextView展示)  返回箭头没有
        setShowBack(false);//设置返回箭头没有
        setShowTitle(false);//设置默认标题不显示
    }


    private void init(Toolbar toolbar) {
        //找到标题的TextView
        tvTitle = ButterKnife.findById(toolbar, R.id.standard_toolbar_title);
        //设置Toolbar作为ActionBar展示
        baseActivity.setSupportActionBar(toolbar);

    }

    private ActionBar getSupportActionBar() {
        return baseActivity.getSupportActionBar();
    }

    //设置自定义标题
    public ToolbarWrapper setCustomTitle(int resId) {
        if (tvTitle == null) {
            throw new UnsupportedOperationException("No title TextView in Toolbar");
        }
        tvTitle.setText(resId);
        return this;
    }


    //为了方便链式调用。返回值为本身
    public ToolbarWrapper setShowTitle(boolean isShowTitle) {
        getSupportActionBar().setDisplayShowTitleEnabled(isShowTitle);
        return this;
    }

    public ToolbarWrapper setShowBack(boolean isShowBack) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBack);
        return this;
    }

}
