package com.ghf.eshop.feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghf.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试Fragment
 */

public class TestFragment extends Fragment {
    @BindView(R.id.text)
    TextView textView;

    private static final String FRAGMENT_TEXT = "fragment text";
    private View view;
    //对外提供一个方法 传递数据

    public static TestFragment newInstance(String text) {
        TestFragment testFragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TEXT, text);
        testFragment.setArguments(bundle);
        return testFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_test, container, false);
            ButterKnife.bind(this, view);
        }
        textView.setText(getArgumentText());
        return view;
    }

    public String getArgumentText() {
        return getArguments().getString(FRAGMENT_TEXT);
    }

}
