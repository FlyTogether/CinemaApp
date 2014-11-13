package com.cpp2.base;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;

public class BaseApp extends Application {
	private static final String TAG = "DemoApplication";
	public LocationClient mLocationClient;
	public Vibrator mVibrator;
	public MyLocationListener mMyLocationListener;
	public double lat = 0;
	public double lng = 0; 
	
	private String s;
	private long l;
	private int i;
	
	public int getInt () {
		return i;
	}
	
	public void setInt (int i) {
		this.i = i;
	}
	
	public long getLong () {
		return l;
	}
	
	public void setLong (long l) {
		this.l = l;
	}
	
	public String getString () {
		return s;
	}
	
	public void setString (String s) {
		this.s = s;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(this);
		Log.i("BaiduLocationApiDem", "init success");
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		//mGeofenceClient = new GeofenceClient(getApplicationContext());
		
		
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
	}

	public class MyLocationListener implements BDLocationListener {
		CoordinateConverter converter = new CoordinateConverter();
		
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			converter.from(CoordType.COMMON);
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			lat = location.getLatitude();
			lng = location.getLongitude();
			converter.coord(new LatLng(lat, lng));
			lat = converter.convert().latitude;
			lng = converter.convert().longitude;
			Log.i(TAG, "convert"+lat+":"+lng);
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//Log.i(TAG, location.getAddrStr());
				//运营商信息
				sb.append("\noperationers : ");
				//sb.append(location.getOperators());

		
			}
			Log.i(TAG, 	location.getCity());
			
			
			Log.i("BaiduLocationApiDem", sb.toString());
		}
		
		
		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}

		


	}
}