package com.me.smartchina.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.me.smartchina.R;
import com.me.smartchina.activity.NewsDetailActivity;

/**
 * @auther yjh
 * @date 2016/8/13
 */
public class SettingFragment extends BaseFragment {

    private final String TAG = "SettingFragment";

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

        view.findViewById(R.id.tv_go_news_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
