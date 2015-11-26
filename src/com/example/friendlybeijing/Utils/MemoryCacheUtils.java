package com.example.friendlybeijing.Utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 所谓内存缓存，就是直接存储在以某种方式保存起来，并不是写成某种文件的形式
 * 
 * @author 思敏
 *
 */
public class MemoryCacheUtils {
	// private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new
	// HashMap<String, SoftReference<Bitmap>>();

	// LruCache 会将内存控制在一定大小内，小于这个大小就会被回收
	private LruCache<String, Bitmap> mMemoryCache;

	public MemoryCacheUtils() {
		// TODO Auto-generated constructor stub
		// Runtime 是应用锁运行的系统环境
		long maxMemory = Runtime.getRuntime().maxMemory(); // 最大内存
		mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory / 8) {  //一般取一个app额定内存的1/8
			//如何获取占用的大小,一定要重写这个函数，不然控制内存也是不准确的
			protected int sizeOf(String key, Bitmap value) {
				int byteCount = value.getByteCount(); //获取占用直接的数量
				return byteCount;
			};
		};
	}
	/**
	 * 从内存中读取图片
	 * @param url
	 * @return
	 */
	public Bitmap getBitmapFromMemory(String url) {
		/*SoftReference<Bitmap> softReference = mMemoryCache.get(url);
		Bitmap bitmap = softReference.get();*/
		return mMemoryCache.get(url);
	}
	/**
	 * 往内存中写入图片
	 * @param url
	 * @param bitmap
	 */
	public void setBitmapToMemory(String url, Bitmap bitmap) {
		/*SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
		mMemoryCache.put(url, softReference);*/
		mMemoryCache.put(url, bitmap);
	}
}
