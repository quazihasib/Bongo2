package com.user;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.movies.bongobd.JSONfunctions;
import com.movies.bongobd.R;

public class Registration extends Activity {

	JSONObject jsonobject;
	JSONArray jsonarray;
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String, String>> arraylist;
	public static String DEBUG_TAG = Login.class.getSimpleName();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.registration);
		
		//new DownloadJSON().execute();
	}

	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(Registration.this);
			// Set progressdialog title
			mProgressDialog.setTitle("BongoBD Login");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrieve JSON Objects from the given URL address
			jsonobject = JSONfunctions
					.getJSONfromURL("http://site.bongobd.com/api/login.php?email=faruk2@bglobalsourcing.com&password=123456");
			Log.d(DEBUG_TAG, "jsonobject: " + jsonobject);

//			try {
//				JSONObject js = jsonobject.getJSONObject("data");
//				// Log.d(DEBUG_TAG, "js: "+js);
//
//				Iterator<String> iter = js.keys();
//				while (iter.hasNext()) {
//					String key = iter.next();
//					try {
//						Object value = js.get(key);
//						Log.d(DEBUG_TAG, "value:" + value);
//
//						JSONObject eachObject = js.getJSONObject("" + key);
//
//						String avg_rating = eachObject.getString("avg_rating");
//						Log.d(DEBUG_TAG, "avg_rating: " + avg_rating);
//
//						String content_title = eachObject
//								.getString("content_title");
//						Log.d(DEBUG_TAG, "content_title: " + content_title);
//
//		
//
//						HashMap<String, String> map = new HashMap<String, String>();
//						map.put("movieName", content_title);
//						// map.put("movieCategory", category_name);
//
//						arraylist.add(map);
//					} catch (JSONException e) {
//						// Something went wrong!
//					}
//				}
//
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			// Locate the listview in listview_main.xml
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
}