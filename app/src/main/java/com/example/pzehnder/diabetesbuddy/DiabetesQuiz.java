package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;


public class DiabetesQuiz extends Activity {

    private static QuizCompWidget quizCompWidget;
    private static int quizDataPosition;
    private static Dialog dialog;
    private static TextView tittle;
    private static TextView dialogText;
    private static ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diabetes_quiz);

        quizCompWidget = (QuizCompWidget) findViewById(R.id.quizComp1);

        DatabaseHandler db = Login.getDb();
        db.open();
        Cursor quizData = db.returnQuizData("Deutsch");
        quizData.moveToFirst();
        quizDataPosition = quizData.getPosition();
        quizCompWidget.setQuizFrageText(quizData.getString(0));
        quizCompWidget.setQuizAntwort1ButtonText(quizData.getString(1));
        quizCompWidget.setQuizAntwort2ButtonText(quizData.getString(2));
        quizCompWidget.setQuizAntwort3ButtonText(quizData.getString(3));
        quizCompWidget.setQuizAntwort4ButtonText(quizData.getString(4));
        db.close();

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        Button button = (Button)dialog.findViewById(R.id.dialogButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        tittle = (TextView)dialog.findViewById(R.id.dialogTitle);
        image = (ImageView)dialog.findViewById(R.id.dialogImage);
        dialogText = (TextView)dialog.findViewById(R.id.dialogText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_profil) {
            return true;
        }
        if (id == R.id.quiz_update) {
            Log.d("test", "sucsess");
            new AsynchNetwork().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void evaluateAnswer(Integer answer)
    {
        DatabaseHandler db = Login.getDb();
        db.open();
        Cursor quizData = db.returnQuizData("Deutsch");
        quizData.moveToPosition(quizDataPosition);
        if(quizData.getInt(5)==answer)
        {
            tittle.setText("Richtig!");
            image.setImageResource(R.drawable.buddysmile);
            dialogText.setText("Du erhälts 10 Bananen");
            Login.bananas = Login.bananas +10;
        }
        else
        {
            tittle.setText("Falsch!");
            image.setImageResource(R.drawable.buddy_sad);
            dialogText.setText("Die Richtige Antwort ist: " + quizData.getString(quizData.getInt(5)));
        }
        dialog.show();
        nextQuestion();
        db.close();
    }
    private static void nextQuestion()
    {
        DatabaseHandler db = Login.getDb();
        db.open();
        Cursor quizData = db.returnQuizData("Deutsch");
        quizData.moveToPosition(quizDataPosition);
        if(quizData.isLast())
        {
            quizData.moveToFirst();
        }
        else
        {
            quizData.moveToNext();
        }
        quizDataPosition = quizData.getPosition();
        quizCompWidget.setQuizFrageText(quizData.getString(0));
        quizCompWidget.setQuizAntwort1ButtonText(quizData.getString(1));
        quizCompWidget.setQuizAntwort2ButtonText(quizData.getString(2));
        quizCompWidget.setQuizAntwort3ButtonText(quizData.getString(3));
        quizCompWidget.setQuizAntwort4ButtonText(quizData.getString(4));
        db.close();
    }


}
