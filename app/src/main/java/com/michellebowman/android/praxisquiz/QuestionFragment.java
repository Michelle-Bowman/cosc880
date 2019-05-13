package com.michellebowman.android.praxisquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QuestionFragment extends Fragment {

    private static final String TAG = "QuestionFragment";
    private static final String KEY_INDEX = "index";
    private static final String QUIZ_END_DIALOG = "QuizEndDialog";
    private static final String TEST_END_DIALOG = "TestEndDialog";

    private Button mSubmitButton;
    private Button mNextButton;
    private TextView mProgressTextView;
    private TextView mQuestionTextView;
    private TextView mAnswerTextView;
    private TextView mFeedbackTextView;
    private RadioGroup mRadioGroup;
    private RadioButton mButtonA, mButtonB, mButtonC, mButtonD;
    private StrategyContext mContext;



    private int mCurrentIndex = 1; // 1 b/c the first Question ID in deck = 1
    private int mQuestionsComplete = 0;

    private String mMode = "Quiz";  // "Quiz" or "Test"; default is Quiz Mode.
    ItrIterface mItr;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Let FragmentManager know that this fragment needs to receive menu callbacks. NR p. 257
        //Toast.makeText(getContext(), "onCreate() called", Toast.LENGTH_SHORT).show();
    }

    /*
     // p. 280. Might not be relevant to my project. If crime is updated by user, want to push these updates via override the onPause(...). Then write changes to DB
    @Override
    public void onPause() {
        super.onPause();
        //Deck.getInstance(getActivity()).updateQuestion(mQuestion);        // From Nerd Ranch
        Toast.makeText(getContext(), "onPause() called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Deck.getInstance(getActivity()).updateQuestion(mQuestion);        // From Nerd Ranch
        Toast.makeText(getContext(), "onResume() called", Toast.LENGTH_SHORT).show();
    }
     */


    // Inflate the Settings menu. NR p. 256
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    // From Android: Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.quiz_mode:
                mMode = "Quiz";
                mContext = new StrategyContext(new StrategyQuizMode());
                mItr = mContext.selectItr(getContext());
                resetGame();
                return true;

            case R.id.test_mode:
                mMode = "Test";
                mContext = new StrategyContext(new StrategyTestMode());
                mItr = mContext.selectItr(getContext());
                resetGame();
                return true;

            case R.id.leaderboard:
                Intent intent = new Intent(getActivity(), LeaderboardActivity.class);
                startActivity(intent);
                resetGame();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }


    // See p. 144 for more about this method.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);

        //Toast.makeText(getContext(), "onCreateView() called", Toast.LENGTH_SHORT).show();

        /*
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 1);
        }
         */


        final Deck deck = Deck.getInstance(this.getActivity());
        final Leaderboard leaderboard = Leaderboard.getInstance(this.getActivity());

        //Toast.makeText(getContext(), "onCreateView() called", Toast.LENGTH_SHORT).show();

        // Strategy design pattern
        mContext = new StrategyContext(new StrategyQuizMode());
        // Iterator design pattern
        mItr = mContext.selectItr(getContext());

        // Quiz Mode is default when open app
        //StrategyContext context = new StrategyContext(new StrategyQuizMode());
        //final ItrIterface mItr = context.selectItr(getContext());
        //Previously: final ItrIterface mItr = deck.getItrAllQuestions(); // Default at runtime. Having this here could pose issues in Activity lifecycle. Check out onPause(...) etc.

        mProgressTextView = (TextView) v.findViewById(R.id.progress_text_view);
        setProgress();

        mQuestionTextView = (TextView) v.findViewById(R.id.question_text_view);
        mFeedbackTextView = (TextView) v.findViewById(R.id.feedback_text_view);
        mAnswerTextView = (TextView) v.findViewById(R.id.answer_text_view);

        mButtonA = (RadioButton) v.findViewById(R.id.button_A);
        mButtonB = (RadioButton) v.findViewById(R.id.button_B);
        mButtonC = (RadioButton) v.findViewById(R.id.button_C);
        mButtonD = (RadioButton) v.findViewById(R.id.button_D);

        mRadioGroup = (RadioGroup) v.findViewById(R.id.radio_group);
        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            }
        });

        mSubmitButton = (Button) v.findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                boolean repeat = false;

                Question question = mItr.current();

                //Switch statement didn't work here b/c "constant expression required"
                if(selectedId == mButtonA.getId()) {
                    checkAnswer("A", question);
                }
                else if(selectedId == mButtonB.getId()) {
                    checkAnswer("B", question);
                }
                else if(selectedId == mButtonC.getId()) {
                    checkAnswer("C", question);
                }
                else if(selectedId == mButtonD.getId()) {
                    checkAnswer("D", question);
                }
                else {
                    Toast.makeText(getContext(), R.string.no_answer_selected, Toast.LENGTH_SHORT).show();
                    repeat = true;
                }

                disappearSubmitButton(repeat);

            }
        });


        mNextButton = (Button) v.findViewById(R.id.next_button);
        mNextButton.setVisibility(View.INVISIBLE);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mItr.hasNext()) {
                    Question question = mItr.next();
                    updateQuestion(question);
                    mRadioGroup.clearCheck();
                }
                else {
                    endGame(mContext);
                }

                mFeedbackTextView.setText(null);
                mAnswerTextView.setText(null);
                mSubmitButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.INVISIBLE);

            }
        });

        updateQuestion(mItr.next());

        return v;
    }



    /*
    // Need onSaveInstanceState to preserve quiz index when rotate between landscape/portrait modes
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        final Deck deck = Deck.getInstance(this.getActivity());
        ArrayList<Question> questions = deck.getQuestionArrayList();

        //Log.i(TAG, "onSaveInstanceState");
        Toast.makeText(getContext(), "onSaveInstanceState() called", Toast.LENGTH_SHORT).show();
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
     */







    // Private methods for quiz logic

    //4-16-19 updates for iterator.  See below for old code.
    public void updateQuestion(Question question){

            String questionText = question.getQuestionText();
            mQuestionTextView.setText(questionText);

            String aText = question.getAnswerA();
            String bText = question.getAnswerB();
            String cText = question.getAnswerC();
            String dText = question.getAnswerD();

            mButtonA.setText("A)  " + aText);
            mButtonB.setText("B)  " + bText);
            mButtonC.setText("C)  " + cText);
            mButtonD.setText("D)  " + dText);

    }

    // 4-11-19 this is looking now at the submit button.
    public void checkAnswer(String userButton, Question question) {
        final Deck deck = Deck.getInstance(this.getActivity());
        String answer = question.getCorrectAnswer();

        mNextButton.setVisibility(View.VISIBLE);

        // QUIZ MODE
        if (mMode.equals("Quiz")) {

            int feedback = R.string.error_message;

            // If correct
            if (userButton.equals(answer)) {
                feedback = R.string.correct_message;
                mFeedbackTextView.setTextColor(Color.GREEN);
                if (question.isCorrectOnce()) {
                    question.setCorrectTwice(true);
                } else {
                    question.setCorrectOnce(true);
                }

                // Else if incorrect
            } else {
                feedback = R.string.incorrect_message;
                mFeedbackTextView.setTextColor(Color.RED);
            }

            mFeedbackTextView.setText(feedback);
            mAnswerTextView.setText("The answer is " + question.getCorrectAnswer() + ".");

        }

        // TEST MODE
        if (mMode.equals("Test")) {

            // Correct
            if (userButton.equals(answer)) {
                question.setCorrectOnce(true);
            }
            else {
                question.setCorrectOnce(false);
            }

            mQuestionsComplete++;
            deck.setNumberQuestionsComplete(mQuestionsComplete);

        }
        setProgress();
    }

    public void resetGame() {

        mCurrentIndex = 1;

        final Deck deck = Deck.getInstance(this.getActivity());

        mSubmitButton.setVisibility(View.VISIBLE);
        mNextButton.setVisibility(View.INVISIBLE);

        // Reset deck scores
        deck.resetDeck();

        // Reset the progress bar
        setProgress();

        // Start iterator at location 1
        Question question = mItr.next();
        updateQuestion(question);
        mRadioGroup.clearCheck();

    }


    public void setProgress(){
        final Deck deck = Deck.getInstance(this.getActivity());

        if (mMode.equals("Quiz")){
            mProgressTextView.setText("Quiz Mode: " + deck.getNumberCorrectTwice() + " of " + deck.getNumberQuestions() + " correct twice");
        }
        else {
            mProgressTextView.setText("Test Mode: " + deck.getNumberQuestionsComplete() + " of " + deck.getNumberQuestions() + " questions complete");
        }
    }

    /* This if/else conditional statement illustrates technique for compatibility.
                   Animator's Circular Reveal was introduced in SDK version 21 (Lollipop). */

    public void disappearSubmitButton(boolean repeat) {
        // Don't want button to disappear if user didn't submit answer.
        if (repeat == false) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int cx = mSubmitButton.getWidth() / 2;
                int cy = mSubmitButton.getHeight() / 2;
                float radius = mSubmitButton.getWidth();
                Animator anim = ViewAnimationUtils.createCircularReveal(mSubmitButton, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mSubmitButton.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();
            } else {
                mSubmitButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void endGame(StrategyContext context){

        logGameStatsInDB();
        resetGame();

        // Strategy pattern determines which Dialog to inflate to signal end of game
        FragmentManager manager = getFragmentManager();
        context.selectDialog(manager);

    }

    // Log stats in Leaderboard DB table
    public void logGameStatsInDB(){
        final Leaderboard leaderboard = Leaderboard.getInstance(this.getActivity());
        final Deck deck = Deck.getInstance(this.getActivity());
        Score score = new Score();

        //final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm/dd/yyyy");

        String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());

        int totalQuestions = deck.getNumberQuestions();
        int totalCorrect = deck.getTotalCorrect();

        int percentScore = score.calculateScore(totalQuestions, totalCorrect);

        if (mMode.equals("Test")) {
            score.setId(score.getId());
            score.setMode(mMode);
            score.setDate(timeStamp);
            score.setTotalCorrect(totalCorrect);
            score.setTotalQuestions(totalQuestions);
            score.setGameScore(percentScore);
        }
        else {
            score.setId(score.getId());
            score.setMode(mMode);
            score.setDate(timeStamp);
            score.setTotalCorrect(totalQuestions);
            score.setTotalQuestions(totalQuestions);
            score.setGameScore(100);  // Quiz score is always 100%
        }

        leaderboard.addScoreToDB(score);

    }


}
