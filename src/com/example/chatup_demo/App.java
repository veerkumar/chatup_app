package com.example.chatup_demo;

import java.util.ArrayList;
import java.util.HashMap;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.filetransfer.Filetransfer1;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ListView;

public class App extends Application {
	public static String ip ="192.168.2.100";
	

	public static  XMPPConnection connection;

public static	ListView list;
public static ArrayList<HashMap<String, Object>> alist1;
public static ArrayList<String> senderList;
public static ArrayList<String> receiverList;
public static ArrayList<String> msgList;
public static ArrayList<String> fileslist;
public static ArrayList<String> statuslist;
public static ArrayList<String> timeOfMsgList;
public static JSONArray jsonchatArray;
public static String me;
public static String pattner;
public static Context Mainactivity;
public static int lastposition;
public static  String username;
public static String email;
public static String name;
public static ArrayList<String> friendusername;
public static ArrayList<String> friendname;
public static ListView friendlist;
public static Context friendlistcontext;
public static Context chatlistcontext;
public static ListView chatlist;
public static ArrayList<String> recentmessages;

// for chatlist item
public static ArrayList<String> chatmsg;
public static ArrayList<String> usernames;
public static ArrayList<Integer> whosent;
public static ArrayList<String> files_chatlist;

public static Boolean InternetStatus;

public static Bitmap[] imag;

public static Bitmap uploadimage;

public static FileTransferListener filelistner;
public static OutgoingFileTransfer transfer;
//public static Filetransfer1 filetransferobj;
public static ArrayList<HashMap<String, Object>> filesendingStatus;
public static  ChatlistFragment fr;

public static boolean status;


}
