package com.itheima.mobileSafe.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.domain.AppInfo;
import com.itheima.mobileSafe.engine.AppInfoProvider;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class AppManagerActivity extends Activity implements OnClickListener {
	List<AppInfo> list;
	List<AppInfo> user;
	List<AppInfo> system;
	AppInfo check;
	LinearLayout app_ll;
	ListView app_lv;
	TextView app_ram, app_sd, tv;
	PopupWindow pop;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appmanager);
		app_ram = (TextView) findViewById(R.id.app_ram);
		app_sd = (TextView) findViewById(R.id.app_sd);
		app_ll = (LinearLayout) findViewById(R.id.app_ll);
		app_lv = (ListView) findViewById(R.id.app_lv);
		long freeSpaceRam = Environment.getDataDirectory().getFreeSpace();
		long freeSpaceSd = Environment.getExternalStorageDirectory()
				.getFreeSpace();
		app_ram.setText("内存可用:" + Formatter.formatFileSize(this, freeSpaceRam));
		app_sd.setText("sd卡可用:" + Formatter.formatFileSize(this, freeSpaceSd));
		tv = (TextView) findViewById(R.id.app_tv);
		fillData();

	}
/**
 * 点击条目生成pop窗口
 */
	private void setOnLitemonclic() {
		app_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object o = app_lv.getItemAtPosition(position);
				if (o != null) {
					check = (AppInfo) o;
					if (pop != null) {
						pop.dismiss();
						pop = null;
					}
					View v = View.inflate(AppManagerActivity.this,
							R.layout.activity_pop, null);
					v.findViewById(R.id.uninstall).setOnClickListener(
							AppManagerActivity.this);
					v.findViewById(R.id.start).setOnClickListener(
							AppManagerActivity.this);
					v.findViewById(R.id.share).setOnClickListener(
							AppManagerActivity.this);
					v.findViewById(R.id.message).setOnClickListener(
							AppManagerActivity.this);
					int[] location = new int[2];
					pop = new PopupWindow(v, -2, -2);
					view.getLocationInWindow(location);
					pop.showAtLocation(parent, Gravity.LEFT + Gravity.TOP, 55,
							location[1]);
				}
			}

		});
	}

	private void setOnScrollListener() {
		app_lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (pop != null) {
					pop.dismiss();
					pop = null;
				}
//让应用程序个数的信息一直在屏幕上
				if (firstVisibleItem >= user.size() + 1) {
					tv.setText("系统应用 : " + system.size() + " 个 ");
				} else {
					tv.setText("用户 应用 : " + user.size() + " 个 ");

				}
			}
		});
	}

	private void fillData() {
		new Thread() {
			public void run() {
				list = AppInfoProvider.getAppInfo(getApplicationContext());
				SystemClock.sleep(3000);
				user = new ArrayList<AppInfo>();
				system = new ArrayList<AppInfo>();
				for (AppInfo info : list) {
					if (info.isUserApp()) {
						user.add(info);
					} else {
						system.add(info);
					}
				}
				Log.i("vivi", list.toString());
				runOnUiThread(new Runnable() {

					public void run() {
						loadUi();
						setOnScrollListener();
						setOnLitemonclic();
					}

				});
			};

		}.start();
	}

	private void loadUi() {
		app_ll.setVisibility(View.GONE);
		adapter = new Myadapter();
		app_lv.setAdapter(adapter);
	}

	static class viewHolder {
		ImageView app_image, app_image_type;
		TextView app_name, app_packagename, app_size;

	}

	Myadapter adapter;

	class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size() + 2;
		}
/**
 * 点击条目获得返回的对象
 */
		public Object getItem(int position) {
			AppInfo info;

			if (position == 0) {

				return null;
			} else if (position > 0 && position <= user.size()) {
				info = user.get(position - 1);
			} else if (position == user.size() + 1) {
				return null;

			} else {
				info = system.get(position - 1 - user.size() - 1);
			}

			return info;

		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			viewHolder holder;
			AppInfo info = null;

			if (position == 0) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("用户应用 : " + user.size() + "个");
				return tv;
			} else if (position > 0 && position <= user.size()) {
				info = user.get(position - 1);

			} else if (position == user.size() + 1) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("系统应用 : " + system.size() + "个");
				return tv;
			} else {
				info = system.get(position - user.size() - 2);

			}
			if (convertView != null && convertView.getTag() != null) {
				v = convertView;
				holder = (viewHolder) convertView.getTag();
			} else {
				v = View.inflate(getApplicationContext(), R.layout.app_item,
						null);
				holder = new viewHolder();
				holder.app_image = (ImageView) v.findViewById(R.id.app_image);
				holder.app_image_type = (ImageView) v
						.findViewById(R.id.app_image_type);
				holder.app_name = (TextView) v.findViewById(R.id.app_name);
				holder.app_packagename = (TextView) v
						.findViewById(R.id.app_packagename);
				holder.app_size = (TextView) v.findViewById(R.id.app_size);
				v.setTag(holder);
			}

			holder.app_image.setImageDrawable(info.getIcon());
			holder.app_name.setText(info.getAppName());
			holder.app_packagename.setText(info.getPackageName());
			holder.app_size.setText(Formatter.formatFileSize(
					getApplicationContext(), info.getSize()));
			if (info.isInRom()) {
				holder.app_image_type.setImageResource(R.drawable.memory);
			}

			return v;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.uninstall:
			uninstall();
			break;
		case R.id.start:
			start();
			break;
		case R.id.message:
			message();
			break;
		case R.id.share:
			share();
			break;

		}
	}

	private void share() {
		if (pop != null) {
			pop.dismiss();
			pop = null;
		}
		Intent intent = new Intent();
		intent.setAction("android.intent.action.SEND");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT,
				"亲, 推荐给你一款很好的 应用 , 叫做 " + check.getAppName());
		startActivity(intent);
	}

	private void message() {
		if (pop != null) {
			pop.dismiss();
			pop = null;
		}
		Intent intent = new Intent();
		intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		intent.setData(Uri.parse("package:" + check.getPackageName()));
		startActivity(intent);
	}

	private void start() {
		if (pop != null) {
			pop.dismiss();
			pop = null;
		}
		String packageName = check.getPackageName();
		Intent intent = getPackageManager().getLaunchIntentForPackage(
				packageName);
		if (intent == null) {
			Toast.makeText(getApplicationContext(), "无法启动", 0).show();
		}
		startActivity(intent);
	}

	private void uninstall() {
		if (pop != null) {
			pop.dismiss();
			pop = null;
		}
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setAction("android.intent.action.DELETE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:" + check.getPackageName()));
		startActivity(intent);

		// 注册一个广播接收者接受 删除的动作
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.PACKAGE_REMOVED");
		filter.addDataScheme("package");

		receiver = new Myreceiver();
		registerReceiver(receiver, filter);

	}

	Myreceiver receiver;

	class Myreceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String replace = intent.getData().toString()
					.replace("package:", "");
			Iterator<AppInfo> it = user.iterator();
			while (it.hasNext()) {
				AppInfo in = it.next();
				
				if (in.getPackageName().equals(replace)) {
					it.remove();
				}
			}

			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onBackPressed() {
		if (pop != null) {
			pop.dismiss();
			pop = null;
		}
		super.onBackPressed();

	}

}
