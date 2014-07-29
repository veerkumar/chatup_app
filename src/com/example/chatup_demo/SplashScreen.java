package com.example.chatup_demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends Activity {
	Intent i;
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
Boolean registered= fileExistance("registration.txt");


		
if(registered.equals(true)){
								
								Log.i("if","true");
								try {
									readfile();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							//	InternetConnectionDetector cd = new InternetConnectionDetector(getApplicationContext());
								 
								//Boolean isInternetPresent = cd.isConnectingToInternet();
						//		Log.i("isInternetPresent","splashactivity"+isInternetPresent);
							//	if(isInternetPresent){
									new InitialConnectionCreator().execute();
							//	}
					i=new Intent(SplashScreen.this,Chatlist.class);
					
			
		}
	else {
		i = new Intent(SplashScreen.this, New_user_registration.class);
		}
		


		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
public boolean fileExistance(String fname){
	    File file = getBaseContext().getFileStreamPath(fname);
	    Log.i("fileexits",""+file.exists());
	    return file.exists();
	}
public void readfile() throws IOException,FileNotFoundException, JSONException{
	FileInputStream fis = null;
	
					fis = openFileInput("registration.txt");


					BufferedInputStream bis=new BufferedInputStream(fis);
					
					StringBuffer b=new StringBuffer();
					
						while (bis.available()!=0) {
							char c=(char) bis.read();
						//	Log.i("1","2"+c);
							b.append(c);
							
						}
					 
					
					 JSONArray	jsonArray = new JSONArray(b.toString());
					
				//	Log.i("jsonarray","+"+b.toString());
					
					for(int i=0;i<jsonArray.length();i++){
						JSONObject explrObject = null;
						
							explrObject = jsonArray.getJSONObject(i);
						
					
						App.username=explrObject.get("username").toString();
						Log.i("splash username","1"+App.username);
						App.name=explrObject.get("name").toString();
						App.email=explrObject.get("email").toString();

						}
}
}
