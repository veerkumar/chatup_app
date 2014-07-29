package com.example.addcontacts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.chatup_demo.App;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap>{

	ImageView image;
	String username;
	String url;
	public DownloadImageTask(String username) {
		// TODO Auto-generated constructor stub
		this.username = username;
	}

	@Override
	protected Bitmap doInBackground(ImageView... arg0) {
		// TODO Auto-generated method stub
		this.image=arg0[0];
		url = (String) arg0[0].getTag();
		return getBitmap(url);
	}

	@Override
	protected void onPostExecute(Bitmap result){
		
		/*if(result==null)
			image.setImageResource(R.drawable.ic_action_person);
		else{
			image.setImageBitmap(result);*/
		if(result!=null){	
			File sdCardDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "ChatUp/Profile Photos");
			Log.i("imagepathmkkmkmkmkmkkm",""+sdCardDirectory);
			String imagename = username+".jpg";
			File image = new File(sdCardDirectory,imagename);
			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(image);
				result.compress(Bitmap.CompressFormat.JPEG, 70, outStream);
				outStream.flush();
				outStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
}
	
	private Bitmap getBitmap(String url) {
		// TODO Auto-generated method stub
		Bitmap bitmap = null;
		if(App.status){
		try {
			bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
		}
		return null;
	}

}
