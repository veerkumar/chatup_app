package com.example.addcontacts;

import java.util.ArrayList;
import  com.example.chatup_demo.*;
import java.util.HashMap;

import com.example.chatup_demo.App;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ContactListAdapter extends BaseAdapter {

	public String IP =App.ip;
	Context context;
	ArrayList<HashMap<String,String>> hm1;
	int pos;
	String username,user;
	String str;
	int j;
	String root=Environment.getExternalStorageDirectory().toString();
	ViewHolder holder;
	static class ViewHolder{
		public TextView text;
		public ImageView image;
		public ProgressBar pb;
		public Button addFriend;
	}
	
	//ArrayList
	public ContactListAdapter(Context context,ArrayList<HashMap<String, String>> list, String user,int j)
	{//user is currently logged in user;
		this.context=context;
		this.j = j;
		this.hm1=list;
		this.user = user;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
	public View getView(final int position, View listview, final ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listview = inflater.inflate(R.layout.contacts__list_item,parent,false);
		ViewHolder viewholder = new ViewHolder();
		viewholder.text = (TextView)listview.findViewById(R.id.username);
		viewholder.image = (ImageView) listview.findViewById(R.id.ImageView);
		//viewholder.addFriend = (Button)listview.findViewById(R.id.addfriend);
		listview.setTag(viewholder);
		holder = (ViewHolder)listview.getTag();
		username=hm1.get(position).get("username");
		String name = hm1.get(position).get("name");
		name=name.substring(0,1).toUpperCase()+name.substring(1);
		holder.text.setText(name);
		username=username.replace(".", "&");
		
		if(App.status && j==0){
			//j==0
			//System.out.println("got Connection");
			String url = IP+":60021/intecons/Profile%20Photos/"+username+".jpg";
			holder.image.setTag(url);
			holder.image.setId(position);
			//new DownloadImageTask(username).execute(holder.image);
			new DownloadImageTask(username).execute(holder.image);
		}
		if(j==1){
			String imagePath = root+"/ChatUp/Profile Photos/"+hm1.get(position).get("username")+".jpg";
			Bitmap bmp = BitmapFactory.decodeFile(imagePath);
			if(bmp!=null)
			holder.image.setImageBitmap(bmp);
			else
				holder.image.setImageResource(R.drawable.ic_action_person);
		}
		/*if(App.status){
			//j==0
		holder.addFriend.setOnClickListener(new AdapterView.OnClickListener(){

			@SuppressLint("NewApi")
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				DefaultHttpClient httpclient = new DefaultHttpClient();
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
				HttpGet httpget = new HttpGet("http://192.168.43.59:9090/plugins/userService/userservice?type=add_roster&secret=veer&username="+user+"&item_jid="+hm1.get(position).get("username")+"@openfire&name="+hm1.get(position).get("username")+"&subscription=3&groups=friends");
				HttpResponse response = null;
				InputStream is=null;
				String jsonString = null;
				try {
					response = httpclient.execute(httpget);
					HttpEntity httpEntity = response.getEntity();
					is = httpEntity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(
							is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					is.close();
					jsonString=sb.toString();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject jObj = null;
				try {
					jObj = XML.toJSONObject(jsonString);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(jObj);
				holder.addFriend.setVisibility(View.GONE);
				httpget = new HttpGet("http://192.168.43.59:9090/plugins/userService/userservice?type=add_roster&secret=veer&username="+hm1.get(position).get("username")+"&item_jid="+user+"@openfire&name="+user+"&subscription=3&groups=friends");
				policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
				try {
					response = httpclient.execute(httpget);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		}*/
		return listview;
	}	
}