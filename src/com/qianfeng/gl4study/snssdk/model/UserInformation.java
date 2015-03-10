package com.qianfeng.gl4study.snssdk.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/5
 * Email:pdsfgl@live.com
 */
public class UserInformation implements Serializable {

	private String avatarUrl;   //用户头像
	private String name;//用户名
	private long userId;    //  用户Id
	private boolean userVerified;   //用户是否验证

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getName() {
		return name;
	}

	public long getUserId() {
		return userId;
	}

	public boolean isUserVerified() {
		return userVerified;
	}


	/*
	"user": {
		"avatar_url": "http://p2.pstatp.com/thumb/953/2658476120",
				"user_id": 1791732391,
				"name": "随遇而安",
				"user_verified": false
	},
	*/

	public UserInformation parseInformation(JSONObject user){

		try {
			name = user.getString("name");
			userId = user.getLong("user_id");
			avatarUrl = user.getString("avatar_url");
			userVerified = user.getBoolean("user_verified");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}


}
