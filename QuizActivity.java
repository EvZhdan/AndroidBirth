/*
** Controller
 */
package ru.drivermql.geoquiz;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestiosTextView;
    private TextView mNewTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),
    };
    private  int mCurrentIndex = 0; // variable for massiv's index
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);//метод заполняет (inflates) макет и выводит на экран

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

        mQuestiosTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);//получаем заполненные объекты View, преобразованные в Button
        //mTrueButton = (Button) findViewById(R.id.question_text_view);//получаем заполненные объекты View, преобразованные в Button
        mTrueButton.setOnClickListener(new View.OnClickListener(){ //нзначаем слушателя для кнопки TRUE
            @Override
            public void onClick(View v){
                checkAnswer(true);
                buttonsOff();
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);//получаем заполненные объекты View, преобразованные в Button
        mFalseButton.setOnClickListener(new View.OnClickListener(){ //нзначаем слушателя для кнопки TRUE
            @Override
            public void onClick(View v){
                checkAnswer(false);
                buttonsOff();
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                updateQuestion();
                buttonsOn();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                startActivityForResult(intent,REQUEST_CODE_CHEAT);
            }
        });
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mCurrentIndex <=0) mCurrentIndex = mQuestionBank.length;

                mCurrentIndex = (mCurrentIndex-1) % mQuestionBank.length;
                updateQuestion();
                buttonsOn();
            }
        });

        mNewTextView = (TextView) findViewById(R.id.question_text_view);
        mNewTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    private void updateQuestion(){

        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestiosTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        } else {

            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toasts;
            } else {
                messageResId = R.string.incorrect_toasts;
            }
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    public void buttonsOff(){
        mFalseButton.setEnabled(false);
        mTrueButton.setEnabled(false);
    }
    public void buttonsOn(){
        mFalseButton.setEnabled(true);
        mTrueButton.setEnabled(true);
        }
}
