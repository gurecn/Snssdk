package com.qianfeng.gl4study.snssdk.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import com.qianfeng.gl4study.snssdk.fragment.SnssdkInfoFragment;
import com.qianfeng.gl4study.snssdk.model.Snssdk;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/11
 * Email:pdsfgl@live.com
 */
public class SnssdkViewPagerAdapter extends FragmentPagerAdapter {


	private Snssdk snssdk;
	public SnssdkViewPagerAdapter(FragmentManager fm,Snssdk snssdk) {
		super(fm);
		this.snssdk = snssdk;

	}

	@Override
	public Fragment getItem(int i) {
		Log.d("SnssdkViewPagerAdapter","到这里");
		Fragment fragment = null;
		Bundle bundle = new Bundle();
		bundle.putSerializable("snssdk", snssdk);
		fragment = new SnssdkInfoFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}
}
