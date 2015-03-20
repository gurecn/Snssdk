package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.SnssdkMainAdapter;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.databases.SnssdkDatabasesManager;
import com.qianfeng.gl4study.snssdk.model.SingletonImage;
import com.qianfeng.gl4study.snssdk.model.SingletonVideo;
import com.qianfeng.gl4study.snssdk.model.SingletonWord;
import com.qianfeng.gl4study.snssdk.model.Snssdk;
import com.qianfeng.gl4study.snssdk.tasks.SnssdkTask;
import com.qianfeng.gl4study.snssdk.tasks.TaskProcessor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/12
 * Email:pdsfgl@live.com
 */
public class FlyleafActivity extends Activity implements TaskProcessor, View.OnClickListener {

	private String levelURL = "level=";
	private String categoryIdURL = "&category_id=";
	private String countURL = "&count=";

	//标记需要获取的段子的参数
	private int level = 6;
	private int category = 1;
	private int count = 20;
	private long minTime = 0;
	private boolean isFirstRun;
	private AlertDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flyleaf);

		//设置隐藏ActionBar隐藏标题，图标，上界面的title栏
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.hide();
		}

		if(getAPNType(this) == -1){//无网络连接
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = LayoutInflater.from(this);
			View view = inflater.inflate(R.layout.item_dialog_menu,null);
			ImageView wifiImage = (ImageView) view.findViewById(R.id.ig_dialog_wifi);
			ImageView netImage = (ImageView) view.findViewById(R.id.ig_dialog_net);
			Button offButton = (Button) view.findViewById(R.id.btn_dialog_off);
			wifiImage.setOnClickListener(this);
			netImage.setOnClickListener(this);
			offButton.setOnClickListener(this);
			Log.d("onCreate","无网络");
			builder.setTitle("提示：").setView(view);
			dialog = builder.create();
			dialog.show();
		}
		getNewDataFromntent();
		interTutorial();
	}

	/**
	 * 进入导航页
	 */
	private void interTutorial(){
		SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
		Log.d("FlyleafActivity","isFirstRun:"+ isFirstRun);
		Intent intent;
		if(isFirstRun){
			SharedPreferences.Editor edit;
			edit = sharedPreferences.edit();
			edit.putBoolean("isFirstRun", false);
			edit.apply();
			intent = new Intent(this, TutorialActivity.class);
			startActivity(intent);
			FlyleafActivity.this.finish();
		}else {
			//等待5秒进入主界面
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(FlyleafActivity.this, MainActivity.class);
					startActivity(intent);
					FlyleafActivity.this.finish();
				}
			}, 4000);
		}
	}
	@Override
		 public void onClick(View v) {
		int id = v.getId();
		switch (id){
			case R.id.ig_dialog_wifi:
				((ImageView)v).setImageResource(R.drawable.ic_dialog_wifi_on);
				setStateWIFI();
				break;
			case R.id.ig_dialog_net:
				((ImageView)v).setImageResource(R.drawable.ic_dialog_net_on);
				setMobileDataStatus(this,true);
				break;
			case R.id.btn_dialog_off:
				break;
		}

		//休息3秒，消失对话框
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
			}
		}, 3000);
		dialog.dismiss();

	}


	private void getNewDataFromntent(){
		SharedPreferences sharedPreferences  = getSharedPreferences("config", MODE_PRIVATE);
		long minWordTime = sharedPreferences.getLong("minTimeWord", 0);
		long minImageTime = sharedPreferences.getLong("minTimeImage", 0);
		StringBuilder stringBuilder = new StringBuilder();
		SnssdkTask snssdkTask  = new SnssdkTask(this);
		String minTimeURL = "&min_time=";
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(6)//推荐分类
				.append(categoryIdURL).append(1)//文本段子
				.append(countURL).append(count)   //返回20个数据
				.append(minTimeURL).append(minWordTime);
		snssdkTask.execute(stringBuilder.toString(), 1 + "");

		snssdkTask  = new SnssdkTask(this);
		stringBuilder.append(Constant.SNSSDK_CONTENT_LIST_URL)
				.append(levelURL).append(6)//推荐分类
				.append(categoryIdURL).append(2)//图片段子
				.append(countURL).append(count)   //返回20个数据
				.append(minTimeURL).append(minImageTime);
		snssdkTask.execute(stringBuilder.toString(), 2 + "");
	}
	@Override
	public void processResult(JSONObject result, String flag) {
		Log.d("processResult","加载  "+isFirstRun);
		if (result != null) {      //段子列表
			Log.d("processResult","加载1  "+isFirstRun);
			try {
				String resultFlag = result.getString("message");
				if ("success".equals(resultFlag)) {
					JSONObject data = result.getJSONObject("data");
					JSONArray dataJSONArray = data.getJSONArray("data");
					int type = Integer.parseInt(flag);
					for (int i = 0; i < dataJSONArray.length(); i++) {
						JSONObject jsonObject = dataJSONArray.getJSONObject(i);
						Snssdk snssdk = new Snssdk();
						snssdk.parseInformation(jsonObject, type);
						ContentValues values = snssdk.getContentValues();
						SnssdkDatabasesManager.createInstance(this).saveSnssdk(values,snssdk.getGroup_id());
					}
					saveTimeToSingleton(this,type,data.getLong("min_time"),data.getLong("max_time"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * 网络获取的数据保存到单例和配置文件中
	 *
	 * @param type          段子更新的类型
	 * @param minTime1      返回的时间
	 * @param maxTime1      返回的时间
	 */
	private void saveTimeToSingleton(Context context,int type,long minTime1, long maxTime1) {

		SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		SharedPreferences.Editor edit = sharedPreferences.edit();
		if (type == 1) {
			edit.putLong("minTimeWord", minTime1);
			edit.putLong("maxTimeWord", maxTime1);
		} else if (type == 2) {
			edit.putLong("minTimeImage", minTime1);
			edit.putLong("maxTimeImage", maxTime1);
		}
		edit.apply();
	}


	/**
	 * 打开移动网络
	 * @return
	 */
	public void setMobileDataStatus(Context context, boolean enabled)
	{
		ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		// ConnectivityManager类
		Class<?> conMgrClass = null;
		// ConnectivityManager类中的字段
		Field iConMgrField = null;
		// IConnectivityManager类的引用
		Object iConMgr = null;
		// IConnectivityManager类
		Class<?> iConMgrClass = null;
		// setMobileDataEnabled方法
		Method setMobileDataEnabledMethod = null;
		try {
			// 取得ConnectivityManager类
			conMgrClass = Class.forName(conMgr.getClass().getName());
			// 取得ConnectivityManager类中的对象Mservice
			iConMgrField = conMgrClass.getDeclaredField("mService");
			// 设置mService可访问
			iConMgrField.setAccessible(true);
			// 取得mService的实例化类IConnectivityManager
			iConMgr = iConMgrField.get(conMgr);
			// 取得IConnectivityManager类
			iConMgrClass = Class.forName(iConMgr.getClass().getName());
			// 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法
			setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);
			// 设置setMobileDataEnabled方法是否可访问
			setMobileDataEnabledMethod.setAccessible(true);
			// 调用setMobileDataEnabled方法
			setMobileDataEnabledMethod.invoke(iConMgr, enabled);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开WIFI
	 * @return
	 */
	private void setStateWIFI(){
		WifiManager  wm =(WifiManager)getSystemService(Context.WIFI_SERVICE);
		wm.setWifiEnabled(true);
	}

	/**
	 * 获取当前的网络状态
	 * @param context
	 * @return      -1：没有网络 1：WIFI网络2：wap网络3：net网络
	 */
	public static int getAPNType(Context context){
		int netType = -1;
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo==null){
			return netType;
		}
		int nType = networkInfo.getType();
		if(nType==ConnectivityManager.TYPE_MOBILE){
			Log.e("networkInfo.getExtraInfo()", "networkInfo.getExtraInfo() is "+networkInfo.getExtraInfo());
			if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){
				netType = 3;
			}
			else{
				netType = 2;
			}
		}
		else if(nType== ConnectivityManager.TYPE_WIFI){
			netType = 1;
		}
		return netType;
	}

}