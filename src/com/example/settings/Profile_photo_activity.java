
package com.example.settings;
import com.example.chatup_demo.App;
import com.example.chatup_demo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

/**
 * @author paulburke (ipaulpro)
 */
public class Profile_photo_activity extends Activity {

    private static final String TAG = "FileChooserExampleActivity";

    private static final int REQUEST_CODE = 6384; // onActivityResult request
                                                  // code

	//private static int RESULT_LOAD_IMAGE = 1;
	private static int RESULT_CLICK_IMAGE = 2;
	String root,imagePath,path,the_string_response,res="",username=App.username,tempPath;
	File ChatUp,profile_photo,photo;
	InputStream inputStream;
	Bitmap bmp;
	Button upload;
	public ImageLoadingUtils utils;
	ImageView image; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a simple button to start the file chooser process
        LinearLayout lv= new LinearLayout(this);
        LinearLayout linear = new LinearLayout(this);
        lv.setOrientation(LinearLayout.VERTICAL);
        lv.addView(linear);
        upload = new Button(this);
        upload.setText("Upload");
		upload.setVisibility(View.GONE);
		image = new ImageView(this);
        Button button = new Button(this);
        button.setText(R.string.choose_file);
        linear.addView(button);
        Button click = new Button(this);
        click.setText("Click Photo");
        linear.addView(click);
        lv.addView(image);
        upload.setGravity(Gravity.CENTER);
        //upload.setLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lv.addView(upload);
        root = Environment.getExternalStorageDirectory().toString();
        tempPath = root + "/DCIM/Camera/";
        		
        ChatUp = new File(root + "/ChatUp");
        //username=username.replace(".","&");
		/*if(!(ChatUp.exists() && ChatUp.isDirectory())){
			ChatUp.mkdir();
			profile_photo = new File(root + "/ChatUp/Profile Photos");
			profile_photo.mkdir();		
		}
		if(!(profile_photo.exists() && profile_photo.isDirectory()))
		{
			profile_photo = new File(root + "/ChatUp/Profile Photos");
			profile_photo.mkdir();
		}*/
		imagePath = root+"/ChatUp/Profile Photos/"+username+".jpg";
		photo = new File(imagePath);
		if(photo.exists()){
			Bitmap bmp = BitmapFactory.decodeFile(imagePath);
					image.setImageBitmap(bmp);
		}
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the file chooser dialog
                showChooser();
            }
        });
        click.setOnClickListener(new View.OnClickListener() {
					@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, RESULT_CLICK_IMAGE);
			}
		});
        
        upload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
								
				// TODO Auto-generated method stub
				Bitmap bm = BitmapFactory.decodeFile(path);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			    byte [] byte_arr = baos.toByteArray();
			    String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
			    final ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

			    nameValuePairs.add(new BasicNameValuePair("image",image_str));
			    nameValuePairs.add(new BasicNameValuePair("username", username));

			     Thread t = new Thread(new Runnable() {
			     
			    @Override
			    public void run() {
			          try{
			                 HttpClient httpclient = new DefaultHttpClient();
			                 HttpPost httppost = new HttpPost(App.ip+":60021/intecons/profile_pic_signup.php");
			                 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			                 HttpResponse response = httpclient.execute(httppost);
			                 the_string_response = convertResponseToString(response);
			                 
			                /////////////////////If response received positive than copy image to ChatUp folder////////////////////////////
			                if(response != null){
			                 photo = new File(imagePath);
			      			if(photo.exists()){
			      				photo.delete();
			      			}
			      			try {
			      				OutputStream photo = new FileOutputStream(imagePath);
			      				bmp.compress(CompressFormat.JPEG,100,photo);
			      			} catch (FileNotFoundException e) {
			      				// TODO Auto-generated catch block
			      				e.printStackTrace();
			      			}}
			      			////////////////////////////////////////////////////////////////////////
			             
			          }catch(final Exception e){
			                  runOnUiThread(new Runnable() {
			                     
			                    @Override
			                    public void run() {
			                        Toast.makeText(Profile_photo_activity.this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();                              
			                    }
			                });
			                   System.out.println("Error in http connection "+e.toString());
			             }  
			    }
			});
			 t.start();
			 
			}
		});

        setContentView(lv);
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        target.setType("image/*");
        Intent intent = Intent.createChooser(
                target, "File selector");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
            
                // If the file selection was successful
                if (requestCode==REQUEST_CODE && resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                    //	data.getSerializableExtra(name)
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        
                        try {
                             // Get the file path from the URI
                             path = FileUtils.getPath(this, uri);
                             Toast.makeText(Profile_photo_activity.this,
                                     "File Selected: " + path, Toast.LENGTH_LONG).show();
                             Log.i("path:",path);
                         } catch (Exception e) {
                             Log.e("FileSelectorTestActivity", "File select error", e);
                         }
                        
                        utils= new ImageLoadingUtils(this);
                        bmp = utils.decodeBitmapFromPath(path);
            			image.setImageBitmap(bmp);
            			//copying image to profile photos only if upload is clicked
            			/*photo = new File(imagePath);
            			if(photo.exists()){
            				photo.delete();
            			}
            			try {
            				OutputStream photo = new FileOutputStream(imagePath);
            				bmp.compress(CompressFormat.JPEG,100,photo);
            			} catch (FileNotFoundException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}*/
                    }
                }
                if(requestCode == RESULT_CLICK_IMAGE && resultCode == RESULT_OK){
                	//String root = Environment.getExternalStorageDirectory().toString();
                	//String imagePath = root+"/ChatUp/Profile Photos/username.jpg";
        			//
                	tempPath+=username+".jpg";
                	path=tempPath;
                	/*File photo = new File(imagePath);
        			if(photo.exists()){
        				photo.delete();
        			}*/
        			bmp = (Bitmap)data.getExtras().get("data");
        			image.setImageBitmap(bmp);
        			/*try {
        				FileOutputStream file = new FileOutputStream(path);
        				BufferedOutputStream bos = new BufferedOutputStream(file);
        				bmp.compress(CompressFormat.JPEG, 100, bos);
        				bos.flush();
        				bos.close();
        			} catch (FileNotFoundException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}*/
        		}
  
        upload.setVisibility(View.VISIBLE);
    }
    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException{

        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        final int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..  
        if (contentLength < 0){
        }
        else{
               byte[] data = new byte[512];
               int len = 0;
               try
               {
                   while (-1 != (len = inputStream.read(data)) )
                   {
                       buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                   }
               }
               catch (IOException e)
               {
                   e.printStackTrace();
               }
               try
               {
                   inputStream.close(); // closing the stream…..
               }
               catch (IOException e)
               {
                   e.printStackTrace();
               }
               res = buffer.toString();     // converting stringbuffer to string…..
               runOnUiThread(new Runnable() {
                   
              	 @Override
                      public void run() {
                      	
                          Toast.makeText(Profile_photo_activity.this, "Image Uploaded Successfully", Toast.LENGTH_LONG).show();                          
                      }
                  });
               //System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
   }
}
