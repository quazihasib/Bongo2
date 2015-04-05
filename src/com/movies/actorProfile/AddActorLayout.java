package com.movies.actorProfile;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.movies.bongobd.R;
import com.movies.startingPage.StartingPage;
import com.tab.ShareData;

public class AddActorLayout 
{

	public static RequestQueue requestQueue;
	public static LinearLayout parentLayout, summaryLayoutSum, filmographyListLayout;
	public static String[] actorImages = new String[500];
	public static int actorImageCounter = 0;
	public static String[] contentTitles, releaseYears, contentImages, popularContentImages, popularContentTitles, popularContentReleaseYears;
//	public static TextView tvTitle, tvSubTitle, tvYear;
	public static ImageView[] im, im1;
	public static String actorId;
	public static String DEBUG_TAG = AddActorLayout.class.getSimpleName();
	
	public static void addLayout()
	{
		parentLayout = (LinearLayout) ActorProfile.actorProfileInstance.findViewById(R.id.parentLayout);
		summaryLayoutSum = (LinearLayout) ActorProfile.actorProfileInstance.findViewById(R.id.SummaryLayoutSum);
		
		actorId = ActorProfile.castId;
		filmographyListLayout = (LinearLayout) ActorProfile.actorProfileInstance.findViewById(R.id.filmographyListLayout);
		makeJsonObjectRequestActorImage("http://dev.bongobd.com/api/artist.php?artist_id="+actorId); 
		Log.d("AddActorLayout", "acImaege:http://dev.bongobd.com/api/artist.php?artist_id="+actorId);
	}
	
	
	public static void makeJsonObjectRequestActorImage(String urlJsonObj) 
	{
//		contentImages = new String[10];
		actorImageCounter = 0;
		actorId = ActorProfile.castId;
		requestQueue = Volley.newRequestQueue(StartingPage.startInstance);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response) 
					{
						//Log.d("ActorProfileLayout", response.toString());
						try 
						{
							JSONObject data = response.getJSONObject("data");
							JSONArray jb = data.getJSONArray("images");
							
							if(jb.length()!=0 && jb!=null)
							{
							LinearLayout ll1 = new LinearLayout(ActorProfile.actorProfileInstance);
							LayoutParams ll1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							ll1.setOrientation(LinearLayout.HORIZONTAL);
							
							LinearLayout ll2 = new LinearLayout(ActorProfile.actorProfileInstance);
							LayoutParams ll2Params = new LayoutParams(LayoutParams.WRAP_CONTENT, 30);
							ll1.setOrientation(LinearLayout.HORIZONTAL);
							
							HorizontalScrollView hs = new HorizontalScrollView(ActorProfile.actorProfileInstance);
							LayoutParams hsParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							
							LinearLayout topLinearLayout = new LinearLayout(ActorProfile.actorProfileInstance);
							LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
							
							RelativeLayout.LayoutParams params = 
								    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
								        RelativeLayout.LayoutParams.WRAP_CONTENT);

							im = new ImageView[jb.length()];
							contentImages = new String[jb.length()];
							for(int i = 0; i < jb.length(); i++)
							{
								im[i] = new ImageView(ActorProfile.actorProfileInstance);
								im[i].setScaleType(ScaleType.FIT_XY);
								im[i].setImageResource(R.drawable.ic);
								RelativeLayout.LayoutParams imParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
								imParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
								imParams.width =  ShareData.getScreenWidth(ActorProfile.actorProfileInstance)/6;
								imParams.height = ShareData.getScreenWidth(ActorProfile.actorProfileInstance)/6;
								im[i].setTag(i);
									
								View v = new View(ActorProfile.actorProfileInstance);
								v.setBackgroundColor(Color.WHITE);
								RelativeLayout.LayoutParams vL= new RelativeLayout.LayoutParams(10,ShareData.getScreenWidth(ActorProfile.actorProfileInstance)/6); 
								vL.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
										 
								final RelativeLayout rl = new RelativeLayout(ActorProfile.actorProfileInstance);
								rl.addView(im[i], imParams);
										
								topLinearLayout.addView(rl,params);
								topLinearLayout.addView(v, vL);
							}
							
							for (int i=0; i<jb.length(); i++) 
							{
								Object actor = jb.get(i);
								contentImages[actorImageCounter] = ""+jb.get(i);
								contentImages[actorImageCounter] = "http://dev.bongobd.com/wp-content/themes/bongobd/images/artistimage/"+contentImages[actorImageCounter];
								//Log.d("ActorProfileLayout", "contentImages2: "+ contentImages[actorImageCounter] );
								
								ActorProfile.actorImageLoader.DisplayImage(contentImages[actorImageCounter], im[i]);
							}
							
							hs.addView(topLinearLayout, tParams);
							ll1.addView(hs, hsParams);
							summaryLayoutSum.addView(ll2, ll2Params);
							summaryLayoutSum.addView(ll1, ll1Params);
							
							}
							
							makeJsonObjectRequestFeaturesIn("http://dev.bongobd.com/api/artist.php?artist_id="+actorId);
							Log.d("AddActorLayout", "FeatureIn:http://dev.bongobd.com/api/artist.php?artist_id="+actorId);
						}
						catch (JSONException e) 
						{
							e.printStackTrace();
							Toast.makeText(StartingPage.startInstance.getApplicationContext(),
									"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
						//hidepDialog();
					}
				}, new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						VolleyLog.d("ActorProfileLayout", "Error: " + error.getMessage());
						Toast.makeText(StartingPage.startInstance.getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						//hidepDialog();
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}
	
	public static void makeJsonObjectRequestFeaturesIn(String urlJsonObj) 
	{
//		contentImages = new String[10];
		actorImageCounter = 0;
		requestQueue = Volley.newRequestQueue(StartingPage.startInstance);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>() 
				{
					@Override
					public void onResponse(JSONObject response) 
					{
						//Log.d("ActorProfileLayout", response.toString());
						try
						{
							JSONObject data = response.getJSONObject("data");
							//Log.d("ActorProfileLayout", "data: " + data);
							
							JSONObject contents = data.getJSONObject("popularContents");
							
							popularContentImages = new String[contents.length()+3];
							popularContentTitles = new String[contents.length()+3];
							popularContentReleaseYears = new String[contents.length()+3];
							
							Iterator<String> iter = contents.keys();
							while (iter.hasNext()) 
							{ 
								String key = iter.next();
								try 
								{
									Object value = contents.get(key);
									actorImageCounter++;
									//Log.d("ActorProfileLayout", "actorImageCounter:" + actorImageCounter);
									JSONObject eachObject = contents.getJSONObject(""+ key);

									popularContentImages[actorImageCounter] = eachObject.getString("content_thumb");
									popularContentImages[actorImageCounter] = "http://dev.bongobd.com/wp-content/themes/bongobd/images/posterimage/thumb/" + popularContentImages[actorImageCounter];
									
									popularContentTitles[actorImageCounter] = eachObject.getString("content_title");
									if(popularContentTitles[actorImageCounter].trim().length()>10)
									{
										popularContentTitles[actorImageCounter] = popularContentTitles[actorImageCounter].trim().substring(0, 9)+"..";
									}
									Log.d(DEBUG_TAG, "popularContentTitles: "+ popularContentTitles[actorImageCounter] );
									
									popularContentReleaseYears[actorImageCounter] = eachObject.getString("release_year");
									Log.d(DEBUG_TAG, "popularContentReleaseYears: "+ popularContentReleaseYears[actorImageCounter] );
								} 
								catch (JSONException e) 
								{
									// Something went wrong!
								}
							}
							
							//Add Most Popular images
							HorizontalScrollView hs1 = (HorizontalScrollView) ActorProfile.actorProfileInstance.findViewById(R.id.hsMostPopular);
								
							LinearLayout fl1 = new LinearLayout(ActorProfile.actorProfileInstance);
							LinearLayout.LayoutParams fl1Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							fl1.setOrientation(LinearLayout.HORIZONTAL);
									
							LinearLayout fl2 = new LinearLayout(ActorProfile.actorProfileInstance);
							LinearLayout.LayoutParams fl2Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							fl2.setOrientation(LinearLayout.VERTICAL);
									
							RelativeLayout.LayoutParams rl1Params = 
									new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
									RelativeLayout.LayoutParams.WRAP_CONTENT);

							im1 = new ImageView[contents.length()+1];
							
							for(int i = 1; i <= contents.length(); i++)
							{
								LinearLayout llPopularContents = new LinearLayout(ActorProfile.actorProfileInstance);
								LinearLayout.LayoutParams llPopularContentsParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
								llPopularContents.setOrientation(LinearLayout.VERTICAL);
								
							    im1[i] = new ImageView(ActorProfile.actorProfileInstance);
								im1[i].setScaleType(ScaleType.FIT_XY);
								im1[i].setImageResource(R.drawable.ic);
								RelativeLayout.LayoutParams imParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
								imParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
								imParams1.width = ShareData.getScreenWidth(ActorProfile.actorProfileInstance)/3-60;
								imParams1.height = ShareData.getScreenWidth(ActorProfile.actorProfileInstance)/3-60;
								im1[i].setTag(i);
								ActorProfile.actorImageLoader.DisplayImage(popularContentImages[i], im1[i]);
								
								TextView tv = new TextView(ActorProfile.actorProfileInstance);
								tv.setText(""+popularContentTitles[i]);
								tv.setTextColor(Color.RED);
								//tv.setBackgroundColor(Color.BLACK);
								LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
									
								TextView tv1 = new TextView(ActorProfile.actorProfileInstance);
								tv1.setText("("+popularContentReleaseYears[i]+")");
								tv1.setTextColor(Color.GRAY);
								//tv1.setBackgroundColor(Color.BLACK);
								LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
							
								llPopularContents.addView(im1[i], imParams1);
								llPopularContents.addView(tv, tvParams);
								llPopularContents.addView(tv1, tv1Params);
								
								View v1 = new View(ActorProfile.actorProfileInstance);
								v1.setBackgroundColor(Color.WHITE);
								RelativeLayout.LayoutParams vL1= new RelativeLayout.LayoutParams(20,150); 
								vL1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
												 
								final RelativeLayout rl1 = new RelativeLayout(ActorProfile.actorProfileInstance);
								rl1.addView(llPopularContents, llPopularContentsParams);
												
								fl1.addView(rl1,rl1Params);
								fl1.addView(v1, vL1);
							}
							fl2.addView(fl1, fl1Params);
							hs1.addView(fl2, fl2Params);
							
							makeJsonObjectRequestFilmography("http://dev.bongobd.com/api/artist.php?artist_id="+actorId);

						} 
						catch (JSONException e) 
						{
							e.printStackTrace();
							Toast.makeText(StartingPage.startInstance.getApplicationContext(),
									"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
						//hidepDialog();
					}
				}, new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error) 
					{
						VolleyLog.d("ActorProfileLayout", "Error: " + error.getMessage());
						Toast.makeText(StartingPage.startInstance.getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						//hidepDialog();
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}
	
	
	
	public static void makeJsonObjectRequestFilmography(String urlJsonObj) 
	{
		actorImageCounter = 0;
		requestQueue = Volley.newRequestQueue(StartingPage.startInstance);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>() 
				{
					@Override
					public void onResponse(JSONObject response) 
					{
						//Log.d("ActorProfileLayout", response.toString());
						try 
						{
							JSONObject data = response.getJSONObject("data");
							//Log.d("ActorProfileLayout", "data: " + data);

							JSONObject contents = data.getJSONObject("contents");
							contentTitles = new String[contents.length()+3];
							releaseYears = new String[contents.length()+3];
							//Log.d(DEBUG_TAG, "contents.len:"+contents.length());
							Iterator<String> iter = contents.keys();
							while (iter.hasNext()) 
							{ 
								String key = iter.next();
								try 
								{
									Object value = contents.get(key);
									actorImageCounter++;
									//Log.d("ActorProfileLayout", "actorImageCounter:" + actorImageCounter);
									JSONObject eachObject = contents.getJSONObject(""+ key);

									contentTitles[actorImageCounter] = eachObject.getString("content_title");
									//Log.d("ActorProfileLayout", "content_title: "+ contentTitles[actorImageCounter] );
									
									releaseYears[actorImageCounter] = eachObject.getString("release_year");
									//Log.d("ActorProfileLayout", "release_year: "+ releaseYears[actorImageCounter] );
								
								} 
								catch (JSONException e) 
								{
									// Something went wrong!
								}
							}
							
							if(contents.length()!=0 || contents!=null)
							{
								TextView t1, t2;
								LinearLayout ll1;
							
								for(int o=1;o<=contents.length();o++)
								{
									ll1 = new LinearLayout(ActorProfile.actorProfileInstance);
									LinearLayout.LayoutParams ll1Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
									//ll1.setOrientation(LinearLayout.VERTICAL);
									if(o%2!=0)
									{
										ll1.setBackgroundColor(Color.parseColor("#E6E6E6"));
									}
									ll1.setPadding(0, 10, 0, 20);
								
									t1 = new TextView(ActorProfile.actorProfileInstance);
									t1.setTextColor(Color.BLACK);
									t1.setTextSize(ShareData.ConvertFromDp(ActorProfile.actorProfileInstance,28));
									LinearLayout.LayoutParams t1Params = new LinearLayout.LayoutParams(ShareData.getScreenWidth(ActorProfile.actorProfileInstance)/2-20, LayoutParams.WRAP_CONTENT);
									t1.setGravity(Gravity.LEFT);
									t1Params.setMargins(20, 0, 0, 0);
									t1.setText(""+contentTitles[o]);
								
									t2 = new TextView(ActorProfile.actorProfileInstance);
									t2.setTextColor(Color.BLACK);
									t2.setTextSize(ShareData.ConvertFromDp(ActorProfile.actorProfileInstance,28));
									LinearLayout.LayoutParams t2Params = new LinearLayout.LayoutParams(ShareData.getScreenWidth(ActorProfile.actorProfileInstance)/2-20, LayoutParams.WRAP_CONTENT);
									t2.setGravity(Gravity.RIGHT);
									t2.setText(""+releaseYears[o]);
									t2Params.setMargins(0, 0, -20, 0);
									//t2Params.setMargins(-20, 0, 0, 0);
								
									ll1.addView(t1, t1Params);
									ll1.addView(t2, t2Params);
									filmographyListLayout.addView(ll1, ll1Params);
								}
							}
						}
						catch (JSONException e) 
						{
							e.printStackTrace();
							Toast.makeText(StartingPage.startInstance.getApplicationContext(),
									"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
						//hidepDialog();
					}
				}, new Response.ErrorListener() 
				{
					@Override
					public void onErrorResponse(VolleyError error) 
					{
						VolleyLog.d("ActorProfileLayout", "Error: " + error.getMessage());
						Toast.makeText(StartingPage.startInstance.getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						//hidepDialog();
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}
	
}
