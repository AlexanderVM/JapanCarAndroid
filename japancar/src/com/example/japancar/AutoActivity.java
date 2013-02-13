package com.example.japancar;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ToggleButton;
import android.app.Activity;
import android.content.Intent;

public class AutoActivity extends Activity implements OnTouchListener {
	Spinner towns_spinner;
	Spinner companies_spinner;
	Spinner models_spinner;
	Spinner priv_spinner;
	Spinner trans_spinner;
	int active_spinner;
	
	EditText auto_from;
	EditText auto_to;
	EditText price_from;
	EditText price_to;
	
	ToggleButton photo;
	ToggleButton price;
	
	
	Button search_but;
	
	DBAdapter db_adapter;
	
	ArrayList<String> townslist_default;
    ArrayList<String> townslist_db;
    ArrayList<String> companies_default;
    ArrayList<String> companies_db;
    ArrayList<String> models_db;
    ArrayList<String> status_sp;
    ArrayList<String> data_arr;
    HashMap<String, String> search_params;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auto);
		
		
		towns_spinner = (Spinner) findViewById(R.id.auto_towns);
		companies_spinner = (Spinner) findViewById(R.id.auto_compnies);
		models_spinner = (Spinner) findViewById(R.id.auto_models);
        priv_spinner = (Spinner) findViewById(R.id.auto_priv);
		trans_spinner = (Spinner) findViewById(R.id.auto_trans);
		
		auto_from = (EditText) findViewById(R.id.auto_from);
		auto_to = (EditText) findViewById(R.id.auto_to);
		price_from = (EditText) findViewById(R.id.auto_price_from);
		price_to = (EditText) findViewById(R.id.auto_price_to);
		
		price = (ToggleButton) findViewById(R.id.price_but);
		photo = (ToggleButton) findViewById(R.id.photo_but);
		
		search_but = (Button) findViewById(R.id.searchp_but);
		
		
		db_adapter = new DBAdapter(this);
		townslist_default = new ArrayList<String>();
		townslist_db = new ArrayList<String>();
		companies_default = new ArrayList<String>();
		companies_db = new ArrayList<String>();
		models_db = new ArrayList<String>();
		status_sp = new ArrayList<String>();
		data_arr = new ArrayList<String>();
		search_params= new HashMap<String, String>();
		
		status_sp.add("Идет загрузка данных");
		townslist_default.add("Все города");
		companies_default.add("Все компании");
		
		setSpinAdapter(townslist_default, towns_spinner);
		setSpinAdapter(companies_default, companies_spinner);
		
		
		
		towns_spinner.setOnTouchListener(this);
		
		companies_spinner.setOnTouchListener(this);

		companies_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent,
					View itemSelected, int selectedItemPosition, long selectedId) {
          
           if(companies_spinner.getSelectedItem().toString() != "Все компании"){
        	   String compId = db_adapter.getCompId(companies_spinner.getSelectedItem().toString());  
        	  
      	      if(!db_adapter.checkModelsList(compId)){ 
                  JsonApiParser models = new JsonApiParser();
                  db_adapter.insertModels(compId, models.JparseModels(compId));
                 }
        	      setSpinAdapter(db_adapter.getModelsList(compId), models_spinner);
        	   }
			
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});		
        
		search_but.setOnClickListener( new View.OnClickListener() {
			
			public void onClick(View v) {

				search_params.put("town", String.valueOf((towns_spinner.getSelectedItem())));
				search_params.put("marka", String.valueOf(companies_spinner.getSelectedItem()));
                search_params.put("model", String.valueOf(models_spinner.getSelectedItem()));
                search_params.put("year_from", String.valueOf(auto_from.getText()));
                search_params.put("year_to", String.valueOf(auto_to.getText()));
                search_params.put("price_from", String.valueOf(price_from.getText()));
                search_params.put("price_to", String.valueOf(price_to.getText()));
                search_params.put("with_photos", String.valueOf(photo.isChecked()));
                search_params.put("with_price", String.valueOf(price.isChecked()));
         
/* No info in api documentation about this 2 parametrs                
 
              search_params.put("privod", String.valueOf(priv_spinner.getSelectedItem()));
              search_params.put("transmision", String.valueOf(trans_spinner.getSelectedItem()));
*/              Intent auto_results = new Intent();
                auto_results.setClass(v.getContext(), AutoResults.class);
                auto_results.putExtra("params", search_params);
                startActivity(auto_results);
					
			}
		});        	

	}

	
	private void setSpinAdapter(ArrayList<String> s_adapter,Spinner s_item){
		
		final ArrayAdapter<String> spin_current_adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				s_adapter);
		s_item.setAdapter(spin_current_adapter);
		
	}

// Ussing AsyncTask for downloading towns, cities, models in background thread	
	class LoadDataTask extends AsyncTask<Void, Void, ArrayList<String>> {
	    @Override
		protected void onPreExecute() {
			super.onPreExecute();
			switch (active_spinner) {
			case 1:
				setSpinAdapter(status_sp, towns_spinner);
				towns_spinner.setEnabled(false);
				break;
			case 2:
				companies_spinner.setEnabled(false);
				break;
			}
		}
	    
	    @Override
		protected ArrayList<String> doInBackground(Void... params) {
			JsonApiParser parseIt = new JsonApiParser();
			switch (active_spinner) {
			case 1:
				db_adapter.insertTowns(parseIt.JtownParse());
				data_arr = db_adapter.getTownsList();
				break;
			case 2:
				db_adapter.insertCompanies(parseIt.JCompaniesParse());
				data_arr = db_adapter.getCompanies();
				break;
			}
			return data_arr;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			switch (active_spinner) {
			case 1:
				towns_spinner.setEnabled(true);
				setSpinAdapter(data_arr, towns_spinner);
				break;
			case 2:
				companies_spinner.setEnabled(true);
				setSpinAdapter(data_arr, companies_spinner);
				break;

			}
			db_adapter.close();
		}
	}
	
	public boolean onTouch(View v, MotionEvent ar) {
		
		switch (v.getId()) {
		case R.id.auto_towns:
			if (!db_adapter.checkTownBase()) {
				active_spinner = 1;
				LoadDataTask mt = new LoadDataTask();
				mt.execute();
			} else {
				townslist_db = db_adapter.getTownsList();
				db_adapter.close();
				setSpinAdapter(townslist_db, towns_spinner);
			}
			break;

		case R.id.auto_compnies:
			if (!db_adapter.checkCompaniesBase()) {
				active_spinner = 2;
				LoadDataTask mt = new LoadDataTask();
				mt.execute();
			} else{
			companies_db = db_adapter.getCompanies();
			db_adapter.close();
			setSpinAdapter(companies_db, companies_spinner);
			}
			break;
			
		}
		return false;
	}
	
	
	
}

