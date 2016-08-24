package com.me.smartchina.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.me.smartchina.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @auther yjh
 * @date 2016/8/21
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private static final int STATE_RELEASE_TO_REFRESH = 1;
    private static final int STATE_PULL_TO_REFRESH = 2;
    private static final int STATE_REFRESHING = 3;

    private int currentState = STATE_RELEASE_TO_REFRESH;
    private boolean isLoadingMore;

    private OnRefreshListener listener;

    private int startY = -1;
    private int endY;
    private int headViewHeight;
    private View headView;
    private View footerView;
    private ImageView ivRedPointer;
    private ProgressBar pbLoading;
    private TextView tvTitle;
    private TextView tvTime;
    private OnItemClickListener onItemClickListener;

    public RefreshListView(Context context) {
        super(context);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setOnScrollListener(this);
        headView = View.inflate(getContext(), R.layout.listview_refresh_head, null);
        addHeaderView(headView);

        ivRedPointer = (ImageView) headView.findViewById(R.id.iv_red_pointer);
        pbLoading = (ProgressBar) headView.findViewById(R.id.pb_loading);
        tvTitle = (TextView) headView.findViewById(R.id.tv_loading_title);
        tvTime = (TextView) headView.findViewById(R.id.tv_time);

        headView.measure(0, 0);
        headViewHeight = headView.getMeasuredHeight();
        headView.setPadding(0, -headViewHeight, 0, 0);

        initFooterView();
    }

    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.listview_loadmore_footer, null);
        addFooterView(footerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentState == STATE_REFRESHING) {
                    break;
                }

                if (startY == -1) {
                    startY = (int) ev.getY();
                }

                endY = (int) ev.getY();
                int dy = endY - startY;
                int padding = dy - headViewHeight;
                if (dy > 0 && getFirstVisiblePosition() == 0) {
                    headView.setPadding(0, padding, 0, 0);

                    if (padding < 0 && currentState != STATE_RELEASE_TO_REFRESH) {
                        currentState = STATE_RELEASE_TO_REFRESH;
                        refreshState();
                    } else if (padding > 0 && currentState != STATE_PULL_TO_REFRESH) {
                        currentState = STATE_PULL_TO_REFRESH;
                        refreshState();
                    }

                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (currentState == STATE_RELEASE_TO_REFRESH) {
                    headView.setPadding(0, -headViewHeight, 0, 0);
                } else if (currentState == STATE_PULL_TO_REFRESH) {
                    headView.setPadding(0, 0, 0, 0);
                    currentState = STATE_REFRESHING;
                    refreshState();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshState() {
        switch (currentState) {
            case STATE_RELEASE_TO_REFRESH:
                tvTitle.setText("下拉刷新");
                RotateAnimation down = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                down.setDuration(200);
                down.setFillAfter(true);
                ivRedPointer.startAnimation(down);
                break;
            case STATE_PULL_TO_REFRESH:
                tvTitle.setText("释放刷新");
                RotateAnimation up = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                up.setDuration(200);
                up.setFillAfter(true);
                ivRedPointer.startAnimation(up);
                break;
            case STATE_REFRESHING:
                tvTitle.setText("正在刷新...");
                ivRedPointer.clearAnimation();
                ivRedPointer.setVisibility(INVISIBLE);
                pbLoading.setVisibility(VISIBLE);

                if (listener != null) {
                    listener.onRefresh();
                }
                break;
        }
    }

    private void setCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.CHINA);
        String time = format.format(new Date());
        tvTime.setText(time);
    }

    public void setFinish() {
        currentState = STATE_RELEASE_TO_REFRESH;
        headView.setPadding(0, -headViewHeight, 0, 0);
        tvTitle.setText("下拉刷新");
        ivRedPointer.setVisibility(VISIBLE);
        pbLoading.setVisibility(INVISIBLE);
        setCurrentTime();
    }

    public void setLoadMoreFinish() {
        isLoadingMore = false;
        footerView.findViewById(R.id.ll_footer).setVisibility(GONE);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            if (getLastVisiblePosition() >= getCount() - 3 && !isLoadingMore) {
                isLoadingMore = true;
                footerView.findViewById(R.id.ll_footer).setVisibility(VISIBLE);
                listener.onLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        super.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        onItemClickListener.onItemClick(parent, view, position - getHeaderViewsCount(), id);
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }
}
