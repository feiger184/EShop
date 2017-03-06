package com.ghf.eshop.base.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseActivity;
import com.ghf.eshop.base.wrapper.ToastWrapper;
import com.ghf.eshop.network.entity.goodsinfo.GoodsInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品选择的弹窗
 */

public class GoodsPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    @BindView(R.id.image_goods)
    ImageView ivGoods;
    @BindView(R.id.text_goods_price)
    TextView tvPrice;
    @BindView(R.id.text_number_value)
    TextView tvNumber;
    @BindView(R.id.text_inventory_value)
    TextView tvInventory;
    @BindView(R.id.number_picker)
    SimpleNumberPicker numberPicker;

    private final ViewGroup parent;
    private GoodsInfo goodsInfo;
    private Activity activity;

    public GoodsPopupWindow(BaseActivity activity, @NonNull GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
        this.activity = activity;

        parent = (ViewGroup) activity.getWindow().getDecorView();
        Context context = parent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_goods_spec, parent, false);
        setContentView(view);

        //设置宽高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(context.getResources().getDimensionPixelSize(R.dimen.size_400));

        //设置焦点
        setFocusable(true);

        //设置点击外面区域关闭popupwindow
        setOutsideTouchable(true);

        //设置背景
        setBackgroundDrawable(new ColorDrawable());

        // 该Activity总是调整屏幕的大小以便留出软键盘的空间
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);

        setOnDismissListener(this);

        ButterKnife.bind(this, view);

        initView();
    }

    private void initView() {
        //商品图
        Picasso.with(parent.getContext()).load(goodsInfo.getImg().getLarge()).into(ivGoods);

        //商品价格
        tvPrice.setText(goodsInfo.getShopPrice());

        tvInventory.setText(String.valueOf(goodsInfo.getNumber()));

        numberPicker.setOnNumberChangeListener(new SimpleNumberPicker.OnNumberChangeListener() {
            @Override
            public void onNumberChanged(int number) {
                tvNumber.setText(String.valueOf(number));
            }
        });

    }

    public void show(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        //显示设置背景
        backgroundAlpha(0.6f);
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = bgAlpha;
        activity.getWindow().setAttributes(params);
    }

    @OnClick(R.id.button_ok)
    public void onClick() {

        // 确定按钮，要将选择的数量传递出去，接口回调的方式。
        int number = numberPicker.getNumber();
        if (number==0){
            ToastWrapper.show(R.string.goods_msg_must_choose_number);
            return;
        }

        onConfirmListener.onConfirm(number);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);
        onConfirmListener = null;
    }


    //-------------接口回调----------

    private OnConfirmListener onConfirmListener;

    public interface OnConfirmListener{
        void onConfirm(int number);
    }
}
