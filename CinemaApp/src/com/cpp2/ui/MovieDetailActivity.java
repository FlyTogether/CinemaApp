package com.cpp2.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cpp2.R;
import com.cpp2.base.BaseActivity;

public class MovieDetailActivity extends BaseActivity{
	private ImageView ivMovieImage;
	private TextView tvMovieName, tvRating, tvDirector, tvCastActor, tvType,
			 tvArea, tvRunTime, tvShowTime, tvIntroduction;
	
	private ImageButton ibtnBack, ibtnShare, ibtnCollect, ibtnComment, 
			ibtnIntroduction, ibtnComments, ibtnPurchase;
	
	private RatingBar rbPopularity;
	private ScrollView svContent;
	private Bundle bundle;//包含该Activity要显示的电影实体信息的类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_detail);
		
		ivMovieImage = 	(ImageView) findViewById(R.id.movie_detail_image);
		tvMovieName = 	(TextView) findViewById(R.id.activity_titlebar_title);
		tvRating = 		(TextView) findViewById(R.id.movie_detail_tv_rating);
		tvDirector = 	(TextView) findViewById(R.id.movie_detail_tv_director);
		tvCastActor = 	(TextView) findViewById(R.id.movie_detail_tv_castactor);
		tvType = 		(TextView) findViewById(R.id.movie_detail_tv_type);
		tvArea = 		(TextView) findViewById(R.id.movie_detail_tv_area);
		tvRunTime = 	(TextView) findViewById(R.id.movie_detail_tv_runtime);
		tvShowTime = 	(TextView) findViewById(R.id.movie_detail_tv_showtime);
		tvIntroduction = new TextView(this);
		tvIntroduction.setTextColor(Color.BLACK);
		
		ibtnBack = 		(ImageButton) findViewById(R.id.activity_titlebar_back);
		ibtnShare = 	(ImageButton) findViewById(R.id.activity_titlebar_menu);
		ibtnCollect = 	(ImageButton) findViewById(R.id.movie_detail_ibtn_collect);
		ibtnComment =	(ImageButton) findViewById(R.id.movie_detail_ibtn_comment);
		ibtnPurchase =	(ImageButton) findViewById(R.id.movie_detail_ibtn_purchase);
		ibtnIntroduction =(ImageButton) findViewById(R.id.movie_detail_tv_introduction);
		ibtnComments = 	(ImageButton) findViewById(R.id.movie_detail_tv_comment); 
		
		rbPopularity = (RatingBar) findViewById(R.id.movie_detail_ratingbar);
		svContent = (ScrollView) findViewById(R.id.movie_detail_scrollview);
		
		ibtnShare.setBackgroundResource(R.drawable.activity_titlebar_share);
		bundle = getIntent().getExtras();
		initData();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}
	//初始化界面，设置信息
	private void initData(){
		tvMovieName.setText(bundle.getString("movieName"));
		rbPopularity.setRating(Float.valueOf(bundle.getString("popularity"))/2);
		tvRating.setText(bundle.getString("popularity"));
		tvDirector.setText("导演："+bundle.getString("director"));
		tvCastActor.setText("主演："+bundle.getString("castActor"));
		tvType.setText("类型："+bundle.getString("type"));
		tvArea.setText("地区："+bundle.getString("area"));
		tvRunTime.setText("时长："+bundle.getString("runTime"));
		tvShowTime.setText("上映日期："+bundle.getString("showTime"));
		//设置电影主图片
		loadImage(ivMovieImage, bundle.getString("movieImage"));
		//为影评或介绍的滚动框默认加上介绍文本框
		tvIntroduction.setText(bundle.getString("introduction"));
		svContent.addView(tvIntroduction);
	}
	
}
