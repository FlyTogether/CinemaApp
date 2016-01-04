package com.cpp2.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cpp2.R;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseFragment;

public class FraMovie extends BaseFragment implements OnClickListener{
	/**
	 * 判断是否为画廊模式显示列表内容，默认为false
	 */
	private boolean isGalleryView ;

	private ImageButton ibtnChangView, ibtnPlaying, ibtnWillPlay;
	private TextView tvArea;

	private FraMoviePlaying moviePlaying;
	private FraMovieWillPlay movieWillPlay;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContext((BaseActivity)getActivity());
		System.out.println("movieFragment -- onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("movieFragment -- onCreateView and movieWillPlay is: "+movieWillPlay);
		View view = inflater.inflate(R.layout.fra_movie, null);

		ibtnChangView = (ImageButton) view
				.findViewById(R.id.movie_bar_image_changeview);
		ibtnPlaying = (ImageButton) view
				.findViewById(R.id.movie_bar_image_playing);
		ibtnWillPlay = (ImageButton) view
				.findViewById(R.id.movie_bar_image_willplay);
		tvArea = (TextView) view.findViewById(R.id.movie_bar_text);
		
		tvArea.setOnClickListener(this);
		ibtnPlaying.setSelected(true);
		ibtnWillPlay.setSaveEnabled(false);

		init();
		return view;
	}

	@Override
	public void onStart() {
		setListener();
		super.onStart();
	}

	private void init() {
		android.support.v4.app.FragmentManager manager = getFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();  
		movieWillPlay = new FraMovieWillPlay();
		moviePlaying = new FraMoviePlaying();
		tx.add(R.id.movie_content_framelayout, moviePlaying,"ONE");
		tx.add(R.id.movie_content_framelayout, movieWillPlay, "TWO");
		tx.hide(movieWillPlay);
        tx.commit();
	}
	//设置监听器
	private void setListener() {
		ibtnChangView.setOnClickListener(new ImageButtonListener(this));
		ibtnPlaying.setOnClickListener(new ImageButtonListener(this));
		ibtnWillPlay.setOnClickListener(new ImageButtonListener(this));
	}
	

	public void changeMovieView() {
		if (isGalleryView) {
			ibtnChangView.setSelected(false);
			isGalleryView = false;
		} else {
			ibtnChangView.setSelected(true);
			isGalleryView = true;
		}
		moviePlaying.changeViewModel(isGalleryView);
		movieWillPlay.changeViewModel(isGalleryView);
	}
	

	class ImageButtonListener implements OnClickListener {
		private Fragment fragment;
		public ImageButtonListener(Fragment fragment){
			this.fragment = fragment;
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.movie_bar_image_playing:
				if (!ibtnPlaying.isSelected()) {
					ibtnPlaying.setSelected(true);
					ibtnWillPlay.setSelected(false);
					FragmentTransaction fTransaction = this.fragment.getFragmentManager().beginTransaction();
					System.out.println("onClickPlaying -- fTransaction is empty: "+fTransaction.isEmpty());
					fTransaction.hide(movieWillPlay);
					fTransaction.show(moviePlaying);
					fTransaction.commit();
				}
				break;

			case R.id.movie_bar_image_willplay:
				if (!ibtnWillPlay.isSelected()) {
					ibtnWillPlay.setSelected(true);
					ibtnPlaying.setSelected(false);
					FragmentTransaction fTransaction = this.fragment.getFragmentManager().beginTransaction();
					System.out.println("onClickWllPlay -- fTransaction is empty: "+fTransaction.isEmpty());
					fTransaction.hide(moviePlaying);
					fTransaction.show(movieWillPlay);
					fTransaction.commit();
				}
				break;

			case R.id.movie_bar_image_changeview:
				changeMovieView();
				break;
			}
		}

	}


	@Override
	public void onClick(View v) {
		overlay(UILocation.class);
		
	}

	
}
