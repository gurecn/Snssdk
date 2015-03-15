package com.qianfeng.gl4study.snssdk.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/7
 * Email:pdsfgl@live.com
 */
public class Discuss {
	/*
			"uid": 0,
			"text": "大师兄有勾人的眼神，豹纹短裙，靴子，钢管，夫复何求。。。",
			"create_time": 1411872704,
			"user_digg": 0,
			"id": 3564228175,
			"user_bury": 0,
			"user_profile_url": "",
			"user_id": 1499882353,
			"bury_count": 0,
			"description": "这个用户很懒，神马都木有写",
			"digg_count": 5,
			"user_verified": false,
			"platform": "feifei",
			"user_name": "濕淺藍銫d愛",
			"user_profile_image_url": "http://p2.pstatp.com/thumb/306/1973981295"
	*/
	private long createTime;
	private String content;     //内容
	private int diggCount;      //顶统计
	private int userDigg;       //用户是否顶
	private long userId;
	private String userName;
	private String userVerified;

	public Discuss parseInformation(JSONObject group){

		if(group!=null){
			try {

				content = group.getString("text");
				createTime = group.getLong("create_time");
				diggCount = group.getInt("digg_count");
				userDigg = group.getInt("user_digg");
				userId = group.getLong("user_id");
				userName = group.getString("user_name");
				userVerified = group.getString("user_profile_image_url");

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return this;
	}


	public long getCreateTime() {
		return createTime;
	}

	public String getContent() {
		return content;
	}

	public int getDiggCount() {
		return diggCount;
	}

	public int getUserDigg() {
		return userDigg;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserVerified() {
		return userVerified;
	}
}
