package com.qianfeng.gl4study.snssdk.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.qianfeng.gl4study.snssdk.tasks.ImageLoaderTask;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/15
 * Email:pdsfgl@live.com
 */
public class DownloadUtils {

	/**
	 * 图片段子下载
	 * @param userImage
	 * @param avatarUrl
	 */
	public static void loaderImage(ImageView userImage,String avatarUrl){
		userImage.setTag(avatarUrl);
		ImageCache imageCache = ImageCache.getInstance();
		Bitmap bitmap = imageCache.getImage(avatarUrl);
		if(bitmap!=null){
			userImage.setImageBitmap(bitmap);
		}else {
			FileCache fileCache = FileCache.getInstance();
			byte[] bytes = fileCache.getContent(avatarUrl);
			if(bytes!=null&&bytes.length>0){
				Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
				userImage.setImageBitmap(bmp);
				imageCache.putImage(avatarUrl,bmp);
			}else {
				ImageLoaderTask imageLoaderTask = new ImageLoaderTask(userImage);
				imageLoaderTask.execute(avatarUrl);
			}
		}
	}
}
