package com.me.smartchina.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.me.smartchina.R;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        LinearLayout llShare = (LinearLayout) findViewById(R.id.ll_share);
        if (llShare != null) {
            llShare.setVisibility(View.VISIBLE);
            llShare.setOnClickListener(this);
        }

        LinearLayout llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        if (llMenu != null) {
            llMenu.setOnClickListener(this);
        }

        ImageButton btnMenu = (ImageButton) findViewById(R.id.btn_menu);
        if (btnMenu != null) {
            btnMenu.setVisibility(View.GONE);
        }

        ImageButton btnBack = (ImageButton) findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setVisibility(View.VISIBLE);
        }

        WebView wbNews = (WebView) findViewById(R.id.wb_news_detail);
        if (wbNews != null) {
            WebSettings settings = wbNews.getSettings();
            settings.setJavaScriptEnabled(true);
            wbNews.loadUrl("https://www.baidu.com");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_share:
                showShare();
                break;
            case R.id.ll_menu:
                back();
                break;
            default:
                break;
        }
    }

    private void back() {
        finish();
    }

    private void showShare() {

        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.ssdk_oks_share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
