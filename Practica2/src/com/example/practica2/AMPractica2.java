package com.example.practica2;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class AMPractica2 extends Activity {

	private final static String CT_TAG = "AMPractica2";
	
	EditText etImporte;
	TextView tvPropina;
	TextView tvTotal;
	RadioButton rbDiezPorc;
	RadioButton rbQuincePorc;
	RadioButton rbVeintePorc;
	RadioButton rbVeinticincoPorc;
	RadioGroup controlGroup;
	Spinner spinner;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am_practica2);
		
		etImporte = (EditText)findViewById(R.id.am_importe);
		tvPropina = (TextView)findViewById(R.id.am_propina);
		tvTotal = (TextView)findViewById(R.id.am_total);
		controlGroup = (RadioGroup)findViewById(R.id.am_controlgroup);
		
		rbDiezPorc = (RadioButton)findViewById(R.id.am_diezporc);
		rbDiezPorc.setTag(Integer.valueOf(10));
		
		rbQuincePorc = (RadioButton)findViewById(R.id.am_qinceporc);
		rbQuincePorc.setTag(Integer.valueOf(15));
		
		rbVeintePorc = (RadioButton)findViewById(R.id.am_veinteporc);
		rbVeintePorc.setTag(Integer.valueOf(20));
		
		rbVeinticincoPorc = (RadioButton)findViewById(R.id.am_veinticincoporc);
		rbVeinticincoPorc.setTag(Integer.valueOf(25));
		
		spinner = (Spinner)findViewById(R.id.spinner1);
		// Carga de recursos a mano
//		ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
//		spAdapter.add("10%");
//		spAdapter.add("15%");
//		spAdapter.add("20%");
//		spAdapter.add("25%");
		
		//Carga de recursos de fichero
		ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.ap_array_strings_porcentajes));
		spinner.setAdapter(spAdapter);
		final int porcentajes[] = getResources().getIntArray(R.array.ap_array_integers_porcentajes);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
						
				int porc = porcentajes[arg2];
				calculaPropina(Integer.valueOf(porc));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		
		((EditText)findViewById(R.id.am_importe)).addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				View v = findViewById(controlGroup.getCheckedRadioButtonId());
				if (v!=null){
					calculaPropina((Integer)v.getTag());
				}	
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
		});
		
		controlGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
		
				View v = findViewById(checkedId);
				calculaPropina((Integer)v.getTag());
			
				
			}
		});
		
//		findViewById(R.id.am_diezporc).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				calculaPropina((Integer)v.getTag());			}
//
//		});
//		findViewById(R.id.am_qinceporc).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				calculaPropina((Integer)v.getTag());			}
//
//		});
//		findViewById(R.id.am_veinteporc).setOnClickListener(new View.OnClickListener() {
//	
//			@Override
//			public void onClick(View v) {
//				calculaPropina((Integer)v.getTag());			}
//		
//		});
//		findViewById(R.id.am_veinticincoporc).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				calculaPropina((Integer)v.getTag());			}
//		
//		});
//
		
	}
	
	private void calculaPropina(Integer porcentaje){
		
		if (porcentaje!=null){
			try{
				int iImporte = Integer.valueOf(etImporte.getText().toString()).intValue();
				int iPropina = iImporte*porcentaje.intValue()/100;
				tvPropina.setText(getResources().getString(R.string.am_propina,iPropina));
				tvTotal.setText(getResources().getString(R.string.am_total,iImporte+iPropina));	
			}catch(Exception e){
				Log.e(CT_TAG,e.getMessage());
			}
		}	
		
		
		
	}


}
