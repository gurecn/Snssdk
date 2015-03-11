package com.qianfeng.gl4study.snssdk.activity;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkMainAdapter;
import com.qianfeng.gl4study.snssdk.model.Snssdk;
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

	public static final String SNSSDK_CONTENT_WORD_URL = "http://ic.snssdk.com/2/essay/zone/category/data/?category_id=1&level=6&count=10&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6";
	public static final String SNSSDK_CONTENT_IMAGE_URL = "http://ic.snssdk.com/2/essay/zone/category/data/?category_id=2&level=6&count=30&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6";
	public static final String SNSSDK_CONTENT_VIDEO_URL = "http://ic.snssdk.com/2/essay/zone/category/data/?category_id=3&level=6&count=10&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6";
	public static final String CATEGORY_WORD_FLAG_SNSSDK = "1";
	public static final String CATEGORY_IMAGE_FLAG_SNSSDK = "2";
	public static final String CATEGORY_VOIDO_FLAG_SNSSDK = "3";
	private LinkedList<Snssdk> snssdks;     //加载进内存的端子集合
	private SnssdkMainAdapter adapter;      //显示段子的Adapter
	private ListView listViewSnssdk;
	private SnssdkTask snssdkTask;
	private MenuItem itemWord;
	private MenuItem itemImage;
	private MenuItem itemVideo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listViewSnssdk = (ListView) findViewById(R.id.recycle_view);//主界面显示段子列表
		snssdks = new LinkedList<Snssdk>();
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
		snssdkTask.execute(SNSSDK_CONTENT_WORD_URL, CATEGORY_WORD_FLAG_SNSSDK);

		adapter = new SnssdkMainAdapter(this, snssdks);
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
		String url = null;
		switch (itemId){
			case R.id.menu_word:
				Toast.makeText(this,"word",Toast.LENGTH_LONG).show();
				onClickImageButton();
				item.setIcon(R.drawable.document_main_full);
				url = SNSSDK_CONTENT_WORD_URL;
				snssdkTask = new SnssdkTask(this);
				snssdkTask.execute(url,CATEGORY_WORD_FLAG_SNSSDK);
				break;
			case R.id.menu_image:
				Toast.makeText(this,"image",Toast.LENGTH_LONG).show();
				onClickImageButton();
				item.setIcon(R.drawable.camera_main_full);
				url = SNSSDK_CONTENT_IMAGE_URL;
				snssdkTask = new SnssdkTask(this);
				snssdkTask.execute(url,CATEGORY_IMAGE_FLAG_SNSSDK);
				break;
			case R.id.menu_video:
				Toast.makeText(this,"video",Toast.LENGTH_LONG).show();
				onClickImageButton();
				item.setIcon(R.drawable.video_main_full);
				url = SNSSDK_CONTENT_VIDEO_URL;
				snssdkTask = new SnssdkTask(this);
				snssdkTask.execute(url,CATEGORY_VOIDO_FLAG_SNSSDK);
				break;
			case R.id.menu_find:
				Toast.makeText(this,"find",Toast.LENGTH_LONG).show();
				break;
			case R.id.menu_examine:
				Toast.makeText(this,"examine",Toast.LENGTH_LONG).show();
				break;
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
			snssdk = snssdks.get(position);
			Log.d("MainActivity","onClick=============");
			switch (id){
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
	}

	/**
	 * 当点击ActionBar时清空显示数据并变换图片
	 */
	private void onClickImageButton(){

		//清空显示列表,将原数据置空
		listViewSnssdk.setAdapter(null);
		snssdks.clear();
		//首先将所有图标设置成暗色，然后点击哪一个哪一个变色即可
		itemWord.setIcon(R.drawable.document_main_one);
		itemImage.setIcon(R.drawable.camera_main_one);
		itemVideo.setIcon(R.drawable.video_main_one);
	}
}
