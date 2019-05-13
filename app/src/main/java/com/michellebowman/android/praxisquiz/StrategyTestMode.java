package com.michellebowman.android.praxisquiz;

import android.content.Context;
import android.support.v4.app.FragmentManager;

public class StrategyTestMode implements StrategyMode {

    private static final String TEST_END_DIALOG = "TestEndDialog";

    @Override
    public ItrIterface getItr(Context context) {
        return Deck.getInstance(context).getItrAllQuestions();
    }

    @Override
    public void inflateDialog(FragmentManager manager) {
        DialogTestEnd dialog = new DialogTestEnd();
        dialog.show(manager, TEST_END_DIALOG);
    }


}
