package com.woodman.emptyviewlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by miss on 2018/5/16.
 */

public class EmptyViewLayout extends LinearLayout {


    private View mView;
    private ViewStub mLoaddingStub;     //加载中的ViewStub
    private ViewStub mOtherStub;        //其他类型的ViewStub
    private ImageView mImgOtherImage;
    private TextView mTvOtherContent;
    private View mLoddingView = null;   //加载视图的根View
    private View mOtherView = null;
    private int viewType;               //当前视图的类型
    private ProgressBar mLoaddingBar;       //加载进度条
    private TextView mTvLoaddingText;       //加载的文本
    private OnErrorViewClickListen mOnErrorViewClickListen;

    //常量
    public static final int LOADDING = 0;           //加载中
    public static final int LOADDING_FAIL = 1;      //加载失败
    public static final int NO_NET = 2;             //没有网络
    public static final int TIME_OUT = 3;           //连接超时
    public static final int LOADDING_SUCCESS = 4;   //加载成功

    //自定义属性
    private String loaddingText;
    private int loaddingTextColor;
    private int loaddingTextSize;
    private int loaddingFailImage;
    private String loaddingFailText;
    private int loaddingFailTextColor;
    private int loaddingFailTextSize;
    private int noNetImage;
    private String noNetText;
    private int nNetTextColor;
    private int noNetTextSize;
    private int timeOutImage;
    private String timeOutText;
    private int timeOutTextColor;
    private int timeOutTextSize;
    private int textAndImageMargin;


    public EmptyViewLayout(@NonNull Context context) {
        this(context, null);
    }

    public EmptyViewLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyViewLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mView = LayoutInflater.from(context).inflate(R.layout.empty_view, this);
        initAttrs(context, attrs);
        initWidget();
        showView(LOADDING);
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyViewLayout);
        loaddingText = typedArray.getString(R.styleable.EmptyViewLayout_loaddingText);
        loaddingTextColor = typedArray.getColor(R.styleable.EmptyViewLayout_loaddingTextColor, Color.RED);
        loaddingTextSize = typedArray.getDimensionPixelSize(R.styleable.EmptyViewLayout_loaddingTextSize, 15);
        loaddingFailImage = typedArray.getResourceId(R.styleable.EmptyViewLayout_loaddingFailImage, R.drawable.loadding_fail);
        loaddingFailText = typedArray.getString(R.styleable.EmptyViewLayout_loaddingFailText);
        loaddingFailTextColor = typedArray.getColor(R.styleable.EmptyViewLayout_loaddingFailTextColor, Color.BLACK);
        loaddingFailTextSize = typedArray.getDimensionPixelSize(R.styleable.EmptyViewLayout_loaddingFailTextSize, 15);
        noNetImage = typedArray.getResourceId(R.styleable.EmptyViewLayout_noNetImage, R.drawable.no_net);
        noNetText = typedArray.getString(R.styleable.EmptyViewLayout_noNetText);
        nNetTextColor = typedArray.getColor(R.styleable.EmptyViewLayout_nNetTextColor, Color.BLACK);
        noNetTextSize = typedArray.getDimensionPixelSize(R.styleable.EmptyViewLayout_noNetTextSize, 15);
        timeOutImage = typedArray.getResourceId(R.styleable.EmptyViewLayout_timeOutImage, R.drawable.time_out);
        timeOutText = typedArray.getString(R.styleable.EmptyViewLayout_timeOutText);
        timeOutTextColor = typedArray.getColor(R.styleable.EmptyViewLayout_timeOutTextColor, Color.BLACK);
        timeOutTextSize = typedArray.getDimensionPixelSize(R.styleable.EmptyViewLayout_timeOutTextSize, 15);
        textAndImageMargin = typedArray.getDimensionPixelSize(R.styleable.EmptyViewLayout_textAndImageMargin, 20);
        typedArray.recycle();
    }

    private void initWidget() {
        mLoaddingStub = findViewById(R.id.view_stub_empty_loadding);
        mOtherStub = findViewById(R.id.view_stub_empty_other);
    }


    /**
     * 显示视图
     * @param viewType 视图类型
     */
    public void showView(int viewType) {
        this.viewType = viewType;
        switch (viewType) {
            case LOADDING:
                showLoaddingView();
                setLoaddingData();
                break;
            case LOADDING_FAIL:
                showOtherView();
                setLoaddingFailData();
                break;
            case NO_NET:
                showOtherView();
                setNoNetlData();
                break;
            case TIME_OUT:
                showOtherView();
                setTimeOutData();
                break;
            default:
            case LOADDING_SUCCESS:
                if(null != mLoaddingStub){
                    mLoaddingStub.setVisibility(GONE);
                }
                if(null != mOtherStub){
                    mOtherStub.setVisibility(GONE);
                }
                break;
        }
    }



    private void setTimeOutData() {
        mImgOtherImage.setBackgroundResource(timeOutImage);
        mTvOtherContent.setText(timeOutText);
        mTvOtherContent.setTextSize(timeOutTextSize);
        mTvOtherContent.setTextColor(timeOutTextColor);

    }

    private void setNoNetlData() {
        mImgOtherImage.setBackgroundResource(noNetImage);
        mTvOtherContent.setText(noNetText);
        mTvOtherContent.setTextSize(noNetTextSize);
        mTvOtherContent.setTextColor(nNetTextColor);
    }

    private void setLoaddingFailData() {
        mImgOtherImage.setBackgroundResource(loaddingFailImage);
        mTvOtherContent.setText(loaddingFailText);
        mTvOtherContent.setTextSize(loaddingFailTextSize);
        mTvOtherContent.setTextColor(loaddingTextColor);
    }


    /**
     * 显示其他视图
     *   加载中，没有网络，连接超时
     */
    public void showOtherView(){
        if (mOtherView == null) {
            mOtherView = mOtherStub.inflate();
            mImgOtherImage = mOtherView.findViewById(R.id.other_image);
            mTvOtherContent = mOtherView.findViewById(R.id.other_content);
            setTextAndImageMargin();
            setOtherViewListen();
        }else {
            mOtherStub.setVisibility(VISIBLE);
        }
        if (null != mLoddingView) {
            mLoaddingStub.setVisibility(GONE);
        }
    }



    /**
     * 设置其他视图的点击事件监听
     * @param
     */
    private void setOtherViewListen() {
        mImgOtherImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnErrorViewClickListen){
                    mOnErrorViewClickListen.clickImage(viewType);
                }
            }
        });
        mTvOtherContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnErrorViewClickListen){
                    mOnErrorViewClickListen.clickText(viewType);
                }
            }
        });

    }


    /**
     * 显示加载视图
     */
    public void showLoaddingView(){
        if (mLoddingView == null) {
            mLoddingView = mLoaddingStub.inflate();
            mLoaddingBar = mLoddingView.findViewById(R.id.loadding_bar);
            mTvLoaddingText = mLoddingView.findViewById(R.id.loadding_text);
            LayoutParams layoutParams = (LayoutParams) mTvLoaddingText.getLayoutParams();
            layoutParams.topMargin  = textAndImageMargin;
            mTvLoaddingText.setLayoutParams(layoutParams);
            setLoaddingData();
            setLoaddingListen();
        }else {
            mLoaddingStub.setVisibility(VISIBLE);
        }
        if (null != mOtherView) {
            mOtherStub.setVisibility(GONE);
        }
    }

    private void setLoaddingListen() {
        mLoaddingBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnErrorViewClickListen){
                    mOnErrorViewClickListen.clickImage(viewType);
                }
            }
        });
        mTvLoaddingText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnErrorViewClickListen){
                    mOnErrorViewClickListen.clickText(viewType);
                }
            }
        });
    }


    /**
     * 设置加载中的数据
     */
    private void setLoaddingData() {
        mTvLoaddingText.setTextColor(loaddingTextColor);
        mTvLoaddingText.setText(loaddingText);
        mTvLoaddingText.setTextSize(loaddingTextSize);
    }


    /**
     * 设置文本和图片之间的间距
     */
    private void setTextAndImageMargin() {
        LayoutParams layoutParams = (LayoutParams) mTvOtherContent.getLayoutParams();
        layoutParams.topMargin  = textAndImageMargin;
        mTvOtherContent.setLayoutParams(layoutParams);
    }

    /** 设置视图中的图片和文字点击事件监听
     * @param mOnErrorViewClickListen
     */
    public void setErrorViewClickListen(OnErrorViewClickListen mOnErrorViewClickListen){
        this.mOnErrorViewClickListen = mOnErrorViewClickListen;
    }




}
