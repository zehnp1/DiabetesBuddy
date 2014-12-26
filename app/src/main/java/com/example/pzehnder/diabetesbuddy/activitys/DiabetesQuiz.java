package com.example.pzehnder.diabetesbuddy.activitys;

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

import com.example.pzehnder.diabetesbuddy.R;
import com.example.pzehnder.diabetesbuddy.components.QuizCompWidget;
import com.example.pzehnder.diabetesbuddy.data.AsynchNetwork;
import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;

/**
 * Log:
 * Erstellt von Michael Heeb.
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * In der Diabetes Quiz Activity kann das Quizz gespielt werden
 *
 */
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

        //Instanziert den Container für die Quizzfragen
        quizCompWidget = (QuizCompWidget) findViewById(R.id.quizComp1);

        //Lädt Datenbank
        DatabaseHandler db = Login.getDb();
        //Öffnet Datenbank
        db.open();
        //Lädt alle Deutschen Quizfragen
        Cursor quizData = db.returnQuizData("Deutsch");
        //Überträgt die erste Quizfage aus der Datenbank in den Container
        quizData.moveToFirst();
        quizDataPosition = quizData.getPosition();
        quizCompWidget.setQuizFrageText(quizData.getString(0));
        quizCompWidget.setQuizAntwort1ButtonText(quizData.getString(1));
        quizCompWidget.setQuizAntwort2ButtonText(quizData.getString(2));
        quizCompWidget.setQuizAntwort3ButtonText(quizData.getString(3));
        quizCompWidget.setQuizAntwort4ButtonText(quizData.getString(4));
        //Schliesst die Datenbank
        db.close();

        //Instanziert den Dialog der Beim Beantworten einer Frage angezeigt wird
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

    /**
     * Überprüft ob die als Parameter übergebene Antwort die richtige ist
     * Ensprechent wird der Dialog angezeigt, der Rückmeldet ob die Antwort Richtig oder Falsch war
     * @param answer
     */
    public static void evaluateAnswer(Integer answer)
    {
        //Lädt Datebnak
        DatabaseHandler db = Login.getDb();
        //Öffnet Datenbank
        db.open();
        //Lädt alle Deutschen Quizzfragen.
        Cursor quizData = db.returnQuizData("Deutsch");
        //Lädt die Aktuell ausgewählte Quizzfrage.
        quizData.moveToPosition(quizDataPosition);
        //überprüft ob die Fage Richtig beantwortet wrude
        if(quizData.getInt(5)==answer)
        {
            //Wenn Ja wird der Dialog entsprechend Dargestellt
            tittle.setText("Richtig!");
            image.setImageResource(R.drawable.buddysmile);
            dialogText.setText("Du erhälts 10 Bananen");
            //Der User erhält 10 Bananen
            Login.bananas = Login.bananas +10;
            //Die Anzahl Bananen werden in die Datenbank gespeichert
            db.updateUserBanana(Login.bananas,Login.user);
        }
        else
        {
            //Wenn die Antwort Falsch war wird der Dialog entsprechend Dargesellt
            tittle.setText("Falsch!");
            image.setImageResource(R.drawable.buddy_sad);
            dialogText.setText("Die Richtige Antwort ist: " + quizData.getString(quizData.getInt(5)));
        }
        //Der Dialog wird Angezeigt
        dialog.show();
        //Es wird die Nächste Frage in den Container geladen
        nextQuestion();
        //Datenbank wird geschlossen
        db.close();
    }
    private static void nextQuestion()
    {
        //Lädt die Nächste Frage in den Container

        //Lädt Datenbank
        DatabaseHandler db = Login.getDb();
        //öffnet Datenbank
        db.open();
        //Lädt alle Deutschen Quizfragen
        Cursor quizData = db.returnQuizData("Deutsch");
        //Lädt die vorherige Quizfrage
        quizData.moveToPosition(quizDataPosition);
        //Falls die Quiz Frage die Letzte ist wird wider die Erste Frage geladen
        if(quizData.isLast())
        {
            quizData.moveToFirst();
        }
        else
        {
            //Fals die vorherige nicht die letzte ist wird die Nächste Frage geladen.
            quizData.moveToNext();
        }
        //Füllt die geladene Frage in den Container ab.
        quizDataPosition = quizData.getPosition();
        quizCompWidget.setQuizFrageText(quizData.getString(0));
        quizCompWidget.setQuizAntwort1ButtonText(quizData.getString(1));
        quizCompWidget.setQuizAntwort2ButtonText(quizData.getString(2));
        quizCompWidget.setQuizAntwort3ButtonText(quizData.getString(3));
        quizCompWidget.setQuizAntwort4ButtonText(quizData.getString(4));
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Erstellt das Menu in der Home Activity
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Reagiert auf Menu Eingaben

        int id = item.getItemId();
        if (id == R.id.action_profil) {
            return true;
        }
        // Durch auswahl von Quiz update im Menu werden neue Quizzfragen per Webservice geladen.
        if (id == R.id.quiz_update) {
            new AsynchNetwork().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
