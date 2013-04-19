package com.example.adivinator;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AMMain extends Activity {

	private final static int CT_RESET = 1;
	private final static int CT_EDITAR = 2;
	private final static String CT_TAG = "AMMAin";
	private final static String CT_RANGO = "rango";
	private final static String CT_INCOGNITA = "incognita";
	private final static String CT_INTENTOS = "intents";
	EditText etValor;
	EditText diRango;
	TextView tvMensaje1;
	TextView tvMensaje2;
	//Button btPrueba;
	/*
	class PruebaListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			computaIntento();
		}
		
	}*/
	
	int mIncognita;
	int mIntentos;
	int mRango = 100;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d(CT_TAG,"onCreate invoke");
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.am_main);
		
		etValor = (EditText)findViewById(R.id.am_et_valor);
		tvMensaje1 = (TextView)findViewById(R.id.am_tv_mensaje1);
		tvMensaje2 = (TextView)findViewById(R.id.am_tv_mensaje2);
//		btPrueba = (Button)findViewById(R.id.am_bt_prueba);
		
//		PruebaListener myListener = new PruebaListener();
//		btPrueba.setOnClickListener(myListener);

		if (savedInstanceState==null){
			restaurarConfiguracion();
		}	
		
		inicializaIncognita(savedInstanceState);
		
		findViewById(R.id.am_bt_prueba).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				computaIntento();
			}

		});
		
	}
	
	private void restaurarConfiguracion(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		String max = sharedPref.getString(getResources().getString(R.string.pref_valor_maximo), "100");
		
		try{
			mRango = Integer.valueOf(max);
		}catch(Exception ex){
			mRango = 100;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.me_menu_reset:
				showDialog(CT_RESET);
				//txTexto.setText("Hola");
				break;
			case R.id.me_menu_edit_range:
				showDialog(CT_EDITAR);
				
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {

		switch(id){
			case CT_RESET:
				AlertDialog.Builder bldr = new AlertDialog.Builder(this);
				bldr.setTitle(R.string.di_txtReset);
				bldr.setMessage(R.string.di_txtReiniciar);
				bldr.setPositiveButton(R.string.di_ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						inicializaIncognita(null);	
					}
				});
				bldr.setNegativeButton(R.string.di_cancel, null);
				return bldr.create();
			case CT_EDITAR:
				AlertDialog.Builder bldr2 = new AlertDialog.Builder(this);
				bldr2.setTitle(R.string.di_txtEditar);
				bldr2.setPositiveButton(R.string.di_ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
							
						mRango =Integer.valueOf(diRango.getText().toString()); 
						inicializaIncognita(null);
					}
				});
				bldr2.setNegativeButton(R.string.di_cancel, null);
				//Incrustar layout
				LayoutInflater li = getLayoutInflater();
				View v = li.inflate(R.layout.di_main, null);
				diRango = (EditText)v.findViewById(R.id.di_rango);
				bldr2.setView(v);
				
				return bldr2.create();	
				
			
				
		}
		return super.onCreateDialog(id);
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
	protected void onRestart() {
		Log.d(CT_TAG,"onRestart invoke");
		super.onRestart();
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(CT_INCOGNITA, mIncognita);
		outState.putInt(CT_INTENTOS, mIntentos);
		outState.putInt(CT_RANGO,mRango);
		Log.d(CT_TAG,"onSaveInstanceState invoke");

	}

	private void inicializaIncognita(Bundle savedInstanceState) {
		if (savedInstanceState==null){
			mIncognita = new Random().nextInt(mRango)+1;
			mIntentos = 0;
			etValor.setText("");
			tvMensaje1.setText("");
			tvMensaje2.setText("");
			
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor spe = sp.edit();
			spe.putString(getResources().getString(R.string.pref_valor_maximo), Integer.toString(mRango));
			spe.commit();
		}else{
			mIncognita = savedInstanceState.getInt(CT_INCOGNITA);
			mIntentos = savedInstanceState.getInt(CT_INTENTOS);
			mRango = savedInstanceState.getInt(CT_RANGO);
		}
		Log.d(CT_TAG,"Incognita:" +mIncognita);
	}
	
	private void computaIntento(){
		
		try{
			int iIncognita = Integer.valueOf(mIncognita).intValue();
			int iActual = Integer.valueOf(etValor.getText().toString()).intValue();
			
			if (iIncognita<iActual){
				tvMensaje1.setText(R.string.am_numero_menor);
				mIntentos++;
			}else if (iIncognita>iActual){
				tvMensaje1.setText(R.string.am_numero_mayor);
				mIntentos++;
			}else{
				tvMensaje1.setText(R.string.am_numero_igual);
				mIntentos++;
				creaNotificacion();
			}
			String texto = getResources().getQuantityString(R.plurals.am_numero_intentos,mIntentos,mIntentos);
			tvMensaje2.setText(texto);
			
		}catch(Exception e){
			tvMensaje1.setText(R.string.am_valid_value);
			tvMensaje2.setText("");
		}
	}
	
	private void creaNotificacion(){

		Notification not = new Notification(R.drawable.ic_launcher,"Incognita acertada",System.currentTimeMillis());
		Intent i = new Intent(this,AMMain.class);
		PendingIntent pi =PendingIntent.getActivity(this, 0, i, Notification.FLAG_AUTO_CANCEL);
		not.setLatestEventInfo(this,"Incognita", "Se acerto la incognita", pi);
		
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		nm.notify(1, not);
		
	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.me_main, menu);
		return true;
	}
}

