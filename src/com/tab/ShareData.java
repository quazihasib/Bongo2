package com.tab;

import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ShareData 
{
	
	public static String PREF_NAME = "MyPref";
	public static int textSize = 28;
	public static String DEBUG_TAG = ShareData.class.getSimpleName();
	public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
	          "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	          "\\@" +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	          "(" +
	          "\\." +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	          ")+"
	      );
	
	public static int getScreenWidth(Activity instance)
	{
		DisplayMetrics displaymetrics = new DisplayMetrics();
		instance.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;
		
		return width;
	}
	
	public static int getScreenHeight(Activity instance)
	{
		DisplayMetrics displaymetrics = new DisplayMetrics();
		instance.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		
		return height;
	}

	public static void saveInt(Activity instance, String key, int value)
	{
		SharedPreferences settings = instance.getSharedPreferences(PREF_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key,value);
		editor.commit();
	}
	
	public static void saveLong(Activity instance, String key, long value)
	{
		SharedPreferences settings = instance.getSharedPreferences(PREF_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key,value);
		editor.commit();
	}
	
	public static Long getSavedLong(Activity instance, String key)
	{
		SharedPreferences settings = instance.getSharedPreferences(PREF_NAME, 0);
		Long val = settings.getLong(key, 0);
		Log.d("ShareData", "LoadPref:"+val);
		
		return val;
	}
	
	public static int getSavedInt(Activity instance, String key)
	{
		SharedPreferences settings = instance.getSharedPreferences(PREF_NAME, 0);
		int val = settings.getInt(key, 0);
		Log.d("ShareData", "LoadPref:"+val);
		
		return val;
	}
	
	public static void saveSting(Activity instance, String key, String value)
	{
		SharedPreferences settings = instance.getSharedPreferences(PREF_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key,value);
		editor.commit();
		
		Log.d("ShareData", "SavePref:"+value);
	}
	
	public static String getSavedString(Activity instance, String key)
	{
		SharedPreferences settings = instance.getSharedPreferences(PREF_NAME, 0);
		String val = settings.getString(key, "");
		Log.d("ShareData", "LoadPref:"+val);
		
		return val;
	}
	
	public static void saveJsonObject(Activity instance, String key, String value)
	{
		SharedPreferences settings = instance.getSharedPreferences(PREF_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key,value);
		editor.commit();
		
		//Log.d("ShareData", "SavePref:"+value);
	}
	
	public static String getSavedJsonObject(Activity instance, String key)
	{
		SharedPreferences settings = instance.getSharedPreferences(PREF_NAME, 0);
		String val = settings.getString(key, "");
		//Log.d("ShareData", "LoadPref:"+val);
		
		return val;
	}
	
	public static boolean checkJsonObject(Activity instance, JSONObject response, String val)
	{
		boolean result = false;
		if(response.toString().length()!=0)
		{
			String catLoadData = ShareData.getSavedJsonObject(instance, val);
			if(response.toString().equals(catLoadData))
			{
				Log.d("Shared JSON", "Matches!!!");
				result = true;
			}
			else
			{
				Log.d("Shared JSON", "Doesn't match!!! add now");
				ShareData.saveJsonObject(instance, val, response.toString());
				result = false;
			}
		}
		return result;
	}
	
	public static boolean isNetworkAvailable(Activity instance) 
	{
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) instance.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public static void killPage()
	{
		System.runFinalizersOnExit(true);
		System.exit(0);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	public static float ConvertFromDp(Activity instance, int size)
	{
	    final float scale = instance.getResources().getDisplayMetrics().density;
	    return ((size - 0.5f) / scale);
	}
	
	public static double dist(float p1, float p2)
	{
		return Math.sqrt(Math.pow((p2 - p1), 2) + Math.pow((p2 - p1), 2));
	}
	
	public static void hideKeyboard(Activity instance) 
	{   
	    // Check if no view has focus:
	    View view = instance.getCurrentFocus();
	    if (view != null)
	    {
	        InputMethodManager inputManager = (InputMethodManager) instance.getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}
	
	
	public static String changeFormat(String dateValue)
	{
		Log.d(DEBUG_TAG, "dateValue:"+dateValue.trim().length());
		String a = dateValue.replace(":", "");
		a=a.trim();
		//Log.d(DEBUG_TAG, "string:"+a);
		
		String hour=a.substring(0,2);
		//Log.d(DEBUG_TAG, "hour:"+hour);
		
		String minute=a.substring(2,4);
		//Log.d(DEBUG_TAG, "minute:"+minute);
		
		String second=a.substring(4,6);
		//Log.d(DEBUG_TAG, "second:"+second);
		
		String b;
		
		if(hour.equals("00"))
		{
			b = minute+"m"+second+"s";
		}
		else
		{
			 b = hour+"h"+minute+"m"+second+"s";
		}
			
		return b.trim();
	}
	
	public static  boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}
	
	public static float SetTextSize(String text, int width, int height)
	{
	    Paint paint = new Paint();
	    float textWidth = paint.measureText(text);
	    float textSize = (int) ((width / textWidth) * paint.getTextSize());
	    paint.setTextSize(textSize);

	    textWidth = paint.measureText(text);
	    textSize = (int) ((width / textWidth) * paint.getTextSize());

	    // Re-measure with font size near our desired result
	    paint.setTextSize(textSize);

	    // Check height constraints
	    FontMetricsInt metrics = paint.getFontMetricsInt();
	    float textHeight = metrics.descent - metrics.ascent;
	    if (textHeight > height)
	    {
	        textSize = (int) (textSize * (height / textHeight));
	        paint.setTextSize(textSize);
	    }
	    return textSize;
	}
	
	public static String dateFix(String date)
	{
		String total;
		
		if(!date.trim().equals("0000-00-00"))
		{
			date = date.replace("-", "").trim();
			
			String year = date.substring(0, 4);
			Log.d(DEBUG_TAG, "year:"+year);
		
			String month = date.substring(4, 6);
			int m = Integer.parseInt(month.trim());
			if(m==1)
			{
				month = "January";
			}
			else if(m==2)
			{
				month = "February";
			}
			else if(m==3)
			{
				month = "March";
			}
			else if(m==4)
			{
				month = "April";
			}
			else if(m==5)
			{
				month = "May";
			}
			else if(m==6)
			{
				month = "June";
			}
			else if(m==7)
			{
				month = "July";
			}
			else if(m==8)
			{
				month = "August";
			}
			else if(m==9)
			{
				month = "September";
			}
			else if(m==10)
			{
				month = "October";
			}
			else if(m==11)
			{
				month = "November";
			}
			else if(m==12)
			{
				month = "December";
			}
			else if(m==0)
			{
				month = "";
			}
			Log.d(DEBUG_TAG, "month:"+month);
		
			String day = date.substring(6, 8);
			Log.d(DEBUG_TAG, "day:"+day);
		
			total = month+" "+day+","+year;
			Log.d(DEBUG_TAG, "total date:"+total);
		}
		else
		{
			total="";
			Log.d(DEBUG_TAG, "no date");
		}
		return total;
	}
}
