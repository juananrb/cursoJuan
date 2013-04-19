package com.example.multithreading;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
	
	protected static final String CT_TAG = "MyReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		int registrosPendientes = intent.getIntExtra(ServiciosRegistros.CT_EXTRA_PENDIENTES,0);
		int registrosProceados = intent.getIntExtra(ServiciosRegistros.CT_EXTRA_PROCESADOS,0);
		Log.i(CT_TAG,"Actualizacion del servicio (" + registrosPendientes + "," + registrosProceados + ")");
	}

}
