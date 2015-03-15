package com.qianfeng.gl4study.snssdk.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.model.Discuss;
import com.qianfeng.gl4study.snssdk.utils.FileCache;
import com.qianfeng.gl4study.snssdk.utils.ImageCache;
import com.qianfeng.gl4study.snssdk.tasks.ImageLoaderTask;
import com.qianfeng.gl4study.snssdk.utils.Utils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/7
 * Email:pdsfgl@live.com
 */
public class DiscussListAdapter extends BaseAdapter{

	private Context context;
	private List<Discuss> data;

	public DiscussListAdapter(Context context, List<Discuss> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = null;

		if(convertView !=null){
			view = convertView;
		}else {
			view = LayoutInflater.from(context).inflate(R.layout.item_discuss,parent,false);

			}
		ViewHolder holder = (ViewHolder)view.getTag();

		if(holder == null){
			holder = new ViewHolder();
			holder.userImage = (ImageView)view.findViewById(R.id.ig_discuss_user_icon);
			holder.userName = (TextView)view.findViewById(R.id.txt_discuss_user_name);
			holder.createTime = (TextView)view.findViewById(R.id.txt_discuss_time);
			holder.txtGood = (TextView)view.findViewById(R.id.txt_discuss_good);
			holder.discussContent = (TextView)view.findViewById(R.id.txt_discuss_content);
		}

		Discuss discuss = data.get(position);
		Utils.loaderImage(-1,holder.userImage,discuss.getUserVerified());
		holder.userName.setText(discuss.getUserName());
		holder.createTime.setText(discuss.getCreateTime()+"");
		holder.txtGood.setText(discuss.getDiggCount()+"");

		holder.discussContent.setText(discuss.getContent());
		return view;
	}

	public static class ViewHolder{
		public  ImageView userImage;
		public  TextView userName;
		public  TextView createTime;
		public  TextView txtGood;
		public  TextView discussContent;

	}
}
