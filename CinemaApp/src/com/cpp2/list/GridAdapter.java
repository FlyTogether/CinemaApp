package com.cpp2.list;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cpp2.R;
import com.cpp2.base.BaseActivity;
import com.cpp2.model.Movie;
import com.cpp2.util.AppCache;
import com.cpp2.util.AppUtil;
import com.cpp2.util.SDUtil;

/**
 * 电影界面的网格适配器
 * @author DeathLove
 *
 */
@SuppressLint("InflateParams")
public class GridAdapter extends BaseAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private List<Movie> movieList;
	private List<Bitmap> movieImageList;
	//网格中每一格的宽与高
	private int width, height;
	
	/**
	 * 网格中的每一项对应的类
	 * @author DeathLove
	 *
	 */
	public class GridViewItem{
		public ImageView movieImage;
		public RatingBar ratingBar;
		public TextView rating;
		public TextView movieName;
	}
	/**
	 * 用给定的宽高给网格的每一项设置宽高，构造网格布局适配器
	 * @param context
	 * @param movieList 数据模型数组
	 * @param width
	 * @param height
	 */
	public GridAdapter(Context context, List<Movie> movieList, int width, int height){
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.movieList = movieList;
		this.width = width;
		this.height = height;
	}
	//没有传入布局宽高则以默认的宽高显示
	public GridAdapter(Context context, List<Movie> movieList){
		this(context, movieList, 100, 180);
	}
	
	@Override
	public int getCount() {
		return movieList.size();
	}

	/**
	 * 返回数组中一个基本模型的子类对象
	 */
	@Override
	public Object getItem(int position) {
		return movieList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void setImageList(List<Bitmap> imageList){
		this.movieImageList = imageList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Movie movie = movieList.get(position);
		GridViewItem item = null;
		//如果convertView存在，则从中得到该网格的对象，类似缓存，优化显示效果；不存在则新建对象
		if(convertView == null){
			item = new GridViewItem();
			convertView = inflater.inflate(R.layout.gridview_movie_item, null);
			convertView.setLayoutParams(new GridView.LayoutParams(width, height));
			convertView.setPadding(12, 8, 12, 8);
			
			item.movieImage = (ImageView) convertView.findViewById(R.id.movie_gridview_movieimage);
			item.ratingBar = (RatingBar) convertView.findViewById(R.id.movie_gridview_ratingbar);
			item.rating = (TextView) convertView.findViewById(R.id.movie_gridview_rating);
			item.movieName= (TextView)convertView.findViewById(R.id.movie_gridview_moviename);
			convertView.setTag(item);
		}
		else{
			item = (GridViewItem)convertView.getTag();
		}
		Bitmap bitmap = SDUtil.getSample(AppUtil.md5(movie.getImage()));
		if(bitmap != null)
			item.movieImage.setImageBitmap(bitmap);
//		else{
//			if(movieImageList != null){
//				item.movieImage.setImageBitmap(movieImageList.get(position));
//			}
//		}
			
//		((BaseActivity)context).loadImage(item.movieImage, movie.getMovieImage());
		item.ratingBar.setRating(Float.parseFloat(movie.getPopularity())/2);
		item.rating.setText(movie.getPopularity());
		item.movieName.setText(movie.getName());
		
		return convertView;
	}
	
}
