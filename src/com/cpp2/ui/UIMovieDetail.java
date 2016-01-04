package com.cpp2.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpp2.R;
import com.cpp2.base.AppVariable;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseMessage;
import com.cpp2.model.MovieComment;
import com.cpp2.util.AppUtil;
import com.cpp2.util.SDUtil;

@SuppressWarnings("unchecked")
public class UIMovieDetail extends BaseActivity implements OnClickListener{
	/**
	 * 标识是否正在显示影评，默认为false
	 */
	private boolean isShowComments = false;
	private ImageView ivMovieImage;
	
	private TextView tvMovieName, tvRating, tvDirector, tvCastActor, tvType,
			 tvArea, tvRunTime, tvShowTime, tvIntroduction, tvIntroduce, tvComments,
			 tvMovieComents;
	
	private ImageButton ibtnBack, ibtnShare, ibtnCollect, ibtnComment, 
			ibtnIntroduction, ibtnComments, ibtnPurchase;
	
	private RatingBar rbPopularity;
	private ScrollView svContent;
	
	private Bundle bundleMovie;//包含该Activity要显示的电影实体信息的类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_movie_detail);
		
		ivMovieImage = 	(ImageView) findViewById(R.id.movie_detail_image);
		tvMovieName = 	(TextView) findViewById(R.id.activity_titlebar_title);
		tvRating = 		(TextView) findViewById(R.id.movie_detail_tv_rating);
		tvDirector = 	(TextView) findViewById(R.id.movie_detail_tv_director);
		tvCastActor = 	(TextView) findViewById(R.id.movie_detail_tv_castactor);
		tvType = 		(TextView) findViewById(R.id.movie_detail_tv_type);
		tvArea = 		(TextView) findViewById(R.id.movie_detail_tv_area);
		tvRunTime = 	(TextView) findViewById(R.id.movie_detail_tv_runtime);
		tvShowTime = 	(TextView) findViewById(R.id.movie_detail_tv_showtime);
		tvIntroduce =	(TextView) findViewById(R.id.movie_detail_tv_introduction);
		tvComments =	(TextView) findViewById(R.id.movie_detail_tv_comments);
		tvIntroduction = new TextView(this);
		tvIntroduction.setTextColor(Color.BLACK);
		tvIntroduction.setLineSpacing(45, 0);
		
		ibtnBack = 		(ImageButton) findViewById(R.id.activity_titlebar_back);
		ibtnShare = 	(ImageButton) findViewById(R.id.activity_titlebar_menu);
		ibtnCollect = 	(ImageButton) findViewById(R.id.movie_detail_ibtn_collect);
		ibtnComment =	(ImageButton) findViewById(R.id.movie_detail_ibtn_comment);
		ibtnPurchase =	(ImageButton) findViewById(R.id.movie_detail_ibtn_purchase);
		ibtnIntroduction =(ImageButton) findViewById(R.id.movie_detail_ibtn_introduction);
		ibtnComments = 	(ImageButton) findViewById(R.id.movie_detail_ibtn_comments); 
		
		rbPopularity = 	(RatingBar) findViewById(R.id.movie_detail_ratingbar);
		svContent = 	(ScrollView) findViewById(R.id.movie_detail_scrollview);
		
		ibtnShare.setBackgroundResource(R.drawable.m_icon_share);
		bundleMovie = getIntent().getExtras();
		ibtnIntroduction.setSelected(true);
		tvIntroduce.setTextColor(Color.WHITE);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		tvMovieComents = new TextView(this);
		tvMovieComents.setText("暂无任何评论！");
		setListener();
		initData();
		HashMap<String, String> params = new HashMap<>();
		params.put("id", bundleMovie.getString("movieId"));
//		doTaskAsync(AppVariable.task.movieComments, AppVariable.api.movieComments, params);
	}
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case AppVariable.task.commentList:
			try {
				ArrayList<MovieComment> commentList = (ArrayList<MovieComment>) message.getResultList("MovieComments");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		}
	}
	
	//为视图按钮设置监听器
	private void setListener(){
		ibtnBack.setOnClickListener(this);
		ibtnShare.setOnClickListener(this);
		ibtnCollect.setOnClickListener(this);
		ibtnComment.setOnClickListener(this);
		ibtnIntroduction.setOnClickListener(this);
		ibtnComments.setOnClickListener(this);
		ibtnPurchase.setOnClickListener(this);
	}
	
	//初始化界面，设置信息
	private void initData(){
		tvMovieName.setText(bundleMovie.getString("movieName"));
		rbPopularity.setRating(Float.valueOf(bundleMovie.getString("popularity"))/2);
		tvRating.setText(bundleMovie.getString("popularity"));
		tvDirector.setText("导演："+bundleMovie.getString("director"));
		tvCastActor.setText("主演："+bundleMovie.getString("castActor"));
		tvType.setText("类型："+bundleMovie.getString("type"));
		tvArea.setText("地区："+bundleMovie.getString("area"));
		tvRunTime.setText("时长："+bundleMovie.getString("runTime"));
		tvShowTime.setText("上映日期："+bundleMovie.getString("showTime"));
		//设置电影主图片
		loadImage(ivMovieImage, bundleMovie.getString("movieImage"));
		ivMovieImage.setImageBitmap(SDUtil.getSample(AppUtil.md5(bundleMovie.getString("movieImage"))));
		//为影评或介绍的滚动框默认加上介绍文本框
		tvIntroduction.setText(bundleMovie.getString("introduction"));
		svContent.addView(tvIntroduction);
	}
	
	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.activity_titlebar_menu:
			Toast.makeText(this, "你按了分享", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.movie_detail_ibtn_collect:
			Toast.makeText(this, "你按了收藏", Toast.LENGTH_SHORT).show();
			break;

		case R.id.movie_detail_ibtn_comment:
			overlay(UICommentMovie.class, bundleMovie);
			break;
			
		case R.id.movie_detail_ibtn_introduction:
			if(!ibtnIntroduction.isSelected()){
				ibtnIntroduction.setSelected(true);
				tvIntroduce.setTextColor(Color.WHITE);
				ibtnComments.setSelected(false);
				tvComments.setTextColor(Color.BLACK);
				
				svContent.removeAllViews();
				svContent.addView(tvIntroduction);
			}
			break;

		case R.id.movie_detail_ibtn_comments:
			if(!ibtnComments.isSelected()){
				ibtnIntroduction.setSelected(false);
				tvIntroduce.setTextColor(Color.WHITE);
				ibtnComments.setSelected(true);
				tvComments.setTextColor(Color.BLACK);
				
				svContent.removeAllViews();
				svContent.addView(tvMovieComents);
				Toast.makeText(this, "你按了查看影评", Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.movie_detail_ibtn_purchase:
			overlay(UIOrderDetail.class, bundleMovie);
			break;
			
		case R.id.activity_titlebar_back:
			this.finish();
			break;
		}
	}
}
