package com.ghf.eshop.base.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.ghf.eshop.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * 自定义轮播图组件
 * 自动轮播
 * 数据随意设置
 * 自动和手动冲突 触摸的事件+轮播时间
 */

public class BannerLayout extends RelativeLayout {

    @BindView(R.id.pager_banner)
    ViewPager viewPager;

    @BindView(R.id.indicator)
    CircleIndicator indicator;

    private static final long duration = 4000;
    private CyclingHandler cyclingHandler;
    private Timer timer;
    private TimerTask timerTask;
    private long resumeCycleTime;

    public BannerLayout(Context context) {
        super(context);
        init(context);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /*
    * 布局填充 初始化
    * */
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_banner_layout, this, true);
        ButterKnife.bind(this);
        cyclingHandler = new CyclingHandler(this);
    }

    //能看到视图时
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //在试图上显示出来的时候 开始计时

        timer = new Timer();
        //定时的发送一些事件 使用Handler发送 处理
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //定时的发送一些事件 使用Handler发送 处理
                cyclingHandler.sendEmptyMessage(0);
            }
        };

        //执行(任务 ， 延时时间 ，循环时间 )
        timer.schedule(timerTask, duration, duration);

    }


    //不能看到视图时
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //取消计时器任务
        timer.cancel();
        timerTask.cancel();
        timer=null;
        timerTask = null;

    }

    //设置适配器的方法
    public void setAdapter(BannerAdapter adapter) {
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(indicator.getDataSetObserver());
    }

    //获取触屏的时间
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        resumeCycleTime = System.currentTimeMillis() + duration;
        return super.dispatchTouchEvent(ev);
    }
    
    /*切换到下一页*/
    public void moveToNextPager() {
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("you need set a banner adapter");
        }

        int count = viewPager.getAdapter().getCount();
        if (count == 0) return;
        //如果展示的是最后一页
        if (viewPager.getCurrentItem() == count - 1) {
            viewPager.setCurrentItem(0, false);
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
    }

    /*
    * 为了防止内部类持有外部类的引用而造成内存泄露 ， 使用静态类+弱引用
    * */
    private static class CyclingHandler extends Handler {

        private WeakReference<BannerLayout> bannerReference;

        public CyclingHandler(BannerLayout banner) {
            bannerReference = new WeakReference<BannerLayout>(banner);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //接受消息  处理:轮播图切换
            if (bannerReference == null) return;
            BannerLayout bannerLayout = bannerReference.get();

            if (bannerLayout == null) return;

            //触摸时间不足4秒 不轮播
            if (System.currentTimeMillis() < bannerLayout.resumeCycleTime) {
                return;
            }
            bannerLayout.moveToNextPager();
        }
    }
}
