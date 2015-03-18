package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.kaixin.KaiXin;
import cn.sharesdk.renren.Renren;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import com.qianfeng.gl4study.snssdk.R;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/17
 * Email:pdsfgl@live.com
 */
public class LoginActivity extends Activity implements PlatformActionListener, Handler.Callback, View.OnClickListener {

	private CheckBox boxProtocol;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//设置隐藏ActionBar隐藏标题，图标，上界面的title栏
		ActionBar actionBar = getActionBar();
		if(actionBar!=null) {
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.hide();
		}

		ImageView imageWeixin = (ImageView) findViewById(R.id.iv_login_activity_weixin);
		imageWeixin.setOnClickListener(this);
		ImageView imageQQ = (ImageView) findViewById(R.id.iv_login_activity_qq);
		imageQQ.setOnClickListener(this);
		ImageView imageSina = (ImageView) findViewById(R.id.iv_login_activity_sina);
		imageSina.setOnClickListener(this);
		ImageView imageTencent = (ImageView) findViewById(R.id.iv_login_activity_tencent);
		imageTencent.setOnClickListener(this);
		ImageView imageRenren = (ImageView) findViewById(R.id.iv_login_activity_renren);
		imageRenren.setOnClickListener(this);
		ImageView imageKaixin = (ImageView) findViewById(R.id.iv_login_activity_kaixin);
		imageKaixin.setOnClickListener(this);
		boxProtocol = (CheckBox) findViewById(R.id.cb_login_activity_protocol);
	}

	@Override
	public void onClick(View v) {
		if(boxProtocol.isChecked()){        //选择接受协议才行
			// 这个方法必须调用，初始化ShareSDK
			ShareSDK.initSDK(this);
			switch (v.getId()){
				case R.id.iv_login_activity_weixin:
					authorize(new Wechat(this));
					break;
				case R.id.iv_login_activity_qq:
					authorize(new QQ(this));
					break;
				case R.id.iv_login_activity_sina:
					authorize(new SinaWeibo(this));
					break;
				case R.id.iv_login_activity_tencent:
					authorize(new TencentWeibo(this));
					break;
				case R.id.iv_login_activity_renren:
					authorize(new Renren(this));
					break;
				case R.id.iv_login_activity_kaixin:
					authorize(new KaiXin(this));
					break;
			}
		}else {
			Toast.makeText(this,"请选择接受协议。",Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * @param plat 希望用那个平台登录，这个地方就传递那个平台的对象
	 */
	private void authorize(Platform plat) {
		if (plat == null) {
			// 如果没有指定平台，那么现在强制选择新浪微博
			plat = new SinaWeibo(this);
		}

		//判断指定平台是否已经完成授权
		// 没有授权的时候，进行授权操作 最终会执行 showUser() 这个方法
		// 作用就是授权并且获取用户信息
		if(plat.isValid()) {
			String userId = plat.getDb().getUserId();
			if (userId != null) {
				// 用于发送注册/登陆成功的消息
				UIHandler.sendEmptyMessage(1, this);
				return;
			}
		}
		// 用于检测用户在登录时操作的状态。
		// 当用户授权成功，会进行回调，回调内部会传递一些用户的信息
		plat.setPlatformActionListener(this);
		// true不使用SSO授权，false使用SSO授权
		plat.SSOSetting(true);
		//获取用户资料
		plat.showUser(null);
	}



	/**
	 * 如果用户信息取到了，那么会发送消息到这个方法。
	 * @param msg
	 * @return
	 */
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	//////// 授权认证的时候，回调的方法

	/**
	 * 授权成功，可以获取用户信息
	 * @param platform
	 * @param i
	 * @param hashMap
	 */
	@Override
	public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
		// 获取哪一个平台的登录授权信息
		String name = platform.getName();

		// 获取特定平台下面的用户数据库
		PlatformDb db = platform.getDb();

		// 授权的用户名称
		String userName = db.getUserName();
		String userId = db.getUserId();

		SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		SharedPreferences.Editor edit = sharedPreferences.edit();
		edit.putString("name",db.getUserName());
		edit.putString("userId",db.getUserId());
		edit.apply();
		Log.d("Login", name +" ->" + userName+"->"+db.getUserIcon()+"->"+db.getUserId());

		Intent intent = new Intent(this, AuthorActivity.class);
		intent.putExtra("userId",userId);
		startActivity(intent);
	}

	/**
	 * 授权出错
	 * @param platform
	 * @param i
	 * @param throwable
	 */
	@Override
	public void onError(Platform platform, int i, Throwable throwable) {
		Toast.makeText(this,"用户登录失败，请重试。",Toast.LENGTH_LONG).show();
	}

	/**
	 * 用户取消了授权
	 * @param platform
	 * @param i
	 */
	@Override
	public void onCancel(Platform platform, int i) {
		Toast.makeText(this,"取消授权成功。",Toast.LENGTH_LONG).show();
	}
}