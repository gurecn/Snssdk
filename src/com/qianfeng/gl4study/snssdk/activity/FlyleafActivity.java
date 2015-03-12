package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkMainAdapter;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.model.SingletonVariable;
import com.qianfeng.gl4study.snssdk.tasks.SnssdkTask;
import com.qianfeng.gl4study.snssdk.tasks.TaskProcessor;
import org.json.JSONObject;

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
		String minTimeWord = sharedPreferences.getString("minTimeWord", null);
		String maxTimeWord = sharedPreferences.getString("axTimeWord", null);
		String minTimeImage = sharedPreferences.getString("minTimeImage", null);
		String maxTimeImage = sharedPreferences.getString("maxTimeImage", null);
		String minTimeVideo = sharedPreferences.getString("minTimeVideo", null);
		String maxTimeVideo = sharedPreferences.getString("maxTimeVideo", null);

		//设置程序运行中的时间，避免多次进行文件读写操作
		SingletonVariable.setMinTimeWord(minTimeWord);
		SingletonVariable.setMaxTimeWord(maxTimeWord);
		SingletonVariable.setMinTimeImage(minTimeImage);
		SingletonVariable.setMaxTimeImage(maxTimeImage);
		SingletonVariable.setMinTimeVideo(minTimeVideo);
		SingletonVariable.setMaxTimeVideo(maxTimeVideo);

		//开启异步加载文本段子信息
		SnssdkTask 	snssdkTask = new SnssdkTask(this);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(6)//推荐分类
				.append(categoryIdURL).append(1)//文本段子
				.append(countURL).append(0)   //返回20个数据
				.append(minTimeURL).append(minTimeWord);

		snssdkTask.execute(stringBuilder.toString(),1+"");

		snssdkTask = new SnssdkTask(this);
		stringBuilder = new StringBuilder();
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(6)//推荐分类
				.append(categoryIdURL).append(2)//文本段子
				.append(countURL).append(0)   //返回20个数据
				.append(minTimeURL).append(minTimeImage);

		snssdkTask.execute(stringBuilder.toString(),2+"");

	}

	@Override
	public void processResult(JSONObject result, String flag) {
		if("1".equals(flag)){
			wordTask = true;
		}else {
			imageTask = true;
		}

		if(wordTask&&imageTask){
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
	}
}