package com.template.localreminder.notification;

import com.template.localreminder.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class NotifyService extends Service {
	
	public class ServiceBinder extends Binder {
		NotifyService getService() {
			return NotifyService.this;
		}
	}
	
	//id to identify the notification
	private static final int NOTIFICATION = 123;
	//Name of an intent extra for identifying if this service was started
	public static final String INTENT_NOTIFY = "com.template.localreminder.INTENT_NOTIFY";
	//System notification manager
	private NotificationManager manager;
	
	@Override
	public void onCreate () {
		Log.i("Notify Service", "onCreate()");
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("Local Service", "Received Start Id " + startId + ": " + intent);
		// if the service was started by AlarmTask intent show notification
		if (intent.getBooleanExtra(INTENT_NOTIFY, false)) {
			showNotification();
		}
		return START_NOT_STICKY;
	}
	
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	private final IBinder binder = new ServiceBinder();
	
	public void showNotification () {
		// title of notification
		CharSequence title = "Alarm!!";
		// icon to display
		int icon = R.drawable.ic_launcher;
		// scrolling text of notification
		CharSequence text = "Your notification time is upon us.";
		// time to show the notification
		long time = System.currentTimeMillis();
		
		Notification notification = new Notification(icon, text, time);
		
		// the pending intent to launch the activity
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, NotifyActivity.class), 0);
		
		// set the info for the views
		notification.setLatestEventInfo(this, title, text, contentIntent);
		
		// clear the notification when it is pressed
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		// send notification to system
		manager.notify(NOTIFICATION, notification);
		
		// stop the service when finished
		stopSelf();
	}
	
	
}
