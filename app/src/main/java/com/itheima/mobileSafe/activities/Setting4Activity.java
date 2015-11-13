package com.itheima.mobileSafe.activities;

import com.itheima.mobileSafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class Setting4Activity extends BaseSettingActivity {
	TextView set4_next,set4_back;
	SharedPreferences sp;
	CheckBox set4_cb;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_set4);
	set4_next=(TextView) findViewById(R.id.set4_next);
	set4_back=(TextView) findViewById(R.id.set4_back);
	set4_cb=(CheckBox) findViewById(R.id.set4_cb);
	sp=getSharedPreferences("config", 0);
	set4_cb.setChecked(sp.getBoolean("protecting", false));
	set4_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			//将 是否开启 防盗保护 状态给保存起来 
			Editor editor = sp.edit();
			editor.putBoolean("protecting", isChecked);
			editor.commit();
		}
	});
	set4_next.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			next();
		}
	});
	set4_back.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			back();
		}
	});
}
@Override
protected void back() {

	Intent intent=new Intent(Setting4Activity.this, Setting3Activity.class);
	startActivity(intent);
	overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
	finish();

	
}
@Override
protected void next() {

	sp.edit().putBoolean("configed", true).commit();
	Intent intent=new Intent(Setting4Activity.this, MainprotectActivity.class);
	startActivity(intent);
	overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
	finish();

}
}
