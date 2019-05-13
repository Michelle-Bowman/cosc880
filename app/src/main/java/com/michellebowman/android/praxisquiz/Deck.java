package com.michellebowman.android.praxisquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import database.GameDbSchema.GameDbHelper;
import database.GameDbSchema.GameDbSchema;
import database.GameDbSchema.GameDbSchema.LeaderboardTable;

public class Deck {

    // p. 277 - Making a lot of changes / "gutting" this file

    private static Deck sDeck = null;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private ArrayList<Question> mQuestionArrayList = new ArrayList<Question>();

    private int mNumberQuestions = 0;
    private int mNumberCorrectOnce = 0;
    private int mNumberCorrectTwice = 0;
    private int mNumberQuestionsComplete = 0;

    // Singleton (p. 159-160) b/c I will only have one Deck.  User may reset the "correct" column.  The scores are pushed to & persist in Leaderboard.
    public static Deck getInstance(Context context) {
        if (sDeck == null) {
            sDeck = new Deck(context);
        }
        return sDeck;
    }

    // p. 161 + p. 272
    // 4-13-19: Commenting out 2nd line here. DB created when import file now. Do we even need the deck object?
    private Deck(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new GameDbHelper(mContext).getWritableDatabase(); // Calls either onCreate(...) or onUpgrade(...) in GameDbHelper
    }

    public void addQuestion(Question question) {
        mQuestionArrayList.add(question);
        mNumberQuestions = mNumberQuestions + 1;
    }

    public ArrayList<Question> getQuestionArrayList() {
        return mQuestionArrayList;
    }

    public Question getQuestion (int id) {
        Question question = null;

        for (int index = 1; index <= mNumberQuestions; index++) {
            if (index == id) {
                question = mQuestionArrayList.get(index - 1);       // index - 1 b/c id for Questions starts at 1, not 0.
            }
        }

        return question;
    }

    public void resetDeck() {
        Question question = null;

        for (int index = 1; index <= mNumberQuestions; index++) {
            question = mQuestionArrayList.get(index - 1);       // index - 1 b/c id for Questions starts at 1, not 0.
            question.setCorrectOnce(false);
            question.setCorrectTwice(false);
        }

        setNumberCorrectOnce(0);
        setNumberCorrectTwice(0);
        setNumberQuestionsComplete(0);

    }

    public int getTotalCorrect() {
        Question question = null;
        int totalCorrect = 0;

        for (int index = 1; index <= mNumberQuestions; index++) {
            question = mQuestionArrayList.get(index - 1);       // index - 1 b/c id for Questions starts at 1, not 0.

            if (question.isCorrectOnce()) {
                totalCorrect++;
            }

        }
        return totalCorrect;
    }


    public int getNumberQuestions() {
        return mNumberQuestions;
    }


    public ItrIterface getItrAllQuestions() {
        return new ItrTest(mContext, this, 1);
    }

    public ItrIterface getItrTwiceCorrect() {
        return new ItrQuiz(mContext, this, 1);
    }

    public int getNumberCorrectOnce() {

        for (int index = 1; index <= mNumberQuestions; index++) {
            Question question = mQuestionArrayList.get(index - 1);      // index - 1 b/c id for Questions starts at 1, not 0.
            if (question.isCorrectOnce()) {
                mNumberCorrectOnce++;
            }
        }

        return mNumberCorrectOnce;
    }

    public void setNumberCorrectOnce(int numberCorrectOnce) {
        mNumberCorrectOnce = numberCorrectOnce;
    }

    public int getNumberCorrectTwice() {
        mNumberCorrectTwice = 0;

        for (int index = 1; index <= mNumberQuestions; index++) {
            Question question = mQuestionArrayList.get(index - 1);      // index - 1 b/c id for Questions starts at 1, not 0.
            if (question.isCorrectTwice()) {
                mNumberCorrectTwice++;
            }
        }
        return mNumberCorrectTwice;
    }

    public void setNumberCorrectTwice(int numberCorrectTwice) {
        mNumberCorrectTwice = numberCorrectTwice;
    }

    public int getNumberQuestionsComplete() {
        return mNumberQuestionsComplete;
    }

    public void setNumberQuestionsComplete(int numberQuestionsComplete) {
        mNumberQuestionsComplete = numberQuestionsComplete;
    }




}