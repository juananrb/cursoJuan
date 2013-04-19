package com.example.adivinator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AEMenu extends Activity {
	
	private final static String CT_TAG = "AEMenu";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(CT_TAG,"onCreate invoke");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ae_menu);
		
		findViewById(R.id.ae_button ).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				invokeAdivinator();
			}

		});
	
		findViewById(R.id.button_settings).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				invokeConfigurator();
			}

		});
		
	}
	
	@Override
	protected void onRestart() {
		Log.d(CT_TAG,"onRestart invoke");
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		Log.d(CT_TAG,"onDestroy invoke");
		super.onDestroy();
		
		
	}

	@Override
	protected void onPause() {
		Log.d(CT_TAG,"onPause invoke");
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.d(CT_TAG,"onResume invoke");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.d(CT_TAG,"onStart invoke");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.d(CT_TAG,"onStart invoke");
		super.onStop();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.d(CT_TAG,"onSaveInstanceState invoke");
		
		super.onSaveInstanceState(outState);
	}

	

	private void invokeAdivinator(){
		
		//Explicit
		Intent i = new Intent(this,AMMain.class);
		startActivity(i);
		
	}
	
	private void invokeConfigurator(){
		Intent i = new Intent(this,APPreferences.class);
		startActivity(i);
	}

}
