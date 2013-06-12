package com.template.localreminder.notification;

import java.util.Calendar;

import com.template.localreminder.ChangeDataSetActivity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmTask implements Runnable{
	private final Calendar date;
	private final AlarmManager manager;
	private final Context context;
	private int alarmId;
	
	public AlarmTask(Context context, Calendar date) {
		this.context = context;
		this.date = date;
		this.manager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
	}
	
	public void run () {
		Intent intent = new Intent(context, NotifyService.class);
		intent.putExtra(NotifyService.INTENT_NOTIFY, true);
		//
		if (ChangeDataSetActivity.PendingIntentAlarmId != 0) {
			alarmId = ChangeDataSetActivity.PendingIntentAlarmId;
		} else {
			alarmId = (int) (Math.random() * 100000);
		}
		ChangeDataSetActivity.PendingIntentAlarmId = alarmId;
		PendingIntent pendingIntent = PendingIntent.getService(context, alarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		manager.set(AlarmManager.RTC, date.getTimeInMillis(), pendingIntent);
	}
	
}
