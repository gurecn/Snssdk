package com.qianfeng.gl4study.snssdk.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.qianfeng.gl4study.snssdk.utils.FileCache;
import com.qianfeng.gl4study.snssdk.utils.HttpTool;
import com.qianfeng.gl4study.snssdk.utils.ImageCache;
import com.qianfeng.gl4study.snssdk.utils.Utils;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/15
 * Email:pdsfgl@live.com
 */
public class UserAvatarTask extends AsyncTask<String, Integer, byte[]> {

	private ImageView imageView;
	private String imgUrl;

	public UserAvatarTask(ImageView imageView) {
		this.imageView = imageView;
	}
	@Override
	protected byte[] doInBackground(String... params) {
		byte[] ret = new byte[0];
		if (params != null && params.length > 0) {
			imgUrl = params[0];
			ret = HttpTool.get(imgUrl);
		}
		return ret;
	}

	@Override
	protected void onPostExecute(byte[] bytes) {

		if (bytes != null) {

			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			bitmap = Utils.getRoundedCornerBitmap(bitmap,2);
			//更新内存缓存信息
			FileCache.getInstance().putContent(imgUrl,bytes);
			ImageCache imageCache = ImageCache.getInstance();
			imageCache.putImage(imgUrl,bitmap);
			if(imageView!=null) {
				Object tag = imageView.getTag();
				if(tag!=null){
					//检查下载地址是否属于当前ImageView
					if(tag instanceof String){
						String sTag = (String) tag;
						if(sTag.equals(imgUrl)){
							//当前下载地址和tag参数一致，即任务该ImageView应该显示该图片
							imageView.setImageBitmap(bitmap);
						}
					}
				}
			}
		}
	}
}
