package com.goldemperor.StockCheck;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class ViewPageAdapter extends PagerAdapter {

    private List<View> viewList;
    public int curUpdatePager;
    public ViewPageAdapter(List<View> viewList)
    {
        this.viewList = viewList;
    }


    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //重写销毁滑动视图布局（将子视图移出视图存储集合（ViewGroup））
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView(viewList.get(position));
        ((ViewPager) container).removeView((View)object);
    }

    //重写初始化滑动视图布局（从视图集合中取出对应视图，添加到ViewGroup）
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = viewList.get(position);
        v.setTag(position);
        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(Object object) {
        View view = (View)object;
        if(curUpdatePager == (Integer)view.getTag()){
            return POSITION_NONE;
        }else{
            return POSITION_UNCHANGED;
        }
        // return super.getItemPosition(object);
    }
}
