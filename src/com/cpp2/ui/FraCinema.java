package com.cpp2.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cpp2.R;
import com.cpp2.base.AppVariable;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseFragment;
import com.cpp2.base.BaseFragmentHandler;
import com.cpp2.base.BaseMessage;
import com.cpp2.base.BaseTask;
import com.cpp2.model.Cinema;

@SuppressLint("ViewHolder")
public class FraCinema extends BaseFragment {
	private static final String TAG = "FraCinema";
	private ListView mListView1;
	private ListView mListView2;
	private ArrayList<Cinema> movieList;
	private String cinemaName = "大地"; 
	private String cinemaWorkingTime="13:00-14:00"; 
	private String cinemaLocation = "湛江"; 
	private String cinemaPhone = "1234567"; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.fra_myinformation);
		setContext((BaseActivity) getActivity());

	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		setHandler(new MyHandler(context, this));
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("method", "getAllCinemaForMobile");
		doTaskAsync(AppVariable.task.cinemaList, AppVariable.api.cinemaList, map);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fra_cinema, null);
		ListView mListView1 = (ListView) view.findViewById(R.id.cinema_listview1);
		ListView mListView2 = (ListView) view.findViewById(R.id.cinema_listview2);

		// 添加ListItem，设置事件响应
		mListView1.setAdapter(new DemoListAdapter(demos1));
		mListView2.setAdapter(new DemoListAdapter(demos2));

		mListView1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int index,
					long arg3) {
				onListItemClick(index);
			}
		});
		mListView2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int index,
					long arg3) {
				onListItemClick(index);
			}
		});

		return view;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		 System.out.println("CinemaFragment -- onTaskComplete");
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case AppVariable.task.cinemaList:
			Log.i(TAG, "complete cinema");
			try {
				movieList = (ArrayList<Cinema>) message.getResultList("Cinema");
				Log.i(TAG, "complete cinemaList");
				for(Cinema cinema:movieList){
					demos1.add(new ItemInfor(cinema.getName(), UICinemaDetail.class));
					demos2.add(new ItemInfor(cinema.getName(), UICinemaDetail.class));
					
					mListView1.setAdapter(new DemoListAdapter(demos1));
					mListView2.setAdapter(new DemoListAdapter(demos2));
					
//					Intent intent = new Intent();
//					intent.putExtra("Cinema_address", cinema.getAddress());
//					intent.putExtra("Cinema_phone", cinema.getPhone());
//					intent.putExtra("Cinema_id", cinema.getId());
//					intent.putExtra("Cinema_name", cinema.getName());
				}
//				setHandler(new MyHandler(context, this));
				
			} catch (Exception e) {
				e.printStackTrace();
				showToast(e.getMessage());
			} finally {
			}
			break;
		}
	}
	class MyHandler extends BaseFragmentHandler {
		public MyHandler(BaseActivity ui, BaseFragment fragment) {
			super(ui, fragment);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				switch (msg.what) {
				case BaseTask.LOAD_CINEMA:
					//此处修改listview
					
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	void onListItemClick(int index) {
		Intent intent = null;
		intent = new Intent(context, UICinemaDetail.class);
		intent.putExtra("cinemaWorkingTime", cinemaWorkingTime);
		intent.putExtra("cinemaLocation", cinemaLocation);
		intent.putExtra("cinemaName", cinemaName);
		intent.putExtra("cinemaPhone", cinemaPhone);
		this.startActivity(intent);
	}

	private List<ItemInfor> demos1 = new ArrayList<>();
	private List<ItemInfor> demos2 = new ArrayList<>();
	
	private class DemoListAdapter extends BaseAdapter {
		private ItemInfor[] itemInfor;

		public DemoListAdapter(List<ItemInfor> demos) {
			super();
			this.itemInfor = new ItemInfor[demos.size()];
			for (int i = 0; i < demos.size(); i++) {
				this.itemInfor[i] = demos.get(i);
			}
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			convertView = View.inflate(context,
					R.layout.cinema_listview_info_item, null);
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT, 70);
			convertView.setLayoutParams(lp);
			TextView title = (TextView) convertView.findViewById(R.id.title);
			TextView small = (TextView) convertView.findViewById(R.id.small);

			title.setText(itemInfor[index].title);
			title.setTextColor(Color.WHITE);
			small.setText(itemInfor[index].small);
			small.setTextColor(Color.WHITE);

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

		private final String small;
		private final String title;

		private final Class<? extends android.app.Activity> demoClass;

		public ItemInfor(String title, String small,
				Class<? extends android.app.Activity> demoClass) {
			this.title = title;
			this.small = small;
			this.demoClass = demoClass;
		}

		public ItemInfor(String title,
				Class<? extends android.app.Activity> demoClass) {
			this.title = title;
			this.small = "";
			this.demoClass = demoClass;
		}
	}

}

