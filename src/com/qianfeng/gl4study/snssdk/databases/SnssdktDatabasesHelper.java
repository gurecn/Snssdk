package com.qianfeng.gl4study.snssdk.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/2/26
 * Email:pdsfgl@live.com
 */
public class SnssdktDatabasesHelper extends SQLiteOpenHelper {

	public static final int VERSION = 1;
	public static final String TABLE_SNSSDK_WORD = "snssdk_word_table";
	public static final String CREATE_TABLE_SNSSDK_WORD_ITEM =
		"create table " + TABLE_SNSSDK_WORD + " (" +
				"_id integer primary key autoincrement," +
				"is_digg int default 0,"+
				"digg_count int default 0,"+
				"comment_id long not null,"+
				"text text,"+
				"comment_name text,"+
				"comment_profile_image_url text,"+

				"name text,"+
				"user_verified  boolean,"+
				"avatar_url text,"+
				"user_id long not null,"+
				"user_favorite int default 0,"+
				"user_digg int default 0,"+
				"user_bury int default 0,"+
				"user_repin int default 0,"+
				"bury_count int default 0,"+
				"digg_count_comment int default 0,"+
				"repin_count int default 0,"+
				"comment_count int default 0,"+
				"has_comments int default 0,"+
				"level int default 0,"+
				"category_type int default 0,"+
				"group_id long not null,"+
				"large_image text,"+
				"origin_video text,"+
				"content text)";
 /*
			"content": ”于是等他选好手套，阿紫小声的的说：“帅哥，我还想帮我男朋友买一盒TT。。。”",

			//图片    large_image
			//视频    origin_video

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
			"user_name": "星爷sjx",			//神评论作者名
			"user_profile_image_url": "http://p2.pstatp.com/thumb/1222/7013180749"	神评论作者头像
			"digg_count": 16548,			//神评论赞
			"is_digg": 0,					//本人是否赞
			"text": "帅哥:靓女，这里不方便啦，要去我家从福尔马林里拿出来试了。",	//神评论内容
 */

	public SnssdktDatabasesHelper(Context context) {
		super(context, "databases4collect", null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SNSSDK_WORD_ITEM);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
