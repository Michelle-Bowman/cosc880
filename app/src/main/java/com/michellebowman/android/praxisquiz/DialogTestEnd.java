package com.michellebowman.android.praxisquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class DialogTestEnd extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Leaderboard leaderboard = Leaderboard.getInstance(this.getActivity());
        int score = leaderboard.getLastScore();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_test_end, null);


        builder.setView(v)
                .setTitle(R.string.questions_complete_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Go to leaderboard
                                Intent intent = new Intent(getActivity(), LeaderboardActivity.class);
                                startActivity(intent);
                            }
                        });

        TextView dialogTextView = (TextView) v.findViewById(R.id.test_end_text);
        dialogTextView.setText(getString(R.string.test_dialog, score));

        return builder.create();
    }
}