package com.example.chatup_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

public class ImageLoader2 extends AsyncTask<String, Void, Void>{
	
	 public Bitmap[] imag;
	 public int len;
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
	}
	 @Override
	protected Void doInBackground(String... path1) {
		
		
		imag=new Bitmap[path1.length];
	//	len=path1.length;
	//	App.imag=new Bitmap[len];
	//	Log.i("length!!!!","!!!!"+len);
		
		 //  for(int i=0;i<path1.length;i++){
			   
			   if(path1[0].contains(".bmp")||path1[0].contains(".BMP")||path1[0].contains(".gif")||path1[0].contains(".GIF")||path1[0].contains(".jpeg")||path1[0].contains(".jpg")||path1[0].contains(".JPEG")||path1[0].contains(".png")||path1[0].contains(".PNG")){
			 
				   Log.i("imagepath","1"+path1[0]);
				   imag[0]= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path1[0]),80, 80);
				//Log.i("image bitmap","00"+imag[i]);  
				App.uploadimage=imag[0];
				
			   }
//			 if (path1[i].contains(".mp4")||path1[i].contains(".avi")||path1[i].contains(".3gp")||path1[i].contains(".flv")||path1[i].contains(".MP4")||path1[i].contains(".WMV")||path1[i].contains(".wmv")||path1[i].contains(".MPEG")||path1[i].contains(".mpeg")||path1[i].contains(".mov")){
//		    	   Bitmap bMap = ThumbnailUtils.createVideoThumbnail(path1[i], MediaStore.Video.Thumbnails.MICRO_KIND);
//		    	   App.imag[i]=bMap;
//		        }
		   
	// }
		   
		return null;
		// TODO Auto-generated method stub
		
	}

	void onPostExecute(){
		
			
		
		
		
	}
	
}
