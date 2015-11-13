package com.itheima.mobileSafe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.dao.NumberQuerydao;

public class LocationActivity extends Activity {
	EditText query_number;
	TextView query_tv;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.utils_query);
		query_number = (EditText) findViewById(R.id.query_number);
		query_tv = (TextView) findViewById(R.id.query_tv);
		query_number.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String number = query_number.getText().toString().trim();
				String location = NumberQuerydao.findLocation(number);
				query_tv.setText(location);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	}

	public void query(View v) {
		String number = query_number.getText().toString().trim();
		if ("".equals(number)) {
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			query_number.startAnimation(shake);
			Toast.makeText(getApplicationContext(), "号码不能为空", 0).show();
			return;
		}
		String location = NumberQuerydao.findLocation(number);
		query_tv.setText(location);

	}
}
