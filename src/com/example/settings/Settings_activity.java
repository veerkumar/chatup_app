package com.example.settings;

import java.util.ArrayList;
import com.example.chatup_demo.R;
import java.util.HashMap;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Settings_activity extends Activity {

	ListView list;
	SettingsAdapter adapter;
	HashMap<String, String> hash;
	ArrayList<HashMap<String, String>> hm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		hash = new HashMap<String, String>();
		hm= new ArrayList<HashMap<String, String>>();
		list = (ListView)findViewById(R.id.settingsList);

		hm= new ArrayList<HashMap<String, String>>();
		hash.put("settings","Help");
		hash.put("image","R.Drawable.ic_launcer");
		hm.add(hash);
		hash = new HashMap<String, String>();
		hash.put("settings", "Profile");
		hash.put("image","R.Drawable.ic_launcer");
		hm.add(hash);
		hash = new HashMap<String, String>();
		hash.put("settings", "Account");
		hash.put("image","R.Drawable.ic_launcer");
		hm.add(hash);
		list.setAdapter(new SettingsAdapter(this,hm));
		
	}

	
}