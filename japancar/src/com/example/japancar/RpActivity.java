package com.example.japancar;



//import java.util.ArrayList;
//import java.util.HashMap;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Gallery;
import android.widget.ImageView;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;

public class RpActivity extends Activity {
    TextView textfield;
    int id = 231573345;
    
    
    HashMap<String, String> search_params;
    
 /*  id: 235861675
  *  230939430
  *  231553474  
  *  231573345  
  */
//	private DBAdapter testdb;
	JSONObject results = new JSONObject();
	JsonApiParser parser;
	HashMap<String, String> hmap;
	
	
	
	JSONObject res;
	ImageView im;
	
	ArrayList<ItemsForList> results1;
	TextView testText;
	TextView testText2;
	String s = "";
	String[] photoArray;
	String ur = "http://5.s2.jc9.ru/filecpd.php?u=aHR0cDovL21lZGlhLndpd2ViLnJ1L2RhdGEvaW1hZ2VzL19qY3RyYWRlLzAxODgzNC9qY3RyYWRlMTM1MTIxMTMxMi05NzExMi01Ny5qcGc=&tp=custom&w=480&h=360&s=1&l&nocrop&sc=d5813&ver=2";
	
	Gallery gallery;
	
	int r;
	private static final String TAG = "RpActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rptest);
        
		search_params= new HashMap<String, String>();
		
		testText = (TextView) findViewById(R.id.textView1);
        testText2 = (TextView) findViewById(R.id.textView2);
        im = (ImageView) findViewById(R.id.imageView1);
        hmap = new HashMap<String, String>();
        try {
			results.put("main_photo_size", "48");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        hmap.put("preved", "medved");

        
//        search_params.put("town","Владивосток");
     
        
        
                parser = new JsonApiParser();
        
  //         result = parser.JparsePartsExtended(id);
       results1 = parser.JparseAutoResults(results);
/*                s = result.get("photos");
        s = s.replace("\\" , "");
        s = s.substring(2, s.length() - 2);
        photoArray = s.split("\",\"");
 */      // testText2.setText(photoArray[0]);
        testText.setText(results1.get(1).getPhoto().toString());
        Log.e(TAG,results1.get(1).getPhoto().toString());
 /*       
        try {
			im.setImageDrawable(grabImageFromUrl(photoArray[0]));
		} catch (Exception e) {
			 testText.setText(e.toString());
		}
        
      gallery = (Gallery) findViewById(R.id.gallery777);
//     gallery.setAdapter(new GalleryAdapter(this, photoArray));
		
}
	private Drawable grabImageFromUrl(String url){
		Drawable draw = null;
		try {
			draw = Drawable.createFromStream(
					(InputStream) new URL(url).getContent(), "src");
			return draw;
		} catch (MalformedURLException e) {
			testText2.setText(e.toString());
			Log.e(TAG,"Error in CheckForm()::" + e.getMessage());
			e.printStackTrace();
			
		} catch (IOException e) {
			testText2.setText(e.toString());
			Log.e(TAG,"Error in CheckForm()::" + e.getMessage());
			e.printStackTrace();
		}
		return draw;
*/	}
}