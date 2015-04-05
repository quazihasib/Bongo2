package com.movies.login;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.auth.RequestToken;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hintdesk.core.activities.AlertMessageBox;
import com.hintdesk.core.util.OSUtil;
import com.hintdesk.core.util.StringUtil;
import com.movies.actorProfile.ActorProfile;
import com.movies.bongobd.R;
import com.movies.browseAll.Movies;
import com.movies.categoryPage.CategoryLanding;
import com.movies.facebook.FacebookApp;
import com.movies.movieSummary.MovieSummary;
import com.movies.people.People;
import com.movies.singleMovie.SingleMoviePage;
import com.movies.startingPage.StartingPage;
import com.movies.subscribe.Subscribe;
import com.movies.twitter.ConstantValues;
import com.movies.twitter.OAuthActivity;
import com.movies.twitter.TwitterActivity;
import com.movies.twitter.TwitterUtil;
import com.tab.AddMenu;
import com.tab.ShareData;

public class LoginActivity extends Activity
{
	public Button btnLogin;
	public EditText userName, password;
	public ImageView facebookLogin;
	public TextView forgotPassword, signuptv;
	public HttpClient httpclient;
	public HttpPost httppost;
	public RequestQueue requestQueue;
	public JSONObject jsonobject;
	public static LoginActivity loginActivityInstance;
	public ProgressDialog pDialog;
	public ImageView crossButtonLogin;
	public LinearLayout wholeLoginScreenLayout;
	
	// Your Facebook APP ID
//	private static String APP_ID = "814015528647528"; // Replace with your App ID
//	// Instance of Facebook Class
//	public static Facebook facebook = new Facebook(APP_ID);
//	private AsyncFacebookRunner mAsyncRunner;
//	String FILENAME = "AndroidSSO_data";
//	private static SharedPreferences mPrefs;
		
	private boolean isUseStoredTokenKey = false;
    private boolean isUseWebViewForAuthentication = false;
    
    public FacebookApp fb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_login);
		    
		loginActivityInstance = this;
//3		fb = new FacebookApp(loginActivityInstance);
		
		wholeLoginScreenLayout = (LinearLayout)findViewById(R.id.wholeLoginScreenLayout);
		wholeLoginScreenLayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				ShareData.hideKeyboard(loginActivityInstance);
			}
		});
		
		TextView tvLoginTitle = (TextView)findViewById(R.id.llTop);
		tvLoginTitle.setTextSize(ShareData.ConvertFromDp(loginActivityInstance,36));
		
		TextView tvFbTitle = (TextView)findViewById(R.id.tvFbTitle);
		tvFbTitle.setTextSize(ShareData.ConvertFromDp(loginActivityInstance, 36));
		
		TextView tvLoginEmail = (TextView)findViewById(R.id.tvLoginEmail);
		tvLoginEmail.setTextSize(ShareData.ConvertFromDp(loginActivityInstance, 36));
		
		//user = new User();
		httpclient = new DefaultHttpClient();
		Login();
		ForgetPassword();
		SignUp();
		crossButtonLogin();
		
		initTwitter();
		//loginTweeter();
		//loginFb();
		LoginUsingFacebook();
		
	}
	
	public void initTwitter()
	{
		   if(!OSUtil.IsNetworkAvailable(getApplicationContext())) 
		   {
	            AlertMessageBox.Show(LoginActivity.this, "Internet connection", "A valid internet connection can't be established", AlertMessageBox.AlertMessageBoxIcon.Info);
	            return;
	        }
	        if (StringUtil.isNullOrWhitespace(ConstantValues.TWITTER_CONSUMER_KEY) || StringUtil.isNullOrWhitespace(ConstantValues.TWITTER_CONSUMER_SECRET)) {
	            AlertMessageBox.Show(LoginActivity.this, "Twitter oAuth infos", "Please set your twitter consumer key and consumer secret", AlertMessageBox.AlertMessageBoxIcon.Info);
	            return;
	        }
	        if (isUseStoredTokenKey)
	            logIn();
	}
	
//	public void loginTweeter()
//	{
//		Button btnTweet = (Button)findViewById(R.id.btnTwitter);
//		btnTweet.setOnClickListener(new View.OnClickListener() 
//		{
//			@Override
//			public void onClick(View v)
//			{
//				// TODO Auto-generated method stub
//				Log.d("LoginActivity", "tweet login");
//				//logIn();
//				logoutFromFacebook();
//			}
//		});
//	}
	
	  private void logIn()
	  {
	        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	        if (!sharedPreferences.getBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN,false))
	        {
	            new TwitterAuthenticateTask().execute();
	        }
	        else
	        {
	            Intent intent = new Intent(this, TwitterActivity.class);
	            startActivity(intent);
	            overridePendingTransition(R.anim.animation1, R.anim.animation2);
	        }
	    }

	    class TwitterAuthenticateTask extends AsyncTask<String, String, RequestToken> 
	    {
	        @Override
	        protected void onPostExecute(RequestToken requestToken)
	        {
	            if (requestToken!=null)
	            {
	                if(!isUseWebViewForAuthentication)
	                {
	                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL()));
	                    startActivity(intent);
	                    overridePendingTransition(R.anim.animation1, R.anim.animation2);
	                }
	                else
	                {
	                    Intent intent = new Intent(getApplicationContext(), OAuthActivity.class);
	                    intent.putExtra(ConstantValues.STRING_EXTRA_AUTHENCATION_URL,requestToken.getAuthenticationURL());
	                    startActivity(intent);
	                    overridePendingTransition(R.anim.animation1, R.anim.animation2);
	                }
	            }
	        }

	        @Override
	        protected RequestToken doInBackground(String... params) {
	            return TwitterUtil.getInstance().getRequestToken();
	        }
	    }
	
//	public void loginFb()
//	{
//		Button btnFB = (Button)findViewById(R.id.btnFB);
//		btnFB.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				FacebookApp fb = new FacebookApp();
////				fb.loginToFacebook(loginActivityInstance);
////				Log.d("LoginActivity", "fb login");
////				loginToFacebook();
//			}
//		});
//	}
	
	public void Login() 
	{
		// TODO Auto-generated method stub
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setText("LOGIN");
		btnLogin.setTextSize(ShareData.ConvertFromDp(loginActivityInstance, 28));
		userName = (EditText) findViewById(R.id.userName);
		userName.setTextSize(ShareData.ConvertFromDp(loginActivityInstance, 30));
		password = (EditText) findViewById(R.id.password);
		password.setTextSize(ShareData.ConvertFromDp(loginActivityInstance, 30));
		
		if(!(ShareData.getSavedString(this, "username").length()==0) &&
				!(ShareData.getSavedString(this, "password").length()==0))
		{
			userName.setText(""+ShareData.getSavedString(this, "username"));
			password.setText(""+ShareData.getSavedString(this, "password"));
		}

		btnLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				btnLogin.setEnabled(false);
				btnLogin.setBackgroundColor(Color.rgb(208, 95, 95));
				
				if(ShareData.checkEmail(userName.getText().toString().trim())==true && userName.getText().toString().trim().endsWith(".com"))
				{
//					Toast.makeText(getBaseContext(), "matches", Toast.LENGTH_SHORT).show();
					Validation(userName.getText().toString().trim(), password.getText().toString().trim());
				}
				else
				{
					Toast.makeText(getBaseContext(), "Please enter valid email address.", Toast.LENGTH_SHORT).show();
				}
				// validate data
//				Validation(userName.getText().toString().trim(), password.getText().toString().trim());
				
			}

		});
	}
	
	public void crossButtonLogin()
	{
		crossButtonLogin = (ImageView)findViewById(R.id.crossButtonLogin);
		crossButtonLogin.setPadding(-10, 
				ShareData.getScreenWidth(loginActivityInstance)/StartingPage.crossButtonPadding, 
				ShareData.getScreenWidth(loginActivityInstance)/StartingPage.crossButtonPadding, 
				ShareData.getScreenWidth(loginActivityInstance)/StartingPage.crossButtonPadding);
		crossButtonLogin.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
				if(AddMenu.pageNumber == 1)
				{
					startActivity(new Intent(getBaseContext(), CategoryLanding.class));
				}
				else if(AddMenu.pageNumber == 2)
				{
					startActivity(new Intent(getBaseContext(), People.class));
				}
				else if(AddMenu.pageNumber == 3)
				{
					startActivity(new Intent(getBaseContext(), Subscribe.class));
				}
				else if(AddMenu.pageNumber == 4)
				{
					startActivity(new Intent(getBaseContext(), SingleMoviePage.class));
				}
				else if(AddMenu.pageNumber == 5)
				{
					startActivity(new Intent(getBaseContext(), Movies.class));
				}
				else if(AddMenu.pageNumber == 6)
				{
					startActivity(new Intent(getBaseContext(), ActorProfile.class));
				}
				else if(AddMenu.pageNumber == 7)
				{
					startActivity(new Intent(getBaseContext(), MovieSummary.class));
				}
				overridePendingTransition(R.anim.animation1, R.anim.animation2);
			}
		});
	}

	@SuppressLint("NewApi")
	private void Validation(String username, String Password) 
	{
		// TODO Auto-generated method stub
		if (username.length()==0 || Password.length()==0)
		{
			btnLogin.setEnabled(true);
			btnLogin.setBackgroundColor(Color.rgb(208, 0, 0));

			Toast.makeText(LoginActivity.this,"Please provide username and password", Toast.LENGTH_SHORT).show();

		}
//		else if (username.isEmpty() && Password.isEmpty()) {
//			if(!ShareData.getSavedString(this, "username").isEmpty() &&
//					!ShareData.getSavedString(this, "password").isEmpty())
//			{
//				userName.setText(""+ShareData.getSavedString(this, "username"));
//				password.setText(""+ShareData.getSavedString(this, "password"));
//			}
//		}
		else if (username != null && Password != null) 
		{
			// validate by service
			ValidateByService(username, Password);
		}
	}

	public void ValidateByService(final String username2, final String password2) 
	{
		// TODO Auto-generated method stub
		// encrypt password
		String encryptedPassword = "";
		byte[] data;
		try 
		{
			data = password2.getBytes("UTF-8");
			encryptedPassword = Base64.encodeToString(data, Base64.DEFAULT);

			// Log.d(DEBUG_TAG, "base64;"+base64);
		}
		catch (UnsupportedEncodingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String url = "http://dev.bongobd.com/api/login.php?email=" + username2
				+ "&password=" + encryptedPassword;

		requestQueue = Volley.newRequestQueue(this);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST, url,
				null, new Response.Listener<JSONObject>()
				{
					@SuppressLint("NewApi")
					@Override
					public void onResponse(JSONObject response) 
					{
						try 
						{
							String jsError = response.getString("error");
							if (jsError.length()==0)
							{
								JSONObject js = response.getJSONObject("data");

								@SuppressWarnings("unchecked")
								Iterator<String> iter = js.keys();
								while (iter.hasNext()) 
								{
									String key = iter.next();
									try 
									{
										JSONObject object = js.getJSONObject(""
												+ key);

										Toast.makeText(getApplicationContext(),"Login successful!",Toast.LENGTH_LONG).show();
										
										//save user info
										ShareData.saveSting(loginActivityInstance, "username", username2);
										ShareData.saveSting(loginActivityInstance, "password", password2);
										ShareData.saveSting(loginActivityInstance, "name", object.getString("display_name"));
										ShareData.saveSting(loginActivityInstance, "pic", object.getString("userprofile"));
										ShareData.saveSting(loginActivityInstance, "secret", object.getString("secret"));
										ShareData.saveSting(loginActivityInstance, "id", object.getString("id"));
										
										//set movie counter to 0
										ShareData.saveInt(loginActivityInstance, "movieCounter", 0);
										
										nextStep();
									} 
									catch (JSONException e) 
									{
										btnLogin.setEnabled(true);
										btnLogin.setBackgroundColor(Color.rgb(208, 0, 0));
										// Something went wrong!
										Toast.makeText(getApplicationContext(),
												"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
									}
								}

							} 
							else 
							{
								btnLogin.setEnabled(true);
								btnLogin.setBackgroundColor(Color.rgb(208, 0, 0));
								Toast.makeText(getApplicationContext(),"Wrong user email or password.", Toast.LENGTH_LONG).show();
							}

						} 
						catch (JSONException e) 
						{
							btnLogin.setEnabled(true);
							btnLogin.setBackgroundColor(Color.rgb(208, 0, 0));

							e.printStackTrace();
							Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
						// hide the progress dialog
						//hidepDialog();
					}
				}, new Response.ErrorListener() 
				{

					@Override
					public void onErrorResponse(VolleyError error)
					{
						btnLogin.setEnabled(true);
						btnLogin.setBackgroundColor(Color.rgb(208, 0, 0));

						Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
						// hide the progress dialog
						//hidepDialog();
					}
				});
		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}

	public void ForgetPassword() 
	{
		forgotPassword = (TextView) findViewById(R.id.forgotpassword);
		forgotPassword.setTextSize(ShareData.ConvertFromDp(loginActivityInstance, 30));
		forgotPassword.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// validate data
				finish();
				startActivity(new Intent(getBaseContext(), ForgotPassword.class));
				overridePendingTransition(R.anim.animation1, R.anim.animation2);
			}

		});
	}
	
	public void nextStep()
	{
		finish();
		if(AddMenu.pageNumber==4)
		{
			startActivity(new Intent(getBaseContext(), SingleMoviePage.class));
		}
		else
		{
			startActivity(new Intent(getBaseContext(), UserProfile.class));
		}
		overridePendingTransition( R.anim.animation1, R.anim.animation2 );
	}

	public void SignUp()
	{
		signuptv = (TextView) findViewById(R.id.signup);
		signuptv.setTextSize(ShareData.ConvertFromDp(loginActivityInstance, 30));
		signuptv.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
				startActivity(new Intent(getBaseContext(), SignUp.class));
				overridePendingTransition(R.anim.animation1, R.anim.animation2);
			}

		});
	}
	
	public static void LogOut(Activity instance)
	{
		ShareData.saveSting(instance, "username", "");
		ShareData.saveSting(instance, "password", "");
		ShareData.saveSting(instance, "name", "");
		ShareData.saveSting(instance, "pic", "");
		ShareData.saveSting(instance, "secret", "");
		ShareData.saveSting(instance, "id", "");

		Toast.makeText(instance.getBaseContext(), "You have been logged out.", Toast.LENGTH_SHORT).show();
	}
	
	public void LoginUsingFacebook()
	{
		facebookLogin = (ImageView)findViewById(R.id.btnFbLogin);
		facebookLogin.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				Log.d("LoginActivity", "FB get pro");
//4				fb.loginToFacebook();
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
//5		fb.facebook.authorizeCallback(requestCode, resultCode, data);
	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		
		finish();
		startActivity(new Intent(getBaseContext(), StartingPage.class));
		overridePendingTransition( R.anim.animation1, R.anim.animation2 );
	}
}
