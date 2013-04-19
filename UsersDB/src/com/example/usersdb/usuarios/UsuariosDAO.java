package com.example.usersdb.usuarios;

import java.util.Date;
import java.util.Vector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsuariosDAO {

	public static final String CT_TAG = "UsuariosDAO";
	public static final String CT_TABLA = "tbl_usuarios";

	public static final String CT_ID = "idUser";
	public static final String CT_NOMBRE = "nombre";
	public static final String CT_APELLIDOS = "apellidos";
	public static final String CT_FECHA_NAC = "fecha_nac";
	
	protected static final String[] CT_CAMPOS = {CT_ID,CT_NOMBRE,CT_APELLIDOS,CT_FECHA_NAC};
	SQLiteDatabase mDB;
	
	public static void createTable(SQLiteDatabase db) {
		
		String sql = "CREATE TABLE " + CT_TABLA + "(" + CT_ID + " INTEGER PRIMARY KEY,"
					+ CT_NOMBRE + " STRING ," + CT_APELLIDOS + " STRING," +
					CT_FECHA_NAC + " INTEGER)";
		try{
			db.execSQL(sql);
			Log.i(CT_TAG,"success create table");
		}catch (Exception ex){
			Log.e(CT_TAG,ex.getMessage());
		}
		
	}
	
	public static void dropTable(SQLiteDatabase db) {
		
		String sql = "DROP TABLE " + CT_TABLA;
		try{
			db.execSQL(sql);
			Log.i(CT_TAG,"drop table");
		}catch (Exception ex){
			Log.e(CT_TAG,ex.getMessage());
		}
		
	}
	
	
	
	public UsuariosDAO(SQLiteDatabase db){
		mDB = db;
	}
	
	public long delete(long id) {
		int result = 0;
		try {
			result = mDB.delete(CT_TABLA,  CT_ID + " = ?", new String[]{String.valueOf(id)} );
		}catch(Exception ex){
			Log.e(CT_TAG,ex.getMessage());
		}
		return result;
	}
	
	public long delete(Usuario user) {
		return this.delete(user.getmId());
	}
	
	public long insertar(Usuario user){
		
		ContentValues cv = getCVFromUser(user);
		
		try{
			return mDB.insert(CT_TABLA, null, cv);
		}catch(Exception ex){
			Log.e(CT_TAG,ex.getMessage());
		}
		
		return -1;
	 
	}

	private ContentValues getCVFromUser(Usuario user) {
		ContentValues cv = new ContentValues();
		cv.put(CT_NOMBRE,user.getmNombre());
		cv.put(CT_APELLIDOS,user.getmApellidos());
		cv.put(CT_FECHA_NAC,user.getmFechaNacimiento().getTime());
		return cv;
	}
	
	public long update(Usuario user){
		
		ContentValues cv = getCVFromUser(user);
		
		
		try{
			return mDB.update(CT_TABLA, cv, CT_ID + " = ?", new String[]{String.valueOf(user.getmId())} );
		}catch(Exception ex){
			Log.e(CT_TAG,ex.getMessage());
		}
		return 0;
		
		
	}
	
	public Usuario getUser(long userID){
		
		Usuario user = null;
		Cursor cursor = null;
		try{
			
			cursor = mDB.query(CT_TABLA,CT_CAMPOS, CT_ID + " = ?", new String[]{String.valueOf(userID)},null,null,null);
			if (cursor.moveToFirst()){
				user = getUsuarioFromCursor(cursor);
			}
			return user;
		}catch(Exception ex){
			Log.e(CT_TAG,ex.getMessage());
			
		}finally{
			if (cursor!=null)
				cursor.close();
		}
		
		return null;
		
	}

	public Vector<Usuario> getAll(){
		
		Cursor cursor = null;
		try{
			Vector<Usuario> vector = new Vector<Usuario>();
			
			cursor = mDB.query(CT_TABLA,CT_CAMPOS,null,null,null,null,CT_APELLIDOS + ","+ CT_NOMBRE);
			if (cursor.moveToFirst()){
				do {
					vector.add(getUsuarioFromCursor(cursor));
				}while (cursor.moveToNext());
			}
			return vector;
		}catch(Exception ex){
			Log.e(CT_TAG,ex.getMessage());
			
		}finally{
			if (cursor!=null)
				cursor.close();
		}
		
		return null;
		
	}

	private Usuario getUsuarioFromCursor(Cursor cursor) {

		Usuario user = new Usuario();
		user.setmId(cursor.getLong(cursor.getColumnIndex(CT_ID)));
		user.setmNombre(cursor.getString(cursor.getColumnIndex(CT_NOMBRE)));
		user.setmApellidos(cursor.getString(cursor.getColumnIndex(CT_APELLIDOS)));
		user.setmFechaNacimiento(new Date(cursor.getLong(cursor.getColumnIndex(CT_FECHA_NAC))));
		
		return user;
		
	}
}
