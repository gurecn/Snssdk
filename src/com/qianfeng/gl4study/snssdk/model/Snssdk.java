package com.qianfeng.gl4study.snssdk.model;

import org.json.JSONArray;
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
public class Snssdk implements Serializable{

	//创建时间
	private long createTime;
	private long groupId;
	private int favoriteCount;  //收藏次数
	private String content;     //内容
	private int repinCount;     //踩统计
	private int diggCount;      //顶统计
	private int userRepin;      //用户是否踩
	private int userDigg;       //用户是否顶
	private UserInformation authorInformation;    //用户信息

	private int snssdkType;     //段子类型  1,文章 2,图片 3，视频

	private String imageUrl;    //图片连接

	private String voideUrl;    //视频连接

	public int getSnssdkType() {
		return snssdkType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public long getGroupId() {
		return groupId;
	}

/*
	"create_time": 1411365213,
	"favorite_count": 1641,
	"user_bury": 0,
	"user_favorite": 0,
	"bury_count": 2143,
	"share_url": "http://toutiao.com/group/3551461874/?iid=2337593504&app=joke_essay",
	"label": 1,
	"content": "今天看到七岁的儿子在画画，画了一只小鸡，我问他:儿子，你这画的是什么啊？儿子说:我画的是老鹰捉小鸡。我问他:那鹰去哪了？儿子回:那英去参加中国好声音了。现在的孩子…",
	"comment_count": 111,
	"status": 3,
	"has_comments": 0,
	"go_detail_count": 944,
	"status_desc": "已发表到热门列表",
	"user": {
	"avatar_url": "http://p2.pstatp.com/thumb/953/2658476120",
		"user_id": 1791732391,
		"name": "随遇而安",
		"user_verified": false
		},
	"user_digg": 0,
	"group_id": 3551461874,
	"level": 4,
	"repin_count": 1641,
	"digg_count": 20001,
	"has_hot_comments": 1,
	"user_repin": 0,
	"category_id": 1

	*/

	public void setRepinCount(int repinCount) {
		this.repinCount = repinCount;
	}

	public void setDiggCount(int diggCount) {
		this.diggCount = diggCount;
	}

	public void setUserRepin(int userRepin) {
		this.userRepin = userRepin;
	}

	public void setUserDigg(int userDigg) {
		this.userDigg = userDigg;
	}

	public long getCreateTime() {
		return createTime;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public String getContent() {
		return content;
	}

	public int getRepinCount() {
		return repinCount;
	}

	public int getDiggCount() {
		return diggCount;
	}

	public int getUserRepin() {
		return userRepin;
	}

	public int getUserDigg() {
		return userDigg;
	}

	public UserInformation getAuthorInformation() {
		return authorInformation;
	}

	public Snssdk parseInformation(JSONObject group,int type){

		if(group!=null){
			try {
				snssdkType = type;
				content = group.getString("content");
				createTime = group.getLong("create_time");
				groupId = group.getLong("group_id");
				favoriteCount = group.getInt("favorite_count");
				repinCount = group.getInt("repin_count");
				diggCount = group.getInt("digg_count");
				userDigg = group.getInt("user_digg");
				userRepin = group.getInt("user_repin");
				authorInformation = new UserInformation();
				authorInformation.parseInformation(group.getJSONObject("user"));

				if(type == 2) {
					JSONArray jsonArray = group.getJSONObject("large_image").getJSONArray("url_list");
					imageUrl = jsonArray.getJSONObject(0).getString("url");
				}

				/*
				//添加视频连接
				else if(type == 3){
					JSONArray jsonArray = group.getJSONObject("large_image").getJSONArray("url_list");
					imageUrl = jsonArray.getJSONObject(0).getString("url");
				}
				*/

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return this;
	}

}