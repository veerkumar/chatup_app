package com.example.internetconnectivity;


import java.io.IOException;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.chatup_demo.*;

public class NetworkChangeReceiver extends BroadcastReceiver {
	private boolean isConnected = false;

	 @Override
	  public void onReceive(final Context context, final Intent intent) {
	 
	   Log.v("conneccctivity", "Receieved notification about network status");
	   Boolean status=isNetworkAvailable(context);
	 App.InternetStatus=status;
	  }
	 
	 
	  private boolean isNetworkAvailable(Context context) {
	   ConnectivityManager connectivity = (ConnectivityManager)
	     context.getSystemService(Context.CONNECTIVITY_SERVICE);
	   if (connectivity != null) {
	    NetworkInfo[] info = connectivity.getAllNetworkInfo();
	    if (info != null) {
	     for (int i = 0; i < info.length; i++) {
	      if (info[i].getState() == NetworkInfo.State.CONNECTED) {
	       if(!isConnected){
	       
	        isConnected = true;
	        //do your processing here ---
	        //if you need to post any data to the server or get status
	        //update from the server
	       }
	       return true;
	      }
	     }
	    }
	   }
	  
	   isConnected = false;
	   return false;
	  }
	 }
