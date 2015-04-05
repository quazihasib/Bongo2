package com.tab;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.movies.bongobd.R;

public class TabAndListView extends Activity 
{

	public static int numberOfTabs;
//	public static TabHost tabHost;
	public static TabAndListView tabAndListViewInstance;
	
	public TextView tv1, tv2, tvName;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.tab_and_list_view);
//        overridePendingTransition(R.anim.animation1, R.anim.animation2);
        tabAndListViewInstance = this;
        
        //HeaderFunction(tabAndListViewInstance);
    }
    
    
}