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
	private static String minTimeWord = "";
	private static String maxTimeWord = "";
	private static String minTimeImage = "";
	private static String maxTimeImage = "";
	private static String minTimeVideo = "";
	private static String maxTimeVideo = "";

	public static SingletonVariable getOurInstance() {
		return ourInstance;
	}

	public static String getMinTimeWord() {
		return minTimeWord;
	}

	public static void setMinTimeWord(String minTimeWord) {
		SingletonVariable.minTimeWord = minTimeWord;
	}

	public static String getMaxTimeWord() {
		return maxTimeWord;
	}

	public static void setMaxTimeWord(String maxTimeWord) {
		SingletonVariable.maxTimeWord = maxTimeWord;
	}

	public static String getMinTimeImage() {
		return minTimeImage;
	}

	public static void setMinTimeImage(String minTimeImage) {
		SingletonVariable.minTimeImage = minTimeImage;
	}

	public static String getMaxTimeImage() {
		return maxTimeImage;
	}

	public static void setMaxTimeImage(String maxTimeImage) {
		SingletonVariable.maxTimeImage = maxTimeImage;
	}

	public static String getMinTimeVideo() {
		return minTimeVideo;
	}

	public static void setMinTimeVideo(String minTimeVideo) {
		SingletonVariable.minTimeVideo = minTimeVideo;
	}

	public static String getMaxTimeVideo() {
		return maxTimeVideo;
	}

	public static void setMaxTimeVideo(String maxTimeVideo) {
		SingletonVariable.maxTimeVideo = maxTimeVideo;
	}
}
