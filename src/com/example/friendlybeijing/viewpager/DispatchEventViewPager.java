package com.example.friendlybeijing.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DispatchEventViewPager extends ViewPager {

	public DispatchEventViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public DispatchEventViewPager(Context context,AttributeSet set) {
		// TODO Auto-generated constructor stub
		super(context, set);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(getCurrentItem()!=0)
		{
			getParent().requestDisallowInterceptTouchEvent(true);
		}
		else {
			getParent().requestDisallowInterceptTouchEvent(false);  //拦截
		}
		return super.dispatchTouchEvent(ev);
	}
}
