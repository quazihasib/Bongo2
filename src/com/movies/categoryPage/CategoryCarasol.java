package com.movies.categoryPage;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class CategoryCarasol  
{
	public static RequestQueue requestQueue;
	public static String[] images, titles, directors;
	public static String[] movieLength;
	public static int imageCounter=0;
	public static String DEBUG_TAG = CategoryCarasol.class.getSimpleName();
	
	
	public static void makeJsonObjectRequestForSlider(String urlJsonObj, final int b)
	{
		Log.d("Carasol", "urlJsonObj:"+urlJsonObj);
		CategoryLanding.carasolLoopCounter++;
		//Log.d("Carasol", "carasolLoopCounter:"+CategoryLanding.carasolLoopCounter);
		
		images = null;
		titles = null;
		directors = null;
		movieLength = null;
//		movieIds = null;
		
		requestQueue = Volley.newRequestQueue(CategoryLanding.categoryInstance);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response) 
					{
						try
						{
							JSONObject js = response.getJSONObject("data");
							imageCounter=0;
							Iterator<String> iter = js.keys();
							//Log.d(DEBUG_TAG, "js length:"+js.length());
							
							images = new String[js.length()+1];
							titles = new String[js.length()+1];
							directors = new String[js.length()+1];
							movieLength = new String[js.length()+1];
							
							
							while (iter.hasNext()) 
							{ 
								String key = iter.next();
								try 
								{
									Object value = js.get(key);
									//Log.d("DEBUG_TAG", "value:" + value);  
									imageCounter++;
									JSONObject eachObject = js.getJSONObject(""+ key);

									String id = eachObject.getString("id");
									if(CategoryLanding.movieIds!=null)
									{
									CategoryLanding.movieIds[CategoryLanding.carasolLoopCounter][imageCounter] = id;
									//Log.d("DEBUG_TAG", "movieIds: "+CategoryLanding.carasolLoopCounter +" "+ imageCounter+" "+CategoryLanding.movieIds[CategoryLanding.carasolLoopCounter][imageCounter]);
									}
									
									String content_length = eachObject.getString("content_length");
									//Log.d("DEBUG_TAG", "content_length: "+ content_length);

									String content_thumb = eachObject.getString("content_thumb");
									content_thumb = "http://dev.bongobd.com/wp-content/themes/bongobd/"
											+ "images/posterimage/thumb/"+ content_thumb;

									String content_title = eachObject.getString("content_title");
									
									String by = eachObject.getString("by");
									//by="aa";
									
									images[imageCounter] = content_thumb;
									movieLength[imageCounter] = content_length;
									directors[imageCounter] = by;
									titles[imageCounter] = content_title;
									if(titles[imageCounter].length()>15)
									{
										titles[imageCounter] = titles[imageCounter].substring(0, 14)+"...";
									}
									CategoryLanding.errorCheck = 0;
								} 
								catch (JSONException e) 
								{
									// Something went wrong!
								}
							}
							hidepDialog1(b);
						} 
						catch (JSONException e) 
						{
							CategoryLanding.errorCheck = 1;
							e.printStackTrace();
							hidepDialog1(b);
						}
						
					}
				}, 
				new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error) 
					{
						CategoryLanding.errorCheck = 1;
						VolleyLog.d("DEBUG_TAG", "Error: 2" + error.getMessage());
						hidepDialog1(b);
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}

	public static void showpDialog1() 
	{
//		if(!pDialog.isShowing()) 
//		{
//			pDialog.show();
//		}
	}

	public static void hidepDialog1(int b)
	{
		CategoryAddLayout.addLayouts(b);
	}
}
