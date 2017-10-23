/*
Контроллер
 */
package ru.drivermql.geoquiz;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestiosTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true),
    };
    private  int mCurrentIndex = 0; // переменная для индекса массива

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);//метод заполняет (inflates) макет и выводит на экран

        mTrueButton = (Button) findViewById(R.id.true_button);//получаем заполненные объекты View, преобразованные в Button
        mTrueButton.setOnClickListener(new View.OnClickListener(){ //нзначаем слушателя для кнопки TRUE
            @Override
            public void onClick(View v){
                Toast toast = Toast.makeText(QuizActivity.this,R.string.correct_toasts,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,50);
                toast.show();
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);//получаем заполненные объекты View, преобразованные в Button
        mFalseButton.setOnClickListener(new View.OnClickListener(){ //нзначаем слушателя для кнопки TRUE
            @Override
            public void onClick(View v){
                Toast toast = Toast.makeText(QuizActivity.this,R.string.incorrect_toasts,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,50);
                toast.show();
            }
        });
    }
}
