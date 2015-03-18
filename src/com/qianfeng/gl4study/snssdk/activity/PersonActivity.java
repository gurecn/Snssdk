package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.tasks.SnssdkTask;
import com.qianfeng.gl4study.snssdk.tasks.TaskProcessor;
import org.json.JSONObject;

public class PersonActivity extends Activity implements TaskProcessor {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.hide();
		}

		Intent intent = getIntent();
		long userId = intent.getLongExtra("userId",0);

		SnssdkTask userTask = new SnssdkTask(this);
		userTask.execute(Constant.SNSSDK_USER_INFOMATION_URL+userId,0+"");

	}

	@Override
	public void processResult(JSONObject result, String flag) {

	}
}
