package com.qianfeng.gl4study.snssdk.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import com.qianfeng.gl4study.snssdk.fragment.SnssdkInfoFragment;
import com.qianfeng.gl4study.snssdk.model.SingletonImage;
import com.qianfeng.gl4study.snssdk.model.SingletonVideo;
import com.qianfeng.gl4study.snssdk.model.SingletonWord;
import com.qianfeng.gl4study.snssdk.model.Snssdk;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/11
 * Email:pdsfgl@live.com
 */
public class SnssdkViewPagerAdapter extends FragmentPagerAdapter {


	private int category;

	public SnssdkViewPagerAdapter(FragmentManager fm,int category) {
		super(fm);
		this.category = category;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = null;
		Bundle bundle = new Bundle();
		Snssdk snssdk = null;
		if(category == 1){
			snssdk = SingletonWord.getSnssdks().get(i);
		}else if(category == 2){
			snssdk = SingletonImage.getSnssdks().get(i);
		}else if(category == 18){
			snssdk = SingletonVideo.getSnssdks().get(i);
		}
		bundle.putSerializable("snssdk", snssdk);
		fragment = new SnssdkInfoFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int getCount() {
		int ret = 0;
		if(category == 1){
			ret = SingletonWord.getSnssdks().size();
		}else if(category == 2){
			ret = SingletonImage.getSnssdks().size();
		}else if(category == 18){
			ret = SingletonVideo.getSnssdks().size();
		}
		return ret;
	}
}
