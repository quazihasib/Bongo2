package com.movies.singleMovie;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.movies.bongobd.ImageLoader;
import com.movies.bongobd.R;
import com.movies.browseAll.ListViewAdapter;
import com.movies.browseAll.Movies;
import com.movies.categoryPage.CategoryLanding;
import com.movies.movieSummary.MovieSummary;
import com.movies.startingPage.StartingPage;
import com.tab.AddMenu;
import com.tab.Header;
import com.tab.ShareData;

public class SingleMoviePage extends Activity
{
	public static String movieShortSummary;
	public static String movieCategory;
	public static String moviePostedOn;
	public static String movieAvgRating;
	public static String jsonData;
	public static float movieRating;
	public static float userRateStatus;

	public static String movieReleaseDate;
	public String movieDuration;
	public static String movieGenre;
	public String movieContentDetails;
	public static String movieUrl;
	public String rate_status;
	public String position;
	public String formattedYear, formattedMonth, formattedDay;
	public String movieName;
	public String movieDirector;
	public String movieViews;
	public String movieImage;
	public static String movieSummary;
	public String detailsId, roleName, roleId, artistProfileImage, contentId, artistId, artistName;
	public String id;

	public WebView webView;
	public ViewGroup.LayoutParams vc;

	public TextView  tvMovieName, tvDirector, tvViews,
			tvCategory, tvMovieCategory, tvPostedOn, tvShortSummary;
	public Button btnRate, btnFullScreen;

	public int height, width, count;
	public int finalHeight, finalWidth;
	public int counter;
	
	public RequestQueue requestQueue;
	public ProgressDialog pDialog;

	public LinearLayout directorLayout, infoLayout;
	public JSONArray cast;
	public JSONObject data, additionalData, userData;
	public ImageLoader imageLoader;
	
	public String[] relatedImgUrls;
	public String[] relatedTitles;
	public String[] relatedViews;
	public String[] relatedIds;
	public String[] relatedContentLength;
	public String[] relatedGenre;

	public static SingleMoviePage singleMovieInstance;
	public static String DEBUG_TAG = SingleMoviePage.class.getSimpleName();

	public static TextView tv;
	public static String userId;
	LinearLayout addTabs;
    int counts=0;
	TabResult tb;
	
	public Header h;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.single_movie_page);

		singleMovieInstance = this;
		h = AddMenu.HeaderFunction(singleMovieInstance);
		jsonData = "";
		AddMenu.pageNumber = 4;
		
		StartingPage.movieSummaryPageReturn = 1;
		imageLoader = new ImageLoader(this);
		addTabs = (LinearLayout)findViewById(R.id.tabs);
		
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
		
		tb = new TabResult(singleMovieInstance);
//		VideoCounter.checkVideoCounter(singleMovieInstance);

		tvDirector = (TextView) findViewById(R.id.tvDirectorName);
		tvViews = (TextView) findViewById(R.id.tvViews);
		tvMovieName = (TextView) findViewById(R.id.tvMovieName);
		tvMovieName.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
		tvMovieName.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				finish();
				Intent i = new Intent(getBaseContext(), MovieSummary.class);
				i.putExtra("movieName", movieName);
				i.putExtra("movieImage", movieImage);
				i.putExtra("movieSummary", movieSummary);
				i.putExtra("movieDuration", movieDuration);
				i.putExtra("movieReleaseDate", movieReleaseDate);
				i.putExtra("movieGenre", movieGenre);

				jsonData = data.toString();
				i.putExtra("json", data.toString());
				// i.putExtra("roleName", roleName);
				// i.putExtra("artistName", artistName);

				// go to Movie Summary page
				startActivity(i);
				overridePendingTransition(R.anim.animation1, R.anim.animation2);			
			}
		});

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;

		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		vc = webView.getLayoutParams();
		vc.height = height / 2;
		Log.d("Screen Height:", "vc.height:" + vc.height);
		webView.setLayoutParams(vc);
		webView.setHorizontalScrollBarEnabled(true);
		
		counts=0;
		webView.setOnTouchListener(new View.OnTouchListener() 
		{
			 @Override
			 public boolean onTouch(View v, MotionEvent event) 
			 {
				 // TODO Auto-generated method stub
		
				 if(event.getAction()==MotionEvent.ACTION_DOWN)
				 {
					 counts++;
					 if(counts==1)
					 {
						 finish();
						 startActivity(new Intent(getBaseContext(), PlayMovie.class));
					 }
				 }
				 else if(event.getAction()==MotionEvent.ACTION_UP)
				 {
					 counts=0;
				 }
				
//				 System.runFinalizersOnExit(true);
//			    	System.exit(0);
//				 android.os.Process.killProcess(android.os.Process.myPid());
				 return false;
			 }
		 });

		// id = ShareData.loadSavedPreferences(singleMovieInstance, "id");

		// addListenerOnRatingBar();
		// addFullScreenButton();
		
		Intent i = getIntent();
		// id = ShareData.getSavedString(singleMovieInstance,
		// "ListViewAdapterMovieId");
		id = i.getStringExtra("movieId");

		Log.d(DEBUG_TAG, "StartingPage.value:"+StartingPage.value);
		
		userId = ShareData.getSavedString(singleMovieInstance, "id");
//		userId="305";
//		ListViewAdapter.singleMovieId = "11";
			if(userId!=null && userId.trim().length()!=0)
			{
				makeJsonObjectRequest("http://dev.bongobd.com/api/content.php?id="+ ListViewAdapter.singleMovieId+"&user_id="+userId);
				Log.d(DEBUG_TAG, "browse user signed in:http://dev.bongobd.com/api/content.php?id="+ ListViewAdapter.singleMovieId+"&user_id="+userId);
			}
			else
			{
				makeJsonObjectRequest("http://dev.bongobd.com/api/content.php?id="+ ListViewAdapter.singleMovieId);
				Log.d(DEBUG_TAG, "browse:http://dev.bongobd.com/api/content.php?id="+ ListViewAdapter.singleMovieId);
			}
	
		
			
		infoLayout = (LinearLayout) findViewById(R.id.infoLayout);
		directorLayout = (LinearLayout) infoLayout
				.findViewById(R.id.directorLayout);
		tb.addTabs();
		tb.addListenerOnRatingBar();
		
		
//		addFullScreenButton();
	}

	 public void addFullScreenButton()
	 {
		 //btnFullScreen = (Button) findViewById(R.id.btnFullScreen1);
		 btnFullScreen.setText("Full Screen");
		 btnFullScreen.setOnClickListener(new View.OnClickListener()
		 {
	
			 @Override
			 public void onClick(View v)
			 {
				 // TODO Auto-generated method stub
				 finish();
				 startActivity(new Intent(getBaseContext(), PlayMovie.class));
	 		}
	 	});
	 }

	public void makeJsonObjectRequest(String urlJsonObj) 
	{
//		showpDialog();
		counter=0;
		relatedImgUrls=null;
		relatedTitles = null;
		relatedViews = null;
		relatedIds = null;
		relatedContentLength = null;
		relatedGenre = null;
		
		requestQueue = Volley.newRequestQueue(this);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response) 
					{
						// Log.d(DEBUG_TAG, response.toString());
						try 
						{
							data = response.getJSONObject("data");
							// Log.d(DEBUG_TAG, "data: "+data);
							if(userId.trim().length()!=0)
							{
								userData = data.getJSONObject("content_user_data");
								Log.d(DEBUG_TAG, "userData:"+userData);
								rate_status = userData.getString("rate_status");
								if(!rate_status.trim().equals("null"))
								{
									userRateStatus = Integer.parseInt(rate_status);
									Log.d(DEBUG_TAG, "userRateStatus:"+userRateStatus);
								}
							}
							else
							{
								Log.d(DEBUG_TAG, "No User loged in");
							}
							additionalData = response.getJSONObject("additionalData");
							// Log.d(DEBUG_TAG, " additionalData:"+additionalData );
							movieUrl = additionalData.getString("iframe2");
							// Log.d(DEBUG_TAG, " movieUrl:"+ movieUrl );

							movieName = data.getString("content_title");
							// Log.d(DEBUG_TAG, "movieName:"+movieName );
							
							movieGenre = data.getString("genre");
							Log.d(DEBUG_TAG, "movieGenre:"+movieGenre );
							
							movieImage = "http://dev.bongobd.com/wp-content/themes/bongobd/images/posterimage/thumb/"
									+ data.getString("content_thumb");
							// Log.d(DEBUG_TAG, "movieImage:"+movieImage );

							movieDirector = data.getString("by");
							// Log.d(DEBUG_TAG,
							// "movieDirector: "+movieDirector);
							// if there is no director then delete it
							if (movieDirector.length() == 0)
							{
								infoLayout.removeView(directorLayout);
							} 
							else
							{
								tvDirector.setText(movieDirector);
							}

							movieViews = data.getString("total_view");
							// Log.d(DEBUG_TAG, "movieViews: "+movieViews);

							movieCategory = data.getString("category_name");
							// Log.d(DEBUG_TAG,
							// "movieCategory: "+movieCategory);

							moviePostedOn = data.getString("entry_time");
							// Log.d(DEBUG_TAG,
							// "moviePostedOn: "+moviePostedOn);

							// moviePostedOn = DateFormatter(moviePostedOn);
							// Log.d(DEBUG_TAG,
							// "DateFormatter: "+moviePostedOn);

							movieShortSummary = data.getString("content_short_summary");
							// Log.d(DEBUG_TAG, "movieShortSummary: "+movieShortSummary);

							movieSummary = data.getString("content_summary");
							Log.d(DEBUG_TAG, "movieSummary: "+movieSummary);

							movieReleaseDate = data.getString("release_date");
//							movieReleaseDate = ShareData.dateFix(movieReleaseDate);
							Log.d(DEBUG_TAG, "movieReleaseDate11: "+movieReleaseDate);
							// movieReleaseDate =
							// DateFormatter(movieReleaseDate);
							// Log.d(DEBUG_TAG,
							// "movieReleaseDate: "+movieReleaseDate);

							movieAvgRating = data.getString("avg_rating");
							//Log.d(DEBUG_TAG, "movieAvgRating2222:"+movieAvgRating.length());
							Log.d(DEBUG_TAG, "movieAvgRating string value:"+movieAvgRating);
							
							if(movieAvgRating.equals("null"))
							{
								Log.d(DEBUG_TAG, "movieAvgRating null:");
								movieRating=0;
							}
							else if(!movieAvgRating.equals("null"))
							{
								Log.d(DEBUG_TAG, " not null movieAvgRating:");
								Log.d(DEBUG_TAG, "movieAvgRating:"+movieAvgRating);
								movieRating = Float.parseFloat(movieAvgRating);
								Log.d(DEBUG_TAG, "movieRating:"+movieRating);
							}
							
							if (data.has("content_details")) 
							{
								movieContentDetails = data.getString("content_details");
								// Log.d(DEBUG_TAG,
								// "movieContentDetails: "+movieContentDetails);

								// Parse Movie Casts
								ArrayList<String> allNames = new ArrayList<String>();
								cast = data.getJSONArray("content_details");
								for (int i = 0; i < cast.length(); i++) 
								{
									JSONObject actor = cast.getJSONObject(i);
									detailsId = actor.getString("details_id");
									contentId = actor.getString("content_id");
									artistId = actor.getString("artist_id");
									artistName = actor.getString("artist_name");
									artistProfileImage = actor.getString("artist_profile_image");
									roleName = actor.getString("role_name");
									roleId = actor.getString("role_id");
								}
							}
							
							JSONObject js = data.getJSONObject("related_contents");
							//Log.d(DEBUG_TAG, "img:"+js);
							
							relatedImgUrls = new String[js.length()+3];
							relatedTitles = new String[js.length()+3];
							relatedViews = new String[js.length()+3];
							relatedIds = new String[js.length()+3];
							relatedContentLength = new String[js.length()+3];
							relatedGenre = new String[js.length()+3];
							
							Iterator<String> iter = js.keys();
							while(iter.hasNext()) 
							{
							    counter++;
							    //Log.d(DEBUG_TAG, "number of items: "+counter); 
							    String key = iter.next();
							    try 
							    {
							        Object value = js.get(key);
							        //Log.d(DEBUG_TAG, "value:"+value ); 
							            
							        JSONObject eachObject = js.getJSONObject(""+ key);
							            
							        String id = eachObject.getString("id").trim();
							        relatedIds[counter] = id;
							        //Log.d(DEBUG_TAG, "id: "+id); 
							        
							        String content_length = eachObject.getString("content_length"); 
							        content_length = ShareData.changeFormat(content_length);
							        relatedContentLength[counter] = content_length;
							            
									String content_title = eachObject.getString("content_title");
									relatedTitles[counter] = content_title;
									//Log.d(DEBUG_TAG, "content_title: "+content_title);
									
									String total_view = eachObject.getString("total_view");
									relatedViews[counter] = total_view;
									//Log.d(DEBUG_TAG, "entry_time: "+entry_time); 
									
									String genre = eachObject.getString("genre");
									relatedGenre[counter]= genre;
									Log.d(DEBUG_TAG, "relatedGenre:"+genre);
									
									String content_thumb = eachObject.getString("content_thumb");
									content_thumb= "http://dev.bongobd.com/wp-content/themes/bongobd/" +
											"images/posterimage/thumb/"+content_thumb;
									relatedImgUrls[counter] = content_thumb;
									//Log.d(DEBUG_TAG, "img:"+relatedImgUrls[counter] +" "+counter);
							    } 
							    catch(JSONException e)
							    {
							         // Something went wrong!
							    }
							}
						}
						catch (JSONException e)
						{
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
									"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
						hidepDialog();
					}
				}, new Response.ErrorListener() 
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						VolleyLog.d(DEBUG_TAG, "Error: " + error.getMessage());
						Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						hidepDialog();
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}
	
	public void hidepDialog() 
	{
		tvViews.setText(movieViews);
		tvMovieName.setText(movieName);
		webView.loadUrl(movieUrl);
		
		tb.addListenerOnRatingBar();
		addRelatedLayout();
	}
	
//	public void onBackPressed() 
//	{
//		//super.onBackPressed();
		// System.runFinalizersOnExit(true);
		// System.exit(0);
		// android.os.Process.killProcess(android.os.Process.myPid());

		// webView.loadUrl("about:blank");
		// webView.stopLoading();
		// webView.setWebChromeClient(null);
		// webView.setWebViewClient(null);
		// webView.destroy();
		// webView = null;
//	}

	public void addRelatedLayout()
	{
		LinearLayout relatedLayouts = (LinearLayout)findViewById(R.id.relatedLayout);
		
		TextView tv1 = new TextView(this);
		LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tv1.setTextColor(Color.RED);
		tv1.setText("Related Content");
		tv1Params.setMargins(10, 10, 0, 10);
		tv1.setTextSize(ShareData.ConvertFromDp(this, 35));
		relatedLayouts.addView(tv1, tv1Params);
		
		//String im = "http://dev.bongobd.com/wp-content/themes/bongobd/images/posterimage/cover/size-250/Chorabali.jpg";
		int i;
		for(i=1; i<=counter; i++)
		{
			LinearLayout mainLayout1 = new LinearLayout(this);
			mainLayout1.setBackgroundColor(Color.WHITE);
			LinearLayout.LayoutParams mainLayout1params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			mainLayout1.setOrientation(LinearLayout.VERTICAL);
			relatedLayouts.addView(mainLayout1, mainLayout1params);
			
			RelativeLayout rl = new RelativeLayout(this);
			RelativeLayout.LayoutParams rlParams= new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, (height/3));
			rl.setLayoutParams(rlParams);
			
			LinearLayout extraLayout = new LinearLayout(this);
			extraLayout.setBackgroundColor(Color.WHITE);
			LinearLayout.LayoutParams extraLayoutparams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ShareData.getScreenWidth(singleMovieInstance)/12);
			extraLayout.setOrientation(LinearLayout.VERTICAL);
			
			final ImageView im1 = new ImageView(this);
			im1.setTag(i);
			im1.setId(1);
			im1.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View arg0) 
				{
					// TODO Auto-generated method stub
					Log.d(DEBUG_TAG, "ids:"+relatedIds[(Integer) im1.getTag()]);
				}
			});
			im1.setScaleType(ScaleType.FIT_XY);
			try 
			{
				imageLoader.DisplayImage(relatedImgUrls[i], im1);
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LinearLayout.LayoutParams im1Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, (height/3));
			rl.addView(im1, im1Params);
			
			
			
			
			TextView tv5 = new TextView(this);
			RelativeLayout.LayoutParams tv5Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tv5Params.addRule(RelativeLayout.ALIGN_BOTTOM,1);
			tv5Params.addRule(RelativeLayout.ALIGN_RIGHT,1);
			tv5Params.setMargins(0, 0, 10, 10);
			tv5.setBackgroundColor(Color.BLACK);
			tv5.setTextColor(Color.WHITE);
			try 
			{
				tv5.setText(""+relatedContentLength[i]);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//tv5Params.setMargins(10, 10, 0, 10);
			tv5.setTextSize(ShareData.ConvertFromDp(this, 28));
			tv5.setLayoutParams(tv5Params);
			rl.addView(tv5);
			mainLayout1.addView(extraLayout, extraLayoutparams);
			mainLayout1.addView(rl);

			
			TextView tv2 = new TextView(this);
			LinearLayout.LayoutParams tv2Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tv2.setTextColor(Color.BLACK);
			try 
			{
				tv2.setText(""+relatedTitles[i]);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tv2Params.setMargins(10, 10, 0, 10);
			tv2.setTextSize(ShareData.ConvertFromDp(this, 33));
			mainLayout1.addView(tv2, tv2Params);
			
			
			LinearLayout mainLayout2 = new LinearLayout(this);
			mainLayout2.setBackgroundColor(Color.WHITE);
			LinearLayout.LayoutParams mainLayout2params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			mainLayout2params.setMargins(0, 0, 0, 10);
			mainLayout2.setOrientation(LinearLayout.HORIZONTAL);
			mainLayout1.addView(mainLayout2, mainLayout2params);
			
			
			TextView tv3 = new TextView(this);
			LinearLayout.LayoutParams tv3Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tv3.setTextColor(Color.GRAY);
			tv3.setText("By "+relatedGenre[i]+" | ");
			tv3.setTextSize(ShareData.ConvertFromDp(this, 28));
			tv3Params.setMargins(10, 0, 0, 10);
			mainLayout2.addView(tv3, tv3Params);
			
			
			TextView tv4 = new TextView(this);
			LinearLayout.LayoutParams tv4Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tv4.setTextColor(Color.GRAY);
			try 
			{
				tv4.setText(relatedViews[i]+" views");
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tv4.setTextSize(ShareData.ConvertFromDp(this, 28));
			tv4Params.setMargins(10, 0, 0, 10);
			mainLayout2.addView(tv4, tv4Params);
			
			
			View v1 = new View(this);
			v1.setBackgroundColor(Color.parseColor("#E6E6E6"));
			LinearLayout.LayoutParams v1Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 10);
			relatedLayouts.addView(v1, v1Params);
		}
		
		
		Button btnSeeMore = new Button(this);
		btnSeeMore.setText("See More");
		btnSeeMore.setTextColor(Color.WHITE);
		btnSeeMore.setBackgroundColor(Color.RED);
		btnSeeMore.setTextSize(ShareData.ConvertFromDp(this, 28));
		LinearLayout.LayoutParams btnSeeMoreParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		relatedLayouts.addView(btnSeeMore, btnSeeMoreParams);
		btnSeeMore.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				Log.d(DEBUG_TAG, "cc");
			}
		});
	}
	
	public void onStop()
	{
		super.onStop();
		overridePendingTransition( R.anim.animation1, R.anim.animation2 );
		Log.d(DEBUG_TAG, "App Stopped");
		TabResult.counter=0;
		movieRating=0;
		userRateStatus=0;
		if (requestQueue != null)
		{
			requestQueue.cancelAll(new RequestQueue.RequestFilter()
			{

				@Override
				public boolean apply(Request<?> request) 
				{
					// TODO Auto-generated method stub
					return true;
				}
			});
		}
		
		//webView.loadUrl("about:blank");
		webView.stopLoading();
//		webView.setWebChromeClient(null);
//		webView.setWebViewClient(null);
		webView.destroy();
		webView = null;
	}

	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		Log.d(DEBUG_TAG, "App Destroyed");
	}
	
	public void onBackPressed() 
	{
		super.onBackPressed();
		// System.runFinalizersOnExit(true);
		// System.exit(0);
		// android.os.Process.killProcess(android.os.Process.myPid());
		SingleMoviePage.this.finish();
		if(StartingPage.singleMoviePageReturn == 1)
		{
			 startActivity(new Intent(getBaseContext(), CategoryLanding.class));
			 overridePendingTransition( R.anim.animation1, R.anim.animation2 );
		} 
		else if(StartingPage.singleMoviePageReturn == 2)
		{
		  	 startActivity(new Intent(getBaseContext(), Movies.class));
		  	 overridePendingTransition( R.anim.animation1, R.anim.animation2 );
		} 
	}

}