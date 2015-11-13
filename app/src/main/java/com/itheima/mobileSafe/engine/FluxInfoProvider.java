package com.itheima.mobileSafe.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.itheima.mobileSafe.domain.FluxInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
public class FluxInfoProvider {
	
public static List<FluxInfo> getFluxInfo(Context context){
	List<FluxInfo> list=new ArrayList<FluxInfo>();
	PackageManager pm = context.getPackageManager();
	List<PackageInfo> applications = pm.getInstalledPackages(0);
	for (PackageInfo app : applications) {
		FluxInfo info = new FluxInfo();
		//获取应用图片
		Drawable icon = app.applicationInfo.loadIcon(pm);
		info.setIcon(icon);
		//获取名称
		String name = app.applicationInfo.loadLabel(pm).toString();
		//获取包名
		info.setAppName(name);
		int uid = app.applicationInfo.uid;
		long uidRxBytes = TrafficStats.getUidRxBytes(uid);
		info.setReceiver(uidRxBytes);
		long uidTxBytes = TrafficStats.getUidTxBytes(uid);
		info.setSender(uidTxBytes);
		
		if ((app.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)!=0) {
			info.setUserApp(false);
		} else {
			info.setUserApp(true);
		}
		
		list.add(info);
	}
	return list;
	}
}
