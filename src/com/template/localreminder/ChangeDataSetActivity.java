package com.template.localreminder;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.template.localreminder.database.DatabaseAdapter;

/**
 * This class manages all the entry changes in the database including creating
 * entries, deleting an update them
 */

public class ChangeDataSetActivity extends Activity {

	// we get the adapter from the MainActivity
	private ArrayAdapter<String> adapter = MainActivity.adapter;
	private DatabaseAdapter dba;
	private boolean isEdited = false;
	private ReminderEntry existingEntry;
	public static String isNotificationSet = "No notification set yet";
	public static TextView displayIsNotificationSet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_dataset);
		EditText titleField = (EditText) findViewById(R.id.edit_title);
		EditText descriptionField = (EditText) findViewById(R.id.edit_description);
		displayIsNotificationSet = (TextView) findViewById(R.id.set_time_heading);
		displayIsNotificationSet.setText(isNotificationSet);
	    
		// check if a new note is created or a note is edited
		Intent intent = getIntent();
		// intent message delivers the entry title
		String intentMessage = intent
				.getStringExtra(DisplayItemActivity.MESSAGE);
		if (intentMessage != null) {
			dba = new DatabaseAdapter(this);
			long entryId = dba.getId(intentMessage);
			existingEntry = dba.getEntry(entryId);

			titleField.setText(existingEntry.getTitle());
			descriptionField.setText(existingEntry.getDescription());
			isEdited = true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_dataset, menu);
		return true;
	}
	

	/**
	 * Action to save an entry to the database, when the "Save" Button is
	 * pressed How the method acts depends on if the entry is created new or
	 * updated
	 * 
	 * @param view
	 */
	public void saveEntry(View view) {
		EditText titleField = (EditText) findViewById(R.id.edit_title);
		EditText descriptionField = (EditText) findViewById(R.id.edit_description);
		String title = titleField.getText().toString();
		String description = descriptionField.getText().toString();

		if (title.isEmpty()) {
			createAlert("Cannot save note with empty title");
			return;
		}

		// create a database adapter
		dba = new DatabaseAdapter(this);
		// retrieve the text from the text field

		if (isEdited) {
			existingEntry.setTitle(title);
			existingEntry.setDescription(description);
			dba.updateEntry(existingEntry);
			adapter.notifyDataSetChanged();
			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		} else {
			// create a new reminder entry
			ReminderEntry newEntry = new ReminderEntry();
			newEntry.setTitle(title);

			long entryWithIdAlreadyExixts = dba.getId(newEntry.getTitle());
			
			if (entryWithIdAlreadyExixts != -1) {
				createAlert("An entry with this title already exists");
				return;
			} else {
				newEntry.setDescription(description);
				// create an entry id (just for now done with Math.Random )
				int entryId = (int) (Math.random() * 10000);
				newEntry.setId(entryId);
				// add the entry to the database
				dba.addEntry(newEntry);
				// AND add the entry to the adapter for a dynamic view
				adapter.add(newEntry.getTitle());
				// notify about the adapter changes, load the list dynamic
				adapter.notifyDataSetChanged();
				// go back to previous view
				finish();
			}
		}
	}
	
	// Action triggered by the button
	public void get_Location(View view) {
		Intent intent = new Intent(this, GetCurrentLocationActivity.class);
		startActivity(intent);
	}
	
	public void getGEOCoordinates(Context ctx) {
	
	}

	/**
	 * Go back to previous view
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}

	/** Creates an alert box to inform the user, when something went wrong
	 * @param message the message to display
	 */
	public void createAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
    
    public void open_set_time (View view){
    	Intent intent = new Intent(this, SetTimeActivity.class);
    	startActivity(intent);
    }
}
