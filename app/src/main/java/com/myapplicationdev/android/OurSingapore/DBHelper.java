package com.myapplicationdev.android.OurSingapore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "Islands.db";
	private static final int DATABASE_VERSION = 2;
	private static final String TABLE_Island = "Island";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_name = "name";
	private static final String COLUMN_description = "description";
	private static final String COLUMN_area = "area";
	private static final String COLUMN_STARS = "stars";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// CREATE TABLE Island
		// (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,
		// description TEXT, stars INTEGER, area INTEGER );
		String createIslandTableSql = "CREATE TABLE " + TABLE_Island + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ COLUMN_name + " TEXT, "
				+ COLUMN_description + " TEXT, "
				+ COLUMN_area + " INTEGER, "
                + COLUMN_STARS + " INTEGER )";
		db.execSQL(createIslandTableSql);
		Log.i("info", createIslandTableSql + "\ncreated tables");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Island);
		onCreate(db);
	}

	public long insertIsland(String name, String description, int area, int stars) {
		// Get an instance of the database for writing
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_name, name);
		values.put(COLUMN_description, description);
        values.put(COLUMN_area, area);
		values.put(COLUMN_STARS, stars);
		// Insert the row into the TABLE_Island
		long result = db.insert(TABLE_Island, null, values);
		// Close the database connection
		db.close();
        Log.d("SQL Insert","" + result);
        return result;
	}

	public ArrayList<Island> getAllIslands() {
		ArrayList<Island> Islandslist = new ArrayList<Island>();
		String selectQuery = "SELECT " + COLUMN_ID + ","
				+ COLUMN_name + "," + COLUMN_description + ","
				+ COLUMN_area + ","
				+ COLUMN_STARS + " FROM " + TABLE_Island;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String description = cursor.getString(2);
				int area = cursor.getInt(3);
                int stars = cursor.getInt(4);

				Island newIsland = new Island(id, name, description, area, stars);
                Islandslist.add(newIsland);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return Islandslist;
	}

	public ArrayList<Island> getAllIslandsByStars(int starsFilter) {
		ArrayList<Island> Islandslist = new ArrayList<Island>();

		SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_name, COLUMN_description, COLUMN_area, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        //String selectQuery = "SELECT " + COLUMN_ID + ","
        //            + COLUMN_name + ","
        //            + COLUMN_description + ","
        //            + COLUMN_area + ","
        //            + COLUMN_STARS
        //            + " FROM " + TABLE_Island;

        Cursor cursor;
        cursor = db.query(TABLE_Island, columns, condition, args, null, null, null, null);

        // Loop through all rows and add to ArrayList
		if (cursor.moveToFirst()) {
			do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int area = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Island newIsland = new Island(id, name, description, area, stars);
                Islandslist.add(newIsland);
			} while (cursor.moveToNext());
		}
		// Close connection
		cursor.close();
		db.close();
		return Islandslist;
	}



	public int updateIsland(Island data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_name, data.getName());
        values.put(COLUMN_description, data.getDescription());
        values.put(COLUMN_area, data.getArea());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_Island, values, condition, args);
        db.close();
        return result;
    }


    public int deleteIsland(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_Island, condition, args);
        db.close();
        return result;
    }

	public ArrayList<String> getArea() {
		ArrayList<String> codes = new ArrayList<String>();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns= {COLUMN_area};

		Cursor cursor;
		cursor = db.query(true, TABLE_Island, columns, null, null, null, null, null, null);
		// Loop through all rows and add to ArrayList
		if (cursor.moveToFirst()) {
			do {
				codes.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		// Close connection
		cursor.close();
		db.close();
		return codes;
	}

	public ArrayList<Island> getAllIslandsByArea(int areaFilter) {
		ArrayList<Island> Islandslist = new ArrayList<Island>();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] columns= {COLUMN_ID, COLUMN_name, COLUMN_description, COLUMN_area, COLUMN_STARS};
		String condition = COLUMN_area + "= ?";
		String[] args = {String.valueOf(areaFilter)};

		Cursor cursor;
		cursor = db.query(TABLE_Island, columns, condition, args, null, null, null, null);

		// Loop through all rows and add to ArrayList
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String description = cursor.getString(2);
				int area = cursor.getInt(3);
				int stars = cursor.getInt(4);

				Island newIsland = new Island(id, name, description, area, stars);
				Islandslist.add(newIsland);
			} while (cursor.moveToNext());
		}
		// Close connection
		cursor.close();
		db.close();
		return Islandslist;
	}
}
