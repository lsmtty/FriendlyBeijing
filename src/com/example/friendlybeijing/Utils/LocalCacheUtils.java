package com.example.friendlybeijing.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class LocalCacheUtils {
	public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	private  MemoryCacheUtils memoryCacheUtils;
	public LocalCacheUtils(MemoryCacheUtils memoryCacheUtils) {
		// TODO Auto-generated constructor stub
		this.memoryCacheUtils = memoryCacheUtils;
	}
	public void setBitmapToLocal(String url,Bitmap bitmap)
	{
		String filename = MD5Utils.enCode(url);
		File file = new File(CACHE_PATH + "/friendlybeijing/"+filename);//创建一个文件夹放置图片
		try {
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));
			memoryCacheUtils.setBitmapToMemory(url, bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public Bitmap getBitmapFromLocal(String url)
	{
		String filename = MD5Utils.enCode(url);
		File file = new File(CACHE_PATH + "/friendlybeijing/"+filename);
		if(file.exists())
		{
			try {
				return BitmapFactory.decodeStream(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		else {
			return null;
		}
	}
}
