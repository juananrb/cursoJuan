package com.example.practiceintents;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AIIntents extends Activity {

	private static final int CT_REQUEST_SEGUNDA = 0;
	public static final String CT_SEGUNDA_RESULTADO = "KK";
	Button mBtnNavegar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ai_intents);
		
		findViewById(R.id.ai_btn_navegar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				navegar();
				
			}
		});
		
		findViewById(R.id.ai_btn_abrirseguna).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				abrirSegunda();
				
			}
		});
		
		findViewById(R.id.ai_btn_lllamar).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				llamar();
				
			}
		});
		
		findViewById(R.id.ai_btn_contactos).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				accederAContactos();
				
			}
		});
	
	}

	protected void llamar() {

		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:666337777"));
		startActivity(intent);
		
	}

	
	protected void abrirSegunda() {

		Intent intent = new Intent("com.example.pruebasvarias.segundaactividad");
		startActivityForResult(intent, CT_REQUEST_SEGUNDA);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		case CT_REQUEST_SEGUNDA:
			if (resultCode == RESULT_CANCELED){
				Toast.makeText(this, "la actividad se canccela",Toast.LENGTH_SHORT).show();
			}else if (resultCode == RESULT_OK){
				Toast.makeText(this, "la actividad devolvio resultado:" + data.getStringExtra(CT_SEGUNDA_RESULTADO),Toast.LENGTH_SHORT).show();
			}else if (resultCode == RESULT_FIRST_USER){
				//... el RESULT_FIRST_USER sería el primer código que se podría ir incrementando para utilizarse por el desarrollador
				
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void accederAContactos(){
		Cursor cur= getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,null,null,null);
	
		startManagingCursor(cur);
	
		String[] result=new String[cur.getCount()];
	
		for (boolean hasData = cur.moveToFirst(); hasData; hasData = cur.moveToNext())
		{
		    int nameidx=cur.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
		    int Ididx=cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
		    String strName=cur.getString(nameidx);
		    String strId=cur.getString(Ididx);
	
		    result[cur.getPosition()]=strName+"("+strId+")";
		}
		stopManagingCursor(cur);
		
		
	}


	protected void navegar() {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://www.google.com"));
		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ai_intents, menu);
		return true;
	}

}
