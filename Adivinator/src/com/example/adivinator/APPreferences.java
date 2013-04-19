package com.example.adivinator;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class APPreferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref_settings);
	}

	
}
