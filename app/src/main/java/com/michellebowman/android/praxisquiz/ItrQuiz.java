package com.michellebowman.android.praxisquiz;

import android.content.Context;
import android.widget.Toast;

public class ItrQuiz implements ItrIterface {

    private int currentIndex = 1;
    private Context mContext;
    private Deck deck = Deck.getInstance(mContext);

    public ItrQuiz(Context context, Deck deck, int start_loc) {
        mContext = context;
        currentIndex = start_loc;
    }

    @Override
    public boolean hasNext(){
        if ( deck.getNumberQuestions() != deck.getNumberCorrectTwice() ){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Question next() {

        // If reach end of deck, start again at beginning until all are correct.
        if (currentIndex > deck.getNumberQuestions()) {
            currentIndex = 1;
        }

        Question question = deck.getQuestion(currentIndex);

        while (question.isCorrectTwice()) {
            currentIndex++;
            question = deck.getQuestion(currentIndex);
        }

        currentIndex++;
        //Toast.makeText(mContext, "Current Quiz Index: " + currentIndex, Toast.LENGTH_SHORT).show();
        return question;
    }

    @Override
    public Question current() {
        Question question = deck.getQuestion(currentIndex - 1);     // Every time we access current question, the index has already been pushed forward
        return question;
    }

}
