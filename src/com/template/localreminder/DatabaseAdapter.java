// see the http://www.vogella.com/articles/AndroidSQLite/article.html tutorial for
// further information

package com.template.localreminder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DatabaseAdapter{
	
	private SQLiteDatabase database;
	private Database dbHelper;
	private String [] allEntries = {Database.KEY_ID, Database.KEY_ITEM};
	
	public DatabaseAdapter (Context context) {
		dbHelper = new Database (context);
	}
	
	/** Open connection to Database
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	/** Close Database connection
	 * 
	 */
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * @param e The entry which has to be inserted into the 
	 * @return the current ReminderEntry
	 */
	public void addEntry(ReminderEntry entry){
		//open the database connection
		try {
			open();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		ContentValues cv = new ContentValues();
		cv.put(Database.KEY_ITEM, entry.getItem());
		database.insert(Database.TABLE_ENTRIES, null, cv);
		close(); //close the database connection
	}
	
	/** Deletes an entry from the database
	 * @param entry the entry, which has to be deleted
	 */
	public void deleteEntry(ReminderEntry entry) {
		//open the database connection
		try {
			open();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		//get the id of the entry and delete it
		long id = entry.getId();
		database.delete(Database.TABLE_ENTRIES, Database.KEY_ID + " = " + id, null);
		close(); //close database connection
	}
	
	/** Creates a list of all database entries
	 * @return the list
	 */
	public List<ReminderEntry> getAllEntries() {
		List<ReminderEntry> allEntries = new ArrayList<ReminderEntry>();
		// create a cursor, which can move through all entries and put them into
		// the result list
		Cursor cursor = 
				database.query(Database.TABLE_ENTRIES, this.allEntries, null, null, null, null, null);
	
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			// creates a ReminderEntry from the current cursor position row
			ReminderEntry currentEntry = cursorToEntry(cursor);
			// add this to the result list
			allEntries.add(currentEntry);
			// move to next entry if there is one more
			cursor.moveToNext();
		}
		cursor.close();
		return allEntries;
	}
	
	/** Creates an ReminderEntry from the current cursor position in the database
	 * @param cursor The cursor
	 * @return the ReminderEntry Object
	 */
	private ReminderEntry cursorToEntry(Cursor cursor) {
		ReminderEntry entry = new ReminderEntry();
		entry.setId(cursor.getLong(0));
		entry.setItem(cursor.getString(1));
		return entry;
	}

	
	
	public int getCount() {
		String countQuery = "SELECT * FROM " + Database.TABLE_ENTRIES;
		Cursor cursor = database.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount() + 1;
	}
//
//
//	public Object getItem(int position) {
//		List<ReminderEntry> tmp = db.getEntries();
//		return tmp.get(position);
//	}
//
//
//	public long getItemId(int position) {
//		return position;
//	}
//
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		TextView tv;
//		if (convertView==null){
//			tv = new TextView(context);
//		}
//		else{
//			tv = (TextView) convertView;
//		}
//		tv.setText(((ReminderEntry)getItem(position)).toString());
//		return tv;
//	}

	

}