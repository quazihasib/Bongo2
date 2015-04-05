package com.movies.singleMovie;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movies.bongobd.R;
import com.movies.login.LoginActivity;
import com.tab.AddMenu;
import com.tab.ShareData;

public class DialogPackage 
{

	public static Dialog dialog(final Activity instance)
	{
		final Dialog dialog = new Dialog(instance);
		dialog.setContentView(R.layout.dialog_package);
		dialog.setTitle("Login to continue");
		dialog.getWindow().setLayout(ShareData.getScreenWidth(instance), LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.setCancelable(false);
		
		// set the custom dialog components - text, image and button
		TextView text1 = (TextView) dialog.findViewById(R.id.tvDescription);
		text1.setText("You have already wathced 5 videos. Please log in to continue using Bongo for FREE!");
		
//		TextView text2 = (TextView) dialog.findViewById(R.id.tvTaka);
//		text2.setText("TK 25");
		
		TextView dialogButton = (TextView) dialog.findViewById(R.id.button1);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				dialog.dismiss();
				
//				AddMenu.pageNumber = 1;
				
				instance.finish();
				instance.startActivity(new Intent(instance.getBaseContext(), LoginActivity.class));
				instance.overridePendingTransition(R.anim.animation1, R.anim.animation2);
//				System.runFinalizersOnExit(true);
//				System.exit(0);
//				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		return dialog;
	}
}
