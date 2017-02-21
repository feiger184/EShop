package com.ghf.eshop.feature;


import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ghf.eshop.MainActivity;
import com.ghf.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends AppCompatActivity implements Animator.AnimatorListener{

    @BindView(R.id.image_splash)
    ImageView mIvSplash;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);
        // 完成视图的操作
        initView();
    }

    private void initView() {
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
        // 设置转场的效果
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        finish();
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
