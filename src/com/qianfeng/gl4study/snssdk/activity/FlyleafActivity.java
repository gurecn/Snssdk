package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkMainAdapter;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.databases.SnssdkDatabasesManager;
import com.qianfeng.gl4study.snssdk.model.SingletonImage;
import com.qianfeng.gl4study.snssdk.model.SingletonVideo;
import com.qianfeng.gl4study.snssdk.model.SingletonWord;
import com.qianfeng.gl4study.snssdk.model.Snssdk;
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

	private String levelURL = "level=";
	private String categoryIdURL = "&category_id=";
	private String countURL = "&count=";

	//标记需要获取的段子的参数
	private int level = 6;
	private int category = 1;
	private int count = 20;
	private long minTime = 0;
	private boolean isFirstRun;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flyleaf);

		//设置隐藏ActionBar隐藏标题，图标，上界面的title栏
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.hide();
		}

		getNewDataFromntent();
		//判断是否是第一次运行
		SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
		Log.d("FlyleafActivity","isFirstRun:"+ isFirstRun);
		if(isFirstRun){
			SharedPreferences.Editor edit;
			edit = sharedPreferences.edit();
			edit.putBoolean("isFirstRun", false);
			edit.apply();
			Intent intent = new Intent(FlyleafActivity.this, TutorialActivity.class);
			startActivity(intent);
			FlyleafActivity.this.finish();
		}
	}
	private void getNewDataFromntent(){
		SharedPreferences sharedPreferences  = getSharedPreferences("config", MODE_PRIVATE);
		long minWordTime = sharedPreferences.getLong("minTimeWord", 0);
		long minImageTime = sharedPreferences.getLong("minTimeImage", 0);
		StringBuilder stringBuilder = new StringBuilder();
		SnssdkTask snssdkTask  = new SnssdkTask(this);
		String minTimeURL = "&min_time=";
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(6)//推荐分类
				.append(categoryIdURL).append(1)//文本段子
				.append(countURL).append(count)   //返回20个数据
				.append(minTimeURL).append(minWordTime);
		snssdkTask.execute(stringBuilder.toString(), 1 + "");

		snssdkTask  = new SnssdkTask(this);
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(6)//推荐分类
				.append(categoryIdURL).append(2)//图片段子
				.append(countURL).append(count)   //返回20个数据
				.append(minTimeURL).append(minImageTime);
		snssdkTask.execute(stringBuilder.toString(), 2 + "");
	}
	@Override
	public void processResult(JSONObject result, String flag) {
		Log.d("processResult","加载  "+isFirstRun);
		if (result != null) {      //段子列表
			Log.d("processResult","加载1  "+isFirstRun);
			try {
				String resultFlag = result.getString("message");
				if ("success".equals(resultFlag)) {
					JSONObject data = result.getJSONObject("data");
					JSONArray dataJSONArray = data.getJSONArray("data");
					int type = Integer.parseInt(flag);
					for (int i = 0; i < dataJSONArray.length(); i++) {
						JSONObject jsonObject = dataJSONArray.getJSONObject(i);
						Snssdk snssdk = new Snssdk();
						snssdk.parseInformation(jsonObject, type);
						ContentValues values = snssdk.getContentValues();
						SnssdkDatabasesManager.createInstance(this).saveSnssdk(values,snssdk.getGroup_id());
					}
					saveTimeToSingleton(this,type,data.getLong("min_time"),data.getLong("max_time"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if("1".equals(flag)&&!isFirstRun){
				Intent intent = new Intent(FlyleafActivity.this, MainActivity.class);
				startActivity(intent);
				FlyleafActivity.this.finish();
			}
		}
	}

	/**
	 * 网络获取的数据保存到单例和配置文件中
	 *
	 * @param type          段子更新的类型
	 * @param minTime1      返回的时间
	 * @param maxTime1      返回的时间
	 */
	private void saveTimeToSingleton(Context context,int type,long minTime1, long maxTime1) {

		SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		SharedPreferences.Editor edit = sharedPreferences.edit();
		if (type == 1) {
			edit.putLong("minTimeWord", minTime1);
			edit.putLong("maxTimeWord", maxTime1);
		} else if (type == 2) {
			edit.putLong("minTimeImage", minTime1);
			edit.putLong("maxTimeImage", maxTime1);
		}
		edit.apply();
	}

}