package com.movies.browseAll;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movies.actorProfile.ActorProfile;
import com.movies.bongobd.ImageLoader;
import com.movies.bongobd.R;
import com.movies.singleMovie.SingleMoviePage;
import com.movies.startingPage.StartingPage;
import com.tab.ShareData;
import com.tab.TabAndListView;

public class ListViewAdapter extends BaseAdapter 
{
	public static PopupWindow popupWindowMenuDropDown;
	public static String singleMovieId;
	public static int pos;
	
	public Context context;
	public LayoutInflater inflater;
	public ImageLoader imageLoader;
	//public ImageView movieMenu = null;
	public PopupWindow popupWindow;

	public ArrayList<HashMap<String, String>> data;
	public HashMap<String, String> resultp = new HashMap<String, String>();
	
	public int height, width;
	public String popUpContents[];
	
	public static String DEBUG_TAG = ListViewAdapter.class.getSimpleName();


	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist)
	{
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
		singleMovieId = "";
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		Movies.moviesInstance.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;
	}

	@Override
	public int getCount() 
	{
		return data.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return null;
	}

	@Override
	public long getItemId(int position) 
	{
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// Declare Variables
		TextView movieName;
		TextView movieDirectorlabel;
		TextView movieDirector;
		TextView movieViews;
		TextView movieViewsLabel;
		TextView movieContentLength;
		ImageView movieImage;
		LinearLayout movieViewsLabelLayout;
		LinearLayout movieDirectorLabelLayout, layoutMain;
		RelativeLayout movieListItem;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_movie_item, parent,
				false);
		// Get the position
		resultp = data.get(position);
		
		//main list item
		layoutMain = (LinearLayout)itemView.findViewById(R.id.layoutMain);
		//layoutMain.setPadding(0, 0, 0, 5);

		// Locate the TextViews in listview_movie_item.xml
		movieListItem =(RelativeLayout)itemView.findViewById(R.id.movieListItem);
		movieListItem.getLayoutParams().height=(int) (ShareData.getScreenHeight(Movies.moviesInstance)/6.5);
				
		movieName = (TextView) itemView.findViewById(R.id.movieName);
		movieName.setTextSize(ShareData.ConvertFromDp(Movies.moviesInstance,28));
		movieName.setPadding(10, 0, 0, 0);
		
		movieDirectorlabel = (TextView) itemView.findViewById(R.id.movieDirectorlabel);
		movieDirectorlabel.setPadding(10, 0, 0, 0);
		movieDirectorlabel.setTextSize(ShareData.ConvertFromDp(Movies.moviesInstance,28));
		
		movieDirector = (TextView) itemView.findViewById(R.id.movieDirector);
		movieDirector.setTextSize(ShareData.ConvertFromDp(Movies.moviesInstance,28));
		
		movieViews = (TextView) itemView.findViewById(R.id.movieViews);
		movieViews.setPadding(10, 0, 0, 0);
		movieViews.setTextSize(ShareData.ConvertFromDp(Movies.moviesInstance,28));
		
		movieViewsLabel = (TextView) itemView.findViewById(R.id.movieViewslabel);
		movieViewsLabel.setPadding(5, 0, 0, 0);
		movieViewsLabel.setTextSize(ShareData.ConvertFromDp(Movies.moviesInstance,28));
		
		movieContentLength = (TextView) itemView.findViewById(R.id.movieContentLength);
		movieContentLength.setTextSize(ShareData.ConvertFromDp(Movies.moviesInstance,28));
		
		movieViewsLabelLayout = (LinearLayout) itemView.findViewById(R.id.movieViewsLabelLayout);
		movieDirectorLabelLayout = (LinearLayout) itemView.findViewById(R.id.movieDirectorLabelLayout);

		// Locate the ImageView in listview_item.xml
		movieImage = (ImageView) itemView.findViewById(R.id.movieImage);

		// Capture position and set results to the TextViews
		String movies = resultp.get(Movies.MOVIE_NAME)
				.substring(0, Math.min(resultp.get(Movies.MOVIE_NAME).length(), 24));
		if(movies.length()>=24)
		{
			movies=movies+"...";
		}
		movieName.setText(movies);
		movieName.setPadding(10, 10, 0, 0);
		movieDirector.setText(resultp.get(Movies.MOVIE_DIRECTOR));
		movieDirector.setPadding(10, 0, 0, 0);
		movieContentLength.setText(resultp.get(Movies.MOVIE_CONTENT_LENGTH));
		//if people page, there is no 'views'
//		if(StartingPage.peoplePageEnable==false)
//		{
//			movieViewsLabel.setText("Views");
//		}
//		else
//		{
//			movieViewsLabel.setText("");
//		}
		if(StartingPage.browseAll==1 || StartingPage.browseAll==3)
		{
			movieViewsLabel.setText("Views");
		}
		else if(StartingPage.browseAll==2)
		{
			movieViewsLabel.setText("");
		}
		

		String DirectorValue = movieDirector.getText().toString();
		//Log.d("ListViewAdapter", "DirectorValue: " + DirectorValue);
		if (DirectorValue.trim().length() == 0) 
		{
			movieDirectorlabel.setText(resultp.get(Movies.MOVIE_VIEWS));
			movieDirector.setPadding(10, 0, 0, 0);
//			if(StartingPage.peoplePageEnable==false)
//			{
//				movieDirector.setText("Views");
//			}
//			else
//			{
//				movieDirector.setText("");
//			}
			
			if(StartingPage.browseAll==1 || StartingPage.browseAll==3)
			{
				movieDirector.setText("Views");
			}
			else if(StartingPage.browseAll==2)
			{
				movieDirector.setText("");
			}
			movieViewsLabel.setText("");
		} 
		else
		{
			movieDirectorlabel.setText("By");
			movieViews.setText(resultp.get(Movies.MOVIE_VIEWS));
		}

		// Capture position and set results to the ImageView
		// Passes movieImage images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(Movies.MOVIE_IMAGE), movieImage);
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				// Get the position
				resultp = data.get(position);
//				Intent intent = new Intent(context, SingleMoviePage.class);
//				ShareData.saveSting(Movies.moviesInstance, "ListViewAdapterMovieName", resultp.get(Movies.MOVIE_NAME));
//				ShareData.saveSting(Movies.moviesInstance, "ListViewAdapterMovieDirector", resultp.get(Movies.MOVIE_DIRECTOR));
//				ShareData.saveSting(Movies.moviesInstance, "ListViewAdapterMovieViews",  resultp.get(Movies.MOVIE_VIEWS));
//				ShareData.saveSting(Movies.moviesInstance, "ListViewAdapterMovieImage", resultp.get(Movies.MOVIE_IMAGE));
//				ShareData.saveSting(Movies.moviesInstance, "ListViewAdapterMovieId",  resultp.get(Movies.MOVIE_ID));
//				ShareData.saveSting(Movies.moviesInstance, "ListViewAdapterMovieShortSummary", resultp.get(Movies.MOVIE_SHORT_SUMMARY));
//				ShareData.saveSting(Movies.moviesInstance, "ListViewAdapterMovieImage", resultp.get(Movies.MOVIE_IMAGE));
				
		
				Intent intent = new Intent(context, TabAndListView.class);
				
				// Pass all data movieName
				intent.putExtra("movieName", resultp.get(Movies.MOVIE_NAME));
				// Pass all data movieDirector
				intent.putExtra("movieDirector",resultp.get(Movies.MOVIE_DIRECTOR));
				// Pass all data movieViews
				intent.putExtra("movieViews", resultp.get(Movies.MOVIE_VIEWS));
				// Pass all data movieImage
				intent.putExtra("movieImage", resultp.get(Movies.MOVIE_IMAGE));

				intent.putExtra("movieShortSummary",
						resultp.get(Movies.MOVIE_SHORT_SUMMARY));
				
				singleMovieId = resultp.get(Movies.MOVIE_ID);

				intent.putExtra("movieId", resultp.get(Movies.MOVIE_ID));
				
				StartingPage.ACTOR_ID = resultp.get(Movies.MOVIE_ID);
				Log.d(DEBUG_TAG, "StartingPage.ACTOR_ID::::"+StartingPage.ACTOR_ID);

				Movies.moviesInstance.finish();
//				if(StartingPage.peoplePageEnable == true)
//				{
//					Movies.moviesInstance.startActivity(new Intent
//							(Movies.moviesInstance.getBaseContext(), ActorProfile.class));
//				}
//				else if(StartingPage.peoplePageEnable == false)
//				{
//					Movies.moviesInstance.startActivity(new Intent
//							(Movies.moviesInstance.getBaseContext(), SingleMoviePage.class));
//				}
				
				if(StartingPage.browseAll == 2)
				{
					Movies.moviesInstance.startActivity(new Intent
							(Movies.moviesInstance.getBaseContext(), ActorProfile.class));
				}
				else if(StartingPage.browseAll == 1 || StartingPage.browseAll == 3)
				{
					Movies.moviesInstance.startActivity(new Intent
							(Movies.moviesInstance.getBaseContext(), SingleMoviePage.class));
				}
				
				
				Movies.moviesInstance.overridePendingTransition
						(R.anim.animation1, R.anim.animation2);
					
			}
		});

//		List<String> dogsList = new ArrayList<String>();
//		dogsList.add("One::1");
//		dogsList.add("Two::2");
//		dogsList.add("Three::3");
//		dogsList.add("Four::4");
//
//		// convert to simple array
//		popUpContents = new String[dogsList.size()];
//		dogsList.toArray(popUpContents);
//
//		// initialize pop up window
//		popupWindowMenuDropDown = popupWindowMenu();
//
//		movieMenu = (ImageView) itemView.findViewById(R.id.imageMenu);
//		movieMenu.setAlpha(0);
//		// button on click listener
//		View.OnClickListener handler = new View.OnClickListener() 
//		{
//			public void onClick(View v)
//			{
//				switch (v.getId())
//				{
//				case R.id.imageMenu:
//					// show the list view as dropdown
//					if(popupWindowMenuDropDown.isShowing() == false) 
//					{
//						pos = Movies.listview.getPositionForView(v);
//						popupWindowMenuDropDown.showAsDropDown(v, -width/2, -50);
//					}
//					else 
//					{
//						popupWindowMenuDropDown.dismiss();
//					}
//
//					break;
//				}
//			}
//		};
//		movieMenu.setOnClickListener(handler);

		return itemView;
	}
	

//	public PopupWindow popupWindowMenu()
//	{
//		// initialize a pop up window type
//		popupWindow = new PopupWindow();
//
//		// the drop down list is a list view
//		ListView listViewMenu = new ListView(Movies.moviesInstance);
//
//		// set our adapter and pass our pop up window contents
//		listViewMenu.setAdapter(dogsAdapter(popUpContents));
//		listViewMenu.setDivider(null);
//		listViewMenu.setDividerHeight(0);
//		listViewMenu.setBackgroundColor(Color.WHITE);
//		// set the item click listener
//		listViewMenu
//				.setOnItemClickListener(new DropdownMenuOnItemClickListener());
//
//		// some other visual settings
//		// popupWindow.setFocusable(true);
//		popupWindow.setWidth(width/2);
//		popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//
//		// set the list view as pop up window content
//		popupWindow.setContentView(listViewMenu);
//
//		return popupWindow;
//	}
//
//	private ArrayAdapter<String> dogsAdapter(String dogsArray[]) 
//	{
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//				Movies.moviesInstance, android.R.layout.simple_list_item_1, dogsArray) 
//			{
//			public View getView(int position, View convertView, ViewGroup parent)
//			{
//				// setting the ID and text for every items in the list
//				String item = getItem(position);
//				String[] itemArr = item.split("::");
//				String text = itemArr[0];
//				String id = itemArr[1];
//
//				// visual settings for the list item
//				TextView listItem = new TextView(Movies.moviesInstance);
//
//				listItem.setText(text);
//				listItem.setTag(position);
//				listItem.setTextSize(ShareData.ConvertFromDp(Movies.moviesInstance,28));
//				listItem.setPadding(10, 10, 10, 10);
//				listItem.setTextColor(Color.BLACK);
//
//				return listItem;
//			}
//		};
//		return adapter;
//	}
}
