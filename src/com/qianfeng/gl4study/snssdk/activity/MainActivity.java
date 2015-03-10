package com.qianfeng.gl4study.snssdk.activity;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.ListView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkMainAdapter;
import com.qianfeng.gl4study.snssdk.model.Snssdk;
import com.qianfeng.gl4study.snssdk.tasks.SnssdkTask;
import com.qianfeng.gl4study.snssdk.tasks.TaskProcessor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


public class MainActivity extends Activity implements TaskProcessor, View.OnClickListener{

	public static final String SNSSDK_CONTENT_WORD_URL = "http://ic.snssdk.com/2/essay/zone/category/data/?category_id=1&level=6&count=10&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6";
	public static final String SNSSDK_CONTENT_IMAGE_URL = "http://ic.snssdk.com/2/essay/zone/category/data/?category_id=2&level=6&count=30&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6";
	public static final String SNSSDK_CONTENT_VIDEO_URL = "http://ic.snssdk.com/2/essay/zone/category/data/?category_id=3&level=6&count=10&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6";
	public static final String CATEGORY_WORD_FLAG_SNSSDK = "1";
	public static final String CATEGORY_IMAGE_FLAG_SNSSDK = "2";
	public static final String CATEGORY_VOIDO_FLAG_SNSSDK = "3";
	private LinkedList<Snssdk> snssdks;
	private SnssdkMainAdapter adapter;
	private ListView recyclerView;
	private SnssdkTask snssdkTask;

	//下方转换页面的ImageButon
	private ImageView actionbarWord;
	private ImageView actionbarImage;
	private ImageView actionbarVideo;
	private ImageView actionbarMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		recyclerView = (ListView) findViewById(R.id.recycle_view);

		snssdks = new LinkedList<Snssdk>();
		//ActionBar设置

		ActionBar actionBar = getActionBar();

		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.hide();
		//actionBar.setSplitBackgroundDrawable(new ColorDrawable(R.drawable.main_rg_bg));



		/*
		actionbarWord = (ImageView) findViewById(R.id.ib_word);
		actionbarImage = (ImageView) findViewById(R.id.ib_image);
		actionbarVideo = (ImageView) findViewById(R.id.ib_video);
		actionbarMore = (ImageView) findViewById(R.id.ib_more);
		*/


		ImageView topUser = (ImageView) findViewById(R.id.ib_user_icon);
		ImageView topContribute = (ImageView) findViewById(R.id.ib_push_contribute);
		topUser.setOnClickListener(this);

		/*
		actionbarMore.setOnClickListener(this);
		actionbarVideo.setOnClickListener(this);
		actionbarImage.setOnClickListener(this);
		actionbarWord.setOnClickListener(this);
*/
		topContribute.setOnClickListener(this);

		snssdkTask = new SnssdkTask(this);
		snssdkTask.execute(SNSSDK_CONTENT_WORD_URL, CATEGORY_WORD_FLAG_SNSSDK);

		adapter = new SnssdkMainAdapter(this, snssdks);
		//recyclerView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		boolean ret = false;

		ret = super.onOptionsItemSelected(item);

		return ret;
	}

	@Override
	public void processResult(JSONObject result, String  flag) {
		if(result!=null){      //段子列表
			try {
				String resultFlag = result.getString("message");
				if("success".equals(resultFlag)){
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
					//adapter.notifyItemInserted(snssdks.size());
					//数据添加完成，更新List
					//recyclerView.setAdapter(adapter);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 点击RecyclerView调用
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
			snssdk = snssdks.get(position);
		}
		String url = null;

		Log.d("MainActivity","onClick=============");
		switch (id){
			/*
			case R.id.ib_image://点击图片

				onClickImageButton();

				actionbarImage.setImageResource(R.drawable.camera_main_full);

				url = SNSSDK_CONTENT_IMAGE_URL;
				snssdkTask = new SnssdkTask(this);
				snssdkTask.execute(url,CATEGORY_IMAGE_FLAG_SNSSDK);
				break;
			case R.id.ib_video://点击视频

				onClickImageButton();

				actionbarVideo.setImageResource(R.drawable.video_main_full);
				url = SNSSDK_CONTENT_VIDEO_URL;
				snssdkTask = new SnssdkTask(this);
				snssdkTask.execute(url,CATEGORY_VOIDO_FLAG_SNSSDK);
				break;
			case R.id.ib_word://点击文字

				onClickImageButton();
				actionbarWord.setImageResource(R.drawable.document_main_full);
				url = SNSSDK_CONTENT_WORD_URL;
				snssdkTask = new SnssdkTask(this);
				snssdkTask.execute(url,CATEGORY_WORD_FLAG_SNSSDK);
				break;

			case R.id.ib_more://点击三点
				onClickImageButton();
				actionbarMore.setImageResource(R.drawable.chat_main_full);
				break;

			*/
			case R.id.item_fragment_common://点击内容跳转到详情页面，文字，图片，视频
				Intent intent = new Intent(this, SnssdkInfoActivity.class);
				if(position>=0) {
					intent.putExtra("snssdk", snssdks.get(position));
				}
				startActivity(intent);
				Log.d("MainActivity","item_fragment_word");
				break;
			case R.id.item_fragment_bar_good://点击顶

				if(snssdk.getUserDigg()==0){
					snssdk.setDiggCount(snssdk.getDiggCount()+1);
					snssdk.setUserDigg(1);
				}else {
					snssdk.setDiggCount(snssdk.getDiggCount()-1);
					snssdk.setUserDigg(0);
				}
				Log.d("MainActivity","item_fragment_bar_good");
				break;
			case R.id.item_fragment_bar_bad://点击踩
				if(snssdk.getUserRepin()==0){
					snssdk.setRepinCount(snssdk.getRepinCount() + 1);
					snssdk.setUserRepin(1);
				}else {
					snssdk.setRepinCount(snssdk.getRepinCount()-1);
					snssdk.setUserRepin(0);
				}
				Log.d("MainActivity","item_fragment_bar_bad");
				break;
			case R.id.item_fragment_bar_hot://点击评论，跳转到评论页面

				break;
			//点击头像
			case R.id.ib_user_icon:
				intent = new Intent(this, PersonActivity.class);
				startActivity(intent);
				break;

			//点击提交
			case R.id.ib_push_contribute:
				intent = new Intent(this, ContributeActivity.class);
				startActivity(intent);
				break;
		}
	}

	/**
	 * 当点击ImageButton时变换图片
	 */
	private void onClickImageButton(){

		//清空显示列表
		// 将原数据置空
		recyclerView.setAdapter(null);
		snssdks.clear();
		actionbarWord.setImageResource(R.drawable.document_main_one);
		actionbarImage.setImageResource(R.drawable.camera_main_one);
		actionbarVideo.setImageResource(R.drawable.video_main_one);
		actionbarMore.setImageResource(R.drawable.chat_main_one);
	}

	public void refreshData(){
		SnssdkTask snssdkTask = new SnssdkTask(this);
		snssdkTask.execute(SNSSDK_CONTENT_WORD_URL, CATEGORY_WORD_FLAG_SNSSDK);
	}

}
