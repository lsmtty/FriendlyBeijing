package com.example.friendlybeijing.viewpager;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.bean.DataInfo;

import android.content.Context;
import android.view.View;

public class MainPageViewpager extends BaseViewPager {

	public MainPageViewpager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		title.setText("智慧北京");
		View item_content = View.inflate(maActivity, R.layout.mainpager, null);
		this.content.addView(item_content);
		super.initData();
	}
}
