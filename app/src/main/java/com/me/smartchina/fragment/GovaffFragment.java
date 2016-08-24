package com.me.smartchina.fragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.smartchina.R;

/**
 * @auther yjh
 * @date 2016/8/13
 */
public class GovaffFragment extends BaseFragment {
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_govaff, container, false);
    }

    @Override
    public void initData(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText("政务");
    }
}
