package com.example.layoutapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class APPruebas extends Activity {

	ListView mlvLista;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_listview);
		
		mlvLista = (ListView)findViewById(R.id.listView1);
		MyAdapter ad = new MyAdapter(this);
		mlvLista.setAdapter(ad);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ap_pruebas, menu);
		return true;
	}

}
