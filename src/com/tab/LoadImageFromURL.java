package com.tab;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap>{
    ImageView bitmapImgView;
    public LoadImageFromURL(ImageView bmImgView){
        bitmapImgView = bmImgView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        // TODO Auto-generated method stub
        String urlStr = params[0];
        Bitmap img = null;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlStr);
        HttpResponse response;
        try {
            response = (HttpResponse)client.execute(request);           
            HttpEntity entity = response.getEntity();
            BufferedHttpEntity bufferedEntity = new BufferedHttpEntity(entity);
            InputStream inputStream = bufferedEntity.getContent();
            img = BitmapFactory.decodeStream(inputStream);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return img;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap){
        bitmapImgView.setImageBitmap(bitmap);
    }
}