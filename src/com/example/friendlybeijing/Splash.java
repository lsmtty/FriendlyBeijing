package com.example.friendlybeijing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;

public class Splash extends Activity {

	private FrameLayout splash;
	private RotateAnimation ra;
	private ScaleAnimation sa;
	private AnimationSet as;
	private AlphaAnimation aa;
	private Handler Handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			jumptoNext();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		initUI();
		initListener();
	}
	private void initListener() {
		// TODO Auto-generated method stub
		as.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Handler.sendEmptyMessage(1);
			}
		});
	}
	private void initUI() {
		as = new AnimationSet(false);
		splash = (FrameLayout) findViewById(R.id.splash_fl);
		ra = new RotateAnimation(-360, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		aa = new AlphaAnimation(0, 1);
		ra.setDuration(1400);
		ra.setFillAfter(true);
		sa.setDuration(1400);
		sa.setFillAfter(true);
		aa.setDuration(1400);
		aa.setFillAfter(true);
		as.addAnimation(ra);
		as.addAnimation(sa);
		
		as.addAnimation(aa);
		splash.startAnimation(as);
		
	}
	private void jumptoNext() {
		// TODO Auto-generated method stub
		/*if (SharedPreferencesUtils.getBoolean("config", Splash.this,
				"first_enter", true)) {
			SharedPreferencesUtils.setBoolean("config", Splash.this,
					"first_enter", false);
			// 进入新手引导页
			startActivity(new Intent(Splash.this, NewUserActivity.class));
		} else {
			// 进入主页面
			startActivity(new Intent(Splash.this, MainActivity.class));
		}*/
		startActivity(new Intent(Splash.this, NewUserActivity.class));
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
		finish();
	}
}
