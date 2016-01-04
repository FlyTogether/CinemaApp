package com.cpp2.base;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

public class BaseFragment extends Fragment{
	/**
	 * 每个Activity中的用于处理UI主线程任务的BaseHandler，一般都要Activity自己去继承，拓展方法
	 */
	protected BaseFragmentHandler handler;
	protected BaseActivity context;
	
	public BaseFragment(){
		
	}
//	public BaseFragment(BaseActivity context){
//		this.context = context;
//		setHandler(new BaseHandler(context, this));
//	}
	/**
	 * 通过此方法将包含此Fragment的Activity对象传入其中，
	 * 创建默认的BaseFragmentHandler对象，方便Fragment调用Activity的方法
	 * @param context
	 */
	public void setContext(BaseActivity context){
		this.context = context;
		this.handler = new BaseFragmentHandler(context, this);
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
	public void setHandler (BaseFragmentHandler handler) {
		this.handler = handler;
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
		context.taskPool.addTask(taskId, new BaseTask(){
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
		context.taskPool.addTask(taskId, baseTask, delayTime);
	}
	
	public void doTaskAsync (int taskId, String taskUrl) {
		showLoadBar();
		context.taskPool.addTask(taskId, taskUrl, new BaseTask(){
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
		context.taskPool.addTask(taskId, taskUrl, taskArgs, new BaseTask(){
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
	
//	public void doTaskAsync (int taskId, int delayTime) {
//		context.doTaskAsync(taskId, delayTime);
//	}
//	
//	public void doTaskAsync (int taskId, BaseTask baseTask, int delayTime) {
//		context.doTaskAsync(taskId, delayTime);
//	}
//	
//	public void doTaskAsync (int taskId, String taskUrl) {
//		context.doTaskAsync(taskId, taskId);
//	}
//	
//	public void doTaskAsync (int taskId, String taskUrl, HashMap<String, String> taskArgs) {
//		context.doTaskAsync(taskId, taskUrl, taskArgs);
//	}
	
	public boolean isNetWorkConnected(){
		return context.isNetWorkConnected();
	}
}
