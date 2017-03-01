package com.ghf.eshop.base.wrapper;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司的包装类
 */

public class ToastWrapper {

    private static Context mContext;
    private static Toast toast;

    public static void init(Context context) {
        mContext = context;
        toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
    }

    public static void show(int resId, Object... args) {
        String text = mContext.getString(resId, args);
        toast.setText(text);
        toast.show();
    }

    public static void show(CharSequence charSequence, Object... args) {
        String text = String.format(charSequence.toString(), args);
        toast.setText(text);
        toast.show();
    }
}
