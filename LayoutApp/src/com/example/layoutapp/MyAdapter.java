package com.example.layoutapp;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	int mVistasTotales = 0;
	int mVistasUsadas = 0;
	Activity mActivity;
	
	public MyAdapter(Activity act){
		mActivity = act;
	}
	
	@Override
	public int getCount() {
		
		return 10000;
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class DatosTag {
		TextView t1;
		TextView t2;
		int id;
		
		DatosTag(TextView t1,TextView t2,int id){
			this.t1 = t1;
			this.t2 = t2;
			this.id = id;
		}
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mVistasTotales++;
		
		if (convertView == null){
			mVistasUsadas++;
			convertView = mActivity.getLayoutInflater().inflate(android.R.layout.simple_list_item_2, parent,false);
			convertView.setTag(new DatosTag((TextView)convertView.findViewById(android.R.id.text1), (TextView)convertView.findViewById(android.R.id.text2), mVistasUsadas));
		}	
		
		//convertView esta preparado para usarse
		DatosTag datos = (DatosTag)convertView.getTag();
		datos.t1.setText("Pos: " + position + " ** Vistas totales " + mVistasTotales);
		datos.t2.setText("Vistas creadas: " + mVistasUsadas + " ** ID:" + datos.id);
		
		return convertView;
	}

}
