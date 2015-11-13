package com.itheima.mobileSafe.activities;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.R.id;
import com.itheima.mobileSafe.dao.BlackNumberDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class AddBlackNumberActivity extends Activity {
EditText add_et;
RadioGroup add_rg;
BlackNumberDao dao;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_addblacknumber);
	add_et=(EditText) findViewById(R.id.add_et);
	add_rg=(RadioGroup) findViewById(R.id.add_rg);
	dao=new BlackNumberDao(this);
}
String number;
String method;
public void save(View v){
	number = add_et.getText().toString().trim();
	 int checkedId = add_rg.getCheckedRadioButtonId();
	 switch (checkedId) {
			case R.id.add_rg_1:
				method="1";
				Log.i("vivi", "add_rg_1");
				break;
			case R.id.add_rg_2:
				method="2";
				Log.i("vivi", "add_rg_2");
				break;
			case R.id.add_rg_3:
				method="3";
				Log.i("vivi", "add_rg_3");
				break;
	 }
	 if ("".equals(number)) {
		 Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		 add_et.startAnimation(shake);
		 Toast.makeText(getApplicationContext(), "号码不能为空", 0).show();
	        return;
	}
	boolean insert = dao.insert(number, method);
	if (insert) {
		Intent data=new Intent();
		data.putExtra("number", number);
		data.putExtra("method", method);
		setResult(0, data);
		finish();
	Toast.makeText(getApplicationContext(), "保存成功", 0).show();	
	}else{
		Toast.makeText(getApplicationContext(), "保存失败", 0).show();	
		
	}
}

public void cancel(View v){
	
	finish();
}
}
