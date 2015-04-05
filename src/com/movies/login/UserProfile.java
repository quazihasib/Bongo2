package com.movies.login;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.layout;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.movies.actorProfile.ActorProfile;
import com.movies.bongobd.ImageLoader;
import com.movies.bongobd.R;
import com.movies.browseAll.Movies;
import com.movies.categoryPage.CategoryLanding;
import com.movies.movieSummary.MovieSummary;
import com.movies.people.People;
import com.movies.singleMovie.SingleMoviePage;
import com.movies.singleMovie.VideoCounter;
import com.movies.startingPage.StartingPage;
import com.movies.subscribe.Subscribe;
import com.tab.AddMenu;
import com.tab.LoadImageFromURL;
import com.tab.ShareData;

public class UserProfile extends Activity implements ImageGetter
{
	public TextView userName, userMail, updatePasswordtv;
	public EditText multipleEmailet, subscribeNewsLetteret;
	public LinearLayout wholeUserProfileScreenlayout;
	public Button referBtn, btnSubscribeNewsLetter;
	public RequestQueue requestQueue;

	public ImageView userImage;
	public ImageLoader userImageLoader;
	public ImageView crossButtonProfile, ivUserProfileSearch;
	public String pic;
	
	public static UserProfile userProfileInstance;
	public static String DEBUG_TAG = UserProfile.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_user_profile);

		userProfileInstance = this;
		
		ImageView bongoIcon = (ImageView)findViewById(R.id.imageView2);
		LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setMargins(40, 20, 40, 20);
		bongoIcon.setLayoutParams(ll);
		
		wholeUserProfileScreenlayout = (LinearLayout)findViewById(R.id.wholeUserProfileScreenlayout);
		wholeUserProfileScreenlayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				ShareData.hideKeyboard(userProfileInstance);
			}
		});
		
		TextView textView1 = (TextView)findViewById(R.id.textView1);
		textView1.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 35));
		
//		TextView uupdatepinfo = (TextView)findViewById(R.id.uupdatepinfo);
//		uupdatepinfo.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 28));

		TextView uchangepass = (TextView)findViewById(R.id.uchangepass);
		uchangepass.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 32));
		
//		TextView tvRefer = (TextView)findViewById(R.id.tvRefer);
//		tvRefer.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 35));
//		
//		TextView tvEarnPoints = (TextView)findViewById(R.id.tvEarnPoints);
//		tvEarnPoints.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 28));
//		
//		TextView tvSubcribe = (TextView)findViewById(R.id.tvSubcribe);
//		tvSubcribe.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 35));
//		
//		TextView tvSubcribeNewsLetter = (TextView)findViewById(R.id.tvSubcribeNewsLetter);
//		tvSubcribeNewsLetter.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 28));
		
		userName = (TextView) findViewById(R.id.uname);
		userName.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 34));
		
		userMail = (TextView) findViewById(R.id.uemail);
		userMail.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 32));
		
		
//		Button btnSubscribeNewsLetter = (Button) findViewById(R.id.btnSubscribeNewsLetter);
//		btnSubscribeNewsLetter.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 28));
	
		userImage = (ImageView) findViewById(R.id.userimg);
		userImageLoader = new ImageLoader(this);
		
		Log.d(DEBUG_TAG, "UserProfile Entered");
		
		try
		{
			pic = ShareData.getSavedString(userProfileInstance, "pic");
			Log.d(DEBUG_TAG, "profile pic:"+pic);
			
			if(pic.length()!=0)
			{
				//Log.d("FB1", "pic2:"+ShareData.getSavedString(userProfileInstance, "pic"));
				//userImageLoader.DisplayImage(pic, userImage);
				new LoadImageFromURL(userImage).execute(pic);
			}
			//getDrawable(ShareData.getSavedString(userProfileInstance, "pic"));
			Log.d(DEBUG_TAG, "name:"+ShareData.getSavedString(userProfileInstance, "name"));
			if(ShareData.getSavedString(userProfileInstance, "name").trim().length()!=0)
			{
				userName.setText(ShareData.getSavedString(userProfileInstance, "name"));
			}
			Log.d(DEBUG_TAG, "username"+ShareData.getSavedString(userProfileInstance, "username"));
			if(ShareData.getSavedString(userProfileInstance, "username").trim().length()!=0)
			{
				userMail.setText(ShareData.getSavedString(userProfileInstance, "username"));
			}
			
			//restart the counter
			VideoCounter.restartVideoCounter(userProfileInstance);
			
//			ReferFriend();
			UpdatePassword();
//			SubscribeNewsLetter();
			crossButtonProfiles();
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getBaseContext(), "The user is not logged in.", Toast.LENGTH_LONG).show();
			
//			finish();
//			startActivity(new Intent(getBaseContext(), LoginActivity.class));
//			overridePendingTransition( R.anim.animation1, R.anim.animation2 );
		}
	}
	
	
	
	public void crossButtonProfiles()
	{
		ivUserProfileSearch = (ImageView)findViewById(R.id.ivUserProfileSearch);
//		LinearLayout.LayoutParams ll1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		ll1.setMargins(30, 20, 30, 20);
//		ivUserProfileSearch.setLayoutParams(ll1);
		
//		ivUserProfileSearch.setPadding(-10, 
//				ShareData.getScreenWidth(userProfileInstance)/StartingPage.crossButtonPadding, 
//				ShareData.getScreenWidth(userProfileInstance)/StartingPage.crossButtonPadding, 
//				ShareData.getScreenWidth(userProfileInstance)/StartingPage.crossButtonPadding);
//		
		crossButtonProfile = (ImageView)findViewById(R.id.crossButtonProfile);
//		crossButtonProfile.setPadding(ShareData.getScreenWidth(userProfileInstance)/StartingPage.crossButtonPadding, 
//				ShareData.getScreenWidth(userProfileInstance)/StartingPage.crossButtonPadding, 
//				ShareData.getScreenWidth(userProfileInstance)/StartingPage.crossButtonPadding, 
//				ShareData.getScreenWidth(userProfileInstance)/StartingPage.crossButtonPadding);
		crossButtonProfile.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				finish();
//				CategoryLanding.catagoryName = "movies";
				//startActivity(new Intent(getBaseContext(), CategoryLanding.class));
				if(AddMenu.pageNumber == 1)
				{
					startActivity(new Intent(getBaseContext(), CategoryLanding.class));
				}
				else if(AddMenu.pageNumber == 2)
				{
					startActivity(new Intent(getBaseContext(), People.class));
				}
				else if(AddMenu.pageNumber == 3)
				{
					startActivity(new Intent(getBaseContext(), Subscribe.class));
				}
				else if(AddMenu.pageNumber == 4)
				{
					startActivity(new Intent(getBaseContext(), SingleMoviePage.class));
				}
				else if(AddMenu.pageNumber == 5)
				{
					startActivity(new Intent(getBaseContext(), Movies.class));
				}
				else if(AddMenu.pageNumber == 6)
				{
					startActivity(new Intent(getBaseContext(), ActorProfile.class));
				}
				else if(AddMenu.pageNumber == 7)
				{
					startActivity(new Intent(getBaseContext(), MovieSummary.class));
				}
				overridePendingTransition( R.anim.animation1, R.anim.animation2 );
			}
		});
	}
	
	public void UpdatePassword()
	{
		// TODO Auto-generated method stub
		updatePasswordtv = (TextView) findViewById(R.id.uchangepass);
		updatePasswordtv.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				finish();
				startActivity(new Intent(getBaseContext(), UpdatePassword.class));
				overridePendingTransition( R.anim.animation1, R.anim.animation2 );
			}
		});
	}

//	public void SubscribeNewsLetter() 
//	{
//		// TODO Auto-generated method stub
//		subscribeNewsLetteret = (EditText) findViewById(R.id.subscribeNewsLetteret);
//		subscribeNewsLetteret.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 28));
//		
//		btnSubscribeNewsLetter = (Button) findViewById(R.id.btnSubscribeNewsLetter);
//		btnSubscribeNewsLetter.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 28));
//		btnSubscribeNewsLetter.setOnClickListener(new OnClickListener() 
//		{
//			@Override
//			public void onClick(View v) 
//			{
//				btnSubscribeNewsLetter.setEnabled(false);
//				btnSubscribeNewsLetter.setBackgroundColor(Color.rgb(208, 95, 95));
//				
//				// validate data
//				Validation(subscribeNewsLetteret.getText().toString().trim());
//			}
//
//		});
//	}
//	
//	public void ReferFriend() 
//	{
//		// TODO Auto-generated method stub
//		multipleEmailet = (EditText) findViewById(R.id.multipleemail);
//		multipleEmailet.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 28));
//		
//		referBtn = (Button) findViewById(R.id.btnrefer);
//		referBtn.setTextSize(ShareData.ConvertFromDp(userProfileInstance, 28));
//		referBtn.setOnClickListener(new OnClickListener() 
//		{
//			@Override
//			public void onClick(View v) 
//			{
//				referBtn.setEnabled(false);
//				referBtn.setBackgroundColor(Color.rgb(208, 95, 95));
//				
//				// validate data
//				Validation(multipleEmailet.getText().toString().trim());
//			}
//
//		});
//	}

	@SuppressLint("NewApi")
	private void Validation(String multipleemailparam)
	{
		// TODO Auto-generated method stub   
		if (multipleemailparam.length()==0) 
		{
			if(referBtn.isEnabled()==false)
			{
				referBtn.setEnabled(true);
				referBtn.setBackgroundColor(Color.rgb(208, 0, 0));
			}
			else if(btnSubscribeNewsLetter.isEnabled()==false)
			{
				btnSubscribeNewsLetter.setEnabled(true);
				btnSubscribeNewsLetter.setBackgroundColor(Color.rgb(208, 0, 0));
			}
			
			Toast.makeText(UserProfile.this,"Please enter multiple email address with comma.",Toast.LENGTH_SHORT).show();
			multipleEmailet.requestFocus();
		} 
		else if (multipleemailparam != null)
		{
			if (multipleemailparam.endsWith(",")) 
			{
				multipleemailparam = multipleemailparam.substring(0, multipleemailparam.length() - 1);
			}

			System.out.println("email =" + multipleemailparam);
			// validate by service
			ValidateByService(multipleemailparam);
		}
	}

	private void ValidateByService(String multiemail) 
	{
		// TODO Auto-generated method stub
		String url = "http://dev.bongobd.com/api/invite_friends.php?email="
				+ multiemail + "&sender_id=" + ShareData.getSavedString(userProfileInstance, "id") + "&secret="
				+ ShareData.getSavedString(userProfileInstance, "secret");
		System.out.println("url = " + url);

		requestQueue = Volley.newRequestQueue(this);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST, url,
				null, new Response.Listener<JSONObject>()
				{
					@SuppressLint("NewApi")
					@Override
					public void onResponse(JSONObject response) 
					{
						try 
						{	
							Log.d(DEBUG_TAG, "response:"+response);
							String jsError = response.getString("error");
							if (jsError.length()==0) 
							{
								//JSONObject js = response.getJSONObject("data");

								String message = response.getString("message");
								
								if(referBtn.isEnabled()==false)
								{
									referBtn.setEnabled(true);
									referBtn.setBackgroundColor(Color.rgb(208, 0, 0));
								}
								else if(btnSubscribeNewsLetter.isEnabled()==false)
								{
									btnSubscribeNewsLetter.setEnabled(true);
									btnSubscribeNewsLetter.setBackgroundColor(Color.rgb(208, 0, 0));
								}
									
								Toast.makeText(getBaseContext(),
										message, Toast.LENGTH_LONG).show();
							} 
							else
							{
								if(referBtn.isEnabled()==false)
								{
									referBtn.setEnabled(true);
									referBtn.setBackgroundColor(Color.rgb(208, 0, 0));
								}
								else if(btnSubscribeNewsLetter.isEnabled()==false)
								{
									btnSubscribeNewsLetter.setEnabled(true);
									btnSubscribeNewsLetter.setBackgroundColor(Color.rgb(208, 0, 0));
								}
								
								Toast.makeText(getBaseContext(),
										"Error: " + jsError, Toast.LENGTH_LONG).show();
							}

						} 
						catch (JSONException e) 
						{
							referBtn.setEnabled(true);
							referBtn.setBackgroundColor(Color.rgb(208, 0, 0));
							
							e.printStackTrace();
							Toast.makeText(getBaseContext(),
									"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
						}
					}
				}, new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error) 
					{
						referBtn.setEnabled(true);
						referBtn.setBackgroundColor(Color.rgb(208, 0, 0));
						
						Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
		System.out.println("url =" + url);
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

	class LoadImage extends AsyncTask<Object, Void, Bitmap> {

		private LevelListDrawable mDrawable;

		@Override
		protected Bitmap doInBackground(Object... params) {
			String source = (String) params[0];
			mDrawable = (LevelListDrawable) params[1];
			// Log.d("", "doInBackground " + source);
			try {
				InputStream is = new URL(source).openStream();
				return BitmapFactory.decodeStream(is);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap)
		{
			// Log.d("", "onPostExecute drawable " + mDrawable);
			// Log.d("", "onPostExecute bitmap " + bitmap);
			if (bitmap != null) {
				@SuppressWarnings("deprecation")
				BitmapDrawable d = new BitmapDrawable(bitmap);
				mDrawable.addLevel(1, 1, d);
				mDrawable.setBounds(0, 0, 20, 50);
				mDrawable.setLevel(1);

			}
		}
	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		
		UserProfile.this.finish();
		startActivity(new Intent(getBaseContext(), CategoryLanding.class));
		overridePendingTransition( R.anim.animation1, R.anim.animation2 );
	}

}
