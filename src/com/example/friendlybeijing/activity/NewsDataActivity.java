package com.example.friendlybeijing.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.example.friendlybeijing.R;
import com.example.friendlybeijing.Utils.WebUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDataActivity extends Activity {
	private ImageView back;
	private ImageView check_text_size;
	private ImageView share;
	private WebView webView;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_data_layout);
		url = getIntent().getExtras().getString("url");
		initView();
		initWebView();
		initLisener();
	}
	
	private void initLisener() {
		// TODO Auto-generated method stub
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubha
				ShareSDK.initSDK(NewsDataActivity.this);
				OnekeyShare oks  = new OnekeyShare();
				oks.setText("我是分享文本");
				oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
				//启动分享GUI
				oks.show(NewsDataActivity.this);
			}
		});
	}

	private void initWebView() {
		// TODO Auto-generated method stub
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		if(url.startsWith(WebUtils.LOCALIP))
		{
			String target = (String)url.subSequence(WebUtils.LOCALIP.length(),url.length());
			url = WebUtils.CELLPHONEIP + target;
		}
		webView.loadUrl(url);
	}

	private void initView() {
		back = (ImageView) findViewById(R.id.iv_back);
		check_text_size = (ImageView) findViewById(R.id.iv_textsize);
		share = (ImageView) findViewById(R.id.iv_share);
		webView = (WebView) findViewById(R.id.web);
	}
}
