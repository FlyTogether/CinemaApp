package com.cpp2.list;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LittleTextAdapter extends BaseAdapter{
	public List<String> textList;
	public Context context;
	
	public LittleTextAdapter(Context context, List<String> list){
		textList = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return textList.size();
	}

	@Override
	public Object getItem(int position) {
		return textList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView mTextView = new TextView(context);
        mTextView.setText(textList.get(position));
        mTextView.setTextSize(15);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setPadding(10, 10, 10, 10);
        return mTextView;
	}

}
