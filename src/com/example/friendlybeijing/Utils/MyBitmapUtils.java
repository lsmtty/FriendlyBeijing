package com.example.friendlybeijing.Utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.friendlybeijing.R;

public class MyBitmapUtils {
	private LocalCacheUtils localCacheUtils ;
	private NetCacheUtils netCacheUtils ;
	private MemoryCacheUtils memoryCacheUtils;
	public MyBitmapUtils() {
		// TODO Auto-generated constructor stub
		memoryCacheUtils = new MemoryCacheUtils();
		localCacheUtils =  new LocalCacheUtils(memoryCacheUtils);
		netCacheUtils = new NetCacheUtils(localCacheUtils,memoryCacheUtils);  //防止生成两个localCacheUtils
	}
	
	public void display(ImageView ivPic,String url)
	{
		ivPic.setImageResource(R.drawable.news_pic_default);
		Bitmap bitmap = null;
		bitmap = memoryCacheUtils.getBitmapFromMemory(url);
		if(bitmap!=null)
		{
			ivPic.setImageBitmap(bitmap);
			System.out.println("从内存读取图片成功啦");
			return;
		}
		else if((bitmap=localCacheUtils.getBitmapFromLocal(url))!=null){
			ivPic.setImageBitmap(bitmap);
			System.out.println("从本地读取图片成功啦");
			memoryCacheUtils.setBitmapToMemory(url, bitmap);
			return;
		}
		else {
			netCacheUtils.getBitmapFromNet(ivPic, url);
			System.out.println("从云端读取图片成功啦");
		}
	}
}
