package com.michellebowman.android.praxisquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import database.GameDbSchema.GameDbHelper;
import database.GameDbSchema.GameDbSchema;
import database.GameDbSchema.GameDbSchema.LeaderboardTable;

public class Leaderboard  {

    private static Leaderboard sLeaderboard = null;

    // For DB
    private Context mContext;
    private SQLiteDatabase mDatabase;

    // All scores
    private ArrayList<Score> mGameScores = new ArrayList();
    private int mNumQuizScores = 0;
    private int mNumTestScores = 0;
    private int mTotalGameScores = 0;
    private int mAvgTestScore = 0;

    // Badges
    private boolean mBronzeBadge = false;
    private boolean mSilverBadge = false;
    private boolean mGoldBadge = false;

    // Singleton p. 159
    public static Leaderboard getInstance(Context context) {
        if (sLeaderboard == null) {
            sLeaderboard = new Leaderboard(context);
        }
        return sLeaderboard;
    }

    private Leaderboard(Context context) {
        mContext = context.getApplicationContext();
        //Toast.makeText(mContext, "Leaderboard(context) Constructor called", Toast.LENGTH_SHORT).show();
        mDatabase = new GameDbHelper(mContext).getWritableDatabase(); // Calls either onCreate(...) or onUpgrade(...) in GameDbHelper
    }

    // Custom Methods

    public void addTestScore(Score score) {
        mGameScores.add(score);
        mTotalGameScores++;

        if (score.getMode().equals("Quiz")) {
            mNumQuizScores++;
        }
        else {
            mNumTestScores++;
        }

    }



    public int getLastScore() {
        ArrayList<Score> scores = getAllScoresFromDB();

        int lastScoreIndex = (scores.size()) - 1;

        int lastScore = scores.get(lastScoreIndex).getGameScore();

        return lastScore;
    }

    public void checkBadges(){

        ArrayList<Score> scores = getAllScoresFromDB();

        if (checkBronzeBadge(scores)) {
            mBronzeBadge = true;
        }

        if (checkSilverBadge(scores)) {
            mSilverBadge = true;
        }

        if (checkGoldBadge(scores)) {
            mGoldBadge = true;
        }

    }

    public boolean checkBronzeBadge(ArrayList<Score> scores) {
        boolean flag = false;
        int numQuiz = 0;

        for(int i = 0; i < scores.size(); i++ ) {
            Score score = scores.get(i);

            if (score.getMode().equals("Quiz")){
                numQuiz++;
            }

        }

        if (numQuiz >= 5) {
            flag = true;
        }

        return flag;
    }

    public boolean checkSilverBadge(ArrayList<Score> scores){

        boolean flag = false;

        for(int i = 0; i < scores.size(); i++ ) {
            Score score = scores.get(i);

            if (score.getMode().equals("Test") && score.getGameScore() == 100){
                flag = true;
            }

        }

        return flag;
    }

    public boolean checkGoldBadge(ArrayList<Score> scores){
        boolean flag = false;
        int numTest = 0;
        int totalPercent = 0;

        for(int i = 0; i < scores.size(); i++ ) {
            Score score = scores.get(i);

            if (score.getMode().equals("Test")) {
                int percent = score.getGameScore();
                totalPercent = totalPercent + percent;
                numTest++;
            }
        }

        int mAvgTestScore = 0;

        if (numTest == 0) {
            flag = false;
        }
        else {
            mAvgTestScore = totalPercent / numTest;
        }


        //Toast.makeText(mContext, "Average test score : " + mAvgTestScore, Toast.LENGTH_SHORT).show();

        if ((numTest >= 5) && mAvgTestScore >= 90) {
            flag = true;
        }

        return flag;
    }


    // Database Methods

    // p. 278. Where is the best place for this method to go?
    private static ContentValues getContentValues(Score score){
        ContentValues values = new ContentValues();

        values.put(LeaderboardTable.Columns.ID, score.getId());
        values.put(LeaderboardTable.Columns.MODE, score.getMode());
        values.put(LeaderboardTable.Columns.SCORE, score.getGameScore());
        values.put(LeaderboardTable.Columns.DATE, score.getDate());
        values.put(LeaderboardTable.Columns.TOTALCORRECT, score.getTotalCorrect());
        values.put(LeaderboardTable.Columns.TOTALQUESTIONS, score.getTotalQuestions());

        return values;
    }

    public void addScoreToDB(Score score) {
        ContentValues values = getContentValues(score);
        mDatabase.insert(GameDbSchema.LeaderboardTable.NAME, null, values);
    }

    // Returns all scores
    private Cursor queryScores(String whereClause, String [] whereArgs) {
        Cursor cursor = mDatabase.query(
                LeaderboardTable.NAME,
                null,       // null selects all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new DBCursorWrapper(cursor);
    }

    public ArrayList<Score> getAllScoresFromDB(){
        ArrayList<Score> scores = new ArrayList<>();

        DBCursorWrapper cursor = (DBCursorWrapper) queryScores(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                scores.add(cursor.getScore());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return scores;
    }

    public void clearAllScoresFromDB(String whereClause, String [] whereArgs){
        mDatabase.delete(LeaderboardTable.NAME, whereClause, whereArgs);
    }

    public int getTotalNumScores() {
        return mTotalGameScores;
    }


    // Getters & Setters

    public ArrayList<Score> getGameScores() {
        return mGameScores;
    }

    public void setGameScores(ArrayList<Score> gameScores) {
        mGameScores = gameScores;
    }

    public int getNumQuizScores() {
        return mNumQuizScores;
    }

    public void setNumQuizScores(int numQuizScores) {
        mNumQuizScores = numQuizScores;
    }

    public int getNumTestScores() {
        return mNumTestScores;
    }

    public void setNumTestScores(int numTestScores) {
        mNumTestScores = numTestScores;
    }

    public int getAvgTestScore() {
        int numTest = 0;
        int totalPercent = 0;
        ArrayList<Score> scores = getAllScoresFromDB();

        for (int i = 0; i < scores.size(); i++) {
            Score score = scores.get(i);

            if (score.getMode().equals("Test")) {
                int percent = score.getGameScore();
                totalPercent = totalPercent + percent;
                numTest++;
            }
        }

        int mAvgTestScore = 0;

        if (numTest == 0) {
            // mAvgTestScore stays 0
        } else {
            mAvgTestScore = totalPercent / numTest;

        }
        return mAvgTestScore;
    }

    public void setAvgTestScore(int avgTestScore) {
        mAvgTestScore = avgTestScore;
    }

    public boolean isBronzeBadge() {
        return mBronzeBadge;
    }

    public void setBronzeBadge(boolean bronzeBadge) {
        mBronzeBadge = bronzeBadge;
    }

    public boolean isSilverBadge() {
        return mSilverBadge;
    }

    public void setSilverBadge(boolean silverBadge) {
        mSilverBadge = silverBadge;
    }

    public boolean isGoldBadge() {
        return mGoldBadge;
    }

    public void setGoldBadge(boolean goldBadge) {
        mGoldBadge = goldBadge;
    }
}




