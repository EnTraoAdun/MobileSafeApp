package com.itheima.mobileSafe.engine;

import java.util.ArrayList;
import java.util.List;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.domain.ProcessInfo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;

public class ProcessInfoProvider {
	public static List<ProcessInfo> getProcessInfo(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager pm = context.getPackageManager();
		List<ProcessInfo> list = new ArrayList<ProcessInfo>();
		List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
		for (RunningAppProcessInfo runInfo : infos) {
			ProcessInfo info = new ProcessInfo();
			// 获取包名
			String processName = runInfo.processName;
			info.setPackageName(processName);
			// 获取当前应用的内存大小
			int pids[] = new int[] { runInfo.pid };
			MemoryInfo info2 = am.getProcessMemoryInfo(pids)[0];
			long dirty = info2.getTotalPrivateDirty() * 1024;
			info.setSize(dirty);
			try {
				PackageInfo packageInfo = pm.getPackageInfo(processName, 0);
				String appname = packageInfo.applicationInfo.loadLabel(pm)
						.toString();
				info.setAppName(appname);
				Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
				info.setIcon(icon);
				if ((packageInfo.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM) != 0) {

					info.setUserProcess(false);
				} else {
					info.setUserProcess(true);
				}
				info.setSelected(false);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
				info.setIcon(context.getResources().getDrawable(
						R.drawable.ic_launcher));
				info.setAppName(processName);

			}
			list.add(info);
		}
		return list;
	}
}
