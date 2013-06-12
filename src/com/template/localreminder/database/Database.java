package com.template.localreminder.database;

import java.util.ArrayList;
import java.util.List;

import com.template.localreminder.ReminderEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// The Database Class stores only Information ABOUT the database.
// Database functionality is implemented in the DatabaseAdapter!

public class Database extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "stuff";
	private static final int DATABASE_VERSION = 2;
	public static final String TABLE_ENTRIES = "entries";
	public static final String KEY_ID = "id";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_TITLE = "title";
	
	public List<ReminderEntry> list = new ArrayList<ReminderEntry>();
	
	public Database(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db){
		String query = "CREATE TABLE " + TABLE_ENTRIES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY, "
				+ KEY_TITLE + " TEXT, "
				+ KEY_DESCRIPTION + " TEXT "
				+ ")";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRIES);
	    onCreate(db);
	}
	
}
