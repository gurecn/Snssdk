package com.qianfeng.gl4study.snssdk.utils;

import android.app.Application;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/10
 * Email:pdsfgl@live.com
 */
public class MyApplication extends Application {
	@Override
	public void onCreate() {
		//
		FileCache.createInstance(getApplicationContext());
	}
}
