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

public class DatabaseAdapter {

	private SQLiteDatabase database;
	private Database dbHelper;

	public DatabaseAdapter(Context context) {
		dbHelper = new Database(context);
	}

	/**
	 * Open connection to Database
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * Close Database connection
	 */
	public void close() {
		dbHelper.close();
	}

	/** Add an entry to the database (insert)
	 * @param e The entry which has to be inserted into the
	 * @return the current ReminderEntry
	 */
	public void addEntry(ReminderEntry entry) {
		// open the database connection
		try {
			open();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		ContentValues cv = new ContentValues();
		cv.put(Database.KEY_ID, entry.getId());
		cv.put(Database.KEY_TITLE, entry.getTitle());
		cv.put(Database.KEY_DESCRIPTION, entry.getDescription());
		database.insert(Database.TABLE_ENTRIES, null, cv);
		close(); // close the database connection
	}

	/**
	 * Deletes an entry from the database
	 * @param entry the entry, which has to be deleted
	 */
	public void deleteEntry(ReminderEntry entry) {
		// open the database connection
		try {
			open();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		// get the id of the entry and delete it
		long id = entry.getId();
		database.delete(Database.TABLE_ENTRIES, Database.KEY_ID + " = " + id,
				null);
		close(); // close database connection
	}
	
	/**
	 * Update a Reminder Entry in the Database
	 * @param entry the entry, which has to be updated
	 */
	public void updateEntry(ReminderEntry entry) {
		// open the database connection
		try {
			open();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		ContentValues cv = new ContentValues();
		cv.put(Database.KEY_ID, entry.getId());
		cv.put(Database.KEY_TITLE, entry.getTitle());
		cv.put(Database.KEY_DESCRIPTION, entry.getDescription());
		database.update(Database.TABLE_ENTRIES, cv, Database.KEY_ID + " = "
				+ entry.getId(), null);
		close(); // close the database connection
	}
	
	/**
	 * Gets the id of a certain entry
	 * @param entry the entry from which the id has to be looked up
	 * @return the id
	 */
	public long getId(String entryTitle) {
		String query = "SELECT " + Database.KEY_ID + " FROM "
				+ Database.TABLE_ENTRIES + " WHERE " + Database.KEY_TITLE
				+ " = '" + entryTitle + "'";
		try {
			open();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		long entryId = Long.parseLong(cursor.getString(0));
		cursor.close();
		close();
		return entryId;
	}
	
	/**
	 * Gets the Title of a certain entry
	 * @param entry the entry from which the id has to be looked up
	 * @return the id
	 */
	public String getTitle(long id) {
		String query = "SELECT " + Database.KEY_TITLE + " FROM "
				+ Database.TABLE_ENTRIES + " WHERE " + Database.KEY_ID
				+ " = " + id ;
		try {
			open();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		String entryTitle = cursor.getString(0);
		cursor.close();
		close();
		return entryTitle;
	}
	
	/**
	 * Gets the description of a certain entry
	 * @param entry the entry from which the id has to be looked up
	 * @return the id
	 */
	public String getDescription(long id) {
		String query = "SELECT " + Database.KEY_DESCRIPTION + " FROM "
				+ Database.TABLE_ENTRIES + " WHERE " + Database.KEY_ID
				+ " = " + id;
		try {
			open();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		String entryDescription = cursor.getString(0);
		cursor.close();
		close();
		return entryDescription;
	}
	
	/** Gets a reminder entry from the database
	 * @param id
	 * @return
	 */
	public ReminderEntry getEntry (long id) {
		ReminderEntry entry = new ReminderEntry();
		entry.setId(id);
		entry.setTitle(getTitle(id));
		entry.setDescription(getDescription(id));
		return entry;
	}

	/**
	 * Creates a list of all database entries
	 * @return the list
	 */
	public List<ReminderEntry> getAllEntries() {
		List<ReminderEntry> allEntries = new ArrayList<ReminderEntry>();
		// create a cursor, which can move through all entries and put them into
		// the result list
		String query = "SELECT * FROM " + Database.TABLE_ENTRIES;
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
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

	/**
	 * Creates an ReminderEntry from the current cursor position in the database
	 * @param cursor The cursor
	 * @return the ReminderEntry Object
	 */
	private ReminderEntry cursorToEntry(Cursor cursor) {
		ReminderEntry entry = new ReminderEntry();
		entry.setId(cursor.getLong(0));
		entry.setTitle(cursor.getString(1));
		entry.setDescription(cursor.getString(2));
		return entry;
	}

	public int getCount() {
		String countQuery = "SELECT * FROM " + Database.TABLE_ENTRIES;
		Cursor cursor = database.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount() + 1;
	}
}
