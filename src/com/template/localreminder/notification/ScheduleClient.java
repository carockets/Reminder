package com.template.localreminder.notification;

import java.util.Calendar;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

// Service Client
public class ScheduleClient {
	
	private ScheduleService boundService = new ScheduleService(); 
	private Context context;
	private boolean isBound;
	
	public ScheduleClient(Context context) {
		this.context = context;
	}
	
	public void doBindService () {
		context.bindService(new Intent(context, ScheduleService.class), mConnection, Context.BIND_AUTO_CREATE);
		isBound = true;
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			boundService = ((ScheduleService.ServiceBinder) service).getService();
		}
		
		public void onServiceDisconnected(ComponentName className) {
			boundService = null;
		}
	};
	
	public void setAlarmForNotification(Calendar calendar) {
		boundService.setAlarm(calendar);
	}
	
	public void doUnbindService () {
		if (isBound) {
			context.unbindService(mConnection);
			isBound = false;
		}
	}
	
}
