package com.example.usersdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.usersdb.usuarios.BDUsuarios;
import com.example.usersdb.usuarios.Usuario;
import com.example.usersdb.usuarios.UsuariosDAO;

public class AUL_EdicioUser extends Activity {

	
	private final static String CT_TAG = "AUL_EdicioUser";
	private final static int CT_DATE_PICKER_DIALOG = 1;
	
	SQLiteDatabase mDB;
	UsuariosDAO mUsuarioDAO;
	TextView tvID;
	EditText etNombre;
	EditText etApellidos;
	EditText etFechaNac;
	Long extraUserID=null;
	Button btFecha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aul_edicio_user);
		
		mDB = new BDUsuarios(this).getWritableDatabase();
		mUsuarioDAO = new UsuariosDAO(mDB);
		
		tvID = (TextView)findViewById(R.id.txt_edicio_id);
		etNombre = (EditText)findViewById(R.id.et_edicio_nombre);
		etApellidos = (EditText)findViewById(R.id.et_edicio_apellidos);
		etFechaNac = (EditText)findViewById(R.id.et_fechaNac);
		btFecha = (Button)findViewById(R.id.btnFechaNac);
		
		btFecha.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(CT_DATE_PICKER_DIALOG);
				
			}
		});
		
		extraUserID = this.getIntent().getExtras()!=null?this.getIntent().getExtras().getLong(AUL_UserList.CT_KEY_ID):null;
		if (extraUserID!=null){
			llenaTabla(extraUserID);
		}
	}
	
	private void llenaTabla(Long userID){
		
		Usuario user = mUsuarioDAO.getUser(userID.longValue());
		tvID.setText(String.valueOf(user.getmId()));
		etNombre.setText(String.valueOf(user.getmNombre()));
		etApellidos.setText(String.valueOf(user.getmApellidos()));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		etFechaNac.setText(sdf.format(user.getmFechaNacimiento()));
		
		
		
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		if (isFinishing()){
			if (extraUserID!=null){
				update();
			}else{
				alta();
			}
		}	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aul_user_list, menu);
		return true;
	}
	
	public void update() {
		
		Usuario u = fillUserFromTable();
		
		long result = mUsuarioDAO.update(u);
		creaNotificacion(u);
		
		Log.i(CT_TAG,"Usuario actualizado con ID:" + result);
		
	}

	public void alta() {
		
		Usuario u = fillUserFromTable();
		
		long result = mUsuarioDAO.insertar(u);
		creaNotificacion(u);
		
		Log.i(CT_TAG,"Usuario insertado con ID:" + result);
		
		
	}

	private Usuario fillUserFromTable() {
		Usuario u = new Usuario();
		if (extraUserID!=null){
			u.setmId(extraUserID);	
		}
		
		u.setmNombre(etNombre.getText().toString());
		u.setmApellidos(etApellidos.getText().toString());
		Calendar c = Calendar.getInstance();
		
		
		String fechaNac = etFechaNac.getText().toString();
		
		Date dateNac = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			dateNac = sdf.parse(fechaNac);
			u.setmFechaNacimiento(dateNac);
		}catch(ParseException pe){
			Log.i(CT_TAG,pe.getMessage());
		}
		
		return u;
	}
	
	public void hazQuery(View v) {
		
		Vector<Usuario> vector = mUsuarioDAO.getAll();
		for (Usuario u : vector){
			Log.i(CT_TAG,u.toString());
		}
	}
	
	public void hazUpdate(View v){
		
		Usuario u = new Usuario();
		u.setmId(1);
		u.setmNombre("Pepe");
		u.setmApellidos("Caco");
		Calendar c = Calendar.getInstance();
		c.set(1970,3,14);
		u.setmFechaNacimiento(c.getTime());
		
		long result = mUsuarioDAO.update(u);
		Log.i(CT_TAG,"Usuario actualizado con ID:" + result);
		
	}
	
	public void hazDelete(long userId){
		
		long result = mUsuarioDAO.delete(userId);
		Log.i(CT_TAG, "Usuario borrado con ID:"+ result);
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {

		switch(id){
			case CT_DATE_PICKER_DIALOG:
				DatePickerDialog dlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						Calendar c = Calendar.getInstance();
						c.set(year,monthOfYear,dayOfMonth);
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						etFechaNac.setText(sdf.format(c.getTime()));

						
					}
					
				}, 2013, 3, 11);
				return dlg;
				
				
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id){
			case CT_DATE_PICKER_DIALOG:
				Calendar c = Calendar.getInstance();
				
				String fechaNac = etFechaNac.getText().toString();
				Date dateNac = null;
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					dateNac = sdf.parse(fechaNac);
					c.setTime(dateNac);
					
					((DatePickerDialog)dialog).updateDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH), Calendar.DAY_OF_MONTH);
				}catch(ParseException pe){
					Log.i(CT_TAG,pe.getMessage());
				}
				
				break;
		}		
		super.onPrepareDialog(id, dialog);
	}
	
	private void creaNotificacion(Usuario usuario){

		Notification not = new Notification(R.drawable.ic_launcher,"Registro modificado",System.currentTimeMillis());
		Intent i = new Intent(this,AUL_EdicioUser.class);
		PendingIntent pi =PendingIntent.getActivity(this, 0, i, Notification.FLAG_AUTO_CANCEL);
		not.setLatestEventInfo(this,"Actualización de usuario", usuario.toString(), pi);
		
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		nm.notify(1, not);
		
	}
	

	
	
}
