package com.itheima.mobileSafe.services;

import java.util.Timer;
import java.util.TimerTask;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.reciver.HeimaWidget;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.RemoteViews;

public class MakeWidgetService extends Service {
	public IBinder onBind(Intent intent) {
		return null;
	}

	ActivityManager am;
	Timer timer;
	TimerTask task;
	AppWidgetManager awm;

	public void onCreate() {
		super.onCreate();
		am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		awm = AppWidgetManager.getInstance(getApplicationContext());
		timer = new Timer();
		task = new TimerTask() {

			@Override
			public void run() {
				Log.i("vivi", "进来");
				make();
			}
		};
		timer.schedule(task, 0, 5000);
	}

	protected void make() {
		ComponentName provider = new ComponentName(MakeWidgetService.this,
				HeimaWidget.class);
		RemoteViews views = new RemoteViews(getPackageName(),
				R.layout.process_widget);
		views.setTextViewText(R.id.process_count, "应用数:"
				+ am.getRunningAppProcesses().size() + "个");
		Intent intent = new Intent();
		intent.setAction("com.itheima.mobileSafe.Kill");
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				getApplicationContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		views.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);
		MemoryInfo info = new MemoryInfo();
		am.getMemoryInfo(info);
		views.setTextViewText(
				R.id.process_memory,
				"内存:"
						+ Formatter.formatFileSize(getApplicationContext(),
								info.availMem));
		awm.updateAppWidget(provider, views);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		task.cancel();
		task = null;
		timer.cancel();
		timer = null;
	}
}
