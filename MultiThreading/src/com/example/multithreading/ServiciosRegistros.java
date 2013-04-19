package com.example.multithreading;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServiciosRegistros extends Service {

	public static final String CT_TAG = "ServiciosRegistros";
	public static final String CT_EXTRA_VALORES = "ExtraValores";
	public static final String CT_BROADCAST_ACTION  = "com.example.multithreading.record_processed";
	
	public static final String CT_EXTRA_PROCESADOS = "registrosProcesados";
	public static final String CT_EXTRA_PENDIENTES = "registrosPendientes";
	
	
	int mRegistrosProcesados = 0;
	int mRegistrosPendientes = 0;
	
	Procesador mProc;

	class Procesador extends AsyncTask<Integer, Integer, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			
			
			while (mRegistrosPendientes>0&& !isCancelled()){
				try{
					Thread.sleep(3000);
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
				synchronized (this) {
					mRegistrosPendientes--;	
				}
				
				publishProgress();
				
				
				if (!isCancelled())
					Log.i(CT_TAG,"Registro procesado. Quedan "+mRegistrosPendientes);
				
			}
				
			stopSelf();
			return null;
		}
		
		

		@Override
		protected void onPostExecute(Integer result) {
			if (mRegistrosPendientes>0){
				iniciarTareaAsincrona();
			}else{
				stopSelf();
				mProc = null;
				stopForeground(true);
			}	
			super.onPostExecute(result);
		}



		@Override
		protected void onProgressUpdate(Integer... values) {
			
			mRegistrosProcesados++;
			
			Intent i = new Intent();
			i.setAction(CT_BROADCAST_ACTION);
			i.putExtra(CT_EXTRA_PENDIENTES, mRegistrosPendientes);
			i.putExtra(CT_EXTRA_PROCESADOS, mRegistrosProcesados);
			sendBroadcast(i);
			
			super.onProgressUpdate(values);
			
		}



		@Override
		protected void onCancelled(Integer result) {
			Log.i(CT_TAG, "onCancel invoke registros procesados "+ mRegistrosProcesados + " registrosPendientes " + mRegistrosPendientes);
		}
		
	}
	@Override
	public void onDestroy() {
		Log.i(CT_TAG, "onDestroy invoke registros procesados "+ mRegistrosProcesados + " registrosPendientes " + mRegistrosPendientes);
		super.onDestroy();
		if (mProc!=null){
			mProc.cancel(true);
		}	
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.i(CT_TAG, "onStartCommand invoke startId:"+ startId + " desde el thread"+ Thread.currentThread());
		synchronized (this) {
			mRegistrosPendientes += intent.getIntExtra(CT_EXTRA_VALORES, 0);
		}	
		if (mProc==null){
			iniciarTareaAsincrona();
			//Pasamos el servicio como foreground (maxima prioridad)
			Notification not = new Notification(android.R.drawable.ic_media_play,"Iniciado servicio de registros",System.currentTimeMillis());
			Intent i = new Intent(this,AMT_MultiThreading.class);
			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
			not.setLatestEventInfo(this, "Servicio Registro","Se estan procesando registros", pi);
			
			startForeground(1,not);
					
		}	
		return START_NOT_STICKY;
	}

	private void iniciarTareaAsincrona() {
		
		mProc = new Procesador();
		mProc.execute(0);
		Log.i(CT_TAG,"Inicio la tarea del servicio");
		
	}

	public class RegistrosBinder extends Binder {
		public boolean isRunning(){
			return mProc !=null;
		}
		public int getPendientes(){
			return mRegistrosPendientes;
		}
		public int getProcesados(){
			return mRegistrosProcesados;
		}
	}

	RegistrosBinder mRegistrosBinder = new RegistrosBinder(); 
	@Override
	public IBinder onBind(Intent arg0) {
		
		return mRegistrosBinder;
	}

	protected void acabarTrabajo(){
		stopSelf();
	}
}
