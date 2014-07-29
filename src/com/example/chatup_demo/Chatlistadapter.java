package com.example.chatup_demo;

import java.util.ArrayList;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Chatlistadapter extends BaseAdapter {
	Context context;
	int pos;
	
	static int i=0;
	ArrayList<String> chatmsg=new ArrayList<String>();
	ArrayList<String> usernames=new ArrayList<String>();
	ArrayList<Integer> whosent=new ArrayList<Integer>();
	ArrayList<String> files_chatlist=new ArrayList<String>();
	

public Chatlistadapter(Context rootview){
	this.context=rootview;
	this.chatmsg=App.chatmsg;
	this.usernames=App.usernames;
	this.whosent=App.whosent;
	this.files_chatlist=App.files_chatlist;
	
}
public int getpos(){
	return pos;
	
}

public void setpos(int position){
	pos=position;
}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
	//	Log.e("Str length","in adapter inflator");
	//	Log.i("senderlist size","s"+usernames.size());
		//App.lastposition=senderList.size();
		return (usernames.size());
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	@Override
	public View getView( int position, View listview, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  
	listview = inflater.inflate(R.layout.chatlist_item,null);
	
	final TextView name=(TextView) listview.findViewById(R.id.username);
	final TextView txtMessage=(TextView) listview.findViewById(R.id.textmsg);
	ImageView photo=(ImageView) listview.findViewById(R.id.imageView1);
	ImageView whosent1=(ImageView) listview.findViewById(R.id.whosent);
	name.setText(usernames.get(position));
	if(!App.recentmessages.equals(null)){
	if(App.recentmessages.contains(usernames.get(position))){
		txtMessage.setTypeface(Typeface.DEFAULT_BOLD);
		listview.setBackgroundColor(Color.MAGENTA);
		
		
	}}
	txtMessage.setText(chatmsg.get(position));
	if(whosent.get(position).equals(0)){
		whosent1.setImageResource(R.drawable.right);
		
	}
	else{
		whosent1.setImageResource(R.drawable.grey_triangle);
	}
	
	

	
	
	return listview;
}

	}



