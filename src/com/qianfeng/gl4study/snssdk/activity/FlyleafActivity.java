package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.model.*;
import com.qianfeng.gl4study.snssdk.tasks.SnssdkTask;
import com.qianfeng.gl4study.snssdk.tasks.TaskProcessor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/12
 * Email:pdsfgl@live.com
 */
public class FlyleafActivity extends Activity implements TaskProcessor {

	private String levelURL = "levelURL=";
	private String categoryIdURL = "&category_id=";
	private String countURL = "&countURL=";
	private String minTimeURL = "&min_time=";


	//标记是否异步任务完成

	private boolean wordTask = false;
	private boolean imageTask = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flyleaf);

		//设置隐藏ActionBar隐藏标题，图标，上界面的title栏
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.hide();

		SharedPreferences sharedPreferences  = getSharedPreferences("config", MODE_PRIVATE);
		//从文件中读取数据
		long minTimeWord = sharedPreferences.getLong("minTimeWord", 0);
		long minTimeImage = sharedPreferences.getLong("minTimeImage", 0);
	/*
		//开启异步加载文本段子信息
		SnssdkTask 	snssdkTask = new SnssdkTask(this);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(6)//推荐分类
				.append(categoryIdURL).append(1)//文本段子
				.append(countURL).append(30)   //返回20个数据
				.append(minTimeURL).append(minTimeWord);
		Log.d("Time1","使用minTime1==="+minTimeWord);
		snssdkTask.execute(stringBuilder.toString(),1+"");

		snssdkTask = new SnssdkTask(this);
		stringBuilder = new StringBuilder();
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(6)//推荐分类
				.append(categoryIdURL).append(2)//文本段子
				.append(countURL).append(30)   //返回20个数据
				.append(minTimeURL).append(minTimeImage);

		snssdkTask.execute(stringBuilder.toString(),2+"");

	*/
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(FlyleafActivity.this, MainActivity.class);
				startActivity(intent);
				FlyleafActivity.this.finish();
			}
		}, 3000);
	}

	@Override
	public void processResult(JSONObject result, String flag) {
		if(result!=null){      //段子列表
			try {
				String resultFlag = result.getString("message");
				if("success".equals(resultFlag)){
					LinkedList<Snssdk> snssdks = new LinkedList<Snssdk>();
					JSONObject data = result.getJSONObject("data");
					JSONArray dataJSONArray = data.getJSONArray("data");
					int type = Integer.parseInt(flag);
					for (int i = 0; i < dataJSONArray.length(); i++) {
						JSONObject jsonObject = dataJSONArray.getJSONObject(i);
						JSONObject group = jsonObject.getJSONObject("group");
						Snssdk snssdk = new Snssdk();
						snssdk.parseInformation(group,type);
						snssdks.add(snssdk);
					}
					long minTime1 = data.getLong("min_time");
					long maxTime1 = data.getLong("max_time");
					Log.d("Time1","返回minTime1==="+minTime1+"maxTime1====="+maxTime1);
					SharedPreferences sharedPreferences  = getSharedPreferences("config", MODE_PRIVATE);
					SharedPreferences.Editor edit = sharedPreferences.edit();
					if("1".equals(flag)){
						wordTask = true;
						SingletonWord.getInstance().removeAll();
						SingletonWord.getInstance().addAllSnssdks(snssdks);
						edit.putLong("minTimeWord", minTime1);
						edit.putLong("maxTimeWord", maxTime1);

					}else if("2".equals(flag)){
						imageTask = true;
						SingletonImage.getInstance().removeAll();
						SingletonImage.getInstance().addAllSnssdks(snssdks);
						edit.putLong("minTimeImage", minTime1);
						edit.putLong("maxTimeImage", maxTime1);
					}
					edit.commit();
					Log.d("MainActivity", "初次下载完成=============" + snssdks.size());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}