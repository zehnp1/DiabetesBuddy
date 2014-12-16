package com.example.pzehnder.diabetesbuddy;

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
import android.widget.SpinnerAdapter;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;

import java.util.Date;


public class Registrierung extends Activity {
    private String gender ="male";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrierung);


        Spinner dropdown = (Spinner)findViewById(R.id.Sprache);
        String[] items = new String[]{"Deutsch", "Französisch", "Italienisch"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        Button buttonRegAbschluss = (Button) findViewById(R.id.RegistrierungsButton);
        buttonRegAbschluss.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try
                {
                    prepareUserData();
                }
                catch (Exception e)
                {
                    Log.d("Registrierungserror",e.toString());
                }
                Intent homeView = new Intent(Registrierung.this, Home.class);
                startActivity(homeView);
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
        if (id == R.id.action_profil) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.maleCheckBox:
                if (checked)
                    // Person is male
                    gender = "male";
                    break;
            case R.id.femaleCheckBox:
                if (checked)
                    // Person is female
                    gender = "female";
                    break;
        }
    }

    private String[] prepareUserData()
    {
        EditText text = (EditText)findViewById(R.id.nameFeld);
        String name = text.getText().toString();

        text = (EditText)findViewById(R.id.vornameFeld);
        String vorname = text.getText().toString();

        text = (EditText)findViewById(R.id.eMailFeld);
        String mail = text.getText().toString();

        text = (EditText)findViewById(R.id.usernameFeld);
        String username = text.getText().toString();

        text = (EditText)findViewById(R.id.PasswortFeld);
        String password = text.getText().toString();

        String passwordTester = text.getText().toString();
        text = (EditText)findViewById(R.id.passwortBestätigen);

        text = (EditText)findViewById(R.id.weight);
        float weight = Float.parseFloat(text.getText().toString());

        text = (EditText)findViewById(R.id.birthday);
        String birthdate = text.getText().toString();

        Spinner spinner = (Spinner)findViewById(R.id.Sprache);
//        String language =  spinner.getPrompt().toString();

        DatabaseHandler db = Login.getDb();
        db.open();
        db.insertUserData(username,name,vorname,mail,password,weight,birthdate,gender);

        Cursor userdata = db.returnUserData(username);

        userdata.moveToFirst();
        Log.d("registrierung:", userdata.getString(0)+userdata.getString(1));
        db.close();
        return null;
    }


}


