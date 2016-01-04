package com.cpp2.ui;

import java.util.zip.Inflater;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpp2.R;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseAuth;
import com.cpp2.base.BaseFragment;

public class FraMyInformation extends BaseFragment {
	// public FraMyInformation(BaseActivity context) {
	// super(context);
	// }

	private static final String LTAG = FraMyInformation.class.getSimpleName();

	private ImageView head = null;// 头像

	private Button myinfor_username = null;// 隐形按钮，用户名
	private Button myinfor_sign = null;// 个性签名

	private View view ;
	private Button islogin;
	ListView mListView1;
	ListView mListView2;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				context.getLayoutInflater().inflate(
						R.layout.fra_myinformation_blank, null);

				Toast.makeText(context, "you press me", Toast.LENGTH_SHORT)
						.show();
				break;

			default:
				break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.fra_myinformation);
		setContext((BaseActivity) getActivity());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(BaseAuth.isLogin()){
		view = inflater.inflate(R.layout.fra_myinformation, null);

		
		head = (ImageView) view.findViewById(R.id.head_image);
		myinfor_username = (Button) view
				.findViewById(R.id.login_button_myinfo_id);
		myinfor_username.setText(BaseAuth.getCustomer().getUsername());
		myinfor_sign = (Button) view
				.findViewById(R.id.login_button_myinfo_sign);
		mListView1 = (ListView) view.findViewById(R.id.list1);
		mListView2 = (ListView) view.findViewById(R.id.list2);
		mListView1.setAdapter(new DemoListAdapter(demos1));
		mListView2.setAdapter(new DemoListAdapter(demos2));
		mListView1.setOnItemClickListener(new onClick());
		
		mListView2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int index,
					long arg3) {
				onListItemClick(index);
			}
		});
		return view;
		}
		else {
			view = inflater.inflate(R.layout.fra_myinformation_blank, null);

			islogin = (Button)view.findViewById(R.id.islogin);
			islogin.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					overlay(UILogin.class);
				}
			});
			
			return view;
		}
	}

	private class onClick implements OnItemClickListener {


		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			//view = inflater.inflate(R.layout.fra_myinformation_blank, null);
			
//onCreateView(inflater, container, savedInstanceState);

			//onCreate(savedInstanceState);
			//transaction.commit();
		}

	}

	void onListItemClick(int index) {
		 Intent intent = null;
		 intent = new Intent(context, demos2[index].demoClass);
		 this.startActivity(intent);
	

	}

	private ItemInfor[] demos1 = {
			new ItemInfor(R.string.my_acount, UILogin.class),
			new ItemInfor(R.string.my_film_friend, UILogin.class),
			new ItemInfor(R.string.my_collect, UILogin.class) };
	private ItemInfor[] demos2 = {
			new ItemInfor(R.string.the_billing_details, UILogin.class),
			new ItemInfor(R.string.discount, UILogin.class),
			new ItemInfor(R.string.setting, UISetting.class) };

	// @Override
	// protected void onResume() {
	// super.onResume();
	// }
	//
	// @Override
	// protected void onDestroy() {
	// super.onDestroy();
	// }

	private class DemoListAdapter extends BaseAdapter {
		private ItemInfor[] itemInfor;

		public DemoListAdapter(ItemInfor itemInfor[]) {
			super();
			this.itemInfor = new ItemInfor[itemInfor.length];
			for (int i = 0; i < itemInfor.length; i++) {
				this.itemInfor[i] = itemInfor[i];
			}
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			convertView = View.inflate(context, R.layout.listview_info_item,
					null);
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT, 70);
			convertView.setLayoutParams(lp);
			TextView title = (TextView) convertView.findViewById(R.id.title);

			title.setText(itemInfor[index].title);
			title.setTextColor(Color.WHITE);

			return convertView;
		}

		@Override
		public int getCount() {
			return demos1.length;
		}

		@Override
		public Object getItem(int index) {
			return demos1[index];
		}

		@Override
		public long getItemId(int id) {
			return id;
		}
	}

	private class ItemInfor {
		private final int title;

		private final Class<? extends BaseActivity> demoClass;

		public ItemInfor(int title, Class<? extends BaseActivity> demoClass) {
			this.title = title;

			this.demoClass = demoClass;
		}
	}

}
