package com.example.chatup_demo;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

public class WriteRecivedFile {
	ArrayList<String> senderList=new ArrayList<String>();
	ArrayList<String> receiverList=new ArrayList<String>();
	ArrayList<String> msgList=new ArrayList<String>();
	ArrayList<String> timeOfMsgList=new ArrayList<String>();
	ArrayList<String> fileslist=new ArrayList<String>();
	ArrayList<String> statuslist=new ArrayList<String>();
	Boolean filestatusresult=false;
	Activity activity;
	ListView listview;
	public WriteRecivedFile(Activity activity) {
		// TODO Auto-generated constructor stub
		
		this.activity=activity;
		
		
	}

	public void writeRecivedFileTransfer(String[] files ,String from)throws IOException, JSONException{
		readFile(from);
			JSONArray data=new JSONArray();
		
			data=App.jsonchatArray;
			Log.i("data inwrite","d"+data);
			JSONObject jsonmsgObject;
			
			jsonmsgObject=new JSONObject();
			//	Log.i("a value","0"+a);
				
							jsonmsgObject.put("sender", from);// no need to define variable type it is
							
							jsonmsgObject.put("receiver", "me");
						
							jsonmsgObject.put("msg","000000000");
							jsonmsgObject.put("status","deliviered" );
							jsonmsgObject.put("file",files[0]);
							
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
							String currentDateandTime = sdf.format(new Date());
						
							jsonmsgObject.put("timeofMsg",currentDateandTime);
							Log.i(" json object filetransfer",""+jsonmsgObject);
								data.put(jsonmsgObject);
								Log.i("data","data"+data);
								App.jsonchatArray=data;
			     

							String text=data.toString();
							
							//String text=UIHelper.getText(this, R.id.editText1);
							String Filename="me_"+from;
							File path = new File(activity.getFilesDir(),"chats");
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
	public Boolean fileExistance(String fname){
		File file = null;
		File path = new File(activity.getFilesDir(),"chats");
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
//		    File file = getBaseContext().getFileStreamPath(fname);
//		    Log.i("file exits",""+file.exists());
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

public void readFile(String from) throws IOException, JSONException {
		String Filename="me_"+from;
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
		File path = new File(activity.getFilesDir(),"chats/"+Filename);
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

public void writeRecivedFileTransfer(String[] files, String from,
		ListView listview1) throws IOException, JSONException{
	// TODO Auto-generated method stub
	this.listview=listview1;
	readFile(from);
	JSONArray data=new JSONArray();

	data=App.jsonchatArray;
	Log.i("data inwrite","d"+data);
	JSONObject jsonmsgObject;
	
	jsonmsgObject=new JSONObject();
	//	Log.i("a value","0"+a);
		
					jsonmsgObject.put("sender", from);// no need to define variable type it is
					
					jsonmsgObject.put("receiver", "me");
				
					jsonmsgObject.put("msg","000000000");
					jsonmsgObject.put("status","deliviered" );
					jsonmsgObject.put("file",files[0]);
					
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
					String currentDateandTime = sdf.format(new Date());
				
					jsonmsgObject.put("timeofMsg",currentDateandTime);
					Log.i(" json object filetransfer",""+jsonmsgObject);
						data.put(jsonmsgObject);
						Log.i("data","data"+data);
						App.jsonchatArray=data;
	     

					String text=data.toString();
					
					//String text=UIHelper.getText(this, R.id.editText1);
					String Filename="me_"+from;
					File path = new File(activity.getFilesDir(),"chats");
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

}
