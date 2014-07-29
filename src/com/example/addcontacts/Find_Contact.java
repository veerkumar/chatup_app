package com.example.addcontacts;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatup_demo.App;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Find_Contact extends Activity {

	public String IP = "http://192.168.2.100";
	//String username="test2";
	TextView name;
	int i;	
	Activity activity;
	HashMap<String, String> hm;
	ArrayList<HashMap<String, String>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list = new ArrayList<HashMap<String, String>>();
		//LinearLayout ll = new LinearLayout(this);
		activity = this;
		fetchContacts(IP,App.username,activity);
		///////////////////////////////////////////////////////////////////////////////////
		
	}
	public void fetchContacts(String IP,final String username,final Activity activity){
		//System.out.println("reached to contacts");
		if(App.status){
			list = new ArrayList<HashMap<String, String>>();
			AsyncHttpClient client1 = new AsyncHttpClient();
			client1.get(IP+":60021/intecons/Mobile_no.php?username="+username, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
					JSONObject output = null;
					//System.out.println(response);
					try {
						output = new JSONObject(response);
						if(output.getString("status").equals("true")){
							JSONArray array = output.getJSONArray("list");
							int len = array.length();
							for(i=0;i<len;i++){
								JSONObject obj = array.getJSONObject(i);
								hm = new HashMap<String, String>();
								hm.put("phone",obj.getString("phone"));
								hm.put("name", obj.getString("username"));
								list.add(hm);
							}
							if(App.status){
								//Log.i("status: ",""+App.status);
								Async_AddContacts addContact = new Async_AddContacts(list,username,activity);
								addContact.execute();
							}	
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			});
		}
		/*else
		{
			Intent in = new Intent(Find_Contact.this,Friend_list_activity.class);
			//i.putExtra("username", username);
			startActivity(in);	
		}*/
	}
}
