package com.tab;

import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.movies.bongobd.R;
import com.movies.categoryPage.CategoryLanding;
import com.movies.people.People;
import com.movies.startingPage.StartingPage;

public class SwipeGestureDetector implements OnGestureListener
{
	public static final int SWIPE_MIN_DISTANCE = 5;
	public static final int SWIPE_THRESHOLD_VELOCITY = 50;
	public static String DEBUG_TAG = SwipeGestureDetector.class.getSimpleName();
	
	@Override
	public boolean onDown(MotionEvent arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		// TODO Auto-generated method stub
		try
		{
			//Log.d(DEBUG_TAG, "browse all:"+StartingPage.browseAll);
			
			// right to left swipe
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
			{
				if(StartingPage.browseAll == 1)
				{
					if(CategoryLanding.viewFlipper.getDisplayedChild()!=0 && CategoryLanding.touchEnable==false)
					{
						CategoryLanding.viewFlipper.setInAnimation(CategoryLanding.categoryInstance, R.anim.slide_in_from_right);
						// Current screen goes out from left. 
						CategoryLanding.viewFlipper.setOutAnimation(CategoryLanding.categoryInstance, R.anim.slide_out_to_left);

						
						CategoryLanding.viewFlipper.showPrevious();
						//Log.d(DEBUG_TAG, "left:"+CategoryLanding.viewFlipper.getDisplayedChild());
					}
//					CategoryLanding.viewFlipper.setInAnimation(CategoryLanding.categoryInstance, R.anim.slide_in_from_right);
//					CategoryLanding.viewFlipper.setOutAnimation(CategoryLanding.categoryInstance, R.anim.slide_out_to_left);
				}
				else if(StartingPage.browseAll == 2)
				{
					//Log.d(DEBUG_TAG, "left");
					if(People.peopleViewFlipper.getDisplayedChild()!=0)
					{
						People.peopleViewFlipper.setInAnimation(People.peopleInstance, R.anim.slide_in_from_right);
						// Current screen goes out from left. 
						People.peopleViewFlipper.setOutAnimation(People.peopleInstance, R.anim.slide_out_to_left);
						
						// Display previous screen.
						People.peopleViewFlipper.showPrevious();
						Log.d(DEBUG_TAG, "left:"+CategoryLanding.viewFlipper.getDisplayedChild());
					}
//					People.peopleViewFlipper.setInAnimation(People.peopleInstance, R.anim.slide_in_from_right);
//					People.peopleViewFlipper.setOutAnimation(People.peopleInstance, R.anim.slide_out_to_left);
					
				}
				return true;
			} 
			else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
			{
				if(StartingPage.browseAll == 1)
				{
					if(CategoryLanding.viewFlipper.getDisplayedChild()!=CategoryLanding.images.length-2 && CategoryLanding.touchEnable==false)
					{
						CategoryLanding.viewFlipper.setInAnimation(CategoryLanding.categoryInstance, R.anim.slide_in_from_left);
						// Current screen goes out from right. 
						CategoryLanding.viewFlipper.setOutAnimation(CategoryLanding.categoryInstance, R.anim.slide_out_to_right);
  
						// Display next screen.
						CategoryLanding.viewFlipper.showNext();
					}
//					CategoryLanding.viewFlipper.setInAnimation(CategoryLanding.categoryInstance, R.anim.slide_in_from_right);
//					CategoryLanding.viewFlipper.setOutAnimation(CategoryLanding.categoryInstance, R.anim.slide_out_to_left);
				}
				else if(StartingPage.browseAll == 2)
				{
					//Log.d(DEBUG_TAG, "right");
					if(People.peopleViewFlipper.getDisplayedChild()!=People.images.length-2)
					{
						People.peopleViewFlipper.setInAnimation(People.peopleInstance, R.anim.slide_in_from_left);
						// Current screen goes out from right. 
						People.peopleViewFlipper.setOutAnimation(People.peopleInstance, R.anim.slide_out_to_right);
  
						// Display next screen.
						People.peopleViewFlipper.showNext();
						//Log.d(DEBUG_TAG, "right:"+CategoryLanding.viewFlipper.getDisplayedChild());
					}
//					People.peopleViewFlipper.setInAnimation(People.peopleInstance, R.anim.slide_in_from_right);
//					People.peopleViewFlipper.setOutAnimation(People.peopleInstance, R.anim.slide_out_to_left);
				}
				return true;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
