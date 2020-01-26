package hr.tvz.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_DIARY = "diary";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_WRITER = "writer";
    public static final String COLUMN_ACTORS = "actors";
    public static final String COLUMN_PLOT = "plot";
    public static final String COLUMN_IMDBRATING = "imdbrating";
    public static final String COLUMN_IMPRESSION = "impression";
    public static final String COLUMN_MYRATING = "myrating";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_DIARY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TYPE + " TEXT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_YEAR + " TEXT, " +
                    COLUMN_GENRE + " TEXT, " +
                    COLUMN_WRITER + " TEXT, " +
                    COLUMN_ACTORS + " TEXT, " +
                    COLUMN_PLOT + " TEXT, " +
                    COLUMN_IMDBRATING + " TEXT, " +
                    COLUMN_IMPRESSION + " TEXT, " +
                    COLUMN_MYRATING + " TEXT " +
                    ")";


    public DiaryDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);
        db.execSQL(TABLE_CREATE);
    }
}