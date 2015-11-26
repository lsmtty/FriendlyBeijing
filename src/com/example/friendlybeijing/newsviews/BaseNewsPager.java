package com.example.friendlybeijing.newsviews;

import com.example.friendlybeijing.bean.DataInfo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public abstract class BaseNewsPager 
{
	protected Activity mActivity;
	protected DataInfo dataInfo ;
	protected RelativeLayout.LayoutParams lp;
	protected View contentView;
	public BaseNewsPager(Context context) {
		// TODO Auto-generated constructor stub
		this.mActivity = (Activity) context;
		lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		initViews();
	}
	/**
	 * 用dataInfo来实现子类contentView 
	 */
	public abstract void initViews();
	/**
	 * 返回contentView
	 * @return
	 */
	public View getView() {
		return contentView;
	}
	
	public void setData(DataInfo dataInfo)
	{
		this.dataInfo = dataInfo;
	}
}
