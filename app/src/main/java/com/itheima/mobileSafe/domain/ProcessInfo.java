package com.itheima.mobileSafe.domain;

import android.graphics.drawable.Drawable;

public class ProcessInfo {
	private Drawable icon;
	private String appName;
	private String packageName;
	private long size;
	private boolean selected; 
	private boolean userProcess;
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isUserProcess() {
		return userProcess;
	}
	public void setUserProcess(boolean userProcess) {
		this.userProcess = userProcess;
	}
	@Override
	public String toString() {
		return "ProcessInfo [icon=" + icon + ", appName=" + appName
				+ ", packageName=" + packageName + ", size=" + size
				+ ", selected=" + selected + ", userProcess=" + userProcess
				+ "]";
	}
	
}
