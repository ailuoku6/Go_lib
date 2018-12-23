package com.ailuoku6.golib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林冠宏 on 2016/7/11.
 *
 * 真正的仿微信网页打开的进度条
 *
 * 两种方式
 *     1，setLayoutParams 的形式
 *     2，动画形式(推荐)
 *
 * 下面的所有属性都可以自己采用 get set 来自定义
 *
 */

public class SlowlyProgressBar {

    private static final int StartAnimation = 0x12;

    private Handler handler;
    private View view;
    private ProgressBar progressBar;

    /** 当前的位移距离和速度 */
    private int thisWidth = 0;
    private int thisSpeed = 0;

    private int progress = 0;  /** 当前的进度长度 */
    private int record = 0;    /** 移动单位 */
    private int width = 10;    /** 10dp each time */
    private int height = 3;    /** 3dp */

    private boolean isStart = false;

    private int phoneWidth = 0; /** 屏幕宽度 */
    private int i = 0;

    /** 每次的移动记录容器，位移对应每帧时间 */
    private List<Integer> progressQuery = new ArrayList<>();
    private List<Integer> speedQuery    = new ArrayList<>();

    /** 第一种 */
    public SlowlyProgressBar(View view, int phoneWidth) {
        initHandler();
        this.phoneWidth = phoneWidth;
        this.view = view;
    }

    /** 第二种 */
    public SlowlyProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    /** 善后工作，释放引用的持有，方能 gc 生效 */
    public void destroy(){
        if(progressQuery!=null){
            progressQuery.clear();
            progressQuery = null;
        }
        if(speedQuery!=null){
            speedQuery.clear();
            speedQuery = null;
        }
        view = null;
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    /** -------------------------------------------第二种方法-------------------------------------- */
    /** 在 WebViewClient onPageStarted 调用 */
    public void onProgressStart(){
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setAlpha(1.0f);
    }

    /** 在 WebChromeClient onProgressChange 调用 */
    public void onProgressChange(
            int newProgress
    ){
        int currentProgress = progressBar.getProgress();
        if (newProgress >= 100 && !isStart) {
            /** 防止调用多次动画 */
            isStart = true;
            progressBar.setProgress(newProgress);
            /** 开启属性动画让进度条平滑消失*/
            startDismissAnimation(progressBar.getProgress());
        } else {
            /** 开启属性动画让进度条平滑递增 */
            startProgressAnimation(newProgress,currentProgress);
        }
    }
    /** -------------------------------第二种结束---------------------------- */

    public void setProgress(int progress){
        if(progress>100 || progress <= 0){ /** 不能超过100 */
            return;
        }
        /** 每次传入的 width 应该是包含之前的数值,所以下面要减去 */
        /** 下面记得转化比例，公式 （屏幕宽度 * progress / 100） */
        this.width = (progress * phoneWidth)/100;

        /** lp.width 总是获取前一次的 大小 */
        /** 移动 100px 时的速度一次倍率 是 2 */
        int size = progressQuery.size();
        if(size != 0){
            size = progressQuery.get(size-1);
        }
        Log.d("zzzzz","width - size = "+(width - size));
        /** 计算倍率，2/100 = x/width */
        int distance = width - size;
        int speedTime;
        if(distance<=100){
            speedTime = 2;
        }else{
            speedTime = (int) ((2 * distance)/100.0);
        }
        /** 添加 */
        progressQuery.add(this.width);
        speedQuery.add(speedTime);
        /** 开始 */
        if(!isStart){
            isStart = true;
            handler.sendEmptyMessage(StartAnimation);
        }
    }

    public SlowlyProgressBar setViewHeight(int height){
        this.height = height;
        return this;
    }

    private void initHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case StartAnimation:
                        /** 提取队列信息 */
                        if(progress >= thisWidth){ /** 如果已经跑完，那么移出 */
                            if(progressQuery.size() == i){
                                Log.d("zzzzz","break");
                                if(progress >= 100){ /** 全部走完，隐藏进度条 */
                                    view.setVisibility(View.INVISIBLE);
                                }
                                isStart = false;
                                break;
                            }
                            Log.d("zzzzz", "size is " + progressQuery.size());
                            thisWidth = progressQuery.get(i);
                            thisSpeed = speedQuery.get(i);
                            i ++;
                        }
                        move(thisSpeed,view.getLayoutParams().width);
                        Log.d("zzzzz", "send 100 "+thisSpeed);
                        /** 发信息的延时长度并不会影响速度 */
                        handler.sendEmptyMessageDelayed(StartAnimation,1);
                        break;
                }
            }
        };
    }

    /** 移动 */
    private void move(int speedTime,int lastWidth){
        if(speedTime > 9){
            speedTime = 9; /** 控制最大倍率 */
        }
        /** 乘 3 是纠正误差 */
        /** 原因是：px 和 db 不是对比的，个人十分建议不乘3，采用函数适配转换，下面提供个函数 */
        progress = (record * speedTime);
        /** 纠正 */
        if(progress >= lastWidth){
            view.setLayoutParams(new RelativeLayout.LayoutParams(progress,height*3));
        }else{
            Log.d("zzzzz","hit "+progress+"---"+lastWidth);
        }
        record ++;
    }

    /** 下面是动画的形式 */
    /**
     * progressBar 进度缓慢递增，300ms/次
     */
    private void startProgressAnimation(int newProgress,int currentProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, newProgress);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator()); /** 减速形式的加速器，个人喜好 */
        animator.start();
    }

    private void startDismissAnimation(final int progress) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(progressBar, "alpha", 1.0f, 0.0f);
        anim.setDuration(1500);  // 动画时长
        anim.setInterpolator(new DecelerateInterpolator());     // 减速

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();      // 0.0f ~ 1.0f
                int offset = 100 - progress;
                progressBar.setProgress((int) (progress + offset * fraction));
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.GONE);
                isStart = false;
            }
        });
        anim.start();
    }

}
