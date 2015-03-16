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

	private String avatarUrl;       //用户头像
	private String name;            //用户名
	private long userId;            //  用户Id

	private String city;        //城市
	private int subscribeCount; //未知，测试的几个总为5
	private String 	description;//个人描述
	private int point;          //积分
	private int gender;         //未知
	private int followings;     //关注
	private int repinCount;     //收藏
	private int commentCount;   //评论
	private String 	screenName;
	private int ugcCount;       //投稿
	private int followers;      //粉丝
	private int newFollowers;

	/*
		"city": "未知星球",
        "subscribe_count": 5,
        "user_id": 2869703728,
        "description": "其实我很低调，不爱写签名",
        "point": 210894,
        "gender": 1,
        "followings": 1,
        "repin_count": 35,
        "comment_count": 112,
        "screen_name": "我是你院长大人",
        "ugc_count": 22,
        "avatar_url": "http://p2.pstatp.com/thumb/2239/5860406114",
        "followers": 165,
        "new_followers": 0,
        "name": "我是你院长大人"
	 */

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getName() {
		return name;
	}

	public long getUserId() {
		return userId;
	}

	public String getCity() {
		return city;
	}

	public int getSubscribeCount() {
		return subscribeCount;
	}

	public String getDescription() {
		return description;
	}

	public int getPoint() {
		return point;
	}

	public int getGender() {
		return gender;
	}

	public int getFollowings() {
		return followings;
	}

	public int getRepinCount() {
		return repinCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public String getScreenName() {
		return screenName;
	}

	public int getUgcCount() {
		return ugcCount;
	}

	public int getFollowers() {
		return followers;
	}

	public int getNewFollowers() {
		return newFollowers;
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
			city = user.getString("city");
			subscribeCount = user.getInt("subscribe_count");
			description = user.getString("description");
			point = user.getInt("point");
			gender = user.getInt("gender");
			followings = user.getInt("followings");
			repinCount = user.getInt("repin_count");
			commentCount = user.getInt("comment_count");
			screenName = user.getString("screen_name");
			ugcCount = user.getInt("ugc_count");
			followers = user.getInt("followers");
			newFollowers = user.getInt("new_followers");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}


}
