package com.cpp2.ui;

import java.util.HashMap;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cpp2.R;
import com.cpp2.base.AppVariable;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseMessage;

public class UIRegister extends BaseActivity {

	protected static final String TAG = "UiRegister";
	private EditText registerUsername = null;
	private EditText registerPassword = null;
	private EditText registerConfirmPassword = null;
	private EditText registerPhone = null;
	private EditText registerEmail = null;
	private EditText registerBirthday = null;
	private RadioGroup registerGender = null;
	private RadioButton boy = null;
	private RadioButton girl = null;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	
		
		// set view after check login
		setContentView(R.layout.ui_register);
		
		// remember password
		registerUsername = (EditText)findViewById(R.id.registerusername_ed);
		registerPassword = (EditText)findViewById(R.id.registerpassword_ed);
		registerConfirmPassword = (EditText)findViewById(R.id.registerconfirmpassword_ed);
		registerPhone = (EditText)findViewById(R.id.registerphone_ed);
		registerEmail = (EditText)findViewById(R.id.registeremail_ed);
		registerBirthday = (EditText)findViewById(R.id.registerbirthday_ed);
		registerGender = (RadioGroup)findViewById(R.id.registergenderredio);
		boy = (RadioButton)findViewById(R.id.radio_boy);
		girl = (RadioButton)findViewById(R.id.radio_girl);
		
		
		
		// login submit
		OnClickListener mOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.registerconfirm : 
						if(validate(registerUsername.getText().toString(), registerPassword.getText().toString(), registerEmail.getText().toString())){
						doTaskRegister();}else {
							showToast("输入格式不对，请检查，你的邮箱，姓名，密码格式是否正确");
						}
						Log.i(TAG,"register");
						break;
				}
			}
		};
		findViewById(R.id.registerconfirm).setOnClickListener(mOnClickListener);
	}
	private boolean validate(String name,String password,String email){
		if(name.matches("(^\\w+$)")&&password.matches("(^\\w+$)")&&email.matches("\\w+@\\w+(\\.\\w+)+")){
			return true;
		}
    	return  false;
    }
	private void doTaskRegister() {
		app.setLong(System.currentTimeMillis());
		Log.i(TAG,"taskstart1");
		if (registerUsername.length() > 0 && registerPassword.length() > 0) {
			Log.i(TAG,"taskstart2");
			HashMap<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("username", registerUsername.getText().toString());
			urlParams.put("password", registerPassword.getText().toString());
			urlParams.put("phone", registerPhone.getText().toString());
			urlParams.put("email", registerEmail.getText().toString());
			urlParams.put("birthday", registerBirthday.getText().toString());
			if(registerGender.getCheckedRadioButtonId() == boy.getId()){
				urlParams.put("gender", boy.getText().toString());			
			}else if(registerGender.getCheckedRadioButtonId() == girl.getId()){
				urlParams.put("gender", girl.getText().toString());			
				
			}
			          
			urlParams.put("method", "register");
			try {Log.i(TAG,"taskstart");
				this.doTaskAsync(AppVariable.task.register, AppVariable.api.register, urlParams);
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