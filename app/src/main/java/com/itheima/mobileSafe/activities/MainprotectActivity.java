package com.itheima.mobileSafe.activities;

import com.itheima.mobileSafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainprotectActivity extends Activity {
	TextView main_tv;
	ImageView main_iv;
	SharedPreferences sp;
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_setmain);
	main_tv=(TextView) findViewById(R.id.main_tv);
	main_iv=(ImageView) findViewById(R.id.main_iv);
	sp=getSharedPreferences("config", 0);
	String number = sp.getString("number", "");
	main_tv.setText(number);
	boolean status = sp.getBoolean("protecting", false);
	if (status) {
		main_iv.setImageResource(R.drawable.lock);
	} else {
		main_iv.setImageResource(R.drawable.unlock);

	}
}
public void change(View v){
	boolean status = sp.getBoolean("protecting", false);
	if (status) {
		sp.edit().putBoolean("protecting", false).commit();
		main_iv.setImageResource(R.drawable.unlock);
	} else {
		sp.edit().putBoolean("protecting", true).commit();
		main_iv.setImageResource(R.drawable.lock);
	}
	
}
public void reset(View v){
	Intent intent=new Intent(this, Setting1Activity.class);
	startActivity(intent);
	finish();
	
}
}
