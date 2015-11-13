package com.itheima.mobileSafe.activities;

import com.itheima.mobileSafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Setting1Activity extends BaseSettingActivity {
	TextView set1_next;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_set1);
	set1_next=(TextView) findViewById(R.id.set1_next);
	set1_next.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			next();
		}
	});
}
@Override
protected void back() {

}
@Override
protected void next() {
	Intent intent=new Intent(Setting1Activity.this, Setting2Activity.class);
	startActivity(intent);
	overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
	finish();
}
}
