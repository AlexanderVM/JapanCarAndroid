package com.example.japancar;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.os.Bundle;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class AutoResults extends Activity {
	JSONObject resInt = null;
	ArrayList<ItemsForList> autoList;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto_results);
		
		ListView listeg = (ListView) findViewById(R.id.autoListView);
		
		Intent intent = getIntent();
        HashMap<String, String> data_search = (HashMap<String, String>) intent.getSerializableExtra("params");
        JsonApiParser auto_results = new JsonApiParser();
        resInt = auto_results.AutoParamsParse(data_search);
        autoList = auto_results.JparseAutoResults(resInt);
        
        listeg.setAdapter(new AutoResListAdapter(this, autoList));	
		
	}
}
