package com.me.smartchina.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.me.smartchina.R;
import com.me.smartchina.utils.PreferencesUtils;

public class GuideActivity extends AppCompatActivity {

    private int[] ids = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private Button mBtnEnter;
    private ImageView mIvRedPointer;
    private LinearLayout mLlPointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mBtnEnter = (Button) findViewById(R.id.btn_enter);
        mIvRedPointer = (ImageView) findViewById(R.id.iv_red_pointer);
        mLlPointer = (LinearLayout) findViewById(R.id.ll_pointer);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vp_pager);

        Adapter adapter = new Adapter();
        if (vpPager != null) {
            vpPager.setAdapter(adapter);
            vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    Log.d("qwe", String.valueOf(positionOffset));
//                    Log.d("qwe", String.valueOf(positionOffsetPixels));
                    int l = ids.length;
                    Point point = new Point();
                    getWindowManager().getDefaultDisplay().getSize(point);
//                    mIvRedPointer.setTranslationX(positionOffsetPixels * ((float) mLlPointer.getWidth() / point.x) / l + position * mLlPointer.getWidth() / l);
                    mIvRedPointer.setTranslationX(mLlPointer.getWidth() * positionOffset / l + (float) position * mLlPointer.getWidth() / l);
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == ids.length - 1) {
                        mBtnEnter.setVisibility(View.VISIBLE);
                    } else {
                        mBtnEnter.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        if (mBtnEnter != null) {
            mBtnEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PreferencesUtils.setBoolean(GuideActivity.this, "isFirstEnter", false);
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    finish();
                }
            });
        }

        for (int id : ids) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setBackgroundResource(R.drawable.normal_pointer);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.leftMargin = 4;
            params.rightMargin = 4;

            iv.setLayoutParams(params);
            if (mLlPointer != null) {
                mLlPointer.addView(iv);
            }
        }
    }

    private class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setBackgroundResource(ids[position]);
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
}
