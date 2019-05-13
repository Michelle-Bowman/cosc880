package com.michellebowman.android.praxisquiz;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

public class Score extends AppCompatActivity {

    final Leaderboard leaderboard = Leaderboard.getInstance(getApplication());

    private int mId;
    private String mMode;
    private int mGameScore;
    private String mDate;
    private int mTotalCorrect;
    private int mTotalQuestions;
    private int mTotalNumScores = 0;

    public Score () {
        mId = setNextId();
    }

    public Score (String mode, String date, int totalCorrect, int totalQuestions){
        mMode = mode;
        mGameScore = calculateScore(totalQuestions, totalCorrect);
        mDate = date;
        mTotalCorrect = totalCorrect;
        mTotalQuestions = totalQuestions;

        mTotalNumScores++;
        mId = setNextId();
    }

    public Score (int id, String mode, int score, String date, int totalCorrect, int totalQuestions) {
        mId = id;
        mMode = mode;
        mGameScore = score;
        mDate = date;
        mTotalCorrect = totalCorrect;
        mTotalQuestions = totalQuestions;
    }

    public int calculateScore (int totalQuestions, int totalCorrect) {
        mTotalQuestions = totalQuestions;
        mTotalCorrect = totalCorrect;

        mGameScore = (totalCorrect * 100) / totalQuestions;

        return mGameScore;
    }

    public int setNextId() {
        ArrayList<Score> mAllScores = leaderboard.getAllScoresFromDB();
        int totalScores = mAllScores.size();

        totalScores++;

        return totalScores;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getMode() {
        return mMode;
    }

    public void setMode(String mode) {
        mMode = mode;
    }

    public int getGameScore() {
        return mGameScore;
    }

    public void setGameScore(int gameScore) {
        mGameScore = gameScore;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public int getTotalCorrect() {
        return mTotalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        mTotalCorrect = totalCorrect;
    }

    public int getTotalQuestions() {
        return mTotalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        mTotalQuestions = totalQuestions;
    }
}
