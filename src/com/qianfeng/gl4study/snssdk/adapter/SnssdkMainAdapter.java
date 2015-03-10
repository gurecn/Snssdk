package com.qianfeng.gl4study.snssdk.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.qianfeng.gl4study.snssdk.model.Snssdk;


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
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
	
	
	
	
	
/*

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_word, viewGroup, false);
		//view.setOnClickListener(listener);
		view.setTag(i);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {

		Snssdk snssdk = snssdks.get(i);

		Log.d("MainRecyclerAdapter","onBindViewHolder=====加载段子");
		int snssdkType = snssdk.getSnssdkType();
		if(snssdkType == 2){    //图片类型段子

			viewHolder.itemImage.setImageResource(R.drawable.loading_icon);
			Log.d("MainRecyclerAdapter","onBindViewHolder=====加载图片");
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


		viewHolder.txtGood.setTag(i);
		viewHolder.txtBad.setTag(i);
		viewHolder.txtHot.setTag(i);

		//viewHolder.itemWord.setTag(i);
		//viewHolder.itemVideo.setTag(i);
		//viewHolder.itemImage.setTag(i);
		viewHolder.layoutFragmentCommon.setTag(i);

		viewHolder.txtHot.setOnClickListener(listener);
		viewHolder.txtBad.setOnClickListener(listener);
		viewHolder.txtGood.setOnClickListener(listener);
		viewHolder.layoutFragmentCommon.setOnClickListener(listener);


		if(i == snssdks.size()-1&&context instanceof MainActivity){
			((MainActivity) context).refreshData();
		}


	}

	@Override
	public int getItemCount() {
		if(snssdks!=null) {
			return snssdks.size();
		}
		return 0;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder{

		private final TextView itemWord;
		private final ImageView itemImage;
		//private final VideoView itemVideo;
		private final TextView txtGood;
		private final TextView txtBad;
		private final TextView txtHot;
		private final ImageView userImage;
		private final TextView userName;
		private final LinearLayout layoutFragmentCommon;

		public ViewHolder(View itemView) {
			super(itemView);
			itemWord = (TextView) itemView.findViewById(R.id.item_fragment_word);
			itemImage = (ImageView) itemView.findViewById(R.id.item_fragment_image);
			//itemVideo = (VideoView) itemView.findViewById(R.id.item_fragment_video);
			txtGood = (TextView) itemView.findViewById(R.id.item_fragment_bar_good);
			txtBad = (TextView) itemView.findViewById(R.id.item_fragment_bar_bad);
			txtHot = (TextView) itemView.findViewById(R.id.item_fragment_bar_hot);
			userImage = (ImageView) itemView.findViewById(R.id.item_fragment_user_icon);
			userName = (TextView) itemView.findViewById(R.id.item_fragment_user_name);
			layoutFragmentCommon = (LinearLayout) itemView.findViewById(R.id.item_fragment_common);
		}
	}
	public void dataChanged(int postion){
		notifyItemChanged(postion);
	}

	public void dataClean(){

		notifyItemRangeRemoved(0,snssdks.size());
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

*/


}
