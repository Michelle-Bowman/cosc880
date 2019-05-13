package com.michellebowman.android.praxisquiz;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class ItrTest implements ItrIterface {

    private int currentIndex = 1;
    private Context mContext;
    private Deck deck = Deck.getInstance(mContext);


    public ItrTest(Context context, Deck deck, int start_loc) {
        currentIndex = start_loc;
        mContext = context;
    }

    @Override
    public boolean hasNext(){
        if ( deck.getNumberQuestions() != deck.getNumberQuestionsComplete() ){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Question next() {

        // Avoids the app crashing at end of array
        if (currentIndex > deck.getNumberQuestions()) {
            currentIndex = 1;
        }

        Question question = deck.getQuestion(currentIndex);
        currentIndex++;
        //Toast.makeText(mContext, "Current Test Index: " + currentIndex, Toast.LENGTH_SHORT).show();
        return question;
    }

    @Override
    public Question current() {
        Question question = deck.getQuestion(currentIndex - 1);             // Every time we access current question, the index has already been pushed forward
        return question;
    }

}
