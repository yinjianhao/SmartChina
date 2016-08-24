package com.me.smartchina.base;

import android.app.Activity;
import android.view.View;

/**
 * 侧边栏菜单切换的详情页面基类
 *
 * @auther yjh
 * @date 2016/8/16
 */
public abstract class BaseMenuDetailPager {

    private Activity mActivity;
    private View mRootView;

    public BaseMenuDetailPager(Activity activity) {
        this.mActivity = activity;
        mRootView = initView();
    }

    public abstract View initView();

    public void initData() {
    }

    public Activity getmActivity() {
        return mActivity;
    }

    public View getmRootView() {
        return mRootView;
    }
}
