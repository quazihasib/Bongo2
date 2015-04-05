package com.movies.singleMovie;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.movies.bongobd.R;

public class PlayMovie extends Activity
{
	public WebView webView1;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		  getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
   
        
		setContentView(R.layout.play_movie);
		
		webView1 = (WebView) findViewById(R.id.webView2);
		webView1.getSettings().setJavaScriptEnabled(true);
		webView1.getSettings().setUseWideViewPort(true);
		webView1.getSettings().setLoadWithOverviewMode(true);
		
        webView1.loadUrl(SingleMoviePage.movieUrl);
		webView1.setHorizontalScrollBarEnabled(true);
        
        
	}
	
	@Override
	public void onBackPressed()
	{
	    super.onBackPressed();

	    if(webView1!=null)
	    {
	    	webView1.stopLoading();
//			webView.setWebChromeClient(null);
//			webView.setWebViewClient(null);
			webView1.destroy();
			webView1 = null;
	    }
		finish();
		startActivity(new Intent(getBaseContext(), SingleMoviePage.class));
		overridePendingTransition( R.anim.animation1, R.anim.animation2 );
		
		
		
//		System.runFinalizersOnExit(true);
//		System.exit(0);
//		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		if(webView1!=null)
		{
		    webView1.stopLoading();
//			webView.setWebChromeClient(null);
//			webView.setWebViewClient(null);
			webView1.destroy();
			webView1 = null;
	    }
	}
}
