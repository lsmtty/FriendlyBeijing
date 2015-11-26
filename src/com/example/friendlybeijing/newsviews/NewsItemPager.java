package com.example.friendlybeijing.newsviews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.Utils.SharedPreferencesUtils;
import com.example.friendlybeijing.Utils.WebUtils;
import com.example.friendlybeijing.activity.MainActivity;
import com.example.friendlybeijing.activity.NewsDataActivity;
import com.example.friendlybeijing.bean.DataInfo;
import com.example.friendlybeijing.bean.NewsInfo;
import com.example.friendlybeijing.bean.NewsInfo.News;
import com.example.friendlybeijing.bean.NewsInfo.TopNews;
import com.example.friendlybeijing.newsviews.FreshListView.OnFreshListener;
import com.example.friendlybeijing.viewpager.DispatchEventTabPageIndicator;
import com.example.friendlybeijing.viewpager.DispatchEventViewPager;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NewsItemPager extends BaseNewsPager {
	private static final String LOCALPATH = new String ("http://192.168.23.1:8080/zhbj");
	private DispatchEventViewPager news;
	private static List<Map<String, Object>> datas;
	private TitlePageAdapter titlePageAdapter;
	private DispatchEventTabPageIndicator indicator;
	private ImageButton ib;
	public NewsInfo localResult;
	public NewsItemPager( Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		contentView = (LinearLayout) LinearLayout.inflate(mActivity, R.layout.news_item_news, null);
		contentView.setLayoutParams(lp);
		datas = new ArrayList<Map<String,Object>>();
		news = (DispatchEventViewPager) contentView.findViewById(R.id.vp_news);
		titlePageAdapter = new TitlePageAdapter();
		news.setAdapter(titlePageAdapter);
		ib = (ImageButton) contentView.findViewById(R.id.ib_next);
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				news.setCurrentItem((news.getCurrentItem()+1));
			}
		});
		indicator = (DispatchEventTabPageIndicator) contentView.findViewById(R.id.indicator);
		indicator.setViewPager(news);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {  //当indicator与viewpager绑定时，应该设置的是indicator的listener
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				MainActivity mainUi = (MainActivity) mActivity;
				SlidingMenu slidingMenu = mainUi.getSlidingMenu();
				if(position==0)
				{
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
				}
				else {
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		indicator.setCurrentItem(0);  //设为第一页
	}

	class TitlePageAdapter extends PagerAdapter
	{
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datas.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			View view  = View.inflate(mActivity, R.layout.news_content_item, null);
			if(datas.get(position).get("data")!=null)
			{
				
				final NewsInfo localNewsInfo = (NewsInfo) datas.get(position).get("data");
				Log.i("message",localNewsInfo.data.topnews.get(0).toString());
				
				//把ViewPage当作第ListView的头View显示
				RelativeLayout header = (RelativeLayout) RelativeLayout.inflate(mActivity, R.layout.listview_header_pager, null);
				TopNewsViewPager top = (TopNewsViewPager) header.findViewById(R.id.vp_top);
				initViewPager(localNewsInfo.data.topnews,top);
				final TextView title = (TextView) header.findViewById(R.id.tv_title);
				top.addOnPageChangeListener(new OnPageChangeListener() {
					
					@Override
					public void onPageSelected(int position) {
						// TODO Auto-generated method stub
						title.setText(localNewsInfo.data.topnews.get(position).title);
					}
					
					@Override
					public void onPageScrolled(int position, float positionOffset,
							int positionOffsetPixels) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onPageScrollStateChanged(int state) {
						// TODO Auto-generated method stub
						
					}
				});
				FreshListView news_item = (FreshListView) view.findViewById(R.id.lv_news);

				news_item.addHeaderView(header);
				initListView(localNewsInfo.data.news,news_item);
				news_item.setOnFreshListener(new OnFreshListener() {
					@Override
					public void onFresh() {
						// TODO Auto-generated method stub
						getDataFromServer();
					}

					@Override
					public void onLoadMore() {
						// TODO Auto-generated method stub
						
					}
				});
				news_item.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						String ids = SharedPreferencesUtils.getString("config", mActivity, "read_ids", "");
						String localID =  localNewsInfo.data.news.get(position).id;
						if(!ids.contains(localID))  //不用去分割字符，如果用“,”分割，直接用contains就可以匹配字符串中是否存在子字符串
						{
							ids = ids + localID +",";
							SharedPreferencesUtils.setString("config", mActivity, "read_ids", ids);
							refreshView(view);  //实现局部View刷新，这个View就是点击的View对象
						}
						//页面跳转
						Intent intent = new Intent();
						intent.setClass(mActivity,NewsDataActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("url", localNewsInfo.data.news.get(position).url);
						intent.putExtras(bundle);
						mActivity.startActivity(intent);
					}
				});
			}
			
			container.addView(view);
			return view;
		}
		private void refreshView(View v)
		{
			TextView title = (TextView) v.findViewById(R.id.tv_title);
			title.setTextColor(Color.DKGRAY);
		}
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return ((String )(datas.get(position).get("title"))).toUpperCase();
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View)object);
		}
	}
	
	
	/**
	 * 从数据库拉取数据
	 */
	protected void getDataFromServer() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setData(DataInfo dataInfo) {
		// TODO Auto-generated method stub
		super.setData(dataInfo);
		for (int i = 0;i<dataInfo.data.get(0).children.size();i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", dataInfo.data.get(0).children.get(i).title);
			getNewsInfo(LOCALPATH+dataInfo.data.get(0).children.get(i).url,i);
			//map.put("data", getNewsInfo(LOCALPATH+dataInfo.data.get(0).children.get(i).url));
			datas.add(map);
			titlePageAdapter.notifyDataSetChanged();
			indicator.notifyDataSetChanged();
		}
	}
	/**
	 * 子页面ListView初始化
	 */
	public void initListView(final ArrayList<News> news,ListView news_item)
	{
		class NewsAdapter extends BaseAdapter
		{
			private BitmapUtils utils;
			public NewsAdapter() {
				// TODO Auto-generated constructor stub
				utils = new BitmapUtils(mActivity);
				utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
			}
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return news.size();
			}
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return news.get(position);
			}
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ViewHolder holder;
				if(convertView==null)
				{
					convertView = View.inflate(mActivity,R.layout.listview_content_layout, null);
					holder = new ViewHolder();
					holder.iv = (ImageView) convertView.findViewById(R.id.news_iv);
					holder.title = (TextView) convertView.findViewById(R.id.tv_title);
					holder.date = (TextView) convertView.findViewById(R.id.tv_date);
					convertView.setTag(holder);
				}
				else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.title.setText(news.get(position).title);
				holder.date.setText(news.get(position).pubdate);
				String url = news.get(position).listimage;
				if(url.startsWith(WebUtils.LOCALIP))
				{
					String target = (String)url.subSequence(WebUtils.LOCALIP.length(),url.length());
					url = WebUtils.CELLPHONEIP + target;
				}
				utils.display(holder.iv, url);
				return convertView;
			}
			class ViewHolder
			{
				public ImageView iv;
				public TextView title;
				public TextView date;
			}
		}
		news_item.setAdapter(new NewsAdapter());
	}
	/**
	 * 子页面ViewPager初始化
	 * @param topnews
	 * @param top
	 */
	public void initViewPager(final ArrayList<TopNews> topnews,TopNewsViewPager top) {
		// TODO Auto-generated method stub
		/**
		 * 子页面TopNewsAdapter
		 * @author 思敏
		 *
		 */
		class TopNewsAdapter extends PagerAdapter
		{
			private BitmapUtils utils;
			/**
			 * 未满足不同的viewPager数据量多少的不同，这样做是否合适？
			 */
			public TopNewsAdapter()
			{
				utils = new BitmapUtils(mActivity);
				utils.configDefaultLoadingImage(R.drawable.topnews_item_default);
			}
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return topnews.size();
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				// TODO Auto-generated method stub
				return view==object;
			}
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				ImageView iv = new ImageView(mActivity);
				iv.setImageResource(R.drawable.topnews_item_default);
				iv.setScaleType(ScaleType.FIT_XY);  //符合控件大小，完整填充控件
				//用BitmapUtils填充imageView
				String url = topnews.get(position).topimage;
				if(url.startsWith(WebUtils.LOCALIP))
				{
					String target = (String)url.subSequence(WebUtils.LOCALIP.length(),url.length());
					url = WebUtils.CELLPHONEIP + target;
				}
				utils.display(iv,url);
				container.addView(iv);
				return iv;
			}
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
				container.removeView((View)object);
			}
		}
		TopNewsAdapter topNewsAdapter = new TopNewsAdapter();
		top.setAdapter(topNewsAdapter);
	}
	
	/**
	 * 通过url，使用httpUtils下载对应页面数据
	 * @param url
	 * @return
	 */
	public void getNewsInfo(String url,final int i)
	{
		final HttpUtils httpUtils = new HttpUtils();
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				/**
				 * 下载结束更新数据，此处才应该使函数输出数据
				 */
				localResult = (NewsInfo)(new Gson().fromJson((String)msg.obj, NewsInfo.class));
				datas.get(i).put("data", localResult);
				super.handleMessage(msg);
			}
		};
		httpUtils.send(HttpMethod.GET,url,null, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				//Log.i("message", "failed:"+ responseInfo.result );
				handler.sendMessage(handler.obtainMessage(1,responseInfo.result));
				localResult = (NewsInfo)(new Gson().fromJson(responseInfo.result, NewsInfo.class));
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				//Log.i("message", "failed:"+ msg );
				mActivity.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(mActivity, "网络连接失败,请检查网络设置", Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}

}
