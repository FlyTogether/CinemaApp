package com.cpp2.ui;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cpp2.R;
import com.cpp2.base.AppVariable;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseAuth;
import com.cpp2.base.BaseMessage;
import com.cpp2.model.User;

public class UILogin extends BaseActivity {

	private TextView tvTitle;
	private EditText mEditName;
	private EditText mEditPass;
	private CheckBox mCheckBox;
	private SharedPreferences settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// check if login
		if (BaseAuth.isLogin()) {
			this.forward(UIMainTab.class);
		}
		
		// set view after check login
		setContentView(R.layout.ui_login);
		((ImageButton) findViewById(R.id.activity_titlebar_menu)).setVisibility(View.INVISIBLE);
		// remember password
		tvTitle = 	(TextView) this.findViewById(R.id.activity_titlebar_title);
		mEditName = (EditText) this.findViewById(R.id.app_login_edit_name);
		mEditPass = (EditText) this.findViewById(R.id.app_login_edit_pass);
		mCheckBox = (CheckBox) this.findViewById(R.id.app_login_check_remember);
		settings = getPreferences(Context.MODE_PRIVATE);
		if (settings.getBoolean("remember", false)) {
			mCheckBox.setChecked(true);
			mEditName.setText(settings.getString("username", ""));
			mEditPass.setText(settings.getString("password", ""));
		}
		
		tvTitle.setText("登陆");
		// remember checkbox
		mCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences.Editor editor = settings.edit();
				if (mCheckBox.isChecked()) {
					editor.putBoolean("remember", true);
					editor.putString("username", mEditName.getText().toString());
					editor.putString("password", mEditPass.getText().toString());
				} else {
					editor.putBoolean("remember", false);
					editor.putString("username", "");
					editor.putString("password", "");
				}
				editor.commit();
			}
		});
	    
		// login submit
		OnClickListener mOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.app_login_btn_submit : 
						if(validate(mEditName.getText().toString(), mEditPass.getText().toString())){
						doTaskLogin();}
						else {
							showToast("用户必须是英文字母或者符号");
						}
						break;
					case R.id.login_button_register : 
						forward(UIRegister.class);
						break;
					case R.id.login_button_forget : 
						forward(UIForgottenPassword.class);
						break;
				}
			}
		};
		findViewById(R.id.app_login_btn_submit).setOnClickListener(mOnClickListener);
		findViewById(R.id.login_button_forget).setOnClickListener(mOnClickListener);
		findViewById(R.id.login_button_register).setOnClickListener(mOnClickListener);
	}
	
	private void doTaskLogin() {
		app.setLong(System.currentTimeMillis());
		if (mEditName.length() > 0 && mEditPass.length() > 0) {
			HashMap<String, String> urlParams = new HashMap<String, String>();
//			urlParams.put("loginNum", mEditName.getText().toString());
			urlParams.put("loginNum", mEditName.getText().toString());
			urlParams.put("password", mEditPass.getText().toString());
			urlParams.put("method", "login");
			try {
				this.doTaskAsync(AppVariable.task.login, AppVariable.api.login, urlParams);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private boolean validate(String name,String password){
		if(name.matches("(^\\w+$)")&&password.matches("(^\\w+$)")){
			return true;
		}
    	return  false;
    }
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
			case AppVariable.task.login:
				User customer = null;
				// login logic
//                Log.i(TAG, AppUtil.getResultMessage())		;
				try {
					customer = (User) message.getResult("User");
					
					// login success
					if (customer.getUsername()!= null) {
						BaseAuth.setCustomer(customer);
						BaseAuth.setLogin(true);
					// login fail
					} else {
						BaseAuth.setCustomer(customer); // set sid
						BaseAuth.setLogin(false);
						showToast(this.getString(R.string.msg_loginfail));
					}
				} catch (Exception e) {
					e.printStackTrace();
					showToast(e.getMessage());
				}
				// login complete
				long startTime = app.getLong();
				long loginTime = System.currentTimeMillis() - startTime;
				Log.w("LoginTime", Long.toString(loginTime));
				// turn to index
				if (BaseAuth.isLogin()) {
					// start service
//					BaseService.start(this, NoticeService.class);
					// turn to index
					forward(UIMainTab.class);
				}
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