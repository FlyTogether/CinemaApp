package com.cpp2.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.cpp2.R;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseApp;
import com.cpp2.list.ListViewAdapter;
import com.cpp2.self_defined.ItemInfo;

public class UILocation extends BaseActivity implements
SwipeRefreshLayout.OnRefreshListener {



/**
* 给ListView添加下拉刷新
*/
private SwipeRefreshLayout swipeLayout;
private final List<LatLng> LatLnglists = new ArrayList<LatLng>();
/**
* ListView
*/
SharedPreferences pePreferences = null;
Editor editor = null;
private ListView listView;

/**
* ListView适配器
*/
private ListViewAdapter adapter;

private List<ItemInfo> infoList;
private Thread thread;
private boolean isThreadRun = false;
private double lat = 0;
private double lng = 0;
protected static final String TAG = "OverlayDemo";
private LocationClient mLocationClient;
// private TextView LocationResult;

private LocationMode tempMode = LocationMode.NORMAL;
private String tempcoor = "gcj02";
/**
* MapView 是地图主控件
*/

private MapView mMapView;
private BaiduMap mBaiduMap;
private Marker mMarkerA;
private Marker mMarkerB;
private Marker mMarkerC;
private Marker mMarkerD;
private Marker mMarkerE;
private InfoWindow mInfoWindow;

private Handler mHandler = new Handler() {

@Override
public void handleMessage(Message msg) {
	// TODO Auto-generated method stub
	super.handleMessage(msg);
	switch (msg.what) {
	case 1:

		mMarkerA.setPosition(new LatLng(lat, lng));
		Log.i(TAG, "跳转数据是L:" + lat + ":" + lng);
		break;

	}
}

};
// 初始化全局 bitmap 信息，不用时及时 recycle
BitmapDescriptor bdA = BitmapDescriptorFactory
	.fromResource(R.drawable.icon_marka);
BitmapDescriptor bdB = BitmapDescriptorFactory
	.fromResource(R.drawable.icon_markb);
BitmapDescriptor bdC = BitmapDescriptorFactory
	.fromResource(R.drawable.icon_markc);
BitmapDescriptor bdD = BitmapDescriptorFactory
	.fromResource(R.drawable.icon_markd);
BitmapDescriptor bd = BitmapDescriptorFactory
	.fromResource(R.drawable.icon_gcoding);
BitmapDescriptor bdGround = BitmapDescriptorFactory
	.fromResource(R.drawable.ground_overlay);

@SuppressLint("NewApi")
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.ui_location);

pePreferences = getSharedPreferences("node", Context.MODE_PRIVATE);
editor = pePreferences.edit();

mMapView = (MapView) findViewById(R.id.bmapView);
mBaiduMap = mMapView.getMap();
MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
mBaiduMap.setMapStatus(msu);

// mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
initOverlay();
swipeLayout = (SwipeRefreshLayout) this
		.findViewById(R.id.swipe_refresh);
swipeLayout.setOnRefreshListener(this);

// 顶部刷新的样式
swipeLayout.setColorScheme(android.R.color.holo_red_light,
		android.R.color.holo_green_light,
		android.R.color.holo_blue_bright,
		android.R.color.holo_orange_light);

infoList = new ArrayList<ItemInfo>();
ItemInfo info = new ItemInfo();
info.setName("coin");
infoList.add(info);
listView = (ListView) this.findViewById(R.id.listview);
adapter = new ListViewAdapter(this, infoList);
listView.setAdapter(adapter);
mLocationClient = ((BaseApp) getApplication()).mLocationClient;
// LocationResult = (TextView)findViewById(R.id.textView1);
// ((DemoApplication)getApplication()).mLocationResult = LocationResult;

mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
	public boolean onMarkerClick(final Marker marker) {
		Button button = new Button(getApplicationContext());
		button.setBackgroundResource(R.drawable.popup);
		OnInfoWindowClickListener listener = null;
		if (marker == mMarkerA || marker == mMarkerD) {
			button.setText("更改位置");
			listener = new OnInfoWindowClickListener() {
				public void onInfoWindowClick() {
					LatLng ll = marker.getPosition();
					LatLng llNew = new LatLng(ll.latitude + 0.005,
							ll.longitude + 0.005);
					marker.setPosition(llNew);
					mBaiduMap.hideInfoWindow();
				}
			};
			LatLng ll = marker.getPosition();
			mInfoWindow = new InfoWindow(BitmapDescriptorFactory
					.fromView(button), ll, -47, listener);
			mBaiduMap.showInfoWindow(mInfoWindow);
		} else if (marker == mMarkerB) {
			button.setText("更改图标");
			button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					marker.setIcon(bd);
					mBaiduMap.hideInfoWindow();
				}
			});
			LatLng ll = marker.getPosition();
			mInfoWindow = new InfoWindow(button, ll, -47);
			mBaiduMap.showInfoWindow(mInfoWindow);
		} else if (marker == mMarkerC) {
			button.setText("删除");
			button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					marker.remove();
					mBaiduMap.hideInfoWindow();
				}
			});
			LatLng ll = marker.getPosition();
			mInfoWindow = new InfoWindow(button, ll, -47);
			mBaiduMap.showInfoWindow(mInfoWindow);
		}
		return true;
	}
});
thread = new Thread(new Runnable() {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Log.i(TAG, "keeping ");
				Thread.sleep(4000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!isThreadRun) {
				continue;
			}
			isThreadRun = false;
			mHandler.obtainMessage(0).sendToTarget();

			lat = ((BaseApp) getApplication()).lat;
			lng = ((BaseApp) getApplication()).lng;
//Toast.makeText(OverlayDemo.this, lat+"", Toast.LENGTH_SHORT).show();
			
			mHandler.obtainMessage(1).sendToTarget();

		}
	}
});
thread.start();

// startLocation.setOnClickListener(new OnClickListener() {
// @Override
// public void onClick(View v) {
// // TODO Auto-generated method stub
// InitLocation();
// if(startLocation.getText().equals(getString(R.string.startlocation))){
// mLocationClient.start();
// startLocation.setText(getString(R.string.stoplocation));
// //mHandler.obtainMessage(1).sendToTarget();
// isThreadRun = true;
//
// }else{
// isThreadRun = false;
// //thread.destroy();
// startLocation.setText(getString(R.string.startlocation));
// }
// }
// });
}

private void InitLocation() {
LocationClientOption option = new LocationClientOption();

// option.setLocationMode(tempMode);//设置定位模式
option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
int span = 5000;

option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
// option.setIsNeedAddress(true);
option.setAddrType("all");
mLocationClient.setLocOption(option);
}

@SuppressLint("NewApi")
public void initOverlay() {
// add marker overlay
LatLng llA = new LatLng(21.156383, 110.295736);


		

HashSet<String> set = (HashSet<String>) pePreferences.getStringSet(
		"nodes", null);
if (set != null) {
	for (Iterator it = set.iterator(); it.hasNext();) {
		String data = (String) it.next();
		String datas[] = data.split("\\:");
		LatLng latLng = new LatLng(Double.parseDouble(datas[0]),
				Double.parseDouble(datas[1]));
		OverlayOptions oo = new MarkerOptions().position(latLng)
				.icon(bdB).zIndex(5);
		Marker mMarker = null;
		mMarker = (Marker) (mBaiduMap.addOverlay(oo));
		// Log.i(TAG,
		// "marker:"+Double.parseDouble(datas[0])+":"+Double.parseDouble(datas[1]));
	}

}

OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
		.zIndex(9).draggable(true);

mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

// LatLng llE = new LatLng(21.15632, 110.296021);
// OverlayOptions ooE = new MarkerOptions().position(llE).icon(bdC)
// .zIndex(9).draggable(true);
// mMarkerE = (Marker) (mBaiduMap.addOverlay(ooE));
// mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

// add ground overlay
LatLng southwest = new LatLng(39.92235, 116.380338);
LatLng northeast = new LatLng(39.947246, 116.414977);
LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
		.include(southwest).build();

OverlayOptions ooGround = new GroundOverlayOptions()
		.positionFromBounds(bounds).image(bdGround).transparency(0.8f);
mBaiduMap.addOverlay(ooGround);
// 设置地图的中心位置，地图状态
MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(new LatLng(
		21.155847, 110.296452));
mBaiduMap.setMapStatus(u);

mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
	public void onMarkerDrag(Marker marker) {
	}

	public void onMarkerDragEnd(Marker marker) {
		Toast.makeText(
				UILocation.this,
				"拖拽结束，新位置：" + marker.getPosition().latitude + ", "
						+ marker.getPosition().longitude,
				Toast.LENGTH_LONG).show();
	}

	public void onMarkerDragStart(Marker marker) {
	}
});
}

/**
* 清除所有Overlay
* 
* @param view
*/
public void clearOverlay(View view) {
mBaiduMap.clear();
}

/**
* 重新添加Overlay
* 
* @param view
*/
public void resetOverlay(View view) {
clearOverlay(null);
initOverlay();
}

@Override
protected void onPause() {
// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
mMapView.onPause();

super.onPause();
}

@Override
protected void onResume() {
// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
mMapView.onResume();

super.onResume();
}

@Override
protected void onDestroy() {
// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
super.onDestroy();
mMapView.onDestroy();
Log.i(TAG, "destory");
// android.os.Process.killProcess(android.os.Process.);//
// 回收 bitmap 资源
bdA.recycle();
bdB.recycle();
bdC.recycle();
bdD.recycle();
bd.recycle();
bdGround.recycle();
}

@Override
public void onRefresh() {
// TODO Auto-generated method stub
new Handler().postDelayed(new Runnable() {
	public void run() {
		swipeLayout.setRefreshing(false);
		Log.i(TAG, "login loca" + lat + ":" + lng);

		InitLocation();
		mLocationClient.start();
		if (!isThreadRun) {
			isThreadRun = true;
		} else {
			isThreadRun = false;
		}

	}

}, 500);
}

}