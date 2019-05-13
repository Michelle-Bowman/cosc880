package com.michellebowman.android.praxisquiz;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        /*





    }

    // From Android: Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Strategy Pattern
        StrategyContext context;
        ItrIterface itr;

        switch (item.getItemId()) {

            case R.id.quiz_mode:
                // Start the Question Fragment from here. Somehow pass the quiz mode.
                return true;

            case R.id.test_mode:
                // Start the Question Fragment from here. Somehow pass the quiz mode.
                return true;

            case R.id.leaderboard:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

         */



    }

}
