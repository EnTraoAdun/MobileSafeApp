package com.itheima.mobileSafe.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp=context.getSharedPreferences("config", 0);
		String sim = sp.getString("bind", "");
		boolean status = sp.getBoolean("protecting", false);
		if (status) {
	TelephonyManager tm=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	String sim1 = tm.getSimSerialNumber();
	if (sim1.equals(sim)) {
		SmsManager smsManager = SmsManager.getDefault();
		String safenumber = sp.getString("number", "5556");
		smsManager.sendTextMessage(safenumber, null, "your cellphone is good", null, null);
		
	} else {
		SmsManager smsManager = SmsManager.getDefault();
		String safenumber = sp.getString("number", "5556");
		smsManager.sendTextMessage(safenumber, null, "your cellphone may have lost", null, null);
	}
		} else {
Toast.makeText(context, "没有勾选", 0).show();
		}
		
	}

}
