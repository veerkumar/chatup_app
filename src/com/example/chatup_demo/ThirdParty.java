package com.example.chatup_demo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import android.widget.TextView;
import android.widget.Toast;

//import com.javacodegeeks.android.sendemailtest.MainActivity;

public class ThirdParty extends Activity implements OnClickListener{

	EditText et;
	TextView tv;
	Button b;
	static String key="";
	static String sendTo = "";
	static String subject = "Your Key for Account Manager";
	@Override
protected void onCreate(Bundle savedInstanceState) {
							// TODO Auto-generated method stub
							super.onCreate(savedInstanceState);
							setContentView(R.layout.tpa_input);
							initialiseVariables();
	}

private void initialiseVariables() {
							// TODO Auto-generated method stub
							//generateCode();
						et = (EditText)findViewById(R.id.alpha_field);
						tv = (TextView)findViewById(R.id.alpha_tag);
						b = (Button)findViewById(R.id.submit);
						b.setOnClickListener(this);
						Bundle b = getIntent().getExtras();
						key = b.getString("KEY");
						
							Log.i("thirtd party",""+key);
	}

		@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
					
					String x = et.getText().toString();
					Log.i("x",x);
					if(x.matches(key))
					{
						
						String url="http://192.168.2.100:9090/plugins/userService/userservice?type=add&secret=veer&username="+App.username+"&password=veer&name="+App.name+"&email="+App.email;
						Log.i("3rdparty",""+url);
						
							try {
								registration(url);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					
						
						
						
						
			
					}else{
						
						Toast.makeText(ThirdParty.this,"Invalid Passcode",Toast.LENGTH_SHORT).show();
						et.setText("");
					}
		
		
	}
	

	
void registration(String url) throws IOException, JSONException,FileNotFoundException{
			 Log.i("registration","url");
			
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(url, new AsyncHttpResponseHandler() {
				    @Override
				    public void onSuccess(String response) {
				    	Log.i("response_final","1"+response);
				    	
				    	
				    	Boolean match=response.contains("ok");
				    	Log.i("matched",""+response.contains("ok"));
				    	if(match.equals(true)){
				    		
				    		Toast.makeText(getApplicationContext(), "successful registration", 
								    Toast.LENGTH_SHORT).show();
				    		JSONArray data=new JSONArray();
				    		
				    			JSONObject jsonmsgObject;
				    		
				    			jsonmsgObject=new JSONObject();
				    		
				    			
				    			
									try {
										jsonmsgObject.put("username", App.username);
										jsonmsgObject.put("name",App.name);
							    		
						    			jsonmsgObject.put("email",App.email);
						    			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
						    			String currentDateandTime = sdf.format(new Date());
						    			Log.i("currentdate",""+currentDateandTime);
						    			jsonmsgObject.put("TimeOfCreation",currentDateandTime);
						    			data.put(jsonmsgObject);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
					    			
								
				    			
				    			
				    		//	Log.i("before msg",text);
				    		
				    	
				    			
				    			
				    			String text=data.toString();
				    			Log.i("text","1"+text);
				    			//String text=UIHelper.getText(this, R.id.editText1);
				    			String Filename="registration.txt";
				    			FileOutputStream fos = null;
								try {
									fos = openFileOutput(Filename, MODE_PRIVATE);
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
									
								
				    			
									try {
										fos.write(text.getBytes());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								
				    		
				    	
				 
				    	Intent i = new Intent(ThirdParty.this,
								Chatlist.class);
							startActivity(i);
				    }}
				});
				
				
			}	
	

}
