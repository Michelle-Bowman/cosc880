package com.michellebowman.android.praxisquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class LeaderboardActivityFragment extends Fragment {

    private TextView mScoreList;
    private ArrayList<Score> mAllScores;

    private ImageView mBronzeBadge;
    private ImageView mSilverBadge;
    private ImageView mGoldBadge;

    private TextView mBronzeMessage;
    private TextView mSilverMessage;
    private TextView mGoldMessage;

    private TextView mAvgScore;

    private Button mDeleteDB;

    public LeaderboardActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        final Leaderboard leaderboard = Leaderboard.getInstance(this.getActivity());
        mAllScores = leaderboard.getAllScoresFromDB();

        mBronzeMessage = (TextView) v.findViewById(R.id.bronze_badge_text);
        mSilverMessage = (TextView) v.findViewById(R.id.silver_badge_text);
        mGoldMessage = (TextView) v.findViewById(R.id.gold_badge_text);
        mAvgScore = (TextView) v.findViewById(R.id.avg_score_text);

        mBronzeMessage.setText(R.string.unlocked_bronze);
        mSilverMessage.setText(R.string.unlocked_silver);
        mGoldMessage.setText(R.string.unlocked_gold);

        mBronzeBadge = (ImageView) v.findViewById(R.id.bronze_badge_image);
        mSilverBadge = (ImageView) v.findViewById(R.id.silver_badge_image);
        mGoldBadge = (ImageView) v.findViewById(R.id.gold_badge_image);
        grayScaleImages();

        int avgScore = leaderboard.getAvgTestScore();
        mAvgScore.setText(getString(R.string.avg_score, avgScore));

        mScoreList = (TextView) v.findViewById(R.id.score_list);
        mScoreList.setText("\n");

        mDeleteDB = (Button) v.findViewById(R.id.deleteDB_button);
        mDeleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaderboard.clearAllScoresFromDB(null, null);
                mScoreList.setText("\n");

                mBronzeBadge.setColorFilter(Color.argb(200, 255, 255, 255));
                mSilverBadge.setColorFilter(Color.argb(200, 255, 255, 255));
                mGoldBadge.setColorFilter(Color.argb(200, 255, 255, 255));

                leaderboard.setBronzeBadge(false);
                leaderboard.setSilverBadge(false);
                leaderboard.setGoldBadge(false);
            }
        });


        for(int i = 0; i < mAllScores.size(); i++) {
            Score score = mAllScores.get(i);

            String id = Integer.toString(score.getId());
            String date = score.getDate();
            String mode = score.getMode();
            String thisScore = Integer.toString(score.getGameScore());

            if (score.getId() < 10) {
                mScoreList.append(id + "\t\t\t\t\t\t\t" + date + "\t\t\t\t\t" + mode + "\t\t\t\t\t" + thisScore + "%" + "\n");
            }
            else {
                mScoreList.append(id + "\t\t\t\t\t" + date + "\t\t\t\t\t" + mode + "\t\t\t\t\t" + thisScore + "%" + "\n");
            }

        }

        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Let FragmentManager know that this fragment needs to receive menu callbacks. NR p. 257

    }

    // Inflate the Settings menu. NR p. 256
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }


    public void grayScaleImages() {
        final Leaderboard leaderboard = Leaderboard.getInstance(this.getActivity());
        leaderboard.checkBadges();

        // Gray out if haven't reached yet.
        if (!leaderboard.isBronzeBadge()) {
            mBronzeMessage.setText(R.string.locked_bronze);
            mBronzeBadge.setColorFilter(Color.argb(200, 255, 255, 255));
        }
        if (!leaderboard.isSilverBadge()) {
            mSilverMessage.setText(R.string.locked_silver);
            mSilverBadge.setColorFilter(Color.argb(200, 255, 255, 255));
        }
        if (!leaderboard.isGoldBadge()) {
            mGoldMessage.setText(R.string.locked_gold);
            mGoldBadge.setColorFilter(Color.argb(200, 255, 255, 255));
        }

    }


}
