package com.qianfeng.gl4study.snssdk.activity;


import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkMainAdapter;
import com.qianfeng.gl4study.snssdk.animation.MyAnimation;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.databases.SnssdkDatabasesManager;
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

public class MainActivity extends Activity implements TaskProcessor, View.OnClickListener,PullToRefreshBase.OnRefreshListener2<ListView> {


	private SnssdkMainAdapter adapter;      //显示段子的Adapter
	private PullToRefreshListView refreshListView;
	private SnssdkTask snssdkTask;
	private MenuItem itemWord;
	private MenuItem itemImage;
	private MenuItem itemVideo;

	private String levelURL = "level=";
	private String categoryIdURL = "&category_id=";
	private String countURL = "&count=";
	private String minTimeURL = "&min_time=";
	private String maxTimeURL = "&max_time=";

	//标记需要获取的段子的参数
	private int level = 6;
	private int category = 1;
	private int count = 30;
	private long minTime = 0;
	private long maxTime = 0;
	private ListView listViewSnssdk;
	private PopupWindow popupWindow;
	private TextView category2;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		doPullToRefreshList();
		//设置隐藏ActionBar隐藏标题，图标，上界面的title栏
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.hide();

		//自定义标题栏的组件获取
		// 用户头像
		ImageView topUser = (ImageView) findViewById(R.id.ib_user_icon);
		topUser.setOnClickListener(this);
		//点击分类
		category2 = (TextView) findViewById(R.id.txt_main_level);
		category2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initPopupMenu();
			}
		});

		//投稿图标
		ImageView topContribute = (ImageView) findViewById(R.id.ib_push_contribute);
		topContribute.setOnClickListener(this);

		LinkedList<Snssdk> snssdks = SnssdkDatabasesManager.createInstance(this).getSnssdkCollect(1);
		SingletonWord.getInstance().addAllSnssdks(snssdks);
		adapter = new SnssdkMainAdapter(this, SingletonWord.getSnssdks());
		listViewSnssdk.setAdapter(adapter);
	}


	/**
	 * 初始化分类2的PopupWindow
	 */
	private void initPopupMenu() {
		LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = lay.inflate(R.layout.item_popup_mian_activity, null);
		popupWindow = new PopupWindow(v, getApplicationContext().getResources().getDisplayMetrics().widthPixels / 3,
				getApplicationContext().getResources().getDisplayMetrics().heightPixels / 4, true);

		//设置整个popupwindow的样式。
		popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.popup_menu_bg));
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();

		//popupWindow.showAsDropDown(category2);
		popupWindow.showAtLocation(category2,Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,200);
	}

	/**
	 * 上下拉刷新的实现
	 */
	private void doPullToRefreshList() {
		refreshListView = (PullToRefreshListView) findViewById(R.id.recycle_view);//主界面显示段子列表
		listViewSnssdk = refreshListView.getRefreshableView();
		refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
		refreshListView.setPullLabel("继续下拉");
		refreshListView.setBackgroundResource(R.drawable.main_rg_bg);
		refreshListView.setRefreshingLabel("松手刷新");
		refreshListView.setReleaseLabel("正在刷新....");
		refreshListView.setOnRefreshListener(this);
	}

	/**
	 * 下拉刷新
	 *
	 * @param refreshView
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

		//从文件中读取数据
		SharedPreferences sharedPreferences  = getSharedPreferences("config", MODE_PRIVATE);
		if (category == 1) {
			minTime = sharedPreferences.getLong("minTimeWord", 0);
		} else if (category == 2) {
			minTime = sharedPreferences.getLong("minTimeImage", 0);
		}else if (category == 3) {
			minTime = sharedPreferences.getLong("minTimeVideo", 0);
		}
		StringBuilder stringBuilder = new StringBuilder();
		snssdkTask = new SnssdkTask(this);
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(level)//推荐分类
				.append(categoryIdURL).append(category)//文本段子
				.append(countURL).append(count)   //返回20个数据
				.append(minTimeURL).append(minTime);
		Log.d("Time1", "使用minTime1===" + minTime);
		snssdkTask.execute(stringBuilder.toString(), category + "");

	}

	/**
	 * 上拉加载
	 *
	 * @param refreshView
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

		//TODO 配置文件读取时间
		//从文件中读取数据
		SharedPreferences sharedPreferences  = getSharedPreferences("config", MODE_PRIVATE);
		if (category == 1) {
			maxTime = sharedPreferences.getLong("maxTimeWord", 0);
		} else if (category == 2) {
			maxTime = sharedPreferences.getLong("maxTimeImage", 0);
		}else if (category == 3) {
			maxTime = sharedPreferences.getLong("maxTimeVideo", 0);
		}
		Log.d("Time1", "使用maxTime1===" + maxTime);
		snssdkTask = new SnssdkTask(this);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(level)//推荐分类
				.append(categoryIdURL).append(category)
				.append(countURL).append(count)   //返回20个数据
				.append(maxTimeURL).append(maxTime);
		snssdkTask.execute(stringBuilder.toString(), category + "");
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

	/**
	 * 菜单的监听事件
	 *
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		boolean ret = false;
		int itemId = item.getItemId();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(level)     //推荐分类
				.append(countURL).append(count);   //返回20个数据
		switch (itemId) {
			case R.id.menu_word:
				onClickImageButton();
				item.setIcon(R.drawable.document_main_full);
				category = Constant.TYPE_1_CATEGORY_ID_WORD_FLAG_SNSSDK;
				break;
			case R.id.menu_image:
				onClickImageButton();
				item.setIcon(R.drawable.camera_main_full);
				category = Constant.TYPE_1_CATEGORY_ID_IMAGE_FLAG_SNSSDK;
				break;
			case R.id.menu_video:
				onClickImageButton();
				item.setIcon(R.drawable.video_main_full);
				category = Constant.TYPE_1_CATEGORY_ID_VIDEO_FLAG_SNSSDK;
				break;
		}

		if (itemId == R.id.menu_find) {
			Toast.makeText(this, "find", Toast.LENGTH_LONG).show();
		} else if (itemId == R.id.menu_examine) {
			Toast.makeText(this, "examine", Toast.LENGTH_LONG).show();
		} else {
			snssdkTask = new SnssdkTask(this);
			stringBuilder.append(categoryIdURL).append(category);//文本段子
			snssdkTask.execute(stringBuilder.toString(), category + "");
		}
		ret = super.onOptionsItemSelected(item);
		return ret;
	}

	/**
	 * 异步任务接口回调，所有数据的下载均使用该异步任务<br/>
	 * 通过该回调函数的第二个参数判断开启回调的类别
	 *
	 * @param result 返回的下载数据
	 * @param flag   开启的异步类别
	 */
	@Override
	public void processResult(JSONObject result, String flag) {
		if (result != null) {      //段子列表
			try {
				String resultFlag = result.getString("message");
				if ("success".equals(resultFlag)) {
					LinkedList<Snssdk> snssdks = new LinkedList<Snssdk>();
					JSONObject data = result.getJSONObject("data");
					String tip = data.getString("tip");
					JSONArray dataJSONArray = data.getJSONArray("data");
					int type = Integer.parseInt(flag);
					for (int i = 0; i < dataJSONArray.length(); i++) {
						JSONObject jsonObject = dataJSONArray.getJSONObject(i);
						Snssdk snssdk = new Snssdk();
						snssdk.parseInformation(jsonObject, type);
						ContentValues values = snssdk.getContentValues();
						SnssdkDatabasesManager.createInstance(this).saveSnssdk(values);
						snssdks.add(snssdk);
					}
					saveTimeToSingleton(this,type, snssdks, data.getLong("min_time"), data.getLong("max_time"));
					Log.d("MainActivity", "下载完成=============" + snssdks.size());
					//数据添加完成，更新List
					listViewSnssdk.setAdapter(adapter);
					refreshListView.onRefreshComplete();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 网络获取的数据保存到单例和配置文件中
	 *
	 * @param type
	 * @param snssdks
	 * @param minTime1
	 * @param maxTime1
	 */
	private void saveTimeToSingleton(Context context,int type, LinkedList<Snssdk> snssdks, long minTime1, long maxTime1) {

		SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		SharedPreferences.Editor edit = sharedPreferences.edit();
		if (type == 1) {
			SingletonWord.getInstance().removeAll();
			SingletonWord.getInstance().addAllSnssdks(snssdks);
			adapter = new SnssdkMainAdapter(context, snssdks);
			edit.putLong("minTimeWord", minTime1);
			edit.putLong("maxTimeWord", maxTime1);
		} else if (type == 2) {
			SingletonImage.getInstance().removeAll();
			SingletonImage.getInstance().addAllSnssdks(snssdks);
			adapter = new SnssdkMainAdapter(context, SingletonImage.getSnssdks());
			edit.putLong("minTimeImage", minTime1);
			edit.putLong("maxTimeImage", maxTime1);
		} else if (type == 18) {
			SingletonVideo.getInstance().addAllSnssdks(snssdks);
			adapter = new SnssdkMainAdapter(context, SingletonVideo.getSnssdks());
			edit.putLong("minTimeVideo", minTime1);
			edit.putLong("maxTimeVideo", maxTime1);
		}
		edit.commit();
	}

	/**
	 * 点击listViewSnssdk调用
	 *
	 * @param v
	 */
	@Override
	public void onClick(View v) {

		int id = v.getId();
		Object tag = v.getTag();
		if (id == R.id.ib_user_icon) {
			Intent intent = new Intent(this, PersonActivity.class);
			startActivity(intent);
		} else if (id == R.id.ib_push_contribute) {
			Intent intent = new Intent(this, ContributeActivity.class);
			startActivity(intent);
		}
		if (tag != null) {

			Log.d("MainActivity", "onClick=============");
			clickDiscussBar(id, (Integer) tag, v);
		}
	}

	/**
	 * 点击段子内容或评论条
	 *
	 * @param id
	 * @param position
	 */
	private void clickDiscussBar(int id, int position, View v) {
		Snssdk snssdk = null;
		if (category == 1) {
			snssdk = SingletonWord.getSnssdks().get(position);
		} else if (category == 2) {
			snssdk = SingletonImage.getSnssdks().get(position);
		} else if (category == 18) {
			snssdk = SingletonVideo.getSnssdks().get(position);
		}
		switch (id) {
			case R.id.item_fragment_common://点击段子内容跳转到详情页面
				skipToInfo(position);
				break;
			case R.id.item_fragment_bar_good_ll://点击赞
				if (snssdk.getUser_repin() == 1) {
					makeToastForBar("你已经踩了，做人不要矛盾哦");
				} else if (snssdk.getUser_digg() == 0) {
					snssdk.setDigg_count(snssdk.getDigg_count() + 1);
					snssdk.setUser_digg(1);
					TextView txtGood = (TextView) v.findViewById(R.id.item_fragment_bar_good_txt);
					MyAnimation.addOneAnimation(this, txtGood);
				}
				adapter.notifyDataSetChanged();
				Log.d("MainActivity", "item_fragment_bar_good");
				break;
			case R.id.item_fragment_bar_bad_ll://点击踩
				if (snssdk.getUser_digg() == 1) {
					makeToastForBar("你已经顶了，做人不要矛盾哦");
				} else if (snssdk.getUser_repin() == 0) {
					snssdk.setRepin_count(snssdk.getRepin_count() + 1);
					snssdk.setUser_repin(1);
					TextView txtBad = (TextView) v.findViewById(R.id.item_fragment_bar_bad_txt);
					MyAnimation.addOneAnimation(this, txtBad);
				}
				adapter.notifyDataSetChanged();
				Log.d("MainActivity", "item_fragment_bar_bad");
				break;
			case R.id.item_fragment_bar_hot_ll://点击评论，跳转到分享页面
				adapter.notifyDataSetChanged();
				break;
		}
	}

	/**
	 * 重复点击赞或踩时弹出提醒
	 *
	 * @param string
	 */
	private void makeToastForBar(String string) {
		Toast toast = Toast.makeText(this, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 跳转到段子详情
	 *
	 * @param position
	 */
	private void skipToInfo(int position) {
		Intent intent = new Intent(this, SnssdkInfoActivity.class);
		if (position >= 0) {
			intent.putExtra("position", position);
			intent.putExtra("category", category);
		}
		startActivity(intent);
		Log.d("MainActivity", "item_fragment_word");
	}

	/**
	 * 当点击ActionBar时清空显示数据并变换图片
	 */
	private void onClickImageButton() {

		//清空显示列表,将原数据置空
		listViewSnssdk.setAdapter(null);
		//snssdks.clear();
		//首先将所有图标设置成暗色，然后点击哪一个哪一个变色即可
		itemWord.setIcon(R.drawable.document_main_one);
		itemImage.setIcon(R.drawable.camera_main_one);
		itemVideo.setIcon(R.drawable.video_main_one);
	}


}
