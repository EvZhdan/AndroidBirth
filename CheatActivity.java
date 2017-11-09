package ru.drivermql.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "ru.drivermql.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "ru.drivermql.geoquiz.answer_shown";
    private static final String TAG = "CheatActivity";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        Log.d(TAG,"1 mAnswerIsTrue: ");
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);

        final TextView versionOS = (TextView) findViewById(R.id.show_version_os);
        int ver = Build.VERSION.SDK_INT;
        versionOS.setText("API Level:" + ver);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
                //Log.d(TAG,"setAnswerShownResult(true): " +mAnswerIsTrue);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;

                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        //Log.d(TAG,"onStart() called, mAnswerIsTrue: " +mAnswerIsTrue);
    }

    @Override
    public void onResume(){
        super.onResume();
     //   Log.d(TAG,"onResume() called , mAnswerIsTrue: " +mAnswerIsTrue);
    }

    @Override
    public void onPause(){
        super.onPause();
       // Log.d(TAG,"onPause() called, mAnswerIsTrue: " +mAnswerIsTrue);
    }

    @Override
    public void onStop(){
        super.onStop();
        //Log.d(TAG,"onStop() called, mAnswerIsTrue: " +mAnswerIsTrue);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //Log.d(TAG,"onDestroy() called, mAnswerIsTrue: " +mAnswerIsTrue);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }
}
