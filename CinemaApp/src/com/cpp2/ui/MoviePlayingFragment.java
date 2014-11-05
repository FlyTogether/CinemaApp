package com.cpp2.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
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
import com.cpp2.base.BaseHandler;
import com.cpp2.base.BaseMessage;
import com.cpp2.list.GridAdapter;
import com.cpp2.model.Movie;
import com.cpp2.util.AppCache;
import com.cpp2.util.AppUtil;

public class MoviePlayingFragment extends BaseFragment implements OnItemSelectedListener, OnItemClickListener {
	public MoviePlayingFragment(BaseActivity context) {
		super(context);
	}
	int screenWidth, screenHeight;
	private GridView gvMovie;
	private Gallery gMovie;
	private LinearLayout llMoviesContent, llGalleryIntroduction;//电影视图的父布局和画廊模式时下边的电影介绍
	private GalleryAdapter galleryAdapter;
	
	private TextView tvMovieName, tvMovieRating;
	private RatingBar rbMovieRating;
	
	private ArrayList<Movie> movieList;
	private ArrayList<Bitmap> movieImageList;
//	private MyHandler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);/* 将此窗口的Display的Metrics传到变量dm */
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		System.out.println("wid: "+screenWidth+"\nheig: "+screenHeight);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_movie_view_layout, null);
		
		llMoviesContent = (LinearLayout) view.findViewById(R.id.movie_view_linearlayout);
		llGalleryIntroduction = (LinearLayout) view.findViewById(R.id.movie_view_gallery_linearlayout);
		tvMovieName = (TextView) view.findViewById(R.id.movie_gallery_moviename);
		tvMovieRating = (TextView) view.findViewById(R.id.movie_gallery_rating);
		rbMovieRating = (RatingBar) view.findViewById(R.id.movie_gallery_ratingbar);
		
//		setData();
		init();
		doTaskAsync(AppVariable.task.movieList, AppVariable.api.movieList, null);
		return view;
	}

	//初始化两个显示电影的视图，并给布局添加默认视图
	private void init() {
		gMovie = new MyGallery(getActivity());
		gvMovie = new GridView(getActivity());
		
		gvMovie.setNumColumns(3);
		llMoviesContent.removeAllViews();
		llMoviesContent.addView(gvMovie);
		llGalleryIntroduction.setVisibility(View.INVISIBLE);;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
//		System.out.println("MoviePlayingFragment -- onTaskComplete");
		super.onTaskComplete(taskId, message);
		switch (taskId) {
		case AppVariable.task.movieList:
			try {
				movieList = (ArrayList<Movie>) message.getResultList("Movie");
//				System.out.println("movieList: "+movieList);
				gvMovie.setAdapter(new GridAdapter(getActivity(), movieList, screenWidth / 3, screenWidth / 5 * 3));
				gMovie.setOnItemSelectedListener(this);
				gMovie.setOnItemClickListener(this);
				gMovie.setSpacing(screenWidth*5/73);
				gMovie.setLayoutParams(new Gallery.LayoutParams(screenWidth, screenWidth));
//				handler = new MyHandler(context);
				loadImageList(movieList);
			} catch (Exception e) {
				e.printStackTrace();
				showToast(e.getMessage());
			}
			break;
		
		}
	}

	@SuppressWarnings("unchecked")
	private void loadImageList(ArrayList<Movie> movies){
//		System.out.println("loadImageList -- - ");
		new AsyncTask<List<Movie>, Void, ArrayList<Bitmap>>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}

			@Override
			protected ArrayList<Bitmap> doInBackground(List<Movie>... params) {
				List<Movie> movies = params[0];
				ArrayList<Bitmap> images = new ArrayList<Bitmap>();
				for(int i=0; i<movies.size(); i++){
					loadImage(movies.get(i).getMovieImage());
					images.add(AppCache.getImage(movies.get(i).getMovieImage()));
				}
				return images;
			}
			
			@Override
			protected void onPostExecute(ArrayList<Bitmap> result) {
				super.onPostExecute(result);
				movieImageList = result;
//				System.out.println("doPost -- movieImageList: "+movieImageList);
				galleryAdapter = new GalleryAdapter(getActivity(), movieImageList);
				gMovie.setAdapter(galleryAdapter);
				galleryAdapter.setImageList(movieImageList);
				galleryAdapter.notifyDataSetChanged();
			}
		}.execute(movies);
		
		
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
		private LayoutInflater inflater;
		private int selectedPosition;
		private List<Bitmap> movieImages;

		public GalleryAdapter(Context c, List<Bitmap> movieList) {
			inflater = LayoutInflater.from(c);
			mContext = c;
			this.movieImages = movieList;
		}
		/**
		 * 设置画廊的内容列表
		 * @param imageList
		 */
		public void setImageList(List<Bitmap> imageList){
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

		public void setSelectItem(int position){
			if(selectedPosition != position){
				selectedPosition = position;
				notifyDataSetChanged();
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
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
			if(selectedPosition == position){
//		        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.my_scale_action);//实现动画效果  
		        i.setLayoutParams(new Gallery.LayoutParams(screenWidth*3/4, screenWidth)); 
//		        i.startAnimation(animation);  //选中时，这是设置的比较大 
		    } 
		    else{ 
		        i.setLayoutParams(new Gallery.LayoutParams(screenWidth*3/5,screenWidth*16/20));  
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
		System.out.println("onItemSelected---------------");
		galleryAdapter.setSelectItem(position);
			Movie movie = movieList.get(position);
			tvMovieName.setText(movie.getMovieName());
			tvMovieRating.setText(movie.getPopularity());
			rbMovieRating.setRating(Float.valueOf(movie.getPopularity())/2);
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println("onitemClick");
		Movie movie = movieList.get(position);
		if(movie != null){
			System.out.println("movie: "+movie);
			Bundle bundle = new Bundle();
			bundle.putString("movieName", movie.getMovieName());
			bundle.putString("movieImage", movie.getMovieImage());
			bundle.putString("director", movie.getDirector());
			bundle.putString("castActor", movie.getCastActor());
			bundle.putString("area", movie.getArea());
			bundle.putString("type", movie.getType());
			bundle.putString("style", movie.getStyle());
			bundle.putString("runTime", movie.getRunTime());
			bundle.putString("showTime", movie.getShowTime());
			bundle.putString("introduction", movie.getIntroduction());
			bundle.putString("popularity", movie.getPopularity());
			forward(MovieDetailActivity.class, bundle);
		}
		
	}

	
}