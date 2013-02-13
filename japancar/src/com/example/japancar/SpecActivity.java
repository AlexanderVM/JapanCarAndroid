package com.example.japancar;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.Activity;

public class SpecActivity extends Activity {
	Spinner towns_spinner;
	Spinner companies_spinner;
	Spinner models_spinner;
	EditText year_from;
	EditText year_to;
	Button search_but;
	
	ArrayList<String> townslist_default;
    ArrayList<String> townslist_db;
    ArrayList<String> companies_default;
    ArrayList<String> companies_db;
    ArrayList<String> models_db;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spec);
	    
		
		towns_spinner = (Spinner) findViewById(R.id.spec_towns_spinner);
		companies_spinner = (Spinner) findViewById(R.id.spec_marks_spinner);
		models_spinner = (Spinner) findViewById(R.id.spec_models_spinner);
		search_but = (Button) findViewById(R.id.searchs_but);
	    
		townslist_default = new ArrayList<String>();
		companies_default = new ArrayList<String>();
		
		townslist_default.add("Все города");
		companies_default.add("Все марки");
		
		
		setSpinAdapter(townslist_default, towns_spinner);
		setSpinAdapter(companies_default, companies_spinner);
	
	
	}

	private void setSpinAdapter(ArrayList<String> s_adapter,Spinner s_item){
		
		final ArrayAdapter<String> spin_current_adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item,
				s_adapter);
		s_item.setAdapter(spin_current_adapter);
		
	}
}
