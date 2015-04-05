package com.movies.categoryPage;

import com.tab.ShareData;

import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CategorySlider
{
	public static Handler handler = new Handler();
	public static Runnable runnable;
	
//	public static void listViewOnClickListener()
//	{
//		CategoryLanding.listview1.setOnTouchListener(new View.OnTouchListener() 
//		{ 
//			//outer scroll listener         
//	        private double downX, downY, moveX, moveY, down, move;
//	        private boolean started = false;
//	        public int a=0;
//	        
//	        @Override
//	        public boolean onTouch(View v, MotionEvent event)
//	        {
//	            switch (event.getAction()) 
//	            {
//	                case MotionEvent.ACTION_MOVE:
////	                    if (started)
////	                    {
////	                    	moveX = event.getX();
////	                    	moveY = event.getY();
////	                    }
////	                    else 
////	                    {
////	                        started = true;
////	                    }
//	                    moveX = event.getX();
//                    	moveY = event.getY();
//                    	
//                    	
//	                    Log.d("Slider", "move");
//	                    a++;
//	                    if(a==1)
//	                    {
//	                    	move = ShareData.dist(event.getX(), event.getY());
//	                    	 if(CategoryLanding.counter1<0)
//	 	                     {
//	 	                        	CategoryLanding.counter1 = 0;
//	 	                     }
//	                    	 
//	                    	 if(down<move)
//	       	                 {
//	       	    	            	Log.d("Slider", "ACTION_RIGHT:");
//	       	    	            	CategoryLanding.counter1 =CategoryLanding.counter1- CategoryLanding.widthScreen;
//	       	    	            	CategoryLanding.listview1.smoothScrollTo(CategoryLanding.counter1, 0); 
//	       	                 }
//	       	    	         else if(down>move)
//	       	    	         {
//	       	    	 	            Log.d("Slider", "ACTION_LEFT:");
//	       	    	            	
//	       	    	 	            // Log.d("Slider", "counters1:"+CategoryLanding.counter1);
//	       	    	            	if(!(CategoryLanding.counter1>=CategoryLanding.widthScreen*(CategoryLanding.images.length-2)))
//	       	    	            	{
//	       	    	            		 Log.d("Slider", "counters2:"+CategoryLanding.counter1);
//	       	    	            		CategoryLanding.counter1 =CategoryLanding.counter1+ CategoryLanding.widthScreen;
//	       	    	            		CategoryLanding.listview1.smoothScrollTo(CategoryLanding.counter1, 0); 
//	       	    	            	}
//	       	    	            	
//	       	    	         }
//	                    	 CategoryLanding.flag=true;
//	                    	 
//	                         final Handler handler = new Handler();
//	 	                     handler.postDelayed(new Runnable()
//	 	                     {
//	 	                        @Override
//	 	                        public void run() 
//	 	                        {
//	 	                            // Do something after 5s = 5000ms
//	 	                        	CategoryLanding.flag=false;
//	 	                        }
//	 	                    }, 2000);
//	                    } 
//	                    break;
//	                    
//	                case MotionEvent.ACTION_DOWN: 
//	                	
//	                	downX = event.getX();
//	                	downY = event.getY();
//	                	down = ShareData.dist(event.getX(), event.getY());
//	                	a=0;
//	                    //started = false;
//	                	Log.d("Slider", "down");
//	                    CategoryLanding.flag=true;
//	                    
//	                    break;
//	                    
//	                case MotionEvent.ACTION_UP: 
//	                    //started = false;
//	                	Log.d("Slider", "up");
//	                	a=0;
////	                    if(CategoryLanding.counter1<0)
////	                    {
////	                    	CategoryLanding.counter1 = 0;
////	                    }
//	                    
//	                    //Log.d("Slider", "moveY:"+moveY);
//	                    //Log.d("Slider", "downY:"+downY);
//	                    
////	                    if(downX<moveX)
////	                    {
////	    	            	Log.d("Slider", "ACTION_RIGHT:");
////	    	            	CategoryLanding.counter1 =CategoryLanding.counter1- CategoryLanding.widthScreen;
////	    	            	CategoryLanding.listview1.smoothScrollTo(CategoryLanding.counter1, 0); 
////	                    }
////	    	            else if(downX>moveX)
////	    	            {
////	    	 	            Log.d("Slider", "ACTION_LEFT:");
////	    	            	
////	    	 	            // Log.d("Slider", "counters1:"+CategoryLanding.counter1);
////	    	            	if(!(CategoryLanding.counter1>=CategoryLanding.widthScreen*(CategoryLanding.images.length-2)))
////	    	            	{
////	    	            		 Log.d("Slider", "counters2:"+CategoryLanding.counter1);
////	    	            		CategoryLanding.counter1 =CategoryLanding.counter1+ CategoryLanding.widthScreen;
////	    	            		CategoryLanding.listview1.smoothScrollTo(CategoryLanding.counter1, 0); 
////	    	            	}
////	    	            	
////	    	            }
//	                    //Log.d("Slider", "counters3:"+CategoryLanding.counter1);
////	                    final Handler handler = new Handler();
////	                    handler.postDelayed(new Runnable()
////	                    {
////	                        @Override
////	                        public void run() 
////	                        {
////	                            // Do something after 5s = 5000ms
////	                        	CategoryLanding.flag=false;
////	                        }
////	                    }, 1500);
//	                    break;
//	            }
//	            
//	          
//	            return true;
//	        }
//	    });
//		
//	}
//
//	
//	
//	public static void startSlider() 
//	{
//		CategoryLanding.startSliders=true;
//		runnable = new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				/* do what you need to do */
//			    if(CategoryLanding.flag==false)
//				{
//					CategoryLanding.counter1=CategoryLanding.counter1+CategoryLanding.widthScreen;
//					//Log.d("Slider", "counter:"+counter);
//					if(CategoryLanding.images!=null)
//					{
//						if(CategoryLanding.counter1>(CategoryLanding.widthScreen*(CategoryLanding.images.length-2)))
//						{
//							CategoryLanding.counter1=0;
//						}
//					}
//					CategoryLanding.listview1.smoothScrollTo(CategoryLanding.counter1, 0); 
//				}
//			    /* and here comes the "trick" */
//			    handler.postDelayed(this, 4000);
//			   }
//			};
//		handler.postDelayed(runnable, 4000);
//	}
}
