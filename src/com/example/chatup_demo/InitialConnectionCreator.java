package com.example.chatup_demo;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class InitialConnectionCreator extends AsyncTask<Void, Void, Void> {
	public static final String HOST = "192.168.2.100";
	public static final int PORT = 5222;
	String caller ="default";
	int i=0;
	
	public static final String USERNAME = App.username;
	public static final String PASSWORD = "veer";
	private XMPPConnection connection;
	private ArrayList<String> recentmessages = new ArrayList<String>();
	ArrayList<String> senderList=new ArrayList<String>();
	ArrayList<String> receiverList=new ArrayList<String>();
	ArrayList<String> msgList=new ArrayList<String>();
	ArrayList<String> timeOfMsgList=new ArrayList<String>();
	private Handler mHandler = new Handler();
	Boolean filestatusresult=false;
    Message message;
  //  Context con;
    String fromName;
    public ConnectionListener listner;
   @Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	ConnectionConfiguration connConfig = new ConnectionConfiguration(
			HOST, PORT);
	App.connection= connection = new XMPPConnection(connConfig);
	
	listner= new ConnectionListener() {

		@Override
		public void reconnectionSuccessful() {
				// TODO Auto-generated method stub
			caller="reconnectionsuccessful";
			Log.i("again","loging"+"+"+connection.isAuthenticated());
//			if(!connection.isAuthenticated()){
//			try {
//				connection.login(USERNAME, PASSWORD);
//				Log.i("XMPPChatDemoActivity",
//						"Logged in as " + connection.getUser());
//
//				// Set the status to available
//				Presence presence = new Presence(Presence.Type.available);
//				connection.sendPacket(presence);
//			} catch (XMPPException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			}
			
				
			}
			
			@Override
			public void reconnectionFailed(Exception arg0) {
				// TODO Auto-generated method stub
				caller="reconnection failed";
				Log.i("reconnectionfailed","reconnection failsed");
				ConnectionConfiguration connConfig = new ConnectionConfiguration(
						HOST, PORT);
				App.connection= connection = new XMPPConnection(connConfig);
				//if(!connection.isAuthenticated()){
				try {
					connect("connection failed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//}
				
			}
			
			@Override
			public void reconnectingIn(int arg0) {
				// TODO Auto-generated method stub
				caller="reconnecting ";
				Log.i("reconnecting","reconnect");
				try {
					connection.connect();
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void connectionClosedOnError(Exception arg0) {
				// TODO Auto-generated method stub
				Log.i("connectioncloasedonerror","closed");
//				if(App.transfer!=null)
//				if(App.transfer.getProgress()>0){
//				      App.transfer.cancel();
//				     //App.connection.removePacketSendingListener(arg0)
//				      Log.i("file cancelling","canceald");}
				caller="connecection closed do error";
			//	App.connection.removeConnectionListener(listner);
			//	connection.removeConnectionListener(listner);
				App.connection=null;
				try {
					connection.connect();
					Log.i("XMPPChatDemoActivity",
							"Connected to " + connection.getHost());
				} catch (XMPPException ex) {
					Log.i("XMPPChatDemoActivity", "Failed to connect to "
							+ connection.getHost());
					Log.e("XMPPChatDemoActivity", ex.toString());
					try {
						connect("error closed");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//setConnection(null);
					App.connection=null;
				}catch (IllegalStateException e) {
					// TODO: handle exception
					Log.i("illegal","illecal conection");
				}
				
				
				
				
			}
			
			@Override
			public void connectionClosed() {
				// TODO Auto-generated method stub
				Log.i("cocloased","closed");
				caller="connection closed";
			//	App.connection.removeConnectionListener(listner);
			//	App.connection=null;
				//connection.disconnect();
//				try {
//					connect();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (XMPPException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				
			}

			
		};

			
//			}

		
}
  
	@Override
	protected Void doInBackground(Void... params){
		recentmessages = new ArrayList<String>();
		// TODO Auto-generated method stub
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// Create a connection
				try {
					Log.i("before connect()","connect");
					connect("first main");
					if(i==0){
						i++;
						Log.i("setting connection listner","ok");
					connection.addConnectionListener(listner);}
					Log.i("after connect()","connect");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					Log.i("got xmPPException","ok");
					e.printStackTrace();
				}
				Log.i("App.connection","1"+App.connection);
				//dialog.dismiss();
			}
		});
		t.start();
		
		
		
		return null;
	}
public void connect(String string) throws java.io.IOException,XMPPException{
	
	Log.e("caller",string);
	
	
	 Log.i("connection return","1"+connection);
	 App.connection=null;

	try {
		connection.connect();
		Log.i("XMPPChatDemoActivity",
				"Connected to " + connection.getHost());
	} catch (XMPPException ex) {
		Log.i("XMPPChatDemoActivity", "1Failed to connect to "
				+ connection.getHost());
		Log.e("XMPPChatDemoActivity", ex.toString());
		//setConnection(null);
		sleepb();
		Log.i("sleepfor ","reconnecting");
		newconnection();
		App.connection=null;
		
	}catch(IllegalStateException e){
		Log.i("illegal","state");
	}
	Log.i("connected","1"+connection.isConnected());
	if(connection.isConnected()&& !connection.isAuthenticated()){
	try {
		// SASLAuthentication.supportSASLMechanism("PLAIN", 0);
		
		connection.login(USERNAME, PASSWORD);
		Log.i("XMPPChatDemoActivity",
				"Logged in as " + connection.getUser());

		// Set the status to available
		Presence presence = new Presence(Presence.Type.available);
		connection.sendPacket(presence);
		//setConnection(connection);
		App.connection=connection;
		
		
		

		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		for (RosterEntry entry : entries) {
			Log.d("XMPPChatDemoActivity",
					"--------------------------------------");
			Log.d("XMPPChatDemoActivity", "RosterEntry " + entry);
			Log.d("XMPPChatDemoActivity",
					"User: " + entry.getUser());
			Log.d("XMPPChatDemoActivity",
					"Name: " + entry.getName());
			Log.d("XMPPChatDemoActivity",
					"Status: " + entry.getStatus());
			Log.d("XMPPChatDemoActivity",
					"Type: " + entry.getType());
			Presence entryPresence = roster.getPresence(entry
					.getUser());

			Log.d("XMPPChatDemoActivity", "Presence Status: "
					+ entryPresence.getStatus());
			Log.d("XMPPChatDemoActivity", "Presence Type: "
					+ entryPresence.getType());
			Presence.Type type = entryPresence.getType();
			if (type == Presence.Type.available)
				Log.d("XMPPChatDemoActivity", "Presence AVIALABLE");
			Log.d("XMPPChatDemoActivity", "Presence : "
					+ entryPresence);

		}
	} catch (XMPPException ex) {
		Log.i("XMPPChatDemoActivity", "Failed to log in as "
				+ USERNAME);
		Log.e("XMPPChatDemoActivity", ex.toString());
		//setConnection(null);
		connect("exception failed");
		App.connection=null;
	}
	}

	
}
@Override
protected void onPostExecute(Void result) {
	// TODO Auto-generated method stub
	super.onPostExecute(result);
	

}

public void sleepb(){
	new Thread(new Runnable() {
	    @Override
	    public void run() {
	        try {
	            Thread.sleep(5000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	       	    }
	}).start();
}
public void newconnection()throws java.io.IOException,XMPPException{
	
	ConnectionConfiguration connConfig = new ConnectionConfiguration(
			HOST, PORT);
	App.connection= connection = new XMPPConnection(connConfig);
	try {
		connection.connect();
		Log.i("XMPPChatDemoActivity",
				"Connected to " + connection.getHost());
	} catch (XMPPException ex) {
		Log.i("XMPPChatDemoActivity", "1Failed to connect to "
				+ connection.getHost());
		Log.e("XMPPChatDemoActivity", ex.toString());
		//setConnection(null);
		sleepb();
		newconnection();
		App.connection=null;
		
	}
	Log.i("connected","1"+connection.isConnected());
	if(connection.isConnected()&&!connection.isAuthenticated()){
	try {
		// SASLAuthentication.supportSASLMechanism("PLAIN", 0);
		
		connection.login(USERNAME, PASSWORD);
		Log.i("XMPPChatDemoActivity",
				"Logged in as " + connection.getUser());

		// Set the status to available
		Presence presence = new Presence(Presence.Type.available);
		connection.sendPacket(presence);
		//setConnection(connection);
		App.connection=connection;
		connection.addConnectionListener(listner);
		
		
		

		Roster roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		for (RosterEntry entry : entries) {
			Log.d("XMPPChatDemoActivity",
					"--------------------------------------");
			Log.d("XMPPChatDemoActivity", "RosterEntry " + entry);
			Log.d("XMPPChatDemoActivity",
					"User: " + entry.getUser());
			Log.d("XMPPChatDemoActivity",
					"Name: " + entry.getName());
			Log.d("XMPPChatDemoActivity",
					"Status: " + entry.getStatus());
			Log.d("XMPPChatDemoActivity",
					"Type: " + entry.getType());
			Presence entryPresence = roster.getPresence(entry
					.getUser());

			Log.d("XMPPChatDemoActivity", "Presence Status: "
					+ entryPresence.getStatus());
			Log.d("XMPPChatDemoActivity", "Presence Type: "
					+ entryPresence.getType());
			Presence.Type type = entryPresence.getType();
			if (type == Presence.Type.available)
				Log.d("XMPPChatDemoActivity", "Presence AVIALABLE");
			Log.d("XMPPChatDemoActivity", "Presence : "
					+ entryPresence);

		}
	} catch (XMPPException ex) {
		Log.i("XMPPChatDemoActivity", "Failed to log in as "
				+ USERNAME);
		Log.e("XMPPChatDemoActivity", ex.toString());
		//setConnection(null);
		connect("new colnnection");
		App.connection=null;
	}
	}
}
}
