package com.example.friendlybeijing.viewpager;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.bean.DataInfo;

import android.content.Context;
import android.view.View;

public class GovaffairsViewpager extends BaseViewPager {

	public GovaffairsViewpager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		title.setText("人口管理");
		View item_content = View.inflate(maActivity, R.layout.govaffairspager, null);
		this.content.addView(item_content);
		super.initData();
	}
}
