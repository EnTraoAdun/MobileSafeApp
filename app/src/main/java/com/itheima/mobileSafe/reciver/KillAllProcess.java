package com.itheima.mobileSafe.reciver;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class KillAllProcess extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ActivityManager am=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			am.killBackgroundProcesses(runningAppProcessInfo.processName);
		}
		Toast.makeText(context, "清理了"+runningAppProcesses.size()+"个进程", 0).show();
	}

}
