package com.michellebowman.android.praxisquiz;

import android.content.Context;
import android.support.v4.app.FragmentManager;

public class StrategyQuizMode implements StrategyMode {

    private static final String QUIZ_END_DIALOG = "QuizEndDialog";

    @Override
    public ItrIterface getItr(Context context) {
        return Deck.getInstance(context).getItrTwiceCorrect();
    }

    @Override
    public void inflateDialog(FragmentManager manager) {
        DialogQuizEnd dialog = new DialogQuizEnd();
        dialog.show(manager, QUIZ_END_DIALOG);
    }

}


