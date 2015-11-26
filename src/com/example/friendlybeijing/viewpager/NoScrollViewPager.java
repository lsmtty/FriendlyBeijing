package com.example.friendlybeijing.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 不重写这个方法，无法在在xml文件中实例化
	 * @param context
	 * @param set
	 */
	public NoScrollViewPager(Context context,AttributeSet set) {
		// TODO Auto-generated constructor stub
		super(context, set);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}
}
