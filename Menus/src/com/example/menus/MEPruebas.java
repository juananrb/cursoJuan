package com.example.menus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class MEPruebas extends Activity {

	private final static int CT_SEGURO_BORRAR = 1;
	private final static int CT_CAMBIA_TEXTO = 2;
	
	TextView txTexto;
	TextView meTexto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_pruebas);
		
		txTexto = (TextView)findViewById(R.id.tx_texto);
		
		registerForContextMenu(txTexto);
		
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.me_menu_hola:
				showDialog(CT_CAMBIA_TEXTO);
				//txTexto.setText("Hola");
				break;
			case R.id.me_menu_adios:
				txTexto.setText("Adios");
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	

	@Override
	public boolean onContextItemSelected(MenuItem item) {
 
		switch(item.getItemId()){
			case R.id.me_menu_hola:
				showDialog(CT_SEGURO_BORRAR);
				//txTexto.setText("Hola contextual");
				break;
			case R.id.me_menu_adios:
				txTexto.setText("Adios contextual");
				break;
			default:
				return super.onContextItemSelected(item);		
		}
		
		return true;
	}


	@Override
	protected Dialog onCreateDialog(int id) {

		switch(id){
			case CT_SEGURO_BORRAR:
//				AlertDialog.Builder bldr = new AlertDialog.Builder(this);
//				bldr.setTitle("Borrar");
//				bldr.setMessage("¿Seguro que desea borrar el texto?");
//				bldr.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						txTexto.setText("");	
//						
//					}
//				});
//				bldr.setNegativeButton("Cancelar", null);
//				return bldr.create();
				DatePickerDialog dlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						Toast.makeText(MEPruebas.this,"Se ha seleccionado una fecha del año" +year, Toast.LENGTH_LONG).show();
						
					}
					
				}, 2013, 3, 11);
				return dlg;
				
			case CT_CAMBIA_TEXTO:
				AlertDialog.Builder bldr2 = new AlertDialog.Builder(this);
				bldr2.setTitle("Cambia texto");
				bldr2.setMessage("¿Introduzca el texto para el primer textview?");
				bldr2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						txTexto.setText(meTexto.getText().toString());	
						
					}
				});
				bldr2.setNegativeButton("Cancelar", null);
				//Incrustar layout
				LayoutInflater li = getLayoutInflater();
				View v = li.inflate(R.layout.me_dialog, null);
				meTexto = (TextView)v.findViewById(R.id.me_text);
				bldr2.setView(v);
				
				return bldr2.create();
				
		}
		return super.onCreateDialog(id);
	}


	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id){
			case CT_CAMBIA_TEXTO:
				meTexto.setText(txTexto.getText());
				break;
		}		
		super.onPrepareDialog(id, dialog);
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.me_menu, menu);
		
	}



	int mContador = 0;
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Toast.makeText(this,"onPrepareOptionsMenu", Toast.LENGTH_SHORT).show();
		mContador++;
		menu.findItem(R.id.me_menu_adios).setEnabled(mContador%2==0);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.me_menu, menu);
		return true;
	}

}
