package com.tab;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movies.bongobd.R;
import com.movies.browseAll.Movies;
import com.movies.login.LoginActivity;
import com.movies.startingPage.StartingPage;

public class DialogSearch
{

	public static EditText etSearch;
	public static String search;
	
	public static Dialog dialogs(final Activity instance)
	{
		final Dialog dialog = new Dialog(instance);
		dialog.setContentView(R.layout.dialog_search);
		dialog.setTitle("Search");
		dialog.setCancelable(false);
		dialog.getWindow().setLayout(ShareData.getScreenWidth(instance), LinearLayout.LayoutParams.WRAP_CONTENT);
		
		etSearch = (EditText) dialog.findViewById(R.id.etSearch);
		
		Button dialogButton = (Button) dialog.findViewById(R.id.btnSearch);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if(etSearch.getText().toString().length()!=0)
				{
					dialog.dismiss();
				
					search = etSearch.getText().toString();
					StartingPage.browseAll = 3;
				
					instance.finish();
					instance.startActivity(new Intent(instance.getBaseContext(), Movies.class));
//					System.runFinalizersOnExit(true);
//					System.exit(0);
//					android.os.Process.killProcess(android.os.Process.myPid());
				}
				else
				{
					Toast.makeText(instance, "please type something.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return dialog;
	}
}
