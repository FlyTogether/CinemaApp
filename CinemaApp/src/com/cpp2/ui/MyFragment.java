package com.cpp2.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpp2.R;

public class MyFragment extends Fragment{
	private GridView gvMovies;
	private LinearLayout llMovies;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, null);
		llMovies = (LinearLayout) view.findViewById(R.id.fragment_my_content);
		TextView tv = (TextView) view.findViewById(R.id.main_tab_item_text);
		tv.setText("MyFagment");
		return view;
	}
	
}
