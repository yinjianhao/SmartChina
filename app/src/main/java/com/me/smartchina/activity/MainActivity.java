package com.me.smartchina.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.me.smartchina.R;
import com.me.smartchina.bean.NewsData;
import com.me.smartchina.fragment.GovaffFragment;
import com.me.smartchina.fragment.HomeFragment;
import com.me.smartchina.fragment.LeftMenuFragment;
import com.me.smartchina.fragment.NewsFragment;
import com.me.smartchina.fragment.ServiceFragment;
import com.me.smartchina.fragment.SettingFragment;

import java.util.ArrayList;

public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {

    public static final String TAG_LEFT_MENU = "leftMenu";

    private Fragment mCurrentFragment;
    private int mCurrentIndex;
    private Fragment mHomeFragment;
    private Fragment mNewsFragment;
    private Fragment mGovaffFragment;
    private Fragment mServiceFragment;
    private Fragment mSettingFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLeftMenu();
        initListener();

        showHideFragment(1);
    }

    private void initListener() {
        RadioButton rbHome = (RadioButton) findViewById(R.id.rb_home);
        rbHome.setOnClickListener(this);

        RadioButton rbNews = (RadioButton) findViewById(R.id.rb_news);
        rbNews.setOnClickListener(this);

        RadioButton rbGovaff = (RadioButton) findViewById(R.id.rb_govaff);
        rbGovaff.setOnClickListener(this);

        RadioButton rbService = (RadioButton) findViewById(R.id.rb_service);
        rbService.setOnClickListener(this);

        RadioButton rbSetting = (RadioButton) findViewById(R.id.rb_setting);
        rbSetting.setOnClickListener(this);
    }

    /**
     * 初始化侧边栏
     */
    private void initLeftMenu() {
        setBehindContentView(R.layout.menu_sliding_left);

        SlidingMenu slidingMenu = getSlidingMenu();

        //设置不能滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

        //设置右边留白
        slidingMenu.setBehindOffset(200);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        //替换侧边栏
        transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), TAG_LEFT_MENU);

        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home:
                showHideFragment(1);
                break;
            case R.id.rb_news:
                showHideFragment(2);
                break;
            case R.id.rb_govaff:
                showHideFragment(3);
                break;
            case R.id.rb_service:
                showHideFragment(4);
                break;
            case R.id.rb_setting:
                showHideFragment(5);
                break;
            default:
                break;
        }
    }

    private void showHideFragment(int index) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (mCurrentIndex == index) {
            return;
        }
        mCurrentIndex = index;

        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }
        switch (index) {
            case 1:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.fl_content, mHomeFragment);
                } else {
                    transaction.show(mHomeFragment);
                }
                setSlidingMenuEnable(false);
                mCurrentFragment = mHomeFragment;
                break;
            case 2:
                if (mNewsFragment == null) {
                    mNewsFragment = new NewsFragment();
                    transaction.add(R.id.fl_content, mNewsFragment);
                } else {
                    transaction.show(mNewsFragment);
                }
                setSlidingMenuEnable(true);
                mCurrentFragment = mNewsFragment;
                break;
            case 3:
                if (mGovaffFragment == null) {
                    mGovaffFragment = new GovaffFragment();
                    transaction.add(R.id.fl_content, mGovaffFragment);
                } else {
                    transaction.show(mGovaffFragment);
                }
                setSlidingMenuEnable(false);
                mCurrentFragment = mGovaffFragment;
                break;
            case 4:
                if (mServiceFragment == null) {
                    mServiceFragment = new ServiceFragment();
                    transaction.add(R.id.fl_content, mServiceFragment);
                } else {
                    transaction.show(mServiceFragment);
                }
                setSlidingMenuEnable(false);
                mCurrentFragment = mServiceFragment;
                break;
            case 5:
                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                    transaction.add(R.id.fl_content, mSettingFragment);
                } else {
                    transaction.show(mSettingFragment);
                }
                setSlidingMenuEnable(false);
                mCurrentFragment = mSettingFragment;
                break;
            default:
                break;
        }

        transaction.commit();
    }

    /**
     * 设置侧边栏是否可用
     *
     * @param enable 是/否
     */
    private void setSlidingMenuEnable(Boolean enable) {
        if (enable) {
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        } else {
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    /**
     * 设置左侧菜单详情view
     *
     * @param i 左侧菜单item position
     */
    public void setMenuDetailContentView(int i) {
        ((NewsFragment) mNewsFragment).setContentView(i);
    }

    public ArrayList<NewsData.NewsInnerData> getNewsInnerData() {
        FragmentManager fm =  getSupportFragmentManager();
        LeftMenuFragment leftFragment = (LeftMenuFragment) fm.findFragmentByTag(TAG_LEFT_MENU);
        return leftFragment.getNewsInnerData();
    }
}
