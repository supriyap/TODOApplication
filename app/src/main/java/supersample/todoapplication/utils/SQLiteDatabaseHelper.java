package supersample.todoapplication.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo_items_db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String todoQuery = SQLiteDatabaseUtils.createToDoTableQuery();
        db.execSQL(todoQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SQLiteDatabaseUtils.TABLE_TODO);
        onCreate(db);
    }

    public void insertTodoItemInDatabase(String item) {
        String insertTodoItemQuery = SQLiteDatabaseUtils.getInsertToDoItemQuery();
        SQLiteDatabase db = getWritableDatabaseOrWait();
        db.beginTransaction();

        SQLiteStatement sqLiteStatement = db.compileStatement(insertTodoItemQuery);
        sqLiteStatement.bindString(1, item);

        sqLiteStatement.execute();
        db.setTransactionSuccessful();
        db.endTransaction();
        closeDatabase(db);

    }

    public ArrayList<String> getToDoItemListFromTable() {
        ArrayList<String> todoItemString = null;
        String selectTodoItemQuery = "SELECT  * FROM " + SQLiteDatabaseUtils.TABLE_TODO;
        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabaseOrWait();
            cursor = db.rawQuery(selectTodoItemQuery, null);
            if (cursor != null && cursor.moveToFirst()) {
                todoItemString = new ArrayList<>();
                do {
                    todoItemString.add(cursor.getString(1));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeDatabase(db);
        }
        return todoItemString;
    }

    public void updateTodoItem(String updateItem, String oldItem) {
        SQLiteDatabase db = this.getReadableDatabaseOrWait();
        ContentValues values = new ContentValues();
        values.put(SQLiteDatabaseUtils.COL_TODO_ITEM, updateItem);
        int result = db.update(SQLiteDatabaseUtils.TABLE_TODO, values, SQLiteDatabaseUtils.COL_TODO_ITEM + " = ? ", new String[]{oldItem});
        Log.d(getClass().getSimpleName(), "Update Result is==>" + result);
        db.close();
    }

    private static Semaphore sem = new Semaphore(1);

    private SQLiteDatabase getReadableDatabaseOrWait() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SQLiteDatabase db = getReadableDatabase();

        return db;
    }

    private SQLiteDatabase getWritableDatabaseOrWait() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SQLiteDatabase db = getWritableDatabase();
        return db;
    }

    private void closeDatabase(SQLiteDatabase db) {
        if (db != null) {
            db.close();
        }
        sem.release();
    }


}
