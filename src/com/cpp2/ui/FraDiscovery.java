package com.cpp2.ui;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.cpp2.R;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseApp;
import com.cpp2.base.BaseFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FraDiscovery extends BaseFragment{

	private Button btn_location;
	private Button btn_talking;
	
	// private TextView LocationResult;

	private LocationMode tempMode = LocationMode.NORMAL;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContext((BaseActivity)getActivity());
		super.onCreate(savedInstanceState);
	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fra_discovery, null);
		btn_location = (Button)view.findViewById(R.id.location_btn);
		btn_talking = (Button)view.findViewById(R.id.talking_btn);
		btn_location.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				overlay(UILocation.class);
			}
		});
		btn_talking.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				overlay(UITalking.class);
				
			}
		});
		return view;
	}
}
