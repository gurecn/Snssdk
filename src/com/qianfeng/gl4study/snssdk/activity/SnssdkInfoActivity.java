package com.qianfeng.gl4study.snssdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkViewPagerAdapter;

public class SnssdkInfoActivity extends FragmentActivity{

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snssdk_info);
		Intent intent = getIntent();
		int position = intent.getIntExtra("position",0);
		int category = intent.getIntExtra("category",0);

		viewPager = (ViewPager) findViewById(R.id.snssdk_info_view_pager);
		SnssdkViewPagerAdapter adapter = new SnssdkViewPagerAdapter(getSupportFragmentManager(),category);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);
	}

}
