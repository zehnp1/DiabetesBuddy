package com.example.pzehnder.diabetesbuddy.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pzehnder.diabetesbuddy.activitys.BESchaetzen;
import com.example.pzehnder.diabetesbuddy.R;

/**
 * Log:
 * Erstellt von Ivan Wissler 29.11.2014
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Die Klasse BeCompWidget bildet den benutzerdefinierten Container zum einfügen der BEfragen.
 */
public class BeCompWidget extends LinearLayout {

    private ImageView nahrungsmittel;
    private Button beAntwort1Button;
    private Button beAntwort2Button;
    private Button beAntwort3Button;

    public BeCompWidget(Context context)
    {
        super(context);
    }
    public BeCompWidget(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        //Dem Component wird das im be_component definierte Layout übertragen
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.be_component, this,true);
        setUpViewItems();
    }

    private void setUpViewItems()
    {
        //Die Component Elemente für das Bild und die 3 Antwort Buttons werden definiert
        nahrungsmittel = (ImageView)findViewById(R.id.beImage);

        beAntwort1Button = (Button) findViewById(R.id.beAntwort1);
        beAntwort1Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BESchaetzen.evaluateAnswer(1);
            }
        });
        beAntwort2Button = (Button) findViewById(R.id.beAntwort2);
        beAntwort2Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BESchaetzen.evaluateAnswer(2);
            }
        });
        beAntwort3Button = (Button) findViewById(R.id.beAntwort3);
        beAntwort3Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               BESchaetzen.evaluateAnswer(3);
            }
        });
    }

    /**
     * SetNahrungsmittel eralubte es dem Container ein neues Bild eins Nahrungsmittels zu übergeben
     * @param nahrungsmittel
     */
    public void setNahrungsmittel(String nahrungsmittel) {
        int id = getResources().getIdentifier(nahrungsmittel,"drawable","com.example.pzehnder.diabetesbuddy");
        this.nahrungsmittel.setImageResource(id);
    }

    /**
     * SetBeAntwort1 erlaubt es dem Container eine neue Antwort 1 zu übergeben
     * @param beAntwort1
     */
    public void setBeAntwort1ButtonText (String beAntwort1)
    {
        this.beAntwort1Button.setText(beAntwort1);
    }
    /**
     * SetBeAntwort2 erlaubt es dem Container eine neue Antwort 2 zu übergeben
     * @param beAntwort2
     */
    public void setBeAntwort2ButtonText (String beAntwort2)
    {
        this.beAntwort2Button.setText(beAntwort2);
    }
    /**
     * SetBeAntwort3 erlaubt es dem Container eine neue Antwort 3 zu übergeben
     * @param beAntwort3
     */
    public void setBeAntwort3ButtonText (String beAntwort3)
    {
        this.beAntwort3Button.setText(beAntwort3);
    }
}
