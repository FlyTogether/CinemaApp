package com.cpp2.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import com.cpp2.R;
import com.cpp2.base.BaseActivity;

public class SplashActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_splash);
		
	}
	
	@Override
	protected void onStart() {
		if(isNetWorkConnected()){
			new Handler().postDelayed(new Thread(){
				@Override
				public void run() {
					Intent intent = new Intent();
					intent.setClass(SplashActivity.this, MainTabActivity.class);
					startActivity(intent);
					finish();
					super.run();
				}
			}, 500);
			
		}
		else{
			showSetNetworkDialog();
		}
		super.onStart();
	}
	
	
	protected String getVesion(){
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info == null ? "1.0.0" : info.versionName;
	}
	
	private void showSetNetworkDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("设置网络");
		builder.setMessage("网络错误请检查网络状态");
		builder.setPositiveButton("设置网络", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(Settings.ACTION_SETTINGS);
				startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		AlertDialog d = builder.create();
		d.setCanceledOnTouchOutside(false);
		d.show();
	}
	
}
