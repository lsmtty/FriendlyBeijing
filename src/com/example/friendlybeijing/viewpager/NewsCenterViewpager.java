package com.example.friendlybeijing.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.bean.DataInfo;
import com.example.friendlybeijing.newsviews.BaseNewsPager;
import com.example.friendlybeijing.newsviews.NewsItemPager;
import com.example.friendlybeijing.newsviews.PicsItemPager;
import com.example.friendlybeijing.newsviews.ThemeItemPager;
import com.example.friendlybeijing.newsviews.TransactionItemPager;

public class NewsCenterViewpager extends BaseViewPager {
	private RelativeLayout newsContent;
	private List<BaseNewsPager> pagers;
	private RelativeLayout item_content;
	private Handler handler;
	public NewsCenterViewpager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		title.setText("新闻");
		
		initPagers();
		item_content = (RelativeLayout) RelativeLayout.inflate(maActivity, R.layout.newscenterpager, null);
		newsContent = (RelativeLayout) item_content.findViewById(R.id.rl_content);
		transToItem(0);//开始默认切换到第一个页面
		this.content.addView(item_content);
		super.initData();
	}
	
	/**
	 * 初始化新闻子页面列表
	 */
	private void initPagers() {
		pagers = new ArrayList<BaseNewsPager>();
		pagers.add(new NewsItemPager( maActivity));
		pagers.add(new ThemeItemPager( maActivity));
		pagers.add(new PicsItemPager(maActivity));
		pagers.add(new TransactionItemPager(maActivity));
		long currentTimeMillis = System.currentTimeMillis();
		
		while(handler==null)
		{
			if(System.currentTimeMillis()-currentTimeMillis>3000)
			{
				return;
			}
		}
		handler.sendEmptyMessage(1);
	}
	public void notifyDataGet(final DataInfo dataInfo) {
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 1:
					for (BaseNewsPager i : pagers) {
						i.setData(dataInfo);
					}
					break;
				case 2:
					Log.e("message", "网络出错");
					break;
				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
	}
	/**
	 * 切换到新闻页面的子页面
	 * @param item  子页面id
	 */
	public void transToItem(int item)
	{
		if(newsContent.getChildCount()==1)
		{
			newsContent.removeAllViews();
			newsContent.addView(pagers.get(item).getView());
		}
		else {
			newsContent.addView(pagers.get(item).getView());
		}
	}
	
	@Override
	public void setLocalDataInfo(DataInfo dataInfo) {
		// TODO Auto-generated method stub
		super.setLocalDataInfo(dataInfo);
		notifyDataGet(dataInfo);
	}
}
