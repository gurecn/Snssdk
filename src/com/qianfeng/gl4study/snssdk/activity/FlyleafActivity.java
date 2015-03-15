package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.qianfeng.gl4study.snssdk.R;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/12
 * Email:pdsfgl@live.com
 */
public class FlyleafActivity extends Activity{
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
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(FlyleafActivity.this, MainActivity.class);
				startActivity(intent);
				FlyleafActivity.this.finish();
			}
		}, 3000);
	}
}