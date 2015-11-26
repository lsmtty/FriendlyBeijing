package com.example.friendlybeijing.viewpager;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.bean.DataInfo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BaseViewPager extends ViewPager {
	protected Activity maActivity;
	protected TextView title;
	protected RelativeLayout content;
	private FrameLayout fl ;
	private RelativeLayout rl;
	private DataInfo localDataInfo;
	public BaseViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		maActivity = (Activity) context;
		initView();
	}
	private void initView() {
		fl = (FrameLayout) FrameLayout.inflate(maActivity, R.layout.main_viewpage_item, null);
		rl = (RelativeLayout) fl.findViewById(R.id.rl_main);
		title = (TextView) fl.findViewById(R.id.tv_title);
		content = (RelativeLayout) fl.findViewById(R.id.v_main);
	}
	
	public DataInfo getLocalDataInfo() {
		return localDataInfo;
	}
	/**
	 * 设置数据
	 * @param dataInfo
	 */
	public void setLocalDataInfo(DataInfo dataInfo)
	{
		this.localDataInfo = dataInfo;
	}
	/**
	 * 不是一定要继承的设置content和title的函数
	 * @param text
	 * @param id
	 */
	public void initData()
	{
		
	}
	public View getView() 
	{
		return fl;
	}
	@Override
	public String toString() {
		return "BaseViewPager [maActivity=" + maActivity + ", title=" + title
				+ ", content=" + content + ", rl=" + rl + "]";
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stu
		return false;
	}
}
