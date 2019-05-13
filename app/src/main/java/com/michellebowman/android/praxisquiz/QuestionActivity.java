package com.michellebowman.android.praxisquiz;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.database.SQLException;
import java.io.IOException;

import database.GameDbSchema.GameDbHelper;

public class QuestionActivity extends SingleFragmentActivity {

    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Deck deck = Deck.getInstance(QuestionActivity.this);
        importDatabaseQuestions(deck);
    }

    @Override
    protected Fragment createFragment() {
        return new QuestionFragment();
    }

    // Import database from file
    protected void importDatabaseQuestions(Deck deck){
        GameDbHelper myDbHelper = new GameDbHelper(QuestionActivity.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        //Toast.makeText(QuestionActivity.this, "Successfully Imported", Toast.LENGTH_SHORT).show();
        c = myDbHelper.query("questions", null, null, null, null, null, null);

        // Read file, create Question objects, & store them in Singleton Deck object
        if (c.moveToFirst()) {
            do {

                int id;
                String questionText, answerA, answerB, answerC, answerD, correctAnswer;

                id = Integer.parseInt(c.getString(0));
                questionText = c.getString(1);
                answerA = c.getString(2);
                answerB = c.getString(3);
                answerC = c.getString(4);
                answerD = c.getString(5);
                correctAnswer = c.getString(6);

                // Avoiding importing twice
                if (deck.getQuestion(id) == null) {
                    Question question = new Question(id, questionText, answerA, answerB, answerC, answerD, correctAnswer);
                    deck.addQuestion(question);
                }

            } while (c.moveToNext());
        }
    }

}
