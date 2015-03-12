package com.qianfeng.gl4study.snssdk.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/12
 * Email:pdsfgl@live.com
 */
public class SingletonImage {

	private static List<Snssdk> snssdks = new LinkedList<Snssdk>();

	public static List<Snssdk> getSnssdks() {
		return snssdks;
	}

	public void addSnssdks(Snssdk snssdk) {
		snssdks.add(snssdks.size(),snssdk);
	}
	public boolean addAllSnssdks(LinkedList<Snssdk> snssdks1){
		return snssdks.addAll(snssdks.size(),snssdks1);
	}
	public void removeSnssdks(int postion){
		snssdks.remove(postion);
	}


	private static SingletonImage ourInstance = new SingletonImage();

	public static SingletonImage getInstance() {
		return ourInstance;
	}

	private SingletonImage() {
	}
}
