package com.movies.subscribe;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.movies.bongobd.R;
import com.movies.browseAll.Movies;
import com.movies.singleMovie.SingleMoviePage;
import com.movies.startingPage.StartingPage;
import com.tab.AddMenu;
import com.tab.ShareData;

public class Subscribe extends Activity
{
	public ProgressDialog pDialog;
 	public static Subscribe suscribeInstance;
 	public static LinearLayout suscribeMainLayout;
 	
 	public static String[] name = new String[2];
 	public static String[] price = new String[2];
 	public static String[] description = new String[2];
 	public static String[] duration = new String[2];
 	
 	public static String[] contentTitleOnDemand = new String[15];
 	public static String[] descriptionOnDemand = new String[15];
 	public static String[] priceOnDemand = new String[15];
 	public static String[] durationOnDemand = new String[5];
 	
 	public int counter=0;
 	public static String DEBUG_TAG = Subscribe.class.getSimpleName();
	
	public RequestQueue requestQueue;
 
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.suscribe);
		
		suscribeInstance = this;
		AddMenu.HeaderFunction(suscribeInstance);
		AddMenu.pageNumber = 3;
		
		if(ShareData.isNetworkAvailable(suscribeInstance)==true)
		{
//			pDialog = ProgressDialog.show(this, "", "");
//			pDialog.setContentView(R.layout.custom_progress);
//			pDialog.show();
//			pDialog.setCancelable(false);
			
			String id = ShareData.getSavedString(suscribeInstance, "id");
			String secret = ShareData.getSavedString(suscribeInstance, "secret");
			//Log.d(DEBUG_TAG, "id:"+id);
			//Log.d(DEBUG_TAG, "secret:"+secret);
		    
			makeJsonObjectRequest("http://dev.bongobd.com/api/packages.php?operator=banglalink");
			Log.d(DEBUG_TAG, "http://dev.bongobd.com/api/packages.php?operator=banglalink");
		}
		else
		{
			Toast.makeText(getBaseContext(), "No Internet", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public void makeJsonObjectRequest(String urlJsonObj) 
	{
		//showpDialog();
		counter=0;
		requestQueue = Volley.newRequestQueue(suscribeInstance);
		Log.d(DEBUG_TAG, "aaaa");
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				urlJsonObj, null, new Response.Listener<JSONObject>() 
				{
					@Override
					public void onResponse(JSONObject response)
					{
						Log.d(DEBUG_TAG, response.toString());

						try 
						{
							JSONObject js = response.getJSONObject("data");
							Log.d(DEBUG_TAG, "js: "+js); 
							
							JSONArray cast = js.getJSONArray("packages");
							for (int i=0; i<cast.length(); i++) 
							{
							    JSONObject actor = cast.getJSONObject(i);
							    
							    name[i] = actor.getString("name");
						        Log.d(DEBUG_TAG, "name: "+name[i]); 
						        
						        price[i] = actor.getString("price");
						        Log.d(DEBUG_TAG, "price: "+price[i]); 
						        
						        description[i] = actor.getString("description");
						        Log.d(DEBUG_TAG, "description: "+description[i]); 
						        
						        duration[i] = actor.getString("duration");
						        Log.d(DEBUG_TAG, "duration: "+duration[i]); 
							}
						
						
							JSONObject j = js.getJSONObject("ondemands");
				            Log.d(DEBUG_TAG, "j: "+j); 
							
						    Iterator<String> iter = j.keys(); 
						    while(iter.hasNext()) 
						    {
						        String key = iter.next();
						        try 
						        {
						            Object value = j.get(key);
						            //Log.d(DEBUG_TAG, "value:"+value ); 
						            counter++;
						            JSONObject eachObject = j.getJSONObject(""+ key);
						            
						            	contentTitleOnDemand[counter] = eachObject.getString("content_title");
						            	Log.d(DEBUG_TAG, "content_title: "+contentTitleOnDemand[counter]+"counter:"+counter); 
						       
						            	descriptionOnDemand[counter] = eachObject.getString("package_description");
						            	Log.d(DEBUG_TAG, "descriptionOnDemand: "+contentTitleOnDemand[counter]+"counter:"+counter); 
									       
						            	priceOnDemand[counter] = eachObject.getString("price");
						            	Log.d(DEBUG_TAG, "priceOnDemand: "+priceOnDemand[counter]+" counter:"+counter); 
									       
						        }
						        catch (JSONException e)
						        {
						            // Something went wrong!
						        }
						    }
						    
						

						}
						catch (JSONException e)
						{
							e.printStackTrace();
							VolleyLog.d(DEBUG_TAG, "Error: " + e.getMessage());
							Log.d(DEBUG_TAG, e.getMessage());
							Toast.makeText(getApplicationContext(),
									"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
						}
						hidepDialog();
					}
				}, new Response.ErrorListener() 
				{

					@Override
					public void onErrorResponse(VolleyError error) 
					{
						VolleyLog.d(DEBUG_TAG, "Error: " + error.getMessage());
						//Log.d(DEBUG_TAG, error.getMessage());
						Toast.makeText(getApplicationContext(),
								error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						hidepDialog();
					}
				});
		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}
	
	
//	private void showpDialog()
//	{
//		if(!pDialog.isShowing())
//		{
//			pDialog.show();
//		}
//	}

	private void hidepDialog() 
	{
//		if(pDialog.isShowing())
//		{
//			pDialog.dismiss();
			createView();
			
//		}
	}
	@Override
	public void onStop() 
	{
		super.onStop();
		
		Log.d(DEBUG_TAG, "App Stopped");
		
//		pDialog = null;
//	 	suscribeInstance = null;
	 	
//	 	name = null;
//	 	price = null;
//	 	description = null;
//	 	duration = null;
//	 	
//	 	contentTitleOnDemand = null;
//	 	descriptionOnDemand = null;
//	 	priceOnDemand = null;
//	 	durationOnDemand = null;
//	 	
//	 	counter=0;
//		//Subscribe
//		if(requestQueue!=null)
//		{
//			requestQueue.cancelAll(new RequestQueue.RequestFilter() {
//
//				@Override
//				public boolean apply(Request<?> request) {
//					// TODO Auto-generated method stub
//					return true;
//				}
//		    });
//		}
	}	
	
	@Override
	public void onDestroy() 
	{
		super.onDestroy();
		Log.d(DEBUG_TAG, "App Destroyed");
	}	
	
	public void createView()
	{
		suscribeMainLayout = (LinearLayout) findViewById(R.id.suscribeMainLayout);
		TextView[] tv1 = new TextView[2];
		for(int i=0; i<2; i++)
		{
			LinearLayout layout1 = new LinearLayout(this);
			LayoutParams layout1Params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layout1.setOrientation(LinearLayout.VERTICAL);
			
			tv1[i] = new TextView(this);
			tv1[i].setTextColor(Color.RED);
			tv1[i].setTextSize(ShareData.ConvertFromDp(suscribeInstance,28));
			LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tv1Params.setMargins(10, 0, 0, 0);
			try 
			{
				tv1[i].setText(""+name[i]);
			} 
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			LinearLayout layout2 = new LinearLayout(this);
			LinearLayout.LayoutParams layout2Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layout2.setOrientation(LinearLayout.VERTICAL);
			layout2Params.setMargins(10, 10, 10, 10);
			layout2.setBackgroundColor(Color.WHITE);
			layout2.setLayoutParams(layout2Params);
			
			View view1 = new View(this);
			view1.setBackgroundColor(Color.RED);
			LinearLayout.LayoutParams view1Params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 2);
			
			LinearLayout layout3 = new LinearLayout(this);
			LinearLayout.LayoutParams layout3Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layout3.setOrientation(LinearLayout.HORIZONTAL);
			layout3.setLayoutParams(layout3Params);
			
			TextView tv2 = new TextView(this);
			//tv2.setTextColor(Color.BLACK);
			tv2.setTextSize(ShareData.ConvertFromDp(suscribeInstance,28));
			LinearLayout.LayoutParams tv2Params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2);
			tv2Params.setMargins(10, 0, 0, 0);
			tv2.setLayoutParams(tv2Params);
			Spannable word = null;
			try 
			{
				word = new SpannableString(""+contentTitleOnDemand[counter]);
				//word.setSpan(new ForegroundColorSpan(Color.BLUE), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				word.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 
	                    0,word.length(), 0);
				tv2.setText(word);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        
			
			
			Spannable wordTwo = null;
			try 
			{
				wordTwo = new SpannableString("\n"+description[i]);
				//wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				tv2.append(wordTwo);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        
			
			TextView tv3 = new TextView(this);
			LinearLayout.LayoutParams tv3Params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			tv3Params.setMargins(0, 10, 0, 0);
			tv3.setTextSize(ShareData.ConvertFromDp(suscribeInstance,28));
			tv3.setLayoutParams(tv3Params);
			tv3.setTextColor(Color.RED);
			try 
			{
				tv3.setText("TK "+duration[i]+"/day");
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TextView btn1 = new TextView(this);
			btn1.setBackgroundColor(Color.RED);
			btn1.setTextColor(Color.WHITE);
			btn1.setTextSize(ShareData.ConvertFromDp(suscribeInstance,28));
			btn1.setGravity(Gravity.CENTER);
			btn1.setTypeface(Typeface.DEFAULT_BOLD);
			LinearLayout.LayoutParams btn1Params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			btn1Params.setMargins(0, 10, 10, 0);
			btn1.setPadding(0, 10, 0, 10);
			btn1.setLayoutParams(btn1Params);
			btn1.setText("SUBSCRIBE");
			btn1.setTextSize(11);
			
//			layout2.addView(tv2, tv2Params);
//			layout2.addView(tv3, tv3Params);
//			layout2.addView(btn1, btn1Params);
			
			layout3.addView(tv2);
			layout3.addView(tv3);
			layout3.addView(btn1);
			
			layout2.addView(view1, view1Params);
			layout2.addView(layout3);
			layout2.setPadding(0, 0, 0, 30);
			
			layout1.addView(tv1[i], tv1Params);
			layout1.addView(layout2);
			
			suscribeMainLayout.addView(layout1, layout1Params);
			
		}
		createViewOnDemandPlans();
	}
	
	
	public void createViewOnDemandPlans()
	{
		TextView tv1 = new TextView(this);
		tv1.setTextColor(Color.RED);
		tv1.setTextSize(ShareData.ConvertFromDp(suscribeInstance,28));
		LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tv1Params.setMargins(10, 0, 0, 0);
		tv1.setText("On Demand Plans");
		suscribeMainLayout.addView(tv1, tv1Params);
		
		for(int i=1; i<=counter; i++)
		{
			LinearLayout layout1 = new LinearLayout(this);
			LayoutParams layout1Params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layout1.setOrientation(LinearLayout.VERTICAL);
			
//			tv1[i] = new TextView(this);
//			tv1[i].setTextColor(Color.RED);
//			tv1[i].setTextSize(20);
//			LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			tv1Params.setMargins(10, 0, 0, 0);
//			tv1[i].setText(""+name[i]);
			
			LinearLayout layout2 = new LinearLayout(this);
			LinearLayout.LayoutParams layout2Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layout2.setOrientation(LinearLayout.VERTICAL);
			layout2Params.setMargins(10, 4, 10, 10);
			layout2.setBackgroundColor(Color.WHITE);
			layout2.setLayoutParams(layout2Params);
			
			View view1 = new View(this);
			view1.setBackgroundColor(Color.RED);
			LinearLayout.LayoutParams view1Params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 2);
			
			LinearLayout layout3 = new LinearLayout(this);
			LinearLayout.LayoutParams layout3Params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			layout3.setOrientation(LinearLayout.HORIZONTAL);
			layout3.setLayoutParams(layout3Params);
			
			TextView tv2 = new TextView(this);
			LinearLayout.LayoutParams tv2Params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2);
			tv2Params.setMargins(10, 0, 0, 0);
			//tv2.setTextColor(Color.BLACK);
			tv2.setTextSize(ShareData.ConvertFromDp(suscribeInstance,28));
			tv2.setLayoutParams(tv2Params);
			Spannable word = null;
			try 
			{
				word = new SpannableString(""+contentTitleOnDemand[i]);
				//word.setSpan(new ForegroundColorSpan(Color.BLUE), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				word.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 
	                    0,word.length(), 0);
				tv2.setText(word);
			}
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        
		
			Spannable wordTwo = null;
			try 
			{
				wordTwo = new SpannableString("\n"+descriptionOnDemand[i]);
				//wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				tv2.append(wordTwo);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        
			
			TextView tv3 = new TextView(this);
			LinearLayout.LayoutParams tv3Params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			tv3Params.setMargins(0, 10, 0, 0);
			tv3.setTextSize(ShareData.ConvertFromDp(suscribeInstance,28));
			tv3.setLayoutParams(tv3Params);
			tv3.setTextColor(Color.RED);
			tv3.setText("TK "+priceOnDemand[i]);
			
			TextView btn1 = new TextView(this);
			btn1.setTextSize(ShareData.ConvertFromDp(suscribeInstance,28));
			btn1.setBackgroundColor(Color.RED);
			btn1.setTextColor(Color.WHITE);
			btn1.setGravity(Gravity.CENTER);
			btn1.setTypeface(Typeface.DEFAULT_BOLD);
			LinearLayout.LayoutParams btn1Params = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			btn1Params.setMargins(0, 10, 10, 0);
			btn1.setPadding(0, 10, 0, 10);
			btn1.setLayoutParams(btn1Params);
			btn1.setText("SUBSCRIBE");
			btn1.setTextSize(11);
			
			layout3.addView(tv2);
			layout3.addView(tv3);
			layout3.addView(btn1);
			
			layout2.addView(view1, view1Params);
			layout2.addView(layout3);
			layout2.setPadding(0, 0, 0, 30);
			
//			layout1.addView(tv1[i], tv1Params);
			layout1.addView(layout2);
			
			suscribeMainLayout.addView(layout1, layout1Params);
		}
	}
	
	
	@Override
	public void onBackPressed() 
	{
		super.onBackPressed();
		Subscribe.this.finish();
		startActivity(new Intent(getBaseContext(), StartingPage.class));
		overridePendingTransition( R.anim.animation1, R.anim.animation2 );
	    
//		System.runFinalizersOnExit(true);
//		System.exit(0);
//		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
