package com.qianfeng.gl4study.snssdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkViewPagerAdapter;
import com.qianfeng.gl4study.snssdk.view.SnssdkInfViewPager;

public class SnssdkInfoActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

	private SnssdkInfViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snssdk_info);
		Intent intent = getIntent();
		int position = intent.getIntExtra("position",0);
		int category = intent.getIntExtra("category",0);
		View discussScrollView = findViewById(R.id.snssdk_info_scroll_view);
		discussScrollView.scrollTo(0,0);
		viewPager = (SnssdkInfViewPager) findViewById(R.id.snssdk_info_view_pager);
		SnssdkViewPagerAdapter adapter = new SnssdkViewPagerAdapter(getSupportFragmentManager(),category);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrolled(int i, float v, int i1) {

	}

	@Override
	public void onPageSelected(int i) {

	}

	@Override
	public void onPageScrollStateChanged(int i) {

	}
}
