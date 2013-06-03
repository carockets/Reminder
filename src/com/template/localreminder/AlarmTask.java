package com.template.localreminder;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmTask implements Runnable{
	private final Calendar date;
	private final AlarmManager manager;
	private final Context context;
	
	public AlarmTask(Context context, Calendar date) {
		this.context = context;
		this.date = date;
		this.manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	}
	
	public void run () {
		Intent intent = new Intent(context, NotifyService.class);
		intent.putExtra(NotifyService.INTENT_NOTIFY, true);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
		manager.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
	}
	
}
