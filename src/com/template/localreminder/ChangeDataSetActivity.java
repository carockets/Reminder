package com.template.localreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

/** This class manages all the entry changes in the database
 * including creating entries, deleting an update them
 */

public class ChangeDataSetActivity extends Activity {
	
	// we get the adapter from the MainActivity
	ArrayAdapter<ReminderEntry> adapter = MainActivity.adapter;
	private boolean isEdited = false;
	private ReminderEntry existingEntry = new ReminderEntry();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_dataset);
		EditText text = (EditText) findViewById(R.id.edit_note);
		
		//check if a new note is created or a note is edited
		Intent intent = getIntent();
		String intentMessage = intent.getStringExtra(DisplayItemActivity.MESSAGE);
		if (intentMessage != null) {
			String existingText = intent.getStringExtra(DisplayItemActivity.MESSAGE);
			existingEntry.setItem(existingText);
			text.setText(existingText);
			isEdited = true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_dataset, menu);
		return true;
	}
	
	/** Action to save an entry to the database, when the "Save" Button is pressed
	 * How the method acts depends on if the entry is created new or updated
	 * @param view
	 */
	public void saveEntry(View view){
		EditText text = (EditText) findViewById(R.id.edit_note);
		String entryText = text.getText().toString();
		
		if (entryText.isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Cannot save an empty note")
			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
			return;
		}
			
		// create a database adapter
		DatabaseAdapter dba = new DatabaseAdapter(this);
		// retrieve the text from the text field
		ReminderEntry entry = new ReminderEntry();
		
		if (isEdited) {
			long entryId = dba.getId(existingEntry.getItem());
			entry.setId(entryId);
			entry.setItem(entryText);
			dba.updateEntry(entry);
			adapter.notifyDataSetChanged();
			Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
		}
		else {
		// create an entry id (just for know done with Math.Random )
		int entryId = (int) Math.random() * 10000;
		// create the ReminderEntry object
		
		entry.setItem(entryText);
		entry.setId(entryId);
		// add the entry to the database
		dba.addEntry(entry);
		// AND add the entry to the adapter for a dynamic view
		adapter.add(entry);
		// notify about the adapter changes, load the list dynamic
		adapter.notifyDataSetChanged();
		// go back to previous view
		finish();
		}
	}
	
	/** Go back to previous view
	 * @param view
	 */
	public void back(View view){
		finish();
	}

}
