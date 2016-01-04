package com.cpp2.list;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.cpp2.R;
import com.cpp2.self_defined.ItemInfo;

/**
 * ListView适配器
 * @author w.w
 */
public class ListViewAdapter extends ArrayAdapter<ItemInfo> {
	
	private LayoutInflater inflater;
	
	public ListViewAdapter(Context context, List<ItemInfo> list) {
		super(context, 0, list);
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemInfo info = getItem(position);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.location_item_listview, null);
		}
		
		ImageView name = (ImageView) convertView.findViewById(R.id.item_name);
		//name.setText(info.getName());
		
		return convertView;
	}

}
