package com.cpp2.base;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

public class BaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		
		super.onStart();
	}
	
	public boolean isNetWorkConnected(){
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
	
		return (info!=null&&info.isConnected());
	}
}
