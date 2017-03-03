package com.ghf.eshop.base.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghf.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索框自定义  组合控件
 */

public class SimpleSearchView extends LinearLayout implements TextWatcher, TextView.OnEditorActionListener {

    @BindView(R.id.button_clear)
    ImageButton btnClear;
    @BindView(R.id.edit_query)
    EditText editText;
    @BindView(R.id.button_search)
    ImageButton btnSearch;

    //代码中
    public SimpleSearchView(Context context) {
        super(context);
        init(context);
    }

    //布局中 不设置Style
    public SimpleSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    //布局中 设置了Style
    public SimpleSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_search_view, this,true);
        ButterKnife.bind(this);

        //设置监听
        editText.addTextChangedListener(this);

        //设置软件盘的操作：回车变成搜索的图标
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //设置输入的类型  文本类型
        editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        //设置软件盘的操作事件监听
        editText.setOnEditorActionListener(this);

    }

    //拿到字符串。然后搜索
    private void search() {
        String query = editText.getText().toString();
        if (onSearchListener != null) {
            onSearchListener.search(query);
        }
        //关闭软键盘
        closeKeyBoard();
    }

    //关闭软键盘
    private void closeKeyBoard() {
        //失去焦点
        editText.clearFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    //文本输入变化前
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //文本输入中
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //文本输入完
    @Override
    public void afterTextChanged(Editable s) {
        String query = editText.getText().toString();
        int visible = TextUtils.isEmpty(query) ? View.INVISIBLE : View.VISIBLE;
        btnClear.setVisibility(visible);
    }

    //输入键盘的操作
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //搜索事件
            search();
            return true;
        }
        return false;
    }

    @OnClick({R.id.button_search, R.id.button_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_search:
                search();
                break;
            //点击清除图标
            case R.id.button_clear:
                editText.setText(null);
                search();
                break;
        }
    }

    public interface OnSearchListener {
        //搜索方法  具体实现让使用者实现
        void search(String query);
    }

    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener listener) {
        onSearchListener = listener;
    }
}
