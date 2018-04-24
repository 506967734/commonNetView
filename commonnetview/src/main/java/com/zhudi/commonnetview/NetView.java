package com.zhudi.commonnetview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 网络加载时候的公共布局
 */
public class NetView extends RelativeLayout {
    private final String TAG = "NetView";
    private Context context;
    private static final String TAG_LOADING = "TAG_LOADING";
    private static final String TAG_EMPTY = "TAG_EMPTY";
    private static final String TAG_ERROR = "TAG_ERROR";

    final String CONTENT = "type_content";
    final String LOADING = "type_loading";
    final String EMPTY = "type_empty";
    final String ERROR = "type_error";
    /**
     * 加载中的布局
     */
    private View loadingLayout;
    private int loadingLayoutResource;

    /**
     * 空数据的布局
     */
    private View emptyLayout;
    private int emptyLayoutResource;
    private ImageView ivEmpty;
    private TextView tvEmptyTitle;
    private TextView tvEmptyMessage;
    private View emptyViewClick;
    /**
     * 错误的布局
     */
    private View errorLayout;
    private int errorLayoutResource;
    private ImageView ivError;
    private TextView tvErrorTitle;
    private TextView tvErrorMessage;
    private View errorViewClick;

    private final int LOADINGWIDTH = 250;
    private final int LOADINGHEIGHT = 250;

    private final int EMPTYWIDTH = 300;
    private final int EMPTYHEIGHT = 300;

    private final int ERRORWIDTH = 300;
    private final int ERRORHEIGHT = 300;

    private int loadingProgressBarWidth;
    private int loadingProgressBarHeight;

    private int emptyImageWidth;
    private int emptyImageHeight;
    private int emptyTitleTextSize;
    private int emptyTitleTextColor;
    private int emptyContentTextSize;
    private int emptyContentTextColor;
    private int emptyClickId;
    private int emptyImageId;
    private int emptyTitleId;
    private int emptyMessageId;

    private int errorImageWidth;
    private int errorImageHeight;
    private int errorTitleTextSize;
    private int errorContentTextSize;
    private int errorTitleTextColor;
    private int errorContentTextColor;
    private int errorClickId;
    private int errorImageId;
    private int errorTitleId;
    private int errorMessageId;

    private LayoutInflater inflater;
    private LayoutParams layoutParams;
    private List<View> contentViews = new ArrayList<>();
    private String state = CONTENT;

    public NetView(Context context) {
        super(context);
    }

    public NetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.netView);

        loadingLayoutResource = typedArray.getResourceId(R.styleable.netView_loadingLayout, R.layout.loading_layout);
        emptyLayoutResource = typedArray.getResourceId(R.styleable.netView_emptyLayout, R.layout.empty_layout);
        errorLayoutResource = typedArray.getResourceId(R.styleable.netView_errorLayout, R.layout.error_layout);

        loadingProgressBarHeight = typedArray.getDimensionPixelSize(R.styleable.netView_loadingProgressBarHeight, LOADINGHEIGHT);
        loadingProgressBarWidth = typedArray.getDimensionPixelSize(R.styleable.netView_loadingProgressBarWidth, LOADINGWIDTH);

        emptyImageWidth = typedArray.getDimensionPixelSize(R.styleable.netView_emptyImageWidth, EMPTYWIDTH);
        emptyImageHeight = typedArray.getDimensionPixelSize(R.styleable.netView_emptyImageHeight, EMPTYHEIGHT);
        emptyTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.netView_emptyTitleTextSize, 14);
        emptyContentTextSize = typedArray.getDimensionPixelSize(R.styleable.netView_emptyContentTextSize, 12);
        emptyTitleTextColor = typedArray.getColor(R.styleable.netView_emptyTitleTextColor, Color.BLACK);
        emptyContentTextColor = typedArray.getColor(R.styleable.netView_emptyContentTextColor, Color.BLACK);
        emptyImageId = typedArray.getResourceId(R.styleable.netView_emptyUseImageId, 0);
        emptyTitleId = typedArray.getResourceId(R.styleable.netView_emptyUseTitleId, 0);
        emptyMessageId = typedArray.getResourceId(R.styleable.netView_emptyUseMessageId, 0);
        emptyClickId = typedArray.getResourceId(R.styleable.netView_emptyUseClickId, 0);

        errorImageWidth = typedArray.getDimensionPixelSize(R.styleable.netView_errorImageWidth, ERRORWIDTH);
        errorImageHeight = typedArray.getDimensionPixelSize(R.styleable.netView_errorImageHeight, ERRORHEIGHT);
        errorTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.netView_errorTitleTextSize, 14);
        errorContentTextSize = typedArray.getDimensionPixelSize(R.styleable.netView_errorContentTextSize, 12);
        errorTitleTextColor = typedArray.getColor(R.styleable.netView_errorTitleTextColor, Color.BLACK);
        errorContentTextColor = typedArray.getColor(R.styleable.netView_errorContentTextColor, Color.BLACK);
        errorImageId = typedArray.getResourceId(R.styleable.netView_errorUseImageId, 0);
        errorTitleId = typedArray.getResourceId(R.styleable.netView_errorUseTitleId, 0);
        errorMessageId = typedArray.getResourceId(R.styleable.netView_errorUseMessageId, 0);
        errorClickId = typedArray.getResourceId(R.styleable.netView_errorUseClickId, 0);

        typedArray.recycle();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (!child.getTag().equals(TAG_LOADING) &&
                !child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR))) {
            contentViews.add(child);
        }
    }

    /**
     * Hide all other states and show content
     */
    public void showContent() {
        switchState(CONTENT, 0, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide content and show the progress bar
     */
    public void showLoading() {
        switchState(LOADING, 0, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Show empty view when there are not data to show
     *
     * @param emptyImageResource Drawable to show
     * @param emptyTextTitle     Title of the empty view to show
     * @param emptyTextContent   Content of the empty view to show
     */
    public void showEmpty(int emptyImageResource, String emptyTextTitle, String emptyTextContent, OnClickListener onClickListener) {
        switchState(EMPTY, emptyImageResource, emptyTextTitle, emptyTextContent, onClickListener, Collections.<Integer>emptyList());
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorImageResource Drawable to show
     * @param errorTextTitle     Title of the error view to show
     * @param errorTextContent   Content of the error view to show
     * @param onClickListener    Listener of the error view button
     */
    public void showError(int errorImageResource, String errorTextTitle, String errorTextContent, OnClickListener onClickListener) {
        switchState(ERROR, errorImageResource, errorTextTitle, errorTextContent, onClickListener, Collections.<Integer>emptyList());
    }

    private void switchState(String state, int imageResource, String titleStr, String messageStr, OnClickListener onClickListener, List<Integer> skipIds) {
        this.state = state;
        switch (state) {
            case CONTENT:
                hideLoadingView();
                hideEmptyView();
                hideErrorView();

                setContentVisibility(true, skipIds);
                break;
            case LOADING:
                hideEmptyView();
                hideErrorView();

                setLoadingView();
                setContentVisibility(false, skipIds);
                break;
            case EMPTY:
                hideLoadingView();
                hideErrorView();

                setEmptyView();

                if (tvEmptyTitle != null) {
                    tvEmptyTitle.setText(TextUtils.isEmpty(titleStr) ? context.getText(R.string.empty) : titleStr);
                }
                if (tvEmptyMessage != null) {
                    tvEmptyMessage.setText(TextUtils.isEmpty(messageStr) ? context.getText(R.string.check_network_retry) : messageStr);
                }
                if (ivEmpty != null && imageResource != 0) {
                    ivEmpty.setBackgroundResource(imageResource);
                }
                if (onClickListener != null) {
                    if (emptyClickId == 0 && emptyViewClick != null) {
                        emptyViewClick.setOnClickListener(onClickListener);
                    } else if (emptyClickId != 0) {
                        View clickView = emptyLayout.findViewById(emptyClickId);
                        if (clickView != null) {
                            clickView.setOnClickListener(onClickListener);
                        }
                    }
                }
                setContentVisibility(false, skipIds);
                break;
            case ERROR:
                hideLoadingView();
                hideEmptyView();

                setErrorView();

                if (tvErrorTitle != null) {
                    tvErrorTitle.setText(TextUtils.isEmpty(titleStr) ? context.getText(R.string.error) : titleStr);
                }
                if (tvErrorMessage != null) {
                    tvErrorMessage.setText(TextUtils.isEmpty(messageStr) ? context.getText(R.string.check_network_retry) : messageStr);
                }
                if (ivError != null && imageResource != 0) {
                    ivError.setBackgroundResource(imageResource);
                }
                if (onClickListener != null) {
                    if (errorClickId == 0 && errorViewClick != null) {
                        errorViewClick.setOnClickListener(onClickListener);
                    } else if (errorClickId != 0) {
                        View clickView = errorLayout.findViewById(errorClickId);
                        if (clickView != null) {
                            clickView.setOnClickListener(onClickListener);
                        }
                    }
                }
                setContentVisibility(false, skipIds);
                break;
        }
    }

    /**
     * 设置加载布局
     */
    private void setLoadingView() {
        if (loadingLayout == null) {
            loadingLayout = inflater.inflate(loadingLayoutResource, null);
            loadingLayout.setTag(TAG_LOADING);
            if (loadingLayoutResource == R.layout.loading_layout) {
                //默认布局
                View progressBar = loadingLayout.findViewById(R.id.progressBar);
                if (loadingProgressBarWidth != 250 && loadingProgressBarHeight != 250) {
                    progressBar.getLayoutParams().width = loadingProgressBarWidth;
                    progressBar.getLayoutParams().height = loadingProgressBarHeight;
                    progressBar.requestLayout();
                }
            }
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(loadingLayout, layoutParams);
        } else {
            loadingLayout.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置空布局
     */
    private void setEmptyView() {
        if (emptyLayout == null) {
            emptyLayout = inflater.inflate(emptyLayoutResource, null);
            emptyLayout.setTag(TAG_EMPTY);
            if (emptyLayoutResource == R.layout.empty_layout) {
                //默认布局
                ivEmpty = emptyLayout.findViewById(R.id.iv);
                tvEmptyTitle = emptyLayout.findViewById(R.id.tvTitle);
                tvEmptyMessage = emptyLayout.findViewById(R.id.tvMessage);
                emptyViewClick = emptyLayout.findViewById(R.id.lyGetData);
                //默认图片
                ivEmpty.setBackgroundResource(R.drawable.svg_common_no_data);
                if (emptyImageWidth != EMPTYWIDTH && emptyImageHeight != EMPTYHEIGHT) {
                    ivEmpty.getLayoutParams().width = emptyImageWidth;
                    ivEmpty.getLayoutParams().height = emptyImageHeight;
                    ivEmpty.requestLayout();
                }
                tvEmptyTitle.setTextSize(emptyTitleTextSize);
                tvEmptyMessage.setTextSize(emptyContentTextSize);
                tvEmptyTitle.setTextColor(emptyTitleTextColor);
                tvEmptyMessage.setTextColor(emptyContentTextColor);
            } else {
                //新设置的界面
                try {
                    ivEmpty = emptyLayout.findViewById(emptyImageId);
                    tvEmptyTitle = emptyLayout.findViewById(emptyTitleId);
                    tvEmptyMessage = emptyLayout.findViewById(emptyMessageId);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(emptyLayout, layoutParams);
        } else {
            emptyLayout.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置错误布局
     */
    private void setErrorView() {
        if (errorLayout == null) {
            errorLayout = inflater.inflate(errorLayoutResource, null);
            errorLayout.setTag(TAG_ERROR);
            if (errorLayoutResource == R.layout.error_layout) {
                //默认布局
                ivError = errorLayout.findViewById(R.id.iv);
                tvErrorTitle = errorLayout.findViewById(R.id.tvTitle);
                tvErrorMessage = errorLayout.findViewById(R.id.tvMessage);
                errorViewClick = errorLayout.findViewById(R.id.lyGetData);
                //默认图片
                ivError.setBackgroundResource(R.drawable.svg_common_error);
                if (errorImageWidth != ERRORWIDTH && errorImageHeight != ERRORHEIGHT) {
                    ivError.getLayoutParams().width = errorImageWidth;
                    ivError.getLayoutParams().height = errorImageHeight;
                    ivError.requestLayout();
                }
                tvErrorTitle.setTextSize(errorTitleTextSize);
                tvErrorMessage.setTextSize(errorContentTextSize);
                tvErrorTitle.setTextColor(errorTitleTextColor);
                tvErrorMessage.setTextColor(errorContentTextColor);
            } else {
                //新设置的界面
                try {
                    ivError = errorLayout.findViewById(errorImageId);
                    tvErrorTitle = errorLayout.findViewById(errorTitleId);
                    tvErrorMessage = errorLayout.findViewById(errorMessageId);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(errorLayout, layoutParams);
        } else {
            errorLayout.setVisibility(VISIBLE);
        }
    }


    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    /**
     * 隐藏加载界面
     */
    private void hideLoadingView() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(GONE);
        }
    }

    /**
     * 隐藏空界面
     */
    private void hideEmptyView() {
        if (emptyLayout != null) {
            emptyLayout.setVisibility(GONE);
        }
    }

    /**
     * 隐藏错误界面
     */
    private void hideErrorView() {
        if (errorLayout != null) {
            errorLayout.setVisibility(GONE);
        }
    }
}
