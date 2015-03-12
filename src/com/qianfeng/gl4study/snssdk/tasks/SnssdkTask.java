package com.qianfeng.gl4study.snssdk.tasks;

import android.os.AsyncTask;
import com.qianfeng.gl4study.snssdk.utils.FileCache;
import com.qianfeng.gl4study.snssdk.utils.HttpTool;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 获取商城的分类信息
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/2/5
 * Email:pdsfgl@live.com
 */
public class SnssdkTask extends AsyncTask<String ,Integer,JSONObject> {


	private TaskProcessor processor;
	private String  flag = "1";

	public SnssdkTask(TaskProcessor processor){
		this.processor = processor;
	}

	/**
	 * 参数顺序：主连接URL，段子Id，异步类型标记，返回评论数量，返回评论起点
	 * @param params
	 * @return
	 */
	@Override
	protected JSONObject doInBackground(String... params) {
		JSONObject ret = null;
		if(params.length==2){
			flag = params[1];
			String url = params[0];

			//检查需要下载的数据是否已经存储到本地，是的话直接返回，否则进行下载
			FileCache fileCache = FileCache.getInstance();

			byte[] bytes = fileCache.getContent(url);

			if(bytes==null||bytes.length<=0){
				bytes = HttpTool.get(params[0]);
				if(bytes!=null) {
					FileCache.getInstance().putContent(url,bytes);

					//每次缓存数据均需更新配置文件
				}
			}
			if(bytes!=null){
				try {
					String str = new String(bytes, "UTF-8");
					ret = new JSONObject(str);

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	@Override
	protected void onPostExecute(JSONObject jsonObject) {
		if(processor!=null){
			processor.processResult(jsonObject,flag);
		}
	}
}
