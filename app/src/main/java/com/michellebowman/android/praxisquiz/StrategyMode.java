package com.michellebowman.android.praxisquiz;

import android.content.Context;
import android.support.v4.app.FragmentManager;

public interface StrategyMode {
    public ItrIterface getItr(Context context);
    public void inflateDialog(FragmentManager manager);
}
