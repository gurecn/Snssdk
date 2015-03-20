package com.qianfeng.gl4study.snssdk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.qianfeng.gl4study.snssdk.R;
import com.qianfeng.gl4study.snssdk.activity.FlyleafActivity;

/**
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/20
 * Email:pdsfgl@live.com
 */
public class TutorialFragment extends Fragment implements View.OnClickListener {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
		Bundle arguments = getArguments();
		int position = arguments.getInt("position");
		ImageView tutorialIVFull = (ImageView) view.findViewById(R.id.iv_tutorial_full);
		switch (position){
			case 0:
				tutorialIVFull.setImageResource(R.drawable.bg_intros1);
				break;
			case 1:
				tutorialIVFull.setImageResource(R.drawable.bg_intros2);
				break;
			case 2:
				tutorialIVFull.setImageResource(R.drawable.bg_intros3);
				ImageView tutorialIVSmall = (ImageView) view.findViewById(R.id.iv_tutorial_small);
				tutorialIVSmall.setImageResource(R.drawable.bg_enter_main_btn);
				tutorialIVSmall.setOnClickListener(this);
				break;
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), FlyleafActivity.class);
		startActivity(intent);
		getActivity().finish();
	}
}