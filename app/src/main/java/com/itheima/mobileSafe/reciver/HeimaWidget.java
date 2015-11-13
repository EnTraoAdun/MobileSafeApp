package com.itheima.mobileSafe.reciver;

import com.itheima.mobileSafe.services.MakeWidgetService;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;

public class HeimaWidget extends AppWidgetProvider {
@Override
public void onReceive(Context context, Intent intent) {
	super.onReceive(context, intent);
	
}
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Intent intent=new Intent(context,MakeWidgetService.class);
		context.startService(intent);
	}
	public void onDisabled(Context context) {
		super.onDisabled(context);
		context.stopService(new Intent(context, MakeWidgetService.class));
	}
	@Override
		public void onUpdate(Context context, AppWidgetManager appWidgetManager,
				int[] appWidgetIds) {
			super.onUpdate(context, appWidgetManager, appWidgetIds);
			Intent intent=new Intent(context,MakeWidgetService.class);
			context.startService(intent);
		}
}
