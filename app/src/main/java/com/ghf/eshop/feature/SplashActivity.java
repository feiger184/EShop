package com.ghf.eshop.feature;


import android.animation.Animator;
import android.content.Intent;
import android.widget.ImageView;

import com.ghf.eshop.MainActivity;
import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseActivity;
import com.ghf.eshop.network.core.ResponseEntity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity implements Animator.AnimatorListener {

    @BindView(R.id.image_splash)
    ImageView mIvSplash;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSucces, ResponseEntity responseEntity) {

    }

    @Override
    protected void initView() {
        mIvSplash.setAlpha(0.3f);
        mIvSplash.animate()
                .alpha(1.0f)
                .setDuration(2000)
                .setListener(this)
                .start();
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishWithDefault();
    }

    //动画取消的时候调用
    @Override
    public void onAnimationCancel(Animator animation) {

    }

    //动画重复播放的时候调用
    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
