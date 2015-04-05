package com.movies.login;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
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
import com.movies.bongobd.R;
import com.movies.facebook.FacebookApp;
import com.movies.people.People;
import com.movies.startingPage.StartingPage;
import com.tab.AddMenu;
import com.tab.ShareData;

public class SignUp extends Activity
{
	public HttpClient httpclient;
	public RequestQueue requestQueue;
	public JSONObject jsonobject;
	public HttpPost httppost;
	public SignUp signUpInstance;

	public Button btnSignUp;
	public EditText nameet, emailet, passwordet, confirmPasswordet;
	public LinearLayout wholeSignUpScreenLayout;
	public ProgressDialog pDialog;
	public ImageView crossButtonSignUp , btnSignUpFB;
	public FacebookApp fb;
	
	public String message, id, secret, display_name, user_email, userprofile, userpassword;
	public static String DEBUG_TAG = SignUp.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_sign_up);

		signUpInstance = this;
		
//6		fb = new FacebookApp(signUpInstance);
		httpclient = new DefaultHttpClient();
		
		wholeSignUpScreenLayout = (LinearLayout)findViewById(R.id.wholeSignUpScreenLayout);
		wholeSignUpScreenLayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				ShareData.hideKeyboard(signUpInstance);
			}
		});
		
		TextView tvSignUpTop = (TextView)findViewById(R.id.llTopSignUp);
		tvSignUpTop.setTextSize(ShareData.ConvertFromDp(signUpInstance, 38));
		
		TextView tvSignUpFB = (TextView)findViewById(R.id.tvSignUpFB);
		tvSignUpFB.setTextSize(ShareData.ConvertFromDp(signUpInstance, 38));
		
		TextView tvSignUpEmail = (TextView)findViewById(R.id.tvSignUpEmail);
		tvSignUpEmail.setTextSize(ShareData.ConvertFromDp(signUpInstance, 38));
		
		Signup();
		crossButtonSignUp();
		addButtonSignUpFB();
	}
	
	public void addButtonSignUpFB()
	{
		btnSignUpFB = (ImageView)findViewById(R.id.btnSignUpFB);
		btnSignUpFB.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
//7				fb.loginToFacebook();
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
//8		fb.facebook.authorizeCallback(requestCode, resultCode, data);
	}

	private void Signup() 
	{
		// TODO Auto-generated method stub
		btnSignUp = (Button) findViewById(R.id.btnSignUp);
		btnSignUp.setTextSize(ShareData.ConvertFromDp(signUpInstance, 32));

		nameet = (EditText) findViewById(R.id.sname);
		nameet.setTextSize(ShareData.ConvertFromDp(signUpInstance, 32));
		
		emailet = (EditText) findViewById(R.id.semail);
		emailet.setTextSize(ShareData.ConvertFromDp(signUpInstance, 32));
		
		passwordet = (EditText) findViewById(R.id.sPassword);
		passwordet.setTextSize(ShareData.ConvertFromDp(signUpInstance, 32));
		
		confirmPasswordet = (EditText) findViewById(R.id.sConfirmPassword);
		confirmPasswordet.setTextSize(ShareData.ConvertFromDp(signUpInstance, 32));
		
		btnSignUp.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v)
			{
				// validate data
				btnSignUp.setEnabled(false);
				btnSignUp.setBackgroundColor(Color.rgb(208, 95, 95));

				userpassword = confirmPasswordet.getText().toString().trim();
				if(ShareData.checkEmail(emailet.getText().toString().trim())==true && emailet.getText().toString().trim().endsWith(".com"))
				{
//					Toast.makeText(getBaseContext(), "matches", Toast.LENGTH_SHORT).show();
					
					Validation(nameet.getText().toString().trim(), emailet.getText()
							.toString().trim(), passwordet.getText().toString().trim(),
							confirmPasswordet.getText().toString().trim());
				}
				else
				{
					Toast.makeText(getBaseContext(), "Please enter valid email address", Toast.LENGTH_SHORT).show();
				}
//				Validation(nameet.getText().toString().trim(), emailet.getText()
//						.toString().trim(), passwordet.getText().toString().trim(),
//						confirmPasswordet.getText().toString().trim());
			}

		});
	}
	
	public void crossButtonSignUp()
	{
		crossButtonSignUp = (ImageView)findViewById(R.id.crossButtonSignUp);
		crossButtonSignUp.setPadding(-30, 
				ShareData.getScreenWidth(signUpInstance)/StartingPage.crossButtonPadding, 
				ShareData.getScreenWidth(signUpInstance)/StartingPage.crossButtonPadding, 
				ShareData.getScreenWidth(signUpInstance)/StartingPage.crossButtonPadding);
		crossButtonSignUp.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				finish();
				startActivity(new Intent(getBaseContext(), LoginActivity.class));
				overridePendingTransition(R.anim.animation1, R.anim.animation2);
			}
		});
	}

	@SuppressLint("NewApi")
	private void Validation(String name, String email, String pass, String confPass) 
	{
		// TODO Auto-generated method stub
		if (name.length()==0 || email.length()==0 || pass.length()==0|| confPass.length()==0) 
		{
			btnSignUp.setEnabled(true);
			btnSignUp.setBackgroundColor(Color.rgb(208, 0, 0));

			Toast.makeText(SignUp.this, "Please provide all informations.",Toast.LENGTH_SHORT).show();
		} 
		else
		{
			if(!pass.equals(confPass)) 
			{
				btnSignUp.setEnabled(true);
				btnSignUp.setBackgroundColor(Color.rgb(208, 0, 0));
				
				Toast.makeText(SignUp.this,"These passwords don't match. Try again?",Toast.LENGTH_SHORT).show();
			}
			else 
			{
				// validate by service
				ValidateByService(name, email, pass);
			}
		}

	}

	private void ValidateByService(String name2, final String email2, final String pass2)
	{
		// TODO Auto-generated method stub
		// encrypt password
		String encryptedPassword = "";
		byte[] data;
		try
		{
			data = pass2.getBytes("UTF-8");
			encryptedPassword = Base64.encodeToString(data, Base64.DEFAULT);
			// Log.d(DEBUG_TAG, "base64;"+base64);
		} 
		catch (UnsupportedEncodingException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String url = "http://dev.bongobd.com/api/register.php?fullName="+name2+"&email="+email2+"&password="+encryptedPassword;
		System.out.println("url =" + url);
		
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
								Log.d(DEBUG_TAG, "responce:"+js);
								
								@SuppressWarnings("unchecked")
								Iterator<String> iter = js.keys();
								while (iter.hasNext()) 
								{
									String key = iter.next();
									try 
									{
										JSONObject object = js.getJSONObject(""+ key);

//										System.out.println("id ="+ object.getString("id"));
//										System.out.println("name ="+ object.getString("display_name"));
//										System.out.println("email ="+ object.getString("user_email"));
//										System.out.println("pic url ="+ object.getString("userprofile"));
//										System.out.println("secret ="+ object.getString("secret"));
										
										id = object.getString("id");
										Log.d(DEBUG_TAG, "id:"+id);
										display_name = object.getString("display_name");
										Log.d(DEBUG_TAG, "display_name:"+display_name);
										user_email = object.getString("user_email");
										Log.d(DEBUG_TAG, "user_email:"+user_email);
										userprofile = object.getString("userprofile");
										Log.d(DEBUG_TAG, "userprofile:"+userprofile);
										secret = object.getString("secret");
										Log.d(DEBUG_TAG, "secret:"+secret);
										
										
										Log.d(DEBUG_TAG, "passwordet:"+userpassword);
										Log.d(DEBUG_TAG, "nextStep:");
										
										ShareData.saveSting(signUpInstance, "password", userpassword);
										ShareData.saveSting(signUpInstance, "username", user_email);
										ShareData.saveSting(signUpInstance, "name", display_name);
										ShareData.saveSting(signUpInstance, "pic", userprofile);
										ShareData.saveSting(signUpInstance, "secret", secret);
										ShareData.saveSting(signUpInstance, "id", id);
										
										
										nextStep();
									} 
									catch (JSONException e) 
									{
										// Something went wrong!
										btnSignUp.setEnabled(true);
										btnSignUp.setBackgroundColor(Color.rgb(208, 0, 0));
									}
								}

							} 
							else
							{
								btnSignUp.setEnabled(true);
								btnSignUp.setBackgroundColor(Color.rgb(208, 0,0));

								Toast.makeText(getApplicationContext(),"Error: " + jsError, Toast.LENGTH_LONG).show();
							}
						}
						catch (JSONException e)
						{
							btnSignUp.setEnabled(true);
							btnSignUp.setBackgroundColor(Color.rgb(208, 0, 0));

							e.printStackTrace();
							Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
						}
					}
				}, new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error) 
					{
						btnSignUp.setEnabled(true);
						btnSignUp.setBackgroundColor(Color.rgb(208, 0, 0));

						Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});
		// Adding request to request queue
		requestQueue.add(jsonObjReq);
	}
	
	public void nextStep()
	{
		Toast.makeText(getBaseContext(),"User created successfully ",Toast.LENGTH_LONG).show();

		finish();
		startActivity(new Intent(getBaseContext(), UserProfile.class));
		overridePendingTransition( R.anim.animation1, R.anim.animation2 );
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
