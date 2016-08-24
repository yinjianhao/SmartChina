package com.me.smartchina.fragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.me.smartchina.R;
import com.me.smartchina.activity.MainActivity;
import com.me.smartchina.bean.NewsData;
import com.me.smartchina.global.Constants;
import com.me.smartchina.utils.CacheUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;


public class LeftMenuFragment extends BaseFragment {

    private ListView lvMenu;
    private ArrayList<NewsData.NewsInnerData> newsInnerData;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_left_menu, container, false);
        lvMenu = (ListView) view.findViewById(R.id.lv_menu);
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity mainActivity = ((MainActivity) getActivity());
                mainActivity.setMenuDetailContentView(i);
                mainActivity.getSlidingMenu().toggle();
            }
        });

        return view;
    }

    @Override
    public void initData(View view) {
        String data;
        if ((data = CacheUtils.getCache(getActivity(), Constants.CATEGORIES_URL, null)) != null) {
            NewsData newsData = JSON.parseObject(data, NewsData.class);
            newsInnerData = newsData.getData();
            lvMenu.setAdapter(new NewsAdapter());
        }
        getDataFromService();
    }

    private void getDataFromService() {
        RequestParams params = new RequestParams(Constants.CATEGORIES_URL);

        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String string) {
                CacheUtils.setCache(getActivity(), Constants.CATEGORIES_URL, string);
                NewsData newsData = JSON.parseObject(string, NewsData.class);
                newsInnerData = newsData.getData();
                lvMenu.setAdapter(new NewsAdapter());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(getContext(), "网络链接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private class NewsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return newsInnerData.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = View.inflate(getContext(), R.layout.listview_item_left_menu, null);
            TextView tvTitle = (TextView) view1.findViewById(R.id.tv_title);
            tvTitle.setText(newsInnerData.get(i).getTitle());
            return view1;
        }
    }

    public ArrayList<NewsData.NewsInnerData> getNewsInnerData() {
        return newsInnerData;
    }

}
