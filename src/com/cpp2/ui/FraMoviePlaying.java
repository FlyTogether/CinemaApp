package com.cpp2.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cpp2.R;
import com.cpp2.base.AppVariable;
import com.cpp2.base.BaseActivity;
import com.cpp2.base.BaseFragment;
import com.cpp2.base.BaseFragmentHandler;
import com.cpp2.base.BaseMessage;
import com.cpp2.base.BaseTask;
import com.cpp2.list.GridAdapter;
import com.cpp2.model.Movie;
import com.cpp2.util.AppCache;

@SuppressLint("HandlerLeak")
@SuppressWarnings("deprecation")
public class FraMoviePlaying extends BaseFragment implements
		OnItemSelectedListener, OnItemClickListener {

	int screenWidth, screenHeight;
	private GridView gvMovie;
	private Gallery gMovie;
	private LinearLayout llMoviesContent, llGalleryIntroduction;// 电影视图的父布局和画廊模式时下边的电影介绍布局
	private GalleryAdapter galleryAdapter;
	private GridAdapter gridAdapter;

	private TextView tvMovieName, tvMovieRating;// 画廊模式下边的电影介绍中的名称和评分
	private RatingBar rbMovieRating;

	private ArrayList<Movie> movieList;
	private ArrayList<Bitmap> movieImageList;

//	public MoviePlayingFragment(BaseActivity context) {
//		super(context);
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);/* 将此窗口的Display的Metrics传到变量dm */
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		System.out.println("wid: " + screenWidth + "\nheig: " + screenHeight);
		setContext((BaseActivity)getActivity());
//		setHandler(new BaseFragmentHandler(context, this));
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fra_movie_view_layout, null);

		llMoviesContent = (LinearLayout) view
				.findViewById(R.id.movie_view_linearlayout);
		llGalleryIntroduction = (LinearLayout) view
				.findViewById(R.id.movie_view_gallery_linearlayout);
		tvMovieName = (TextView) view
				.findViewById(R.id.movie_gallery_moviename);
		tvMovieRating = (TextView) view.findViewById(R.id.movie_gallery_rating);
		rbMovieRating = (RatingBar) view
				.findViewById(R.id.movie_gallery_ratingbar);

		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();

		init();
		HashMap<String, String> map = new HashMap<>();
		map.put("method", "getOnNowMovieForMobile");
		doTaskAsync(AppVariable.task.movieList, AppVariable.api.movieList, map);
	}

	// 初始化两个显示电影的视图，并给布局添加默认视图
	private void init() {
		gMovie = new MyGallery(getActivity());
		gvMovie = new GridView(getActivity());

		gvMovie.setNumColumns(3);
		llMoviesContent.removeAllViews();
		llMoviesContent.addView(gvMovie);
		llGalleryIntroduction.setVisibility(View.INVISIBLE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		 System.out.println("MoviePlayingFragment -- onTaskComplete");
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case AppVariable.task.movieList:
			try {
				movieList = (ArrayList<Movie>) message.getResultList("Movie");
				gridAdapter = new GridAdapter(getActivity(), movieList, screenWidth / 3, screenWidth / 5 * 3);
				gvMovie.setAdapter(gridAdapter);
				gvMovie.setOnItemClickListener(this);
				
				gMovie.setOnItemSelectedListener(this);
				gMovie.setOnItemClickListener(this);
				gMovie.setSpacing(screenWidth * 5 / 73);
				gMovie.setLayoutParams(new Gallery.LayoutParams(screenWidth, screenWidth));
				movieImageList = new ArrayList<Bitmap>();
				setHandler(new MyHandler(context, this));
				loadImageList();
			} catch (Exception e) {
				e.printStackTrace();
				showToast(e.getMessage());
			}
			break;
		}
	}

	/**
	 * 通过刚从网上获得的电影列表开启线程到网上下载对应的图片列表，下载完发出信息回到UI主线程设置图片
	 * @param movies
	 */
	private void loadImageList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < movieList.size(); i++) {
					Bitmap cacheBit = AppCache.getCachedImage(context, movieList.get(i).getImage());
					System.out.println("马上得到的第"+i+"张： "+cacheBit);
						movieImageList.add(cacheBit);
				}// for end
				sendMessage(BaseTask.LOAD_IMAGE);
			}
		}).start();

	}

	/**
	 * 改变视图的显示模式
	 * @param isGalleryView
	 *            如果为true，显示画廊模式；否则显示网格模式
	 */
	public void changeViewModel(boolean isGalleryView) {
		llMoviesContent.removeAllViews();
		if (isGalleryView) {
			llMoviesContent.addView(gMovie);
			llGalleryIntroduction.setVisibility(View.VISIBLE);
		} else {
			llMoviesContent.addView(gvMovie);
			llGalleryIntroduction.setVisibility(View.INVISIBLE);
		}
	}

	public class GalleryAdapter extends BaseAdapter {
		private Context mContext;
		private int selectedPosition;
		private List<Bitmap> movieImages;

		public GalleryAdapter(Context c, List<Bitmap> movieList) {
			mContext = c;
			this.movieImages = movieList;
		}

		/**
		 * 设置画廊的内容列表
		 * @param imageList
		 */
		public void setImageList(List<Bitmap> imageList) {
			this.movieImages = imageList;
		}

		@Override
		public int getCount() {
			return movieImages.size();
		}

		@Override
		public Object getItem(int position) {
			return movieImages.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public void setSelectItem(int position) {
			if (selectedPosition != position) {
				selectedPosition = position;
				notifyDataSetChanged();
			}
		}

		@Override
		// 只有在所有数据更新时调用
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = null;
			if (convertView == null) {
				i = new ImageView(mContext);
				convertView = new View(mContext);
				i.setAdjustViewBounds(true);
				i.setScaleType(ImageView.ScaleType.FIT_XY);
				convertView.setTag(i);

			} else {
				i = (ImageView) convertView.getTag();
			}
			i.setImageBitmap(movieImages.get(position));
			if (selectedPosition == position) {
				i.setLayoutParams(new Gallery.LayoutParams(screenWidth * 3 / 4,
						screenWidth));
			} else {
				i.setLayoutParams(new Gallery.LayoutParams(screenWidth * 3 / 5,
						screenWidth * 16 / 20));
			}
			return i;
		}
	}

	class MyGallery extends Gallery {

		public MyGallery(Context context) {
			super(context);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			System.out.println("moviePlaying -- onFling ");
			int keyCode;
			if (isScrollLeft(e1, e2)) {
				keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
			} else {
				keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
			}
			onKeyDown(keyCode, null);

			return false;
		}

		private boolean isScrollLeft(MotionEvent e1, MotionEvent e2) {
			return e2.getX() > e1.getX();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println("onItemSelected---------------position: "+position+"\nhalleryadapter's count: "+galleryAdapter.getCount());
		galleryAdapter.setSelectItem(position);
		Movie movie = movieList.get(position);
		tvMovieName.setText(movie.getName());
		tvMovieRating.setText(movie.getPopularity());
		rbMovieRating.setRating(Float.valueOf(movie.getPopularity()) / 2);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println("onitemClick");
		Movie movie = movieList.get(position);
		if (movie != null) {
			System.out.println("movie: " + movie);
			Bundle bundle = new Bundle();
			bundle.putInt("movieId", movie.getId());
			bundle.putString("movieName", movie.getName());
			bundle.putString("movieImage", movie.getImage());
			bundle.putString("director", movie.getDirector());
			bundle.putString("castActor", movie.getCastActor());
			bundle.putString("area", movie.getArea());
			bundle.putString("type", movie.getType());
			bundle.putString("style", movie.getStyle());
			bundle.putString("runTime", movie.getRuntime());
			bundle.putString("showTime", movie.getShowTime());
			bundle.putString("introduction", movie.getIntroduction());
			bundle.putString("popularity", movie.getPopularity());
			overlay(UIMovieDetail.class, bundle);
		}

	}

	class MyHandler extends BaseFragmentHandler {
		public MyHandler(BaseActivity ui, BaseFragment fragment) {
			super(ui, fragment);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				switch (msg.what) {
				case BaseTask.LOAD_IMAGE:
					galleryAdapter = new GalleryAdapter(getActivity(), movieImageList);
					// galleryAdapter.setImageList(movieImageList);
					gMovie.setAdapter(galleryAdapter);
					galleryAdapter.notifyDataSetChanged();
					
//					gridAdapter.setImageList(movieImageList);
					gridAdapter.notifyDataSetChanged();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}