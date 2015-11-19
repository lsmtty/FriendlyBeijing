package com.example.friendlybeijing;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import fragment.LeftFragment;
import fragment.MainFragment;

public class MainActivity extends SlidingActivity {

	private static final String MAIN_FRAGMENT = "MainFragment";
	private static final String LEFT_FRAGMENT = "LeftFragment";
	private int winWidth;
	private Display display;
	private SlidingMenu slidingMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		setBehindContentView(R.layout.left_menu_empty);
		initData();
		initUI();
		initFragment();
	}


	private void initData() {
		// TODO Auto-generated method stub
		WindowManager wmg = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		display = wmg.getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		winWidth = point.x;
	}

	private void initUI() {
		slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);  //设置触摸模式为全屏幕触摸
		slidingMenu.setBehindOffset((int)(winWidth*0.70));  //从拖出的方向设置拖出靠另一侧的最大长度 ，这里设置为离右侧70%屏幕宽
	}
	/**
	 * 初始化Fragment
	 */
	private void initFragment() {
		// TODO Auto-generated method stub
		FragmentManager fm = getFragmentManager();
		FragmentTransaction beginTransaction = fm.beginTransaction();
		//第三个参数是Fragment的tag，创建时最好给每个fragment放一个tag，这样便于查找
		beginTransaction.replace(R.id.left_rl, new LeftFragment(),LEFT_FRAGMENT);
		beginTransaction.replace(R.id.main_rl, new MainFragment(),MAIN_FRAGMENT);
		beginTransaction.commit();// Fragment中的任何处理必须再提交后才能生效，谨记
		//LeftFragment lf = (LeftFragment) fm.findFragmentByTag(LEFT_FRAGMENT);
	}
}
