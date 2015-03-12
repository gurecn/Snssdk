package com.qianfeng.gl4study.snssdk.activity;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkMainAdapter;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.model.*;
import com.qianfeng.gl4study.snssdk.tasks.SnssdkTask;
import com.qianfeng.gl4study.snssdk.tasks.TaskProcessor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * 整个项目的主界面显示
 */

public class MainActivity extends Activity implements TaskProcessor, View.OnClickListener{



	private SnssdkMainAdapter adapter;      //显示段子的Adapter
	private ListView listViewSnssdk;
	private SnssdkTask snssdkTask;
	private MenuItem itemWord;
	private MenuItem itemImage;
	private MenuItem itemVideo;

	private String levelURL = "levelURL=";
	private String categoryIdURL = "&category_id=";
	private String countURL = "&countURL=";
	private String minTimeURL = "&min_time=";
	private String maxTimeURL = "&max_time=";

	//标记需要获取的段子的参数
	private int level=6;
	private int category = 1;
	private int count = 0;
	private long minTime = 0;
	private long maxTime = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listViewSnssdk = (ListView) findViewById(R.id.recycle_view);//主界面显示段子列表

//=================================================
		//设置隐藏ActionBar隐藏标题，图标，上界面的title栏
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.hide();
		//设置背景色
		//actionBar.setSplitBackgroundDrawable(new ColorDrawable(R.drawable.main_rg_bg));
//==================================================
		//自定义标题栏的组件获取

		// 用户头像
		ImageView topUser = (ImageView) findViewById(R.id.ib_user_icon);
		topUser.setOnClickListener(this);
		//投稿图标
		ImageView topContribute = (ImageView) findViewById(R.id.ib_push_contribute);
		topContribute.setOnClickListener(this);
//===================================================
		//开启异步加载段子信息
		snssdkTask = new SnssdkTask(this);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(level)//推荐分类
				.append(categoryIdURL).append(category)//文本段子
				.append(countURL).append(count)  //返回20个数据
				.append(minTimeURL).append(SingletonVariable.getMinTimeWord());
		snssdkTask.execute(stringBuilder.toString(),category+"");

		adapter = new SnssdkMainAdapter(this, SingletonWord.getSnssdks());
		listViewSnssdk.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		itemWord = menu.getItem(0);
		itemImage = menu.getItem(1);
		itemVideo = menu.getItem(2);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		boolean ret = false;
		int itemId = item.getItemId();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(level)     //推荐分类
				.append(countURL).append(count);   //返回20个数据
		switch(itemId){
			case R.id.menu_word:
				Toast.makeText(this,"word",Toast.LENGTH_LONG).show();
				onClickImageButton();
				item.setIcon(R.drawable.document_main_full);
				category = Constant.TYPE_1_CATEGORY_ID_WORD_FLAG_SNSSDK;
				break;
			case R.id.menu_image:
				Toast.makeText(this,"image",Toast.LENGTH_LONG).show();
				onClickImageButton();
				item.setIcon(R.drawable.camera_main_full);
				category = Constant.TYPE_1_CATEGORY_ID_IMAGE_FLAG_SNSSDK;
				break;
			case R.id.menu_video:
				Toast.makeText(this,"video",Toast.LENGTH_LONG).show();
				onClickImageButton();
				item.setIcon(R.drawable.video_main_full);
				category = Constant.TYPE_1_CATEGORY_ID_VIDEO_FLAG_SNSSDK;
				break;
		}

		if(itemId == R.id.menu_find){
			Toast.makeText(this,"find",Toast.LENGTH_LONG).show();
		}else if(itemId == R.id.menu_examine){
			Toast.makeText(this,"examine",Toast.LENGTH_LONG).show();
		}else {
			snssdkTask = new SnssdkTask(this);
			stringBuilder.append(categoryIdURL).append(category);//文本段子
			snssdkTask.execute(stringBuilder.toString(),category+"");
		}
		ret = super.onOptionsItemSelected(item);
		return ret;
	}

	@Override
	public void processResult(JSONObject result, String  flag) {
		if(result!=null){      //段子列表
			try {
				String resultFlag = result.getString("message");
				if("success".equals(resultFlag)){
					LinkedList<Snssdk> snssdks = new LinkedList<Snssdk>();
					JSONObject data = result.getJSONObject("data");
					String tip = data.getString("tip");
					JSONArray dataJSONArray = data.getJSONArray("data");
					int type = Integer.parseInt(flag);
					for (int i = 0; i < dataJSONArray.length(); i++) {
						JSONObject jsonObject = dataJSONArray.getJSONObject(i);
						JSONObject group = jsonObject.getJSONObject("group");
						Snssdk snssdk = new Snssdk();
						snssdk.parseInformation(group,type);
						snssdks.add(snssdk);
					}
					double minTime1 = data.getDouble("min_time");
					double maxTime1 = data.getDouble("max_time");
					SharedPreferences sharedPreferences  = getSharedPreferences("config", MODE_PRIVATE);
					SharedPreferences.Editor edit = sharedPreferences.edit();
					if(type == 1){
						SingletonWord.getInstance().addAllSnssdks(snssdks);
						String minTimeWordString = SingletonVariable.getMinTimeWord();
						if(null == minTimeWordString||minTime1>Double.parseDouble(minTimeWordString)){
							SingletonVariable.setMinTimeWord(minTime1+"");
							edit.putString("minTimeWord",minTime1+"");
						}
						String maxTimeWordString = SingletonVariable.getMaxTimeWord();
						if(null == maxTimeWordString||maxTime1<Double.parseDouble(maxTimeWordString)){
							SingletonVariable.setMaxTimeWord(maxTime1 + "");
							edit.putString("maxTimeWord",maxTime1+"");
						}
					}else if(type == 2){
						SingletonImage.getInstance().addAllSnssdks(snssdks);
						String minTimeImageString = SingletonVariable.getMinTimeImage();
						if(null == minTimeImageString||minTime1>Double.parseDouble(minTimeImageString)){
							SingletonVariable.setMinTimeImage(minTime1 + "");
							edit.putString("minTimeImage",minTime1+"");
						}
						String maxTimeImageString = SingletonVariable.getMaxTimeImage();
						if(null == maxTimeImageString||maxTime1<Double.parseDouble(maxTimeImageString)){
							SingletonVariable.setMaxTimeImage(maxTime1 + "");
							edit.putString("maxTimeImage",maxTime1+"");
						}

					}else if(type == 18){
						SingletonVideo.getInstance().addAllSnssdks(snssdks);
						String minTimeVideoString = SingletonVariable.getMinTimeVideo();
						if(null == minTimeVideoString||minTime1>Double.parseDouble(minTimeVideoString)){
							SingletonVariable.setMinTimeVideo(minTime1 + "");
							edit.putString("minTimeVideo",minTime1+"");
						}
						String maxTimeVideoString = SingletonVariable.getMaxTimeVideo();
						if(null == maxTimeVideoString||maxTime1<Double.parseDouble(maxTimeVideoString)){
							SingletonVariable.setMaxTimeVideo(maxTime1 + "");
							edit.putString("maxTimeVideo",maxTime1+"");
						}
					}
					edit.commit();
					Log.d("MainActivity","下载完成============="+snssdks.size());
					//数据添加完成，更新List
					listViewSnssdk.setAdapter(adapter);
					//adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 点击listViewSnssdk调用
	 * @param v
	 */
	@Override
	public void onClick(View v) {

		int id = v.getId();
		Object tag = v.getTag();
		int position = -1;
		Snssdk snssdk = null;
		if(tag!=null) {
			position = (Integer) tag;

			if(category == 1){
				snssdk = SingletonWord.getSnssdks().get(position);
			}else if(category == 2){
				snssdk = SingletonImage.getSnssdks().get(position);
			}else if(category == 18){
				snssdk = SingletonVideo.getSnssdks().get(position);
			}
			Log.d("MainActivity","onClick=============");
			switch (id){
				case R.id.item_fragment_common://点击内容跳转到详情页面，文字，图片，视频
					skipToInfo(position);
					break;
				case R.id.item_fragment_bar_good_ll://点击顶
					if(snssdk.getUserRepin()==1){
						Toast.makeText(this,"你已经踩了，做人不要矛盾哦",Toast.LENGTH_SHORT).show();
					}else {
						if (snssdk.getUserDigg() == 0) {
							snssdk.setDiggCount(snssdk.getDiggCount() + 1);
							snssdk.setUserDigg(1);
						} else {
							snssdk.setDiggCount(snssdk.getDiggCount() - 1);
							snssdk.setUserDigg(0);
						}
						adapter.notifyDataSetChanged();
						Log.d("MainActivity", "item_fragment_bar_good");
					}
					break;
				case R.id.item_fragment_bar_bad_ll://点击踩
					if(snssdk.getUserDigg() == 1){
						Toast.makeText(this,"你已经顶了，做人不要矛盾哦",Toast.LENGTH_SHORT).show();
					}else {
						if (snssdk.getUserRepin() == 0) {
							snssdk.setRepinCount(snssdk.getRepinCount() + 1);
							snssdk.setUserRepin(1);
						} else {
							snssdk.setRepinCount(snssdk.getRepinCount() - 1);
							snssdk.setUserRepin(0);
						}
						adapter.notifyDataSetChanged();
						Log.d("MainActivity", "item_fragment_bar_bad");
					}
					break;
				case R.id.item_fragment_bar_hot_ll://点击评论，跳转到分享页面
					adapter.notifyDataSetChanged();
					break;
				//点击头像
				case R.id.ib_user_icon:
					Intent intent = new Intent(this, PersonActivity.class);
					startActivity(intent);
					break;

				//点击提交
				case R.id.ib_push_contribute:
					intent = new Intent(this, ContributeActivity.class);
					startActivity(intent);
					break;
			}
		}
	}

	private void skipToInfo(int position){
		Intent intent = new Intent(this, SnssdkInfoActivity.class);
		if(position>=0) {
			intent.putExtra("position",position);
			intent.putExtra("category",category);
		}
		startActivity(intent);
		Log.d("MainActivity","item_fragment_word");
	}

	/**
	 * 当点击ActionBar时清空显示数据并变换图片
	 */
	private void onClickImageButton(){

		//清空显示列表,将原数据置空
		listViewSnssdk.setAdapter(null);
		//snssdks.clear();
		//首先将所有图标设置成暗色，然后点击哪一个哪一个变色即可
		itemWord.setIcon(R.drawable.document_main_one);
		itemImage.setIcon(R.drawable.camera_main_one);
		itemVideo.setIcon(R.drawable.video_main_one);
	}
}
