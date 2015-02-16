package com.djk_shop.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.djk_shop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by liz on 2015/1/20.
 * author : liz
 * desc : Android开发之ViewPager实现轮播图（轮播广告）效果的自定义View
 * detail:既支持自动轮播页面也支持手势滑动切换页面
 * address : http://www.it165.net/pro/html/201406/16227.html
 */
public class SlideShowView extends FrameLayout {
    //轮播图片数量
    private final static int IMAGE_COUNT = 5;
    //自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
    //自动轮播启动开关
    private final static boolean isAutoPlay = true;

    //自定义轮播图的资源ID
    private int[] imagesResourceIds;
    //放轮播图片的ImageView的list
    private List<ImageView> imageViewList;
//    放置圆点的View的List
    private List<View> dotViewsList;

    private ViewPager viewPager;
    //当前轮播页
    private int currentItem = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;
    //handler
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
    };
    public SlideShowView(Context context) {
        super(context,null);
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData();
        initUI(context);
        if( isAutoPlay ){
            startPlay();    
        }
    }

    //初始化相关数据
    private void initData() {
        imagesResourceIds = new int[]{
            R.drawable.lunbo1,R.drawable.lunbo2,R.drawable.lunbo3,R.drawable.lunbo4,R.drawable.lunbo5
        };
        imageViewList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
    }
    //初始化UI
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow,this,true);
        for(int imageId :imagesResourceIds){
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageId);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewList.add(imageView);
        }
        dotViewsList.add(findViewById(R.id.v_dot1));
        dotViewsList.add(findViewById(R.id.v_dot2));
        dotViewsList.add(findViewById(R.id.v_dot3));
        dotViewsList.add(findViewById(R.id.v_dot4));
        dotViewsList.add(findViewById(R.id.v_dot5));

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setFocusable(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    private void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(),1,4, TimeUnit.SECONDS);
    }
    //执行轮播图切换任务
    private class SlideShowTask implements Runnable {
        @Override
        public void run() {
            synchronized(viewPager){
                currentItem = (currentItem+1)%imageViewList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }
    //销毁ImageView资源，回收内存
    private void destroyBitmaps(){
        for( int i=0;i<IMAGE_COUNT;i++){
            ImageView imageView = imageViewList.get(i);
            Drawable drawable = imageView.getDrawable();
            if( drawable != null ){
                //解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }


    /**
     * ViewPager 的监听器
     * 当ViewPager中的页面的状态发生改变时被调用
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener{
        boolean isAutoPlay = false;
        @Override
        public void onPageScrolled(int i, float v, int i2) {
        }
        @Override
        public void onPageSelected(int pos) {
            currentItem = pos;
            for( int i = 0;i<dotViewsList.size();i++){
                if( i == pos ){
                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.big_dot);
                }else{
                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.little_dot);
                }
            }
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
            //此方法是在状态改变的时候调用，
            // 其中arg0这个参数有三种状态（0，1，2）
            // arg0 ==1  默示正在滑动中，
            // arg0==2   默示滑动完毕了，界面切换中
            // arg0==0   默示滑动结束，即切换完毕。
            switch (arg0){
                case 1: isAutoPlay = false;
                    break;
                case 2: isAutoPlay = true;
                    break;
                case 0 :// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if(viewPager.getCurrentItem() == viewPager.getAdapter().getCount()-1 &&  !isAutoPlay){
                        viewPager.setCurrentItem(0);
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    }else if(viewPager.getCurrentItem() == 0 && !isAutoPlay){
                        viewPager.setCurrentItem( viewPager.getAdapter().getCount()-1 );
                    }
                    break;
            }
        }
    }




    /**
     * 填充ViewPager的页面适配器
     * @author liz
     */
    private class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            ((ViewPager)container).removeView(imageViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            ((ViewPager)container).addView(imageViewList.get(position));
            return imageViewList.get(position);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void finishUpdate(ViewGroup container) {

        }

        @Override
        public void startUpdate(ViewGroup container) {

        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {

        }
    }
}
