package com.qianfeng.gl4study.snssdk.model;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/12
 * Email:pdsfgl@live.com
 */
public class SingletonVideo {
	private static SingletonVideo ourInstance = new SingletonVideo();

	public static SingletonVideo getInstance() {
		return ourInstance;
	}

	private SingletonVideo() {
	}
}
