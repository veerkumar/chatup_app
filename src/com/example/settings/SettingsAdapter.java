package com.example.settings;

import java.util.ArrayList;
import com.example.chatup_demo.R;
import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsAdapter extends BaseAdapter {

	ArrayList<HashMap<String, String>> hm;
	Context context;
	public SettingsAdapter(Context context, ArrayList<HashMap<String, String>> hm) {
		// TODO Auto-generated constructor stub
		this.hm = hm;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hm.size();
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
	public View getView(final int position, View listview, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listview = inflater.inflate(R.layout.setting_content, parent,false); 
		TextView settingsText = (TextView)listview.findViewById(R.id.SettingsText);
		ImageView settingsImage = (ImageView)listview.findViewById(R.id.settingsImage);
		settingsText.setText(hm.get(position).get("settings"));
		//Log.i("settings",hm.get(position).get("settings"));
		//settingsImage.setImageDrawable(hm.get(position).get("image"));
		if("Profile"==hm.get(position).get("settings"))
			settingsImage.setImageResource(R.drawable.ic_action_person);
		if("Account"==hm.get(position).get("settings"))
			settingsImage.setImageResource(R.drawable.ic_action_accounts);
		if("Help"==hm.get(position).get("settings"))
			settingsImage.setImageResource(R.drawable.ic_action_help);
		listview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, hm.get(position).get("settings"), Toast.LENGTH_SHORT).show();
				if(!("Profile"==hm.get(position).get("settings")))
				{
					Intent intent = new Intent(view.getContext(),Help.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("label",hm.get(position).get("settings"));
					view.getContext().startActivity(intent);
				}
				if("Profile"==hm.get(position).get("settings")){
				Intent intent = new Intent(view.getContext(),Profile_photo_activity.class);
				view.getContext().startActivity(intent);}
			}
		});
		return listview;
	}

}
