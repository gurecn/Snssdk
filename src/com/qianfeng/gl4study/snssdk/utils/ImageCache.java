package com.qianfeng.gl4study.snssdk.utils;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;


import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 图片缓存工具，进行内存图片的管理
 * FileCache进行文件缓存管理
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/10
 * Email:pdsfgl@live.com
 */
public class ImageCache {
	private static ImageCache ourInstance;

	public static ImageCache getInstance() {
		if(ourInstance == null){
			ourInstance = new ImageCache();
		}
		return ourInstance;
	}
	private LruCache<String,Bitmap> firstCache;
	private HashMap<String ,SoftReference<Bitmap>> secondCache;
	private ImageCache() {
		long freeMemory = Runtime.getRuntime().freeMemory();
		int memory;
		if (freeMemory>0) {
			memory = (int)(freeMemory/8);
		}else {
			memory = 5*1024*1024;
		}
		firstCache = new LruCache<String, Bitmap>(memory){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				int size = 0;
				if(Build.VERSION.SDK_INT>=19){
					size = value.getAllocationByteCount();
				}else {
					int rowBytes = value.getRowBytes();//一行的字节数
					int height = value.getHeight();
					return rowBytes*height;
				}
				return size;
			}
		};
		secondCache = new LinkedHashMap<String, SoftReference<Bitmap>>();
	}

	/**
	 * 获取内存缓存中指定url地址的图片
	 * @param url   图片地址
	 * @return      对象
	 */
	public Bitmap getImage(String url){
		Bitmap ret = null;

		if(url!=null){
			//先从一级开始顺次查找
			ret = firstCache.get(url);
			if(ret ==null){ //向第二级查找
				if(secondCache.containsKey(url)){
					SoftReference<Bitmap> softReference = secondCache.get(url);
					//将SoftReference转化，结果可能会是null，因为GC可能会把SoftReference的数据释放掉
					if(softReference!=null){
						ret = softReference.get();//获取引用的实际对象
					}
				}
			}
		}
		return ret;
	}

	/**
	 * 更新内存缓存
	 * @param url
	 * @param bitmap
	 */
	public void putImage(String url,Bitmap bitmap){
		if(url!=null&&bitmap!=null){
			//更新二级缓存
			secondCache.put(url,new SoftReference<Bitmap>(bitmap));
			//更新一级缓存
			firstCache.put(url,bitmap);
		}
	}
}
