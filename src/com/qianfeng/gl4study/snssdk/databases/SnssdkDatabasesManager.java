package com.qianfeng.gl4study.snssdk.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.qianfeng.gl4study.snssdk.model.Snssdk;

import java.util.LinkedList;


/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/2/26
 * Email:pdsfgl@live.com
 */
public class SnssdkDatabasesManager {
	private static SnssdkDatabasesManager ourInstance;

	private SnssdktDatabasesHelper dbHelper;
	private SQLiteDatabase database;
	private boolean ret = false;

	public static SnssdkDatabasesManager createInstance(Context context){
		if(ourInstance==null){
			ourInstance = new SnssdkDatabasesManager(context);
		}
		return ourInstance;
	}

	public static SnssdkDatabasesManager getInstance() {
		return ourInstance;
	}

	Context context;
	private SnssdkDatabasesManager(Context context) {
		this.context = context;
	}


	/**
	 * 查询段子信息
	 * @param group_id    段子Id
	 * @return  结果游标
	 */
	public Cursor querySnssdk(long group_id){
		SQLiteDatabase db = openDatabases();
		Cursor query = db.query(
				SnssdktDatabasesHelper.TABLE_SNSSDK_WORD,
				null,
				"group_id = ?",
				new String[]{group_id+""},
				null, null, null
		);
		return query;
	}

	/**
	 * 获取段子列表
	 * @param category  段子类别
	 * @return  返回结果集合
	 */

	public LinkedList<Snssdk> getSnssdkCollect(int category,int level){

		LinkedList<Snssdk> ret = null;
		SQLiteDatabase db = openDatabases();
		Cursor query;
		if(level ==6){
			query = db.query(
					SnssdktDatabasesHelper.TABLE_SNSSDK_WORD,
					null,
					"category_type = ?",
					new String[]{category+""},
					null, null,  "group_id ASC"
			);
		}else {
			query = db.query(
					SnssdktDatabasesHelper.TABLE_SNSSDK_WORD,
					null,
					"category_type = ?AND level = ?",
					new String[]{category + "", level + ""},
					null, null, "group_id ASC"
			);
		}
		if(query!=null){
			ret = new LinkedList<Snssdk>();
			while (query.moveToNext()){
				Snssdk snssdk = new Snssdk();
				snssdk.parseCursor(query);
				ret.add(snssdk);
			}
			query.close();
		}
		return ret;
	}

	/**
	 * 数据库存储段子信息
	 * @param values    需要存储的信息
	 * @return  返回存储结果
	 */

	public boolean saveSnssdk(ContentValues values,long groupId){
		/*
		检查数据库是否打开
		未打开进行打开操作
		*/
		SQLiteDatabase db = openDatabases();
		if(db!=null){
			if(values!=null) {
				try {
					db.beginTransaction();
					//检查段子是否存在
					if(querySnssdk(groupId)==null||querySnssdk(groupId).getCount()<=0){
						long rid = db.insert(SnssdktDatabasesHelper.TABLE_SNSSDK_WORD, null, values);
						ret = rid!=-1;
						if(ret){
							db.setTransactionSuccessful();
						}
					}
				}catch (Exception ex){
					ex.printStackTrace();
				}finally {
					db.endTransaction();
				}
			}
		}
		return ret;
	}

	/**
	 * 打开数据库的操作
	 * @return      打开的数据库
	 */
	private SQLiteDatabase openDatabases(){
		if(database == null){
			if(dbHelper==null) {
				dbHelper = new SnssdktDatabasesHelper(context);
			}
			database = dbHelper.getWritableDatabase();
		}
		return database;
	}


}
