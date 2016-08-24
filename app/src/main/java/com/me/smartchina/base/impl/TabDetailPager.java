package com.me.smartchina.base.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.me.smartchina.R;
import com.me.smartchina.activity.NewsDetailActivity;
import com.me.smartchina.base.BaseMenuDetailPager;
import com.me.smartchina.bean.NewsData.NewsInnerData.NewsTabData;
import com.me.smartchina.bean.NewsListData;
import com.me.smartchina.global.Constants;
import com.me.smartchina.utils.CacheUtils;
import com.me.smartchina.view.RefreshListView;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @auther yjh
 * @date 2016/8/18
 */
public class TabDetailPager extends BaseMenuDetailPager {

    private NewsTabData newsTabData;
    private String url;
    private NewsListData newsListData;
    private ArrayList<NewsListData.NewsTab.TopNews> topNewsList;
    private ViewPager vpAd;
    private TextView tvTitle;
    private CirclePageIndicator circleIndicator;
    private ArrayList<NewsListData.NewsTab.News> NewsList;
    private RefreshListView lvNews;
    private Adapter adapter;
    private ListViewAdapter listViewAdapter;

    public TabDetailPager(Activity activity, NewsTabData newsTabData) {
        super(activity);
        this.newsTabData = newsTabData;
    }

    @Override
    public View initView() {
        View view = View.inflate(getmActivity(), R.layout.pager_tab_detail, null);
        lvNews = (RefreshListView) view.findViewById(R.id.lv_news_list);
        View head = View.inflate(getmActivity(), R.layout.listview_top_topnews, null);
        lvNews.addHeaderView(head);
        return view;
    }

    @Override
    public void initData() {
        url = Constants.SERVICE_URL + newsTabData.getUrl();
        String string;
        vpAd = (ViewPager) getmRootView().findViewById(R.id.vp_ad);
        tvTitle = (TextView) getmRootView().findViewById(R.id.tv_title);
        circleIndicator = (CirclePageIndicator) getmRootView().findViewById(R.id.circle_indicator);

        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getmActivity(), NewsDetailActivity.class);
                getmActivity().startActivity(intent);
            }
        });

        lvNews.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromService();
            }

            @Override
            public void onLoadMore() {
                Toast.makeText(getmActivity(), "加载更多", Toast.LENGTH_SHORT).show();
            }
        });

        if ((string = CacheUtils.getCache(getmActivity(), url, null)) != null) {
            processResultData(string);
        }

        getDataFromService();
    }

    private void processResultData(String string) {
        newsListData = JSON.parseObject(string, NewsListData.class);
        topNewsList = newsListData.getData().getTopnews();
        if (topNewsList != null) {
            if (adapter == null) {
                adapter = new Adapter(topNewsList);
                vpAd.setAdapter(adapter);
                circleIndicator.setViewPager(vpAd);
                tvTitle.setText(topNewsList.get(0).getTitle());
                circleIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        tvTitle.setText(topNewsList.get(position).getTitle());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            } else {
                adapter.notifyDataSetChanged();
            }
        }

        NewsList = newsListData.getData().getNews();
        if (NewsList != null) {
            if (listViewAdapter == null) {
                listViewAdapter = new ListViewAdapter();
                lvNews.setAdapter(listViewAdapter);
            } else {
                listViewAdapter.notifyDataSetChanged();
            }
        }
    }

    private void getDataFromService() {
        RequestParams params = new RequestParams(url);

        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String string) {
                CacheUtils.setCache(getmActivity(), url, string);
                processResultData(string);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getmActivity(), "网络链接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                lvNews.setFinish();
            }
        });
    }

    private class Adapter extends PagerAdapter {

        private ArrayList<NewsListData.NewsTab.TopNews> topNews;

        public Adapter(ArrayList<NewsListData.NewsTab.TopNews> topNews) {
            this.topNews = topNews;
        }

        @Override
        public int getCount() {
            return topNews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(getmActivity());
            ImageOptions.Builder builder = new ImageOptions.Builder();
            builder.setLoadingDrawableId(R.drawable.news_pic_default);
            ImageOptions op = builder.build();
            x.image().bind(iv, topNews.get(position).getTopimage(), op);
            container.addView(iv);
            return iv;
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

    private class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return NewsList.size();
        }

        @Override
        public NewsListData.NewsTab.News getItem(int i) {
            return NewsList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(getmActivity(), R.layout.listview_item_news, null);
                viewHolder = new ViewHolder();
                viewHolder.ivNewsImg = (ImageView) view.findViewById(R.id.iv_news_img);
                viewHolder.tvNewsTitle = (TextView) view.findViewById(R.id.tv_news_title);
                viewHolder.tvNewsDate = (TextView) view.findViewById(R.id.tv_news_date);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            NewsListData.NewsTab.News news = getItem(i);
            viewHolder.tvNewsTitle.setText(news.getTitle());
            viewHolder.tvNewsDate.setText(news.getPubdate());
            ImageOptions.Builder builder = new ImageOptions.Builder();
            builder.setLoadingDrawableId(R.drawable.news_pic_default);
            ImageOptions op = builder.build();
            x.image().bind(viewHolder.ivNewsImg, news.getListimage(), op);

            return view;
        }
    }

    private class ViewHolder {
        public ImageView ivNewsImg;
        private TextView tvNewsTitle;
        private TextView tvNewsDate;
    }
}
