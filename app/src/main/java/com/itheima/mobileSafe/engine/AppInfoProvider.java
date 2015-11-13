package com.itheima.mobileSafe.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.itheima.mobileSafe.domain.AppInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
public class AppInfoProvider {
	
public static List<AppInfo> getAppInfo(Context context){
	List<AppInfo> list=new ArrayList<AppInfo>();
	PackageManager pm = context.getPackageManager();
	List<PackageInfo> applications = pm.getInstalledPackages(0);
	for (PackageInfo app : applications) {
		AppInfo info = new AppInfo();
		//获取应用图片
		Drawable icon = app.applicationInfo.loadIcon(pm);
		info.setIcon(icon);
		//获取名称
		String name = app.applicationInfo.loadLabel(pm).toString();
		//获取包名
		info.setAppName(name);
		String packageName = app.applicationInfo.packageName;
		//获取大小
		info.setPackageName(packageName);
		String sourceDir = app.applicationInfo.sourceDir;
		//是否是系统应用
		File file=new File(sourceDir);
		long length = file.length();
		info.setSize(length);
		if ((app.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)!=0) {
			info.setUserApp(false);
		} else {
			info.setUserApp(true);
		}
		if ((app.applicationInfo.flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)!=0) {
			info.setInRom(false);
		} else {
			info.setInRom(true);
		}
		list.add(info);
	}
	return list;
	}
}
