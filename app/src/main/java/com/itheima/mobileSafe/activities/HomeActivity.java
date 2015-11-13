package com.itheima.mobileSafe.activities;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.reciver.MyDeviceAdmin;
import com.itheima.mobileSafe.reciver.SmsReceiver;
import com.itheima.mobileSafe.utils.MD5Utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	GridView gv;
	ImageView iv_heima;
	ImageView iv_set;
	SharedPreferences sp;
	int[] iv_items = { R.drawable.sjfd, R.drawable.srlj, R.drawable.rjgj,
			R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd, R.drawable.xtjs,
			R.drawable.cygj };
	String[] tv_items_titles = { "手机防盗", "骚扰拦截", "软件管家", "进程管理", "流量统计",
			"手机杀毒", "系统加速", "常用工具" };
	String[] tv_items_descriptions = { "远程定位手机", "全面拦截骚扰", "管理您的软件", "管理正在运行",
			"流量一目了然", "病毒无法藏身", "系统快如火箭", "常用工具大全" };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		iv_heima = (ImageView) findViewById(R.id.iv_heima);
		gv = (GridView) findViewById(R.id.gv);
		sp = getSharedPreferences("config", 0);

		ObjectAnimator animator = ObjectAnimator.ofFloat(iv_heima, "rotationY",
				45, 90, 135, 180, 225, 270, 315, 360);
		animator.setDuration(3000);
		animator.setRepeatMode(ObjectAnimator.REVERSE);
		animator.setRepeatCount(ObjectAnimator.INFINITE);
		animator.start();
		fill();
		iv_set = (ImageView) findViewById(R.id.iv_set);
		Setclick();
		checkAdmin();
	}

	private void checkAdmin() {

			ComponentName who = new ComponentName(this, MyDeviceAdmin.class);
			DevicePolicyManager manager=(DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
			boolean active = manager.isAdminActive(who);
			if (!active) {
				
				Intent intent1 = new Intent(
						DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent1.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
						who);
				intent1.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
						"必须安装");
				startActivity(intent1);
			}
		} 
		

	

	// 设置点击条目事件
	private void Setclick() {
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					// 进行是否第一次进去的判断
					String pwd = sp.getString("pwd", "");
					if (TextUtils.isEmpty(pwd)) {
						// 进行设置密码
						setpassword();
					} else {
						entrypassword();
					}
					break;
				case 1:
					Intent intent=new Intent(HomeActivity.this, BlackNumberActivity.class);
					startActivity(intent);
					break;
				case 2:
					Intent intent3=new Intent(HomeActivity.this, AppManagerActivity.class);
					startActivity(intent3);

					break;
				case 3:
					Intent intent4=new Intent(HomeActivity.this, ProcessAvtivity.class);
					startActivity(intent4);

					break;
				case 4:
					Intent intent5=new Intent(HomeActivity.this, MonitoringActivity.class);
					startActivity(intent5);
					break;
				case 5:

					break;
				case 6:

					break;
				case 7:
					Intent intent2 =new Intent(getApplicationContext(), UtilsActvity.class);
					startActivity(intent2);
					break;

				}

			}
		});
	}

	protected void entrypassword() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View v = View.inflate(this, R.layout.entry_protect, null);
		final EditText entry_pwd = (EditText) v.findViewById(R.id.entry_pwd);
		Button confirm = (Button) v.findViewById(R.id.entry_confirm);
		Button cancel = (Button) v.findViewById(R.id.entry_cancel);

		confirm.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String pwd1 = entry_pwd.getText().toString().trim();
				String pwd2 = sp.getString("pwd", "");
				if (TextUtils.isEmpty(pwd1)) {

					Toast.makeText(HomeActivity.this, "不能为空", 0).show();

				} else {
					String md5 = MD5Utils.md5(pwd1);
					if (md5.equals(pwd2)) {
						Intent intent = new Intent(HomeActivity.this,
								MainprotectActivity.class);
						dia.dismiss();
						startActivity(intent);
					} else {

						Toast.makeText(HomeActivity.this, "密码错误", 0).show();
					}
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dia.dismiss();
			}
		});
		builder.setView(v);
		dia = builder.show();
	}

	AlertDialog dia;

	protected void setpassword() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View v = View.inflate(this, R.layout.activity_protect, null);
		final EditText confir_pwd = (EditText) v.findViewById(R.id.confir_pwd);
		final EditText set_pwd = (EditText) v.findViewById(R.id.set_pwd);
		Button confirm = (Button) v.findViewById(R.id.confirm);
		Button cancel = (Button) v.findViewById(R.id.cancel);

		confirm.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 获取密码要放入内部类里面
				String pwd1 = confir_pwd.getText().toString().trim();
				String pwd2 = set_pwd.getText().toString().trim();
				if (TextUtils.isEmpty(pwd1) && TextUtils.isEmpty(pwd2)) {
					Log.i("vivi", TextUtils.isEmpty(pwd1) + "");
					Log.i("vivi", TextUtils.isEmpty(pwd2) + "");

					Toast.makeText(HomeActivity.this, "不能为空", 0).show();

				} else {
					if (pwd1.equals(pwd2)) {
						String md5 = MD5Utils.md5(pwd2);
						sp.edit().putString("pwd", md5).commit();
						Toast.makeText(HomeActivity.this, "保存成功", 0).show();
						dia.dismiss();
					} else {

						Toast.makeText(HomeActivity.this, "两次输入必须一致", 0).show();
					}
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dia.dismiss();
			}
		});
		builder.setView(v);
		dia = builder.show();
	}

	GridAdapter adapter;

	private void fill() {
		adapter = new GridAdapter();
		gv.setAdapter(adapter);

	}

	class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 8;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View v;
			if (arg1 != null) {
				v = arg1;
			} else {
				v = View.inflate(getApplicationContext(), R.layout.list_item,
						null);
			}
			ImageView iv_item = (ImageView) v.findViewById(R.id.iv_item);
			TextView tv1_item = (TextView) v.findViewById(R.id.tv1_item);
			TextView tv2_item = (TextView) v.findViewById(R.id.tv2_item);
			iv_item.setImageResource(iv_items[arg0]);
			tv1_item.setText(tv_items_titles[arg0]);
			tv2_item.setText(tv_items_descriptions[arg0]);
			return v;
		}
	}

	public void set(View v) {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
	}
}
