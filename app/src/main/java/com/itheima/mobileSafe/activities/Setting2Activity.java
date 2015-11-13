package com.itheima.mobileSafe.activities;

import com.itheima.mobileSafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Setting2Activity extends BaseSettingActivity {
	TextView set2_next,set2_back;
	ImageView set2_bind;
	SharedPreferences sp;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_set2);
	set2_next=(TextView) findViewById(R.id.set2_next);
	set2_back=(TextView) findViewById(R.id.set2_back);
	set2_bind=(ImageView) findViewById(R.id.set2_bind);
	sp=getSharedPreferences("config", 0);
	String bind = sp.getString("bind", "");
	if (TextUtils.isEmpty(bind)) {
		set2_bind.setImageResource(R.drawable.unlock);
	} else {
		set2_bind.setImageResource(R.drawable.lock);
	}
	set2_bind.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			String bind = sp.getString("bind", "");
			if (TextUtils.isEmpty(bind)) {
				//记录sim信息
				TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				String number = manager.getSimSerialNumber();
				sp.edit().putString("bind", number).commit();
				set2_bind.setImageResource(R.drawable.lock);
			} else {
				//解除绑定
				sp.edit().putString("bind", "").commit();
				set2_bind.setImageResource(R.drawable.unlock);
			}
			
		}
	});
	set2_next.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			next();
		}
	});
	set2_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			back();
		}
	});
}
@Override
protected void back() {
	Intent intent=new Intent(Setting2Activity.this, Setting1Activity.class);
	startActivity(intent);
	overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
	finish();
}
@Override
protected void next() {

	String bind = sp.getString("bind", "");
	if (TextUtils.isEmpty(bind)) {
		Toast.makeText(Setting2Activity.this, "没绑定不能进入下一步", 0).show();
	} else {
		Intent intent=new Intent(Setting2Activity.this, Setting3Activity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
		finish();
	}

}
}
