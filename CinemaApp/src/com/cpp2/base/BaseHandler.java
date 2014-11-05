package com.cpp2.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.cpp2.util.AppUtil;

/**
 * 继承Handler类，重写handleMessage方法
 * @author Administrator
 *
 */
public class BaseHandler extends Handler {
	protected BaseFragment fragment;
	protected BaseActivity ui;
	
	public BaseHandler (BaseActivity ui) {
		this(ui, null);
	}
	/**
	 * 如果是在Fragment界面里要调用Activity访问网络得到数据并回调到Fragment的话，就要传入当前Fragment对象
	 * @param ui
	 * @param fragment
	 */
	public BaseHandler (BaseActivity ui, BaseFragment fragment){
		this.ui = ui;
		this.fragment = fragment;
	}
	
	public BaseHandler (Looper looper) {
		super(looper);
	}
	
	@Override
	public void handleMessage(Message msg) {
		try {
			int taskId;
			String result;
			switch (msg.what) {
				case BaseTask.TASK_COMPLETE:
					ui.hideLoadBar();
					taskId = msg.getData().getInt("task");
					result = msg.getData().getString("data");
					if (result != null) {
						BaseMessage message = AppUtil.getMessage(result);
						ui.onTaskComplete(taskId, message);
						if(fragment!=null)
							fragment.onTaskComplete(taskId, message);
					} else if (!AppUtil.isEmptyInt(taskId)) {
						ui.onTaskComplete(taskId);
						if(fragment!=null)
							fragment.onTaskComplete(taskId);
					} else {
						ui.showToast(AppVariable.err.message);
					}
					break;
				case BaseTask.NETWORK_ERROR:
					ui.hideLoadBar();
					taskId = msg.getData().getInt("task");
					ui.onNetworkError(taskId);
					break;
				case BaseTask.SHOW_LOADBAR:
					ui.showLoadBar();
					break;
				case BaseTask.HIDE_LOADBAR:
					ui.hideLoadBar();
					break;
				case BaseTask.SHOW_TOAST:
					ui.hideLoadBar();
					result = msg.getData().getString("data");
					ui.showToast(result);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ui.showToast(e.getMessage());
		}
	}
	
}