package com.ghf.eshop.feature.home;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseFragment;

/**
 * 首页Fragment
 **/
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Override
    protected int geContentViewLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {


    }
}
