package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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

        beCompWidget = (BeCompWidget)findViewById(R.id.beComp1);
        DatabaseHandler db = Login.getDb();
        db.open();
        Cursor beData = db.returnBeData("BE");
        beData.moveToFirst();
        beDataPosition = beData.getPosition();
        beCompWidget.setNahrungsmittel(beData.getString(0));
        beCompWidget.setBeAntwort1ButtonText(beData.getString(1));
        beCompWidget.setBeAntwort2ButtonText(beData.getString(2));
        beCompWidget.setBeAntwort3ButtonText(beData.getString(3));
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

    public static void evaluateAnswer(Integer answer)
    {
        DatabaseHandler db = Login.getDb();
        db.open();
        Cursor beData = db.returnBeData("BE");
        beData.moveToPosition(beDataPosition);
        if(beData.getInt(4)==answer)
        {
            Log.d("Antwort","Correct");
            tittle.setText("Richtig!");
            image.setImageResource(R.drawable.buddysmile);
            dialogText.setText("Du erhälts 10 Bananen");
            Login.bananas = Login.bananas +10;
        }
        else
        {
            Log.d("Antwort","Falsch");
            tittle.setText("Falsch!");
            image.setImageResource(R.drawable.buddy_sad);
            dialogText.setText("Die Richtige Antwort ist: " + beData.getString(beData.getInt(4)));
        }
        dialog.show();
        nextQuestion();
        db.close();

    }
    private static void nextQuestion()
    {
        DatabaseHandler db = Login.getDb();
        db.open();
        Cursor beData = db.returnBeData("BE");
        beData.moveToPosition(beDataPosition);
        if(beData.isLast())
        {
            beData.moveToFirst();
        }
        else
        {
            beData.moveToNext();
        }
        beDataPosition = beData.getPosition();
        beCompWidget.setNahrungsmittel(beData.getString(0));
        beCompWidget.setBeAntwort1ButtonText(beData.getString(1));
        beCompWidget.setBeAntwort2ButtonText(beData.getString(2));
        beCompWidget.setBeAntwort3ButtonText(beData.getString(3));
        db.close();
    }
}

