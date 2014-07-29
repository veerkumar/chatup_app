package com.example.chatup_demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;

public class FriendlistClass extends Activity {
	
	static Context context;
	String Filename="FriendList.txt";
	Boolean filestatusresult=false;
	ArrayList<String> username=new ArrayList<String>();
	ArrayList<String> name=new ArrayList<String>();
	
	ListView friendlist;
	HashMap<String, String> hm = new HashMap<String, String>();
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
	int i=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist);
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(false);
		 friendlist=(ListView) findViewById(R.id.friendlist);
		 App.friendlist=this.friendlist;
		 MyAsyncTask.conset(this);
		 App.friendlistcontext=this;
		
	try {
		readfile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	friendlist.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
			Intent i= new Intent(FriendlistClass.this,ListviewAct.class);
			i.putExtra("name", App.friendusername.get(position));
			startActivity(i);
			
		}
	});
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.friend_list_menu, menu);

			// Associate searchable configuration with the SearchView
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
					.getActionView();
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));

			return super.onCreateOptionsMenu(menu);
		}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_search:
			// search action
			return true;
		
		
		case R.id.action_refresh:
			// refresh
			
			// load the data from server
			refresh();
			
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}

		
		
	}
public void refresh(){
	
		
		new MyAsyncTask().execute();
	}
public void cleanall(){
	username=new ArrayList<String>();
	name=new ArrayList<String>();
	App.friendname=new ArrayList<String>();
	App.friendusername=new ArrayList<String>();
	
}
public void  readfile() throws IOException, JSONException {
	ArrayList<String> friendusername=new ArrayList<String>();
	ArrayList<String> friendname=new ArrayList<String>();
	
	filestatusresult=	fileExistance(Filename);
		if(filestatusresult.equals(true)){
			cleanall();
			FileInputStream fis=openFileInput(Filename);
			BufferedInputStream bis=new BufferedInputStream(fis);
			
			StringBuffer b=new StringBuffer();
			while (bis.available()!=0) {
				char c=(char) bis.read();
				b.append(c);
				
			}
			JSONObject jsonObject = new JSONObject(b.toString());
			 if(jsonObject.getString("status").contains("true")){
			      Log.i("status","true");
											JSONArray jsonArray = jsonObject.getJSONArray("list");
											
											
											
										    for (int i = 0; i < jsonArray.length(); i++) {
										        JSONObject explrObject = jsonArray.getJSONObject(i);
										        
										      friendusername.add(explrObject.getString("username"));
										      friendname.add(explrObject.getString("name"));
										      Log.i("name",""+explrObject.getString("name"));
										    
										}
										   
												Log.i("friendusers size",""+friendusername.size());
												//Log.i("alist before",""+articletitle.get(13));
								
										   
												
												App.friendusername=friendusername;
												App.friendname=friendname;
												
												
												
			     }
			Shu_FriendlistAdapter adapter=new Shu_FriendlistAdapter(getApplicationContext());
			App.friendlist.setAdapter(adapter);
		}
		
	}
public boolean fileExistance(String fname){
    File file = getFileStreamPath(fname);
    Log.i("file exits",""+file.exists());
    return file.exists();
}
}

