package com.qianfeng.gl4study.snssdk.model;

import android.content.ContentValues;
import android.database.Cursor;
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

	private String content  ; 
	private long group_id  ;             //段子Id
	private int category_type  ;  		//段子分类1
	private int level  ;  					//段子分类2
	private int has_comments  ; 			//有无神评论
	private int comment_count  ; 			//总评论
	private int repin_count  ;  			//转发次数
	private int digg_count  ;  		    //赞次数
	private int bury_count  ;  			//踩次数
	private int user_favorite  ;  		//用户是否喜欢
	private int user_digg  ;  			//是否赞
	private int user_bury  ; 				//是否踩
	private int user_repin  ; 			//是否转发

	private long user_id  ; 		//作者Id
	private String avatar_url  ; 	//作者头像	*
	private String name  ;  	    //作者名					*
	private long user_verified  ; 	//作者是否认证

	private long comment_id  ; 	            //神评论Id
	private String comment_name; 		        //神评论作者名
	private String comment_profile_image_url;    //神评论作者头像
	private int digg_count_comment  ;  		    //神评论赞
	private int is_digg  ; 		                //本人是否赞
	private String text  ; 	                    //神评论内容
	
	//有数据库得出需要的参数
	 /*

			"content": ”于是等他选好手套，阿紫小声的的说：“帅哥，我还想帮我男朋友买一盒TT。。。”",
			"group_id": 4048033285,			//段子Id
			"category_type": 1,				//段子分类1
			"level": 4,						//段子分类2
			"has_comments": 1,				//有无神评论
			"comment_count": 781,			//总评论
			"repin_count": 2135,			//转发次数
			"digg_count": 37450,			//赞次数
			"bury_count": 4693,				//踩次数
			"user_favorite": 0,				//用户是否喜欢
			"user_digg": 0,					//是否赞
			"user_bury": 0,					//是否踩
			"user_repin": 0,				//是否转发

			"user_id": 3112621377,			//作者Id
			"avatar_url": "http://p2.pstatp.com/thumb/1322/2062247312",	//作者头像	*
			"name": "女爱女",				//作者名					*
			"user_verified": false			//作者是否认证

			"comment_id": 4048167546,		//神评论Id
			"comment_name": "星爷sjx",			//神评论作者名
			"comment_profile_image_url": "http://p2.pstatp.com/thumb/1222/7013180749"	神评论作者头像
			"digg_count": 16548,			//神评论赞
			"is_digg": 0,					//本人是否赞
			"text": "帅哥:靓女，这里不方便啦，要去我家从福尔马林里拿出来试了。",	//神评论内容

 */


	public String getContent() {
		return content;
	}

	public long getGroup_id() {
		return group_id;
	}

	public int getCategory_type() {
		return category_type;
	}

	public int getLevel() {
		return level;
	}

	public int getHas_comments() {
		return has_comments;
	}

	public int getComment_count() {
		return comment_count;
	}

	public int getRepin_count() {
		return repin_count;
	}

	public int getDigg_count() {
		return digg_count;
	}

	public int getBury_count() {
		return bury_count;
	}

	public int getUser_favorite() {
		return user_favorite;
	}

	public int getUser_digg() {
		return user_digg;
	}

	public int getUser_bury() {
		return user_bury;
	}

	public int getUser_repin() {
		return user_repin;
	}

	public long getUser_id() {
		return user_id;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public String getName() {
		return name;
	}

	public long getUser_verified() {
		return user_verified;
	}

	public long getComment_id() {
		return comment_id;
	}

	public String getComment_name() {
		return comment_name;
	}

	public String getComment_profile_image_url() {
		return comment_profile_image_url;
	}

	public int getDigg_count_comment() {
		return digg_count_comment;
	}

	public int getIs_digg() {
		return is_digg;
	}

	public String getText() {
		return text;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public void setCategory_type(int category_type) {
		this.category_type = category_type;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setHas_comments(int has_comments) {
		this.has_comments = has_comments;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public void setRepin_count(int repin_count) {
		this.repin_count = repin_count;
	}

	public void setDigg_count(int digg_count) {
		this.digg_count = digg_count;
	}

	public void setBury_count(int bury_count) {
		this.bury_count = bury_count;
	}

	public void setUser_favorite(int user_favorite) {
		this.user_favorite = user_favorite;
	}

	public void setUser_digg(int user_digg) {
		this.user_digg = user_digg;
	}

	public void setUser_bury(int user_bury) {
		this.user_bury = user_bury;
	}

	public void setUser_repin(int user_repin) {
		this.user_repin = user_repin;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUser_verified(long user_verified) {
		this.user_verified = user_verified;
	}

	public void setComment_id(long comment_id) {
		this.comment_id = comment_id;
	}

	public void setComment_name(String comment_name) {
		this.comment_name = comment_name;
	}

	public void setComment_profile_image_url(String comment_profile_image_url) {
		this.comment_profile_image_url = comment_profile_image_url;
	}

	public void setDigg_count_comment(int digg_count_comment) {
		this.digg_count_comment = digg_count_comment;
	}

	public void setIs_digg(int is_digg) {
		this.is_digg = is_digg;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 将JSONObject数据解析成段子信息
	 * @param group
	 * @param type
	 * @return
	 */
	public Snssdk parseInformation(JSONObject group,int type){

		if(group!=null){
			try {
				content = group.getString("content");
				group_id = group.getLong("group_id");
				category_type = type;
				level = group.getInt("level");
				has_comments = group.getInt("has_comments");
				comment_count = group.getInt("comment_count");
				repin_count = group.getInt("repin_count");
				digg_count = group.getInt("digg_count");
				bury_count = group.getInt("bury_count");
				user_favorite = group.getInt("user_favorite");
				user_digg = group.getInt("user_digg");
				user_bury = group.getInt("user_bury");
				user_repin = group.getInt("user_repin");

				user_id = group.getLong("user_id");
				avatar_url = group.getString("avatar_url");
				name = group.getString("name");
				user_verified = group.getInt("user_verified");
				comment_id = group.getLong("comment_id");
				comment_name = group.getString("comment_name");
				comment_profile_image_url = group.getString("comment_profile_image_url");
				digg_count = group.getInt("digg_count");
				is_digg = group.getInt("is_digg");
				text = group.getString("text");

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return this;
	}


	/**
	 * 将段子信息转化成键值对，存储数据库
	 * @return
	 */
	public ContentValues getContentValues(){
		ContentValues values = new ContentValues();

		values.put("content",content);
		values.put("group_id",group_id);
		values.put("category_type",category_type);
		values.put("level",level);
		values.put("has_comments",has_comments);
		values.put("comment_count",comment_count);
		values.put("repin_count",repin_count);
		values.put("digg_count",digg_count);
		values.put("bury_count",bury_count);
		values.put("user_favorite",user_favorite);
		values.put("user_digg",user_digg);
		values.put("user_bury",user_bury);
		values.put("user_repin",user_repin);

		values.put("user_id",user_id);
		values.put("avatar_url",avatar_url);
		values.put("name",name);
		//values.put("user_verified",user_verified);

		values.put("comment_id",comment_id);
	//	values.put("comment_name", comment_name);
		values.put("comment_profile_image_url", comment_profile_image_url);
		values.put("digg_count",digg_count);
		values.put("is_digg",is_digg);
		values.put("text",text);
		return values;
	}


	/**
	 * 数据库检索出的信息转化成段子信息
	 * @param query
	 */
	public void parseCursor(Cursor query) {
		int index = query.getColumnIndex("content");
		if (index != -1) {
			content = query.getString(index);
		}
		index = query.getColumnIndex("group_id");
		if (index != -1) {
			group_id = query.getLong(index);
		}
		index = query.getColumnIndex("category_type");
		if (index != -1) {
			category_type = query.getInt(index);
		}
		index = query.getColumnIndex("level");
		if (index != -1) {
			level = query.getInt(index);
		}
		index = query.getColumnIndex("has_comments");
		if (index != -1) {
			has_comments = query.getInt(index);
		}
		index = query.getColumnIndex("comment_count");
		if (index != -1) {
			comment_count = query.getInt(index);
		}
		index = query.getColumnIndex("repin_count");
		if (index != -1) {
			repin_count = query.getInt(index);
		}
		index = query.getColumnIndex("digg_count");
		if (index != -1) {
			digg_count = query.getInt(index);
		}
		index = query.getColumnIndex("bury_count");
		if (index != -1) {
			bury_count = query.getInt(index);
		}
		index = query.getColumnIndex("user_favorite");
		if (index != -1) {
			user_favorite = query.getInt(index);
		}
		index = query.getColumnIndex("user_digg");
		if (index != -1) {
			user_digg = query.getInt(index);
		}
		index = query.getColumnIndex("user_bury");
		if (index != -1) {
			user_bury = query.getInt(index);
		}
		index = query.getColumnIndex("user_repin");
		if (index != -1) {
			user_repin = query.getInt(index);
		}

		index = query.getColumnIndex("user_id");
		if (index != -1) {
			user_id = query.getLong(index);
		}
		index = query.getColumnIndex("avatar_url");
		if (index != -1) {
			avatar_url = query.getString(index);
		}
		index = query.getColumnIndex("name");
		if (index != -1) {
			name = query.getString(index);
		}


		index = query.getColumnIndex("comment_id");
		if (index != -1) {
			comment_id = query.getLong(index);
		}
		index = query.getColumnIndex("comment_name");
		if (index != -1) {
			comment_name = query.getString(index);
		}
		index = query.getColumnIndex("comment_profile_image_url");
		if (index != -1) {
			comment_profile_image_url = query.getString(index);
		}
		index = query.getColumnIndex("digg_count");
		if (index != -1) {
			digg_count = query.getInt(index);
		}
		index = query.getColumnIndex("is_digg");
		if (index != -1) {
			is_digg = query.getInt(index);
		}
		index = query.getColumnIndex("text");
		if (index != -1) {
			text = query.getString(index);
		}
	}


}