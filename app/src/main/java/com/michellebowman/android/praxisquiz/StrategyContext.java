package com.michellebowman.android.praxisquiz;

import android.content.Context;
import android.support.v4.app.FragmentManager;

public class StrategyContext {

    private StrategyMode mStrategy;

    public StrategyContext(StrategyMode strategy) {
        mStrategy = strategy;
    }

    public ItrIterface selectItr(Context context) {
        return mStrategy.getItr(context);
    }

    public void selectDialog(FragmentManager manager) {
        mStrategy.inflateDialog(manager);
    }


}
