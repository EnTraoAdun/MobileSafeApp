package com.itheima.mobileSafe.reciver;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.activities.HomeActivity;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

	String body;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] object = (Object[]) intent.getExtras().get("pdus");
		for (Object object2 : object) {
			SmsMessage message = SmsMessage.createFromPdu((byte[]) object2);
			body = message.getMessageBody();
		}
		if ("#*alarm*#".equals(body)) {
			Log.i("vivi", body);
			MediaPlayer player = MediaPlayer.create(context, R.raw.ddd);
			player.setLooping(true);
			player.start();
			abortBroadcast();
		}
		if ("#*location*#".equals(body)) {
			Intent intent2 = new Intent(context, GPSservice.class);
			context.startService(intent2);
		}
		if ("#*wipedata*#".equals(body)) {
			DevicePolicyManager policyManager = (DevicePolicyManager) context
					.getSystemService(Context.DEVICE_POLICY_SERVICE);
				policyManager.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
				abortBroadcast();
				Log.i("vivi", "成功擦除");



		}
		if ("#*lockscreen*#".equals(body)) {
			DevicePolicyManager policyManager = (DevicePolicyManager) context
					.getSystemService(Context.DEVICE_POLICY_SERVICE);
			policyManager.resetPassword("778899", 0);
				policyManager.lockNow();
				abortBroadcast();
		}

	}

}
