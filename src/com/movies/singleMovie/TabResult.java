package com.movies.singleMovie;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hintdesk.core.activities.AlertMessageBox;
import com.hintdesk.core.util.OSUtil;
import com.hintdesk.core.util.StringUtil;
import com.movies.bongobd.R;
import com.movies.browseAll.ListViewAdapter;
import com.movies.startingPage.StartingPage;
import com.movies.twitter.ConstantValues;
import com.movies.twitter.OAuthActivity;
import com.movies.twitter.TwitterActivity;
import com.movies.twitter.TwitterUtil;
import com.tab.ShareData;

public class TabResult  
{
	String txt, txt1; 
	private boolean isUseStoredTokenKey = false;
    private boolean isUseWebViewForAuthentication = false;
    public Activity tabResultInstance;
    public RequestQueue requestQueue;
    public static String DEBUG_TAG = TabResult.class.getSimpleName();
    public static int counter;
    
    LinearLayout tabContent;
    
    public TabResult(Activity instance)
    {
    	this.tabResultInstance=instance;
    	initTwitter();
    	counter=0;
    }
    
    public void addTabs()
	{
	    tabContent = (LinearLayout)tabResultInstance.findViewById(R.id.tabContent);
	    
	    final TextView tv1 = (TextView)tabResultInstance.findViewById(R.id.tv11);
	    final TextView tv2 = (TextView)tabResultInstance.findViewById(R.id.tv22);
	    final TextView tv3 = (TextView)tabResultInstance.findViewById(R.id.tv33);
	    tv1.setTextColor(Color.RED);
	    
	    tv1.setOnClickListener(new View.OnClickListener()
	    {
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				tv1.setTextColor(Color.RED);
				tv2.setTextColor(Color.BLACK);
				tv3.setTextColor(Color.BLACK);
				Log.d(DEBUG_TAG, "1");
				
				addListenerOnRatingBar();
				
			}
		});
	    
	    tv2.setOnClickListener(new View.OnClickListener() 
	    {
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				tv1.setTextColor(Color.BLACK);
				tv2.setTextColor(Color.RED);
				tv3.setTextColor(Color.BLACK);
				Log.d(DEBUG_TAG, "2");
				
				addShortSummary();
			}
		});

	    tv3.setOnClickListener(new View.OnClickListener() 
	    {
	    	@Override
	    	public void onClick(View arg0) 
	    	{
	    		// TODO Auto-generated method stub
	    		tv1.setTextColor(Color.BLACK);
	    		tv2.setTextColor(Color.BLACK);
	    		tv3.setTextColor(Color.RED);
	    		Log.d(DEBUG_TAG, "3");
	    		
	    		addShare();
	    	}
	    });
	    
	}
    
    public void addShare()
	{
		if(tabContent.getChildCount()!=0)
		{
			tabContent.removeAllViews();
		}
		
		LinearLayout share = new LinearLayout(tabResultInstance);
		LinearLayout.LayoutParams shareParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		ImageView ivFB = new ImageView(tabResultInstance);
		LinearLayout.LayoutParams ivFBParams = new LinearLayout.LayoutParams(100, 100);
		ivFB.setImageResource(R.drawable.facebook);
		ivFB.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				Log.d(DEBUG_TAG, "Fb");
				postToWall();
				
			}
		});
		share.addView(ivFB, ivFBParams);
		
		ImageView ivTwitter = new ImageView(tabResultInstance);
		LinearLayout.LayoutParams ivTwitterParams = new LinearLayout.LayoutParams(100, 100);
		ivTwitterParams.setMargins(20, 0, 0, 0);
		ivTwitter.setImageResource(R.drawable.twitter);
		ivTwitter.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				Log.d(DEBUG_TAG, "Tweet");
				twitterDialog();
			}
		});
		share.addView(ivTwitter, ivTwitterParams);
		shareParams.setMargins(0, 0, 0, ShareData.getScreenWidth(tabResultInstance)/6);
		tabContent.addView(share, shareParams);
	}
	
	public void addShortSummary()
	{
		if(tabContent.getChildCount()!=0)
		{
			tabContent.removeAllViews();
		}
		TextView tvShortSummary = new TextView(tabResultInstance);
		LinearLayout.LayoutParams tvShortSummaryParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tvShortSummary.setTextColor(Color.GRAY);
		Log.d(DEBUG_TAG, "movieSummary:"+SingleMoviePage.movieSummary);
		tvShortSummary.setText(""+ SingleMoviePage.movieSummary);
		tvShortSummaryParams.setMargins(10, 10, 0, 10);
		tvShortSummary.setTextSize(ShareData.ConvertFromDp(tabResultInstance, 28));
		tabContent.addView(tvShortSummary, tvShortSummaryParams);
		
	}
	
	public void addListenerOnRatingBar() 
	{
		if(tabContent.getChildCount()!=0)
		{
			tabContent.removeAllViews();
		}
		
		
		TextView tvCategory = new TextView(tabResultInstance);
		LinearLayout.LayoutParams tvCategoryParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tvCategory.setTextColor(Color.GRAY);
		tvCategory.setText("Category: "+ SingleMoviePage.movieCategory);
		tvCategory.setTextSize(ShareData.ConvertFromDp(tabResultInstance, 28));
		tabContent.addView(tvCategory, tvCategoryParams);
		
		TextView tvGenre = new TextView(tabResultInstance);
		LinearLayout.LayoutParams tvGenreParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tvGenre.setTextColor(Color.GRAY);
		tvGenre.setText("Genre: "+ SingleMoviePage.movieGenre);
		tvGenre.setTextSize(ShareData.ConvertFromDp(tabResultInstance, 28));
		tabContent.addView(tvGenre, tvGenreParams);
		
		TextView tv2 = new TextView(tabResultInstance);
		LinearLayout.LayoutParams tv2Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tv2.setTextColor(Color.GRAY);
//		if(SingleMoviePage.movieReleaseDate==null)
//		{
//			tv2.setText("");
//		}
//		else
//		{
//			if(SingleMoviePage.movieReleaseDate.trim().length()==0)
//			{
//				tv2.setText("");
//			}
//			else
//			{
			tv2.setText("Release Date: "+ SingleMoviePage.movieReleaseDate);
//			}
//		}
		tv2.setTextSize(ShareData.ConvertFromDp(tabResultInstance, 28));
		tabContent.addView(tv2, tv2Params);
		
		
		LinearLayout rateLayout = new LinearLayout(tabResultInstance);
		rateLayout.setBackgroundColor(Color.WHITE);
		LinearLayout.LayoutParams rateLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		rateLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView tvRating = new TextView(tabResultInstance);
		LinearLayout.LayoutParams tvRatingParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tvRating.setTextColor(Color.RED);
		tvRating.setText("Rating:");
		tvRating.setTextSize(ShareData.ConvertFromDp(tabResultInstance, 28));

		
		final TextView tvRate = new TextView(tabResultInstance);
		LinearLayout.LayoutParams tvRateParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tvRate.setTextColor(Color.RED);
		tvRate.setPadding(0, 20, 0, 0);
		tvRateParams.setMargins(0, 0, 0, ShareData.getScreenWidth(tabResultInstance)/6);
		tvRate.setTextSize(ShareData.ConvertFromDp(tabResultInstance, 28));
		
		
	    RatingBar ratingBar = new RatingBar(tabResultInstance, null , android.R.attr.ratingBarStyleSmall);
	    ratingBar.setPadding(0, 20, 0, 0);
	    ratingBar.setClickable(false);
	    LinearLayout.LayoutParams ratingBarParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
	    //if user is logged in
	    if(SingleMoviePage.userId.trim().length()!=0)
	    {
	    	final float userDataRating = SingleMoviePage.userRateStatus;
		    Log.d(DEBUG_TAG, "user loged in, Rating on tab:"+userDataRating);
		    
		    //user has not rated
		    if(userDataRating==0)
		    {	
		    	final float movieAvgRate =  SingleMoviePage.movieRating;
		    	Log.d(DEBUG_TAG, "user loged in, has not rated, avg rate:"+movieAvgRate);
		    	ratingBar.setClickable(true);
		    	ratingBar.setIsIndicator(false);
		    	ratingBar.setNumStars(10);
		   		ratingBar.setStepSize(1);
		   		tvRate.setText(""+movieAvgRate);
		   		
		   		
		   		LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
		   		//starFullySelected
				stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
				//starPartiallySelected
				stars.getDrawable(1).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
				//starNotSelected
				stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
					
				ratingBar.setRating(movieAvgRate);
		   		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
		   		{
		   			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) 
		    		{
		   				tvRate.setText(String.valueOf(rating)+"/10");
		   				counter++;
		   				Log.d(DEBUG_TAG, "cccc:"+counter);
		   				if(rating!=0)
		   				{		
		   					if(ratingBar.isClickable()==true)
		   					{
		   					StartingPage.rateType = "content";
		   				
		   					sendRatings("http://dev.bongobd.com/api/rating.php?user_id="+
		   							ShareData.getSavedString(tabResultInstance, "id")+
		   							"&secret="+ShareData.getSavedString(tabResultInstance, "secret")+
		   							"&rate="+String.valueOf(rating)+
		   							"&content_id="+ListViewAdapter.singleMovieId+"&type="+StartingPage.rateType);
		   					
		   					Log.d(DEBUG_TAG, "Rate url:http://dev.bongobd.com/api/rating.php?user_id="+
		   							ShareData.getSavedString(tabResultInstance, "id")+
		   							"&secret="+ShareData.getSavedString(tabResultInstance, "secret")+
		   							"&rate="+String.valueOf(rating)+
		   							"&content_id="+ListViewAdapter.singleMovieId+"&type="+StartingPage.rateType);
		   				   	ratingBar.setClickable(false);
		   			   		ratingBar.setIsIndicator(true);
		   					}
		   					else
		   					{
								Toast.makeText(tabResultInstance, "You have already rated this.", Toast.LENGTH_SHORT).show();
		   					}
		   				}
		    		}
		    	});
			}
		    //user already rated
		    else if(userDataRating!=0)
		    {
		    	Log.d(DEBUG_TAG, "user loged in already rated.");
		    	ratingBar.setClickable(false);
		   		ratingBar.setIsIndicator(true);
		   		ratingBar.setNumStars(10);
		   		ratingBar.setStepSize(1);
		   		tvRate.setText(" "+userDataRating+"/10");
		    		
		   		LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
		   		//starFullySelected
				stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
				//starPartiallySelected
				stars.getDrawable(1).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
				//starNotSelected
				stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
				
				ratingBar.setRating(userDataRating);
				ratingBar.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						Toast.makeText(tabResultInstance, "You have already rated this.", Toast.LENGTH_SHORT).show();
						return false;
					}
				});
			        
//				ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
//		   		{
//		    		public void onRatingChanged(RatingBar ratingBar, float rating,
//		    			boolean fromUser) 
//		    		{
//		    			tvRate.setText(userDataRating+"/10");
//		    			//Toast.makeText(tabResultInstance, "You have already rated this.", Toast.LENGTH_SHORT).show();
//		    			//sendRatings("http://dev.bongobd.com/api/rating.php?user_id=305&secret=4duqigkh3r9u0nq5en33kgd3l2-3607dbc46404e10bd98d86886912036d&rate=5&content_id=26&rate=5&type=artist&raw=ture");
//		    				
//		    		}
//		    	});
		    		
		    }
	    }
	    
	    
	    
	    //if user is not logged in	
	    else if(SingleMoviePage.userId.trim().length()==0)
	    {
	    	final float txt2 = SingleMoviePage.movieRating;
	    	Log.d(DEBUG_TAG, "user not loged in, show avg rate:"+txt2);
	    	ratingBar.setClickable(false);
	   		ratingBar.setIsIndicator(true);
	   		ratingBar.setNumStars(10);
	   		ratingBar.setStepSize(1);
	   		tvRate.setText(" "+txt2+"/10");
	    		
	   		LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
	   		//starFullySelected
			stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
			//starPartiallySelected
			stars.getDrawable(1).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
			//starNotSelected
			stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
			
			ratingBar.setRating(txt2);
			ratingBar.setOnTouchListener(new View.OnTouchListener() 
			{
				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					// TODO Auto-generated method stub
					Toast.makeText(tabResultInstance, "Please log in to rate this.", Toast.LENGTH_SHORT).show();
					return false;
				}
			});
			
			
//			ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener()
//	   		{
//	    		public void onRatingChanged(RatingBar ratingBar, float rating,
//	    			boolean fromUser) 
//	    		{
//	    			tvRate.setText(txt2+"/10");
//	    			//Toast.makeText(tabResultInstance, "Please log in to rate this.", Toast.LENGTH_SHORT).show();
//	    			//sendRatings("http://dev.bongobd.com/api/rating.php?user_id=305&secret=4duqigkh3r9u0nq5en33kgd3l2-3607dbc46404e10bd98d86886912036d&rate=5&content_id=26&rate=5&type=artist&raw=ture");
//	    				
//	    		}
//	    	});
	    }

	    rateLayout.addView(tvRating, tvRatingParams);
	    rateLayout.addView(ratingBar, ratingBarParams);
	    rateLayout.addView(tvRate, tvRateParams);
	    tabContent.addView(rateLayout, rateLayoutParams);
	    
//	    ratingBar.setMax(10);
	}
    
    
    public void sendRatings(String urlJsonObj) 
    {
    	requestQueue = Volley.newRequestQueue(tabResultInstance);
    	JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
    			urlJsonObj, null, new Response.Listener<JSONObject>()
   				{
    				@Override
    				public void onResponse(JSONObject response) 
    				{
    					Log.d(DEBUG_TAG, response.toString());
    					try 
    					{
    						String errorMessage = response.getString("error");
    						Log.d(DEBUG_TAG, "error message: "+errorMessage);
    						if(errorMessage.trim().length()==0)
    						{
    							String message = response.getString("message");
        						Log.d(DEBUG_TAG, "message: "+message);
        						
        						Toast.makeText(tabResultInstance,"" + message,Toast.LENGTH_LONG).show();
    						}
    						else
    						{
    							Toast.makeText(tabResultInstance.getBaseContext(),
    									"" + errorMessage,Toast.LENGTH_LONG).show();
    						}
    						
//    						additionalData = response.getJSONObject("additionalData");
//       					    Log.d(DEBUG_TAG, " additionalData:"+additionalData );
//    						movieUrl = additionalData.getString("iframe2");
//    						// Log.d(DEBUG_TAG, " movieUrl:"+ movieUrl );
//
//    						movieName = data.getString("content_title");
//    						// Log.d(DEBUG_TAG, "movieName:"+movieName );
    					}
    					catch (JSONException e)
    					{
    						e.printStackTrace();
    						Toast.makeText(tabResultInstance, "Error:" + e.getMessage(),Toast.LENGTH_LONG).show();
    					}
    				}
    			}, new Response.ErrorListener() 
    			{
    				@Override
    				public void onErrorResponse(VolleyError error)
    				{
    					VolleyLog.d("Tag", "Error: " + error.getMessage());
    					Toast.makeText(tabResultInstance,error.getMessage(), Toast.LENGTH_SHORT).show();
    					// hide the progress dialog
    				}
    			});

    		// Adding request to request queue
    		requestQueue.add(jsonObjReq);
    }
    
    //Function to post to facebook wall
  	@SuppressWarnings("deprecation")
  	public void postToWall()
  	{
  		
  		 Bundle parameters = new Bundle();
  		    parameters.putString("name", "BongoBD app");
  		    parameters.putString("caption", "BongoBD caption.");
  		    parameters.putString("description", "BongoBD description.");
  		    parameters.putString("link", "https://play.google.com/store/apps/details?id=com.ck.batterymonitor");
  		    //parameters.putString("picture", "link to the picture");
  		    parameters.putString("display", "page");
  		    
 		// post on user's wall.
//1  		FacebookApp.facebook.dialog(tabResultInstance, "feed", parameters, new DialogListener()
//  		{
//  			@Override
//  			public void onFacebookError(FacebookError e)
//  			{
//  				
//  			}
//  			@Override
//  			public void onError(DialogError e) 
//  			{
//  				
//  			}
//  			@Override
//  			public void onComplete(Bundle values)
//  			{
//  				
//  			}
//  			@Override
//  			public void onCancel() 
//  			{
//  				
//  			}
//  		});
  	}
  	
  	//Twitter
    private void initControl()
    {
        Uri uri = tabResultInstance.getIntent().getData();
        if (uri != null && uri.toString().startsWith(ConstantValues.TWITTER_CALLBACK_URL)) {
            String verifier = uri.getQueryParameter(ConstantValues.URL_PARAMETER_TWITTER_OAUTH_VERIFIER);
            new TwitterGetAccessTokenTask().execute(verifier);
        } else
            new TwitterGetAccessTokenTask().execute("");
    }

//    private void initializeComponent() {
//        buttonUpdateStatus = (Button) findViewById(R.id.buttonUpdateStatus);
//        buttonLogout = (Button) findViewById(R.id.buttonLogout);
//        editTextStatus = (EditText) findViewById(R.id.editTextStatus);
//        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
//        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
//        buttonUpdateStatus.setOnClickListener(buttonUpdateStatusOnClickListener);
//        buttonLogout.setOnClickListener(buttonLogoutOnClickListener);
//        
//        editTextStatus.setText("Hello");
//    }

    private View.OnClickListener buttonLogoutOnClickListener = new View.OnClickListener() 
    {
        @Override
        public void onClick(View v)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(tabResultInstance);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, "");
            editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, "");
            editor.putBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN, false);
            editor.commit();
            TwitterUtil.getInstance().reset();
            Log.d("TestActivity", "Ok Loged out");
//            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
//            startActivity(intent);
        }
    };

//    private View.OnClickListener buttonUpdateStatusOnClickListener = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            String status = editTextStatus.getText().toString();
//            if (!StringUtil.isNullOrWhitespace(status)) {
//                new TwitterUpdateStatusTask().execute(status);
//            } else {
//                Toast.makeText(getApplicationContext(), "Please enter a status", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    };

    class TwitterGetAccessTokenTask extends AsyncTask<String, String, String> 
    {
        @Override
        protected void onPostExecute(String userName) 
        {
        	Toast.makeText(tabResultInstance, "Name:"+userName, Toast.LENGTH_SHORT).show();
            //textViewUserName.setText(Html.fromHtml("<b> Welcome " + userName + "</b>"));
        }

        @Override
        protected String doInBackground(String... params) 
        {
            Twitter twitter = TwitterUtil.getInstance().getTwitter();
            RequestToken requestToken = TwitterUtil.getInstance().getRequestToken();
            if (!StringUtil.isNullOrWhitespace(params[0])) 
            {
                try 
                {
                    AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, params[0]);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(tabResultInstance);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, accessToken.getToken());
                    editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, accessToken.getTokenSecret());
                    editor.putBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN, true);
                    editor.commit();
                    return twitter.showUser(accessToken.getUserId()).getName();
                } 
                catch (TwitterException e) 
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } 
            else 
            {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(tabResultInstance);
                String accessTokenString = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, "");
                String accessTokenSecret = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, "");
                AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
                try 
                {
                    TwitterUtil.getInstance().setTwitterFactory(accessToken);
                    return TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getName();
                } 
                catch (TwitterException e) 
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    class TwitterUpdateStatusTask extends AsyncTask<String, String, Boolean>
    {
        @Override
        protected void onPostExecute(Boolean result) 
        {
            if (result)
                Toast.makeText(tabResultInstance, "Tweet successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(tabResultInstance, "Tweet failed", Toast.LENGTH_SHORT).show();
            	logIn();
        }

        @Override
        protected Boolean doInBackground(String... params)
        {
            try 
            {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(tabResultInstance);
                String accessTokenString = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, "");
                String accessTokenSecret = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, "");

                if(!StringUtil.isNullOrWhitespace(accessTokenString) && !StringUtil.isNullOrWhitespace(accessTokenSecret)) {
                    AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
                    twitter4j.Status status = TwitterUtil.getInstance().getTwitterFactory().getInstance(accessToken).updateStatus(params[0]);
                    return true;
                }

            } 
            catch (TwitterException e) 
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return false;  //To change body of implemented methods use File | Settings | File Templates.

        }
    }
    
    public void twitterDialog()
    {
    	final Dialog dialog = new Dialog(tabResultInstance);
		dialog.setContentView(R.layout.twitter_dialog);
		dialog.setCancelable(false);
		dialog.setTitle("Twitter Share");

		final TextView text = (TextView) dialog.findViewById(R.id.tvTweet);
		//text.setText("BongoBD Tweet");

		Button dialogButton = (Button) dialog.findViewById(R.id.btnTweet);
		dialogButton.setTextSize(ShareData.ConvertFromDp(tabResultInstance, 28));
		dialogButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				String status = text.getText().toString();
				if (!StringUtil.isNullOrWhitespace(status)) 
				{
	                new TwitterUpdateStatusTask().execute(status);
	            } 
				else 
				{
	                Toast.makeText(tabResultInstance, "Please enter a status", Toast.LENGTH_SHORT).show();
	            }
				dialog.dismiss();
			}
		});
		
		Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
		cancelButton.setTextSize(ShareData.ConvertFromDp(tabResultInstance, 28));
		cancelButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				dialog.dismiss();
			}
		});

		dialog.show();
    }
    
    public void initTwitter()
	{
		   if(!OSUtil.IsNetworkAvailable(tabResultInstance)) 
		   {
	            AlertMessageBox.Show(tabResultInstance, "Internet connection", "A valid internet connection can't be established", AlertMessageBox.AlertMessageBoxIcon.Info);
	            return;
	       }

	       if(StringUtil.isNullOrWhitespace(ConstantValues.TWITTER_CONSUMER_KEY) || StringUtil.isNullOrWhitespace(ConstantValues.TWITTER_CONSUMER_SECRET))
	       {
	            AlertMessageBox.Show(tabResultInstance, "Twitter oAuth infos", "Please set your twitter consumer key and consumer secret", AlertMessageBox.AlertMessageBoxIcon.Info);
	            return;
	       }
	       if (isUseStoredTokenKey)
	            logIn();
	}
    
    private void logIn() 
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(tabResultInstance);
        if (!sharedPreferences.getBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN,false))
        {
            new TwitterAuthenticateTask().execute();
        }
        else
        {
            Intent intent = new Intent(tabResultInstance, TwitterActivity.class);
            tabResultInstance.startActivity(intent);
//        	twitterDialog();
        }
    }

    class TwitterAuthenticateTask extends AsyncTask<String, String, RequestToken>
    {
        @Override
        protected void onPostExecute(RequestToken requestToken)
        {
            if (requestToken!=null)
            {
                if (!isUseWebViewForAuthentication)
                {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL()));
                    tabResultInstance.startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(tabResultInstance, OAuthActivity.class);
                    intent.putExtra(ConstantValues.STRING_EXTRA_AUTHENCATION_URL,requestToken.getAuthenticationURL());
                    tabResultInstance.startActivity(intent);
                }
            }
        }

        @Override
        protected RequestToken doInBackground(String... params)
        {
            return TwitterUtil.getInstance().getRequestToken();
        }
    }
    
//    @Override
//    public void onBackPressed() 
//	{
//		super.onBackPressed();
//		//SingleMoviePage.this.finish();
//	    if(StartingPage.singleMoviePageReturn == 1)
//	    {
//			 startActivity(new Intent(getBaseContext(), CategoryLanding.class));
//			 overridePendingTransition( R.anim.animation1, R.anim.animation2 );
//		} 
//		else if(StartingPage.singleMoviePageReturn == 2)
//		{
//		  	 startActivity(new Intent(getBaseContext(), Movies.class));
//		  	 overridePendingTransition( R.anim.animation1, R.anim.animation2 );
//		} 
//	}
}