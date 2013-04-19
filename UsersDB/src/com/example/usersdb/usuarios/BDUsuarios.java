package com.example.usersdb.usuarios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BDUsuarios extends SQLiteOpenHelper {

	private final static String CT_BD = "usuarios.bd";
	private final static int CT_VERSION = 3;
	
	public BDUsuarios(Context context) {
		super(context, CT_BD, null, CT_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		UsuariosDAO.createTable(db);

	}
	
	

	

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		UsuariosDAO.dropTable(arg0);
		UsuariosDAO.createTable(arg0);
	}

}
