package com.movies.categoryPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import com.movies.login.LoginActivity;
import com.movies.startingPage.StartingPage;
import com.tab.AddMenu;
import com.tab.Header;
import com.tab.ShareData;
import com.tab.SwipeGestureDetector;


public class CategoryLanding extends Activity 
{
	public ProgressDialog mProgressDialog;
	public static String catagoryName;
	
	public static int categoryID;
	public static int widthScreen;
	public static int loop, count;
	public static int counter1=0;
	public static int errorCheck=0;
	public static int carasolLoopCounter=0;
	public static int carasolCounter = 0;
	public static int[] maxScroll;
	public static boolean touchEnable;
	
	public static String[] images, ids, titles, catagoryNames;
	public static String[][] movieIds;
	public static String val;
	
	public static ImageView[] ivL, ivR;
	
	public static HorizontalScrollView[] scrollView;
	public static LinearLayout mainLayout, mLinearLayout, mainSlider;
	public static RelativeLayout rLayout;
	public static RequestQueue requestQueue;
	public static ProgressDialog pDialog;
	public static ImageLoader imageLoader;
	
	public static boolean flag;
	public static boolean startSliders;
	
	public static ViewFlipper viewFlipper;
	public final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());

	
	public static String DEBUG_TAG = CategoryLanding.class.getSimpleName();
	public static CategoryLanding categoryInstance;
	
	public Header h;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.catagory_landing);
		
		categoryInstance = this;
		h = AddMenu.HeaderFunction(categoryInstance);
		AddMenu.pageNumber = 1;
		
		touchEnable = false;
	
		StartingPage.singleMoviePageReturn = 1;
		StartingPage.browseAllPageReturn = 1;
		
		mainLayout = (LinearLayout)findViewById(R.id.mainLayoutPeople);
		mainSlider = (LinearLayout)findViewById(R.id.categoryMainSlider);
		viewFlipper = (ViewFlipper)findViewById(R.id.viewflipper);
		viewFlipper.getParent().requestDisallowInterceptTouchEvent(true);
		mainSlider.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(final View view, final MotionEvent event)
			{
				if(event.getAction()==MotionEvent.ACTION_DOWN)
				{
					//Log.d(DEBUG_TAG, "downnn");
				}
				else if(event.getAction()==MotionEvent.ACTION_UP)
				{
					//Log.d(DEBUG_TAG, "uppp");
				}
				
				detector.onTouchEvent(event);
				return true;
			}
		});
	
		startSliders = false;
		StartingPage.browseAll = 1;
		Log.d(DEBUG_TAG, "CategoryLanding Entered");
		
		CategoryAddLayout.imgCounter=0;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		Log.d(DEBUG_TAG, "On onResume");
		if(ShareData.isNetworkAvailable(categoryInstance)==true)
		{
			mLinearLayout = (LinearLayout) findViewById(R.id.categoryViewFlipperLayer);
			LayoutParams params = mLinearLayout.getLayoutParams();
			params.height = StartingPage.bannerHeight;
			
			widthScreen = ShareData.getScreenWidth(categoryInstance);
			imageLoader = new ImageLoader(this);
			
			
			if(catagoryName.trim().equals("movies"))
			{
				h.tv1.setBackgroundColor(Color.parseColor("#B40404"));
    			h.tv2.setBackgroundColor(Color.parseColor("#FF0000"));
    			h.tv3.setBackgroundColor(Color.parseColor("#FF0000"));
    			h.tv4.setBackgroundColor(Color.parseColor("#FF0000"));
			}
			else if(catagoryName.trim().equals("tv"))
			{
				h.tv1.setBackgroundColor(Color.parseColor("#FF0000"));
    			h.tv2.setBackgroundColor(Color.parseColor("#B40404"));
    			h.tv3.setBackgroundColor(Color.parseColor("#FF0000"));
    			h.tv4.setBackgroundColor(Color.parseColor("#FF0000"));
			}
			else if(catagoryName.trim().equals("music"))
			{
				h.tv1.setBackgroundColor(Color.parseColor("#FF0000"));
    			h.tv2.setBackgroundColor(Color.parseColor("#FF0000"));
    			h.tv3.setBackgroundColor(Color.parseColor("#B40404"));
    			h.tv4.setBackgroundColor(Color.parseColor("#FF0000"));
			}
			
			makeJsonObjectRequestForBanners("http://dev.bongobd.com/api/menu_categories.php?menu="+catagoryName);
			Log.d(DEBUG_TAG, "main url1:"+"http://dev.bongobd.com/api/menu_categories.php?menu="+catagoryName);
		}
		else
		{
			Toast.makeText(getBaseContext(), "No Internet", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void makeJsonObjectRequestForBanners(String urlJsonObj)
	{
		images=null;
		titles = null;
		catagoryNames = null;
		ids = null;
		movieIds = null;
		if(CategoryCarasol.requestQueue!=null)
		{
			CategoryCarasol.requestQueue.cancelAll(new RequestQueue.RequestFilter()
			{
				@Override
				public boolean apply(Request<?> request) 
				{
					// TODO Auto-generated method stub
					return true;
				}
		    });
		}
		//CategoryCarasol.requestQueue = null;
		
		errorCheck = 0;
		count =0;
		counter1=0;
		
		flag=false;
		mainLayout.removeAllViews();
		carasolLoopCounter = 0;
		carasolCounter = 0;
		
		
		requestQueue = Volley.newRequestQueue(this);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response) 
					{
						//Log.d(DEBUG_TAG, response.toString());
						
						//if matches
//						if(ShareData.checkJsonObject(categoryInstance, response, "cat")==true)
//						{
//							String loadJson = ShareData.getSavedJsonObject(categoryInstance, "cat");
//							try 
//							{
//								response = new JSONObject(loadJson.trim().toString());
//							    Log.d(DEBUG_TAG, "loaded:"+response.toString());
//							} 
//							catch (Throwable t) 
//							{
//							    Log.e(DEBUG_TAG, "Could not parse malformed JSON");
//							}
//						}
//						//if doesn't matches
//						else
//						{
//							//do nothing
//							Log.d(DEBUG_TAG, "do nothing.");
//						}
						
						try 
						{
							JSONArray json = response.getJSONArray("banners");
							images = new String[json.length()+1];
							titles = new String[json.length()+1];
							movieIds = new String[100][100];
							for (int i=0; i<json.length(); i++)
							{
							    JSONObject js = json.getJSONObject(i);
							    String image_url = js.getString("full_image_url");
							    images[i] = image_url;
							    //Log.d(DEBUG_TAG, "banner images:" + images[i]);
							    
							    String bannerTitles = js.getString("title");
							    titles[i] = bannerTitles;
							    //Log.d(DEBUG_TAG, "titles[i]:" + titles[i]);
							}
							
							JSONArray json2 = response.getJSONArray("categories");
							ids = new String[json2.length()+1]; 
							catagoryNames = new String[json2.length()+1];
							
							for (int i=0; i<json2.length(); i++)
							{
							    JSONObject js2 = json2.getJSONObject(i);
							    ids[i] = js2.getString("id");
							    //Log.d(DEBUG_TAG, "ids:" + ids[i]);
							    
							    catagoryNames[i] = js2.getString("category_name");
							    //Log.d(DEBUG_TAG, "category_name:" + catagoryNames[i]);
							}

						} catch (JSONException e) 
						{
							errorCheck = 1;
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
							"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
							//dismiss progress dialog
							//pDialog.dismiss();
						}
						hidepDialog();
					}
				}, 
				new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error) 
					{
						errorCheck = 1;
						VolleyLog.d(DEBUG_TAG, "Error: " + error.getMessage());
						hidepDialog();
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}
	
	@SuppressLint("NewApi")
	public void hidepDialog()
	{
		if(errorCheck==0)
		{
			for (int i = 0; i < images.length-1; i++) 
			{
				try 
				{
					LinearLayout bannerLayout = new LinearLayout(this);
					LinearLayout.LayoutParams bannerLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, StartingPage.bannerHeight);
					
					ImageView imageView = new ImageView(this);
					imageView.setId(12);
					LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(widthScreen, StartingPage.bannerHeight);
					imageView.setScaleType(ScaleType.FIT_XY);
					imageLoader.DisplayImage(images[i], imageView);
					
					TextView tvTitles = new TextView(this);
					RelativeLayout.LayoutParams tvTitlesParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
					tvTitlesParams.addRule(RelativeLayout.ALIGN_BOTTOM,12);
					tvTitlesParams.setMargins(20, 0, 0, StartingPage.bannerHeight/7);
					tvTitles.setTextSize(ShareData.ConvertFromDp(categoryInstance, 28));
					tvTitles.setBackgroundColor(Color.BLACK);
					tvTitles.setTextColor(Color.WHITE);
					tvTitles.setPadding(5, 0, 5, 0);
					Log.d(DEBUG_TAG, "titles:"+titles[i]);
					if(titles[i]!=null)
					{
						tvTitles.setText(""+titles[i]);
					}
					
					rLayout = new RelativeLayout(this);
					RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT); 
					rLayout.addView(imageView, imageViewParams);
					rLayout.addView(tvTitles, tvTitlesParams);
					
					bannerLayout.addView(rLayout, rLayoutParams);
					viewFlipper.addView(bannerLayout, bannerLayoutParams);
					viewFlipper.setDisplayedChild(images.length-2);
					//Log.d(DEBUG_TAG, "shared res:"+ShareData.getSavedJsonObject(categoryInstance, "cat"));
			
//					viewFlipper.setAutoStart(true);
//			        viewFlipper.setFlipInterval(2000);
//			        if(!viewFlipper.isFlipping())
//			        {
//			        	viewFlipper.startFlipping();
//			        }
//			    	viewFlipper.setInAnimation(categoryInstance, R.anim.slide_in_from_right);
//					viewFlipper.setOutAnimation(categoryInstance, R.anim.slide_out_to_left);
					viewFlipper.getInAnimation().setAnimationListener(new Animation.AnimationListener() 
	    		    {
	    		        public void onAnimationStart(Animation animation) 
	    		        {
	    		          	Log.d(DEBUG_TAG, "animation start");
	    		          	touchEnable = true;
	    		        }
	    		        public void onAnimationRepeat(Animation animation) 
	    		        {
	    		           	Log.d(DEBUG_TAG, "animation repeat");
	    		        }
	    		        public void onAnimationEnd(Animation animation) 
	    		        {
	    		          	Log.d(DEBUG_TAG, "animation end");
	    		          	touchEnable = false;
	    		        }
	    		    });
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				 
			loop = ids.length;
			count = 0;
			
			AddMenu.clickable=true;
			//CategorySlider.startSlider();
			//CategorySlider.listViewOnClickListener();
			if(CategoryCarasol.requestQueue!=null)
			{
				CategoryCarasol.requestQueue.cancelAll(new RequestQueue.RequestFilter()
				{
					@Override
					public boolean apply(Request<?> request) 
					{
						// TODO Auto-generated method stub
						return true;
					}
			    });
			}
			//requestQueue = null;
			CategoryCarasol.requestQueue = null;
			maxScroll = new int[CategoryLanding.loop];
			scrollView = new HorizontalScrollView[loop];
			ivL = new ImageView[loop];
			ivR = new ImageView[loop];
			
			CategoryCarasol.makeJsonObjectRequestForSlider("http://dev.bongobd.com/api/category.php?catID="+ids[count], loop);
//			new Timer().schedule(new TimerTask() {          
//			    @Override
//			    public void run() {
//			        // this code will be executed after 2 seconds   
//			    	AddMenu.clickable=true;
//			    }
//			}, 4000);
		}
	}
	
	@Override
	public void onStop() 
	{
		super.onStop();
		
		Log.d(DEBUG_TAG, "App Stopped");
		
		//catagoryName = null;
		
		//catagoryID = 0;
		//widthScreen = 0;
		loop = 0;
		count = 0;
		counter1=0;
		errorCheck=0;
		carasolLoopCounter=0;
		AddMenu.clickable=true;
		
		images = null;
		titles = null;
//		catagoryNames = null;
		ids = null;
//		catagoryNames = null;
		
//		public static HorizontalScrollView listview1;
//		public static LinearLayout mainLayout, mLinearLayout;
//		pDialog = null;
//		imageLoader = null;
		
		CategoryCarasol.images=null;
		CategoryCarasol.movieLength=null;
		CategoryCarasol.imageCounter=0;
		
		
		flag = true;
		startSliders = false;
		
		//categoryInstance  = null;
		
		//CatagoryCarasol
//		if(requestQueue!=null)
//		{
//			requestQueue.cancelAll(new RequestQueue.RequestFilter()
//			{
//				@Override
//				public boolean apply(Request<?> request) 
//				{
//					// TODO Auto-generated method stub
//					return true;
//				}
//		    });
//		}
//		if(CategoryCarasol.requestQueue!=null)
//		{
//			CategoryCarasol.requestQueue.cancelAll(new RequestQueue.RequestFilter()
//			{
//				@Override
//				public boolean apply(Request<?> request) 
//				{
//					// TODO Auto-generated method stub
//					return true;
//				}
//		    });
//		}
//		//requestQueue = null;
//		CategoryCarasol.requestQueue = null;
		CategoryCarasol.images = null;
		CategoryCarasol.directors = null;
		CategoryCarasol.titles = null;
		CategoryCarasol.movieLength = null;
		CategoryCarasol.imageCounter=0;
		
		StartingPage.loader = 0;
		
		CategorySlider.handler.removeCallbacks(CategorySlider.runnable);
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
		CategoryLanding.this.finish();
		startActivity(new Intent(getBaseContext(), StartingPage.class));
		overridePendingTransition(R.anim.animation1, R.anim.animation2);
	}
}