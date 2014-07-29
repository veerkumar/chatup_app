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
import java.util.Timer;
import java.util.TimerTask;

import com.example.chatup_demo.R;
import com.example.filetransfer.Filetransfer1;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.MessageTypeFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import veer.example.fileselector.FileSelectionActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;



public class ListviewAct extends Activity{
	private static final String FILES_TO_UPLOAD = "upload";
	String [] files_paths ;
	public static final String HOST = "192.168.2.100";
	public static final int PORT = 5222;
	
//	public static final String USERNAME = App.username;
//	public static final String PASSWORD = "veer";
	//private XMPPConnection connection;
	
	ArrayList<String> senderList=new ArrayList<String>();
	ArrayList<String> receiverList=new ArrayList<String>();
	ArrayList<String> msgList=new ArrayList<String>();
	ArrayList<String> timeOfMsgList=new ArrayList<String>();
	ArrayList<String> fileslist=new ArrayList<String>();
	ArrayList<String> statuslist=new ArrayList<String>();

	public XMPPConnection connection;
	private ArrayList<String> messages = new ArrayList<String>();
	private Handler mHandler = new Handler();

	private EditText recipient;
	private EditText textMessage;
	private ListView listview;
	Message message;
	String fromName;
	String text="999999999";
	String recep;
	Boolean filestatusresult=false;
	PopupWindow pwindo;
	public PacketListener packetlistener;
	Button attachment;
	Filetransfer1 filetransferobj;

	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_chat);
		
	   //set content view AFTER ABOVE sequence (to avoid crash)
	    

		Intent intent = getIntent();
		 recep = intent.getStringExtra("name");
		 this.connection=App.connection;
		
		textMessage = (EditText) this.findViewById(R.id.messageEdit);
		listview = (ListView) this.findViewById(R.id.messagesContainer);
	 TextView friendname=(TextView) this.findViewById(R.id.companionLabel);
	 attachment=(Button) findViewById(R.id.attachment);
					 attachment.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getBaseContext(), FileSelectionActivity.class);
			                startActivityForResult(intent, 0);
						}
					});
	 
		friendname.setText(recep);
		
		Log.i("before settting 1st adapter","1"+textMessage.getText());
		setListAdapter(3);
		 filetransferobj=new Filetransfer1(ListviewAct.this);
//		Timer t = new Timer();
//		//Set the schedule function and rate
//		t.scheduleAtFixedRate(new TimerTask() {
//
//		    @Override
//		    public void run() {
//		       
//		    	runOnUiThread(new Runnable() {
//
//		    	    @Override
//		    	    public void run() {
//		    	    	Log.i("timer","timer");
//		    	    	
//		    	    	setListAdapter(3);
//				    //	adap.notifyDataSetChanged();
//		    	    }
//
//		    	});
//		    	
//		    }},10000,10000);

		// Set a listener to send a chat text message
		Button send = (Button) this.findViewById(R.id.chatSendButton);
		send.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String to = recep+"@openfire";
				Log.i("before settting 1st adapter","2");
				 text = textMessage.getText().toString();
				// textMessage.setHint("message text");
				 textMessage.setText("");
				 
				 
				//setListAdapter(1);
				// if(connection.isConnected()){
				 if(!text.equalsIgnoreCase("")){
				Log.i("XMPPChatDemoActivity", "Sending text " + text + " to " + to);
				Message msg = new Message(to, Message.Type.chat);
				msg.setBody(text);				
				if (App.connection != null&& App.connection.isConnected()) {
					connection.sendPacket(msg);
					messages.add(connection.getUser() + ":");
					messages.add(text);
					Log.i("setting adapter","after msg clicked");
					setListAdapter(1);
					//listview.setSelectionFromTop(0, App.lastposition);
				}}//}
				 
			}
		});

	//	connect();
		Log.i("connection","1"+App.connection);
		this.connection=App.connection;
		setConnection();
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				initiatePopupWindow();
				return false;
				
			}
		});

	}
private void initiatePopupWindow() {
		
	
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.alert_chat_deletion, null);
		

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
		//LinearLayout l=( LinearLayout) promptsView.findViewById(R.id.alertdelete);
		
	    TextView text= (TextView) promptsView.findViewById(R.id.body);
	    text.setText("Do You really want to delete?");
	    
	    

	 //  l.addView(text);
	    
		alertDialogBuilder.setView(promptsView);

		

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("delete", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					filedelete();
					
				}
				private void filedelete() {
					// TODO Auto-generated method stub
					String Filename="me_"+recep;
					
			//		filestatusresult=	fileExistance(Filename);
					if(filestatusresult.equals(false)){
						
					}
					else{
						File path = new File(getFilesDir(),"chats");
						File file = new File(path, Filename);
						
						
						boolean deleted = file.delete();
						
						
						Log.e("deleteing file",""+deleted);
						
						try {
							readFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						adapt adapter=new adapt(this);
//						adapter.notifyDataSetChanged();
						setContentView(R.layout.activity_chat);
					}
				}
			
			})
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  })
			  .setTitle("Delete Chat");

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		//alertDialog.setContentView(promptsView);
		alertDialog.show();
}
int i=0;
public void setConnection() {
		
		//this.connection = connection;
		Log.i("setconnection","2" +connection);
		
		if (connection != null ) {
			// Add a packet listener to get messages sent to us
			PacketFilter filter = new MessageTypeFilter(Message.Type.chat);
			packetlistener=new PacketListener() {
				
				
				@Override
				public void processPacket(Packet packet) {
					 message = (Message) packet;
					if (message.getBody() != null) {
						 fromName = StringUtils.parseBareAddress(message
								.getFrom());
						 Log.i("i","0"+i);
						 i++;
						Log.i("XMPPChatDemoActivity", "Text Recieved in listview " + message.getBody()
								+ " from " + fromName );
						messages.add(fromName + ":");
						messages.add(message.getBody());
						String username;
						String reg="(@.*)";
						username=fromName.replaceAll(reg,"").toString();
						Log.i("username","u"+username);
						
						if(!recep.equalsIgnoreCase(username)){
							App.recentmessages.add(username);
						}
						// Add the incoming message to the list view
						mHandler.post(new Runnable() {
							public void run() {
								setListAdapter(2);
								
							}
						});
					}
				}
			};
			connection.addPacketListener(packetlistener, filter);
			
		}
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	if(App.connection!=null)
		if(!App.connection.isConnected()){
			App.connection=null;
		}
		else {
			filetransferobj.ReceiveFile(listview);
			setConnection();

		}
	
	super.onResume();
}
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	if(App.connection!=null)
		if(!App.connection.isConnected()){
			App.connection=null;
		}
		else{
			filetransferobj.managerListner.removeFileTransferListener(App.filelistner);
			}
		
	super.onPause();
}

@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	if(connection!=null)
	connection.removePacketListener(packetlistener);
}
private void setListAdapter(int a) {
		if(a!=3)
		{
			
			try {
				readFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(a!=3){
			try {
				Log.i("going to","write infile");
				writefile(a);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adapt adapter=new adapt(this);
			listview.setAdapter(adapter);
			App.lastposition+=1;
		if(App.lastposition>2){
		 listview.setSelectionFromTop(App.lastposition-1, App.lastposition);}
			}
		}
		else if(text.equalsIgnoreCase("999999999")){
			try {
				readFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!filestatusresult.equals(false)){
			adapt adapter=new adapt(this);
			listview.setAdapter(adapter);
			if(App.lastposition>2){
				 listview.setSelectionFromTop(App.lastposition-1, App.lastposition);}
			}
		}
		else{
			
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
public void cleanall(){
App.senderList=new ArrayList<String>();
	App.receiverList=new ArrayList<String>();
	App.msgList=new ArrayList<String>();
	App.timeOfMsgList=new ArrayList<String>();
	App.fileslist=new ArrayList<String>();
	App.statuslist=new ArrayList<String>();
	senderList=new ArrayList<String>();
	receiverList=new ArrayList<String>();
	msgList=new ArrayList<String>();
	timeOfMsgList=new ArrayList<String>();
	fileslist=new ArrayList<String>();
	statuslist=new ArrayList<String>();
}
public void readFile() throws IOException, JSONException {
		String Filename="me_"+recep;
	//	filestatusresult;
		
		filestatusresult=fileExistance(Filename);
	if(filestatusresult.equals(false)){
		
		App.senderList=new ArrayList<String>();
		App.receiverList=new ArrayList<String>();
		App.msgList=new ArrayList<String>();
		App.timeOfMsgList=new ArrayList<String>();
		App.fileslist=new ArrayList<String>();
		App.statuslist=new ArrayList<String>();
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
		//Log.i("jsonArray",""+jsonArray);
		App.jsonchatArray=jsonArray;
		Log.i("jsonarrayinread",""+jsonArray);
		cleanall();
		for(int i=0;i<jsonArray.length();i++){
			JSONObject explrObject = jsonArray.getJSONObject(i);
			this.senderList.add(explrObject.getString("sender"));
			this.receiverList.add(explrObject.getString("receiver"));
			this.msgList.add(explrObject.getString("msg"));
			this.statuslist.add(explrObject.getString("status"));
			this.fileslist.add(explrObject.getString("file"));
			
			this.timeOfMsgList.add(explrObject.getString("timeofMsg"));
			//Log.i("msg in read",explrObject.getString("timeofMsg"));
			}
		App.senderList=this.senderList;
		App.receiverList=this.receiverList;
		App.msgList=this.msgList;
		App.timeOfMsgList=this.timeOfMsgList;
		App.fileslist=this.fileslist;
		App.statuslist=this.statuslist;
	}
		
	}
@SuppressLint("SimpleDateFormat")
public void writefile(int a) throws IOException, JSONException {
	//cleanall();
	Log.i("Listview","writefile method");
		
		JSONArray data=new JSONArray();
	//	Log.i("jsonarray assing",""+data);
		data=App.jsonchatArray;
		JSONObject jsonmsgObject;
	//	Log.i("jsonarray assing",""+data);
		jsonmsgObject=new JSONObject();
	//	Log.i("a value","0"+a);
		if(a==1){
					jsonmsgObject.put("sender", "me");// no need to define variable type it is
					//like a javascript style
					jsonmsgObject.put("receiver", recep);
				//	Log.i("receviver",recep);
					jsonmsgObject.put("msg",text);
					jsonmsgObject.put("status","delivered" );
					jsonmsgObject.put("file","00000000");
					//Log.i("before msg",text);
		
		
		}
		else{
			jsonmsgObject.put("sender",recep);
			jsonmsgObject.put("receiver", "me");
			jsonmsgObject.put("msg",message.getBody());
			jsonmsgObject.put("status","delivered" );
			jsonmsgObject.put("file","00000000");
		//	Log.i("after msg",message.getBody());
			
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
		String Filename="me_"+recep;
		File path = new File(getFilesDir(),"chats");
		if(!path.exists()){
			//File mydir = getDir("chats", MODE_PRIVATE);
			path.mkdir();
			Log.i("write directory","making"+path);
			File mypath=new File(path,Filename);
			BufferedWriter bw=new BufferedWriter(new FileWriter(mypath));
			//FileOutputStream fos=openFileOutput(fileWithinMyDir, MODE_PRIVATE);
			//fos.write(text.getBytes());
			bw.write(text);
			bw.close();
			readFile();
			
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
	     
	//Log.i("last modified"+Filename,"0"+formatter.format(calendar.getTime()));
		bw.close();
		readFile();
		}
		
	}
public void writeFileForFileTransfer(String[] files )throws IOException, JSONException{
	readFile();
		JSONArray data=new JSONArray();
	
		data=App.jsonchatArray;
		Log.i("data inwrite","d"+data);
		JSONObject jsonmsgObject;
		for(int k=0;k<files.length;k++){
		jsonmsgObject=new JSONObject();
		//	Log.i("a value","0"+a);
			
						jsonmsgObject.put("sender", "me");// no need to define variable type it is
						
						jsonmsgObject.put("receiver", recep);
					
						jsonmsgObject.put("msg","000000000");
						jsonmsgObject.put("status","sending" );
						jsonmsgObject.put("file",files[k]);
						
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
						String currentDateandTime = sdf.format(new Date());
					
						jsonmsgObject.put("timeofMsg",currentDateandTime);
						Log.i(" json object filetransfer",""+jsonmsgObject);
							data.put(jsonmsgObject);
							Log.i("data","data"+data);
							App.jsonchatArray=data;
		     }	

						String text=data.toString();
						
						//String text=UIHelper.getText(this, R.id.editText1);
						String Filename="me_"+recep;
						File path = new File(getFilesDir(),"chats");
						if(!path.exists()){
							
							path.mkdir();
							Log.i("write directory","making"+path);
							File mypath=new File(path,Filename);
							BufferedWriter bw=new BufferedWriter(new FileWriter(mypath));
						
							bw.write(text);
							bw.close();
				           }
						else{
							File mypath=new File(path,Filename);
							BufferedWriter bw=new BufferedWriter(new FileWriter(mypath));
						
							bw.write(text);
							bw.close();
						}
	
}
@Override

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == 0 && resultCode == RESULT_OK){
    	//ArrayList<File> Filess=data.getBundleExtra(FILES_TO_UPLOAD);
    	
    	
    	Log.i("on activity result","1");
        ArrayList<File> Files = (ArrayList<File>) data.getSerializableExtra(FILES_TO_UPLOAD); //file array list
         //string array
        Log.i("fileeeee","+"+Files.get(0));
        int i = 0;
        files_paths=new String[ Files.size()];
        Log.i("on activity result","2");
        for(File file : Files){
            //String fileName = file.getName();
            String uri = file.getAbsolutePath();
            Log.i("uri","1"+uri);
            files_paths[i] = uri.toString(); 
            Log.i("file"+i,"%"+files_paths[i]);//storing the selected file's paths to string array files_paths
            i++;
        }
        
     // get prompts.xml view
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.alert_file_slection, null);
		

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);

		// Find the ScrollView 
		LinearLayout ll = (LinearLayout) promptsView.findViewById(R.id.id_lin);

	    // Add text
	    //int j;
	    Log.i("before","before");
	 //   ImageView[] str=new ImageView[files_paths.length];
	    Log.i("before","before");
	    for(int j=0;j<files_paths.length;j++){
	    	
	    	if(files_paths[j].contains(".bmp")||files_paths[j].contains(".BMP")||files_paths[j].contains(".gif")||files_paths[j].contains(".GIF")||files_paths[j].contains(".jpeg")||files_paths[j].contains(".jpg")||files_paths[j].contains(".JPEG")||files_paths[j].contains(".png")||files_paths[j].contains(".PNG")){
	    		
	    		new ImageLoader2().execute(files_paths[j]);
	    		ImageView str=new ImageView(this);
	    		if(App.uploadimage!=null)
	    		str.setImageBitmap(App.uploadimage);
	    		str.setMaxHeight(200);
		    	str.setMaxWidth(400);
		    	str.setScaleType(ScaleType.CENTER_CROP);
		    	

		    	ll.addView(str);
	    		}}
	    
	    
		alertDialogBuilder.setView(promptsView);

		

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("send", new DialogInterface.OnClickListener() {
				ArrayList<JSONObject> filelist;
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 filelist= new ArrayList<JSONObject>();
					JSONObject j;
					for (int i=0;i<files_paths.length;i++){
						
						j=new JSONObject();
						try {
							j.put("to", recep);
							j.put("path", files_paths[i]);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						filelist.add(j);
						
					}
					
					new Thread(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							try {
								writeFileForFileTransfer(files_paths);
								setListAdapter(1);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try{
							filetransferobj.sendfile(filelist);
							
							}catch(IllegalStateException e){
								Log.i("illegal state exception","1"+e);
								//App.connection.disconnect();
							}
							super.run();
							}
						
				}.start();
						
				
					dialog.cancel();
				}
			})
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		//alertDialog.setContentView(promptsView);
		alertDialog.show();

    }

}


}
