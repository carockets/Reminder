package com.template.localreminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class AddItemActivity extends Activity {
	
	// we get the adapter from the MainActivity
	ArrayAdapter<ReminderEntry> adapter = MainActivity.adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_additem);
		EditText text = (EditText) findViewById(R.id.edit_note);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.additem, menu);
		return true;
	}
	
	/** Action to save an entry to the database, when the "Save" Button is pressed
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
		
		
		// create an entry id (just for know done with Math.Random )
		int entryId = (int) Math.random() * 10000;
		// create the ReminderEntry object
		ReminderEntry entry = new ReminderEntry();
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
	
	public void back(View view){
		finish();
	}

}
