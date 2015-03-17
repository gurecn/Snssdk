package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkViewPagerAdapter;
import com.qianfeng.gl4study.snssdk.constant.Constant;

public class SnssdkInfoActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

	private ViewPager viewPager;
	private int category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snssdk_info);
		Intent intent = getIntent();
		int position = intent.getIntExtra("position",0);
		category = intent.getIntExtra("category",0);

		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.hide();
		}
		viewPager = (ViewPager) findViewById(R.id.snssdk_info_view_pager);
		ImageView publishNewDiscuss = (ImageView) findViewById(R.id.iv_publish_new_discuss);
		publishNewDiscuss.setOnClickListener(this);
		SnssdkViewPagerAdapter adapter = new SnssdkViewPagerAdapter(getSupportFragmentManager(), category);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);
		viewPager.setOnPageChangeListener(this);
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.iv_publish_new_discuss){
			Intent intent = new Intent(this, PublishDiscuss.class);
			startActivity(intent);
		}
	}

	@Override
	public void onPageScrolled(int i, float v, int i1) {

	}

	/**
	 * 当界面滚动时记录显示的位置
	 */

	@Override
	public void onPageSelected(int i) {
		switch (category){
			case Constant.TYPE_1_CATEGORY_ID_WORD_FLAG_SNSSDK:
				Constant.MAIN_ACTIVITY_LIST_WORD_POSITION = viewPager.getCurrentItem();
				break;
			case Constant.TYPE_1_CATEGORY_ID_IMAGE_FLAG_SNSSDK:
				Constant.MAIN_ACTIVITY_LIST_IMAGE_POSITION = viewPager.getCurrentItem();
				break;
			case Constant.TYPE_1_CATEGORY_ID_VIDEO_FLAG_SNSSDK:
				Constant.MAIN_ACTIVITY_LIST_VIDEO_POSITION = viewPager.getCurrentItem();
				break;
		}
		Log.d("onPullUpToRefresh", "Position:" + viewPager.getCurrentItem()+"Constant："+Constant.MAIN_ACTIVITY_LIST_WORD_POSITION);
	}

	@Override
	public void onPageScrollStateChanged(int i) {

	}
}
