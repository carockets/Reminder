package com.template.localreminder;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.template.localreminder.notification.ScheduleClient;

public class SetTimeActivity extends Activity {

	// This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;
    // This is the time picker used to select the date for our notification
	private TimePicker timePicker;
	// This is the date picker used to select the date for the notification
	private DatePicker datePicker;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);
        
        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this.getApplicationContext());;
        scheduleClient.doBindService();

        // Get a reference to our date picker
        timePicker = (TimePicker) findViewById(R.id.set_time);
        timePicker.setIs24HourView(true);
        datePicker = (DatePicker) findViewById(R.id.set_date);
        datePicker.setCalendarViewShown(false);
    }
	
    /**
     * This is the onClick called from the XML to set a new notification 
     */
    public void set_time(View v){
    	// Get the date from our date picker
    	Calendar c = Calendar.getInstance();
    	int day = datePicker.getDayOfMonth();
    	int month = datePicker.getMonth();
    	int year = datePicker.getYear();
    	// Create a new calendar set to the date chosen
    	// we set the time to midnight (i.e. the first minute of that day)
    	c.set(year, month, day);
    	c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
    	c.set(Calendar.MINUTE, timePicker.getCurrentMinute());
    	c.set(Calendar.SECOND, 0);
    	// Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
    	scheduleClient.setAlarmForNotification(c);
    	ChangeDataSetActivity.displayIsNotificationSet.setText("Notification set for: \n"+ day +"/"+ (month+1) +"/"+ year);
    	finish();
    	// Notify the user what they just did
    	Toast.makeText(this, "Notification set for: "+ day +"/"+ (month+1) +"/"+ year, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onStop() {
    	// When our activity is stopped ensure we also stop the connection to the service
    	// this stops us leaking our activity into the system *bad*
    	if(scheduleClient != null)
    		scheduleClient.doUnbindService();
    	super.onStop();
    }
}
