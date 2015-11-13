package com.itheima.mobileSafe.domain;

import android.graphics.drawable.Drawable;

/**
 * 
 *    ������װ Ӧ�ó��� ��Ϣ��java bean
 *   
 * 
 *
 */
public class AppInfo {

	/**
	 *  Ӧ�ó����ͼ�� 
	 * 
	 */
	private Drawable icon;
	/**
	 * Ӧ�ó�������� 
	 * 
	 */
	private String appName;
	
	/**
	 *   Ӧ�� �����Ψһ��ʶ --- ����
	 * 
	 */
	private String packageName;
	/**
	 *   Ӧ�� ����Ĵ�С 
	 * 
	 */
	private long size;
	/**
	 *  Ӧ�õİ�װ λ�� 
	 * 
	 */
	private boolean inRom; // read only memory
	
	private boolean userApp;
	
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
	public boolean isUserApp() {
		return userApp;
	}
	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}
	public boolean isInRom() {
		return inRom;
	}
	public void setInRom(boolean inRom) {
		this.inRom = inRom;
	}
	@Override
	public String toString() {
		return "AppInfo [icon=" + icon + ", appName=" + appName
				+ ", packageName=" + packageName + ", size=" + size
				+ ", inRom=" + inRom + ", userApp=" + userApp + "]";
	}
	
	
}
