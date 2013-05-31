package com.template.localreminder;

import java.sql.SQLException;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

// inherit from ListActivity!
public class MainActivity extends ListActivity {
	
	// make the Adapter available from everywhere (probably not so good but
	// I didn't know a better implementation at this moment.
	// One has to hold the data in the database AND in an ArrayAdapter
	// (God knows why) if one wants a dynamic view
	public static ArrayAdapter<ReminderEntry> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	
		// general settings
        super.onCreate(savedInstanceState);
        // the ListView in the activity_main.xml HAS TO have the id="list"!
        setContentView(R.layout.activity_main);
        
        DatabaseAdapter dba = new DatabaseAdapter(this);
        try {
			dba.open();  // open Database connection
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        // make a list with all entries. This one is displayed at the
        // beginning
        List<ReminderEntry> entries = dba.getAllEntries();
        
        // the ArrayAdapter received this list and will display it
        adapter = 
        		new ArrayAdapter<ReminderEntry>(
        				this, 
        				android.R.layout.simple_expandable_list_item_1, 
        				entries
        				);
        
        setListAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Adds an ReminderItem to the List
     * @param view
     */
    public void add_Item(View view){
    	// create an Intent and hand it to the view
    	Intent intent = new Intent(this, AddItemActivity.class);
    	startActivity(intent);
    }
    
    /** Deletes the first ReminderItem of the list
     * @param view
     */
    public void delete_Item(View view) {
    	// create a DatabaseAdapter
    	DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
    	// it's only possible to delete an item when there is more than one
    	if (getListAdapter().getCount() > 0) {
    		// select the entry, which has to be deleted
    		ReminderEntry entry = (ReminderEntry) getListAdapter().getItem(0);
    		databaseAdapter.deleteEntry(entry); // remove Entry from Database
    		adapter.remove(entry); // and from the List (Adapter)
    	}
    	// refresh the list
    	adapter.notifyDataSetChanged();
    }
    
}
