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
import android.content.Context;
import android.content.Intent;


public class PartsActivity extends Activity implements OnTouchListener {
	Spinner towns_spin;
	Spinner companies_spin;
	Spinner spareparts_spin;
	Spinner models_spin;
	Spinner condition_spin;
	Spinner locationLR_spin;
	Spinner locationFB_spin;
	Spinner locationUD_spin;
	ToggleButton withphoto;
	ToggleButton withprice;
	
	EditText frame;
	EditText engine;
	Button search_parts_button;
    Context context = this;
    DBAdapter db_adapter;
    
    ArrayList<String> townslist_default;
    ArrayList<String> townslist_db;
    ArrayList<String> companiesList_default;
    ArrayList<String> companiesList;
    ArrayList<String> spareparts_default;
    ArrayList<String> spareparts_list;
    ArrayList<String> models_list;
    ArrayList<String> models_default;
    ArrayList<String> status_sp;
    ArrayList<String> data_arr;
    
    int active_spin;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parts);
        
		final HashMap<String, String> search_params = new HashMap<String, String>();
		
		townslist_default = new ArrayList<String>();
		townslist_db = new ArrayList<String>();
		companiesList_default  = new ArrayList<String>();
		companiesList = new ArrayList<String>();
		spareparts_default = new ArrayList<String>();
		models_default = new ArrayList<String>();
		data_arr = new ArrayList<String>();
		status_sp = new ArrayList<String>();
		db_adapter = new DBAdapter(this);
		
		
		townslist_default.add("Все города");
		companiesList_default.add("Все компании");
		spareparts_default.add("Все запчасти");
		models_default.add("Все модели");
		status_sp.add("Идет загрузка данных");
		
		
		towns_spin = (Spinner) findViewById(R.id.towns_spinner);
		companies_spin = (Spinner) findViewById(R.id.comp_list);
		spareparts_spin = (Spinner) findViewById(R.id.parts_list);
		models_spin = (Spinner) findViewById(R.id.models_list);
		condition_spin = (Spinner) findViewById(R.id.state);
		locationLR_spin = (Spinner) findViewById(R.id.loc_lr);
		locationFB_spin = (Spinner) findViewById(R.id.loc_fr);
		locationUD_spin = (Spinner) findViewById(R.id.loc_ud);
		withphoto = (ToggleButton) findViewById(R.id.photo_but);
		withprice = (ToggleButton) findViewById(R.id.price_but);
		
		search_parts_button = (Button) findViewById(R.id.searchp_but);
		frame = (EditText) findViewById(R.id.frame_tf);
		engine = (EditText) findViewById(R.id.engine_tf);
		
		

		setSpinAdapter(townslist_default,towns_spin);
		setSpinAdapter(companiesList_default,companies_spin);
		setSpinAdapter(spareparts_default,spareparts_spin);
		setSpinAdapter(models_default, models_spin);
//
		
		towns_spin.setOnTouchListener(this);
		companies_spin.setOnTouchListener(this);
		spareparts_spin.setOnTouchListener(this);
		
		
		
		companies_spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent,
					View itemSelected, int selectedItemPosition, long selectedId) {
          
           if(companies_spin.getSelectedItem().toString() != "Все компании"){
        	   String compId = db_adapter.getCompId(companies_spin.getSelectedItem().toString());  
        	  
      	      if(!db_adapter.checkModelsList(compId)){ 
                  JsonApiParser models = new JsonApiParser();
                  db_adapter.insertModels(compId, models.JparseModels(compId));
                 }
        	      setSpinAdapter(db_adapter.getModelsList(compId), models_spin);
        	   }
			
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});		
		
		search_parts_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				search_params.put("town", String.valueOf((towns_spin.getSelectedItem())));
				search_params.put("types", String.valueOf(spareparts_spin.getSelectedItem()));
                search_params.put("marka", String.valueOf(companies_spin.getSelectedItem()));
                search_params.put("model", String.valueOf(models_spin.getSelectedItem()));
                search_params.put("body", String.valueOf(frame.getText()));
                search_params.put("engine", String.valueOf(engine.getText()));
                search_params.put("condition", String.valueOf(condition_spin.getSelectedItem()));
                search_params.put("pos_fr", String.valueOf(locationFB_spin.getSelectedItem()));
                search_params.put("pos_rl", String.valueOf(locationLR_spin.getSelectedItem()));
                search_params.put("pos_ud", String.valueOf(locationUD_spin.getSelectedItem()));
                search_params.put("with_photos", String.valueOf(withphoto.isChecked()));
                search_params.put("with_price", String.valueOf(withprice.isChecked()));
                Intent sp_results_intent = new Intent();
				sp_results_intent.setClass(v.getContext(), SPResults.class);
				sp_results_intent.putExtra("params", search_params);
				startActivity(sp_results_intent);
//				finish();
			}
		});
		
		
		
		
	}
	private void setSpinAdapter(ArrayList<String> s_adapter,Spinner s_item){
		
		final ArrayAdapter<String> spin_current_adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				s_adapter);
		s_item.setAdapter(spin_current_adapter);
		
	}
	
	
	
public boolean onTouch(View v, MotionEvent ar) {
		
		switch (v.getId()) {
		case R.id.towns_spinner:
			if (!db_adapter.checkTownBase()) {
				active_spin = 1;
				LoadDataTask dt = new LoadDataTask();
				dt.execute();
			} else {

				townslist_db = db_adapter.getTownsList();
				db_adapter.close();
				setSpinAdapter(townslist_db, towns_spin);
			}
			break;

		case R.id.comp_list:
			if (!db_adapter.checkCompaniesBase()){
				active_spin = 2;
				LoadDataTask dt = new LoadDataTask();
				dt.execute();
				} else {
				companiesList = db_adapter.getCompanies();
				db_adapter.close();
				setSpinAdapter(companiesList,companies_spin);}
			break;
		case R.id.parts_list:	
			if (!db_adapter.checkSparePartsBase()){
				active_spin = 3;
				LoadDataTask dt = new LoadDataTask();
				dt.execute();
				} else {
				spareparts_list = db_adapter.getSparePartsList();
				db_adapter.close();
				setSpinAdapter(db_adapter.getSparePartsList(),spareparts_spin);}
		 break;		
			
		}
	return false;
	}

//Ussing AsyncTask for downloading towns, cities, models in background thread	
		class LoadDataTask extends AsyncTask<Void, Void, ArrayList<String>> {
		    @Override
			protected void onPreExecute() {
				super.onPreExecute();
				switch (active_spin) {
				case 1:
					setSpinAdapter(status_sp, towns_spin);
					towns_spin.setEnabled(false);
					break;
				case 2:
					companies_spin.setEnabled(false);
					break;
				case 3:
					setSpinAdapter(status_sp, spareparts_spin);
					spareparts_spin.setEnabled(false);
					break;
				}
			}
		    
		    @Override
			protected ArrayList<String> doInBackground(Void... params) {
				JsonApiParser parseIt = new JsonApiParser();
				switch (active_spin) {
				case 1:
					db_adapter.insertTowns(parseIt.JtownParse());
					data_arr = db_adapter.getTownsList();
					break;
				case 2:
					db_adapter.insertCompanies(parseIt.JCompaniesParse());
					data_arr = db_adapter.getCompanies();
					break;
				case 3:
					db_adapter.insertSpareParts(parseIt.JParseSpareParts());
					data_arr = db_adapter.getSparePartsList();
					break;
				}
				return data_arr;
			}

			@Override
			protected void onPostExecute(ArrayList<String> result) {
				super.onPostExecute(result);
				switch (active_spin) {
				case 1:
					towns_spin.setEnabled(true);
					setSpinAdapter(data_arr, towns_spin);
					break;
				case 2:
					companies_spin.setEnabled(true);
					setSpinAdapter(data_arr,companies_spin);
					break;
				case 3:
					spareparts_spin.setEnabled(true);
					setSpinAdapter(data_arr,spareparts_spin);
					break;

				}
				db_adapter.close();
			}
		}
	


}