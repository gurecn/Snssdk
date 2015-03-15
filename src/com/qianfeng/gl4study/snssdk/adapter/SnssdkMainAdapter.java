package com.qianfeng.gl4study.snssdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.model.Snssdk;
import com.qianfeng.gl4study.snssdk.utils.Utils;


import java.util.List;

/**
 * 主界面ListView的Adapter
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
		View view;
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
			viewHolder.llGood = (RelativeLayout) view.findViewById(R.id.item_fragment_bar_good_ll);
			viewHolder.llBad = (RelativeLayout) view.findViewById(R.id.item_fragment_bar_bad_ll);
			viewHolder.llHot = (LinearLayout) view.findViewById(R.id.item_fragment_bar_hot_ll);

			viewHolder.imgGood = (ImageView) view.findViewById(R.id.item_fragment_bar_good_img);
			viewHolder.imgBad = (ImageView) view.findViewById(R.id.item_fragment_bar_bad_img);
			viewHolder.imgHot = (ImageView) view.findViewById(R.id.item_fragment_bar_hot_img);
			viewHolder.imgForward = (ImageView) view.findViewById(R.id.item_fragment_bar_forward_img);

			viewHolder.llGood.setTag(position);
			viewHolder.llBad.setTag(position);
			viewHolder.llHot.setTag(position);
			viewHolder.layoutFragmentCommon.setTag(position);

			viewHolder.llGood.setOnClickListener(listener);
			viewHolder.llBad.setOnClickListener(listener);
			viewHolder.llHot.setOnClickListener(listener);
			viewHolder.layoutFragmentCommon.setOnClickListener(listener);
		}

		Snssdk snssdk = snssdks.get(position);

		int snssdkType = snssdk.getCategory_type();
		if(snssdkType == 2){    //图片类型段子

			//占位图
			viewHolder.itemImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			viewHolder.itemImage.setImageResource(R.drawable.loading_icon);
			String imageUrl = snssdk.getInageContentURL();
			Utils.loaderImage(view.getWidth(),viewHolder.itemImage, imageUrl);
		}
		//TODO 视频信息
		/*
		else if(snssdkType == 3){  //视频类型段子

		}
		*/
		viewHolder.itemWord.setText(snssdk.getContent());
		viewHolder.txtGood.setText(snssdk.getDigg_count()+"");
		viewHolder.txtBad.setText(snssdk.getRepin_count()+"");
		viewHolder.txtHot.setText(snssdk.getComment_count()+"");
		viewHolder.userName.setText(snssdk.getName());
		String avatarUrl = snssdk.getAvatar_url();
		//加载头像
		Utils.loaderImage(-1,viewHolder.userImage, avatarUrl);

		//评论条的显示
		if(snssdk.getUser_digg()==1){
			viewHolder.imgGood.setImageResource(R.drawable.ic_bar_digg_pressed);
		}else {
			viewHolder.imgGood.setImageResource(R.drawable.ic_bar_digg_normal);
		}
		if(snssdk.getUser_repin()==1){
			viewHolder.imgBad.setImageResource(R.drawable.ic_bar_bury_pressed);
		}else {
			viewHolder.imgBad.setImageResource(R.drawable.ic_bar_bury_normal);
		}
		if(snssdk.getComment_count()>500){
			viewHolder.imgHot.setImageResource(R.drawable.ic_bar_hot_commenticon_pressed);
		}else {
			viewHolder.imgHot.setImageResource(R.drawable.ic_bar_hot_commenticon);
		}

		viewHolder.imgForward.setImageResource(R.drawable.ic_bar_more_action_normal);

		return view;
	}

	public static class ViewHolder {

		private TextView itemWord;
		private  ImageView itemImage;
		//private final VideoView itemVideo;
		private  TextView txtGood;
		private  TextView txtBad;
		private  TextView txtHot;
		private ImageView imgGood;
		private ImageView imgBad;
		private ImageView imgHot;
		private ImageView imgForward;

		private  ImageView userImage;
		private  TextView userName;
		private  LinearLayout layoutFragmentCommon;

		private RelativeLayout llGood;
		private RelativeLayout llBad;
		private LinearLayout llHot;

	}
}
