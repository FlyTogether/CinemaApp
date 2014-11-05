package com.cpp2.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost.TabSpec;

import com.cpp2.R;
import com.cpp2.base.BaseActivity;

public class MainTabActivity extends BaseActivity{
	

	int[] tabItemImageSource = {R.drawable.main_tab_movie_selector, R.drawable.main_tab_cinema_selector,
			R.drawable.main_tab_discovery_selector, R.drawable.main_tab_my_selector};
	
	int[] tabItemStringSource = {R.string.tab_movie, R.string.tab_cinema, R.string.tab_discovery, R.string.tab_my};
	private Class<?>[] fragments = {MovieFragment.class, CinemaFragment.class, DiscoveryFragment.class, MyFragment.class};
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main_tab_layout);
		
		FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(), R.id.main_tab_fragment);
		
		
		for(int i=0; i<fragments.length; i++){
			TabSpec tabSpec = tabHost.newTabSpec(getResources().getString(tabItemStringSource[i])).setIndicator(getTabView(i));
			tabHost.addTab(tabSpec, fragments[i], null);
		}
	}
	
	@Override
	protected void onStart() {
//		checkLogin();
		super.onStart();
	}
	
	private View getTabView(int position){
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.main_tab_item, null);
		view.setBackgroundResource(tabItemImageSource[position]);
//		((ImageView)view.findViewById(R.id.main_tab_item_image)).setImageResource(tabItemImageSource[position]);;
//		((TextView)view.findViewById(R.id.main_tab_item_text)).setText(tabItemStringSource[position]);
		
		return view;
	}
	
}
