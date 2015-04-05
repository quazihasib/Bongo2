package com.movies.facebook;


public class FacebookApp 
{

//2	public String names, emails;
//	// Your Facebook APP ID
//	public static String APP_ID = "406196952868186"; // Replace with your App ID
//
//	// Instance of Facebook Class
//	public static Facebook facebook = new Facebook(APP_ID);
//	public AsyncFacebookRunner mAsyncRunner;
//	public String FILENAME = "AndroidSSO_data";
//	//public static SharedPreferences mPrefs;
//	public static Activity ac;
//	
//	public FacebookApp(Activity a)
//	{
//		mAsyncRunner = new AsyncFacebookRunner(facebook);
//		this.ac = a;
//	}
//	
//	//Get Profile information by making request to Facebook Graph API
//	@SuppressWarnings("deprecation")
//	public void getProfileInformation()
//	{
//		mAsyncRunner.request("me", new RequestListener() 
//		{
//			@Override
//			public void onComplete(String response, Object state)
//			{
//				Log.d("Profile FB Data:", response);
//				String json = response;
//				try 
//				{
//					// Facebook Profile JSON data
//					JSONObject profile = new JSONObject(json);
//					
//					// getting name of the user
//					final String name = profile.getString("name");
//					Log.d("FB", "name:"+name);
//					ShareData.saveSting(ac, "name", name);
//					
//					final String id = profile.getString("id");
//					Log.d("FB", "id:"+id);
//					ShareData.saveSting(ac, "id", id);
//					
//					// getting email of the user
//					final String email = profile.getString("email");
//					Log.d("FB", "email:"+email);
//					ShareData.saveSting(ac, "username", email);
//					
//					String pic = "http://graph.facebook.com/"+id+"/picture?type=large";
//					ShareData.saveSting(ac, "pic", pic);
//					
//					ac.runOnUiThread(new Runnable()
//					{
//						@Override
//						public void run()
//						{
//							Toast.makeText(ac.getApplicationContext(), "Name: " + name + "\nEmail: " + email, Toast.LENGTH_LONG).show();
//							
//						}
//
//					});
//					
//					ac.finish();
//					ac.startActivity(new Intent(ac.getBaseContext(), UserProfile.class));
//					ac.overridePendingTransition(R.anim.animation1, R.anim.animation2);
//				} 
//				catch (JSONException e) 
//				{
//					e.printStackTrace();
//				}
//			}
//
//			@Override
//			public void onIOException(IOException e, Object state) 
//			{
//				
//			}
//			@Override
//			public void onFileNotFoundException(FileNotFoundException e,
//					Object state)
//			{
//				
//			}
//			@Override
//			public void onMalformedURLException(MalformedURLException e,
//					Object state) 
//			{
//				
//			}
//			@Override
//			public void onFacebookError(FacebookError e, Object state)
//			{
//				
//			}
//		});
//	}
//	
//	
//	//Function to post to facebook wall
//	@SuppressWarnings("deprecation")
//	public void postToWall()
//	{
//			
//		Bundle parameters = new Bundle();
//	    parameters.putString("name", "Battery Monitor");
//	    parameters.putString("caption", "Build great social apps and get more installs.");
//	    parameters.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
//		parameters.putString("link", "https://play.google.com/store/apps/details?id=com.ck.batterymonitor");
//		//parameters.putString("picture", "link to the picture");
//		parameters.putString("display", "page");
//			    
//		//post on user's wall.
//		facebook.dialog(ac, "feed", parameters, new DialogListener()
//		{
//			@Override
//			public void onFacebookError(FacebookError e)
//			{
//				
//			}
//			@Override
//			public void onError(DialogError e) 
//			{
//					
//			}
//			@Override
//			public void onComplete(Bundle values)
//			{
//					
//			}
//			@Override
//			public void onCancel() 
//			{
//					
//			}
//		});
//	}
//	
//	//Function to login into facebook
//	@SuppressWarnings("deprecation")
//	public void loginToFacebook()
//	{
////		mPrefs = ac.getPreferences(ac.MODE_PRIVATE);
////		String access_token = mPrefs.getString("access_token", null);
////		long expires = mPrefs.getLong("access_expires", 0);
//		String access_token = ShareData.getSavedString(ac, "access_token");
//		long expires = ShareData.getSavedLong(ac, "access_expires");
//		if (access_token != null)
//		{
//			facebook.setAccessToken(access_token);
//			Log.d("FB Sessions", "" + facebook.isSessionValid()); 
//		}
//
//		if (expires != 0) 
//		{
//			facebook.setAccessExpires(expires);
//		}
//				
//		if (!facebook.isSessionValid())
//		{
//			facebook.authorize(ac,
//				new String[]
//						{ 
//							"email", "publish_stream"
//						},
//				new DialogListener()
//						{
//							@Override
//							public void onCancel() 
//							{
//								// Function to handle cancel event
//							}
//
//							@Override
//							public void onComplete(Bundle values) 
//							{
//								// Function to handle complete event
//								// Edit Preferences and update facebook acess_token
////								SharedPreferences.Editor editor = mPrefs.edit();
////								editor.putString("access_token",facebook.getAccessToken());
////								editor.putLong("access_expires",facebook.getAccessExpires());
////								editor.commit();
//								
//								ShareData.saveSting(ac, "access_token", facebook.getAccessToken());
//								ShareData.saveLong(ac, "access_expires", facebook.getAccessExpires());
//
//								getProfileInformation();
//							}
//
//							@Override
//							public void onError(DialogError error) 
//							{
//								// Function to handle error
//							}
//
//							@Override
//							public void onFacebookError(FacebookError fberror) 
//							{
//								// Function to handle Facebook errors
//							}
//
//					});
//			}
//		}
//
////			@Override
////			public void onActivityResult(int requestCode, int resultCode, Intent data) 
////			{
////				super.onActivityResult(requestCode, resultCode, data);
////				facebook.authorizeCallback(requestCode, resultCode, data);
////			}	
//
//	//Function to show Access Tokens
//	public void showAccessTokens()
//	{
//		String access_token = facebook.getAccessToken();
//
//		Toast.makeText(ac.getApplicationContext(),
//				"Access Token: " + access_token, Toast.LENGTH_LONG).show();
//	}
//	
//	@SuppressWarnings("deprecation")
//	public static void logoutFromFacebook(final Activity ac) 
//	{
//		ShareData.saveSting(ac, "access_token", "");
//		ShareData.saveLong(ac, "access_expires", 0);
//		//SharedPreferences mPrefs = ac.getSharedPreferences("MyPrefs", 0);
//		//SharedPreferences.Editor editor = mPrefs.edit();
//		//editor.putString("access_token","");
//		//editor.putLong("access_expires",0); 
//		//editor.commit();
//		Thread t = new Thread(new Runnable()
//		{ 
//		    public void run() 
//		    {
//		    	try 
//		    	{
//					facebook.logout(ac);
//				} 
//		    	catch (MalformedURLException e1)   
//				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) 
//				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace(); 
//				}
//		    }
//		});
//		t.start();
//		
//		
////		try {
//////			Session.getActiveSession().closeAndClearTokenInformation();
////			facebook.setSession(null);
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
////		mAsyncRunner.logout(LoginActivity.this, new RequestListener()
////		{
////			@Override
////			public void onComplete(String response, Object state) 
////			{
////				Log.d("Logout from Facebook", response);
//////				if (Boolean.parseBoolean(response) == true) 
//////				{
//////					runOnUiThread(new Runnable() 
//////					{
//////						@Override
//////						public void run() 
//////						{
//////							// make Login button visible
//////						}
//////
//////					});
////
//////				}
////			}
////
////			@Override
////			public void onIOException(IOException e, Object state) 
////			{
////				
////			}
////			@Override
////			public void onFileNotFoundException(FileNotFoundException e, Object state) 
////			{
////				
////			}
////			@Override
////			public void onMalformedURLException(MalformedURLException e, Object state)
////			{
////				
////			}
////			@Override
////			public void onFacebookError(FacebookError e, Object state) 
////			{
////				
////			}
////		});
//	}
}
