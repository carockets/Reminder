package com.template.localreminder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// inherit from ListActivity!
public class MainActivity extends ListActivity {
	
	public static final String EXTRA_MESSAGE = "com.template.localreminder.Message";
	
	// make the Adapter available from everywhere (probably not so good but
	// I didn't know a better implementation at this moment.
	// One has to hold the data in the database AND in an ArrayAdapter
	// (God knows why) if one wants a dynamic view
	public static ArrayAdapter<String> adapter;

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
        List<String> entryTitles = new ArrayList<String>();
        
        for (ReminderEntry e : entries) {
        	entryTitles.add(dba.getTitle(e.getId()));
        }
        
        // the ArrayAdapter received this list and will display it
        adapter = 
        		new ArrayAdapter<String>(
        				this, 
        				android.R.layout.simple_expandable_list_item_1, 
        				entryTitles
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
    	Intent intent = new Intent(this, ChangeDataSetActivity.class);
    	startActivity(intent);
    }
    
    // Display a selected Item
    @Override 
    public void onListItemClick(ListView l, View view, int position, long id) {
    	// Do something when a list item is clicked
    	Intent intent = new Intent(this, DisplayItemActivity.class);
    	String entryTitle = adapter.getItem(position);
    	//String title = entryToDisplay.getTitle(); // get reminder entry;
    	intent.putExtra(EXTRA_MESSAGE, entryTitle); // next activity has to query the data
    	startActivity(intent);
    }
    
}
