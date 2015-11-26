package com.example.friendlybeijing.newsviews;

import android.content.Context;
import android.widget.RelativeLayout;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.bean.DataInfo;

public class TransactionItemPager extends BaseNewsPager {

	public TransactionItemPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		contentView = (RelativeLayout) RelativeLayout.inflate(mActivity, R.layout.news_item_transaction, null);
		contentView.setLayoutParams(lp);
	}

}
