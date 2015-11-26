package com.example.friendlybeijing.newsviews;

import android.content.Context;
import android.mtp.MtpObjectInfo;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TopNewsViewPager extends ViewPager {

	private float startX;
	private float startY;

	public TopNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public TopNewsViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true); // 当按下时不让祖先拦截事件
			startX = ev.getRawX();
			startY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			float endX = ev.getRawX();
			float endY = ev.getRawY();
			if(endX - startX > 0) //向右滑动，拉动左边
			{
				if(this.getCurrentItem()!=0)
					getParent().requestDisallowInterceptTouchEvent(true);
			}
			else if(endX - startX< 0) //向左滑动，拉出右边
			{
				if(getCurrentItem()!=getAdapter().getCount()-1)  //这里要用Adapter 的 getCount,如果用getChildCount,是viewpager的控件子类
					getParent().requestDisallowInterceptTouchEvent(true);
			}
			else if(endY - startY >0)  //向下滑动，拉出上边
			{	
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			else {
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			startX = -1;
			startY = -1;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
