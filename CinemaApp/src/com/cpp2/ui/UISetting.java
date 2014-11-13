package com.cpp2.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cpp2.R;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseAuth;

public class UISetting extends BaseActivity {

//	private Button btn_login;
	private Button btn_logout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_setting);
//		btn_login = (Button)findViewById(R.id.login);
		btn_logout = (Button)findViewById(R.id.logout);
		if(BaseAuth.isLogin()){
//			btn_login.setEnabled(false);
		}
//		btn_login.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				forward(UILogin.class);
//			}
//		});
		btn_logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BaseAuth.setLogin(false);
//				btn_login.setEnabled(true);
				SharedPreferences settings = getSharedPreferences("LOGIN_SP", Context.MODE_PRIVATE);
				Editor editor = settings.edit();
				editor.clear();
				editor.commit();
				forward(UIMainTab.class);
			}
		});
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.finish();
			
//			forward(UIMainTab.class);
		}
		return super.onKeyDown(keyCode, event);
	}
	

}
