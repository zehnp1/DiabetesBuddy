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
 * Log:
 * Erstellt von Ivan Wissler 29.11.2014
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Die Klasse QuizCompWidget bildet den benutzerdefinierten Container zum einfügen der Quizfragen.
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
        //Dem Component wird das im quiz_component definierte Layout übertragen
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.quiz_component, this,true);
        setUpViewItems();
    }

    private void setUpViewItems()
    {
        //Die Component Elemente für die Frage und die 4 Antwort Buttons werden definiert
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

    /**
     * setQuizFrageText erlaubt es dem Conteainer eine neue Frage zu übergeben
     * @param quizFrage
     */
    public void setQuizFrageText(String quizFrage) {
        this.quizFrageText.setText(quizFrage);
    }

    /**
     * SetQuizAntwort1 erlaubt es dem Container eine neue Antwort 1 zu übergeben
     * @param quizAntwort1
     */
    public void setQuizAntwort1ButtonText (String quizAntwort1)
    {
        this.quizAntwort1Button.setText(quizAntwort1);
    }
    /**
     * SetQuizAntwort2 erlaubt es dem Container eine neue Antwort 2 zu übergeben
     * @param quizAntwort2
     */
    public void setQuizAntwort2ButtonText (String quizAntwort2)
    {
        this.quizAntwort2Button.setText(quizAntwort2);
    }
    /**
     * SetQuizAntwort3 erlaubt es dem Container eine neue Antwort 3 zu übergeben
     * @param quizAntwort3
     */
    public void setQuizAntwort3ButtonText (String quizAntwort3)
    {
        this.quizAntwort3Button.setText(quizAntwort3);
    }
    /**
     * SetQuizAntwort4 erlaubt es dem Container eine neue Antwort 4 zu übergeben
     * @param quizAntwort4
     */
    public void setQuizAntwort4ButtonText (String quizAntwort4)
    {
        this.quizAntwort4Button.setText(quizAntwort4);
    }
}
