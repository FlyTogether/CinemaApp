package com.cpp2.ui;

import java.util.HashMap;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.cpp2.R;
import com.cpp2.base.AppVariable;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseMessage;


public class UIForgottenPassword extends BaseActivity {

	private EditText forgottenUsername = null;
	private EditText forgottenPassword = null;
	
	private EditText forgottenPhone = null;
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	
		
		// set view after check login
		setContentView(R.layout.ui_forgotten);
		
		// remember password
		forgottenUsername = (EditText)findViewById(R.id.forgottenusername_ed);
		forgottenPassword = (EditText)findViewById(R.id.forgottenpassword_ed);
		
		forgottenPhone = (EditText)findViewById(R.id.forgottenphone_ed);
	
	
		
		
		// login submit
		OnClickListener mOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.forgottenconfirm : 
						doTaskForgotten();
						break;
				}
			}
		};
		findViewById(R.id.forgottenconfirm).setOnClickListener(mOnClickListener);
	}
	
	private void doTaskForgotten() {
		app.setLong(System.currentTimeMillis());
		if (forgottenUsername.length() > 0 && forgottenPassword.length() > 0) {
			HashMap<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("username", forgottenUsername.getText().toString());
			urlParams.put("password", forgottenPassword.getText().toString());
			urlParams.put("phone", forgottenPhone.getText().toString());
			
			          
			urlParams.put("method", "forgotten");
			try {
				this.doTaskAsync(AppVariable.task.forgotten, AppVariable.api.forgotten, urlParams);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
			case AppVariable.task.register:
				
					forward(UILogin.class);
				
				break;
		}
	}
	
	@Override
	public void onNetworkError (int taskId) {
		super.onNetworkError(taskId);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// other methods
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}