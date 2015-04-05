package com.tab;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.movies.bongobd.R;
import com.movies.categoryPage.CategoryLanding;
import com.movies.startingPage.StartingPage;

public class Header extends LinearLayout 
{
	public static final String TAG = Header.class.getSimpleName();

	public TextView tv1, tv2, tv3, tv4, tv5;
	public ImageView menuIcon, bongoIcon, searchIcon;
	public Activity Instance;
	public LinearLayout headerLayout;
	public HorizontalScrollView hsv;
	public LinearLayout.LayoutParams menuLayout,bongoLayout,searchLayout;
	public ImageView sideLeft, sideRight;
	public static int mainMaxScroll;
	
	public static String DEBUG_TAG = Header.class.getSimpleName();
	 
	public Header(Context context) 
	{
		super(context);
	}

	public Header(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public Header(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs);
	}

	public void initHeader(Activity instance) 
	{
		inflateHeader(instance);
	}

	private void inflateHeader(final Activity instance)
	{
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header, this);
		
		int width = ShareData.getScreenWidth(instance)/3;
		int height = (int) (width/2.5);
		LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(width, height);
		
		headerLayout = (LinearLayout) findViewById(R.id.headerLayout);
		
		menuIcon = new ImageView(instance);
//		menuIcon.setScaleType(ScaleType.CENTER_INSIDE);
		menuIcon.setImageResource(R.drawable.menu);
		menuLayout = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		
		bongoIcon = new ImageView(instance);
//		bongoIcon.setScaleType(ScaleType.CENTER);
//		bongoIcon.setScaleType(ScaleType.CENTER_INSIDE);
		bongoIcon.setImageResource(R.drawable.logo);
		bongoLayout = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 5);
	
		searchIcon = new ImageView(instance);
		searchIcon.setImageResource(R.drawable.search);
		searchLayout = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
		
		bongoLayout.setMargins(40, 20, 40, 20);
		menuLayout.setMargins(30, 20, 30, 20);
		searchLayout.setMargins(20, 20, 20, 20);
		
		float textSize = ShareData.SetTextSize("text", (int) (ShareData.getScreenWidth(instance) * 0.1), (int) ( (ShareData.getScreenHeight(instance) * 0.15)));
		
		tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)getResources().getDimension(R.dimen.text_size1));
		tv1.setText("Movies");
		tv1.setLayoutParams(tv1Params);
		
		tv2 = (TextView) findViewById(R.id.tv2);
		tv2.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)getResources().getDimension(R.dimen.text_size1));
		tv2.setText("Tv");
		tv2.setLayoutParams(tv1Params);
		
		tv3 = (TextView) findViewById(R.id.tv3);
		tv3.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)getResources().getDimension(R.dimen.text_size1));
		tv3.setText("Music");
		tv3.setLayoutParams(tv1Params);
		
		tv4 = (TextView) findViewById(R.id.tv4);
		tv4.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)getResources().getDimension(R.dimen.text_size1));
		tv4.setText("People");
		tv4.setLayoutParams(tv1Params);
		
//		tv5 = (TextView) findViewById(R.id.tv5);
//		tv5.setText("SUBSCRIBE");
//		tv5.setLayoutParams(tv1Params);
		
		headerLayout.addView(menuIcon, menuLayout);
		headerLayout.addView(bongoIcon, bongoLayout);
		headerLayout.addView(searchIcon, searchLayout);
		
		hsv = (HorizontalScrollView)findViewById(R.id.hsv);
		
		sideLeft = (ImageView)findViewById(R.id.sideLeft);
		sideLeft.getLayoutParams().width = width/3;
		sideLeft.getLayoutParams().height = (int)(height/1.5);
		sideLeft.setPadding(0, 25, 0, 0);
		sideLeft.setVisibility(View.INVISIBLE);
		
		sideRight = (ImageView)findViewById(R.id.sideRight);
		sideRight.getLayoutParams().width = width/3;
		sideRight.getLayoutParams().height = (int)(height/1.5);
		sideRight.setPadding(0, 25, 0, 0);
		
		ViewTreeObserver vto = hsv.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() 
		{
		    @Override
		    public void onGlobalLayout() 
		    {
		    	hsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		        mainMaxScroll = hsv.getChildAt(0).getMeasuredWidth()-
		        		instance.getWindowManager().getDefaultDisplay().getWidth();
		        //Log.d(DEBUG_TAG, "mainMaxScroll:"+mainMaxScroll);

		    }
		});
		
	}
}