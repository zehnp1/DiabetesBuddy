package com.example.pzehnder.diabetesbuddy.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pzehnder.diabetesbuddy.activitys.DiabetesQuiz;
import com.example.pzehnder.diabetesbuddy.R;

/**
 * Created by Ivan on 29.11.2014.
 */
public class QuizCompWidget extends LinearLayout {

    private TextView quizFrageText;
    private Button quizAntwort1Button;
    private Button quizAntwort2Button;
    private Button quizAntwort3Button;
    private Button quizAntwort4Button;

    public QuizCompWidget(Context context)
    {
        super(context);
    }
    public QuizCompWidget(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.quiz_component, this,true);
        setUpViewItems();
    }

    private void setUpViewItems()
    {
        quizFrageText = (TextView) findViewById(R.id.quizFrage);
        quizAntwort1Button = (Button) findViewById(R.id.quizAntwort1);
        quizAntwort1Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DiabetesQuiz.evaluateAnswer(1);
            }
        });
        quizAntwort2Button = (Button) findViewById(R.id.quizAntwort2);
        quizAntwort2Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DiabetesQuiz.evaluateAnswer(2);
            }
        });
        quizAntwort3Button = (Button) findViewById(R.id.quizAntwort3);
        quizAntwort3Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DiabetesQuiz.evaluateAnswer(3);
            }
        });
        quizAntwort4Button = (Button) findViewById(R.id.quizAntwort4);
        quizAntwort4Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DiabetesQuiz.evaluateAnswer(4);
            }
        });


    }

    public void setQuizFrageText(String quizFrage) {
        this.quizFrageText.setText(quizFrage);
    }

    public void setQuizAntwort1ButtonText (String quizAntwort1)
    {
        this.quizAntwort1Button.setText(quizAntwort1);
    }
    public void setQuizAntwort2ButtonText (String quizAntwort2)
    {
        this.quizAntwort2Button.setText(quizAntwort2);
    }
    public void setQuizAntwort3ButtonText (String quizAntwort3)
    {
        this.quizAntwort3Button.setText(quizAntwort3);
    }
    public void setQuizAntwort4ButtonText (String quizAntwort4)
    {
        this.quizAntwort4Button.setText(quizAntwort4);
    }
}
