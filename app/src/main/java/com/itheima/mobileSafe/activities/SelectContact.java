package com.itheima.mobileSafe.activities;

import java.util.List;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.domain.ContactInfo;
import com.itheima.mobileSafe.utils.ContactInfoUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class SelectContact extends Activity {
	ListView contact_item;
	List<ContactInfo> list;
	LinearLayout ll_loading;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.contact_item);
	contact_item=(ListView) findViewById(R.id.contact_item);
	ll_loading=(LinearLayout) findViewById(R.id.ll_loading);
	//新建线程获取联系人集合
	new Thread(){
		
		public void run() {
			SystemClock.sleep(3000);
			list = ContactInfoUtils.getAllContactInfos(SelectContact.this);
			//在主线程中实现ui操作
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					Log.i("vivi", list.toString());
					ll_loading.setVisibility(View.GONE);
					contact_item.setAdapter(new ArrayAdapter<ContactInfo>(SelectContact.this, android.R.layout.simple_list_item_1, list));
				}
			});
		};
		
	}.start();
    contact_item.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			ContactInfo c=list.get(position);
			String phone = c.getPhone();
			Intent intent=new Intent();
			intent.putExtra("phone", phone);
			setResult(0, intent);
			finish();
		}
	});
}
}
