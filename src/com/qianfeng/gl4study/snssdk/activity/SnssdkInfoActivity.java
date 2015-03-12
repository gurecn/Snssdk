package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkViewPagerAdapter;

public class SnssdkInfoActivity extends FragmentActivity implements View.OnClickListener {

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snssdk_info);
		Intent intent = getIntent();
		int position = intent.getIntExtra("position",0);
		int category = intent.getIntExtra("category",0);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.hide();

		viewPager = (ViewPager) findViewById(R.id.snssdk_info_view_pager);
		ImageView publishNewDiscuss = (ImageView) findViewById(R.id.iv_publish_new_discuss);
		publishNewDiscuss.setOnClickListener(this);
		SnssdkViewPagerAdapter adapter = new SnssdkViewPagerAdapter(getSupportFragmentManager(),category);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.iv_publish_new_discuss){
			Intent intent = new Intent(this, PublishDiscuss.class);
			startActivity(intent);
		}
	}
}
