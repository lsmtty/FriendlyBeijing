package com.example.friendlybeijing.newsviews;

import android.content.Context;
import android.widget.RelativeLayout;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.bean.DataInfo;

public class ThemeItemPager extends BaseNewsPager {

	public ThemeItemPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		contentView  = (RelativeLayout) RelativeLayout.inflate(mActivity, R.layout.news_item_theme, null);
		contentView.setLayoutParams(lp);
	}

}
