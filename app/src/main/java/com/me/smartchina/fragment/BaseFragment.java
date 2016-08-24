package com.me.smartchina.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基类
 *
 * @auther yjh
 * @date 2016/8/13
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initData(view);
    }

    /**
     * 初始化View
     *
     * @param inflater
     * @param container
     * @return
     */
    public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 初始化Data
     */
    public void initData(View view) {
    }
}
