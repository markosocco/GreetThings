package ph.edu.dlsu.namiarko.greetthings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by namihihii on 11/10/2017 AD.
 */

public class DatabaseHelper extends  SQLiteOpenHelper{

    public static final String SCHEMA = "events";

    public DatabaseHelper(Context context) {
        super(context, SCHEMA, null, 4);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL = "CREATE TABLE " + Event.TABLE_NAME + " ("
                + Event.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Event.COLUMN_TITLE + " TEXT NOT NULL,"
                + Event.COLUMN_DATE + " TEXT NOT NULL,"
                + Event.COLUMN_TIME + " TEXT NOT NULL,"
                + Event.COLUMN_RECIPIENT + " TEXT NOT NULL,"
                + Event.COLUMN_MESSAGE + " TEXT NOT NULL,"
                + Event.COLUMN_REPEAT + " TEXT);";

        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " +  Event.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);

        onCreate(sqLiteDatabase);
    }


    public boolean addEvent(Event e){

        SQLiteDatabase sqldb =  getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Event.COLUMN_TITLE, e.getTitle());
        cv.put(Event.COLUMN_DATE, e.getDate());
        cv.put(Event.COLUMN_TIME, e.getTime());
        cv.put(Event.COLUMN_RECIPIENT, e.getRecipient());
        cv.put(Event.COLUMN_MESSAGE, e.getMessage());
        cv.put(Event.COLUMN_REPEAT, e.getRepeat());

        long id = sqldb.insert(Event.TABLE_NAME, "columnHack", cv);

        return (id != -1);
    }

    public Event getEvent(long id){

        SQLiteDatabase sqldb = getReadableDatabase();
        Cursor c = sqldb.query(Event.TABLE_NAME,
                null,
                Event.COLUMN_ID + " = ?",
                new String[] {String.valueOf(id)},
                null, null, null);

        Event event = null;
        if(c.moveToFirst()) {
            event = new Event();
            String title = c.getString(c.getColumnIndex(Event.COLUMN_TITLE));
            String date = c.getString(c.getColumnIndex(Event.COLUMN_DATE));
            String time = c.getString(c.getColumnIndex(Event.COLUMN_TIME));
            String recipient = c.getString(c.getColumnIndex(Event.COLUMN_RECIPIENT));
            String message = c.getString(c.getColumnIndex(Event.COLUMN_MESSAGE));
            String repeat = c.getString(c.getColumnIndex(Event.COLUMN_REPEAT));

            event.setTitle(title);
            event.setDate(date);
            event.setTime(time);
            event.setRecipient(recipient);
            event.setMessage(message);
            event.setRepeat(repeat);
            event.setId(id);
        }
        c.close();
        sqldb.close();

        return event;
    }

    public Cursor getAllEventCursor(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Event.TABLE_NAME, null, null,
                null, null, null, null);
    }

    public void editEvent(Event newEvent, long id){

        SQLiteDatabase sqldb = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Event.COLUMN_TITLE, newEvent.getTitle());
        cv.put(Event.COLUMN_DATE, newEvent.getDate());
        cv.put(Event.COLUMN_TIME, newEvent.getTime());
        cv.put(Event.COLUMN_RECIPIENT, newEvent.getRecipient());
        cv.put(Event.COLUMN_MESSAGE, newEvent.getMessage());
        cv.put(Event.COLUMN_REPEAT, newEvent.getRepeat());

        sqldb.update(Event.TABLE_NAME, cv, Event.COLUMN_ID + " = ? ", new String[]{String.valueOf(id)});
    }

    public boolean deleteEvent(long id){

        SQLiteDatabase sqldb = getWritableDatabase();
        sqldb.delete(Event.TABLE_NAME,
                Event.COLUMN_ID + " = ? ",
                new String[]{String.valueOf(id)});
        return true;
    }
}

