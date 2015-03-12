package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import com.qianfeng.gl4study.snssdk.R;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/12
 * Email:pdsfgl@live.com
 */
public class PublishDiscuss extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_doscuss);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.hide();

	}
}