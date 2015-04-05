package com.movies.singleMovie;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;

import com.movies.startingPage.StartingPage;
import com.tab.ShareData;

public class VideoCounter 
{
	public static String DEBUG_TAG = VideoCounter.class.getSimpleName();
	
	//if there is no user, only then count the videos up to 5 times and redirect to login page
	public static void checkVideoCounter(Activity instance)
	{
		Log.d(DEBUG_TAG, "Movie counter entered");
		
		//if user not logged in
		if(ShareData.getSavedString(instance, "username").trim().length()==0
		&& ShareData.getSavedString(instance, "password").trim().length()==0)
		{
			int movieCounter = ShareData.getSavedInt(instance,"movieCounter");
			Log.d(DEBUG_TAG, "Movie counter:" + StartingPage.movieCounter);
			if(movieCounter<5)
			{
				movieCounter++;
				Log.d(DEBUG_TAG, "movieCounter:"+movieCounter);
		   	
				ShareData.saveInt(instance, "movieCounter",movieCounter);
			}
			else
			{
//				Toast.makeText(instance,"You Have Watched The Video More Than Five Times. Please Login Now.",
//						Toast.LENGTH_SHORT).show();
			
				Dialog dialogSubscribe = DialogPackage.dialog(instance);
				dialogSubscribe.show();
			}
		}
	}
	
	
	public static void restartVideoCounter(Activity instance)
	{
		if(ShareData.getSavedString(instance, "username").trim().length()!=0
				  && ShareData.getSavedString(instance, "password").trim().length()!=0)
		{
			StartingPage.movieCounter=0;
			ShareData.saveInt(instance, "movieCounter", StartingPage.movieCounter);
		}
	}
}
