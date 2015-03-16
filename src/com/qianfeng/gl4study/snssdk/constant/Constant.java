package com.qianfeng.gl4study.snssdk.constant;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/11
 * Email:pdsfgl@live.com
 */
public class Constant {

	//段子列表数据获取
	public static final String SNSSDK_CONTENT_LIST_URL = "http://ic.snssdk.com/neihan/stream/category/data/v2/?";

	//评论数据列表获取
	public static final String DISCUSS_CONTENT_LIST_URL= "http://isub.snssdk.com/2/data/get_essay_comments/?";

	//用户信息获取
	public static final String SNSSDK_USER_INFOMATION_URL= "http://isub.snssdk.com/neihan/user/profile/v1/?";

	//端子数据分类1
	public static final int TYPE_1_CATEGORY_ID_WORD_FLAG_SNSSDK = 1;
	public static final int TYPE_1_CATEGORY_ID_IMAGE_FLAG_SNSSDK = 2;
	public static final int TYPE_1_CATEGORY_ID_VIDEO_FLAG_SNSSDK = 18;

	//段子类型选择分类2
	public static final int TYPE_2_CATEGORY_ID_RECOEND_FLAG_SNSSDK = 6;//推荐
	public static final int TYPE_2_CATEGORY_ID_ESSENCE_FLAG_SNSSDK = 5;//精华
	public static final int TYPE_2_CATEGORY_ID_HOT_FLAG_SNSSDK = 4;//热门
	public static final int TYPE_2_CATEGORY_ID_FRESH_FLAG_SNSSDK = 3;//新鲜

	//获取手机屏幕信息
	public static int DISPLAYMETRICS_WIDTH = 0;
	public static int DISPLAYMETRICS_HEIGHT = 0;


	//用户ID。默认为0，未登录
	public static long SNSSDK_USER_ID = 0;

	public static boolean ISFIRSTRUN = false;


}
