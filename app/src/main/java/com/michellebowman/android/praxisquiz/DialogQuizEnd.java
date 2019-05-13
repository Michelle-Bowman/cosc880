package com.michellebowman.android.praxisquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class DialogQuizEnd extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_quiz_end, null);


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.questions_complete_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Go to leaderboard
                                Intent intent = new Intent(getActivity(), LeaderboardActivity.class);
                                startActivity(intent);
                            }
                        })
                .create();
    }

}
