package com.cpp2.ui;

import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cpp2.R;
import com.cpp2.base.AppVariable;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseMessage;

public class UICommentMovie extends BaseActivity implements OnClickListener {
	private EditText edtComment;
	private Button btnSubmit;
	private TextView tvTitle;
	private ImageButton ibtnBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_movie_comment);
		// show keyboard
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

		((ImageButton) findViewById(R.id.activity_titlebar_menu))
				.setVisibility(View.INVISIBLE);
		ibtnBack = (ImageButton) findViewById(R.id.activity_titlebar_back);
		tvTitle = (TextView) findViewById(R.id.activity_titlebar_title);
		edtComment = (EditText) this.findViewById(R.id.movie_comment_edt);
		btnSubmit = (Button) this.findViewById(R.id.movie_comment_submit);
		
		btnSubmit.setOnClickListener(this);
		ibtnBack.setOnClickListener(this);

		// bind action logic
		Bundle params = this.getIntent().getExtras();
		String movieId = params.getString("movieId");
		tvTitle.setText(params.getString("movieName"));
		edtComment.setText("get the ID is:" + params.getString("movieId"));

		final String blogId = params.getString("blogId");
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String input = edtComment.getText().toString();
				HashMap<String, String> urlParams = new HashMap<String, String>();
				urlParams.put("blogId", blogId);
				urlParams.put("content", input);
				doTaskAsync(AppVariable.task.commentCreate,
						AppVariable.api.commentCreate, urlParams);
			}
		});
	}

	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		this.finish();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.movie_comment_submit:
			
			break;

		case R.id.activity_titlebar_back:
			this.finish();
			break;
		}
	}
}
