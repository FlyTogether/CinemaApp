package com.cpp2.service;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

import com.cpp2.model.BmobMessage;
import com.cpp2.ui.UITalking;

@SuppressLint("NewApi")
public class MessageTransformationCenter extends Service {

	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor; 
	private static final String TAG = "MessageTransformationCenter";
	private int num;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 Bmob.initialize(this, "f789dc4aaf0f782fdc3d841359a024e0");
		 Log.i(TAG, "createservice");
		   preferences = getSharedPreferences("messages", Context.MODE_PRIVATE);
		   editor = preferences.edit();
		 
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				synchronized (UITalking.class) {
					
				
				while(true){
						try {
							
					Thread.sleep(2000);
					
					BmobQuery<BmobMessage> query = new BmobQuery<BmobMessage>();
					query.count(UITalking.context, BmobMessage.class, new CountListener() {
						
						@Override
						public void onSuccess(int arg0) {
							// TODO Auto-generated method stub
							editor.putInt("count", arg0);
							editor.commit();
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							
						}
					});
					
					query.findObjects(UITalking.context, new FindListener<BmobMessage>() {
						
						@Override
						public void onSuccess(List<BmobMessage> arg0) {
							// TODO Auto-generated method stub
							
							if(arg0.size()>50){
								num = arg0.size() - 50;
							}else {
								num = 0;
							}
							for(int i=num;i<arg0.size();i++){					
								editor.putString("data"+i, arg0.get(i).getFrom()+":"+arg0.get(i).getData());
							}
							editor.commit();
		
							
							
						}
						
						@Override
						public void onError(int arg0, String arg1) {
							// TODO Auto-generated method stub
							
						
						}
					});
				for(int i=num;i<preferences.getInt("count", 0);i++){
					String msg[] = preferences.getString("data"+i, "@").split("\\:");
					if(msg.length > 1)
					//MainActivity.textView.append(msg[0]+"\r"+msg[1]+"\r");
					UITalking.msgs.add(msg[0]+"说\r\n"+msg[1]+"\r\n");
				}
				UITalking.handler.obtainMessage(0).sendToTarget();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					editor.clear();
					editor.commit();
				}
					}
				
				
			}}
		}).start();
	}

}
