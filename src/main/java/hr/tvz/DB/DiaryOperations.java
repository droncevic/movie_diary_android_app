package hr.tvz.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import hr.tvz.movieDiary.Diary;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class DiaryOperations {
    public static final String LOGTAG = "DRY_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DiaryDBHandler.COLUMN_ID,
            DiaryDBHandler.COLUMN_TYPE,
            DiaryDBHandler.COLUMN_TITLE,
            DiaryDBHandler.COLUMN_YEAR,
            DiaryDBHandler.COLUMN_GENRE,
            DiaryDBHandler.COLUMN_WRITER,
            DiaryDBHandler.COLUMN_ACTORS,
            DiaryDBHandler.COLUMN_PLOT,
            DiaryDBHandler.COLUMN_IMDBRATING,
            DiaryDBHandler.COLUMN_IMPRESSION,
            DiaryDBHandler.COLUMN_MYRATING
    };

    public DiaryOperations(Context context){
        dbhandler = new DiaryDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }
    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }
    public Diary addDiaryEntry (Diary Diary){

        ContentValues values  = new ContentValues();
        values.put(DiaryDBHandler.COLUMN_TYPE,Diary.getType());
        values.put(DiaryDBHandler.COLUMN_TITLE,Diary.getTitle());
        values.put(DiaryDBHandler.COLUMN_YEAR, Diary.getYear());
        values.put(DiaryDBHandler.COLUMN_GENRE, Diary.getGenre());
        values.put(DiaryDBHandler.COLUMN_WRITER, Diary.getWriter());
        values.put(DiaryDBHandler.COLUMN_ACTORS, Diary.getActors());
        values.put(DiaryDBHandler.COLUMN_PLOT, Diary.getPlot());
        values.put(DiaryDBHandler.COLUMN_IMDBRATING, Diary.getImdbrating());
        values.put(DiaryDBHandler.COLUMN_IMPRESSION, Diary.getImpression());
        values.put(DiaryDBHandler.COLUMN_MYRATING, Diary.getMyrating());
        long insertid = database.insert(DiaryDBHandler.TABLE_DIARY,null,values);
        Diary.setId(insertid);
        return Diary;

    }

    public Diary getDiaryEntry (long id) {

        Cursor cursor = database.query(DiaryDBHandler.TABLE_DIARY,allColumns,DiaryDBHandler.COLUMN_ID +
                "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Diary diary;

        diary = new Diary(cursor.getLong(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8), cursor.getString(9), cursor.getString(10));

        return diary;
    }

    public List<Diary> getAllDiaryEntries() {

        Cursor cursor = database.query(DiaryDBHandler.TABLE_DIARY,allColumns,null,null,
                null, null, null);

        List<Diary> diaryEntries = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Diary diary = new Diary();
                diary.setId(cursor.getLong(cursor.getColumnIndex(DiaryDBHandler.COLUMN_ID)));
                diary.setType(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_TYPE)));
                diary.setTitle(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_TITLE)));
                diary.setYear(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_YEAR)));
                diary.setGenre(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_GENRE)));
                diary.setWriter(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_WRITER)));
                diary.setActors(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_ACTORS)));
                diary.setPlot(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_PLOT)));
                diary.setImdbrating(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_IMDBRATING)));
                diary.setImpression(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_IMPRESSION)));
                diary.setMyrating(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_MYRATING)));

                diaryEntries.add(diary);
            }
        }
        return diaryEntries;
    }

    public int updateDiaryEntry (Diary diaryEntry) {

        ContentValues values = new ContentValues();
        values.put(DiaryDBHandler.COLUMN_TYPE, diaryEntry.getType());
        values.put(DiaryDBHandler.COLUMN_TITLE, diaryEntry.getTitle());
        values.put(DiaryDBHandler.COLUMN_YEAR, diaryEntry.getYear());
        values.put(DiaryDBHandler.COLUMN_GENRE, diaryEntry.getGenre());
        values.put(DiaryDBHandler.COLUMN_WRITER, diaryEntry.getWriter());
        values.put(DiaryDBHandler.COLUMN_ACTORS, diaryEntry.getActors());
        values.put(DiaryDBHandler.COLUMN_PLOT, diaryEntry.getPlot());
        values.put(DiaryDBHandler.COLUMN_IMDBRATING, diaryEntry.getImdbrating());
        values.put(DiaryDBHandler.COLUMN_IMPRESSION, diaryEntry.getImpression());
        values.put(DiaryDBHandler.COLUMN_MYRATING, diaryEntry.getMyrating());

        return database.update(DiaryDBHandler.TABLE_DIARY, values,
                DiaryDBHandler.COLUMN_ID + "=?",new String[] { String.valueOf(diaryEntry.getId())});
    }

    public void removeDiaryEntry (Diary diaryEntry) {

        database.delete(DiaryDBHandler.TABLE_DIARY, DiaryDBHandler.COLUMN_ID + "=" +
                diaryEntry.getId(), null);
    }
}
