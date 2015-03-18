package com.qianfeng.gl4study.snssdk.tasks;

import android.os.AsyncTask;
import com.qianfeng.gl4study.snssdk.utils.FileCache;
import com.qianfeng.gl4study.snssdk.utils.HttpTool;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/18
 * Email:pdsfgl@live.com
 */
public class VideoLoaderTask extends AsyncTask<String, Integer, byte[]> {

	private String videoUrl;

	private TaskProcessor processor;
	private String  flag = "1";

	public VideoLoaderTask(TaskProcessor processor){
		this.processor = processor;
	}
	@Override
	protected byte[] doInBackground(String... params) {
		byte[] ret = new byte[0];
		if (params != null && params.length > 0) {
			videoUrl = params[0];
			ret = HttpTool.get(videoUrl);
		}
		return ret;
	}

	@Override
	protected void onPostExecute(byte[] bytes) {

		// TODO 数据缓存到本地
		if (bytes != null) {
			//更新内存缓存信息
			FileCache.getInstance().putContent(videoUrl,bytes);
			if(processor!=null){
				//processor.processResult(bytes,flag);
			}
		}
	}
}
