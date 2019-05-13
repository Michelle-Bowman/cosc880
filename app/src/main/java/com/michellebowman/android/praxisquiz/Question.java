package com.michellebowman.android.praxisquiz;

import java.util.UUID;

public class Question {

    private int mQuestionID;
    private String mQuestionText;
    private String mCorrectAnswer;

    private String mAnswerA;
    private String mAnswerB;
    private String mAnswerC;
    private String mAnswerD;

    private boolean mIsCorrectOnce;
    private boolean mIsCorrectTwice;


    public Question(int questionID, String questionText, String correctAnswer) {
        mQuestionID = questionID;
        mQuestionText = questionText;
        mCorrectAnswer = correctAnswer;
    }

    public Question(int questionID, String questionText, String answerA, String answerB, String answerC, String answerD,
                    String correctAnswer) {
        mQuestionID = questionID;
        mQuestionText = questionText;
        mAnswerA = answerA;
        mAnswerB = answerB;
        mAnswerC = answerC;
        mAnswerD = answerD;
        mCorrectAnswer = correctAnswer;
        mIsCorrectOnce = false;
        mIsCorrectTwice = false;
    }


    // Getters & Setters

    public int getQuestionID() {
        return mQuestionID;
    }

    // Added b/c having tricky issues with uuid.toString() methods.  Double-check it works.
    public String getQuestionIdString(){
        String id = Integer.toString(mQuestionID);
        return id;
    }


    public void setQuestionID(int questionID) {
        mQuestionID = questionID;
    }

    public String getQuestionText() {
        return mQuestionText;
    }

    public void setQuestionText(String questionText) {
        mQuestionText = questionText;
    }

    public String getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public void setCorrectAnswer(String Answer) {
        mCorrectAnswer = Answer;
    }

    public String getAnswerA() {
        return mAnswerA;
    }

    public void setAnswerA(String answerA) {
        mAnswerA = answerA;
    }

    public String getAnswerB() {
        return mAnswerB;
    }

    public void setAnswerB(String answerB) {
        mAnswerB = answerB;
    }

    public String getAnswerC() {
        return mAnswerC;
    }

    public void setAnswerC(String answerC) {
        mAnswerC = answerC;
    }

    public String getAnswerD() {
        return mAnswerD;
    }

    public void setAnswerD(String answerD) {
        mAnswerD = answerD;
    }

    public boolean isCorrectOnce() {
        return mIsCorrectOnce;
    }

    public void setCorrectOnce(boolean correctOnce) {
        mIsCorrectOnce = correctOnce;
    }

    public boolean isCorrectTwice() {
        return mIsCorrectTwice;
    }

    public void setCorrectTwice(boolean correctTwice) {
        mIsCorrectTwice = correctTwice;
    }

}
