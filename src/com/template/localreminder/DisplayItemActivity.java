package com.template.localreminder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class DisplayItemActivity extends Activity {
	
	// the message which has to be passed to the change_dataset view
	// in case one wants to edit the entry
	public static final String MESSAGE = "";
	private String entryText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_display_item);

		// Get message from intent
		Intent intent = getIntent();
		entryText = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

		// Create the text view
		//TextView textView = new TextView(this);
		TextView textView = (TextView) findViewById(R.id.EntryView);
		textView.setText(entryText);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_item, menu);
		return true;
	}
	
	/** go back to previous view
	 * @param view
	 */
	public void back(View view){
		finish();
	}
	
	/** Opens the edit mode to edit the text
	 * @param view
	 */
	public void edit_entry(View view) {
		Intent intent = new Intent(this, ChangeDataSetActivity.class);
		TextView textView = (TextView) findViewById(R.id.EntryView);
		String text = textView.getText().toString();
		intent.putExtra(MESSAGE, text);
    	startActivity(intent);
	}
	
	/** Deletes an entry from database
	 * @param view
	 */
	public void delete_entry(View view) {
		DatabaseAdapter dba = new DatabaseAdapter(this);
		ReminderEntry entryToDelete = new ReminderEntry();
		entryToDelete.setItem(entryText);
		entryToDelete.setId(dba.getId(entryText));
		dba.deleteEntry(entryToDelete);
		// return to start
		Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
	}

}
