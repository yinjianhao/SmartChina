package com.me.smartchina.base.impl;

import android.app.Activity;
import android.view.View;

import com.me.smartchina.R;
import com.me.smartchina.base.BaseMenuDetailPager;

/**
 * @auther yjh
 * @date 2016/8/16
 */
public class PhotosMenuDetailPager extends BaseMenuDetailPager {

    public PhotosMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        return View.inflate(getmActivity(), R.layout.pager_photos_menu_detail, null);
    }
}
