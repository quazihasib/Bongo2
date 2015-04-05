package com.movies.bongobd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.movies.browseAll.Movies;
import com.movies.startingPage.StartingPage;

import android.util.Log;
import android.widget.Toast;

public class JSONfunctions {

	 // Volley's request queue
    private static RequestQueue requestQueue;
    public static String data;
    
    
    public static void getVolleyJSONfromURL()
    {
    	// Create the request queue
        requestQueue = Volley.newRequestQueue(StartingPage.startInstance);


		String url = "http://site.bongobd.com/api/category.php?catID=1";
		//http://en.wikipedia.org/w/api.php?action=parse&page=Mango&format=json&prop=links
		//http://site.bongobd.com/api/category.php?catID=1
		//http://demo.mysamplecode.com/Servlets_JSP/CountryJSONData
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    // response
                    Log.d("Response", response);
                    
                    if(response.length()!=0)
                    {
                    	data = response;
                    }
                }
            },
            new Response.ErrorListener()
            {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     // error
                     Log.d("Error.Response", "sss");
               }
            }
        ) {    
            @Override
            protected Map<String, String> getParams()
            { 
                    Map<String, String>  params = new HashMap<String, String>(); 
//                    params.put("name", "Alif"); 
//                    params.put("domain", "http://itsalif.info");
                     
                    return params; 
            }
        };
        requestQueue.add(postRequest);
        
    }
    
    
	public static JSONObject getJSONfromURL(String url) {
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		// Download JSON data from URL
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		// Convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		try {

			jArray = new JSONObject(result);
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return jArray;
	}
}
