package com.movies.categoryPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movies.bongobd.R;
import com.movies.browseAll.ListViewAdapter;
import com.movies.browseAll.Movies;
import com.movies.singleMovie.SingleMoviePage;
import com.tab.ShareData;

public class CategoryAddLayout
{
	public static int sliderImageCount = 0, imgCounter = 0;
	public static String DEBUG_TAG = CategoryAddLayout.class.getSimpleName();
	
	public static void addLayouts(int a) 
	{
		CategoryLanding.carasolCounter++;
		imgCounter++;
//		View vb = new View(CategoryLanding.categoryInstance);
//		vb.setBackgroundColor(Color.WHITE); 
//		LinearLayout.LayoutParams vLb= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,ShareData.getScreenWidth(CategoryLanding.categoryInstance)/16); 
		
		TextView title = new TextView(CategoryLanding.categoryInstance);
		title.setTextColor(Color.RED);
//		title.setTypeface(null, Typeface.BOLD);
		LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1);
		//Log.d("PeopleAddLayout", "People.carasolLoopCounter:"+CategoryLanding.carasolLoopCounter);
		//title.setTextSize(ShareData.ConvertFromDp(CategoryLanding.categoryInstance,22));
		titleParams.setMargins(10, 0, 0, 0);
		try 
		{
			title.setText("" + CategoryLanding.catagoryNames[CategoryLanding.carasolLoopCounter-1]);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//Browse All text
		final TextView title1 = new TextView(CategoryLanding.categoryInstance);
		LinearLayout.LayoutParams titleParams1 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,1);
		titleParams1.setMargins(0, 0, 10, 0);
		title1.setTextColor(Color.BLACK);
		title1.setText("Browse All");
//		title1.setTextSize((int)CategoryLanding.categoryInstance.getResources().getDimension(R.dimen.text_size1));
		try 
		{
			title1.setTag(CategoryLanding.ids[CategoryLanding.carasolLoopCounter-1]);
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    CategoryLanding.scrollView[CategoryLanding.carasolCounter] = new HorizontalScrollView(CategoryLanding.categoryInstance);
	    CategoryLanding.scrollView[CategoryLanding.carasolCounter].setVerticalScrollBarEnabled(false);
	    CategoryLanding.scrollView[CategoryLanding.carasolCounter].setHorizontalScrollBarEnabled(false);
	    CategoryLanding.scrollView[CategoryLanding.carasolCounter].setId(13);
	
		
		LinearLayout topLinearLayout = new LinearLayout(CategoryLanding.categoryInstance);
		LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
			
			
		RelativeLayout.LayoutParams params = 
			    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
			        RelativeLayout.LayoutParams.WRAP_CONTENT);
			
		LinearLayout topLinearLayout1 = new LinearLayout(CategoryLanding.categoryInstance);
		LinearLayout.LayoutParams tParams1 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout1.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout topLinearLayout2 = new LinearLayout(CategoryLanding.categoryInstance);
		LinearLayout.LayoutParams tParams2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout2.setOrientation(LinearLayout.HORIZONTAL);
		topLinearLayout2.setPadding(0, 0, 0, 15);
		
		LinearLayout topLinearLayout3 = new LinearLayout(CategoryLanding.categoryInstance);
		LinearLayout.LayoutParams tParams3 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		topLinearLayout3.setOrientation(LinearLayout.VERTICAL);
			
		//Log.d("PeopleAddLayout", "PeopleCarasol.images:"+PeopleCarasol.images.length);
		
		final int b=CategoryLanding.carasolCounter;
		Log.d("Add", "rows:"+b);
		sliderImageCount=0;
		try 
		{
			for(sliderImageCount = 1; sliderImageCount<CategoryCarasol.images.length; sliderImageCount++)
			{
				final ImageView[][] im= new ImageView[CategoryCarasol.images.length+3][CategoryCarasol.images.length+3];
				im[b][sliderImageCount] = new ImageView(CategoryLanding.categoryInstance);
				im[b][sliderImageCount].setImageResource(R.drawable.ic);
				im[b][sliderImageCount].setScaleType(ScaleType.FIT_XY);
				CategoryLanding.imageLoader.DisplayImage(CategoryCarasol.images[sliderImageCount], im[b][sliderImageCount]);
				RelativeLayout.LayoutParams imParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				imParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
				imParams.width = ShareData.getScreenWidth(CategoryLanding.categoryInstance)/3;
				imParams.height = (ShareData.getScreenWidth(CategoryLanding.categoryInstance)/3)-(ShareData.getScreenWidth(CategoryLanding.categoryInstance)/9);
				im[b][sliderImageCount].setTag(sliderImageCount);
					
				final TextView[][] tv =  new TextView[CategoryCarasol.titles.length+3][CategoryCarasol.titles.length+3];
				tv[b][sliderImageCount] = new TextView(CategoryLanding.categoryInstance);
				tv[b][sliderImageCount].setText(""+CategoryCarasol.titles[sliderImageCount]);
				tv[b][sliderImageCount].setTextColor(Color.WHITE);
				//tv[b][sliderImageCount].setBackgroundColor(Color.BLACK);
				RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				tvParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
				if(CategoryCarasol.directors[sliderImageCount].trim().length()!=0)
				{
					tv[b][sliderImageCount].append("\n"+CategoryCarasol.directors[sliderImageCount]);
					tvParams.setMargins(10, 90, 0, 0);
				}
				else
				{
					tvParams.setMargins(10, 120, 0, 0);
				}
				 
				tv[b][sliderImageCount].setTextSize(ShareData.ConvertFromDp(CategoryLanding.categoryInstance,22));
					
				View v = new View(CategoryLanding.categoryInstance);
				v.setBackgroundColor(Color.WHITE);
				RelativeLayout.LayoutParams vL= new RelativeLayout.LayoutParams(5,10); 
				vL.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
						 
					
				//if there is no image data then do not attach
				if(CategoryLanding.errorCheck == 0)
				{
					final RelativeLayout rl = new RelativeLayout(CategoryLanding.categoryInstance);
					rl.addView(im[b][sliderImageCount], imParams);
					rl.addView(tv[b][sliderImageCount], tvParams);
					topLinearLayout.addView(rl,params);
					topLinearLayout.addView(v, vL);
				}
				
				im[b][sliderImageCount].setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						// Log.e("Tag"," "+imageView[ii].getTag());
						String a = ""+v.getTag();
						int c = Integer.parseInt(a);
						//Log.d("DEBUG_TAG", "b: "+b);
						//Log.d("DEBUG_TAG", "c: "+c);
						ListViewAdapter.singleMovieId = CategoryLanding.movieIds[b][c];
						Log.d("DEBUG_TAG", "CategoryLanding.val:"+CategoryLanding.val);
						
						CategoryLanding.categoryInstance.finish();
						CategoryLanding.categoryInstance.startActivity(new Intent
							(CategoryLanding.categoryInstance.getBaseContext(), SingleMoviePage.class));
						CategoryLanding.categoryInstance.overridePendingTransition
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
		
		
		CategoryLanding.scrollView[CategoryLanding.carasolCounter].addView(topLinearLayout, tParams);
		
		//if there is no image data then do not attach
		if(CategoryLanding.errorCheck == 0)
		{
			title.setGravity(Gravity.LEFT);
			title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)CategoryLanding.categoryInstance.getResources().getDimension(R.dimen.text_size1));
			topLinearLayout2.addView(title, titleParams);
			title1.setGravity(Gravity.RIGHT);
			title1.setTextSize(TypedValue.COMPLEX_UNIT_PX,(int)CategoryLanding.categoryInstance.getResources().getDimension(R.dimen.text_size1));
			topLinearLayout2.addView(title1, titleParams1);
			//topLinearLayout3.addView(vb, vLb);
			topLinearLayout1.addView(topLinearLayout3, tParams3);
			topLinearLayout1.addView(topLinearLayout2, tParams2);
			
			//go to Browse All Page
			title1.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					
					CategoryLanding.categoryID =  Integer.parseInt(title1.getTag().toString());
					Log.d("Tag","People.catagoryID: "+CategoryLanding.categoryID);
				
					CategoryLanding.categoryInstance.finish();
					CategoryLanding.categoryInstance.startActivity(new Intent(CategoryLanding.categoryInstance.getBaseContext(), Movies.class));
					CategoryLanding.categoryInstance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
				}
			});
		}
		
		RelativeLayout rLayout = new RelativeLayout(CategoryLanding.categoryInstance);
		RelativeLayout.LayoutParams rLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		ViewTreeObserver vto = CategoryLanding.scrollView[CategoryLanding.carasolCounter].getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		    @Override
		    public void onGlobalLayout() {
		    	CategoryLanding.scrollView[CategoryLanding.carasolCounter].getViewTreeObserver().removeGlobalOnLayoutListener(this);
		        CategoryLanding.maxScroll[imgCounter] = CategoryLanding.scrollView[CategoryLanding.carasolCounter].getChildAt(0)
		                .getMeasuredWidth()-CategoryLanding.categoryInstance.getWindowManager().getDefaultDisplay().getWidth();
		        //Log.d(DEBUG_TAG, "maxScroll:"+imgCounter+"::"+CategoryLanding.maxScroll[imgCounter]);

		    }
		});
		
		int width = (ShareData.getScreenWidth(CategoryLanding.categoryInstance)/3)/4;
		int height = (ShareData.getScreenWidth(CategoryLanding.categoryInstance)/3)/4;
		
		CategoryLanding.ivL[CategoryLanding.carasolCounter] = new ImageView(CategoryLanding.categoryInstance);
		RelativeLayout.LayoutParams ivLParams = new RelativeLayout.LayoutParams(width, height);
		CategoryLanding.ivL[CategoryLanding.carasolCounter].setImageResource(R.drawable.sidel1);
		CategoryLanding.ivL[CategoryLanding.carasolCounter].setVisibility(View.INVISIBLE);
		ivLParams.setMargins(0, 45, 0, 0);
		
		
		CategoryLanding.ivR[CategoryLanding.carasolCounter] = new ImageView(CategoryLanding.categoryInstance);
		RelativeLayout.LayoutParams ivRParams = new RelativeLayout.LayoutParams(width, height);
		CategoryLanding.ivR[CategoryLanding.carasolCounter].setImageResource(R.drawable.sider1);
		ivRParams.setMargins(0, 45, 0, 0);
		
		
		rLayout.addView(CategoryLanding.scrollView[CategoryLanding.carasolCounter]);
		rLayout.addView(CategoryLanding.ivL[CategoryLanding.carasolCounter], ivLParams);
		rLayout.addView(CategoryLanding.ivR[CategoryLanding.carasolCounter], ivRParams);
		
		CategoryLanding.scrollView[CategoryLanding.carasolCounter].setId(CategoryLanding.carasolCounter);
		CategoryLanding.scrollView[CategoryLanding.carasolCounter].setOnTouchListener(new OnTouchListener() {           
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		    	
//		    	for(int i=0; i<CategoryLanding.maxScroll.length;i++)
//		    	{
//		    		Log.d(DEBUG_TAG, "mx1111:"+CategoryLanding.maxScroll[i]);
//		    	}
		    	//Log.d(DEBUG_TAG, "id:"+v.getId());
		    	
		    	//Log.d(DEBUG_TAG, "xx:"+CategoryLanding.scrollView[CategoryLanding.carasolCounter].getScrollX());
		    
		    	if(CategoryLanding.scrollView[v.getId()].getScrollX()==0) 
		    	{
		    		//Log.d(DEBUG_TAG, "scroller:"+v.getId()+" hide left");
		    		
		    		CategoryLanding.ivL[v.getId()].setVisibility(View.INVISIBLE);
		    		if(CategoryLanding.ivR[v.getId()].getVisibility()==View.INVISIBLE)
		    		{
		    			CategoryLanding.ivR[v.getId()].setVisibility(View.VISIBLE);
		    		}
		    	}
		    	else if(CategoryLanding.scrollView[v.getId()].getScrollX() >= CategoryLanding.maxScroll[v.getId()] )
		    	{
		    		//Log.d(DEBUG_TAG, "scroller:"+v.getId()+" hide right");
		    		
		    		CategoryLanding.ivR[v.getId()].setVisibility(View.INVISIBLE);
		    		if(CategoryLanding.ivL[v.getId()].getVisibility()==View.INVISIBLE)
		    		{
		    			CategoryLanding.ivL[v.getId()].setVisibility(View.VISIBLE);
		    		}
		    	}
		    	else
		    	{
		    		if(CategoryLanding.ivL[v.getId()].getVisibility()==View.INVISIBLE)
		    		{
		    			CategoryLanding.ivL[v.getId()].setVisibility(View.VISIBLE);
		    		}
		    		
		    		if(CategoryLanding.ivR[v.getId()].getVisibility()==View.INVISIBLE)
		    		{
		    			CategoryLanding.ivR[v.getId()].setVisibility(View.VISIBLE);
		    		}
		    	}
			return false;
		    }
		});
		
		ivLParams.addRule(RelativeLayout.ALIGN_LEFT, CategoryLanding.carasolCounter);
		ivRParams.addRule(RelativeLayout.ALIGN_RIGHT, CategoryLanding.carasolCounter);
		
		topLinearLayout1.addView(rLayout, rLayoutParams);
		CategoryLanding.mainLayout.addView(topLinearLayout1, tParams1);
		
		if(CategoryLanding.carasolLoopCounter<a-1)
		{
			CategoryLanding.count++;
			try {
				CategoryCarasol.makeJsonObjectRequestForSlider("http://dev.bongobd.com/api/category.php?catID="+CategoryLanding.ids[CategoryLanding.count],a);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			//Log.d("Carasol", "People.pDialog.dismiss():::::::::::::::::");
//			People.pDialog.dismiss();
			//Log.d("Carasol", "People.startSlider:"+ CategoryLanding.startSliders);
			//if(CategoryLanding.startSliders==false)
			//{
				Log.d("Carasol", "Yes, add now");
//				CategorySlider.startSlider();
//				CategorySlider.listViewOnClickListener();
//				AddMenu.clickable=true;
		//	}
		}
	}
	
}
