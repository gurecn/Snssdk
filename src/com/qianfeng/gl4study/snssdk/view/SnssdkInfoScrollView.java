package com.qianfeng.gl4study.snssdk.view;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/19
 * Email:pdsfgl@live.com
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class SnssdkInfoScrollView extends ScrollView {

	private ScrollBottomListener scrollBottomListener;

	public SnssdkInfoScrollView(Context context) {
		super(context);
	}

	public SnssdkInfoScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SnssdkInfoScrollView(Context context, AttributeSet attrs,int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt){
		if(t + getHeight() >=  computeVerticalScrollRange()){
			//ScrollView滑动到底部了
			scrollBottomListener.scrollBottom();
		}
	}

	public void setScrollBottomListener(ScrollBottomListener scrollBottomListener){
		this.scrollBottomListener = scrollBottomListener;
	}

	public interface ScrollBottomListener{
		public void scrollBottom();
	}

}