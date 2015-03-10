package com.qianfeng.gl4study.snssdk.tasks;

import org.json.JSONObject;

/**
 * 用于Activity与AsyncTAsk之间的沟通桥梁，异步任务一般不会采用内部类编写，Activity的UI无法直接更新。
 * 故使用接口回调
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/2/5
 * Email:pdsfgl@live.com
 */
public interface TaskProcessor {

	/**
	 * 用于接收异步任务的结果，该方法在异步任务中调用，可以直接更新UI
	 * @param result
	 */
	void processResult(JSONObject result, String flag);
}
