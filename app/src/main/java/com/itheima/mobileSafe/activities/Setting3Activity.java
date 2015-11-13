package com.itheima.mobileSafe.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.domain.ContactInfo;
import com.itheima.mobileSafe.utils.ContactInfoUtils;

public class Setting3Activity extends BaseSettingActivity {
	SharedPreferences sp;
	TextView set3_next,set3_back;
	EditText safenumber;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_set3);
	set3_next=(TextView) findViewById(R.id.set3_next);
	set3_back=(TextView) findViewById(R.id.set3_back);
	safenumber=(EditText) findViewById(R.id.safenumber);
	sp=getSharedPreferences("config", 0);
	String num = sp.getString("number", "");
	safenumber.setText(num);
	set3_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			back();
		}
	});
	set3_next.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			next();
		}
	});
}
 
public void select(View v){
	Intent intent=new Intent(this, SelectContact.class);
	startActivityForResult(intent, 0);
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);

	if (resultCode==0) {
		String phone = data.getStringExtra("phone");
		safenumber.setText(phone);
		
	}
}

@Override
protected void back() {

	Intent intent=new Intent(Setting3Activity.this, Setting2Activity.class);
	startActivity(intent);
	overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
	finish();

}

@Override
protected void next() {


	String number = safenumber.getText().toString().trim();
	if (TextUtils.isEmpty(number)) {
		
		Toast.makeText(Setting3Activity.this, "必须输入安全号码", 0).show();
	} else {
		sp.edit().putString("number", number).commit();
		Intent intent=new Intent(Setting3Activity.this, Setting4Activity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.next_enter, R.anim.next_exit);
		finish();
	}

}
}
