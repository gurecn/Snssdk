package com.qianfeng.gl4study.snssdk.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.qianfeng.gl4study.snssdk.fragment.TutorialFragment;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/20
 * Email:pdsfgl@live.com
 */
public class TutorialPagerAdapter extends FragmentPagerAdapter {

	public TutorialPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = null;
		fragment = new TutorialFragment();
		Bundle args = new Bundle();
		args.putInt("position",i);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}
}
