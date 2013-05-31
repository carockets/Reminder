package com.template.localreminder;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// The Database Class stores only Information ABOUT the database.
// Database functionality is implemented in the DatabaseAdapter!

public class Database extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "stuff";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_ENTRIES = "entries";
	public static final String KEY_ID = "id";
	public static final String KEY_ITEM = "item";
	
	public List<ReminderEntry> list = new ArrayList<ReminderEntry>();
	
	public Database(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db){
		String query = "CREATE TABLE " + TABLE_ENTRIES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_ITEM + " TEXT"
				+ ")";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
