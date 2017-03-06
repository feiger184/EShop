package com.ghf.eshop.base.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghf.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品选择器
 */

public class SimpleNumberPicker extends RelativeLayout {

    @BindView(R.id.text_number)
    TextView tvNumber;
    @BindView(R.id.image_minus)
    ImageView imMinus;
    @BindView(R.id.image_plus)
    ImageView imPlus;

    // 最小值
    private int mMin = 0;

    public SimpleNumberPicker(Context context) {
        super(context);
        init(context);
    }

    public SimpleNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public SimpleNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_number_picker, this, true);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.image_minus, R.id.image_plus})
    public void onClick(View view) {
        if (view.getId() == R.id.image_minus) {
            if (getNumber() == mMin) {
                // 不可以再减少了
                return;
            }
            // 数量减少1
            setNumber(getNumber()-1);

        }else {
            // 点击+，数量增加1
            setNumber(getNumber()+1);
        }

        // 减少图标的视图
        if (getNumber()==mMin){
            imMinus.setImageResource(R.drawable.btn_minus_gray);
        }else {
            imMinus.setImageResource(R.drawable.btn_minus);
        }

        // 选择的数量发生变化了，实时的告诉别人：将数量变化传递出去
        // 我们提供一个监听：监听数量变化，将数量传递出去
        if (onNumberChangeListener != null) {
            onNumberChangeListener.onNumberChanged(getNumber());
        }
    }

    //分别设置数量
    private void setNumber(int number) {
        if (number<mMin){
            throw new IllegalArgumentException("Min Number is"+mMin+"while number is "+number);
        }
        tvNumber.setText(String.valueOf(number));
    }

    //拿到当前的数量
    public int getNumber() {
        String string = tvNumber.getText().toString();
        return Integer.parseInt(string);
    }

    private OnNumberChangeListener onNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener listener) {
        onNumberChangeListener = listener;
    }

    // 对外提供一个接口，用于监听数量的变化
    public interface OnNumberChangeListener{
        void onNumberChanged(int number);
    }
}
