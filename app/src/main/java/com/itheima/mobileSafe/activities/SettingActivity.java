package com.itheima.mobileSafe.activities;

import java.util.List;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.services.BlackNumberService;
import com.itheima.mobileSafe.services.ShowLocationService;
import com.itheima.mobileSafe.ui.SwitchImageView;
import com.itheima.mobileSafe.utils.ServiceInfoStatusUtils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class SettingActivity extends Activity {
	RelativeLayout rl_set,rl_set2,rl_set3,rl_set4,rl_set5;
	SwitchImageView set1,set2,set3;
	SharedPreferences sp;
	EditText rl_set5_et;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.setting_item);
rl_set=(RelativeLayout) findViewById(R.id.rl_set);
rl_set2=(RelativeLayout) findViewById(R.id.rl_set2);
rl_set3=(RelativeLayout) findViewById(R.id.rl_set3);
rl_set4=(RelativeLayout) findViewById(R.id.rl_set4);
rl_set5=(RelativeLayout) findViewById(R.id.rl_set5);
rl_set5_et=(EditText) findViewById(R.id.rl_set5_et);
set1=(SwitchImageView) findViewById(R.id.set1);
set2=(SwitchImageView) findViewById(R.id.set2);
set3=(SwitchImageView) findViewById(R.id.set3);

  Log.i("vivv", "进来");
set2.setStatus(ServiceInfoStatusUtils.isServiceRunning(getApplicationContext(), "com.itheima.mobileSafe.services.BlackNumberService"));
  //是否
sp=getSharedPreferences("status", 0);
rl_set5_et.setText(sp.getString("ip", "17951"));
    set1.setStatus(sp.getBoolean("status", true));
    rl_set.setOnClickListener(new OnClickListener() {
    

    	
    	public void onClick(View v) {
			set1.changStatus();
			boolean status=set1.getStatus();
			sp.edit().putBoolean("status", status).commit();
		}
	});
  
    rl_set2.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			set2.changStatus();
			boolean status=set2.getStatus();
			if (status) {
				Intent service =new Intent(getApplicationContext(),BlackNumberService.class); 
				
				startService(service);
				return;
			}
			stopService(new Intent(getApplicationContext(),BlackNumberService.class));
		}
	});
    sp=getSharedPreferences("status", 0);
    set3.setStatus(sp.getBoolean("status3", false));
    rl_set3.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			set3.changStatus();
			boolean status=set3.getStatus();
			if (status) {
				Intent service =new Intent(getApplicationContext(),ShowLocationService.class); 
				sp.edit().putBoolean("status3", true).commit();
				startService(service);
				return;
			}
			stopService(new Intent(getApplicationContext(),ShowLocationService.class));
		}
	});
    final String[] systles_info = {"天空蓝","金属灰","清新绿","果粒橙"};
    rl_set4.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			AlertDialog.Builder builder=new AlertDialog.Builder(SettingActivity.this);
			builder.setTitle("选择背景颜色");
			builder.setSingleChoiceItems(systles_info, sp.getInt("which", 0), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					sp.edit().putInt("which", which).commit();
				}
			});
			builder.show();
		}
	});
    rl_set5.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			String trim = rl_set5_et.getText().toString().trim();
			sp.edit().putString("ip", trim).commit();
		}
	});
}
	

}
