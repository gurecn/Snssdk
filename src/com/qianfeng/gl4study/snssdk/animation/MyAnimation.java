package com.qianfeng.gl4study.snssdk.animation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.qianfeng.gl4study.snssdk.R;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/14
 * Email:pdsfgl@live.com
 */
public class MyAnimation {

	public static void addOneAnimation(Context context,TextView textView){
		textView.setVisibility(View.VISIBLE);
		Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_move_up);
		textView.startAnimation(animation);
		textView.setVisibility(View.GONE);
	}

	public static void addRotateAnimation(Context context,ImageView imageView){
		Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_rotate);
		imageView.startAnimation(animation);
		Log.d("addOneAnimation", "旋转");
	}
}
