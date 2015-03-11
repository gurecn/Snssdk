package com.qianfeng.gl4study.snssdk.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.adapter.DiscussListAdapter;
import com.qianfeng.gl4study.snssdk.constant.Constant;
import com.qianfeng.gl4study.snssdk.model.Discuss;
import com.qianfeng.gl4study.snssdk.model.Snssdk;
import com.qianfeng.gl4study.snssdk.tasks.SnssdkTask;
import com.qianfeng.gl4study.snssdk.tasks.TaskProcessor;
import com.qianfeng.gl4study.snssdk.utils.FileCache;
import com.qianfeng.gl4study.snssdk.utils.ImageCache;
import com.qianfeng.gl4study.snssdk.utils.ImageLoader;
import com.qianfeng.gl4study.snssdk.view.MyListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/11
 * Email:pdsfgl@live.com
 */
public class SnssdkInfoFragment extends Fragment implements TaskProcessor {

	private LinkedList<Discuss> dataFresh;
	private LinkedList<Discuss> dataTop;
	private DiscussListAdapter adapterHot;
	private MyListView recyclerViewHot;
	private SnssdkTask discussTask;
	private MyListView recyclerViewFresh;
	private DiscussListAdapter adapterFresh;
	private View view;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.snssdk_info_fragment, container, false);
		Bundle arguments = getArguments();
		Log.d("SnssdkInfoFragment","到这里");
		Serializable serializable = arguments.getSerializable("snssdk");
		dataFresh = new LinkedList<Discuss>();
		dataTop = new LinkedList<Discuss>();
		recyclerViewHot = (MyListView) view.findViewById(R.id.list_view_hot_discuss);
		recyclerViewFresh = (MyListView) view.findViewById(R.id.list_view_fresh_discuss);

		if(null!=serializable&&serializable instanceof Snssdk){
			Snssdk snssdk = (Snssdk)serializable;
			displaySnssdk(snssdk);
			displayDiscuss(snssdk);
		}
		return view;
	}

	//显示评论信息
	private void displayDiscuss(Snssdk snssdk){
		discussTask = new SnssdkTask(this);
		//主连接，段子Id，异步类型标记，返回评论数量，返回评论起点

		StringBuilder stringBuilder = new StringBuilder();
		String groupId="group_id="+snssdk.getGroupId();
		String count="&count=10";		//返回的新鲜评论数量
		String offset="&offset=0";    //返回的新鲜评论的起点
		stringBuilder.append(Constant.DISCUSS_CONTENT_LIST_URL).append(groupId).append(count).append(offset);

		discussTask.execute(stringBuilder.toString(),"1");
		adapterHot = new DiscussListAdapter(getActivity(), dataTop);
		adapterFresh = new DiscussListAdapter(getActivity(),dataFresh );
		recyclerViewHot.setAdapter(adapterHot);
		recyclerViewFresh.setAdapter(adapterFresh);
		Log.d("SnssdkInfoActivity", "displayDiscuss");
	}

	//显示段子信息
	private void displaySnssdk(Snssdk snssdk){

		TextView itemWord = (TextView) view.findViewById(R.id.item_fragment_word);
		//VideoView itemVideo = (VideoView) view.findViewById(R.id.item_fragment_video);
		ImageView itemImage = (ImageView) view.findViewById(R.id.item_fragment_image);
		ImageView itemIconUser = (ImageView) view.findViewById(R.id.item_fragment_user_icon);
		TextView itemUserName = (TextView) view.findViewById(R.id.item_fragment_user_name);
		loaderImage(itemIconUser,snssdk.getAuthorInformation().getAvatarUrl());
		itemUserName.setText(snssdk.getAuthorInformation().getName());

		Log.d("SnssdkInfoActivity","displaySnssdk");

		int snssdkType = snssdk.getSnssdkType();
		if(snssdkType == 1){
			String content = snssdk.getContent();
			if(null!=content) {
				itemWord.setText(content);
			}
			//	itemVideo.setVisibility(View.INVISIBLE);
			itemImage.setVisibility(View.INVISIBLE);
		}else if(snssdkType == 2){
			String content = snssdk.getContent();
			if(null!=content) {
				itemWord.setText(content);
			}
			loaderImage(itemImage,snssdk.getImageUrl());
			//		itemVideo.setVisibility(View.GONE);
		}else if(snssdkType == 3){
			//	itemVideo
		}
	}

	//图片段子下载
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
	@Override
	public void processResult(JSONObject result, String flag) {

		Log.d("SnssdkInfoActivity","processResult");

		if(result!=null){      //评论列表
			try {
				String resultFlag = result.getString("message");
				if("success".equals(resultFlag)){
					JSONObject dataObject = result.getJSONObject("data");
					//int tip = dataObject.getInt("total_number");
					JSONArray dataJSONArray = dataObject.getJSONArray("top_comments");
					int type = Integer.parseInt(flag);
					for (int i = 0; i < dataJSONArray.length(); i++) {
						JSONObject jsonObject = dataJSONArray.getJSONObject(i);
						Discuss discuss = new Discuss();
						discuss.parseInformation(jsonObject, type);
						dataTop.add(discuss);
					}
					//最热评论数据添加完成，更新List
					adapterHot.notifyDataSetChanged();
					dataJSONArray = dataObject.getJSONArray("recent_comments");
					for (int i = 0; i < dataJSONArray.length(); i++) {
						JSONObject jsonObject = dataJSONArray.getJSONObject(i);
						Discuss discuss = new Discuss();
						discuss.parseInformation(jsonObject, type);
						dataFresh.add(discuss);
					}
					//最新评论数据添加完成，更新List
					adapterFresh.notifyDataSetChanged();

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}


}