package com.movies.login;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.movies.startingPage.StartingPage;
import com.tab.AddMenu;
import com.tab.ShareData;

public class ForgotPassword extends Activity
{
	public HttpClient httpclient;
	public RequestQueue requestQueue;
	public JSONObject jsonobject;
	public HttpPost httppost;
	public TextView loginPage, signuptv;
	public LinearLayout wholeForgotPasswordScreenLayout;
	
	public Button btnReset;
	public ImageView crossButtonForgotPass;
	public TextView resetemail;
	public ForgotPassword forgotPasswordInstance;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_forgot_password);

		forgotPasswordInstance = this;
		
		wholeForgotPasswordScreenLayout = (LinearLayout)findViewById(R.id.wholeForgotPasswordScreenLayout);
		wholeForgotPasswordScreenLayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				ShareData.hideKeyboard(forgotPasswordInstance);
			}
		});
		
		TextView tvTopForgotPass = (TextView)findViewById(R.id.llTopForgotPass);
		tvTopForgotPass.setTextSize(ShareData.ConvertFromDp(forgotPasswordInstance,36));
		
		TextView tvEnterEmail = (TextView)findViewById(R.id.tvEnterEmail);
		tvEnterEmail.setTextSize(ShareData.ConvertFromDp(forgotPasswordInstance,32));
		
		httpclient = new DefaultHttpClient();
		Reset();
//		LoginPage();
//		SignupPage();
		crossButtonForgotPassword();
	}

	private void crossButtonForgotPassword()
	{
		// TODO Auto-generated method stub
		crossButtonForgotPass = (ImageView) findViewById(R.id.crossButtonForgotPass);
		crossButtonForgotPass.setPadding(-10, 
				ShareData.getScreenWidth(forgotPasswordInstance)/StartingPage.crossButtonPadding, 
				ShareData.getScreenWidth(forgotPasswordInstance)/StartingPage.crossButtonPadding, 
				ShareData.getScreenWidth(forgotPasswordInstance)/StartingPage.crossButtonPadding);
		crossButtonForgotPass.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
				startActivity(new Intent(getBaseContext(), LoginActivity.class));
				overridePendingTransition( R.anim.animation1, R.anim.animation2);
			}

		});
	}
	
	private void Reset() 
	{
		// TODO Auto-generated method stub
		btnReset = (Button) findViewById(R.id.resetpassbtn);
		btnReset.setTextSize(ShareData.ConvertFromDp(forgotPasswordInstance,32));
		
		resetemail = (EditText) findViewById(R.id.forgotemail);
		resetemail.setTextSize(ShareData.ConvertFromDp(forgotPasswordInstance,32));
		
		btnReset.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				btnReset.setEnabled(false);
				btnReset.setBackgroundColor(Color.rgb(208, 95, 95));

				if(ShareData.checkEmail(resetemail.getText().toString().trim())==true && resetemail.getText().toString().trim().endsWith(".com"))
				{
					// validate data
					Validation(resetemail.getText().toString().trim());
				}
				else
				{
					Toast.makeText(getBaseContext(), "Please enter valid email address.", Toast.LENGTH_SHORT).show();
					btnReset.setEnabled(true);
					btnReset.setBackgroundColor(Color.rgb(208, 0, 0));
				}
			}

		});
	}

	@SuppressLint("NewApi")
	private void Validation(String email) 
	{
		if (email.length()==0) 
		{
			btnReset.setEnabled(true);
			btnReset.setBackgroundColor(Color.rgb(208, 0, 0));

			Toast.makeText(ForgotPassword.this, "Please enter your email.",Toast.LENGTH_SHORT).show();
			resetemail.requestFocus();
		}
		else if (email != null) 
		{
			// validate by service
			ValidateByService(email);
		}
	}

	private void ValidateByService(String email2)
	{
		// TODO Auto-generated method stub

		String url = "http://dev.bongobd.com/api/forget_pass.php?email="
				+ email2;

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

								String message = js.getString("message");
								
								btnReset.setEnabled(true);
								btnReset.setBackgroundColor(Color.rgb(208, 0, 0));
								
								Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

							}
							else 
							{
								btnReset.setEnabled(true);
								btnReset.setBackgroundColor(Color
										.rgb(208, 0, 0));

								Toast.makeText(getApplicationContext(),
										"Error: " + jsError, Toast.LENGTH_LONG).show();
							}

						} 
						catch (JSONException e)
						{
							btnReset.setEnabled(true);
							btnReset.setBackgroundColor(Color.rgb(208, 0, 0));

							e.printStackTrace();
							Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
						}
					}
				}, new Response.ErrorListener()
				{

					@Override
					public void onErrorResponse(VolleyError error) 
					{
						btnReset.setEnabled(true);
						btnReset.setBackgroundColor(Color.rgb(208, 0, 0));

						Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});

		// Adding request to request queue
		requestQueue.add(jsonObjReq);

	}

//	private void LoginPage()
//	{
//		// TODO Auto-generated method stub
//		loginPage = (TextView) findViewById(R.id.flogin);
//		loginPage.setTextSize(ShareData.ConvertFromDp(forgotPasswordInstance,32));
//		loginPage.setOnClickListener(new OnClickListener() 
//		{
//			@Override
//			public void onClick(View v)
//			{
//				// validate data
//				Intent forgotpass = new Intent(ForgotPassword.this, LoginActivity.class);
//				startActivity(forgotpass);
//				overridePendingTransition( R.anim.animation1, R.anim.animation2 );
//			}
//
//		});
//	}
//
//	private void SignupPage()
//	{
//		// TODO Auto-generated method stub
//		signuptv = (TextView) findViewById(R.id.fsignup);
//		signuptv.setTextSize(ShareData.ConvertFromDp(forgotPasswordInstance,32));
//		signuptv.setOnClickListener(new OnClickListener() 
//		{
//			@Override
//			public void onClick(View v) 
//			{
//				Intent signup = new Intent(ForgotPassword.this, SignUp.class);
//				startActivity(signup);
//				overridePendingTransition(R.anim.animation1, R.anim.animation2 );
//			}
//
//		});
//	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		ForgotPassword.this.finish();
		startActivity(new Intent(getBaseContext(), StartingPage.class));
		overridePendingTransition( R.anim.animation1, R.anim.animation2 );
	}
}
