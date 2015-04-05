package com.movies.movieTiming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.movies.bongobd.ImageLoader;
import com.movies.bongobd.R;

public class MovieTimingListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
	public static String DEBUG_TAG = MovieTimingListViewAdapter.class.getSimpleName();

	String popUpContents[];
	public static PopupWindow popupWindowMenuDropDown;
	public static int pos;
	PopupWindow popupWindow;
	public Button bookMovie = null;
	int height, width;

	public MovieTimingListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		MovieTiming.movieTimingInstance.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		width = displaymetrics.widthPixels;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView movieName;
		TextView movieDirectorlabel;
		TextView movieDirector;
		TextView movieViews;
		TextView movieViewsLabel;
		ImageView movieImage;
		LinearLayout movieViewsLabelLayout;
		LinearLayout movieDirectorLabelLayout, layoutMain;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.movie_timing_listview, parent,
				false);
		// Get the position
		resultp = data.get(position);
		
		layoutMain = (LinearLayout)itemView.findViewById(R.id.layoutMain);
		layoutMain.setPadding(0, 0, 0, 20);

		// Locate the TextViews in listview_item.xml
		movieName = (TextView) itemView.findViewById(R.id.movieName);
		movieName.setPadding(10, 0, 0, 0);
		movieDirectorlabel = (TextView) itemView
				.findViewById(R.id.movieDirectorlabel);
		movieDirectorlabel.setPadding(10, 0, 0, 0);
		movieDirector = (TextView) itemView.findViewById(R.id.movieDirector);
		movieViews = (TextView) itemView.findViewById(R.id.movieViews);
		movieViews.setPadding(10, 0, 0, 0);
		movieViewsLabel = (TextView) itemView
				.findViewById(R.id.movieViewslabel);
		movieViewsLabel.setPadding(5, 0, 0, 0);

		movieViewsLabelLayout = (LinearLayout) itemView
				.findViewById(R.id.movieViewsLabelLayout);
		movieDirectorLabelLayout = (LinearLayout) itemView
				.findViewById(R.id.movieDirectorLabelLayout);

		// Locate the ImageView in listview_item.xml
		movieImage = (ImageView) itemView.findViewById(R.id.movieImage);

		// Capture position and set results to the TextViews
		String movies = resultp.get(MovieTiming.MOVIE_NAME).substring(0, 
				Math.min(resultp.get(MovieTiming.MOVIE_NAME).length(), 24));
		if(movies.length()>=24)
		{
			movies=movies+"...";
		}
		movieName.setText(movies);
		movieName.setPadding(10, 10, 0, 0);
		
		movieDirectorlabel.setText("Runtime:");
		movieDirector.setText(resultp.get(MovieTiming.MOVIE_VIEWS));
		movieDirector.setPadding(10, 0, 0, 0);
		
		movieViewsLabel.setText("Release Date:");
		movieViewsLabel.setPadding(10, 0, 0, 0);
		movieViews.setText(resultp.get(MovieTiming.MOVIE_VIEWS));
		

		// Capture position and set results to the ImageView
		// Passes movieImage images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(MovieTiming.MOVIE_IMAGE), movieImage);
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				// Get the position
				resultp = data.get(position);
//				Intent intent = new Intent(context, SingleMoviePage.class);
//				// Pass all data movieName
//				intent.putExtra("movieName", resultp.get(MovieTiming.MOVIE_NAME));
//				// Pass all data movieDirector
//				intent.putExtra("movieDirector",
//						resultp.get(MovieTiming.MOVIE_DIRECTOR));
//				// Pass all data movieViews
//				intent.putExtra("movieViews", resultp.get(MovieTiming.MOVIE_VIEWS));
//				// Pass all data movieImage
//				intent.putExtra("movieImage", resultp.get(MovieTiming.MOVIE_IMAGE));
//
//				intent.putExtra("movieShortSummary",
//						resultp.get(MovieTiming.MOVIE_SHORT_SUMMARY));
//
//				intent.putExtra("movieId", resultp.get(MovieTiming.MOVIE_ID));
//
//				MovieTiming.instance.finish();
//				// Start SingleItemView Class
//				context.startActivity(intent);

			}
		});

		List<String> dogsList = new ArrayList<String>();
		dogsList.add("One::1");
		dogsList.add("Two::2");
		dogsList.add("Three::3");
		dogsList.add("Four::4");

		// convert to simple array
		popUpContents = new String[dogsList.size()];
		dogsList.toArray(popUpContents);

		// initialize pop up window
		popupWindowMenuDropDown = popupWindowMenu();

		bookMovie = (Button) itemView.findViewById(R.id.movieBook);
		// button on click listener
		View.OnClickListener handler = new View.OnClickListener() 
		{
			public void onClick(View v)
			{
				switch (v.getId())
				{
				case R.id.movieBook:
					// show the list view as dropdown
//					if(popupWindowMenuDropDown.isShowing() == false) 
//					{
//						pos = MovieTiming.listview.getPositionForView(v);
//						popupWindowMenuDropDown.showAsDropDown(v, -width/2, -50);
//					}
//					else 
//					{
//						popupWindowMenuDropDown.dismiss();
//					}

					break;
				}
			}
		};
		bookMovie.setOnClickListener(handler);

		return itemView;
	}

	public PopupWindow popupWindowMenu()
	{
		// initialize a pop up window type
		popupWindow = new PopupWindow();

		// the drop down list is a list view
		ListView listViewMenu = new ListView(MovieTiming.movieTimingInstance);

		// set our adapter and pass our pop up window contents
		listViewMenu.setAdapter(dogsAdapter(popUpContents));
		listViewMenu.setDivider(null);
		listViewMenu.setDividerHeight(0);
		listViewMenu.setBackgroundColor(Color.WHITE);
		// set the item click listener
//		listViewMenu
//				.setOnItemClickListener(new DropdownMenuOnItemClickListener());

		// some other visual settings
		// popupWindow.setFocusable(true);
		popupWindow.setWidth(width/2);
		popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

		// set the list view as pop up window content
		popupWindow.setContentView(listViewMenu);

		return popupWindow;
	}

	private ArrayAdapter<String> dogsAdapter(String dogsArray[]) 
	{
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				MovieTiming.movieTimingInstance, android.R.layout.simple_list_item_1, dogsArray) 
			{
			public View getView(int position, View convertView, ViewGroup parent)
			{
				// setting the ID and text for every items in the list
				String item = getItem(position);
				String[] itemArr = item.split("::");
				String text = itemArr[0];
				String id = itemArr[1];

				// visual settings for the list item
				TextView listItem = new TextView(MovieTiming.movieTimingInstance);

				listItem.setText(text);
				listItem.setTag(position);
				listItem.setTextSize(18);
				listItem.setPadding(10, 10, 10, 10);
				listItem.setTextColor(Color.BLACK);

				return listItem;
			}
		};
		return adapter;
	}
	
	
	public static void hideMenuPopUp()
	{
		
	}
	
}
