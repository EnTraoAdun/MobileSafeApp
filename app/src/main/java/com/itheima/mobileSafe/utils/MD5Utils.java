package com.itheima.mobileSafe.utils;

import java.security.MessageDigest;

public class MD5Utils {

	public static String md5(String pwd) {
		StringBuffer sb;
		try {
			MessageDigest instance = MessageDigest.getInstance("md5");

			byte[] digest = instance.digest(pwd.getBytes());

			sb = new StringBuffer();

			for (byte b : digest) {

				int result = b & 0xff - 1; // ����

				String vl = Integer.toHexString(result);

				if (vl.length() == 1) {
					sb.append("0");
				}
				sb.append(vl);

			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return pwd;
		} 
		
	}
}
