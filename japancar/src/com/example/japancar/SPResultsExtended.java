package com.example.japancar;


import java.util.HashMap;

import android.os.Bundle;
import android.widget.Gallery;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class SPResultsExtended extends Activity {
	
	String id;
	int id1;
	HashMap<String,String> result = new HashMap<String, String>();
	String photosData ="";
	String photosParsed ="";
	String s = "";
	String[] photosArray;
	
	TextView id_text, name_text, loc_text, car_text, 
	         frame_text, engine_text, cond_text, code_text,
	         addit_text, price_text, seller_text, phone_text, 
	         fax_text, web_text, sloc_text;
	
	Gallery gallery;
	JsonApiParser parser;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_more);
		
		name_text = (TextView) findViewById(R.id.part_name);
		loc_text = (TextView) findViewById(R.id.part_loc);
		car_text = (TextView) findViewById(R.id.part_car);
		frame_text = (TextView) findViewById(R.id.part_frame);
		engine_text = (TextView) findViewById(R.id.part_engine);
		cond_text = (TextView) findViewById(R.id.part_cond);
		addit_text = (TextView) findViewById(R.id.part_addit);
		price_text = (TextView) findViewById(R.id.price_part);
		seller_text = (TextView) findViewById(R.id.part_seller);
		phone_text = (TextView) findViewById(R.id.part_phone);
		sloc_text = (TextView) findViewById(R.id.part_seller_loc);
        
        gallery = (Gallery) findViewById(R.id.gallery1);
        
 		
		
		Intent intent = getIntent();
		
        id1 = Integer.valueOf(intent.getStringExtra("id"));
        parser = new JsonApiParser();
        result = parser.JparsePartsExtended(id1);
        photosData = result.get("photos");
        photosData = photosData.replace("\\" , "");
        photosData = photosData.substring(2, photosData.length() - 2);
        photosArray = photosData.split("\",\"");
        
        gallery.setAdapter(new GalleryAdapter(this,photosArray));
        
        name_text.setText(result.get("name"));
        loc_text.setText(result.get("pos_RL"));
        car_text.setText(result.get("model"));
        frame_text.setText(result.get("frame"));
        engine_text.setText(result.get("engine"));
        cond_text.setText(result.get("condition"));
        addit_text.setText(result.get("note"));
        price_text.setText(result.get("price"));
 	    
        seller_text.setText(result.get("contact_name"));
        phone_text.setText(result.get("contact_phone"));
        sloc_text.setText(result.get("town"));
	}
}
