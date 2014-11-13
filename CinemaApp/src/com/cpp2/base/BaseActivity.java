package com.cpp2.base;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

import com.cpp2.R;
import com.cpp2.util.AppCache;

public class BaseActivity extends FragmentActivity{
	/**
	 * 每个Activity中的用于处理UI主线程任务的BaseHandler，一般都要Activity自己去继承，拓展方法
	 */
	protected BaseHandler handler;
	/**
	 * 每个Activity的线程池类，用于添加线程任务
	 */
	protected BaseTaskPool taskPool;
	protected boolean isShowingLoadBar = false;//标识目前是否正在显示加载
	protected BaseApp app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// async task handler
		this.handler = new BaseHandler(this);
		// init task pool
		this.taskPool = new BaseTaskPool(this);
		this.app = (BaseApp) this.getApplicationContext();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	/**
	 * 带有返回信息的请求任务的回调方法
	 * @param taskId
	 */
	public void onTaskComplete(int taskId, BaseMessage message){}
	
	/**
	 * 没有返回信息的请求任务的回调方法
	 * @param taskId
	 */
	public void onTaskComplete (int taskId) {}

	/**
	 * 网络错误时显示提示框
	 * @param taskId
	 */
	public void onNetworkError (int taskId) {
		showToast(AppVariable.err.network);
	}
	
	/**************  Util Method  **************/
	/**
	 * 显示消息提示框
	 * @param msg 要显示的消息内容
	 */
	public void showToast (String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 跳转到目标Activity
	 * @param classObj 目标Activity类
	 */
	public void overlay (Class<?> classObj) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setClass(this, classObj);
        startActivity(intent);
	}
	/**
	 * 跳转到目标Activity，附带Bundle参数
	 * @param classObj 目标Activity类
	 */
	public void overlay (Class<?> classObj, Bundle params) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setClass(this, classObj);
        intent.putExtras(params);
        startActivity(intent);
	}
	
	/**
	 * 关闭当前Activity并跳转到目标Activity
	 * @param classObj 目标Activity类
	 */
	public void forward (Class<?> classObj) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		this.finish();
	}
	/**
	 * 关闭当前Activity并跳转到目标Activity，附带Bundle参数
	 * @param classObj 目标Activity类
	 */
	public void forward (Class<?> classObj, Bundle params) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		this.startActivity(intent);
		this.finish();
	}
	
	/**
	 * 设置当前Activity的Handler
	 * @param handler BaseHandler或者他的子类
	 */
	public void setHandler (BaseHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * 显示加载进度条
	 */
	public void showLoadBar () {
		this.findViewById(R.id.main_load_bar).setVisibility(View.VISIBLE);
		this.findViewById(R.id.main_load_bar).bringToFront();
		isShowingLoadBar = true;
	}
	/**
	 * 隐藏加载进度条
	 */
	public void hideLoadBar () {
		if (isShowingLoadBar) {
			this.findViewById(R.id.main_load_bar).setVisibility(View.GONE);
			isShowingLoadBar = false;
		}
	}
	/**
	 * 读取图片，实质为检查SD卡中是否有这图片，如果本地没有则从网络下载并存储在SD卡,此方法执行完肯定有图片（尽量确保）
	 * @param url
	 */
	public void loadImage (final String url) {
		taskPool.addTask(0, new BaseTask(){
			@Override
			public void onComplete(){
				Bitmap bit = AppCache.getImage(url);
				if(bit == null){
					
					new Thread(new Runnable(){
						@Override
						public void run() {
							Bitmap bit = AppCache.getRemoteImage(getContext(), url);
							System.out.println("刚从网络下载的图片 bitmap: "+bit);
							try{
								while(/*AppCache.getImage(url)*/bit == null){
									Thread.sleep(500);
								};
								sendMessage(BaseTask.LOAD_IMAGE);
							}catch (InterruptedException e) {
								System.err.println("interrupted");
							}
						}
					}).start();
				}else
					sendMessage(BaseTask.LOAD_IMAGE);
			}
		}, 0);
	}
	
	public void loadImage (final ImageView view, final String url){
		taskPool.addTask(0, new BaseTask(){
			@Override
			public void onComplete(){
				Bitmap bit = AppCache.getImage(url);
				if(bit == null){
					
					new Thread(new Runnable(){
						@Override
						public void run() {
							Bitmap cacheBit = AppCache.getRemoteImage(getContext(), url);
							System.out.println("刚从网络下载的 cacheBit: "+cacheBit);
							
							try{
								while(cacheBit != null){
									Thread.sleep(500);
								}
								view.setImageBitmap(cacheBit);
							}catch (InterruptedException e) {
								System.err.println("interrupted");
							}
						}
					}).start();
					
//				sendMessage(BaseTask.LOAD_IMAGE);
				}else{
					view.setImageBitmap(bit);
				}
			}
		}, 0);
	}
	
	public Context getContext(){
		return this;
	}
	/*******************logic method**********************/
	public void sendMessage (int what) {
		Message m = new Message();
		m.what = what;
		handler.sendMessage(m);
	}
	
	public void sendMessage (int what, String data) {
		Bundle b = new Bundle();
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}
	
	public void sendMessage (int what, int taskId, String data) {
		Bundle b = new Bundle();
		b.putInt("task", taskId);
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}
	
	public void doTaskAsync (int taskId, int delayTime) {
		taskPool.addTask(taskId, new BaseTask(){
			@Override
			public void onComplete () {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), null);
			}
			@Override
			public void onError (String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, delayTime);
	}
	
	public void doTaskAsync (int taskId, BaseTask baseTask, int delayTime) {
		taskPool.addTask(taskId, baseTask, delayTime);
	}
	
	public void doTaskAsync (int taskId, String taskUrl) {
		showLoadBar();
		taskPool.addTask(taskId, taskUrl, new BaseTask(){
			@Override
			public void onComplete (String httpResult) {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
			}
			@Override
			public void onError (String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, 0);
	}
	
	public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
		showLoadBar();
		taskPool.addTask(taskId, taskUrl, taskArgs, new BaseTask(){
			@Override
			public void onComplete (String httpResult) {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
			}
			@Override
			public void onError (String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, 0);
	}
	
	//将值与视图绑定在一起
	public class BitmapViewBinder implements ViewBinder {
		@Override
		public boolean setViewValue(View view, Object data, String textRepresentation) {
			if ((view instanceof ImageView) & (data instanceof Bitmap)) {
				ImageView iv = (ImageView) view;
				Bitmap bm = (Bitmap) data;
				iv.setImageBitmap(bm);
				return true;
			}
			return false;
		}
	}
	
	public boolean isNetWorkConnected(){
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
	
		return (info!=null&&info.isConnected());
	}
}
