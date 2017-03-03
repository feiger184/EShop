package com.ghf.eshop;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ghf.eshop.base.BaseActivity;
import com.ghf.eshop.feature.TestFragment;
import com.ghf.eshop.feature.category.CategoryFragment;
import com.ghf.eshop.feature.home.HomeFragment;
import com.ghf.eshop.network.core.ResponseEntity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private HomeFragment mHomeFragment;
    private CategoryFragment mCategoryFragment;
    private TestFragment mCartFragment;
    private TestFragment mMineFragment;

    private Fragment mCurrentFragment;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_eshop_main;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSucces, ResponseEntity responseEntity) {

    }

    @Override
    protected void initView() {

        // 可以看一下Fragmentmanager里面是不是已经有了这些Fragment
        retrieveFragment();

        mBottomBar.setOnTabSelectListener(this);

    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_home:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                }
                switchFragment(mHomeFragment);
                break;
            case R.id.tab_category:
                if (mCategoryFragment == null) {
                    mCategoryFragment = CategoryFragment.newInstance();
                }
                switchFragment(mCategoryFragment);
                break;
            case R.id.tab_cart:
                if (mCartFragment == null) {
                    mCartFragment = TestFragment.newInstance("CartFragment");
                }
                switchFragment(mCartFragment);
                break;
            case R.id.tab_mine:
                if (mMineFragment == null) {
                    mMineFragment = TestFragment.newInstance("MineFragment");
                }
                switchFragment(mMineFragment);
                break;
            default:
                throw new UnsupportedOperationException("unsupport");

        }
    }

    private void switchFragment(Fragment target) {
        if (mCurrentFragment == target) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }

        if (target.isAdded()) {
            transaction.show(target);
        } else {
            String tag;
            if (target instanceof TestFragment) {

                tag = ((TestFragment) target).getArgumentText();
            } else {
                tag = target.getClass().getName();
            }
            transaction.add(R.id.layout_container, target, tag);
        }
        transaction.commit();

        mCurrentFragment = target;
    }

    // 恢复因为系统重启造成的Fragmentmanager里面恢复的Fragment
    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        mHomeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        mCategoryFragment = (CategoryFragment) manager.findFragmentByTag(CategoryFragment.class.getName());
        mCartFragment = (TestFragment) manager.findFragmentByTag("CartFragment");
        mMineFragment = (TestFragment) manager.findFragmentByTag("MineFragment");
    }

    @Override
    public void onBackPressed() {

        if (mCurrentFragment != mHomeFragment) {

            // 如果不是在首页，就切换首页上
            mBottomBar.selectTabWithId(R.id.tab_home);
            return;
        }
        // 是首页，我们不去关闭，退到后台运行
        moveTaskToBack(true);
    }
}
