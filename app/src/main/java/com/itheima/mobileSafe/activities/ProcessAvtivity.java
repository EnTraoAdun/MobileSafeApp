package com.itheima.mobileSafe.activities;

import java.util.ArrayList;
import java.util.List;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.R.id;
import com.itheima.mobileSafe.domain.ProcessInfo;
import com.itheima.mobileSafe.engine.ProcessInfoProvider;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProcessAvtivity extends Activity {
	int count;
	
	final static String TAG = "ProcessAvtivity";
	List<ProcessInfo> list;
	List<ProcessInfo> user;
	List<ProcessInfo> system;
	TextView process_tv1, process_tv2;
	ListView process_lv;
	LinearLayout process_ll;
	ActivityManager am;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process);
		process_tv1 = (TextView) findViewById(R.id.process_tv1);
		process_tv2 = (TextView) findViewById(R.id.process_tv2);
		process_lv = (ListView) findViewById(R.id.process_lv);
		process_ll = (LinearLayout) findViewById(R.id.process_ll);
		am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		fillData();
		
		process_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object o = process_lv.getItemAtPosition(position);
				ProcessInfo processInfo = (ProcessInfo) o;
				Log.i(TAG, processInfo.toString());
				if (processInfo.isSelected()) {
					processInfo.setSelected(false);
				} else {
					processInfo.setSelected(true);
				}
				adapter.notifyDataSetChanged();
			}
		});
	}

	private long getMemory() {
		MemoryInfo info = new MemoryInfo();
		am.getMemoryInfo(info);
		return info.availMem;

	}

	private int getProcessCount() {
		List<RunningAppProcessInfo> runningAppProcesses = am
				.getRunningAppProcesses();

		return runningAppProcesses.size();

	}

	private void fillData() {
		new Thread() {
			public void run() {

				list = ProcessInfoProvider
						.getProcessInfo(getApplicationContext());
				count = list.size();
				user = new ArrayList<ProcessInfo>();
				system = new ArrayList<ProcessInfo>();
				for (ProcessInfo info : list) {
					if (info.isUserProcess()) {
						user.add(info);
					} else {
						system.add(info);
					}
				}
				runOnUiThread(new Runnable() {
					public void run() {

						// 获取进程数
						process_tv1.setText("应用数:" + getProcessCount() + "个");
						// 获取剩余内存
						process_tv2.setText("可用内存:"
								+ Formatter.formatFileSize(
										getApplicationContext(), getMemory()));
						process_ll.setVisibility(View.GONE);
						adapter = new Myadapter();
						process_lv.setAdapter(adapter);
					}
				});
			};
		}.start();
	}

	static class ViewHolder {
		ImageView process_image;
		TextView process_name, process_packagename, process_size;
		CheckBox process_cb;
	}

	ViewHolder holder;
	Myadapter adapter;

	class Myadapter extends BaseAdapter {

		public int getCount() {
			return system.size() + user.size() + 2;
		}

		public Object getItem(int position) {
			if (position == 0 || position == user.size() + 1) {
				return null;
			} else if (position > 0 && position <= user.size()) {
				return user.get(position - 1);
			} else {
				return system.get(position - 2 - user.size());
			}
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			holder = new ViewHolder();
			ProcessInfo info;
			if (convertView != null && convertView.getTag() != null) {
				v = convertView;
				holder = (ViewHolder) convertView.getTag();
			} else {
				v = View.inflate(getApplicationContext(),
						R.layout.process_item, null);
				holder.process_cb = (CheckBox) v.findViewById(R.id.process_cb);
				holder.process_image = (ImageView) v
						.findViewById(R.id.process_image);
				holder.process_name = (TextView) v
						.findViewById(R.id.process_name);
				holder.process_packagename = (TextView) v
						.findViewById(R.id.process_packagename);
				holder.process_size = (TextView) v
						.findViewById(R.id.process_size);
				v.setTag(holder);
			}

			if (position == 0) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("用户进程 : " + user.size() + "个");
				return tv;
			} else if (position > 0 && position <= user.size()) {
				info = user.get(position - 1);
				if (getPackageName().equals(info.getPackageName())) {
					holder.process_cb.setVisibility(View.GONE);
				} else {

					holder.process_cb.setChecked(info.isSelected());
				}
				holder.process_image.setImageDrawable(info.getIcon());
				holder.process_name.setText(info.getAppName());
				holder.process_packagename.setText(info.getPackageName());
				holder.process_size.setText(Formatter.formatFileSize(
						getApplicationContext(), info.getSize()));
			} else if (position == user.size() + 1) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("系统进程 : " + system.size() + "个");
				return tv;
			} else {
				Log.i(TAG, (position - 2 - user.size() + ""));
				Log.i(TAG, user.size() + "user.size()");
				Log.i(TAG, position + "position");
				Log.i(TAG, system.toString() + "system");

				info = system.get(position - 2 - user.size());
				holder.process_cb.setChecked(info.isSelected());
				holder.process_image.setImageDrawable(info.getIcon());
				holder.process_name.setText(info.getAppName());
				holder.process_packagename.setText(info.getPackageName());
				holder.process_size.setText(Formatter.formatFileSize(
						getApplicationContext(), info.getSize()));
			}
			return v;
		}

	}

	public void button_1(View v) {
		List<ProcessInfo>killlist = new ArrayList<ProcessInfo>();
		for (ProcessInfo info : user) {
			if (getPackageName().equals(info.getPackageName())) {
				continue;
			}
			if (info.isSelected()) {
				killlist.add(info);
			}
		}
		for (ProcessInfo info : system) {
			if (info.isSelected()) {
				killlist.add(info);
			}
		}
		long total = 0;
		for (ProcessInfo processInfo : killlist) {
			total += processInfo.getSize();
			String packageName = processInfo.getPackageName();
			am.killBackgroundProcesses(packageName);
			if (processInfo.isUserProcess()) {
				user.remove(processInfo);
			} else {
				system.remove(processInfo);
			}
		}
		Toast.makeText(
				getApplicationContext(),
				"释放了"
						+ Formatter.formatFileSize(getApplicationContext(),
								total) + "清理了" + killlist.size() + "个进程", 0).show();
		process_tv1.setText("应用数:" + (count - killlist.size()) + "个");
		// 获取剩余内存
		count -= killlist.size();
		process_tv2.setText("可用内存:"
				+ Formatter.formatFileSize(getApplicationContext(), getMemory()
						+ total));
		adapter.notifyDataSetChanged();

	}

	@Override
	protected void onResume() {
		super.onResume();
		fillData();
	}

	public void button_2(View v) {
		for (ProcessInfo info : user) {
			info.setSelected(true);
		}
		for (ProcessInfo info : system) {
			info.setSelected(true);
		}
		adapter.notifyDataSetChanged();
	}

	public void button_3(View v) {
		for (ProcessInfo info : user) {
			if (info.isSelected()) {
				info.setSelected(false);
			} else {
				info.setSelected(true);
			}
		}
		for (ProcessInfo info : system) {
			if (info.isSelected()) {
				info.setSelected(false);
			} else {
				info.setSelected(true);
			}
		}
		adapter.notifyDataSetChanged();
	}
}
