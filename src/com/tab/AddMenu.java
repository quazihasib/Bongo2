package com.tab;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.Parser;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.movies.actorProfile.ActorProfile;
import com.movies.bongobd.ImageLoader;
import com.movies.bongobd.R;
import com.movies.browseAll.Movies;
import com.movies.categoryPage.CategoryLanding;
import com.movies.facebook.FacebookApp;
import com.movies.login.LoginActivity;
import com.movies.login.UserProfile;
import com.movies.movieSummary.MovieSummary;
import com.movies.people.People;
import com.movies.singleMovie.SingleMoviePage;
import com.movies.startingPage.StartingPage;
import com.movies.subscribe.Subscribe;
import com.navdrawer.SimpleSideDrawer;

public class AddMenu 
{
	public static boolean menuFlag;
	public static ImageLoader userImageLoader;
	public static ImageView menuButton, loginPic, settings, ivSearch;
	public static TextView tvName;
	public static SimpleSideDrawer slide_me;
	public static boolean clickable;
	public static String searchQuery;
	
	public static int pageNumber;
	
	static int height;
	static int h1;
	
	public static Handler clickHandler;
	public static Runnable alternate;
	
	public static String DEBUG_TAG = AddMenu.class.getSimpleName();
	
	public static Header HeaderFunction(final Activity instance)
    {

    	AddMenu.addMenus(instance);
    	clickable=false;
    	
        final Header header = (Header) instance.findViewById(R.id.headers);
		header.initHeader(instance);
		
		header.bongoIcon.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				instance.finish();
				instance.startActivity(new Intent(instance.getBaseContext(), StartingPage.class));
				instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
			}
		});
		
		final EditText et = new EditText(instance);
		et.setHint("Search");
		et.setHintTextColor(Color.GRAY);
		et.setGravity(Gravity.CENTER);
		et.setTextColor(Color.BLACK);
		et.setBackgroundResource(R.drawable.border);
		et.setBackgroundColor(Color.WHITE);
		et.setSingleLine();
		et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		et.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
		   @Override
		   public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
		   {
			    if((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || 
			    		(actionId == EditorInfo.IME_ACTION_SEARCH))
			    {
			    	searchQuery = et.getText().toString().trim();
					Log.d("AddMenu", "searchQuery:"+searchQuery);
					//If there is data
					if(searchQuery.length()!=0)
					{
						header.headerLayout.removeAllViews();
						header.headerLayout.addView(header.menuIcon, header.menuLayout);
						header.headerLayout.addView(header.bongoIcon, header.bongoLayout);
						header.headerLayout.addView(header.searchIcon, header.searchLayout);
						header.searchLayout.setMargins(80, 20, 50, 20);
					
						StartingPage.browseAll = 3;
					
						instance.finish();
						instance.startActivity(new Intent(instance.getBaseContext(), Movies.class));
						instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
					}
					//If there is no data
					else
					{
						instance.finish();
						instance.startActivity(new Intent(instance.getBaseContext(), CategoryLanding.class));
						instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
					}
	            } 
		        return false;
		    }
		});
		
		final LinearLayout.LayoutParams etLayout = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 5);

//		InputMethodManager mIMEMgr = (InputMethodManager) instance.getSystemService(Context.INPUT_METHOD_SERVICE);
		//mIMEMgr.
		
		
		header.searchIcon.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				//Dialog dialog1s = DialogSearch.dialogs(instance);
			 	//dialog1s.show();
				
				//search button pressed first time
				if(header.headerLayout.getChildCount()==3)
				{
					header.headerLayout.removeView(header.menuIcon);
					header.headerLayout.removeView(header.bongoIcon);
					header.headerLayout.addView(et, etLayout);
					header.searchLayout.setMargins(40, 20, 120, 20);
					
				}
				//after search query is written
				else if(header.headerLayout.getChildCount()==2)
				{
					searchQuery = et.getText().toString().trim();
					Log.d("AddMenu", "searchQuery:"+searchQuery);
					//If there is data
					if(searchQuery.length()!=0)
					{
						header.headerLayout.removeAllViews();
						header.headerLayout.addView(header.menuIcon, header.menuLayout);
						header.headerLayout.addView(header.bongoIcon, header.bongoLayout);
						header.headerLayout.addView(header.searchIcon, header.searchLayout);
						header.searchLayout.setMargins(80, 20, 50, 20);
					
						StartingPage.browseAll = 3;
					
						instance.finish();
						instance.startActivity(new Intent(instance.getBaseContext(), Movies.class));
						instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
					}
					//If there is no data
					else
					{
						instance.finish();
						instance.startActivity(new Intent(instance.getBaseContext(), CategoryLanding.class));
						instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
					}
				}
			}
		});
		
		
		header.menuIcon.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				slide_me.toggleLeftDrawer();
			}
		});
		
		new CountDownTimer(3000, 1000) 
		{
			public void onTick(long millisUntilFinished) 
		    {
//		    	header.tv1.setText("Wait for " + (millisUntilFinished / 1000) + " seconds");
		    }
		    public void onFinish() 
		    {
		    	header.tv1.setOnClickListener(new View.OnClickListener() 
		 		{ 
		    		@Override
		 			public void onClick(View arg0) 
		 			{
		 				// TODO Auto-generated method stub
		 				CategoryLanding.catagoryName = "movies";
		 				instance.finish();
		 				instance.startActivity(new Intent(instance.getBaseContext(), CategoryLanding.class));
		 				instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
		 				
		 				header.tv1.setBackgroundColor(Color.parseColor("#B40404"));
		    			header.tv2.setBackgroundColor(Color.parseColor("#FF0000"));
		    			header.tv3.setBackgroundColor(Color.parseColor("#FF0000"));
		    			header.tv4.setBackgroundColor(Color.parseColor("#FF0000"));
		 			}
		 		});
		    	
		    	header.tv2.setOnClickListener(new View.OnClickListener() 
				{
					@Override
					public void onClick(View arg0) 
					{
						// TODO Auto-generated method stub
						CategoryLanding.catagoryName = "tv";
						instance.finish();
						instance.startActivity(new Intent(instance.getBaseContext(), CategoryLanding.class));
						instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
						
						header.tv1.setBackgroundColor(Color.parseColor("#FF0000"));
		    			header.tv2.setBackgroundColor(Color.parseColor("#B40404"));
		    			header.tv3.setBackgroundColor(Color.parseColor("#FF0000"));
		    			header.tv4.setBackgroundColor(Color.parseColor("#FF0000"));
					}
				});
				
				header.tv3.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						// TODO Auto-generated method stub
						CategoryLanding.catagoryName = "music";
						instance.finish();
						instance.startActivity(new Intent(instance.getBaseContext(), CategoryLanding.class));
						instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
						
						header.tv1.setBackgroundColor(Color.parseColor("#FF0000"));
		    			header.tv2.setBackgroundColor(Color.parseColor("#FF0000"));
		    			header.tv3.setBackgroundColor(Color.parseColor("#B40404"));
		    			header.tv4.setBackgroundColor(Color.parseColor("#FF0000"));
					}
				});
		     }
		}.start();
		  
		
		header.tv4.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				instance.finish();
				instance.startActivity(new Intent(instance.getBaseContext(), People.class));
				instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
				
				header.tv1.setBackgroundColor(Color.parseColor("#FF0000"));
    			header.tv2.setBackgroundColor(Color.parseColor("#FF0000"));
    			header.tv3.setBackgroundColor(Color.parseColor("#FF0000"));
    			header.tv4.setBackgroundColor(Color.parseColor("#B40404"));
			}
		});

//		header.tv5.setOnClickListener(new View.OnClickListener() 
//		{
//			@Override
//			public void onClick(View arg0) 
//			{
//				// TODO Auto-generated method stub
//				
//				instance.finish();
//				instance.startActivity(new Intent(instance.getBaseContext(), Subscribe.class));
//				instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
//			}
//		});
		
		
		header.hsv.setOnTouchListener(new OnTouchListener()
		{           
		    @Override
		    public boolean onTouch(View v, MotionEvent event) 
		    {
		    	//Log.d(DEBUG_TAG, "scroller:"+header.hsv.getScrollX());
		    	if(header.hsv.getScrollX()==0) 
		    	{
		    		header.sideLeft.setVisibility(View.INVISIBLE);
		    		if(header.sideRight.getVisibility()==View.INVISIBLE)
		    		{
		    			header.sideRight.setVisibility(View.VISIBLE);
		    		}
		    	}
		    	else if(header.hsv.getScrollX() >= Header.mainMaxScroll)
		    	{
		    		//Log.d(header.DEBUG_TAG, "scroller:"+v.getId()+" hide right");
		    		
		    		header.sideRight.setVisibility(View.INVISIBLE);
		    		if(header.sideLeft.getVisibility()==View.INVISIBLE)
		    		{
		    			header.sideLeft.setVisibility(View.VISIBLE);
		    		}
		    	}
		    	else
		    	{
		    		if(header.sideLeft.getVisibility()==View.INVISIBLE)
		    		{
		    			header.sideLeft.setVisibility(View.VISIBLE);
		    		}
		    		
		    		if(header.sideRight.getVisibility()==View.INVISIBLE)
		    		{
		    			header.sideRight.setVisibility(View.VISIBLE);
		    		}
		    	}
			return false;
		    }
		});
		
		return header;
    }
	
	public static  void addMenus(final Activity instance)
	{
		slide_me = new SimpleSideDrawer(instance);
		slide_me.setLeftBehindContentView(R.layout.left_menu);
		
		userImageLoader = new ImageLoader(instance);
		loginPic = (ImageView) instance.findViewById(R.id.loginPic);
		loginPic.getLayoutParams().width = ShareData.getScreenWidth(instance)/8;
		loginPic.getLayoutParams().height = ShareData.getScreenWidth(instance)/8;
		
		settings = (ImageView) instance.findViewById(R.id.settings);
		settings.getLayoutParams().width = ShareData.getScreenWidth(instance)/8;
		settings.getLayoutParams().height = ShareData.getScreenWidth(instance)/8;
    	tvName = (TextView) instance.findViewById(R.id.tvName);
		
		ScrollView sv = (ScrollView)instance.findViewById(R.id.scrollView);
		sv.setLayoutParams(new LayoutParams(ShareData.getScreenWidth(instance)- ShareData.getScreenWidth(instance)/4,
                LayoutParams.FILL_PARENT));
		
		LinearLayout mainLayout = (LinearLayout)instance.findViewById(R.id.mainLayout);
		for(int a=0; a<2; a++)
		{
			final LinearLayout ll1 = new LinearLayout(instance);
			LinearLayout.LayoutParams llM1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			ll1.setBackgroundColor(Color.parseColor("#ededed"));
			ll1.setLayoutParams(llM1);
			ll1.setId(a);
			
			ll1.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					if(slide_me.isClosed()==false)
					{
						//Toast.makeText(instance.getBaseContext(), "menu:"+v.getId(), Toast.LENGTH_SHORT).show();
						if(v.getId()==0)
						{
							instance.finish();
							instance.startActivity(new Intent(instance.getBaseContext(), StartingPage.class));
							instance.overridePendingTransition( R.anim.animation1, R.anim.animation2 );
						}
						else if(v.getId()==1)
						{
							instance.finish();
							instance.startActivity(new Intent(instance.getBaseContext(), UserProfile.class));
							instance.overridePendingTransition( R.anim.animation1, R.anim.animation2 );
						}
					}
				}
			});
			ImageView iv = new ImageView(instance);
			LinearLayout.LayoutParams iv1 = new LinearLayout.LayoutParams(ShareData.getScreenWidth(instance)/8, 
											ShareData.getScreenWidth(instance)/8);
			iv1.setMargins(15, 10, 10, 10);
			iv.setLayoutParams(iv1);
		
			TextView tv = new TextView(instance);
			tv.setTextSize(ShareData.ConvertFromDp(instance,28));
			if(a==0)
			{
				tv.setText("Main Menu");
				iv.setImageResource(R.drawable.menu1);
			}
			else if(a==1)
			{
				tv.setText("My Account");
				iv.setImageResource(R.drawable.myaccount);
			}
			
			tv.setTextColor(Color.BLACK);
			tv.setGravity(Gravity.FILL_VERTICAL);
			LinearLayout.LayoutParams tv1 = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT);
			tv.setLayoutParams(tv1);
			
	
			View v = new View(instance);
			v.setBackgroundColor(Color.parseColor("#dbdbdb"));
			LinearLayout.LayoutParams v1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 3);
		
			ll1.addView(iv);
			ll1.addView(tv);
		
			mainLayout.addView(ll1);
			mainLayout.addView(v,v1);

			mainLayout.post(new Runnable() 
			{
				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					height= ll1.getHeight();
					//Log.d("AddMenu", "height:"+height);
				}
			});

			
		}
		
		h1=height;
		
		for(int t=0; t<5; t++)
		{
		
			LinearLayout ll2 = new LinearLayout(instance);
			LinearLayout.LayoutParams llM2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			ll2.setBackgroundColor(Color.parseColor("#ededed"));
			ll2.setOrientation(LinearLayout.VERTICAL);
			ll2.setLayoutParams(llM2);
			ll2.setId(t);
			ll2.setOnClickListener(new View.OnClickListener() 
			{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				if(slide_me.isClosed()==false)
				{
					menuFlag = true;
					if(v.getId()==1)
					{
						//movie
						slide_me.toggleLeftDrawer();
						instance.finish();
						CategoryLanding.catagoryName = "movies";
						instance.startActivity(new Intent(instance.getBaseContext(), CategoryLanding.class));
						instance.overridePendingTransition( R.anim.animation1, R.anim.animation2 );
					}
					else if(v.getId()==2)
					{
						//tv
						slide_me.toggleLeftDrawer();
						instance.finish();
						CategoryLanding.catagoryName = "tv";
						instance.startActivity(new Intent(instance.getBaseContext(), CategoryLanding.class));
						instance.overridePendingTransition( R.anim.animation1, R.anim.animation2 );
					}
					else if(v.getId()==3)
					{
						slide_me.toggleLeftDrawer();
						instance.finish();
						CategoryLanding.catagoryName = "music";
						instance.startActivity(new Intent(instance.getBaseContext(), CategoryLanding.class));
						instance.overridePendingTransition( R.anim.animation1, R.anim.animation2 );
					}
					else if(v.getId()==4)
					{
						//people
						slide_me.toggleLeftDrawer();
						instance.finish();
						instance.startActivity(new Intent(instance.getBaseContext(), People.class));
						instance.overridePendingTransition( R.anim.animation1, R.anim.animation2 );
					}
//					else if(v.getId()==5)
//					{
//						//subscribe
//						slide_me.toggleLeftDrawer();
//						instance.finish();
//						instance.startActivity(new Intent(instance.getBaseContext(), Subscribe.class));
//						instance.overridePendingTransition( R.anim.animation1, R.anim.animation2 );
//					}					

				}
			}
		});
		
		TextView tv1 = new TextView(instance);
		tv1.setTextSize(ShareData.ConvertFromDp(instance,28));
		tv1.setTextColor(Color.BLACK);
		tv1.setGravity(Gravity.FILL_VERTICAL);
		if(t==0)
		{
			tv1.setText("CATEGORIES");
			tv1.setTextColor(Color.RED);
		}
		else if(t==1)
		{
			tv1.setText("Movies");
		}
		else if(t==2)
		{
			tv1.setText("Tv");
		}
		else if(t==3)
		{
			tv1.setText("Music");
		}
		else if(t==4)
		{
			tv1.setText("People");
		}
//		else if(t==5)
//		{
//			tv1.setText("Subscription");
//		}
	
		
		tv1.setPadding(20, 20, 0, 20);
		LinearLayout.LayoutParams tv2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,8);
		tv1.setLayoutParams(tv2);
		
		View v3 = new View(instance);
		v3.setBackgroundColor(Color.parseColor("#dbdbdb"));
		LinearLayout.LayoutParams vL3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 2);
		v3.setLayoutParams(vL3);
		
		ll2.addView(tv1);
		ll2.addView(v3);
		mainLayout.addView(ll2);
		}
		
		userBanner(instance);
	}
	
	
	public static void userBanner(final Activity instance)
    {
    	tvName.setTextSize(ShareData.ConvertFromDp(instance,34));
		
    	String pics = ShareData.getSavedString(instance, "pic");
    	if(ShareData.getSavedString(instance, "pic").length()!=0)
		{
			//userImageLoader.DisplayImage(ShareData.getSavedString(instance, "pic"), loginPic);
			new LoadImageFromURL(loginPic).execute(pics);
		}
		
		if(!(ShareData.getSavedString(instance, "name").length()==0))
		{
			//tvName.setText(""+ShareData.getSavedString(this, "name"));
			tvName.setText("Logout");
			tvName.setTypeface(null,Typeface.BOLD);
			tvName.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					if(slide_me.isClosed()==false)
					{
						LoginActivity.LogOut(instance);
						
//9						FacebookApp.logoutFromFacebook(instance);
						
//						slide_me.toggleLeftDrawer();
						instance.finish();
						if(pageNumber == 1)
						{
							instance.startActivity(new Intent(instance.getBaseContext(), CategoryLanding.class));
						}
						else if(pageNumber == 2)
						{
							instance.startActivity(new Intent(instance.getBaseContext(), People.class));
						}
						else if(pageNumber == 3)
						{
							instance.startActivity(new Intent(instance.getBaseContext(), Subscribe.class));
						}
						else if(AddMenu.pageNumber == 4)
						{
							instance.startActivity(new Intent(instance.getBaseContext(), SingleMoviePage.class));
						}
						else if(AddMenu.pageNumber == 5)
						{
							instance.startActivity(new Intent(instance.getBaseContext(), Movies.class));
						}
						else if(AddMenu.pageNumber == 6)
						{
							instance.startActivity(new Intent(instance.getBaseContext(), ActorProfile.class));
						}
						else if(AddMenu.pageNumber == 7)
						{
							instance.startActivity(new Intent(instance.getBaseContext(), MovieSummary.class));
						}
						instance.overridePendingTransition( R.anim.animation1, R.anim.animation2 );
					}
				}
			});
		}
		else
		{
			tvName.setText("Login");
			tvName.setTypeface(null,Typeface.BOLD);
			tvName.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					if(slide_me.isClosed()==false)
					{
						instance.finish();
						instance.startActivity(new Intent(instance.getBaseContext(), LoginActivity.class));
						instance.overridePendingTransition( R.anim.animation1, R.anim.animation2 );
					}
				}
			});
		}
    }
	
}
