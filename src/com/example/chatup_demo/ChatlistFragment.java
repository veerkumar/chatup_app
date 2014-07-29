package com.example.chatup_demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ChatlistFragment extends Fragment {
	Context con;
	
	ArrayList<String> chatfiles=new ArrayList<String>();
	ArrayList<String> chatmsg=new ArrayList<String>();
	ArrayList<String> files=new ArrayList<String>();
	ArrayList<String> usernames=new ArrayList<String>();
	ArrayList<Integer> whosent=new ArrayList<Integer>();
	View rootview;
	ListView chatlistview;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootview=inflater.inflate(R.layout.chatlist_fragment, container,false);
		return rootview;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	 chatlistview=(ListView) rootview.findViewById(R.id.chatlist);
	 chatlistview.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				Intent i= new Intent(getActivity(),ListviewAct.class);
				i.putExtra("name", App.usernames.get(position));
				App.recentmessages.remove(App.usernames.get(position));
				Log.i("username",App.usernames.get(position));
				startActivity(i);
				
			}
		});
		super.onActivityCreated(savedInstanceState);
		
	}
public void setadapter(Context context){
		this.con=context;
		
		 try {
				filereadforchatlist();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Log.i("first adapter","adapter");
		Chatlistadapter adapter=new Chatlistadapter(getActivity());
		chatlistview.setAdapter(adapter);
		//text.setText(str);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		try {
			filereadforchatlist();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	Log.i("second","adapture");
	Chatlistadapter adapter=new Chatlistadapter(getActivity());
	chatlistview.setAdapter(adapter);
	//setadapter(context);
		super.onResume();
	}
public void cleanall(){
	chatfiles=new ArrayList<String>();
		chatmsg=new ArrayList<String>();
	usernames=new ArrayList<String>();
	App.usernames=new ArrayList<String>();
	App.whosent=new ArrayList<Integer>();
	App.chatmsg=new ArrayList<String>();
	whosent=new ArrayList<Integer>();
	
}
public void filereadforchatlist() throws IOException, JSONException{
								cleanall();
								File path = new File(getActivity().getFilesDir(),"chats");
								File[] files= path.listFiles();
								if(files.length!=0){
								for(int i=0;i<files.length;i++){
									//Log.i("file name","1"+files[i]);
									String str=""+files[i];
								//	Log.i("file name",str);
									List<String> dirs = new ArrayList<String>(Arrays.<String>asList(str.split("/")));
									Integer len=dirs.size();
									Log.i("files",""+dirs.get(len-1));
									chatfiles.add(dirs.get(len-1));
								}
								
								for(int i=0;i<chatfiles.size();i++){
									readf(chatfiles.get(i));
								}
								App.usernames=this.usernames;
								Log.i("usernamesread",App.usernames.get(0));
								App.chatmsg=this.chatmsg;
								App.whosent=this.whosent;
								App.files_chatlist=this.files;
								}
	
}
public void readf(String filename)throws IOException, JSONException{
	File path = new File(getActivity().getFilesDir(),"chats/"+filename);
	 FileInputStream fis=new FileInputStream(path);
		
		BufferedInputStream bis=new BufferedInputStream(fis);
		StringBuffer b=new StringBuffer();
		while (bis.available()!=0) {
			char c=(char) bis.read();
			b.append(c);
			
		}
		JSONArray jsonArray=new JSONArray(b.toString());
		JSONObject explrObject = jsonArray.getJSONObject(jsonArray.length()-1);
		String str1;
		if(explrObject.getString("msg").length()>25){
			str1=explrObject.getString("msg").substring(0, 20)+".....";
		}
		else{
			str1=explrObject.getString("msg");
		}
		this.chatmsg.add(str1);
		Log.i("last","1"+str1);
		if(explrObject.getString("sender").equalsIgnoreCase("me")){
			this.usernames.add(explrObject.getString("receiver"));
			this.whosent.add(0);
		}
		else{
			this.usernames.add(explrObject.getString("sender"));
			this.whosent.add(1);
		}

}
}
