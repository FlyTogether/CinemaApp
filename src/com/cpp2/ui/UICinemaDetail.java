package com.cpp2.ui;



import java.util.ArrayList;
import java.util.List;

import com.cpp2.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UICinemaDetail extends Activity {

	String cinemaWorkingTime;
	String cinemaLocation ;
	String cinemaName;
	String cinemaPhone ;
	
	private ListView listView ;
	private TextView textView, tvTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_cinema_detail);
		((ImageButton) findViewById(R.id.activity_titlebar_menu)).setVisibility(View.INVISIBLE);
		tvTitle = (TextView) findViewById(R.id.activity_titlebar_title);
		textView = (TextView)findViewById(R.id.text1);
		listView = (ListView)findViewById(R.id.cinema_listview1);
		
		
		Intent intent = getIntent();

		cinemaWorkingTime = intent.getStringExtra("cinemaWorkingTime");
		cinemaLocation = intent.getStringExtra("cinemaLocation");
		cinemaName = intent.getStringExtra("cinemaName");
		cinemaPhone = intent.getStringExtra("cinemaPhone");
		tvTitle.setText("影院详情");
		textView.setTextSize(50.0f);
		textView.setText("        "+cinemaName);
						
		demos1.add( new ItemInfor(cinemaPhone,R.drawable.face,
							UILogin.class));
		demos1.add( new ItemInfor("营业时间："+cinemaWorkingTime,R.drawable.ic_launcher,
				UILogin.class));
		demos1.add( new ItemInfor(cinemaLocation,R.drawable.ic_launcher,
				UILogin.class));
		listView.setAdapter(new DemoListAdapter(demos1));
	Toast.makeText(getApplicationContext(), cinemaLocation+cinemaPhone, Toast.LENGTH_SHORT).show();
	}
	private List<ItemInfor> demos1 = new ArrayList<ItemInfor>();
	private class DemoListAdapter extends BaseAdapter {
		private ItemInfor[] itemInfor;

		public DemoListAdapter(List<ItemInfor> demos1) {
			super();
			this.itemInfor = new ItemInfor[demos1.size()];
			for (int i = 0; i < demos1.size(); i++) {
				this.itemInfor[i] = demos1.get(i);
			}
		}
	
		@SuppressLint("NewApi")
		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			convertView = View.inflate(getApplicationContext(),
					R.layout.cinema_listview_info_item2, null);
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT, 70);
			convertView.setLayoutParams(lp);
			ImageView title = (ImageView) convertView.findViewById(R.id.title);
			TextView small = (TextView) convertView.findViewById(R.id.small);

			title.setImageResource(itemInfor[index].picture);
		//	Log.i(TAG, itemInfor[index].title);
			small.setText(itemInfor[index].title);
			small.setTextColor(Color.WHITE);
            small.setTextSize(25.0f);
			return convertView;
		}

		@Override
		public int getCount() {
			return demos1.size();
		}

		@Override
		public Object getItem(int index) {
			return demos1.get(index);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}
	}

	private class ItemInfor {

		private final String title;
		private final int picture;

		private final Class<? extends android.app.Activity> demoClass;

		public ItemInfor(String title, int picture,
				Class<? extends android.app.Activity> demoClass) {
			this.picture = picture;
			this.title = title;
			this.demoClass = demoClass;
		}

		
	}

}
