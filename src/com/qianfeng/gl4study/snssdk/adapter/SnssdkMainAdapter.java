package com.qianfeng.gl4study.snssdk.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.model.Snssdk;
import com.qianfeng.gl4study.snssdk.model.UserInformation;
import com.qianfeng.gl4study.snssdk.utils.FileCache;
import com.qianfeng.gl4study.snssdk.utils.ImageCache;
import com.qianfeng.gl4study.snssdk.utils.ImageLoader;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/5
 * Email:pdsfgl@live.com
 */
public class SnssdkMainAdapter extends BaseAdapter{

	private Context context;
	private List<Snssdk> snssdks;
	private View.OnClickListener listener;


	public SnssdkMainAdapter(Context context, List<Snssdk> snssdks) {
		this.context = context;
		this.snssdks = snssdks;
		if(context instanceof View.OnClickListener){
			listener = (View.OnClickListener)context;
		}
	}

	@Override
	public int getCount() {
		if(snssdks!=null){
			return snssdks.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(snssdks!=null){
			return snssdks.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if(convertView !=null) {
			view = convertView;
		}else {
			view = LayoutInflater.from(context).inflate(R.layout.item_fragment_word, parent, false);
		}

		ViewHolder viewHolder = (ViewHolder) view.getTag();
		if(viewHolder == null){
			viewHolder = new ViewHolder();
			viewHolder.itemWord = (TextView) view.findViewById(R.id.item_fragment_word);
			viewHolder.itemImage = (ImageView) view.findViewById(R.id.item_fragment_image);
			//viewHolder.itemVideo = (VideoView) view.findViewById(R.id.item_fragment_video);
			viewHolder.txtGood = (TextView) view.findViewById(R.id.item_fragment_bar_good);
			viewHolder.txtBad = (TextView) view.findViewById(R.id.item_fragment_bar_bad);
			viewHolder.txtHot = (TextView) view.findViewById(R.id.item_fragment_bar_hot);
			viewHolder.userImage = (ImageView) view.findViewById(R.id.item_fragment_user_icon);
			viewHolder.userName = (TextView) view.findViewById(R.id.item_fragment_user_name);
			viewHolder.layoutFragmentCommon = (LinearLayout) view.findViewById(R.id.item_fragment_common);
		}

		Snssdk snssdk = snssdks.get(position);

		int snssdkType = snssdk.getSnssdkType();
		if(snssdkType == 2){    //图片类型段子
			viewHolder.itemImage.setImageResource(R.drawable.loading_icon);
			String imageUrl = snssdk.getImageUrl();
			loaderImage(viewHolder.itemImage,imageUrl);
		}else if(snssdkType == 3){  //视频类型段子

		}
		viewHolder.itemWord.setText(snssdk.getContent());
		viewHolder.txtGood.setText(snssdk.getDiggCount()+"");
		viewHolder.txtBad.setText(snssdk.getRepinCount()+"");
		viewHolder.txtHot.setText(snssdk.getFavoriteCount()+"");
		UserInformation authorInformation = snssdk.getAuthorInformation();
		viewHolder.userName.setText(authorInformation.getName());
		String avatarUrl = authorInformation.getAvatarUrl();
		//加载头像
		loaderImage(viewHolder.userImage,avatarUrl);

		viewHolder.txtGood.setTag(position);
		viewHolder.txtBad.setTag(position);
		viewHolder.txtHot.setTag(position);
		viewHolder.layoutFragmentCommon.setTag(position);

		viewHolder.txtHot.setOnClickListener(listener);
		viewHolder.txtBad.setOnClickListener(listener);
		viewHolder.txtGood.setOnClickListener(listener);
		viewHolder.layoutFragmentCommon.setOnClickListener(listener);

		return view;
	}

	public static class ViewHolder {

		private TextView itemWord;
		private  ImageView itemImage;
		//private final VideoView itemVideo;
		private  TextView txtGood;
		private  TextView txtBad;
		private  TextView txtHot;
		private  ImageView userImage;
		private  TextView userName;
		private  LinearLayout layoutFragmentCommon;


	}

	private void loaderImage(ImageView userImage,String avatarUrl){
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
				ImageLoader imageLoader = new ImageLoader(userImage);
				imageLoader.execute(avatarUrl);
			}
		}
	}

}
