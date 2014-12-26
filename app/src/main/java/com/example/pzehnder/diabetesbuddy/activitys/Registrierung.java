package com.example.pzehnder.diabetesbuddy.activitys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import com.example.pzehnder.diabetesbuddy.R;
import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;


/**
 * Log:
 * Erstellt von Michael Heeb.
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * In der Registrierungs Activity kann ein neuer Benutzer Account erstellte werden
 * Der username muss dabei Eindeutig Sein.
 *
 * Username, Passwort, Passwort Bestätigung und Telefonnummer einer Bezugsperson müssen zwingend angegeben werden.
 * Alle anderen Felder werden momentan nicht ausgewertet, könnten aber bei möglichen Erweiterungen der App interessant sein.
 *
 */
public class Registrierung extends Activity {
    private String gender ="male";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrierung);

        //Sprachauswahl wird auf Deutsch, Französisch und Italiensich gesetzt
        //Sprachauswahl wird momentan nicht ausgewertet, es ist eine nur Deutsche Version.
        Spinner dropdown = (Spinner)findViewById(R.id.Sprache);
        String[] items = new String[]{"Deutsch", "Französisch", "Italienisch"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        //Registrieurngs Button zum Abschliessen der Registrierung
        Button buttonRegAbschluss = (Button) findViewById(R.id.RegistrierungsButton);
        buttonRegAbschluss.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try
                {

                   if(saveUserData())
                   {
                       //Insofern alle Pflichtfelder ausgefüllt wurden und in die Datenbank gespeichert
                       //werden konneten, wird man zur Home activity weitergeleitet.
                       Intent homeView = new Intent(Registrierung.this, Home.class);
                       startActivity(homeView);
                   }

                }
                catch (Exception e)
                {
                    Log.d("Registrierungserror",e.toString());
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Erstellt das Menu in der Registrierung Activity
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Reagiert auf Eingaben im Menu
        int id = item.getItemId();
        if (id == R.id.action_profil) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


   private void onRadioButtonClicked(View view) {
       //Überprüft die Radiobuttons auf welches Geschlecht ausgewählt wurde.
       //Wird momentan nicht weiterverwertet.
        boolean checked = ((RadioButton) view).isChecked();

        //Überprüft welcher Radiobutton geklickt wurde
        switch(view.getId()) {
            case R.id.maleCheckBox:
                if (checked)
                    // User ist Männlich
                    gender = "male";
                    break;
            case R.id.femaleCheckBox:
                if (checked)
                    // User ist Weiblich
                    gender = "female";
                    break;
        }
    }

    private boolean saveUserData()
    {
        //Liest die Eingaben aus dem Registrierungsformular und überprüft ob alle Pflichtfelder
        //ausgefüllt wurden. Wenn Ja werden die Daten in die Datenbank gespeichert.
        //Pflichtfelder sind  username, password, passwordTester und telefon.

        //der Tester wird auf false Gesetzt wenn nicht alle nötigen angaben richtig gemacht wurden
        boolean tester = true;

        //Speichert eingaben von Name
        EditText text = (EditText)findViewById(R.id.nameFeld);
        String name = text.getText().toString();

        //Speichert eingaben von Vorname
        text = (EditText)findViewById(R.id.vornameFeld);
        String vorname = text.getText().toString();

        ///Speichet eingaben von Mail
        text = (EditText)findViewById(R.id.eMailFeld);
        String mail = text.getText().toString();

        //Speichert eingaben von Username und überprüft ob das Feld ausgefüllt wurde
        text = (EditText)findViewById(R.id.usernameFeld);
        if(text.getText().length() == 0)
        {
            tester = false;
        }
        String username = text.getText().toString();

        //Speichert eingabe von Passwort und überprüft ob das Feld ausgewüllt wurde
        text = (EditText)findViewById(R.id.PasswortFeld);
        if(text.getText().length() == 0)
        {
            tester = false;
        }
        String password = text.getText().toString();

        //Speichert eingabe von Passwort Bestäteigen und über Prüft ob das Feld gleich wie Passwort ausgefüllt wurde.
        text = (EditText)findViewById(R.id.passwortBestätigen);
        String passwordTester = text.getText().toString();
        if(!password.equals(passwordTester))
        {
            tester = false;
        }

        //Speichert eingabe zu Gewicht, falls keine eingabe gemacht wurde, wird Gewicht auf 0 gesetzt.
        float weight = 0;
        try {
            text = (EditText) findViewById(R.id.weight);
            weight = Float.parseFloat(text.getText().toString());
        }
        catch (Exception e)
        {
            Log.d("Registrierung kein gewicht",e.toString());
        }

        //Speichert eingaben zum Geburtsdatumg
        text = (EditText)findViewById(R.id.birthday);
        String birthdate = text.getText().toString();

        //Speichert eingabe zum Telefon der Bezugsperson und überprüft ob das Fedld ausgefüllt wurde
        text = (EditText)findViewById(R.id.telefonFeld);
        if(text.getText().length() == 0)
        {
            tester = false;
        }
        String telefon = text.getText().toString();

        Spinner spinner = (Spinner)findViewById(R.id.Sprache);

        if(tester) {
            //Wenn alle Pflichtfelder ausgefüllt wurden, werden die Daten in die Datenbank geladen.
            DatabaseHandler db = Login.getDb();
            db.open();
            db.insertUserData(username, name, vorname, mail, password, weight, birthdate, gender, telefon);
            Login.user = username;
            Login.phonenumber = telefon;
            Login.bananas = 0;
        }
        return tester;
    }


}


