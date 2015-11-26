package com.example.friendlybeijing.fragment;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.activity.MainActivity;
import com.example.friendlybeijing.bean.DataInfo;
import com.example.friendlybeijing.viewpager.NewsCenterViewpager;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class LeftFragment extends MyBaseFragment {

	private View view;
	private ListView lv;
	private int currentPos;
	private static LeftListViewAdapter la;
	public static ArrayList<String> leftTags;
	@Override
	public View initViews() {
		/*IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.friendlybeijing.data");
		mactivity.registerReceiver(new MyDataReceiver(), intentFilter);*/
		view = mactivity.getLayoutInflater().inflate(R.layout.left_menu_fragment, null);
		lv = (ListView) view.findViewById(R.id.lv_left_meau);
		leftTags = new ArrayList<String>();
		la = new LeftListViewAdapter();
		lv.setAdapter(la);
		initListener();
		return view;
	}


	private void initListener() {
		// TODO Auto-generated method stub
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i("message", "position:"+position);
				currentPos = position;
				MainFragment mf = (MainFragment) getFragmentManager().findFragmentByTag(MainActivity.MAIN_FRAGMENT);
				((NewsCenterViewpager)mf.getNewsPager()).transToItem(position);
				la.notifyDataSetChanged();
				toggleSlidingMenu();
				//响应点击事件，收缩左边框
				
			}
		});
	}
	/**
	 * 从Mainactivity操控侧边栏的显示和隐藏
	 */
	protected void toggleSlidingMenu() 
	{
		MainActivity mainUi = (MainActivity) mactivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();  //获取滑动目录
		slidingMenu.toggle();   //显示时隐藏，隐藏时显示
	}

	/**
	 * 加载数据
	 * @param obj 
	 */
	public static void initLeftList(String obj) 
	{
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Log.i("message", "obj:"+obj);
		DataInfo dataInfo = gson.fromJson(obj, DataInfo.class);
		
		for(int i = 0;i<=dataInfo.data.size()-1;i++)
		{
			leftTags.add(dataInfo.data.get(i).title);
		}
		la.notifyDataSetChanged();
	}
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	class LeftListViewAdapter  extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return leftTags.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return leftTags.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = View.inflate(mactivity, R.layout.left_meau_itemt, null);
			TextView tv = (TextView) v.findViewById(R.id.tv_title);
			if(position==currentPos)
			{
				tv.setEnabled(true);
			}
			else {
				tv.setEnabled(false);
			}
			tv.setText(getItem(position));
			return v;
		}	
	}
	/*class MyDataReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals("com.example.friendlybeijing.data"))
			{
				initLeftList(intent.getStringExtra("dataInfo"));
			}
		}
	}*/
}
