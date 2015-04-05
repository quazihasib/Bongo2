package com.movies.people;

import com.tab.ShareData;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Slider 
{
	public static Handler peopleHandler = new Handler();
	public static Runnable peopleRunnable;

//	public static void listViewOnClickListener() 
//	{
//		People.listview1.setOnTouchListener(new View.OnTouchListener() 
//		{
//			// outer scroll listener
//			private double downX, downY, moveX, moveY, down, move;
//			private boolean started = false;
//			public int a = 0;
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) 
//			{
//				switch (event.getAction()) 
//				{
//				case MotionEvent.ACTION_MOVE:
//					// if (started)
//					// {
//					// moveX = event.getX();
//					// moveY = event.getY();
//					// }
//					// else
//					// {
//					// started = true;
//					// }
//					moveX = event.getX();
//					moveY = event.getY();
//
//					Log.d("Slider", "move");
//					a++;
//					if(a == 1) 
//					{
//						move = ShareData.dist(event.getX(), event.getY());
//						if (People.counter1 < 0)
//						{
//							People.counter1 = 0;
//						}
//
//						if (down < move)
//						{
//							Log.d("Slider", "ACTION_RIGHT:");
//							People.counter1 = People.counter1 - People.widthScreen;
//							People.listview1.smoothScrollTo(People.counter1, 0);
//						}
//						else if (down > move)
//						{
//							Log.d("Slider", "ACTION_LEFT:");
//
//							// Log.d("Slider",
//							// "counters1:"+People.counter1);
//							if (!(People.counter1 >= People.widthScreen* (People.images.length - 2))) 
//							{
//								Log.d("Slider", "counters2:"+ People.counter1);
//								People.counter1 = People.counter1+ People.widthScreen;
//								People.listview1.smoothScrollTo(People.counter1, 0);
//							}
//
//						}
//						People.flag = true;
//
//						final Handler handler = new Handler();
//						handler.postDelayed(new Runnable() 
//						{
//							@Override
//							public void run() 
//							{
//								// Do something after 5s = 5000ms
//								People.flag = false;
//							}
//						}, 2000);
//					}
//					break;
//
//				case MotionEvent.ACTION_DOWN:
//
//					downX = event.getX();
//					downY = event.getY();
//					down = ShareData.dist(event.getX(), event.getY());
//					a = 0;
//					// started = false;
//					Log.d("Slider", "down");
//					People.flag = true;
//
//					break;
//
//				case MotionEvent.ACTION_UP:
//					// started = false;
//					Log.d("Slider", "up");
//					a = 0;
//					// if(People.counter1<0)
//					// {
//					// People.counter1 = 0;
//					// }
//
//					// Log.d("Slider", "moveY:"+moveY);
//					// Log.d("Slider", "downY:"+downY);
//
//					// if(downX<moveX)
//					// {
//					// Log.d("Slider", "ACTION_RIGHT:");
//					// People.counter1 =People.counter1-
//					// People.widthScreen;
//					// People.listview1.smoothScrollTo(People.counter1,
//					// 0);
//					// }
//					// else if(downX>moveX)
//					// {
//					// Log.d("Slider", "ACTION_LEFT:");
//					//
//					// // Log.d("Slider",
//					// "counters1:"+People.counter1);
//					// if(!(People.counter1>=People.widthScreen*(People.images.length-2)))
//					// {
//					// Log.d("Slider", "counters2:"+People.counter1);
//					// People.counter1 =People.counter1+
//					// People.widthScreen;
//					// People.listview1.smoothScrollTo(People.counter1,
//					// 0);
//					// }
//					//
//					// }
//					// Log.d("Slider", "counters3:"+People.counter1);
//					// final Handler handler = new Handler();
//					// handler.postDelayed(new Runnable()
//					// {
//					// @Override
//					// public void run()
//					// {
//					// // Do something after 5s = 5000ms
//					// People.flag=false;
//					// }
//					// }, 1500);
//					break;
//				}
//
//				return true;
//			}
//		});
//	}
//
//	public static void startSlider()
//	{
//		People.startSliders = true;
//		peopleRunnable = new Runnable() 
//		{
//			@Override
//			public void run() 
//			{
//				if (People.flag == false)
//				{
//					People.counter1 = People.counter1 + People.widthScreen;
//					// Log.d("Slider", "counter:"+counter);
//					if (People.images != null) 
//					{
//						if (People.counter1 > (People.widthScreen * (People.images.length - 2)))
//						{
//							People.counter1 = 0;
//						}
//					}
//					People.listview1.smoothScrollTo(People.counter1, 0);
//				}
//				/* and here comes the "trick" */
//				peopleHandler.postDelayed(this, 4000);
//			}
//		};
//
//		peopleHandler.postDelayed(peopleRunnable, 4000);
//	}
	
}
