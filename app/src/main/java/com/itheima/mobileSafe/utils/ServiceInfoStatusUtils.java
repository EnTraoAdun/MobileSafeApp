package com.itheima.mobileSafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceInfoStatusUtils {

	//�ж�  ĳ�� ����ķ����״̬... 
	public static boolean isServiceRunning(Context context , String serviceName) {
		
		//�õ� ���̹����� 
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		
		List<RunningServiceInfo> services = am.getRunningServices(200);
		
		for (RunningServiceInfo info : services) {
			if(info.service.getClassName().equals(serviceName)){
				//�������,  ˵��������� �������� 
				return true;
			}
		}
		
		return false;
	}

}
