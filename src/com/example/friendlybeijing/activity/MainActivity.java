package com.example.friendlybeijing.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.bean.DataInfo;
import com.example.friendlybeijing.fragment.LeftFragment;
import com.example.friendlybeijing.fragment.MainFragment;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class MainActivity extends SlidingActivity {

	public static final String MAIN_FRAGMENT = "MainFragment";
	public static final String LEFT_FRAGMENT = "LeftFragment";
	private int winWidth;
	private Display display;
	private SlidingMenu slidingMenu;
	private LeftFragment lf;
	private MainFragment mf;
	public DataInfo dataInfo;
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
		WindowManager wmg = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		display = wmg.getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		winWidth = point.x;
		
	}

	private void initUI() {
		slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); // 设置触摸模式为全屏幕触摸
		slidingMenu.setBehindOffset((int) (winWidth * 0.70)); // 从拖出的方向设置拖出靠另一侧的最大长度													// ，这里设置为离右侧70%屏幕宽
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragment() {
		// TODO Auto-generated method stub
		final FragmentManager fm = getFragmentManager();
		FragmentTransaction beginTransaction = fm.beginTransaction();
		lf = new LeftFragment();
		mf = new MainFragment();
		beginTransaction.replace(R.id.left_rl, lf,
				LEFT_FRAGMENT);
		beginTransaction.replace(R.id.main_rl,mf,
				MAIN_FRAGMENT);
		
		beginTransaction.commit();// Fragment中的任何处理必须再提交后才能生效，谨记
		//main_vp.setAdapter(mvp);
		// LeftFragment lf = (LeftFragment) fm.findFragmentByTag(LEFT_FRAGMENT);
		final String urlString = "http://192.168.23.1:8080/zhbj/categories.json";
		final HttpUtils httpUtils = new HttpUtils();
		new Thread(new Runnable() {		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				httpUtils.send(HttpMethod.GET, urlString, null, new RequestCallBack<String>(){

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						//Log.i("message", "success:"+responseInfo.result);
						/*Intent intent = new Intent();
						intent.setAction("com.example.friendlybeijing.data");
						intent.putExtra("dataInfo", responseInfo.result);
						sendBroadcast(intent);*/
						LeftFragment leftFragment = (LeftFragment) fm.findFragmentByTag(LEFT_FRAGMENT);
						leftFragment.initLeftList(responseInfo.result);
						MainFragment mainFragment= (MainFragment) fm.findFragmentByTag(MAIN_FRAGMENT);
						mainFragment.initMainList(responseInfo.result);
						dataInfo = new Gson().fromJson(responseInfo.result, DataInfo.class);
					}
					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						
						Log.i("message", "failed:"+ msg );
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(MainActivity.this, "网络连接失败,请检查网络设置", Toast.LENGTH_LONG).show();
							}
						});
					}
				});
			}
		}).start();
	}
	
}
