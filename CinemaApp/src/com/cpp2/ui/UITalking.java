package com.cpp2.ui;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

import com.cpp2.R;
import com.cpp2.base.BaseActivity;


import com.cpp2.model.BmobMessage;
import com.cpp2.model.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UITalking extends BaseActivity{

	public static TextView tv_title = null;
	public static Context context;
	private List<Person> list1 = new ArrayList<Person>();
	protected static final String TAG = "MainActivity";

	private String username = "东方";
	private Button btn_sendMessage;
	private EditText edit_msg;
	public static List<String> msgs = new ArrayList<String>();
	public static  Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				StringBuilder data2 = new StringBuilder();
				for(String data:msgs){
				data2.append(data);
				}
				tv_title.setText(data2);
				msgs.clear();
				break;

			default:
				break;
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_talking);
		tv_title = (TextView)findViewById(R.id.talking_text);
		context = getApplicationContext();
		Bmob.initialize(this, "f789dc4aaf0f782fdc3d841359a024e0");
		startService(new Intent("MessageTransformationCenter"));
		btn_sendMessage = (Button)findViewById(R.id.talking_send);
		edit_msg = (EditText)findViewById(R.id.msg_edit);
		btn_sendMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!edit_msg.getText().toString().equals("")){
				BmobMessage message = new BmobMessage();
				message.setData(edit_msg.getText().toString());
				message.setFrom(username);
				edit_msg.setText("");
				message.save(getApplicationContext(), new SaveListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "发送失败，请检查网络", Toast.LENGTH_SHORT).show();
						
					}
				});
				}else {
					Toast.makeText(getApplicationContext(), "请输入文字", Toast.LENGTH_SHORT).show();
					
				}
			}
		});
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.finish();
			stopService(new Intent("MessageTransformationCenter"));
		}
		return super.onKeyDown(keyCode, event);
	}
}
