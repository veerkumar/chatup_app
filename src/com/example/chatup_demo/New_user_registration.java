package com.example.chatup_demo;


import java.util.UUID;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class New_user_registration extends Activity {

	public EditText nametext;
	public	EditText emailtext;
	public	Button register;
	public	String name;
	public	String email;
	public String username;
	public Boolean i=false;
	 static String key="";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		setContentView(R.layout.registration_page);
		nametext=(EditText) findViewById(R.id.name);
		emailtext=(EditText) findViewById(R.id.email);
		register=(Button) findViewById(R.id.register);
		
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name=nametext.getText().toString();
				email=emailtext.getText().toString();
				
				String reg="(@.*)";
				username=email.replaceAll(reg,"").toString();
				
				App.username=username;
				App.name=name;
				App.email=email;
				Log.i("new_regis_username","1"+App.username);
				String url="http://192.168.2.100:9090/plugins/userService/userservice?type=add&secret=veer&username="+username+"&name="+name+"&email="+email;
				Log.i("url",""+url);
				
				tryregistration(url);
				
			}
		});
		}

	

void tryregistration(String url){
	 Log.i("registration","url");
	
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.i("rsponce",""+response);
		    	
		    	
		    	Boolean match=response.contains("Already");
		    	Log.i("matched",""+response.contains("Already"));
		    	if(match.equals(true)){
		    		
		    		i=true;
		    		Log.i("old user","old");
		    		Toast.makeText(getApplicationContext(), "Email Id Already Exist please try again", Toast.LENGTH_SHORT).show();
		    		
		    	}
		    	else{
		    		
		    		
		    		Log.i("in new user","new");
		    		String ur1="http://192.168.2.100:9090/plugins/userService/userservice?type=delete&secret=veer&username="+username;
		    		Log.i("delete url",ur1);
		    		deleteregistration(ur1);
		    		
		    	}
		      
		    }
		});
		
			
	}
void deleteregistration(String url1){
	 Log.i("registration",url1);
//	 try {
//	      Thread.sleep(4000);
//	      // Do some stuff
//	    } catch (Exception e) {
//	      e.getLocalizedMessage();
//	    }
//	
		AsyncHttpClient client1 = new AsyncHttpClient();
		client1.get(url1, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.i("responce",""+response);
		    	
		    	
		    	Boolean match=response.contains("ok");
		    	
		    	if(match.equals(true)){
		    		
		    		
		    		Log.i("user delete","deleted");
		    		verification();
		    	}
		    	else{
		    		Toast.makeText(getApplicationContext(), "registration successful", 
		    				   Toast.LENGTH_LONG).show();
		    		
		    		Log.i("not deleted user","notdeleted");
		    	}
		      
		    }
		});
	
}

 public int verification(){
	 
	  Intent i = null;
	  Bundle bundle = new Bundle();
						try {
		i = new Intent(New_user_registration.this,
				ThirdParty.class);
		

	} catch (Exception e) {
		e.printStackTrace();
	} finally {

		Toast.makeText(getApplicationContext(), "Generating key", 
			    Toast.LENGTH_SHORT).show();
		key = generateKey();
		Log.i("key",""+key);
		bundle.putString("KEY", key);
		i.putExtras(bundle);
		Thread thread = new Thread(new Runnable(){
		    @Override
		    public void run() {
		        try {
		            //Your code goes here
		        	GMailSender sender = new GMailSender("chatup.veer@gmail.com", "987654321abcd");
                    sender.sendMail("CHATUP ADMIN:Your Key for registration",   
                            key,   
                            "chatup.veer@gmail.com",   
                            email);   
		        } catch (Exception e) {
		        	Log.e("SendMail", e.getMessage(), e);
		        }
		    }
		});

		thread.start(); 
		
		
		Toast.makeText(getApplicationContext(), "Redirecting...", 
			    Toast.LENGTH_SHORT).show();
					     
		startActivity(i);
	}

	  
	  
	  //Toast.makeText(getApplicationContext(), x,Toast.LENGTH_LONG).show();
  return 0;
	 
 }
 private String generateKey() {
		// TODO Auto-generated method stub
		String uuid = UUID.randomUUID().toString();
		key = uuid.substring(0,6);
		return key;
	}

}
