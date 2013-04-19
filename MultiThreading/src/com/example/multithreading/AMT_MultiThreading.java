package com.example.multithreading;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AMT_MultiThreading extends Activity {
	
	ProcesadorBatch mPB;
	private final static String CT_TAG = "AMT_MultiThreading";
	int registrosProcesados = 0;
	TextView mTVMsg1;
	TextView mTVMsg2;
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amt_multi_threading);
		
		mTVMsg1 = (TextView)findViewById(R.id.amt_tv_msg1);
		mTVMsg2 = (TextView)findViewById(R.id.amt_tv_msg2);
		
		findViewById(R.id.amt_btn_rapido).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				funcionRapida();
				
			}
		});
		
		findViewById(R.id.amt_button_lento).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				funcionLenta();
				
			}
		});
		
		findViewById(R.id.amt_button_lentoAsync).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				functionAsyncTask();
				
			}

			
		});
		
		findViewById(R.id.amt_button_initSR).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				iniciarServicio();
				
			}

			
		});
		
		findViewById(R.id.amt_button_stopSR).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pararServicio();
				
			}

			
		});
		
		findViewById(R.id.amt_btn_conecta).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				conectaServicio();
				
			}

		

			
		});
		
		findViewById(R.id.amt_btn_desconecta).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				desconectaServicio();
				
			}

			
			
		});
		
		findViewById(R.id.amt_btn_refresca).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				refrescaServicio();
				
			}

			
		});
	}
	
	
	ServiciosRegistros.RegistrosBinder mConexion;
	ServiceConnection mServicio = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mConexion = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mConexion = (ServiciosRegistros.RegistrosBinder)service;
		}
	};
	
	protected void conectaServicio() {
		
		bindService(new Intent(this,ServiciosRegistros.class),mServicio, BIND_AUTO_CREATE);
		
	}
	
	protected void desconectaServicio() {
		if (mConexion != null){
			unbindService(mServicio);
		}	
		mConexion = null;
		
	}
	
	protected void refrescaServicio() {
		
		if (mConexion != null){
			if (mConexion.isRunning()){
				mTVMsg2.setText("Actualizacion del servicio( " + mConexion.getPendientes() + "," + mConexion.getProcesados());
			}else{
				mTVMsg2.setText("El servicio está parado");
			}
		}
		
	}
	
	

	private void refrescaRegistros(int i){
		/*
		synchronized (this) {
			
		}*/
		
		registrosProcesados += i;
		mTVMsg1.setText("Registros procesados " + registrosProcesados);
	}
	
	private void funcionLenta(){
		
		Thread lentorro = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.i(CT_TAG,"Soy lentorro mi nombre es:" + Thread.currentThread().getName());
				try{
					Thread.sleep(10000);
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
			
				runOnUiThread(new Runnable() {
					public void run() {
						refrescaRegistros(100);		
					}
				});
			}
			
			
			
			
		});
		
		Log.i(CT_TAG,"Voy a iniciar a lentorro. Soy:" + Thread.currentThread().getName());
		lentorro.start();
		
	}
	
	
	
	private void funcionRapida(){
		refrescaRegistros(1);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.amt_multi_threading, menu);
		return true;
	}
	
	
	//Generic consructor: Params,progress,result(doInBackground,onPostExecute)
	class ProcesadorBatch extends AsyncTask<Integer, Integer, String> {

		protected static final String CT_TAG_ASYNC = "ProcesadorBatch";
		
		@Override
		protected void onPostExecute(String result) {
			Log.i(CT_TAG_ASYNC,"onPostExecute invoke: " +Thread.currentThread().getName() + " resultat:" + result);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			Log.i(CT_TAG_ASYNC,"onPreExecute invoke: "+Thread.currentThread().getName());
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Integer... params) {
			Log.i(CT_TAG_ASYNC,"doInBackground invoke: "+Thread.currentThread().getName());
			int ciclos = params[0];
			int registrosCiclo = params[1];
			for (int i=0;i< ciclos && !isCancelled();i++){
				try {
					Thread.sleep(1000);
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
				publishProgress(registrosCiclo);
			}
			mPB = null;
			Log.i(CT_TAG_ASYNC,"doInBackground finish: "+Thread.currentThread().getName());
			return isCancelled()?"Cancelado":"Finalizado";
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			Log.i(CT_TAG_ASYNC,"onProgressUpdate invoke: "+Thread.currentThread().getName());
			for (Integer prog: values){
				refrescaRegistros(prog);
			}
		}

		@Override
		protected void onCancelled() {
			Log.i(CT_TAG_ASYNC,"onCancelledX invoke: "+Thread.currentThread().getName());
			super.onCancelled();
		}

		@Override
		protected void onCancelled(String result) {
		
			Log.i(CT_TAG_ASYNC,"onCancelled invoke: "+Thread.currentThread().getName());
			super.onCancelled();
		}
		
		
		
	}
	
	private void functionAsyncTask() {
		
		if (mPB == null){
			mPB = new ProcesadorBatch();
			mPB.execute(10,100);
		} 
		Toast.makeText(this,"La tarea ya está en marcha",Toast.LENGTH_SHORT).show();
	}
	
	protected void onDestroy(){
		super.onDestroy();
		if (mPB !=null){
			mPB.cancel(true);
		}
		
	}
	
	protected void iniciarServicio(){
		Intent i =new Intent(this,ServiciosRegistros.class);
		i.putExtra(ServiciosRegistros.CT_EXTRA_VALORES, 10);
		startService(i);
	}
	
	protected void pararServicio(){
		stopService(new Intent(this,ServiciosRegistros.class));
	}
	

	
	@Override
	protected void onStart() {
		super.onStart();
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ServiciosRegistros.CT_BROADCAST_ACTION);
		
		registerReceiver(broadcastReceiver, intentFilter);
	}

	@Override
	protected void onStop() {
		
		super.onStop();
		unregisterReceiver(broadcastReceiver);
		
	}


	//Suscripcion al evento
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			int registrosPendientes = intent.getIntExtra(ServiciosRegistros.CT_EXTRA_PENDIENTES,0);
			int registrosProceados = intent.getIntExtra(ServiciosRegistros.CT_EXTRA_PROCESADOS,0);
			
			mTVMsg2.setText("Actualizacion del servicio (" + registrosPendientes + "," + registrosProceados + ")");
			
			
		}
	};
	
}
