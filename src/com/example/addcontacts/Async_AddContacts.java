package com.example.addcontacts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;

public class Async_AddContacts extends AsyncTask<Void, Void, Void>{
	public String IP = "http://192.168.2.100";
	ArrayList<HashMap<String, String>> list;
	String username;
	int len;
	String user;//Contact list's usernames
	String phone;
	Activity activity;
	int i;
	public Async_AddContacts(ArrayList<HashMap<String, String>> list,String username,Activity activity) {
		// TODO Auto-generated constructor stub
		this.list=list;
		len = list.size();
		this.username = username; 
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(Void... params){
		// TODO Auto-generated method stub
		//Log.i("in ansync","task  "+username);
		for(i=0;i<len;i++){
			phone = list.get(i).get("phone");
			user = list.get(i).get("name");
			if (phone!= null || phone!="") {

		        Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
		        String[] mPhoneNumberProjection = { PhoneLookup._ID, PhoneLookup.NUMBER, PhoneLookup.DISPLAY_NAME };
		        Cursor cur = activity.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
		        try {
		            if (cur.moveToFirst()) {
		            	String name1 = cur.getString(cur.getColumnIndex(PhoneLookup.DISPLAY_NAME));
		            	Log.i("name: ",name1);
		            	DefaultHttpClient httpclient = new DefaultHttpClient();
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
						StrictMode.setThreadPolicy(policy);
						HttpGet httpget = new HttpGet(IP+":9090/plugins/userService/userservice?type=add_roster&secret=veer&username="+username+"&item_jid="+user+"@openfire&name="+user+"&subscription=3&groups=friends");
						HttpResponse response = null;
						response = httpclient.execute(httpget);
						httpget = new HttpGet(IP+":9090/plugins/userService/userservice?type=add_roster&secret=veer&username="+user+"&item_jid="+username+"@openfire&name="+username+"&subscription=3&groups=friends");
						response = httpclient.execute(httpget);
						System.out.println(name1+" added");
		            }
		        } catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
		            if (cur != null)
		                cur.close();
		        }
		    }
			}
		return null;
	}

}
