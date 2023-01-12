package com.example.sqlitelistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import static android.provider.BaseColumns._ID;
import static com.example.sqlitelistview.Constants.TABLE_NAME;
import static com.example.sqlitelistview.Constants.TIME;
import static com.example.sqlitelistview.Constants.TITLE;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EventsData events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton button1 = (ImageButton) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                events = new EventsData(MainActivity.this);
                try{
                    addEvent();
                    Cursor cursor = getEvents();
                    showEvents(cursor);
                }finally{
                    events.close();
                }
            }
        });
        final ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                events = new EventsData(MainActivity.this);
                try{
                    editEvent();
                    Cursor cursor = getEvents();
                    showEvents(cursor);
                }finally{
                    events.close();
                }
            }
        });
        final ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                events = new EventsData(MainActivity.this);
                try{
                    deleteEvent();
                    Cursor cursor = getEvents();
                    showEvents(cursor);
                }finally{
                    events.close();
                }
            }
        });
        final ImageButton button4 = (ImageButton) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                events = new EventsData(MainActivity.this);
                try{
                    resetAutoInc();
                    Cursor cursor = getEvents();
                    showEvents(cursor);
                }finally{
                    events.close();
                }
            }
        });
    }//end onCreate

    private void addEvent() {
        EditText et1 = (EditText) findViewById(R.id.editText);
        String string = String.format("%1$s", et1.getText());
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(TITLE, string);
        db.insert(TABLE_NAME, null, values);
    }//end addEvent

    private void editEvent() {
        EditText et1 = (EditText) findViewById(R.id.editText);
        String string = String.format("%1$s", et1.getText());
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(TITLE, string);
        //db.update(TABLE_NAME, values, "ROWID="+getLastId(), null);
        ArrayList<String> whereArgs = new ArrayList<String>();
        whereArgs.add(String.valueOf(getLastId()));
        String [] whereArgsStrArray = new String[whereArgs.size()];
        whereArgsStrArray = whereArgs.toArray(whereArgsStrArray);
        db.update(TABLE_NAME, values, "ROWID=?", whereArgsStrArray);
    }//end editEvent

    private void deleteEvent() {
        SQLiteDatabase db = events.getWritableDatabase();
        db.delete(TABLE_NAME, "ROWID="+getLastId(), null);
    }//end deleteEvent

    private void resetAutoInc() {
        SQLiteDatabase db = events.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
    }//end resetAutoInc

    private Cursor getEvents() {
        String[] FROM = {_ID, TIME, TITLE};
        String ORDER_BY = TIME + " DESC";
        SQLiteDatabase db = events.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
        return cursor;
    }//end getEvents

    private void showEvents(Cursor cursor) {
        final ListView listView = (ListView)findViewById(R.id.listView);
        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        while(cursor.moveToNext()) {
            map = new HashMap<String, String>();
            map.put("row_id", String.valueOf(cursor.getLong(0)));
            map.put("time", String.valueOf(cursor.getLong(1)));
            map.put("title", cursor.getString(2));
            MyArrList.add(map);
        }
        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter( MainActivity.this, MyArrList, R.layout.column,
                new String[] {"row_id", "time", "title"},
                new int[] {R.id.col_row_id, R.id.col_time, R.id.col_title} );
        listView.setAdapter(sAdap);
    }//end showEvents

    private long getLastId(){
        long id = 0;
        SQLiteDatabase db = events.getWritableDatabase();
        String[] FROM = {_ID};
        String ORDER_BY = TIME + " DESC";
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY, "1");
        while(cursor.moveToNext()) {
            id = cursor.getLong(0);
        }
        return id;
    }//end getLastId
}