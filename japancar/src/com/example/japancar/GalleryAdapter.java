package com.example.japancar;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {

	private int mGalleryItemBackground;
	private Context mContext;
	private String[] images;

	public GalleryAdapter(Context сontext, String[] photoArray) {
		mContext = сontext;
		images = photoArray;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return images[position];
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView view = new ImageView(mContext);
//		view.setImageResource(mImage[position]);
		view.setPadding(20, 20, 20, 20);
		view.setLayoutParams(new Gallery.LayoutParams(250, 250));
		view.setScaleType(ImageView.ScaleType.FIT_XY);
		view.setBackgroundResource(mGalleryItemBackground);
		try {
			                AsyncImageLoad as = new AsyncImageLoad();
			                as.execute(images[position]);
			                view.setImageBitmap(as.get());
			            } catch (Exception ex) {
			                Log.v("Exec", ex.toString());
			            }

		
		

		return view;
	}
	class AsyncImageLoad extends AsyncTask<String, Void, Bitmap> {
		        protected Bitmap doInBackground(String... strings) {
		            HttpClient http = new DefaultHttpClient();
		            Bitmap bit = null;
		            try {
		                HttpGet request = new HttpGet(strings[0]);
		                HttpParams params = new BasicHttpParams();
		                HttpConnectionParams.setSoTimeout(params, 60000);
		                request.setParams(params);
		                HttpResponse response = http.execute(request);
		                byte[] image = EntityUtils.toByteArray(response.getEntity());
		                bit = BitmapFactory.decodeByteArray(image, 0, image.length);
		                return bit;
		            } catch (Exception ex) {
		                Log.e("Exec", ex.toString());
		            }
		            return bit;
		        }
		        protected void onPostExecute(Bitmap result) {
		            super.onPostExecute(result);
		            Log.v("Work", "1");
		        }
		
		    }

}