package com.cpp2.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.cpp2.R;
import com.cpp2.base.BaseActivity;

public class MovieFragment extends Fragment {
	/**
	 * 判断是否为画廊模式显示列表内容，默认为false
	 */
	private boolean isGalleryView ;

	private ImageButton ibtnChangView, ibtnPlaying, ibtnWillPlay;
	private Spinner spArea;

	private MoviePlayingFragment moviePlaying;
	private MovieWillPlayFragment movieWillPlay;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("movieFragment -- onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("movieFragment -- onCreateView");
		View view = inflater.inflate(R.layout.fragment_movie, null);

		ibtnChangView = (ImageButton) view
				.findViewById(R.id.movie_bar_image_changeview);
		ibtnPlaying = (ImageButton) view
				.findViewById(R.id.movie_bar_image_playing);
		ibtnWillPlay = (ImageButton) view
				.findViewById(R.id.movie_bar_image_willplay);
		spArea = (Spinner) view.findViewById(R.id.movie_bar_spinner);
		System.out.println("isg: "+isGalleryView);
		
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
		movieWillPlay = new MovieWillPlayFragment();
		moviePlaying = new MoviePlayingFragment((BaseActivity)getActivity());
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

	
}
