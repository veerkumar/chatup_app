package com.example.filetransfer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.provider.PrivacyProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.GroupChatInvitation;
import org.jivesoftware.smackx.PrivateDataManager;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.bytestreams.ibb.provider.CloseIQProvider;
import org.jivesoftware.smackx.bytestreams.ibb.provider.DataPacketProvider;
import org.jivesoftware.smackx.bytestreams.ibb.provider.OpenIQProvider;
import org.jivesoftware.smackx.bytestreams.socks5.provider.BytestreamsProvider;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferNegotiator;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.packet.AttentionExtension;
import org.jivesoftware.smackx.packet.ChatStateExtension;
import org.jivesoftware.smackx.packet.LastActivity;
import org.jivesoftware.smackx.packet.Nick;
import org.jivesoftware.smackx.packet.OfflineMessageInfo;
import org.jivesoftware.smackx.packet.OfflineMessageRequest;
import org.jivesoftware.smackx.packet.SharedGroupsInfo;
import org.jivesoftware.smackx.provider.DataFormProvider;
import org.jivesoftware.smackx.provider.DelayInformationProvider;
import org.jivesoftware.smackx.provider.DiscoverInfoProvider;
import org.jivesoftware.smackx.provider.DiscoverItemsProvider;
import org.jivesoftware.smackx.provider.HeaderProvider;
import org.jivesoftware.smackx.provider.HeadersProvider;
import org.jivesoftware.smackx.provider.MUCAdminProvider;
import org.jivesoftware.smackx.provider.MUCOwnerProvider;
import org.jivesoftware.smackx.provider.MUCUserProvider;
import org.jivesoftware.smackx.provider.MessageEventProvider;
import org.jivesoftware.smackx.provider.MultipleAddressesProvider;
import org.jivesoftware.smackx.provider.RosterExchangeProvider;
import org.jivesoftware.smackx.provider.StreamInitiationProvider;
import org.jivesoftware.smackx.provider.VCardProvider;
import org.jivesoftware.smackx.provider.XHTMLExtensionProvider;
import org.jivesoftware.smackx.pubsub.provider.AffiliationProvider;
import org.jivesoftware.smackx.pubsub.provider.AffiliationsProvider;
import org.jivesoftware.smackx.pubsub.provider.ConfigEventProvider;
import org.jivesoftware.smackx.pubsub.provider.EventProvider;
import org.jivesoftware.smackx.pubsub.provider.FormNodeProvider;
import org.jivesoftware.smackx.pubsub.provider.ItemProvider;
import org.jivesoftware.smackx.pubsub.provider.ItemsProvider;
import org.jivesoftware.smackx.pubsub.provider.PubSubProvider;
import org.jivesoftware.smackx.pubsub.provider.RetractEventProvider;
import org.jivesoftware.smackx.pubsub.provider.SimpleNodeProvider;
import org.jivesoftware.smackx.pubsub.provider.SubscriptionProvider;
import org.jivesoftware.smackx.pubsub.provider.SubscriptionsProvider;
import org.jivesoftware.smackx.search.UserSearch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.chatup_demo.App;
import com.example.chatup_demo.Chatlist;
import com.example.chatup_demo.ListviewAct;
import com.example.chatup_demo.WriteRecivedFile;
import com.example.chatup_demo.adapt;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

public  class Filetransfer1  {
	public static final String HOST = "192.168.2.100";
	public static final int PORT = 5222;
	
	public static final String USERNAME = App.username;
	public static final String PASSWORD = "admin";

public XMPPConnection connection=App.connection;
	private ArrayList<String> messages = new ArrayList<String>();
	private Handler mHandler = new Handler();
	public String recept;
	public  FileTransferManager managerListner;
	public  FileTransferListener filelistner;
	public File file;
	Context context;
	 HashMap<String, Object> map;
	 Activity activity;
	 public ListView listview;

	

	
public Filetransfer1(Activity activity) {
	// TODO Auto-generated constructor stub
	this.activity=activity;
}

public void sendfile   (ArrayList<JSONObject> filelist) 
{
	 
	if(App.connection!=null){
		File file = null;
		int lock=0;
	OutgoingFileTransfer transfer;
	    configureProviderManager(App.connection);
	    FileTransferNegotiator.IBB_ONLY = true;
	    FileTransferNegotiator.setServiceEnabled(App.connection, true);
	    FileTransferManager manager = new FileTransferManager(App.connection);
	  
	while(filelist.size()!=0){
	lock=0;
						    	JSONObject j=filelist.get(0);
						    	String too = null;
								try {
									too = (String) j.get("to");
									String path=(String) j.get("path");
									 file =new File(path);
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								 map=new HashMap<String,Object>();
								 map.put("filepath", file);
								 
								 
						    	
						    	
						       String to = App.connection.getRoster().getPresence(too+"@openfire").getFrom();
								//String to= "virender@openfire/Spark 2.6.3";
						       Log.i("to",to);
						     transfer = manager.createOutgoingFileTransfer(to);
						     App.transfer=transfer;

						 
						    Log.i("sendingfile","1"+file);
						
						    try {
						       // Log.d("file sending",file.getAbsolutePath()+" "+file.getName());
						        configureProviderManager(App.connection);
						        if(App.connection.isConnected()){
						       transfer.sendFile(file, ""+file.getName());}
						       
						    }catch (XMPPException e) {
						    	//Log.i("exception occure","infile transfer");
							       e.printStackTrace();
							    } catch (IllegalStateException e) {
								// TODO: handle exception
							    	e.printStackTrace();
						    //	Log.i("illigal state exception","catu");
						    	//transfer.cancel();
						    }
						    
					
						    while(!transfer.isDone()) {
						    	
						   
							        if (transfer.getStatus() == Status.error) {
							            
							        		transfer.cancel();
							        }
						        int i=0;
					// this code use for debugging
						        if(transfer.getStatus().equals(Status.refused))
						                 System.out.println("refused  " + transfer.getError());
						        else if( transfer.getStatus().equals(Status.error))
								    {		Log.i("error","error");
								    if(lock==0){
								    	lock=1; //locking
								    		filelist.remove(0);
								    		map.put("status","waiting for connection" );
								    		if(transfer.getProgress()<1){
												try {
													offlineFileTransferList(file,too);
													
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
													
											
												}
								    		}
								    map.put("status","waiting for connection" );
						             System.out.println(" error " + transfer.getError());
						             } 
						        if(transfer.getStatus().equals(Status.cancelled)){
						        	if(lock==0){
								        		lock=1;
									    		filelist.remove(0);
									    		map.put("status","waiting for connection" );
									    		 //locking
							    		if(transfer.getProgress()<1){
											try {
												offlineFileTransferList(file,too);
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
										}}}
						       Log.i(" cancelled  ","1"+ transfer.getError());
						         }
						         
						        else
						        	i++;
						        		if(i==10){
						        			System.out.println("Success");
						          		i=0;
						        	}
						        	if(transfer.isDone()){
						        		if(lock==0){
						        			lock=1; //locking
								    		filelist.remove(0);
								    		
										Log.i("filesent",""+file+"///"+transfer.isDone()+transfer.getProgress());
										
										if(transfer.getProgress()<1){
										try {
											offlineFileTransferList(file,too);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											map.put("status", "waiting for connection");
									}}
										else{
											map.put("status", "delivered"); //means delivered
										}
									} 
						        		}
					
						    }
						
			
	    		   } //while loop ends
	
	      }
	else {
		
					File file = null;
					for(int i=0;i<filelist.size();i++){
						
						JSONObject j=filelist.get(i);
				    	String too = null;
						try {
							too = (String) j.get("to");
							String path=(String) j.get("path");
							 file =new File(path);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						 map=new HashMap<String,Object>();
						 map.put("filepath", file);
						 map.put("status", "waiting for connection");
						try {
							offlineFileTransferList(file,too);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
	}
	Log.i("map","+"+map);
App.filesendingStatus.add(map);	
}
public void ReceiveFile() {
	 
		configureProviderManager(App.connection);
		ServiceDiscoveryManager sdm = ServiceDiscoveryManager
                .getInstanceFor(App.connection);

		 if (sdm == null)
             sdm = new ServiceDiscoveryManager(App.connection);

         sdm.addFeature("http://jabber.org/protocol/disco#info");

         sdm.addFeature("jabber:iq:privacy");

         // Create the file transfer manager
         managerListner = new FileTransferManager(
                 App.connection);
         FileTransferNegotiator.IBB_ONLY = true;
        
         FileTransferNegotiator
                 .setServiceEnabled(App.connection, true);
         

         Log.i("File transfere manager", "created");
         App.filelistner=null;
      App.filelistner=filelistner=   new FileTransferListener() {
    	  String from;
				
				@Override
				public void fileTransferRequest(FileTransferRequest request) {
					 Log.i("Recieve File",
                          "new file transfere request  new file transfere request   new file transfere request");

                  Log.i("file request",
                          "from" + request.getRequestor());
                  from=request.getRequestor();
                  		Log.i("filesize","0"+request.getFileSize());
                  		final IncomingFileTransfer inTransfer = request.accept();
                  		
                  		
                  		  
                  		 try {  
                  			  
                  			File mf = Environment.getExternalStorageDirectory();
                  			Log.i("filen nmae","1"+inTransfer.getFileName());
                  			file = new File(mf.getAbsoluteFile()+File.separator+"/ChatUp/");
                  			if(!file.isDirectory())
                  			{
                  			file.mkdir();
                  			} 
                    		  file = new File(mf.getAbsoluteFile()+"/Chatup/"+inTransfer.getFileName());
                    		 file =fileexists(file);
                              Log.i("info", file.getAbsolutePath());  
                             
                              new Thread(){  
                
                                  @Override  
                                  public void run() {  
                                      // TODO Auto-generated method stub  
                                      try {  
                                          inTransfer.recieveFile(file);  
                                          while (!inTransfer.getStatus().equals(Status.complete)) {  
                                              if (inTransfer.getStatus().equals(Status.error)) {  
                                                  Log.e("error","ERROR!!! " + inTransfer.getError());  
                                                  inTransfer.cancel();  
                                                  inTransfer.recieveFile(file);  
                                              } else {  
                                                //  Log.i("info", "status:" + inTransfer.getStatus());  
                                                //  Log.i("info", "process:" + inTransfer.getProgress());  
                                              }  
                                          }  
                                          if (inTransfer.isDone()) {  
                                              // Log.i("info", "Done+status:" + inTransfer.getStatus());  
                                              // Log.i("info",  
                                               //        "Done+process:" + inTransfer.getProgress());  
                                         	  
                                         	  String files[]=new String[1];
                                         	  files[0]=file.toString();
                                         	  WriteRecivedFile writerecivedfileobj=new WriteRecivedFile(activity);
                                         	 String reg="(@.*)";
                             				from=from.replaceAll(reg,"").toString();
                             			App.recentmessages.add(from);
                             			activity.runOnUiThread(new Runnable() {
                      					     @Override
                      					     public void run() {
                             			App.fr.setadapter(App.chatlistcontext);
                      					   }
                       					});
                              
                                         	  try {
            										writerecivedfileobj.writeRecivedFileTransfer(files, from);
            									} catch (IOException e) {
            										// TODO Auto-generated catch block
            										e.printStackTrace();
            									} catch (JSONException e) {
            										// TODO Auto-generated catch block
            										e.printStackTrace();
            									}
                                           }
                                          
                                      } catch (XMPPException e) {  
                                          // TODO Auto-generated catch block  
                                          e.printStackTrace();  
                                      }  
                                  }  
                                    
                              }.start();  
                           
                
                          } catch (Exception e) {  
                              e.printStackTrace();  
                          }  
                
					
				}
				
			};

         // Create the listener
         managerListner
                 .addFileTransferListener(filelistner);
	    
}
public void configureProviderManager(XMPPConnection connection) {


	    ProviderManager.getInstance().addIQProvider("query","http://jabber.org/protocol/bytestreams", new BytestreamsProvider());
	    ProviderManager.getInstance().addIQProvider("query","http://jabber.org/protocol/disco#items", new DiscoverItemsProvider());
	    ProviderManager.getInstance().addIQProvider("query","http://jabber.org/protocol/disco#info", new DiscoverInfoProvider());



	    ProviderManager.getInstance().addIQProvider("query",
	            "http://jabber.org/protocol/bytestreams",
	            new BytestreamsProvider());
	    ProviderManager.getInstance().addIQProvider("query",
	            "http://jabber.org/protocol/disco#items",
	            new DiscoverItemsProvider());
	    ProviderManager.getInstance().addIQProvider("query",
	            "http://jabber.org/protocol/disco#info",
	            new DiscoverInfoProvider());

	    ServiceDiscoveryManager sdm = ServiceDiscoveryManager.getInstanceFor(connection);
	    if (sdm == null)
	        sdm = new ServiceDiscoveryManager(connection);

	    sdm.addFeature("http://jabber.org/protocol/disco#info");
	    sdm.addFeature("http://jabber.org/protocol/disco#item");
	    sdm.addFeature("jabber:iq:privacy");


	    ProviderManager pm = ProviderManager.getInstance();

	    // The order is the same as in the smack.providers file

	    //  Private Data Storage
	    pm.addIQProvider("query","jabber:iq:private", new PrivateDataManager.PrivateDataIQProvider());
	    //  Time
	    try {
	        pm.addIQProvider("query","jabber:iq:time", Class.forName("org.jivesoftware.smackx.packet.Time"));
	    } catch (ClassNotFoundException e) {
	        System.err.println("Can't load class for org.jivesoftware.smackx.packet.Time");
	    }

	    //  Roster Exchange
	    pm.addExtensionProvider("x","jabber:x:roster", new RosterExchangeProvider());
	    //  Message Events
	    pm.addExtensionProvider("x","jabber:x:event", new MessageEventProvider());
	    //  Chat State
	    pm.addExtensionProvider("active","http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
	    pm.addExtensionProvider("composing","http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
	    pm.addExtensionProvider("paused","http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
	    pm.addExtensionProvider("inactive","http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());
	    pm.addExtensionProvider("gone","http://jabber.org/protocol/chatstates", new ChatStateExtension.Provider());

	    //  XHTML
	    pm.addExtensionProvider("html","http://jabber.org/protocol/xhtml-im", new XHTMLExtensionProvider());

	    //  Group Chat Invitations
	    pm.addExtensionProvider("x","jabber:x:conference", new GroupChatInvitation.Provider());
	    //  Service Discovery # Items
	    pm.addIQProvider("query","http://jabber.org/protocol/disco#items", new DiscoverItemsProvider());
	    //  Service Discovery # Info
	    pm.addIQProvider("query","http://jabber.org/protocol/disco#info", new DiscoverInfoProvider());
	    //  Data Forms
	    pm.addExtensionProvider("x","jabber:x:data", new DataFormProvider());
	    //  MUC User
	    pm.addExtensionProvider("x","http://jabber.org/protocol/muc#user", new MUCUserProvider());
	    //  MUC Admin
	    pm.addIQProvider("query","http://jabber.org/protocol/muc#admin", new MUCAdminProvider());
	    //  MUC Owner
	    pm.addIQProvider("query","http://jabber.org/protocol/muc#owner", new MUCOwnerProvider());
	    //  Delayed Delivery
	    pm.addExtensionProvider("x","jabber:x:delay", new DelayInformationProvider());
	    pm.addExtensionProvider("delay", "urn:xmpp:delay", new DelayInformationProvider());
	    //  Version
	    try {
	        pm.addIQProvider("query","jabber:iq:version", Class.forName("org.jivesoftware.smackx.packet.Version"));
	    } catch (ClassNotFoundException e) {
	        System.err.println("Can't load class for org.jivesoftware.smackx.packet.Version");
	    }
	    //  VCard
	    pm.addIQProvider("vCard","vcard-temp", new VCardProvider());
	    //  Offline Message Requests
	    pm.addIQProvider("offline","http://jabber.org/protocol/offline", new OfflineMessageRequest.Provider());
	    //  Offline Message Indicator
	    pm.addExtensionProvider("offline","http://jabber.org/protocol/offline", new OfflineMessageInfo.Provider());
	    //  Last Activity
	    pm.addIQProvider("query","jabber:iq:last", new LastActivity.Provider());
	    //  User Search
	    pm.addIQProvider("query","jabber:iq:search", new UserSearch.Provider());
	    //  SharedGroupsInfo
	    pm.addIQProvider("sharedgroup","http://www.jivesoftware.org/protocol/sharedgroup", new SharedGroupsInfo.Provider());

	    //  JEP-33: Extended Stanza Addressing
	    pm.addExtensionProvider("addresses","http://jabber.org/protocol/address", new MultipleAddressesProvider());

	    //   FileTransfer
	    pm.addIQProvider("si","http://jabber.org/protocol/si", new StreamInitiationProvider());
	    pm.addIQProvider("query","http://jabber.org/protocol/bytestreams", new BytestreamsProvider());
	    pm.addIQProvider("open","http://jabber.org/protocol/ibb", new OpenIQProvider());
	    pm.addIQProvider("data","http://jabber.org/protocol/ibb", new DataPacketProvider());
	    pm.addIQProvider("close","http://jabber.org/protocol/ibb", new CloseIQProvider());
	    pm.addExtensionProvider("data","http://jabber.org/protocol/ibb", new DataPacketProvider());

	    //  Privacy
	    pm.addIQProvider("query","jabber:iq:privacy", new PrivacyProvider());

	    // SHIM
	    pm.addExtensionProvider("headers", "http://jabber.org/protocol/shim", new HeadersProvider());
	    pm.addExtensionProvider("header", "http://jabber.org/protocol/shim", new HeaderProvider());

	    // PubSub
	    pm.addIQProvider("pubsub", "http://jabber.org/protocol/pubsub", new PubSubProvider());
	    pm.addExtensionProvider("create", "http://jabber.org/protocol/pubsub", new SimpleNodeProvider());
	    pm.addExtensionProvider("items", "http://jabber.org/protocol/pubsub", new ItemsProvider());
	    pm.addExtensionProvider("item", "http://jabber.org/protocol/pubsub", new ItemProvider());
	    pm.addExtensionProvider("subscriptions", "http://jabber.org/protocol/pubsub", new SubscriptionsProvider());
	    pm.addExtensionProvider("subscription", "http://jabber.org/protocol/pubsub", new SubscriptionProvider());
	    pm.addExtensionProvider("affiliations", "http://jabber.org/protocol/pubsub", new AffiliationsProvider());
	    pm.addExtensionProvider("affiliation", "http://jabber.org/protocol/pubsub", new AffiliationProvider());
	    pm.addExtensionProvider("options", "http://jabber.org/protocol/pubsub", new FormNodeProvider());
	    // PubSub owner
	    pm.addIQProvider("pubsub", "http://jabber.org/protocol/pubsub#owner", new PubSubProvider());
	    pm.addExtensionProvider("configure", "http://jabber.org/protocol/pubsub#owner", new FormNodeProvider());
	    pm.addExtensionProvider("default", "http://jabber.org/protocol/pubsub#owner", new FormNodeProvider());
	    // PubSub event
	    pm.addExtensionProvider("event", "http://jabber.org/protocol/pubsub#event", new EventProvider());
	    pm.addExtensionProvider("configuration", "http://jabber.org/protocol/pubsub#event", new ConfigEventProvider());
	    pm.addExtensionProvider("delete", "http://jabber.org/protocol/pubsub#event", new SimpleNodeProvider());
	    pm.addExtensionProvider("options", "http://jabber.org/protocol/pubsub#event", new FormNodeProvider());
	    pm.addExtensionProvider("items", "http://jabber.org/protocol/pubsub#event", new ItemsProvider());
	    pm.addExtensionProvider("item", "http://jabber.org/protocol/pubsub#event", new ItemProvider());
	    pm.addExtensionProvider("retract", "http://jabber.org/protocol/pubsub#event", new RetractEventProvider());
	    pm.addExtensionProvider("purge", "http://jabber.org/protocol/pubsub#event", new SimpleNodeProvider());

	    // Nick Exchange
	    pm.addExtensionProvider("nick", "http://jabber.org/protocol/nick", new Nick.Provider());

	    // Attention
	    pm.addExtensionProvider("attention", "urn:xmpp:attention:0", new AttentionExtension.Provider());

	    //input
	    pm.addIQProvider("si", "http://jabber.org/protocol/si",
	            new StreamInitiationProvider());
	    pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams",
	            new BytestreamsProvider());
	    pm.addIQProvider("open", "http://jabber.org/protocol/ibb",
	            new OpenIQProvider());
	    pm.addIQProvider("close", "http://jabber.org/protocol/ibb",
	            new CloseIQProvider());
	    pm.addExtensionProvider("data", "http://jabber.org/protocol/ibb",
	            new DataPacketProvider());

	}
public File fileexists(File file1){
	
	if(file1.exists()){
		file1=new File( file1.toString()+"_"+1);
		file1=fileexists(file1);
		return file1;
	}
	else{
		return file1;
	}
	
	
	
}
public File getFile(){
	File root,file,outputfile = null; 
	root = Environment.getExternalStorageDirectory();
	file = new File(root.getAbsoluteFile()+File.separator+"/ChatUp");
	if(!file.isDirectory())
	{
						file.mkdir();
						file = new File(root.getAbsoluteFile()+File.separator+"/ChatUp/OfflineMsg");
						file.mkdir();
						outputfile = new File(file,"FileTOTransfer.txt");
						
						if(!outputfile.exists())
						try {
						outputfile.createNewFile();
						} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
						Log.i("outputfile","1"+outputfile);
						return outputfile;
			}
	else{
							file = new File(root.getAbsoluteFile()+File.separator+"/ChatUp/OfflineMsg");
							if(!file.isDirectory()){
										file.mkdir();
										outputfile = new File(file,"FileToTransfer.txt");
										if(!outputfile.exists())
										try {
										outputfile.createNewFile();
										} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();}
								
								}
							else{
										outputfile = new File(file,"FileToTransfer.txt");
										if(!outputfile.exists())
										try {
										outputfile.createNewFile();
										} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();}
							}
					
						//String path = file.toString()+"/Contacts.txt";
							Log.i("outputfile","2"+outputfile);
							return outputfile;
		} 
	
}
public String readofflinefile(File outputfile)  {
     File file;
    // outputfile=getFile();
     String jsonstr;
     StringBuilder sb = new StringBuilder();
    
    
			     BufferedReader reader;
				try {
					reader = new BufferedReader(new FileReader(outputfile));
					 StringBuffer buffer = new StringBuffer();
				     while((jsonstr = reader.readLine())!=null)
				     {
				     sb.append(jsonstr);
				     sb.append('\n');
				     }
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    

    
     Log.i("buffer: ",sb.toString());
    return sb.toString();
     
     }
public void offlineFileTransferList(File file2,String to) throws JSONException, IOException{
	File outputfile;
	int Flag=0;
	BufferedWriter writer;
	outputfile=getFile();
	        String data=readofflinefile(outputfile);
	        JSONArray jsonArray;
	        Log.i("data","1"+data);
	        if(!data.equalsIgnoreCase("")){
	        jsonArray=new JSONArray(data);}
	        else{
	        	jsonArray=new JSONArray();
	        }
	        JSONObject jsonfilepathObject;
			//	Log.i("jsonarray assing",""+data);
				
	        jsonfilepathObject=new JSONObject();
	        jsonfilepathObject.put("filepath", file2);
	        Log.i("filepath on canceal","1"+file2);
	        jsonfilepathObject.put("to",to);
	       
	        	jsonArray.put(jsonfilepathObject);
	        	
				 
				 writer = new BufferedWriter(new FileWriter(outputfile,true));
				 
				 writer.write(jsonArray.toString());
				
				 writer.flush();
				 Log.i("writing canceal","done");
				 writer.close(); 
	     
			 
			
	
}

public void ReceiveFile(final ListView listview1) {
	// TODO Auto-generated method stub
this.listview=listview1;
	configureProviderManager(App.connection);
	ServiceDiscoveryManager sdm = ServiceDiscoveryManager
            .getInstanceFor(App.connection);

	 if (sdm == null)
         sdm = new ServiceDiscoveryManager(App.connection);

     sdm.addFeature("http://jabber.org/protocol/disco#info");

     sdm.addFeature("jabber:iq:privacy");

     // Create the file transfer manager
     managerListner = new FileTransferManager(
             App.connection);
     FileTransferNegotiator.IBB_ONLY = true;
    
     FileTransferNegotiator
             .setServiceEnabled(App.connection, true);
     

     Log.i("File transfere manager", "created");
     App.filelistner=null;
  App.filelistner=filelistner=   new FileTransferListener() {
	  String from;
			
			@Override
			public void fileTransferRequest(FileTransferRequest request) {
				 Log.i("Recieve File",
                      "new file transfere request  new file transfere request   new file transfere request");

              Log.i("file request",
                      "from" + request.getRequestor());
              from=request.getRequestor();
              		Log.i("filesize","0"+request.getFileSize());
              		final IncomingFileTransfer inTransfer = request.accept();
              		
              		
              		  
              		 try {  
              			  
              			File mf = Environment.getExternalStorageDirectory();
              			Log.i("filen nmae","1"+inTransfer.getFileName());
              			file = new File(mf.getAbsoluteFile()+File.separator+"/ChatUp/");
              			Log.i("filepatheeeee","1"+file);
              			if(!file.isDirectory())
              			{
              			file.mkdir();
              			} 
                		  file = new File(mf.getAbsoluteFile()+"/Chatup/"+inTransfer.getFileName());
                		 file =fileexists(file);
                          Log.i("info", file.getAbsolutePath());  
                         
                          new Thread(){  
            
                              @Override  
                              public void run() {  
                                  // TODO Auto-generated method stub  
                                  try {  
                                      inTransfer.recieveFile(file);  
                                      while (!inTransfer.getStatus().equals(Status.complete)) {  
                                          if (inTransfer.getStatus().equals(Status.error)) {  
                                              Log.e("error","ERROR!!! " + inTransfer.getError());  
                                              inTransfer.cancel();  
                                              inTransfer.recieveFile(file);  
                                          } else {  
                                            //  Log.i("info", "status:" + inTransfer.getStatus());  
                                            //  Log.i("info", "process:" + inTransfer.getProgress());  
                                          }  
                                      }  
                                      if (inTransfer.isDone()) {  
                                          // Log.i("info", "Done+status:" + inTransfer.getStatus());  
                                          // Log.i("info",  
                                           //        "Done+process:" + inTransfer.getProgress());  
                                     	  
                                     	  String files[]=new String[1];
                                     	  files[0]=file.toString();
                                     	  WriteRecivedFile writerecivedfileobj=new WriteRecivedFile(activity);
                                     	 String reg="(@.*)";
                         				from=from.replaceAll(reg,"").toString();
                          
                                     	  try {
                                     		 App.senderList.add(from);
                                     		App.receiverList.add("me");
                                     		App.msgList.add("00000000");
                                     		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                                    		String currentDateandTime = sdf.format(new Date());
                                    	
                                    		App.timeOfMsgList.add(currentDateandTime);
                                     		
                                     		App.fileslist.add(files[0]);
                                     		App.statuslist.add("delivered");
                                     		//App.jsonchatArray=new JSONArray();
                                     		activity.runOnUiThread(new Runnable() {
                       					     @Override
                       					     public void run() {
                       					    	 
                       					    	 adapt adapter=new adapt(activity);
                       								listview.setAdapter(adapter);
                       					//stuff that updates ui
                       								Log.i("running","running");
                       								App.lastposition+=1;
                       								if(App.lastposition>2){
                       								 listview.setSelectionFromTop(App.lastposition-1, App.lastposition);}
                       									
                       					    }
                       					});
											writerecivedfileobj.writeRecivedFileTransfer(files, from,listview);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
                                       }
                                      
                                  } catch (XMPPException e) {  
                                      // TODO Auto-generated catch block  
                                      e.printStackTrace();  
                                  }  
                              }  
                                
                          }.start();  
                       
            
                      } catch (Exception e) {  
                          e.printStackTrace();  
                      }  
            
				
			}
			
		};

     // Create the listener
     managerListner
             .addFileTransferListener(filelistner);

	
}

}
  