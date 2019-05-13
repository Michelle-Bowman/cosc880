package database.GameDbSchema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import database.GameDbSchema.GameDbSchema.LeaderboardTable;

public class GameDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    String DB_PATH = null;
    private static final String DB_NAME = "quizDataBase";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public GameDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        //Added these three lines 4-12-19
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }

    // 4-12-19
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    // 4-12-19
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    // 4-12-19
    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // 4-12-19
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    // 4-12-19
    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    // p. 273. Good idea to specify type of column at create time, but not doing that as of p. 273
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + LeaderboardTable.NAME + "(" +
                "_id integer primary key autoincrement, " +                     // This is automatically generated for you as a unique row ID.
                LeaderboardTable.Columns.ID + ", " +
                LeaderboardTable.Columns.MODE + ", " +
                LeaderboardTable.Columns.SCORE + ", " +
                LeaderboardTable.Columns.DATE + ", " +
                LeaderboardTable.Columns.TOTALCORRECT + ", " +
                LeaderboardTable.Columns.TOTALQUESTIONS +
                ")"
        );

        /*  Commenting this out on 4-12-19. I'm now creating the DB from a file instead.
        db.execSQL("create table " + GameDbSchema.QuestionTable.NAME + "(" +
                "_id integer primary key autoincrement, " +                     // This is automatically generated for you as a unique row ID.
                QuestionTable.Columns.ID + ", " +
                QuestionTable.Columns.QUESTION + ", " +
                QuestionTable.Columns.ANSWERA + ", " +
                QuestionTable.Columns.ANSWERB + ", " +
                QuestionTable.Columns.ANSWERC + ", " +
                QuestionTable.Columns.ANSWERD + ", " +
                QuestionTable.Columns.CORRECTANSWER + ", " +
                QuestionTable.Columns.ISCORRECT +
                ")"
        );
        */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("questions", null, null, null, null, null, null);
    }



}