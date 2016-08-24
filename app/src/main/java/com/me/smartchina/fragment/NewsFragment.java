package com.me.smartchina.fragment;

import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.smartchina.R;
import com.me.smartchina.activity.MainActivity;
import com.me.smartchina.base.impl.InteractMenuDetailPager;
import com.me.smartchina.base.impl.NewsMenuDetailPager;
import com.me.smartchina.base.impl.PhotosMenuDetailPager;
import com.me.smartchina.base.impl.TopicMenuDetailPager;

/**
 * @auther yjh
 * @date 2016/8/13
 */
public class NewsFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "NewsFragment";
    private View mRootView;

    private int mCurrentIndex;
    private NewsMenuDetailPager mNewsMenuDetailPager;
    private TopicMenuDetailPager mTopicMenuDetailPager;
    private PhotosMenuDetailPager mPhotosMenuDetailPager;
    private InteractMenuDetailPager mInteractMenuDetailPager;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        mRootView = inflater.inflate(R.layout.fragment_news, container, false);
        LinearLayout llMenu = (LinearLayout) mRootView.findViewById(R.id.ll_menu);
        llMenu.setOnClickListener(this);
        setContentView(0);
        return mRootView;
    }

    @Override
    public void initData(View view) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText("新闻");
    }

    public void setContentView(int i) {

        if (mCurrentIndex == i && mNewsMenuDetailPager != null) {
            return;
        }
        mCurrentIndex = i;

        FrameLayout flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);

        flContent.removeAllViews();
        switch (i) {
            case 0:
                if (mNewsMenuDetailPager == null) {
                    mNewsMenuDetailPager = new NewsMenuDetailPager(getActivity(), ((MainActivity) getActivity()).getNewsInnerData().get(i).getChildren());
                }
                mNewsMenuDetailPager.initData();
                flContent.addView(mNewsMenuDetailPager.getmRootView());
                break;
            case 1:
                if (mTopicMenuDetailPager == null) {
                    mTopicMenuDetailPager = new TopicMenuDetailPager(getActivity());
                }
                flContent.addView(mTopicMenuDetailPager.getmRootView());
                break;
            case 2:
                if (mPhotosMenuDetailPager == null) {
                    mPhotosMenuDetailPager = new PhotosMenuDetailPager(getActivity());
                }
                flContent.addView(mPhotosMenuDetailPager.getmRootView());
                break;
            case 3:
                if (mInteractMenuDetailPager == null) {
                    mInteractMenuDetailPager = new InteractMenuDetailPager(getActivity());
                }
                flContent.addView(mInteractMenuDetailPager.getmRootView());
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_menu:
                ((MainActivity) getActivity()).getSlidingMenu().toggle();
                break;
        }
    }
}
