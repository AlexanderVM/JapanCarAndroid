package com.example.japancar;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;

/**
* For the brave souls who get this far: You are the chosen ones,
* the valiant knights of programming who toil away, without rest,
* fixing our most awful code. To you, true saviors, kings of men,
* I say this: never gonna give you up, never gonna let you down,
* never gonna run around and desert you. Never gonna make you cry,
* never gonna say goodbye. Never gonna tell a lie and hurt you.
*/



public class MainActivity extends Activity implements OnClickListener {
	Button auto_but, 
	       spec_but,
	       parts_but,
	       test_but,
	       stat_but,
	       newad_but;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		parts_but = (Button) findViewById(R.id.parts_but);
        auto_but = (Button) findViewById(R.id.auto_but);
        spec_but = (Button) findViewById(R.id.spec_but);
        stat_but = (Button) findViewById(R.id.stat_but);
		newad_but = (Button) findViewById(R.id.newad_but);
		test_but = (Button) findViewById(R.id.test_but);
		
		test_but.setOnClickListener(this);
		parts_but.setOnClickListener(this);
		auto_but.setOnClickListener(this);
		spec_but.setOnClickListener(this);
		stat_but.setOnClickListener(this);
		newad_but.setOnClickListener(this);
}

	public void onClick(View v) {

		Intent act_intent = new Intent();

		switch (v.getId()) {
		case R.id.test_but:
			// this is a test activity, right now not active
			// spec_intent.setClass(v.getContext(), RpActivity.class);
			break;
		case R.id.newad_but:
			act_intent.setClass(v.getContext(), LoginActivity.class);
			break;
		case R.id.parts_but:
			act_intent.setClass(v.getContext(), PartsActivity.class);
			break;
		case R.id.auto_but:
			act_intent.setClass(v.getContext(), AutoActivity.class);
			break;
		case R.id.spec_but:
			act_intent.setClass(v.getContext(), SpecActivity.class);
			break;
		case R.id.stat_but:
			act_intent.setClass(v.getContext(), LoginActivity.class);
			break;
  
	     }
		startActivity(act_intent);
	   }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_layout, menu);
		return true;
	}

}
