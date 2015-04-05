package com.movies.movieSummary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movies.actorProfile.ActorProfile;
import com.movies.bongobd.ImageLoader;
import com.movies.bongobd.LeadingMarginSpan2;
import com.movies.bongobd.R;
import com.movies.categoryPage.CategoryLanding;
import com.movies.singleMovie.SingleMoviePage;
import com.movies.startingPage.StartingPage;
import com.tab.AddMenu;
import com.tab.Header;
import com.tab.ShareData;

public class MovieSummary extends Activity implements ImageGetter 
{
	public String movieSummary, movieReleaseDate, movieDuration, movieGenre, movieName, movieImage;
	public String detailsId, roleName , roleId, artistProfileImage, contentId, artistId, artistName;

	public static String singleArtistId, singleArtistName, singleArtistRole;
	
	public ImageView ivMovieImage;
	public ImageLoader imageLoader1;
	
	public TextView tvMovieSummary, tvDuration, tvGenre, tvReleaseDate, tvMovieNameSum;
	public TextView show, hide;
	public TextView tvRoleName, tvArtsitName, tvCastTitle;
	public TextView tvDurationLabelSum, tvGenreLabelSum, tvReleaseDateLabelSum;
	
	public LinearLayout summaryLayout, castlayout;
	
	public int finalHeight, finalWidth;
	public int screenheight, screenWidth;

	public TextView [] tvArtistName;
	public TextView [] tvArtistRole;
	public String [] artistIds;
	
	public static String DEBUG_TAG = MovieSummary.class.getSimpleName();
	public static MovieSummary movieSummaryInstance;
	
	public Header h;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.movie_summary_page);

		movieSummaryInstance = this;
		h = AddMenu.HeaderFunction(movieSummaryInstance);
		AddMenu.pageNumber = 7;
		
		StartingPage.actorPageReturn = 1;
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenheight = displaymetrics.heightPixels;
		screenWidth = displaymetrics.widthPixels;
		
		if(CategoryLanding.catagoryName.trim().equals("movies"))
		{
			h.tv1.setBackgroundColor(Color.parseColor("#B40404"));
			h.tv2.setBackgroundColor(Color.parseColor("#FF0000"));
			h.tv3.setBackgroundColor(Color.parseColor("#FF0000"));
			h.tv4.setBackgroundColor(Color.parseColor("#FF0000"));
		}
		else if(CategoryLanding.catagoryName.trim().equals("tv"))
		{
			h.tv1.setBackgroundColor(Color.parseColor("#FF0000"));
			h.tv2.setBackgroundColor(Color.parseColor("#B40404"));
			h.tv3.setBackgroundColor(Color.parseColor("#FF0000"));
			h.tv4.setBackgroundColor(Color.parseColor("#FF0000"));
		}
		else if(CategoryLanding.catagoryName.trim().equals("music"))
		{
			h.tv1.setBackgroundColor(Color.parseColor("#FF0000"));
			h.tv2.setBackgroundColor(Color.parseColor("#FF0000"));
			h.tv3.setBackgroundColor(Color.parseColor("#B40404"));
			h.tv4.setBackgroundColor(Color.parseColor("#FF0000"));
		}
		
		LinearLayout castLayoutsLeft = (LinearLayout)findViewById(R.id.castLayoutLeft);
		LinearLayout castLayoutsRight = (LinearLayout)findViewById(R.id.castLayoutRight);
		
		tvDurationLabelSum = (TextView)findViewById(R.id.tvDurationLabelSum);
		tvDurationLabelSum.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
		
		tvGenreLabelSum = (TextView)findViewById(R.id.tvGenreLabelSum);
		tvGenreLabelSum.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
		
		tvReleaseDateLabelSum = (TextView)findViewById(R.id.tvReleaseDateLabelSum);
		tvReleaseDateLabelSum.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
	
		// load image from url
		imageLoader1 = new ImageLoader(this);

		singleArtistId = null;
		singleArtistName = null;
		singleArtistRole = null;
		
		try
		{
			JSONObject obj = new JSONObject(SingleMoviePage.jsonData);
			Log.d(DEBUG_TAG, "obj:" + obj);
			movieName = obj.getString("content_title");
			Log.d(DEBUG_TAG, "movieName:" + movieName);
			
			movieImage = "http://dev.bongobd.com/wp-content/themes/bongobd/images/posterimage/thumb/"+obj.getString("content_thumb");
//	        Log.d(DEBUG_TAG, "movieImage:"+movieImage );
            
	        movieSummary = obj.getString("content_summary");
			Log.d(DEBUG_TAG, "movieSummary: "+movieSummary);
			
			movieReleaseDate = obj.getString("release_date");
			Log.d(DEBUG_TAG, "movieReleaseDate: "+movieReleaseDate);
//			movieReleaseDate = SingleMoviePage.DateFormatter(movieReleaseDate);
//			Log.d(DEBUG_TAG, "movieReleaseDate: "+movieReleaseDate);
			
			movieDuration = obj.getString("content_length");
			Log.d(DEBUG_TAG, "movieDuration: "+movieDuration);
			
			movieGenre = obj.getString("genre");
			Log.d(DEBUG_TAG, "movieGenre: "+movieGenre);
			//Parse Movie Casts
			ArrayList<String> allNames = new ArrayList<String>();
			JSONArray cast  = obj.getJSONArray("content_details");
			
			tvArtistName = new TextView[cast.length()];
			tvArtistRole = new TextView[cast.length()];
			artistIds = new String[cast.length()];
			
			//set cast title
			if(cast.length()!=0)
			{
				Log.d(DEBUG_TAG, " Cast");
				TextView tvRoleTitle=new TextView(MovieSummary.this);
				tvRoleTitle.setText("Cast");
				tvRoleTitle.setTextColor(Color.RED);
				tvRoleTitle.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
				tvRoleTitle.setTypeface(Typeface.DEFAULT_BOLD);
				tvRoleTitle.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				castLayoutsLeft.addView(tvRoleTitle);
				
				TextView tvCastNameTitle=new TextView(MovieSummary.this);
				tvCastNameTitle.setText("Name");
				tvCastNameTitle.setTextColor(Color.RED);
				tvCastNameTitle.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
				tvCastNameTitle.setTypeface(Typeface.DEFAULT_BOLD);
				tvCastNameTitle.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				castLayoutsRight.addView(tvCastNameTitle);
			}
			else
			{
				Log.d(DEBUG_TAG, "No Cast");
			}
			
			for(int s=0; s<cast.length(); s++) 
			{
			    JSONObject actor;
				try 
				{
					actor = cast.getJSONObject(s);
					detailsId = actor.getString("details_id");
					contentId = actor.getString("content_id");
					artistId = actor.getString("artist_id");
					artistName = actor.getString("artist_name");
					artistProfileImage = actor.getString("artist_profile_image");
					roleName = actor.getString("role_name");
					roleId = actor.getString("role_id");
					    
					Log.d(DEBUG_TAG, "detailsId: "+detailsId);
					Log.d(DEBUG_TAG, "contentId: "+contentId);
					Log.d(DEBUG_TAG, "artistId: "+artistId);
					Log.d(DEBUG_TAG, "artistName: "+artistName);
					Log.d(DEBUG_TAG, "artistProfileImage: "+artistProfileImage);
					Log.d(DEBUG_TAG, "roleName: "+roleName);
					Log.d(DEBUG_TAG, "roleId: "+roleId);
					
					
					//tvRoleName
					tvArtistRole[s]=new TextView(MovieSummary.this);
					tvArtistRole[s].setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
					tvArtistRole[s].setText(roleName);
					tvArtistRole[s].setId(s);
					tvArtistRole[s].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
					castLayoutsLeft.addView(tvArtistRole[s]);
				
					
					
					//ArtistIds
					artistIds[s] = artistId;
					
					//ArtistName
					tvArtistName[s]=new TextView(MovieSummary.this);
					tvArtistName[s].setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
					tvArtistName[s].setText(artistName);
					tvArtistName[s].setId(s);
					tvArtistName[s].setLayoutParams(new
						LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
					castLayoutsRight.addView(tvArtistName[s]);
					tvArtistName[s].setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
					tvArtistName[s].setOnClickListener(new OnClickListener()
					{
						public void onClick(View v) 
						{
							finish();
							Intent i = new Intent(getBaseContext(), ActorProfile.class);
							
							singleArtistName = ""+tvArtistName[v.getId()].getText();
							singleArtistId = ""+artistIds[v.getId()];
							singleArtistRole = ""+tvArtistRole[v.getId()].getText();
							
							i.putExtra("artistName", tvArtistName[v.getId()].getText());
							i.putExtra("artistId", artistIds[v.getId()]);
							i.putExtra("artistRole", tvArtistRole[v.getId()].getText());
							
							startActivity(i);
							overridePendingTransition( R.anim.animation1, R.anim.animation2 );
						}
					});
				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		tvMovieNameSum = (TextView) findViewById(R.id.tvMovieNameSum);
		tvMovieNameSum.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
		tvMovieNameSum.setText(movieName);

		tvMovieSummary = (TextView) findViewById(R.id.message_view);
		tvMovieSummary.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));

		ivMovieImage = (ImageView) findViewById(R.id.icon);
		imageLoader1.DisplayImage(movieImage, ivMovieImage);

		ivMovieImage.getLayoutParams().width = screenWidth / 2 - 10;
		ivMovieImage.getLayoutParams().height = screenWidth / 3;

		tvDuration = (TextView) findViewById(R.id.tvDurationSum);
		tvDuration.setTextColor(Color.GRAY);
		tvDuration.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
		tvDuration.setText(movieDuration);

		tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDateSum);
		tvReleaseDate.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
		if(movieReleaseDate.toString().trim().equals("0000-00-00"))
		{
			tvReleaseDateLabelSum.setText("");
			tvReleaseDate.setText("");
		}
		else
		{
			tvReleaseDate.setText(movieReleaseDate);
		}
		
		tvGenre = (TextView) findViewById(R.id.tvGenreSum);
		tvGenre.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
		tvGenre.setText(movieGenre);
		
		show = (TextView) findViewById(R.id.show);
		show.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
		show.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				// System.out.println("Show button");
				show.setVisibility(View.INVISIBLE);
				hide.setVisibility(View.VISIBLE);
				tvMovieSummary.setMaxLines(Integer.MAX_VALUE);

			}
		});
		hide = (TextView) findViewById(R.id.hide);
		hide.setTextSize(ShareData.ConvertFromDp(movieSummaryInstance,28));
		hide.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				System.out.println("Hide button");
				hide.setVisibility(View.INVISIBLE);
				show.setVisibility(View.VISIBLE);
				tvMovieSummary.setMaxLines(4);

			}
		});
		setSome();
		if(tvMovieSummary.getText().toString().trim().length()==0)
		{
			show.setVisibility(View.INVISIBLE);
			hide.setVisibility(View.INVISIBLE);
		}
//		else if(tvMovieSummary.getText().toString().trim().length()!=0)
//		{
//			show.setVisibility(View.VISIBLE);
//			hide.setVisibility(View.VISIBLE);
//		}
	}

//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) 
//	{
//		
//	}

	public void setSome()
	{
		int width = ivMovieImage.getLayoutParams().width;
		int height = ivMovieImage.getLayoutParams().height;

		Log.d(DEBUG_TAG, "width:" + width);
		Log.d(DEBUG_TAG, "height:" + height);

		int leftMargin = width + 10;

		// icon.setImageDrawable(getResources().getDrawable(R.drawable.download));
		float textLineHeight = tvMovieSummary.getPaint().getTextSize();
		int lines = (int) Math.round(height / textLineHeight);
		SpannableString ss = new SpannableString(movieSummary);
		ss.setSpan(new ForegroundColorSpan(Color.BLACK), 0, ss.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new LeadingMarginSpan2(lines, leftMargin), 0, ss.length(),
				0);
		tvMovieSummary.setText(ss);
		tvMovieSummary.setMaxLines(4);
	}
	@Override
	public Drawable getDrawable(String source) 
	{
		LevelListDrawable d = new LevelListDrawable();
		Drawable empty = getResources().getDrawable(R.drawable.ic);
		d.addLevel(0, 0, empty);
		d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
		new LoadImage().execute(source, d);

		return d;
	}

	class LoadImage extends AsyncTask<Object, Void, Bitmap>
	{

		private LevelListDrawable mDrawable;

		@Override
		protected Bitmap doInBackground(Object... params) 
		{
			String source = (String) params[0];
			mDrawable = (LevelListDrawable) params[1];
			Log.d("", "doInBackground " + source);
			try 
			{
				InputStream is = new URL(source).openStream();
				return BitmapFactory.decodeStream(is);
			}
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap)
		{
			Log.d("", "onPostExecute drawable " + mDrawable);
			Log.d("", "onPostExecute bitmap " + bitmap);
			if (bitmap != null)
			{
				BitmapDrawable d = new BitmapDrawable(bitmap);
				mDrawable.addLevel(1, 1, d);
				mDrawable.setBounds(0, 0, 20, 50);
				mDrawable.setLevel(1);
				// d.setGravity(Gravity.TOP);
				// i don't know yet a better way to refresh TextView
				// mTv.invalidate() doesn't work as expected
				CharSequence t = tvMovieSummary.getText();
				tvMovieSummary.setText(t);
			}
		}
	}
	
	public void onStop() 
	{
		super.onStop();
		Log.d(DEBUG_TAG, "App Stopped");
	}	
	
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		Log.d(DEBUG_TAG, "App Destroyed");
	}
	
	@Override
	public void onBackPressed()  
	{
		super.onBackPressed();

		//ListViewAdapter.singleMovieId = "";
		//SingleMoviePage.jsonData = null;
		
		MovieSummary.this.finish();
		if(StartingPage.movieSummaryPageReturn  == 1)
		{
		  	 startActivity(new Intent(getBaseContext(), SingleMoviePage.class));
		  	 overridePendingTransition( R.anim.animation1, R.anim.animation2 );
		} 
	    
//		System.runFinalizersOnExit(true);
//		System.exit(0);
//		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
