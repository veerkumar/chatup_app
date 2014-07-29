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
import java.util.Date;
import java.util.HashMap;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.example.addcontacts.Find_Contact;
import com.example.addcontacts.Friend_list_activity;
import com.example.filetransfer.Filetransfer1;
import com.example.internetconnectivity.NetworkChangeReceiver;
import com.example.settings.Settings_activity;


import android.R.string;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

public class Chatlist extends FragmentActivity{
	
	public static final String USERNAME = App.username;
	public static final String PASSWORD = "veer";
	private ArrayList<String> recentmessages = new ArrayList<String>();
	ArrayList<String> senderList=new ArrayList<String>();
	ArrayList<String> receiverList=new ArrayList<String>();
	ArrayList<String> msgList=new ArrayList<String>();
	ArrayList<String> timeOfMsgList=new ArrayList<String>();
	private Handler mHandler = new Handler();
	Boolean filestatusresult=false;
    Message message;
    String fromName;
    String username;
   public XMPPConnection connection ;
   public PacketListener packetlistner;
    public ChatlistFragment fr;
    public Filetransfer1 filetransferobj;
    Find_Contact find_contact_obj;

	
							ListView chatlist;
							// action bar
							private ActionBar actionBar;
							ArrayList<String> chatfiles=new ArrayList<String>();
							ArrayList<String> chatmsg=new ArrayList<String>();
							ArrayList<String> usernames=new ArrayList<String>();
							ArrayList<Integer> whosent=new ArrayList<Integer>();
						
							// Refresh menu item
							private MenuItem refreshMenuItem;
							private IntentFilter filter;
							private NetworkChangeReceiver receiver;
						
							@Override
							protected void onCreate(Bundle savedInstanceState) {
								super.onCreate(savedInstanceState);
								setContentView(R.layout.chatlist);
								
								
						
								actionBar = getActionBar();
								 
								// Hide the action bar title
								actionBar.setDisplayShowTitleEnabled(false);
							//	 App.chatlistcontext=this;
								this.connection=App.connection;
								//brodcast();
								filetransferobj=new Filetransfer1(Chatlist.this);
								App.chatlistcontext=Chatlist.this;
								find_contact_obj = new Find_Contact();
								filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
								receiver = new NetworkChangeReceiver();
								registerReceiver(receiver, filter);
								receiver.onReceive(this, new Intent(ConnectivityManager.CONNECTIVITY_ACTION));
								Log.i("status",""+App.status);
								if(App.status){
								find_contact_obj.fetchContacts(App.ip, App.username, Chatlist.this);}
//								Timer t = new Timer();
//								//Set the schedule function and rate
//								t.scheduleAtFixedRate(new TimerTask() {
//
//								    @Override
//								    public void run() {
//								       
//								    	runOnUiThread(new Runnable() {
//
//								    	    @Override
//								    	    public void run() {
//								    	    	Log.i("timer","timer");
//										    	if(App.connection==null|| !App.connection.isConnected())
//										    new InitialConnectionCreator().execute();
//								    	    }
//
//								    	});
//								    	
//								    }},5,10000);
								
							//	setConnection(App.connection);
								//File path = new File(getFilesDir(),"chats");
//								File file = new File(path, "me_virender@openfire");
//								boolean deleted = file.delete();
								//File path = new File(getFilesDir(),"chats");
							//	File file1 = new File(path, "me_null");
								
								
							//	 file1.delete();
							//	App.filesendingStatus=(ArrayList<JSONObject>) new ArrayList<JSONObject>();
								
								Log.i("fragmentmanger","man");
								App.recentmessages=new ArrayList<String>();
								App.filesendingStatus=new ArrayList<HashMap<String, Object>>();
							App.fr=	 fr=(ChatlistFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
								
//								Chatlistadapter adapter=new Chatlistadapter(this);
//								chatlist.setAdapter(adapter);
							
						
							

	

		// Changing the action bar icon
		// actionBar.setIcon(R.drawable.ico_actionbar);
	}


	@Override
public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.chat_list_menu, menu);

		// Associate searchable configuration with the SearchView
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
        
       
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_search:
			// search action
			return true;
		case R.id.friend_list:
			// location found
			friendlist_function();
			return true;
		
		case R.id.setting:
			Intent intent1= new Intent(Chatlist.this,Settings_activity.class);
			startActivity(intent1);
			
			// refresh
			//refreshMenuItem = item;
			// load the data from server
			//new SyncData().execute();
			return true;
		case R.id.action_help:
			// help action
			return true;
		case R.id.add_contacts:
			// check for updates action
			Intent intent =new Intent(Chatlist.this,Friend_list_activity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

		
		
	}


	@SuppressLint("NewApi")
private class SyncData extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			// set the progress bar view
			refreshMenuItem.setActionView(R.layout.action_progressbar);

			refreshMenuItem.expandActionView();
		}

		@Override
		protected String doInBackground(String... params) {
			// not making real request in this demo
			// for now we use a timer to wait for sometime
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		
		@Override
		protected void onPostExecute(String result) {
			refreshMenuItem.collapseActionView();
			// remove the progress bar view
			refreshMenuItem.setActionView(null);
		}
	}

private void friendlist_function(){
		Intent i = new Intent(Chatlist.this, FriendlistClass.class);
		startActivity(i);
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	if(App.connection!=null){
		
			if(!App.connection.isConnected()){
		App.connection=null;
	}
	else {
		filetransferobj.ReceiveFile();
		setConnection();

	}
	}
	
	 
	super.onResume();
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	if(connection!=null){
	connection.removePacketListener(packetlistner);
	filetransferobj.managerListner.removeFileTransferListener(App.filelistner);
	}
	// filetransferobj.ReceiveFile();
}

/////////////////////////////////////////////////////////////////*

public void setConnection() {
	connection=App.connection;
	//App.recentmessages = new ArrayList<String>();
	Log.i("setconnection","dnnj");
	if (connection != null) {
		// Add a packet listener to get messages sent to us
		PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
		packetlistner=new PacketListener() {
			@Override
			public void processPacket(Packet packet) {
				 message = (Message) packet;
				if (message.getBody() != null) {
					 fromName = StringUtils.parseBareAddress(message
							.getFrom());
					 
					Log.i("XMPPChatDemoActivity", "Text Recieved " + message.getBody()
							+ " from " + fromName );
					
				//	recentmessages.add(message.getBody());
					// Add the incoming message to the list view
					mHandler.post(new Runnable() {
						public void run() {
							//setListAdapter(2);
							String reg="(@.*)";
							username=fromName.replaceAll(reg,"").toString();
							Log.i("username","u"+username);
							App.recentmessages.add(username);
											try {
												readFile();
											} catch (IOException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											} catch (JSONException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											Log.i("writing","in chatlist");
							try {
								
								writefile(2);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							fr.setadapter(getApplicationContext());
							}
					});
				}
			}
		};
		connection.addPacketListener(packetlistner, filter);
	}
}
@SuppressLint("SimpleDateFormat")
public void writefile(int a) throws IOException, JSONException {
									//cleanall();
									Log.i("chatlist","writefile method");
										
										JSONArray data=new JSONArray();
									//	Log.i("jsonarray assing",""+data);
										data=App.jsonchatArray;
										JSONObject jsonmsgObject;
									//	Log.i("jsonarray assing",""+data);
										jsonmsgObject=new JSONObject();
									//	Log.i("a value","0"+a);
										if(a==2){
										
											jsonmsgObject.put("sender",username);
											jsonmsgObject.put("receiver", "me");
											jsonmsgObject.put("msg",message.getBody());
											Log.i("after msg",message.getBody());
											
										}
										
										
										
										SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
										String currentDateandTime = sdf.format(new Date());
									//	Log.i("currentdate",""+currentDateandTime);
										jsonmsgObject.put("timeofMsg",currentDateandTime);
									//	Log.i(" json object",""+jsonmsgObject);
									data.put(jsonmsgObject);
									App.jsonchatArray=data;
									//	Log.i("jsonChat array",""+data);
										
										
										String text=data.toString();
										
										//String text=UIHelper.getText(this, R.id.editText1);
										String Filename="me_"+username;
										File path = new File(getFilesDir(),"chats");
										if(!path.exists()){
											//File mydir = getDir("chats", MODE_PRIVATE);
											path.mkdir();
										//Log.i("write directory","making"+path);
											File mypath=new File(path,Filename);
											BufferedWriter bw=new BufferedWriter(new FileWriter(mypath));
											//FileOutputStream fos=openFileOutput(fileWithinMyDir, MODE_PRIVATE);
											//fos.write(text.getBytes());
											bw.write(text);
											bw.close();
										//	readFile();
											
										}
										//File fileDirectory = new File(Environment.getDataDirectory()+"/chats/");
										else{
										File mypath=new File(path,Filename);
										BufferedWriter bw=new BufferedWriter(new FileWriter(mypath));
										//FileOutputStream fos=openFileOutput(fileWithinMyDir, MODE_PRIVATE);
										//fos.write(text.getBytes());
										bw.write(text);
										 DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
										Calendar calendar = Calendar.getInstance();
									     calendar.setTimeInMillis(mypath.lastModified());
									     
									Log.i("last modified"+Filename,"0"+formatter.format(calendar.getTime()));
										bw.close();
										//readFile();
										}
										
									}
public Boolean fileExistance(String fname){
									File file = null;
									File path = new File(getFilesDir(),"chats");
									if(path.exists()){
										
										 file = new File(path, fname);
								
									    if(file.exists()) {
									        Log.i("FILE EXISTS", fname);
									        
									        return true;
									    } else {
									        Log.i("DOWNLOAD", fname);
									        return false;
									     //   new Download().execute(context, name, "http://192.168.2.136:8080/rest/transfer/"+ linkid +"/"+ username +"/" + json_data.getString("ID"));
									       }
										
									}
									else{
										Log.i("folder doesnot","exist");
										return false;
									}
								//	    File file = getBaseContext().getFileStreamPath(fname);
								//	    Log.i("file exits",""+file.exists());
									   // return false;
	}

public void readFile() throws IOException, JSONException {
		String Filename="me_"+username;
	//	filestatusresult;
		
		filestatusresult=fileExistance(Filename);
	if(filestatusresult.equals(false)){
		

		App.jsonchatArray=new JSONArray();
	}
	else{
		//File filewithinDirectory = new File(Environment.getDataDirectory()+"/chats/"+Filename);
		File path = new File(getFilesDir(),"chats/"+Filename);
		//lists all the files into an array
		Log.i("filedirectory","1"+path);
	
		        	  FileInputStream fis=new FileInputStream(path);
		      		
		      		BufferedInputStream bis=new BufferedInputStream(fis);
		      		StringBuffer b=new StringBuffer();
		      		while (bis.available()!=0) {
		      			char c=(char) bis.read();
		      			b.append(c);
		      			
		      		}
		         
		JSONArray jsonArray=new JSONArray(b.toString());
		Log.i("jsonArray",""+jsonArray);
		App.jsonchatArray=jsonArray;
		Log.i("jsonarrayinread",""+jsonArray);
		
	}
		
	}
public void brodcast(){
	Intent intent = new Intent();
	  intent.setAction("InternetBroadcast");
	 // intent.putExtra("value", 1000);
	  sendBroadcast(intent);
	
}

}
