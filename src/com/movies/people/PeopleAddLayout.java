package com.movies.people;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movies.actorProfile.ActorProfile;
import com.movies.bongobd.R;
import com.movies.browseAll.Movies;
import com.movies.categoryPage.CategoryLanding;
import com.movies.startingPage.StartingPage;
import com.tab.ShareData;

public class PeopleAddLayout
{
	public static int peopleImageCount = 0 , imgCounter = 0;
	public static String DEBUG_TAG = PeopleAddLayout.class.getSimpleName();
	
	public static void addLayouts(int a) 
	{
		imgCounter++;
		
		View vb = new View(People.peopleInstance);
		vb.setBackgroundColor(Color.WHITE);
		LinearLayout.LayoutParams vLb= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,ShareData.getScreenWidth(People.peopleInstance)/16); 
	
		TextView title = new TextView(People.peopleInstance);
		title.setTextColor(Color.RED);
//		title.setTypeface(null, Typeface.BOLD);
		LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1);
		titleParams.setMargins(10, 0, 0, 0);
		Log.d("PeopleAddLayout", "People.carasolLoopCounter:"+People.carasolLoopCounter);
		title.setTextSize(ShareData.ConvertFromDp(People.peopleInstance,28));
		try 
		{
			title.setText("" + People.artistNames[People.carasolLoopCounter-1]);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Browse All text
		final TextView title1 = new TextView(People.peopleInstance);
		LinearLayout.LayoutParams titleParams1 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1);
		title1.setTextColor(Color.BLACK);
		titleParams1.setMargins(0, 0, 10, 0);
		title1.setText("Browse All");
		title1.setTextSize(ShareData.ConvertFromDp(People.peopleInstance,28));
		try 
		{
			title1.setTag(People.ids[People.carasolLoopCounter-1]);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		People.scrollView[People.carasolLoopCounter] = new HorizontalScrollView(People.peopleInstance);
		People.scrollView[People.carasolLoopCounter].setVerticalScrollBarEnabled(false);
		People.scrollView[People.carasolLoopCounter].setHorizontalScrollBarEnabled(false);
		People.scrollView[People.carasolLoopCounter].setId(13);
	    
		LinearLayout topLinearLayout = new LinearLayout(People.peopleInstance);
		LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
			
			
		RelativeLayout.LayoutParams params = 
			    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
			        RelativeLayout.LayoutParams.WRAP_CONTENT);
			
		LinearLayout topLinearLayout1 = new LinearLayout(People.peopleInstance);
		LinearLayout.LayoutParams tParams1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout1.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout topLinearLayout2 = new LinearLayout(People.peopleInstance);
		LinearLayout.LayoutParams tParams2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout2.setOrientation(LinearLayout.HORIZONTAL);
		topLinearLayout2.setPadding(0, 0, 0, 15);
		
		LinearLayout topLinearLayout3 = new LinearLayout(People.peopleInstance);
		LinearLayout.LayoutParams tParams3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout3.setOrientation(LinearLayout.VERTICAL);
			
		//Log.d("PeopleAddLayout", "PeopleCarasol.images:"+PeopleCarasol.images.length);
		final int b=People.carasolLoopCounter;
		Log.d("Add", "rows:"+b);
		peopleImageCount = 1;
		try
		{
			for(peopleImageCount = 1; peopleImageCount<PeopleCarasol.images.length; peopleImageCount++)
			{
				final ImageView[][] im =  new ImageView[PeopleCarasol.images.length+3][PeopleCarasol.images.length+3];
				im[b][peopleImageCount] = new ImageView(People.peopleInstance);
				im[b][peopleImageCount].setImageResource(R.drawable.ic);
				im[b][peopleImageCount].setScaleType(ScaleType.FIT_XY);
				People.imageLoader.DisplayImage(PeopleCarasol.images[peopleImageCount-1], im[b][peopleImageCount]);
				RelativeLayout.LayoutParams imParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				imParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
				imParams.width = ShareData.getScreenWidth(People.peopleInstance)/3;
				imParams.height = (ShareData.getScreenWidth(People.peopleInstance)/3)-(ShareData.getScreenWidth(People.peopleInstance)/9);
				im[b][peopleImageCount].setTag(peopleImageCount);
					
				final TextView tv = new TextView(People.peopleInstance);
				//tv.setText(""+People.movieLength[i]);
				tv.setTextColor(Color.WHITE);
				tv.setBackgroundColor(Color.BLACK);
				RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				tvParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
				tvParams.setMargins(10, 90, 0, 0);
				tv.setTextSize(ShareData.ConvertFromDp(People.peopleInstance,28));
						
				View v = new View(People.peopleInstance);
				v.setBackgroundColor(Color.WHITE);
				RelativeLayout.LayoutParams vL= new RelativeLayout.LayoutParams(5,10); 
				vL.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
						 
				//if there is no image data then do not attach
				if(People.errorCheck == 0)
				{
					final RelativeLayout rl = new RelativeLayout(People.peopleInstance);
					rl.addView(im[b][peopleImageCount], imParams);
					rl.addView(tv, tvParams);
					topLinearLayout.addView(rl,params);
					topLinearLayout.addView(v, vL);
				}
				
				im[b][peopleImageCount].setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub

						String a = ""+v.getTag();
						int c = Integer.parseInt(a);
						//Log.d("DEBUG_TAG", "b: "+b);
						//Log.d("DEBUG_TAG", "c: "+c);
						StartingPage.ACTOR_ID = People.peopleIds[b][c];
						Log.d("DEBUG_TAG", "people id: "+StartingPage.ACTOR_ID);

						People.peopleInstance.finish();
						People.peopleInstance.startActivity(new Intent
								(People.peopleInstance.getBaseContext(), ActorProfile.class));
						People.peopleInstance.overridePendingTransition
						(R.anim.animation1, R.anim.animation2);						
		
					}
				});
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		People.scrollView[People.carasolLoopCounter].addView(topLinearLayout, tParams);
			
		//if there is no image data then do not attach
		if(People.errorCheck == 0)
		{
			title.setGravity(Gravity.LEFT);
			title.setTextSize(TypedValue.COMPLEX_UNIT_PX,(int)People.peopleInstance.getResources().getDimension(R.dimen.text_size1));
			topLinearLayout2.addView(title, titleParams);
			title1.setGravity(Gravity.RIGHT);
			title1.setTextSize(TypedValue.COMPLEX_UNIT_PX,(int)People.peopleInstance.getResources().getDimension(R.dimen.text_size1));
			topLinearLayout2.addView(title1, titleParams1);
			topLinearLayout3.addView(vb, vLb);
			topLinearLayout1.addView(topLinearLayout3, tParams3);
			topLinearLayout1.addView(topLinearLayout2, tParams2);
			
			title1.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					People.categoryID =  Integer.parseInt(title1.getTag().toString());
					//CategoryLanding.categoryID = People.categoryID;
					Log.d("Tag","People.catagoryID: "+People.categoryID);
					
					People.peopleInstance.finish();
					People.peopleInstance.startActivity(new Intent(People.peopleInstance.getBaseContext(), Movies.class));
					People.peopleInstance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
				}
			});
		}
//////
		
		RelativeLayout rLayout = new RelativeLayout(People.peopleInstance);
		RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		ViewTreeObserver vto = People.scrollView[imgCounter].getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		    @Override
		    public void onGlobalLayout() {
		    	People.scrollView[imgCounter].getViewTreeObserver().removeGlobalOnLayoutListener(this);
		    	People.maxScroll[imgCounter] = People.scrollView[imgCounter].getChildAt(0)
		                .getMeasuredWidth()-People.peopleInstance.getWindowManager().getDefaultDisplay().getWidth();
		        //Log.d(DEBUG_TAG, "maxScroll:"+imgCounter+"::"+People.maxScroll[imgCounter]);

		    }
		});
		
		
		int width = (ShareData.getScreenWidth(People.peopleInstance)/3)/4;
		int height = (ShareData.getScreenWidth(People.peopleInstance)/3)/4;
		
		People.ivL[imgCounter] = new ImageView(People.peopleInstance);
		RelativeLayout.LayoutParams ivLParams = new RelativeLayout.LayoutParams(width, height);
		People.ivL[imgCounter].setImageResource(R.drawable.sidel1);
		People.ivL[imgCounter].setVisibility(View.INVISIBLE);
		ivLParams.setMargins(0, 45, 0, 0);
		
		People.ivR[imgCounter] = new ImageView(People.peopleInstance);
		RelativeLayout.LayoutParams ivRParams = new RelativeLayout.LayoutParams(width, height);
		People.ivR[imgCounter].setImageResource(R.drawable.sider1);
		ivRParams.setMargins(0, 45, 0, 0);
		
		
		rLayout.addView(People.scrollView[People.carasolLoopCounter]);
		rLayout.addView(People.ivL[imgCounter], ivLParams);
		rLayout.addView(People.ivR[imgCounter], ivRParams);
		
		People.scrollView[People.carasolLoopCounter].setId(imgCounter);
		People.scrollView[People.carasolLoopCounter].setOnTouchListener(new OnTouchListener() 
		{           
		    @Override
		    public boolean onTouch(View v, MotionEvent event)
		    {
		    	if(People.scrollView[v.getId()].getScrollX()==0) 
		    	{
		    		//Log.d(DEBUG_TAG, "scroller:"+v.getId()+" hide left");
		    		People.ivL[v.getId()].setVisibility(View.INVISIBLE);
		    		if(People.ivR[v.getId()].getVisibility()==View.INVISIBLE)
		    		{
		    			People.ivR[v.getId()].setVisibility(View.VISIBLE);
		    		}
		    	}
		    	else if(People.scrollView[v.getId()].getScrollX() >= People.maxScroll[v.getId()] )
		    	{
		    		//Log.d(DEBUG_TAG, "scroller:"+v.getId()+" hide right");
		    		People.ivR[v.getId()].setVisibility(View.INVISIBLE);
		    		if(People.ivL[v.getId()].getVisibility()==View.INVISIBLE)
		    		{
		    			People.ivL[v.getId()].setVisibility(View.VISIBLE);
		    		}
		    	}
		    	else
		    	{
		    		if(People.ivL[v.getId()].getVisibility()==View.INVISIBLE)
		    		{
		    			People.ivL[v.getId()].setVisibility(View.VISIBLE);
		    		}
		    		
		    		if(People.ivR[v.getId()].getVisibility()==View.INVISIBLE)
		    		{
		    			People.ivR[v.getId()].setVisibility(View.VISIBLE);
		    		}
		    	}
			return false;
		    }
		});
		
		ivLParams.addRule(RelativeLayout.ALIGN_LEFT, imgCounter);
		ivRParams.addRule(RelativeLayout.ALIGN_RIGHT, imgCounter);
//		
//		
//////
		topLinearLayout1.addView(rLayout, rLayoutParams);
		
		People.mainLayout.addView(topLinearLayout1, tParams1);
		
		if(People.carasolLoopCounter<a-1)
		{
			People.count++;
			try 
			{
				PeopleCarasol.makeJsonObjectRequestForCarasols("http://dev.bongobd.com/api/people_sliders.php?slider_id="+People.ids[People.count],a);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Log.d("Carasol", "People.pDialog.dismiss():::::::::::::::::");
//			People.pDialog.dismiss();
			Log.d("Carasol", "People.startSlider:"+ People.startSliders);
			if(People.startSliders==false)
			{
				Log.d("Carasol", "Yes, add now");
//				Slider.startSlider();
//				Slider.listViewOnClickListener();
			}
		}
	}
}
