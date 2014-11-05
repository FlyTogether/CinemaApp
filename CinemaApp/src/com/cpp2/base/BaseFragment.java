package com.cpp2.base;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;

import com.cpp2.util.AppCache;

public class BaseFragment extends Fragment{
	protected BaseActivity context;
	
	public BaseFragment(BaseActivity context){
		this.context = context;
		setHandler(new BaseHandler(context, this));
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
	
	/**************************  Util Method  ************************/
	/**
	 * 显示消息提示框
	 * @param msg 要显示的消息内容
	 */
	public void showToast (String msg) {
		context.showToast(msg);
	}
	
	/**
	 * 跳转到目标Activity
	 * @param classObj 目标Activity类
	 */
	public void overlay (Class<?> classObj) {
        context.overlay(classObj);
	}
	/**
	 * 跳转到目标Activity，附带Bundle参数
	 * @param classObj 目标Activity类
	 */
	public void overlay (Class<?> classObj, Bundle params) {
        context.overlay(classObj, params);
	}
	
	/**
	 * 关闭当前Activity并跳转到目标Activity
	 * @param classObj 目标Activity类
	 */
	public void forward (Class<?> classObj) {
		context.forward(classObj);
	}
	/**
	 * 关闭当前Activity并跳转到目标Activity，附带Bundle参数
	 * @param classObj 目标Activity类
	 */
	public void forward (Class<?> classObj, Bundle params) {
		context.forward(classObj, params);
	}
	
	/**
	 * 设置当前Activity的Handler
	 * @param handler BaseHandler或者他的子类
	 */
	public void setHandler (BaseHandler handler) {
		context.setHandler(handler);
	}
	
	/**
	 * 显示加载进度条
	 */
	public void showLoadBar () {
		context.showLoadBar();
	}
	/**
	 * 隐藏加载进度条
	 */
	public void hideLoadBar () {
		context.hideLoadBar();
	}

	public void loadImage (ImageView view, final String url) {
		context.loadImage(view, url);
	}
	/**
	 * 读取图片，实质为检查SD卡中是否有这图片，如果本地没有则从网络下载并存储在SD卡
	 * @param url
	 */
	public void loadImage (final String url) {
		context.loadImage(url);
	}
	
	/*******************logic method**********************/
	public void sendMessage (int what) {
		context.sendMessage(what);
	}
	
	public void sendMessage (int what, String data) {
		context.sendMessage(what, data);
	}
	
	public void sendMessage (int what, int taskId, String data) {
		context.sendMessage(what, taskId, data);
	}
	
	public void doTaskAsync (int taskId, int delayTime) {
		context.doTaskAsync(taskId, delayTime);
	}
	
	public void doTaskAsync (int taskId, BaseTask baseTask, int delayTime) {
		context.doTaskAsync(taskId, delayTime);
	}
	
	public void doTaskAsync (int taskId, String taskUrl) {
		context.doTaskAsync(taskId, taskId);
	}
	
	public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
		context.doTaskAsync(taskId, taskUrl, taskArgs);
	}
	

	public class BitmapViewBinder implements ViewBinder {
		// 
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
		return context.isNetWorkConnected();
	}
}
