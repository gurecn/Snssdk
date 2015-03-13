package com.qianfeng.gl4study.snssdk.model;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/12
 * Email:pdsfgl@live.com
 */
public class SingletonVariable {

	private static final SingletonVariable ourInstance = new SingletonVariable();

	public static SingletonVariable getInstance() {
		return ourInstance;
	}
	private SingletonVariable() {
	}

	//记录时间
	private static long minTimeWord;
	private static long maxTimeWord;
	private static long minTimeImage;
	private static long maxTimeImage;
	private static long minTimeVideo;
	private static long maxTimeVideo;

	public static long getMinTimeWord() {
		return minTimeWord;
	}

	public static void setMinTimeWord(long minTimeWord) {
		SingletonVariable.minTimeWord = minTimeWord;
	}

	public static long getMaxTimeWord() {
		return maxTimeWord;
	}

	public static void setMaxTimeWord(long maxTimeWord) {
		SingletonVariable.maxTimeWord = maxTimeWord;
	}

	public static long getMinTimeImage() {
		return minTimeImage;
	}

	public static void setMinTimeImage(long minTimeImage) {
		SingletonVariable.minTimeImage = minTimeImage;
	}

	public static long getMaxTimeImage() {
		return maxTimeImage;
	}

	public static void setMaxTimeImage(long maxTimeImage) {
		SingletonVariable.maxTimeImage = maxTimeImage;
	}

	public static long getMinTimeVideo() {
		return minTimeVideo;
	}

	public static void setMinTimeVideo(long minTimeVideo) {
		SingletonVariable.minTimeVideo = minTimeVideo;
	}

	public static long getMaxTimeVideo() {
		return maxTimeVideo;
	}

	public static void setMaxTimeVideo(long maxTimeVideo) {
		SingletonVariable.maxTimeVideo = maxTimeVideo;
	}

	public static SingletonVariable getOurInstance() {
		return ourInstance;
	}

}
