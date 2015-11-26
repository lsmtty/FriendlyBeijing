package com.example.friendlybeijing.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NetCacheUtils {
	//需要在下载完的同时，往内存和sd卡（本地）同时存储数据
	private LocalCacheUtils localCacheUtils;
	private MemoryCacheUtils memoryCacheUtils;
	public void getBitmapFromNet(ImageView ivPic,String url)
	{
		new BitmapTask().execute(ivPic,url);  //启动AsnyTask
	}
	public NetCacheUtils(LocalCacheUtils localCacheUtils,MemoryCacheUtils memoryCacheUtils) {
		// TODO Auto-generated constructor stub
		this.localCacheUtils = localCacheUtils;
		this.memoryCacheUtils = memoryCacheUtils;
	}
	//实际上是对handler和线程池进行了封装
	/**
	 * Asyn用法注意：1参数传递含义 2.三个方法的具体使用方法 3.每个方法运行在什么线程
	 * 第一个泛型 ：参数类型
	 * 第二个泛型：更新进度的泛型
	 * 第三个泛型：doInBackground返回的泛型 
	 * @author 思敏
	 *
	 */
	class BitmapTask extends AsyncTask<Object, Void, Bitmap>
	{
        private ImageView ivPic;
		private String url;

		//必须了解这三个方法
		/**
		 * 后台耗时方法在此执行，在子线程运行
		 */
		@Override
		protected Bitmap doInBackground(Object... params) {  
			ivPic = (ImageView) params[0];
			url = (String) params[1];
			Bitmap downloadBitmap = downloadBitmap(url);
			ivPic.setTag(url);   //用url绑定ImageView ****
			return downloadBitmap;
		}
		
		private Bitmap downloadBitmap(String url2) {
			// TODO Auto-generated method stub
			HttpURLConnection conn = null;
			try {
				  conn = (HttpURLConnection) new URL(url2).openConnection();
				  conn.setConnectTimeout(5000);
				  conn.setReadTimeout(5000);
				  conn.setRequestMethod("GET");
				  conn.connect();
				  int responseCode = conn.getResponseCode();
				  if(responseCode==200)
				  {
					  InputStream inputStream = conn.getInputStream();
					  Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					  localCacheUtils.setBitmapToLocal(url2, bitmap);
					  memoryCacheUtils.setBitmapToMemory(url2, bitmap);
					  return bitmap;
				  }
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{
				conn.disconnect();
			}
			return null;
		}

		//主线程更新
		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub 
			super.onProgressUpdate(values);
		}
		
		/**
		 * 耗时方法结束后，执行该方法，运行于主线程
		 */
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			if(result!=null && ivPic.getTag().equals(url))  //图片不为空，且绑定的是正确的ImageView
				ivPic.setImageBitmap(result);
			localCacheUtils.setBitmapToLocal(url, result);
			super.onPostExecute(result);
		}
	}
	
	
}
