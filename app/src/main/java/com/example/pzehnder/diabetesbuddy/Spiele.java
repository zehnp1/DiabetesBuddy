package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;





public class Spiele extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spielen_lernen);

        Button buttonBE = (Button) findViewById(R.id.buttonSpielBE);
        buttonBE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent beView = new Intent(Spiele.this, BESchaetzen.class);
                startActivity(beView);
            }

        });


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





}

