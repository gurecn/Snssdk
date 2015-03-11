package com.qianfeng.gl4study.snssdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkViewPagerAdapter;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.view.MyListView;
import com.qianfeng.gl4study.snssdk.adapter.DiscussListAdapter;
import com.qianfeng.gl4study.snssdk.model.Discuss;
import com.qianfeng.gl4study.snssdk.model.Snssdk;
import com.qianfeng.gl4study.snssdk.tasks.SnssdkTask;
import com.qianfeng.gl4study.snssdk.tasks.TaskProcessor;
import com.qianfeng.gl4study.snssdk.utils.FileCache;
import com.qianfeng.gl4study.snssdk.utils.ImageCache;
import com.qianfeng.gl4study.snssdk.utils.ImageLoader;
import com.qianfeng.gl4study.snssdk.view.SnssdkInfViewPager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;

public class SnssdkInfoActivity extends FragmentActivity{


	private LinkedList<Discuss> dataFresh;
	private LinkedList<Discuss> dataTop;
	private DiscussListAdapter adapterHot;
	private MyListView recyclerViewHot;
	private SnssdkTask discussTask;
	private MyListView recyclerViewFresh;
	private DiscussListAdapter adapterFresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snssdk_info);
		Intent intent = getIntent();

		int position = intent.getIntExtra("position",0);
		Serializable serializableExtra = intent.getSerializableExtra("snssdk");

		if(null!=serializableExtra&&serializableExtra instanceof Snssdk){
			Snssdk snssdk = (Snssdk)serializableExtra;
			View discussScrollView = findViewById(R.id.snssdk_info_scroll_view);
			discussScrollView.scrollTo(0,0);
			SnssdkInfViewPager viewPager = (SnssdkInfViewPager) findViewById(R.id.snssdk_info_view_pager);
			SnssdkViewPagerAdapter adapter = new SnssdkViewPagerAdapter(getSupportFragmentManager(),snssdk);
			viewPager.setAdapter(adapter);
		}
	}
}
