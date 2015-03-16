package com.qianfeng.gl4study.snssdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.model.UserInformation;
import com.qianfeng.gl4study.snssdk.tasks.SnssdkTask;
import com.qianfeng.gl4study.snssdk.tasks.TaskProcessor;
import com.qianfeng.gl4study.snssdk.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/16
 * Email:pdsfgl@live.com
 */
public class AuthorActivity extends Activity implements TaskProcessor {



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author);
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.hide();
		}
		Intent intent = getIntent();
		long userId = intent.getLongExtra("userId",0);
		SnssdkTask userTask = new SnssdkTask(this);
		userTask.execute(Constant.SNSSDK_USER_INFOMATION_URL+userId,0+"");

	}

	/**
	 * 异步回调
	 * @param result
	 * @param flag
	 */
	@Override
	public void processResult(JSONObject result, String flag) {
		if("0".equals(flag)&&result!=null){
			String message = null;
			try {
				message = result.getString("message");
				if("success".equals(message)){
					JSONObject jsonObject = result.getJSONObject("data");
					UserInformation userInformation = new UserInformation();
					userInformation.parseInformation(jsonObject);
					displayInformation(userInformation);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 将异步获取的信息显示到组件上
	 */
	private void displayInformation(UserInformation userInformation){

		TextView userName = (TextView) findViewById(R.id.author_ig_user_name);//姓名
		userName.setText(userInformation.getName());
		TextView titleName = (TextView) findViewById(R.id.author_txt_name);//姓名
		titleName.setText(userInformation.getName());
		ImageView userIcon = (ImageView) findViewById(R.id.author_ig_user_icon);//头像
		Utils.loaderImage(-1, userIcon, userInformation.getAvatarUrl());
		TextView watchCount = (TextView) findViewById(R.id.author_txt_watching_count);//关注的人数
		watchCount.setText(userInformation.getFollowings()+"");
		TextView followCount = (TextView) findViewById(R.id.author_txt_follow_count);//粉丝数
		followCount.setText(userInformation.getFollowers()+"");
		TextView pointCount = (TextView) findViewById(R.id.author_txt_point_count);//积分
		pointCount.setText(userInformation.getPoint()+"");
		TextView description = (TextView) findViewById(R.id.author_txt_description);//描述
		description.setText(userInformation.getDescription());
		TextView cityLoaction = (TextView) findViewById(R.id.author_txt_location_city);//收藏数量
		cityLoaction.setText(userInformation.getCity());

		TextView publishList = (TextView) findViewById(R.id.author_txt_publish_count);//投稿数量
		publishList.setText(userInformation.getUgcCount()+"");
		TextView discussList = (TextView) findViewById(R.id.author_txt_discuss_count);//评论数量
		discussList.setText(userInformation.getCommentCount()+"");
		TextView favoritetList = (TextView) findViewById(R.id.author_txt_favoritet_count);//收藏数量
		favoritetList.setText(userInformation.getRepinCount()+"");


		RelativeLayout publish = (RelativeLayout) findViewById(R.id.author_rl_publish_list);//投稿列表

		RelativeLayout discuss = (RelativeLayout) findViewById(R.id.author_rl_discuss_list);//评论列表

		RelativeLayout favoritet = (RelativeLayout) findViewById(R.id.author_rl_favoritet_list);//收藏列表
	}

}