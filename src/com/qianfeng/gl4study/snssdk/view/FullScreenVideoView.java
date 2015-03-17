package com.qianfeng.gl4study.snssdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/17
 * Email:pdsfgl@live.com
 */
public class FullScreenVideoView extends VideoView {
	public FullScreenVideoView(Context context) {
		super(context);
	}

	public FullScreenVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(widthMeasureSpec);
		//若未调用super，则必须调用setMeasuredDimension（）。
		setMeasuredDimension(width,height);

//
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
