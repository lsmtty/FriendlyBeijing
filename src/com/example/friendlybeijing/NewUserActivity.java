package com.example.friendlybeijing;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class NewUserActivity extends Activity {
	private ViewPager viewPager;
	private LinearLayout ll;
	private ArrayList<Integer> pages;
	private Button enter;
	private MyPageAdapter pageAdapter;
	private View circle_red;
	private float dx = 0;
	private float redX;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newuser_layout);
		initUI();
		initListerer();
	}

	

	private void initUI() {
		pages = new ArrayList<Integer>();
		pages.add(R.drawable.guide_1);
		pages.add(R.drawable.guide_2);
		pages.add(R.drawable.guide_3);

		viewPager = (ViewPager) findViewById(R.id.learn_vp);
		ll = (LinearLayout) findViewById(R.id.learn_ll);
		enter = (Button) findViewById(R.id.learn_bt);
		circle_red = findViewById(R.id.redCircle);
		for (int i = 0; i < pages.size(); i++) {
			LayoutParams lp = new LayoutParams(30, 30);
			if (i != 2)
				lp.rightMargin = 40;
			View view = new View(this);
			view.setLayoutParams(lp);
			view.setBackgroundResource(R.drawable.small_cricle_white);
			ll.addView(view);
		}
		pageAdapter = new MyPageAdapter();
		viewPager.setAdapter(pageAdapter);
	}
	private void initListerer() {
		// TODO Auto-generated method stub
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				if(position==pages.size()-1)
				{
					enter.setVisibility(View.VISIBLE);
					AlphaAnimation aa = new AlphaAnimation(0, 1);
					aa.setDuration(1300);
					enter.startAnimation(aa);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				//positionOffset 是 0-1的百分比
				//positionOffsetPixels是滑动的像素值
				//为满足屏幕适配，应选择百分比
				Log.i("offset", "offset:"+positionOffset);
				float startX = redX + position*dx;    
				 //***这里出现过不好理解的错误，如果直接拿redX当作移动的初始位置，如果position不是 0 ，则每次都会加一个position*dx,所以出先距离指数型增大
				circle_red.setX((int)(startX+dx*positionOffset));
				Log.i("redX", ""+circle_red.getX());
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
			}
		});
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jumpToNext();
			}
		}); 
		ll.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			//当layout执行结束才回调此方法
			@Override
			public void onGlobalLayout() {
				dx = ll.getChildAt(1).getLeft()  - ll.getChildAt(0).getLeft();
				redX = circle_red.getX();
				ll.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}
	private void jumpToNext() {
		// TODO Auto-generated method stub
		startActivity(new Intent(NewUserActivity.this, MainActivity.class));
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
		finish();
	}
	class MyPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method st
			RelativeLayout rt = (RelativeLayout) RelativeLayout.inflate(
					NewUserActivity.this, R.layout.viewpager_item, null);
			rt.setTag(position);
			Log.i("message", "createPostion:"+position);
			rt.setBackgroundResource(pages.get(position % pages.size()));
			container.addView(rt);
			return rt;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			Log.i("message", "removePostion:"+position);
			container.removeView((RelativeLayout) object);
		}
	}
}
