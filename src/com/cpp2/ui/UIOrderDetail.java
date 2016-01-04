package com.cpp2.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cpp2.R;
import com.cpp2.base.AppVariable;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseMessage;
import com.cpp2.list.LittleTextAdapter;

public class UIOrderDetail extends BaseActivity implements OnClickListener{
	
	protected ImageButton ibtnPay;
	protected TextView tvTitle;
	protected ListView lvOrderDetail;
	protected String[] content = {"电影：心花路放", "场次：2014年10月1日 19:30 二号厅（2D）"
			, "标价：35", "座位：A排3座", "优惠：无"};
	
	
	private Bundle bundleMovie;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_order_detail);
		bundleMovie = getIntent().getExtras();
		
		tvTitle = (TextView) findViewById(R.id.activity_titlebar_title);
		ibtnPay = (ImageButton) findViewById(R.id.movie_detail_ibtn_pay);
		((ImageButton) findViewById(R.id.activity_titlebar_back)).setVisibility(View.INVISIBLE);
		((ImageButton) findViewById(R.id.activity_titlebar_menu)).setVisibility(View.INVISIBLE);
		lvOrderDetail = (ListView) findViewById(R.id.order_detail_listview);
		
		init();
	}
	
	private void init(){
		ibtnPay.setOnClickListener(this);
		
		ArrayList<String> list = new ArrayList<>();
		for(int i=0; i<content.length; i++){
			list.add(content[i]);
		}
		tvTitle.setText("订单详情");
		LittleTextAdapter adapter = new LittleTextAdapter(this, list);
		lvOrderDetail.setAdapter(adapter);
	}
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		if("1".equals(message.getCode())){
			showToast("购买成功！！");
		}
		else{
			showToast("购买失败！！");
		}
	}

	@Override
	public void onClick(View v) {
		HashMap<String, String> params = new HashMap<>();
		params.put("movieID", "1");
		params.put("scheduleID", "1");
		params.put("seatID", "1");
		doTaskAsync(AppVariable.task.payOrder, AppVariable.api.payOrder, params);
		
	}
}
