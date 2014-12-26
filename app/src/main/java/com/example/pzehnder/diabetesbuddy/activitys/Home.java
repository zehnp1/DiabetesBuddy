package com.example.pzehnder.diabetesbuddy.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.pzehnder.diabetesbuddy.R;
import com.example.pzehnder.diabetesbuddy.data.AsynchNetwork;

import static com.example.pzehnder.diabetesbuddy.R.*;

/**
 * Log:
 * Erstellt von Michael Heeb am 13.11.2014.
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Zur Home Activity gelangt man nach erfolgreichem Einloggen oder Registrieren.
 * In dieser Activity kann man Auswählen ob man ein Blutzuckertagebuch Eintrag machen will,
 * ein Spiel Spielen oder im Shop Einkaufen will.
 *
 */
public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.home);


        //Button Blutzucker leitet einem zur Blutzucker Activity weiter.
        Button buttonBlutzucker = (Button) findViewById(id.homeButton1);
        buttonBlutzucker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent blutzuckerView = new Intent(Home.this, Blutzucker.class);
                startActivity(blutzuckerView);
            }

        });

        //Button Spiel leitet einem zur Spielauswahl weiter.
        Button buttonSpiele = (Button) findViewById(id.homeButton2);
        buttonSpiele.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent spieleView = new Intent(Home.this, Spiele.class);
                startActivity(spieleView);
            }

        });

        //Button Shop leitet einem zum Shop weiter.
        Button buttonShop = (Button) findViewById(id.homeButton3);
        buttonShop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent shopView = new Intent(Home.this, Shop.class);
                startActivity(shopView);
            }

        });

        //Button Logout leitet einem zum Loggin weiter, wobei momentan kein echtes Loggout
        //gemacht wird und man durch "zurück" wider in die Home Activity gelangen kann.
        Button buttonLogout = (Button) findViewById(id.logoutButton);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent loginView = new Intent(Home.this, Login.class);
                startActivity(loginView);

            }

        });
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

