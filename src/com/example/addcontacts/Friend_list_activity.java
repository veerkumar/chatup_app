package com.example.addcontacts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.example.chatup_demo.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.example.chatup_demo.App;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Friend_list_activity extends Activity{
	
	public ContactListAdapter friendlistadapter;
	public String IP = "http://192.168.2.100"; 
	String username=App.username;//for whose friend list to be fetched
	File root,file,outputfile;
	Find_Contact obj;
	Writer writer;
	Activity activity;
	App app;
	Intent starterIntent ;
	int j;
	int delay = 15000;
	Timer timer = new Timer();
	NetworkChangeReceiver receiver;
	IntentFilter filter;
	ListView listview;
	HashMap<String, String> hm = new HashMap<String, String>();
	ArrayList<HashMap<String, String>> list;
	int i=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_list);
		activity =this;
		
			//App.status=false;
		obj = new Find_Contact();
		filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
		receiver = new NetworkChangeReceiver();
		registerReceiver(receiver, filter);
		receiver.onReceive(this, new Intent(ConnectivityManager.CONNECTIVITY_ACTION));
		Log.i("status",""+App.status);
		Toast.makeText(getApplicationContext(), "status "+App.status, Toast.LENGTH_LONG).show();
		listview = (ListView)findViewById(R.id.lv);
		starterIntent = getIntent();
		root = Environment.getExternalStorageDirectory();
		file = new File(root.getAbsoluteFile()+File.separator+"/ChatUp");
		if(!file.isDirectory())
		{
			file.mkdir();
		}
		File file2 = new File(file.getAbsolutePath()+File.separator+"/Profile Photos");
		if(!file2.isDirectory())
		{
			file2.mkdir();
		}
		//String path = file.toString()+"/Contacts.txt";
		outputfile = new File(file,"Contacts.txt");
		if(!outputfile.exists())
			try {
				outputfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		localContacts();
		//if(App.status)
		//obj.fetchContacts(IP, username, activity);
		final Handler handler = new Handler();
		final Runnable r = new Runnable(){
			public void run() {
				// TODO Auto-generated method stub
				if(App.status)
				{
					updateContacts();
					localContacts();
					handler.postDelayed(this, delay);
				}
				
			}
		};
		handler.postDelayed(r, delay);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				popupWindow(list.get(position).get("username"),list.get(position).get("name"));
			}
		});
		}
	@Override
	 protected void onDestroy() {
	  super.onDestroy();
	  unregisterReceiver(receiver);
	 }
	public void updateContacts(){
		AsyncHttpClient client1 = new AsyncHttpClient();
		client1.get(IP+":60021/intecons/data.php?username="+username, new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(String response) {
				JSONObject output = null;
				try {
					output = new JSONObject(response);
					writer = new BufferedWriter(new FileWriter(outputfile));
					writer.write(response);
					writer.flush();
					writer.close();
				} catch (JSONException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				try {
					if(output.getString("status").equals("true")){
						
						list = new ArrayList<HashMap<String,String>>();
						JSONArray array = output.getJSONArray("list");
						int len=array.length();
						for(int i=0;i<len;i++)
						{
							JSONObject obj = array.getJSONObject(i);
							hm = new HashMap<String,String>();
							hm.put("username", obj.getString("username"));
							hm.put("name", obj.getString("name"));
							list.add(hm);
						}	
						friendlistadapter=new ContactListAdapter(Friend_list_activity.this, list, username,j);
						//listview.setAdapter(friendlistadapter);
					}
					else 
						Log.i("no users","found");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		});
	}	
	public void localContacts(){
		
			JSONObject output;
			String jsonstr;
			StringBuilder sb = new StringBuilder();
			if(outputfile.exists()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(outputfile));
				j=1;
				
				StringBuffer buffer = new StringBuffer();
				while((jsonstr = reader.readLine())!=null)
				{
					sb.append(jsonstr);
					sb.append('\n');
				}
				//Log.i("buffer: ",sb.toString());
				if(!sb.toString().contains("{}")){
				output = new JSONObject(sb.toString());
				
				//System.out.println(output);
				JSONArray array = output.getJSONArray("list");
				if(array!=null){
					list = new ArrayList<HashMap<String,String>>();
				int arrlen = array.length();
				for(int i=0;i<arrlen;i++)
				{
					JSONObject obj = array.getJSONObject(i);
					hm = new HashMap<String,String>();
					hm.put("username",obj.getString("username"));
					hm.put("name", obj.getString("name"));
					list.add(hm);
				}
				friendlistadapter=new ContactListAdapter(this, list, username,j);
				listview.setAdapter(friendlistadapter);
				}
				}
				else
					Toast.makeText(getApplicationContext(), "No more Contacts Available", Toast.LENGTH_LONG).show();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
			else
				Log.i("file","doesn't exist");
	}
	private void popupWindow(final String user2, String name){
		LayoutInflater li = LayoutInflater.from(this);
		View prompt = li.inflate(R.layout.alert_add_friend, null);
		name = name.substring(0,1).toUpperCase()+name.substring(1);
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		TextView tv = (TextView)prompt.findViewById(R.id.textView);
		tv.setText("Add "+name+" as your friend.");
		alertDialog.setView(prompt);
		
		alertDialog.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(App.status){
					DefaultHttpClient httpclient = new DefaultHttpClient();
					String user=username;
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
					HttpGet httpget = new HttpGet(IP+":9090/plugins/userService/userservice?type=add_roster&secret=veer&username="+user+"&item_jid="+user2+"@openfire&name="+user2+"&subscription=3&groups=friends");
					HttpResponse response = null;
					InputStream is=null;
					String jsonString = null;
					
						try {
							response = httpclient.execute(httpget);
						} catch (ClientProtocolException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					httpget = new HttpGet(IP+":9090/plugins/userService/userservice?type=add_roster&secret=veer&username="+user2+"&item_jid="+user+"@openfire&name="+user+"&subscription=3&groups=friends");
					policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
					try {
						response = httpclient.execute(httpget);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finish();
					startActivity(starterIntent);
				}
				else
					Toast.makeText(getApplicationContext(), "No internet Connection", Toast.LENGTH_LONG).show();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		AlertDialog alertDialog2 = alertDialog.create();
		alertDialog2.show();
	}
	
}