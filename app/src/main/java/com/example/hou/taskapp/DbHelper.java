package com.example.hou.taskapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hou on 12/8/2016.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String TAG = "DbHelper";

    // Databse info
    private static final String DATABASE_NAME = "TaskDatabase";
    private static final int DATABASE_VERSION = 2;

    // Table name
    private static final String TABLE_TASK = "taskdetail";

    // Task table columns
    private static final String _ID = "_id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    private static DbHelper mDbHelper;

    public static synchronized DbHelper getInstance(Context context) {
        // Use the application context, which will ensure that
        // you dont accidentally leak an Activity's context.
        if (mDbHelper == null) {
            mDbHelper = new DbHelper(context.getApplicationContext());
        }

        return mDbHelper;
    }

    // Constructor should be private to prevent direct instantiation.
    // Make a call to the static method getInstance instead.
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK +
                "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT," +
                DESCRIPTION + " TEXT" +
                ")";
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

            onCreate(db);
        }

    }

    // Insert a task detail into database
    public void insertTaskDetail(TaskData taskData) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(NAME, taskData.name);
            values.put(DESCRIPTION, taskData.description);

            long id = db.insertOrThrow(TABLE_TASK, null, values);
            taskData.setID(((int) id));
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    // fetch all data from table
    public List<TaskData> getAllTask() {

        List<TaskData> taskDetail = new ArrayList<>();

        String TASK_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_TASK;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TASK_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    TaskData taskData = new TaskData();
                    taskData.name = cursor.getString(cursor.getColumnIndex(NAME));
                    taskData.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    taskData._id = cursor.getInt(cursor.getColumnIndex(_ID));
                    taskDetail.add(taskData);

                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return taskDetail;
    }

    // Delete single row from table
    public void deleteRow(String name) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM " + TABLE_TASK + " WHERE name ='" + name + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d(TAG, "Error while trying to delete task details");
        } finally {
            db.endTransaction();
        }
    }

    // update task data in db
    public void updateTask(TaskData task) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(NAME, task.name);
            values.put(DESCRIPTION, task.description);
            int number = db.update(TABLE_TASK, values, _ID + " = ?",new String[] { String.valueOf(task.getID())});
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to edit task in database");
        } finally {
            db.endTransaction();
        }
    }

}
