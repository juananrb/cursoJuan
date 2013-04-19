package com.example.usersdb;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.example.usersdb.usuarios.BDUsuarios;
import com.example.usersdb.usuarios.Usuario;
import com.example.usersdb.usuarios.UsuariosDAO;

public class AUL_UserList extends Activity {
	
	private final static String CT_TAG = "AUL_UserList";
	public final static String CT_KEY_ID = "AUL_KeyID";
	SQLiteDatabase mDB;
	UsuariosDAO mUsuarioDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aul_user_list);
		
		mDB = new BDUsuarios(this).getWritableDatabase();
		mUsuarioDAO = new UsuariosDAO(mDB);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aul_user_list, menu);
		return true;
	}

	public void hazAccion(View v) {
		
		invokeAlta();
		
		
	}
	
	public void hazQuery(View v) {
		
		Vector<Usuario> vector = mUsuarioDAO.getAll();
		for (Usuario u : vector){
			Log.i(CT_TAG,u.toString());
		}
	}
	
	public void hazUpdate(View v){
		
		invokeEdicio(new Long(1));
		
	}
	
	
	private void invokeAlta(){
		
		Intent i = new Intent(this,AUL_EdicioUser.class);
		startActivity(i);
		
	}
	private void invokeEdicio(Long userId){
		
		//Explicit
		Intent i = new Intent(this,AUL_EdicioUser.class);
		i.putExtra(CT_KEY_ID,userId);
		
		startActivity(i);
		
	}
	
}
