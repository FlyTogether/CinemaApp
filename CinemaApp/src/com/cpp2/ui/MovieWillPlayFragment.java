package com.cpp2.ui;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cpp2.R;
import com.cpp2.list.GridAdapter;
import com.cpp2.model.Movie;
import com.cpp2.ui.MoviePlayingFragment.GalleryAdapter;
import com.cpp2.ui.MoviePlayingFragment.MyGallery;

public class MovieWillPlayFragment extends Fragment implements OnItemSelectedListener{
	int screenWidth;
	private GridView gvMovie;
	private Gallery gMovie;
	private LinearLayout llMoviesContent;
	private GalleryAdapter adapter;
	
	private TextView tvMovieName, tvMovieRating;
	private RatingBar rbMovieRating;
	
	private String[] ratings = { "8.5", "2.6", "3.0", "1.0", "5.5", "8.5",
			"2.6", "3.0", "1.0", "5.5" };
	private Integer[] mThumbIds = { R.drawable.ic_launcher, R.drawable.sample2,
			R.drawable.sample3, R.drawable.sample5, R.drawable.sample4,
			R.drawable.ic_launcher, R.drawable.sample2, R.drawable.sample3,
			R.drawable.sample1, R.drawable.sample4 };
	private String[] movies = { "movie", "my", "youre", "hahahhohohoho1221",
			"nenan", "made", "fade", "youerde", "joejfoiwjefoij", "ijfeowj" };
	private ArrayList<Movie> movieList;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("movieWillPlay -- onCreate");
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("movieWillPlay -- onCreateView");
		View view = inflater.inflate(R.layout.fragment_movie_view_layout, null);
		
		llMoviesContent = (LinearLayout) view
				.findViewById(R.id.movie_view_linearlayout);
		tvMovieName = (TextView) view.findViewById(R.id.movie_gallery_moviename);
		tvMovieRating = (TextView) view.findViewById(R.id.movie_gallery_rating);
		rbMovieRating = (RatingBar) view.findViewById(R.id.movie_gallery_ratingbar);
		
		// 获取屏幕宽度
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);/* 将此窗口的Display的Metrics传到变量dm */
		screenWidth = dm.widthPixels;

		setData();
		init();

		return view;
	}

	//初始化两个显示电影的视图，并给布局添加默认视图
	private void init() {
		gMovie = new MyGallery(getActivity());
		gvMovie = new GridView(getActivity());
		adapter = new GalleryAdapter(getActivity());

		gMovie.setAdapter(adapter);
		gMovie.setOnItemSelectedListener(this);
		gMovie.setSpacing(30);
		gvMovie.setNumColumns(3);
		gvMovie.setAdapter(new GridAdapter(getActivity(), movieList,
				screenWidth / 3, screenWidth / 5 * 3));
		llMoviesContent.removeAllViews();
		llMoviesContent.addView(gvMovie);
	}

	// 提供测试数据
	private void setData() {
		movieList = new ArrayList<>();
		Movie movie;
		for (int i = 0; i < mThumbIds.length; i++) {
			movie = new Movie();
			movie.setPopularity(ratings[i]);
			movie.setMovieName(movies[i]);
			movieList.add(movie);
		}
	}

	/**
	 * 改变视图的显示模式
	 * 
	 * @param isGalleryView
	 *            如果为true，显示画廊模式；否则显示网格模式
	 */
	public void changeViewModel(boolean isGalleryView) {
		llMoviesContent.removeAllViews();
		if (isGalleryView) {
			llMoviesContent.addView(gMovie);
		} else {
			llMoviesContent.addView(gvMovie);
		}
	}

	public class GalleryAdapter extends BaseAdapter {
		private Context mContext;
		private LayoutInflater inflater;
		private int selectedPosition;

		public GalleryAdapter(Context c) {
			inflater = LayoutInflater.from(c);
			mContext = c;
		}

		@Override
		public int getCount() {
			return mThumbIds.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
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
			i.setImageResource(mThumbIds[position]);
			if(selectedPosition == position){
//		        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.my_scale_action);    //实现动画效果  
		        i.setLayoutParams(new Gallery.LayoutParams(512, 670)); 
//		        i.startAnimation(animation);  //选中时，这是设置的比较大 
		        
		    } 
		    else{ 
		        i.setLayoutParams(new Gallery.LayoutParams(342,446));  
		    }
			return i;
		}
	}

	class MyGallery extends Gallery {
		private int mCoveflowCenter;// 半径值

		public MyGallery(Context context) {
			super(context);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
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
		adapter.setSelectItem(position);
		tvMovieName.setText(movies[position]);
		tvMovieRating.setText(ratings[position]);
		rbMovieRating.setRating(Float.valueOf(ratings[position])/2);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}
