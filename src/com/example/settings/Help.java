package com.example.settings;

import java.util.ArrayList;
import com.example.chatup_demo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Help extends Activity{

	ArrayList<String> array;
	ListView settingsList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		settingsList = (ListView)findViewById(R.id.settingsList);
		array = new ArrayList<String>();
		Intent intent = getIntent();
		String label = intent.getStringExtra("label");
		
		if(label.equals("Help")){
			array.add("About");
			array.add("FAQ");
			array.add("System Status");
			array.add("Contact Us");
		}
		if(label.equals("Account")){
			array.add("Privacy");
			array.add("Change Number");
			array.add("Delete My Account");
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.setting_content,R.id.SettingsText,array);
		settingsList.setAdapter(adapter);
		settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Click Item"+position, Toast.LENGTH_SHORT).show();
			}
		});
	}

	
}
