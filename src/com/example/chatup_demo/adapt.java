package com.example.chatup_demo;

import java.io.File;
import java.util.ArrayList;

import com.example.addcontacts.ImageLoadingUtils;
import com.sun.mail.imap.Rights.Right;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class adapt extends BaseAdapter {
	Context context;
	int pos;
	
	static int i=0;
	ArrayList<String> senderList=new ArrayList<String>();
	ArrayList<String> receiverList=new ArrayList<String>();
	ArrayList<String> msgList=new ArrayList<String>();
	ArrayList<String> timeOfMsgList=new ArrayList<String>();
	ArrayList<String> filesList=new ArrayList<String>();
	ArrayList<String> statusList=new ArrayList<String>();
	ImageLoadingUtils util;

public adapt(Context context){
	this.context=context;
	this.senderList=App.senderList;
	this.receiverList=App.receiverList;
	this.msgList=App.msgList;
	this.timeOfMsgList=App.timeOfMsgList;
	this.filesList=App.fileslist;
	this.statusList=App.statuslist;
	util=new ImageLoadingUtils(this.context);
    
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
		Log.i("senderlist size","s"+senderList.size());
		App.lastposition=senderList.size();
		return (senderList.size());
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
	

	@SuppressLint("NewApi")
	@Override
	public View getView( int position, View listview, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  
	listview = inflater.inflate(R.layout.list_item_message,null);
	
	final TextView txtInfo=(TextView) listview.findViewById(R.id.txtInfo);
	final TextView txtMessage=(TextView) listview.findViewById(R.id.txtMessage);
	LinearLayout content = (LinearLayout) listview.findViewById(R.id.content);
    LinearLayout contentWithBG = (LinearLayout) listview.findViewById(R.id.contentWithBackground);	
	
	if(!senderList.get(position).equalsIgnoreCase("me")){
		if(filesList.get(position).equalsIgnoreCase("00000000")){
			contentWithBG.setBackgroundResource(R.drawable.bubble_gray);
			Log.i("in msg","msg setting");
			  LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentWithBG.getLayoutParams();
	          layoutParams.gravity = Gravity.LEFT;
	          contentWithBG.setLayoutParams(layoutParams);
	          
	          RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) content.getLayoutParams();
	          lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
	          lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	          content.setLayoutParams(lp);
	          layoutParams = (LinearLayout.LayoutParams) txtMessage.getLayoutParams();
	          layoutParams.gravity = Gravity.LEFT;
	          txtMessage.setLayoutParams(layoutParams);

	          layoutParams = (LinearLayout.LayoutParams) txtInfo.getLayoutParams();
	          layoutParams.gravity = Gravity.LEFT;
	          txtInfo.setLayoutParams(layoutParams);
	          txtInfo.setText(this.timeOfMsgList.get(position));
	      	txtMessage.setText(this.msgList.get(position));
			
				}
		else{
			Log.i("image","image setting");
			 LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentWithBG.getLayoutParams();
	          layoutParams.gravity = Gravity.LEFT;
	          contentWithBG.setLayoutParams(layoutParams);
	          RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) content.getLayoutParams();
	          lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
	          lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	          content.setLayoutParams(lp);
	          ImageView imgview= new ImageView(context);
	          imgview.setLayoutDirection(Gravity.LEFT);
	          imgview.setScaleType(ScaleType.FIT_XY);
	          contentWithBG.setBackgroundResource(R.drawable.bubble_gray);
	         
	          
	          if(filesList.get(position).contains(".bmp")||filesList.get(position).contains(".BMP")||filesList.get(position).contains(".gif")||filesList.get(position).contains(".GIF")||filesList.get(position).contains(".jpeg")||filesList.get(position).contains(".jpg")||filesList.get(position).contains(".JPEG")||filesList.get(position).contains(".png")||filesList.get(position).contains(".PNG")){
	        	  
	        	  contentWithBG.addView(imgview);
		          imgview.getLayoutParams().height = 330;
			         imgview.getLayoutParams().width=350;
	        	  Bitmap bmp= util.decodeBitmapFromPath(filesList.get(position));
	        	  layoutParams = (LinearLayout.LayoutParams) imgview.getLayoutParams();
	        	  layoutParams.gravity = Gravity.LEFT;
	        	  layoutParams.setMargins(0, 0,0,0);
	        	  
	        	  imgview.setImageBitmap(bmp);
	        	 
	        	  layoutParams = (LinearLayout.LayoutParams) txtInfo.getLayoutParams();
		          layoutParams.gravity = Gravity.LEFT;
		          layoutParams.setMargins(0, 0,0,20);
		          txtInfo.setLayoutParams(layoutParams);
		          txtInfo.setText(this.timeOfMsgList.get(position));
	        	  
	          }
	          else{
	        	  File file=new File(filesList.get(position));
	        	  layoutParams = (LinearLayout.LayoutParams) txtInfo.getLayoutParams();
		          layoutParams.gravity = Gravity.LEFT;
		          txtInfo.setLayoutParams(layoutParams);
		          txtInfo.setText(this.timeOfMsgList.get(position));
		          layoutParams = (LinearLayout.LayoutParams) txtMessage.getLayoutParams();
		          layoutParams.gravity = Gravity.LEFT;
		          txtMessage.setLayoutParams(layoutParams);
		          
		          
		          txtInfo.setText(this.timeOfMsgList.get(position));
		          Log.i("file name","a"+file.getName().toString());
		          txtMessage.setText(file.getName().toString());
		          
	          }
		}
		 
		 
         
          
        
          
		
		
	    }
	//for right side
	else{
		if(filesList.get(position).equalsIgnoreCase("00000000")){
			Log.i("setting message","mm");
		contentWithBG.setBackgroundResource(R.drawable.bubble_orange);
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentWithBG.getLayoutParams();
        layoutParams.gravity = Gravity.RIGHT;
        contentWithBG.setLayoutParams(layoutParams);
        
		 RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) content.getLayoutParams();
         lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
         lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
         content.setLayoutParams(lp);
         layoutParams = (LinearLayout.LayoutParams) txtMessage.getLayoutParams();
         layoutParams.gravity = Gravity.RIGHT;
         txtMessage.setLayoutParams(layoutParams);

         layoutParams = (LinearLayout.LayoutParams) txtInfo.getLayoutParams();
         layoutParams.gravity = Gravity.RIGHT;
         txtInfo.setLayoutParams(layoutParams);
         txtInfo.setText(this.timeOfMsgList.get(position));
     	txtMessage.setText(this.msgList.get(position));
	}
		else{
		
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentWithBG.getLayoutParams();
	        layoutParams.gravity = Gravity.RIGHT;
	        contentWithBG.setLayoutParams(layoutParams);
	        
			 RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) content.getLayoutParams();
	         lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
	         lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	         content.setLayoutParams(lp);
	         ImageView imgview= new ImageView(context);
	        
	        // imgview.setLayoutDirection(Gravity.LEFT);
	          TextView status=new TextView(context);
	          
	          Log.i("filelist","f"+filesList.get(position));
	          
	          if(filesList.get(position).contains(".bmp")||filesList.get(position).contains(".BMP")||filesList.get(position).contains(".gif")||filesList.get(position).contains(".GIF")||filesList.get(position).contains(".jpeg")||filesList.get(position).contains(".jpg")||filesList.get(position).contains(".JPEG")||filesList.get(position).contains(".png")||filesList.get(position).contains(".PNG")){
	        	  imgview.setScaleType(ScaleType.FIT_XY);
	  	        
	 	         
	 	         contentWithBG.addView(imgview);
	 	        contentWithBG.addView(status);
	 	         imgview.getLayoutParams().height = 330;
	 	         imgview.getLayoutParams().width=350;
	        	  Bitmap bmp= util.decodeBitmapFromPath(filesList.get(position));
	        	  layoutParams = (LinearLayout.LayoutParams) imgview.getLayoutParams();
	        	  layoutParams.gravity = Gravity.RIGHT;
	        	  layoutParams.setMargins(0, 0,0,0);
	        	 imgview.setLayoutParams(layoutParams);
	        	  layoutParams = (LinearLayout.LayoutParams) status.getLayoutParams();
	              layoutParams.gravity = Gravity.RIGHT;
	              layoutParams.setMargins(0, 0, 0, 20);
	              status.setLayoutParams(layoutParams);
	              Log.i("bmp","1"+bmp);
	             // txtMessage.setVisibility()
	        	  
	        	  imgview.setImageBitmap(bmp);
	        	  status.setText(statusList.get(position));
	        	  layoutParams = (LinearLayout.LayoutParams) txtInfo.getLayoutParams();
		          layoutParams.gravity = Gravity.RIGHT;
		          txtInfo.setLayoutParams(layoutParams);
		          //this.timeOfMsgList.get(position)
		          txtInfo.setText("hello");
	        	  
	        	  
	          }
	          else{
	        	  File file=new File(filesList.get(position));
	        	  layoutParams = (LinearLayout.LayoutParams) txtInfo.getLayoutParams();
		          layoutParams.gravity = Gravity.RIGHT;
		          txtInfo.setLayoutParams(layoutParams);
		          txtInfo.setText(this.timeOfMsgList.get(position));
		          layoutParams = (LinearLayout.LayoutParams) txtMessage.getLayoutParams();
		          layoutParams.gravity = Gravity.RIGHT;
		          txtMessage.setLayoutParams(layoutParams);
		          
		          
		          txtInfo.setText(this.timeOfMsgList.get(position));
		     //     Log.i("file name in right","a"+file.getName().toString());
		          txtMessage.setText(file.getName().toString());
		          
	          }
			
		}
	}
	
	
	return listview;
}

	}
