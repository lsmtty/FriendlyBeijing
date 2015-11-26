package com.example.friendlybeijing.viewpager;

import android.content.Context;
import android.util.AttributeSet;

import com.viewpagerindicator.TabPageIndicator;

public class DispatchEventTabPageIndicator extends TabPageIndicator {

	public DispatchEventTabPageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public DispatchEventTabPageIndicator(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
	}
	public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	};
}
