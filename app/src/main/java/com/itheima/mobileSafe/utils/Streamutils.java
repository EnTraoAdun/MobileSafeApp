package com.itheima.mobileSafe.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.ByteArrayBuffer;

public class  Streamutils {
	public static String getString(InputStream is) throws IOException{
		
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		byte [] bys=new byte[1024];
		int len=-1;
		while ((len=is.read(bys))>0) {
			bos.write(bys, 0, len);
		}
		return bos.toString();

		
	}

}
