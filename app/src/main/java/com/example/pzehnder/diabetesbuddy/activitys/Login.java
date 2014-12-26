package com.example.pzehnder.diabetesbuddy.activitys;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.View;

import com.example.pzehnder.diabetesbuddy.R;
import com.example.pzehnder.diabetesbuddy.data.AsynchNetwork;
import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;
import com.example.pzehnder.diabetesbuddy.data.InitDbValues;

/**
 * Log:
 * Erstellt von Michael Heeb.
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Die Login Activity ist die Main Activity und wird nach dem Starten der
 * Applikation als erstes ausgeführt. Der Benutzer kann sich mit einem bereits vorhanden Account einloggen
 * oder durch "Registrieren" einen neuen Account anlegen.
 *
 */
public class Login extends Activity {

    private static DatabaseHandler dbHandler;
    /**
     * user: Speichert den eingeloggten Username und setellt ihn den anderen Activitis zu verfügung.
     */
    public static String user;
    /**
     * phonenumber: Speichert die Telefonnummer der Bezugsperson aus der Datenbank.
     */
    public static String phonenumber = "";
    /**
     * bananas: Speichert die anzahl Bananen des Benutzers aus der Datenbank.
     */
    public static int bananas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Erstellt eine Datenbank Verbindung
        dbHandler = new DatabaseHandler(this);
        // Lädt die Initialeinträge der Datenbank insofern diese noch nicht Vorhanden sind
        InitDbValues.init(this);

        //Login Button
        Button buttonLogin = (Button) findViewById(R.id.homeButton1);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //Liest die Eingaben des Logins
                EditText userfeld = (EditText) findViewById(R.id.nameFeld);
                EditText passwortfeld = (EditText) findViewById(R.id.Passwort);
                //Speichert die Eingabe passwort und user
                String passwort = passwortfeld.getText().toString();
                user = userfeld.getText().toString();

                //öffnet die Datenbankverbindung
                dbHandler.open();
                try {
                    //Überprüft ob der angegebene Username in der Datenbank vorhanden ist und
                    //liest die zu diesem Username vohandenen Angaben aus der Datenbank.
                    Cursor cursor = dbHandler.returnUserData(user);
                    cursor.moveToFirst();

                    //Überprüft ob das angegebene passwort mit der Datenbank übereinstimmt.
                    if (passwort.equals(cursor.getString(4))) {
                        //Wenn ja wird die Telefonnummer der Bezugsperson aus der Datenbank gelesen.
                        phonenumber = cursor.getString(9);
                        //Und die Anzahl von Bananen des Users
                        bananas = cursor.getInt(10);

                        //Es wird zur Home Activity navigiert
                        Intent homeView = new Intent(Login.this, Home.class);
                        startActivity(homeView);

                    }
                } catch (Exception e) {
                    Log.d("Loginerror", e.toString());
                }
                //Datenbankverbindung wird geschlossen
                dbHandler.close();
            }
        });

        //Registrierungs Button
        Button buttonReg = (Button) findViewById(R.id.Registrieren);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Durch klicken des Registrierungsbuttons wird man zur Registrierung Activity
                //weitergeleitet.
                Intent regView = new Intent(Login.this, Registrierung.class);
                startActivity(regView);
            }

        });


    }



    /**
     * getDb() gibt den in der Login Activity erstellten Datenbank Handler zurück
     * und erlaubt es von den anderen Activities auf die Datenbank zuzugreiffen ohne einen
     * neuen Handler erstellen zu müssen.
     *
     * @return dbHandler
     */
    public static DatabaseHandler getDb() {
        return dbHandler;
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
