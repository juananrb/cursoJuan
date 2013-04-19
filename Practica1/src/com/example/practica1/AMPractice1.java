package com.example.practica1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AMPractice1 extends Activity {

	TextView tvTexto;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.am_practice1);
		tvTexto = (TextView)findViewById(R.id.textView1);
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cambiaTexto();
			}
		});
		
	}
	
	private void cambiaTexto() {
		if (tvTexto.getText().toString().equals(getResources().getText(R.string.am_texto2).toString())){
			tvTexto.setText(R.string.am_texto1);
		}else{
			tvTexto.setText(R.string.am_texto2);
		}
		
	}
	

}
