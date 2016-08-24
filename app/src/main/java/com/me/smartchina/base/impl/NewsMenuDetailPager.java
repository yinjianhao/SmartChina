package com.me.smartchina.base.impl;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.me.smartchina.R;
import com.me.smartchina.base.BaseMenuDetailPager;
import com.me.smartchina.bean.NewsData.NewsInnerData.NewsTabData;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;

/**
 * @auther yjh
 * @date 2016/8/16
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private ArrayList<TabDetailPager> tabDetailPagers;
    private ArrayList<NewsTabData> newsTabDatas;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsTabData> newsTabDatas) {
        super(activity);
        this.newsTabDatas = newsTabDatas;
    }

    @Override
    public View initView() {
        return View.inflate(getmActivity(), R.layout.pager_news_menu_detail, null);
    }

    @Override
    public void initData() {
        tabDetailPagers = new ArrayList<>();
        for (NewsTabData newsTabData : newsTabDatas) {
            tabDetailPagers.add(new TabDetailPager(getmActivity(), newsTabData));
        }

        ViewPager vpNewsPager = (ViewPager) getmRootView().findViewById(R.id.vp_news_pager);
        vpNewsPager.setAdapter(new Adapter());

        TabPageIndicator indicator = (TabPageIndicator) getmRootView().findViewById(R.id.indicator);
        indicator.setViewPager(vpNewsPager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class Adapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return newsTabDatas.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = tabDetailPagers.get(position).getmRootView();
            container.addView(view);
            tabDetailPagers.get(position).initData();
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
