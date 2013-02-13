package com.example.japancar;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;



public class SPResults extends Activity {
	ImageView testimg;
	PartsResListAdapter partsAdap;
	boolean loadingMore;
	int o = 0;
	
	TextView results_text;
	ListView listeg;
	
	String testString;
	JSONObject resInt = null;
	HashMap<String, String> params;
	ArrayList<ItemsForList> partsList = new ArrayList<ItemsForList>();

	JSONObject p2s; 
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searching_parts_results);
		
		results_text = (TextView) findViewById(R.id.parts_text_results);
        listeg = (ListView) findViewById(R.id.sprListView);
        
        
    	Intent intent = getIntent();
        HashMap<String, String> data_search = (HashMap<String, String>) intent.getSerializableExtra("params");
        final JsonApiParser parts_results = new JsonApiParser();
        p2s = parts_results.ParamsParse(data_search);
        partsList = parts_results.JparsePartsResults(p2s);
	    partsAdap = new PartsResListAdapter(this, partsList); 
	    View footerView = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_footer, null, false);
        
       listeg.addFooterView(footerView); 
       listeg.setAdapter(partsAdap);	
	
   
	   listeg.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> parent, View view,
		          int position, long id) {
		        
/*		    	partsAdap.imageLoader.clearCache();
		        partsAdap.notifyDataSetChanged();  
*/		    	  
		        Intent sp_results_intent = new Intent();
				sp_results_intent.setClass(view.getContext(), SPResultsExtended.class);
				sp_results_intent.putExtra("id", partsList.get(position).getId());
				startActivity(sp_results_intent);
			
		        
		        
		      }
		    });

		    listeg.setOnItemSelectedListener(new OnItemSelectedListener() {
		    	public void onItemSelected(AdapterView<?> parent, View view,
		          int position, long id) {
		    
		      }

		      public void onNothingSelected(AdapterView<?> parent) {
		      }

		
		    });
		    listeg.setOnScrollListener(new OnScrollListener(){
				
				//useless here, skip!
				public void onScrollStateChanged(AbsListView view, int scrollState) {}
				
				//dumdumdum			
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					
					
					//what is the bottom item that is visible
					int lastInScreen = firstVisibleItem + visibleItemCount;				
					
					//is the bottom item visible & not loading more already ? Load more !
					if((lastInScreen == totalItemCount) && !(loadingMore)){	
					
						Toast.makeText(getApplicationContext(), "Конец списка", Toast.LENGTH_SHORT).show();
					}
				}
			});
	   
}
	
	}