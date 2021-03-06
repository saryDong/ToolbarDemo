package com.example.abu.toolbardemo.wecome.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * created by 董长峰 on 2018/4/12.
 */
public class GuideViewPaperAdapter extends PagerAdapter {
    private List<View> views;

    public GuideViewPaperAdapter(List<View> views) {
        super();
        this.views = views;
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //删除当前页卡
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //官方提示这样写判断是否由对象生成view
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //实例化页卡，添加一个页卡
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }
}