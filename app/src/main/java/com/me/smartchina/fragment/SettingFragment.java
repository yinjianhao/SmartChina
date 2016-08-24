package com.me.smartchina.fragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.me.smartchina.R;

/**
 * @auther yjh
 * @date 2016/8/13
 */
public class SettingFragment extends BaseFragment {
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void initData(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText("设置");
        ImageButton btnMenu = (ImageButton) view.findViewById(R.id.btn_menu);
        btnMenu.setVisibility(View.GONE);
    }
}
