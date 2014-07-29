package com.example.chatup_demo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class Shu_FriendlistAdapter extends BaseAdapter{

	Context context;
	ArrayList<String> hm1;
	int pos;
	
	static class ViewHolder{
		public TextView text;
		public ImageView image;
	}
	
	//ArrayList
	public Shu_FriendlistAdapter(Context context)
	{
		this.context=context;
		this.hm1=App.friendname;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i("lengtj in shufriend",""+hm1.size());
		return hm1.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View listview, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listview = inflater.inflate(R.layout.friendlistitem,parent,false);
		ViewHolder viewholder = new ViewHolder();
		viewholder.text = (TextView)listview.findViewById(R.id.username);
		viewholder.image = (ImageView) listview.findViewById(R.id.ImageView);
		listview.setTag(viewholder);
		ViewHolder holder = (ViewHolder)listview.getTag();
		holder.text.setText(hm1.get(position));
		//String picture = hm1.get(position).get("vcard");
		String picture ="null";
		
		if(picture.equals("null"))
		{///storage/emulated/0/Chatup/image_95.png

			holder.image.setImageResource(R.drawable.ic_action_person);
			String path="/storage/emulated/0/Chatup/Profile Photos/" +hm1.get(position)+".jpg";
			if(new File(path).exists()){
			holder.image.setImageURI(Uri.parse(path));}
		}	
		else
		{
			byte[] imageByteArray = Base64.decode(picture.getBytes(),Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
	        //image.setImageBitmap(bitmap);
			holder.image.setImageBitmap(bitmap);
		}
		
		return listview;
	}	
}