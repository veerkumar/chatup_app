package com.example.chatup_demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;



public class FriendJsonDecoding extends Activity{
	

	public  void writingfriendlist(String json1) throws IOException{
		Log.i("writing frientlist","1"+json1);
					FileOutputStream fos=App.friendlistcontext.openFileOutput("FriendList.txt", MODE_PRIVATE);
					fos.write(json1.getBytes());
				}
	public Object clone() throws CloneNotSupportedException {
	    throw new CloneNotSupportedException("I'm a singleton!");
	}
	
	 public static Boolean decode(String json) throws JSONException,IOException {
		 
						JSONObject jsonObject = new JSONObject(json);
						
						
					//articletitle=new ArrayList<String>();
					// dates=new ArrayList<String>();
					// desc=new ArrayList<String>();
						ArrayList<String> friendusername=new ArrayList<String>();
						ArrayList<String> friendname=new ArrayList<String>();
						
					     ArrayList<HashMap<String, Object>> alist=new ArrayList<HashMap<String,Object>>();
						
					  //   Log.i("status","1"+jsonObject.getString("status"));
					     if(jsonObject.getString("status").contains("true")){
					      Log.i("status","true");
													JSONArray jsonArray = jsonObject.getJSONArray("list");
													
													
													
												    for (int i = 0; i < jsonArray.length(); i++) {
												        JSONObject explrObject = jsonArray.getJSONObject(i);
												        
												      friendusername.add(explrObject.getString("username"));
												      friendname.add(explrObject.getString("name"));
												    
												}
												   
														Log.i("friendusers size",""+friendusername.size());
														//Log.i("alist before",""+articletitle.get(13));
										
												   
														App.friendname=new ArrayList<String>();
														App.friendusername=new ArrayList<String>();
														App.friendusername=friendusername;
														App.friendname=friendname;
														
														return true;
														
					     }
					     else{
					    	 App.friendusername=null;
					    	 App.friendname=null;
					    	 Log.i("status","false");
					    	 return false;
					     }
							
					 //  Log.i("size of alist",""+alist.size());
					   //Log.i("alist",""+alist.get(13));
							
						}

}
