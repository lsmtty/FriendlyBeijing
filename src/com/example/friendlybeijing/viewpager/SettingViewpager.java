package com.example.friendlybeijing.viewpager;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.bean.DataInfo;

import android.content.Context;
import android.provider.ContactsContract.Contacts.Data;
import android.view.View;

public class SettingViewpager extends BaseViewPager {

	public SettingViewpager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		title.setText("设置");
		View item_content = View.inflate(maActivity, R.layout.settingpager, null);
		this.content.addView(item_content);
		super.initData();
	}
}
