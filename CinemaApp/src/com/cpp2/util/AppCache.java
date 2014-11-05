package com.cpp2.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * 处理缓存的类
 * @author Administrator
 *
 */
public class AppCache {
	
	// tag for log
	private static String TAG = AppCache.class.getSimpleName();
	
	/**
	 * 通过图片的url在缓存中查找对应Bitmap对象，没有则访问网络下载到本地，注：没开线程
	 * @param ctx 要查找图片的上下文对象
	 * @param url 要查找的图片的网络地址
	 * @return 要查找的图片对象
	 */
	public static Bitmap getCachedImage (Context ctx, String url) {
		String cacheKey = AppUtil.md5(url);
		Bitmap cachedImage = SDUtil.getImage(cacheKey);
		if (cachedImage != null) {
			Log.w(TAG, "get cached image");
			return cachedImage;
		} else {
			Log.w(TAG, "get remote image");
			Bitmap newImage = IOUtil.getBitmapRemote(ctx, url);
			SDUtil.saveImage(newImage, cacheKey);
			return newImage;
		}
	}
	
	/**
	 * 通过图片的url在缓存中查找对应的Bitmap对象，没有则返回null，注：没开线程
	 * @param url 要查找的图片的网络地址
	 * @return 要查找的图片对象
	 */
	public static Bitmap getImage (String url) {
		String cacheKey = AppUtil.md5(url);
		return SDUtil.getImage(cacheKey);
	}
}