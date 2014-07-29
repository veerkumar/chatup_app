package com.example.chatup_demo;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


public class MyAsyncTask extends AsyncTask< String,String, String>{
	static List<NameValuePair> se=new  ArrayList<NameValuePair>();
	static InputStream is = null;
static String json ;
ArrayList<String> articletitle=new ArrayList<String>();
 ArrayList<String> dates=new ArrayList<String>();
 ArrayList<String> desc=new ArrayList<String>();
 static ArrayList<HashMap<String, Object>> alist=new ArrayList<HashMap<String,Object>>();
 HashMap<String, Object> map=new HashMap<String,Object>();
Boolean decodeingresult;

 HttpResponse response;


static Context context;
public static void conset(Context con){
	Log.i("con","is set");
	context=con;
}

	
	
	protected void onPreExecute() {
		super.onPreExecute();
		Log.i("in ","preexecute");
	}

	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://192.168.2.100:60021/intecons/friend_list.php");
		
		

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", App.username));
            Log.i("username","1"+App.username);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
         response = httpclient.execute(httppost);
         Log.i("httpresponce","1"+response);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

				//Log.i("string",""+se);
			    
					
					int code = response.getStatusLine().getStatusCode();
					HttpEntity httpEntity = response.getEntity();
					
					
					is = httpEntity.getContent();
					Log.i("status code",""+code);
	             //  Log.i("returned","1"+httpEntity.getContent());
					
						}catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							} catch (IOException e) {
							// TODO Auto-generated catch block
							}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				
			}
			is.close();
			json=sb.toString() ;
		Log.i("json string",json);
		
		decodeingresult=FriendJsonDecoding.decode(json);
			
			//Log.i("responce",json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		
		
					
				
		    
		return null;
		}
		
		protected   void onPostExecute(String result){
	        
		if(decodeingresult.equals(true)){
		
			try {
				onclic();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	     }
	
	
	public void onclic() throws IOException{
		
		 FriendJsonDecoding friend=new FriendJsonDecoding();
		 friend.writingfriendlist(json);
		Shu_FriendlistAdapter adapter=new Shu_FriendlistAdapter(context);
		App.friendlist.setAdapter(adapter);
	

	
		
	}
		
}

