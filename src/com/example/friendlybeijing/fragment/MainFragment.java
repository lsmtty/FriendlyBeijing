package com.example.friendlybeijing.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.bean.DataInfo;
import com.example.friendlybeijing.viewpager.BaseViewPager;
import com.example.friendlybeijing.viewpager.GovaffairsViewpager;
import com.example.friendlybeijing.viewpager.MainPageViewpager;
import com.example.friendlybeijing.viewpager.NewsCenterViewpager;
import com.example.friendlybeijing.viewpager.NoScrollViewPager;
import com.example.friendlybeijing.viewpager.SettingViewpager;
import com.example.friendlybeijing.viewpager.WiseServerViewpager;
import com.google.gson.Gson;

public class MainFragment extends MyBaseFragment {

	private List<BaseViewPager> pagers;
	private RadioGroup radioGroup;
	private View v;
	private NoScrollViewPager vp;
	private MainViewPagerAdapter mvp;
	private DataInfo dataInfo;
	@Override
	public View initViews() {
		v = mactivity.getLayoutInflater().inflate(R.layout.main_menu_fragment, null);
		radioGroup = (RadioGroup) v.findViewById(R.id.rg_navigator);
		vp = (NoScrollViewPager) v.findViewById(R.id.vp_main);
		vp.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		radioGroup.check(R.id.rb_main_page);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_main_page:
					vp.setCurrentItem(0, false);
					break;
				case R.id.rb_news_center:
					vp.setCurrentItem(1, false);
					break;
				case R.id.rb_wise_server:
					vp.setCurrentItem(2, false);
					break;
				case R.id.rb_setting:
					vp.setCurrentItem(3, false);
					break;
				case R.id.rb_govaffairs:
					vp.setCurrentItem(4, false);
					break;
				default:
					break;
				}
				
			}
		});
		return v;
	}
	/**
	 * 获得新闻子页面
	 * @return
	 */
	public BaseViewPager getNewsPager()
	{
		return pagers.get(1);
	}
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		pagers = new ArrayList<BaseViewPager>();
		pagers.add(new MainPageViewpager(mactivity));
		pagers.add(new NewsCenterViewpager(mactivity));
		pagers.add(new WiseServerViewpager(mactivity)); 
		pagers.add(new GovaffairsViewpager(mactivity));
		pagers.add(new SettingViewpager(mactivity));
		mvp = new MainViewPagerAdapter();
		vp.setAdapter(mvp);
		super.initData();
	}
	
	class MainViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return object == view;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			BaseViewPager itemPager = pagers.get(position);
			itemPager.initData();
			container.addView(itemPager.getView());
			return itemPager.getView();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((FrameLayout) object);
		}
	}

	public void initMainList(String result) {
		dataInfo = new Gson().fromJson(result, DataInfo.class);
		for (BaseViewPager baseViewPager : pagers) {
			baseViewPager.setLocalDataInfo(dataInfo);
			mvp.notifyDataSetChanged();
		}
		
	}
}
