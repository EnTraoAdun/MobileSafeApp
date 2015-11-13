package com.itheima.mobileSafe.domain;

import android.graphics.drawable.Drawable;

/**
 * 
 *    ������װ Ӧ�ó��� ��Ϣ��java bean
 *   
 * 
 *
 */
public class FluxInfo {

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
	 * @return the icon
	 */
	public Drawable getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the receiver
	 */
	public long getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the sender
	 */
	public long getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(long sender) {
		this.sender = sender;
	}

	/**
	 * @return the userApp
	 */
	public boolean isUserApp() {
		return userApp;
	}

	/**
	 * @param userApp the userApp to set
	 */
	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}

	private long receiver;
	/**
	 *  Ӧ�õİ�װ λ�� 
	 * 
	 */
	private long sender; // read only memory
	
	private boolean userApp;
	
	
	
	
}
