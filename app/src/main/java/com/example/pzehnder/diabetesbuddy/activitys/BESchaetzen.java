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
import com.example.pzehnder.diabetesbuddy.components.BeCompWidget;
import com.example.pzehnder.diabetesbuddy.data.AsynchNetwork;
import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;

/**
 * Log:
 * Erstellt von Michael Heeb.
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * In der BE Schätzen Activity kann das Spiel gespielt werden wo man Kohlenhydrate schätzen muss
 *
 */
public class BESchaetzen extends Activity {

    private static BeCompWidget beCompWidget;
    private static Dialog dialog;
    private static TextView tittle;
    private static TextView dialogText;
    private static ImageView image;
    private static int beDataPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.be_schaetzen);

        //instanziert Container wo die Fragen dargestellt werden.
        beCompWidget = (BeCompWidget)findViewById(R.id.beComp1);
        //Lädt Datenbank
        DatabaseHandler db = Login.getDb();
        //Öffnet Datenbank
        db.open();
        //Lädt alle Daten wo die Einheit der Kohlenhydrate in BE angiebt.
        Cursor beData = db.returnBeData("BE");
        //Lädt erste Frage
        beData.moveToFirst();
        beDataPosition = beData.getPosition();
        //Füllt die geladene Frage in den Container
        beCompWidget.setNahrungsmittel(beData.getString(0));
        beCompWidget.setBeAntwort1ButtonText(beData.getString(1));
        beCompWidget.setBeAntwort2ButtonText(beData.getString(2));
        beCompWidget.setBeAntwort3ButtonText(beData.getString(3));
        //Schliesst Datenbank
        db.close();

        //Instanziert Dialog der beim Beantwortetn der Frage anzeigt ob die Frage richtig oder
        //falsch beantwortet wurde
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
        //Lädt Datenbank
        DatabaseHandler db = Login.getDb();
        //Öffnet Datenbank
        db.open();
        //Lädt alle Fragen mit der Einheit BE für Kohlenhydrate
        Cursor beData = db.returnBeData("BE");
        //Lädt die Aktuelle Frage
        beData.moveToPosition(beDataPosition);
        //Überprüfft ob die Frage Richtig beantwortet wurde
        if(beData.getInt(4)==answer)
        {
            //Wenn Ja wird der Dialog entsprechend Dargestellt
            tittle.setText("Richtig!");
            image.setImageResource(R.drawable.buddysmile);
            dialogText.setText("Du erhälts 10 Bananen");
            //Der User erhät 10 Bananen
            Login.bananas = Login.bananas +10;
            //Die neue Anzahl Bananen wird in die Datenbank gespeichert
            db.updateUserBanana(Login.bananas,Login.user);
        }
        else
        {
            //Wenn die Antwort falsch beantowrtet wrude wird der Dialog entsprechend dargestellt
            tittle.setText("Falsch!");
            image.setImageResource(R.drawable.buddy_sad);
            dialogText.setText("Die Richtige Antwort ist: " + beData.getString(beData.getInt(4)));
        }
        //Der Dialog wird Angezeigt
        dialog.show();
        //Die Nächste Frage wird geladen
        nextQuestion();
        db.close();

    }
    private static void nextQuestion()
    {
        //Lädt die Nächste Frage

        //Lädt Datenbank
        DatabaseHandler db = Login.getDb();
        //öffnet Datenbank
        db.open();
        //Lädt alle Fragen mit der Einheit BE für Kohlenhydrate
        Cursor beData = db.returnBeData("BE");
        //Lädt die vorhergehende Frage.
        beData.moveToPosition(beDataPosition);
        //War die Vorhergehende Frage die Letzte, wird wider die erste Frage geladen.
        if(beData.isLast())
        {
            beData.moveToFirst();
        }
        else
        {
            //War die Vorhergehende Frage nich die Letzte wird die Nächste Frage geladen.
            beData.moveToNext();
        }
        beDataPosition = beData.getPosition();
        //Füllt die geladene Frage in den Container
        beCompWidget.setNahrungsmittel(beData.getString(0));
        beCompWidget.setBeAntwort1ButtonText(beData.getString(1));
        beCompWidget.setBeAntwort2ButtonText(beData.getString(2));
        beCompWidget.setBeAntwort3ButtonText(beData.getString(3));
        //Datenbank wird geschlossen
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Erstellt das Menu in der Login Activity
        getMenuInflater().inflate(R.menu.login, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Reagiert auf Eingaben im Menu

        int id = item.getItemId();
        if (id == R.id.action_profil) {
            return true;
        }
        // Wenn im Menu quiz update gewählt wurde wird eine asynchrone Netzwerkverbindung gestartet
        // und per webservice neue Fragen für das Quiz in die Datebank geladen.
        if (id == R.id.quiz_update) {
            new AsynchNetwork().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

