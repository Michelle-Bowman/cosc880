package com.michellebowman.android.praxisquiz;

import android.database.Cursor;
import android.database.CursorWrapper;

import database.GameDbSchema.GameDbSchema;
import database.GameDbSchema.GameDbSchema.LeaderboardTable;

// p. 282. Helps to look through SQL table without repeating a lot of code (DRY "Don't Repeat Yourself" principle).
// Else would need to write the String questionText = cursor.getString(cursor.getColumnIndex(QuestionTable.Columns.QUESTION)) each time you pull a question out of cursor.

public class DBCursorWrapper extends CursorWrapper {
    public DBCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Score getScore(){
        int id = getInt(getColumnIndex(LeaderboardTable.Columns.ID));
        String mode = getString(getColumnIndex(LeaderboardTable.Columns.MODE));
        int score = getInt(getColumnIndex(LeaderboardTable.Columns.SCORE));
        String date = getString(getColumnIndex(LeaderboardTable.Columns.DATE));
        int totalCorrect = getInt(getColumnIndex(LeaderboardTable.Columns.TOTALCORRECT));
        int totalQuestions = getInt(getColumnIndex(LeaderboardTable.Columns.TOTALQUESTIONS));

        Score testScore = new Score(id, mode, score, date, totalCorrect, totalQuestions);
        return testScore;
    }




    /* Reading from file. No longer need.
    // p. 282-3 This method pulls out the relevant column data.
    public Question getQuestion(){
        int id = getInt(getColumnIndex(QuestionTable.Columns.ID));
        String questionText = getString(getColumnIndex(QuestionTable.Columns.QUESTION));
        String answerA = getString(getColumnIndex(GameDbSchema.QuestionTable.Columns.ANSWERA));
        String answerB = getString(getColumnIndex(GameDbSchema.QuestionTable.Columns.ANSWERB));
        String answerC = getString(getColumnIndex(QuestionTable.Columns.ANSWERC));
        String answerD = getString(getColumnIndex(QuestionTable.Columns.ANSWERD));
        String correctAnswer = getString(getColumnIndex(QuestionTable.Columns.CORRECTANSWER));
        int isCorrect = getInt(getColumnIndex(QuestionTable.Columns.ISCORRECT));

        Question question = new Question(id, questionText, correctAnswer);  // p. 283. Will this be issue for boolean? Reading data from above but why need to create new instance?
        return question;
    }
     */




}


