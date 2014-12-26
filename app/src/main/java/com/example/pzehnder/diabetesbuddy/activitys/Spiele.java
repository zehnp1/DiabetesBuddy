package com.example.pzehnder.diabetesbuddy.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.pzehnder.diabetesbuddy.R;
import com.example.pzehnder.diabetesbuddy.data.AsynchNetwork;

/**
 * Log:
 * Erstellt von Michael Heeb.
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * In der Spiele Activity kann ausgewählt werden welches Spiel mann spielen will.
 * Momentan steht das Diabetes Quizz oder Kohlenhydrate schätzen zu Verfügung.
 *
 */
public class Spiele extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spielen_lernen);

        //Button BE Schätzen leitet einem zum Be Schätzen Spiel weiter
        Button buttonBE = (Button) findViewById(R.id.buttonSpielBE);
        buttonBE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent beView = new Intent(Spiele.this, BESchaetzen.class);
                startActivity(beView);
            }

        });


        //Button Quiz leitet einem zmm Diabetes Quizz weiter
        Button buttonQuiz = (Button) findViewById(R.id.buttonSpieleQuiz);
        buttonQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent quizView = new Intent(Spiele.this, DiabetesQuiz.class);
                startActivity(quizView);
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

