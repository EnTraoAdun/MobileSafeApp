package com.itheima.mobileSafe.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.R.id;
import com.itheima.mobileSafe.R.layout;
import com.itheima.mobileSafe.utils.PackageInfoutils;
import com.itheima.mobileSafe.utils.Streamutils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.ResType;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
protected static final int FOUND_UPDATE = 1;
protected static final int ERROR = 2;
private TextView tv_version;
String download;
RelativeLayout rl;
SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        tv_version=(TextView) findViewById(R.id.tv_version);
        tv_version.setText("版本号:"+PackageInfoutils.getPackageVersion(this));
        rl=(RelativeLayout) findViewById(R.id.rl);
        sp=getSharedPreferences("status", 0);
        boolean status=sp.getBoolean("status", true);
        //对勾选的状态进行判断
        if (status) {
        	 checkVersion();
		} else {
			//当没有勾选检查更新时
			new Thread(){
				public void run() {
					SystemClock.sleep(2000);
					intoHome();
					String name="address.db";
					File file=new File(getFilesDir(),name);
					if (file.exists()&&file.length()>0) {
						return;
					}
					try {
						InputStream is=getAssets().open(name);
						OutputStream os=new FileOutputStream(file);
						byte []bys=new byte[1024];
						int len=-1;
						while((len=is.read(bys))>0){
							os.write(bys,0 , len);
						}
						is.close();
						os.close();
						Log.i("viiv", "复制完成");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			
					
				};
			}.start();
		}
        AlphaAnimation aa=new AlphaAnimation(0.3f, 1f);
        aa.setDuration(3000);
        rl.startAnimation(aa);
    }
  private  Handler handler=new Handler(){
    	public void handleMessage(Message msg) {
    		super.handleMessage(msg);
    		switch (msg.what) {
			case FOUND_UPDATE:
			String description=	(String) msg.obj;
			showDialo(description);
				break;
			case ERROR:
				String message=	(String) msg.obj;
				Toast.makeText(SplashActivity.this, "出现"+message+"错误", 0).show();
				intoHome();
				break;
			}
    		
    	}
    };
	private void checkVersion() {
		new Thread(){
			public void run() {
				Message ms=Message.obtain();
				
				try {
					URL url=new URL("http://188.188.3.54:8080/a.txt");
					
						HttpURLConnection conn=(HttpURLConnection) url.openConnection();
						conn.setConnectTimeout(2000);
						int code=conn.getResponseCode();
						if(code==200){
						InputStream is = conn.getInputStream();
						String string = Streamutils.getString(is);
							Log.i("vivi", string);
							JSONObject object = new JSONObject(string);
							String version = object.getString("version");
							String description = object.getString("description");
							download = object.getString("download");
							String nowVersion=PackageInfoutils.getPackageVersion(SplashActivity.this);
							//���а汾�ж�
							if (nowVersion.equals(version)) {
								intoHome();
							} else {
								ms.what=FOUND_UPDATE;
								ms.obj=description;
							}
						}else{
							ms.what=ERROR;
							ms.obj="405";
							
						}
						
					//��ȡ״̬��
						//�԰汾�Ž��бȶ�
						//��handler����Ϣ
				} catch (MalformedURLException e) {
					e.printStackTrace();
					ms.what=ERROR;
					ms.obj="401";
				} catch (IOException e) {
					e.printStackTrace();
					ms.what=ERROR;
					ms.obj="402";
				} catch (JSONException e) {
					e.printStackTrace();
					ms.what=ERROR;
					ms.obj="403";
				}finally{
					SystemClock.sleep(2000);
					handler.sendMessage(ms);
				}
				
			};
			
		}.start();
	}
	ProgressDialog pd;
	protected void showDialo(String description) {
		 pd=new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("是否更新");
		builder.setMessage(description);
		builder.setNegativeButton("暂不更新", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				intoHome();
			}
		});
		builder.setPositiveButton("开始下载", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				pd.show();
				HttpUtils util=new HttpUtils();
				String target="/mnt/sdcard/"+getPackageName()+".apk";
				util.download(download, target, new RequestCallBack<File>() {
					
					@Override
					public void onSuccess(ResponseInfo<File> arg0) {
						Toast.makeText(SplashActivity.this, "下载成功", 0).show();
						pd.dismiss();
						Intent intent =new Intent();
						intent.setAction("android.intent.action.VIEW");
						intent.setDataAndType(Uri.fromFile(arg0.result),"application/vnd.android.package-archive");
						startActivity(intent);
					}
					
					@Override
					public void onFailure(HttpException arg0, String arg1) {
							pd.dismiss();
							Toast.makeText(SplashActivity.this, "下载失败", 0).show();
					}
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
						pd.setMax((int) total);
						pd.setProgress((int) current);
					}
				});
			}
		});
		builder.show();
	}
	protected void intoHome() {
 Intent intent=new Intent(this, HomeActivity.class);
 startActivity(intent);
 finish();
	}


    
    
}
