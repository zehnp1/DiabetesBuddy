package com.example.pzehnder.diabetesbuddy.components;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pzehnder.diabetesbuddy.R;

/**
 * Log:
 * Erstellt von Ivan Wissler 19.12.2014
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Die Klasse SgarCompWidget bildet den benutzerdefinierten Container zum einfügen der Blutzuckerwerte.
 */
public class SugarCompWidget extends LinearLayout{
    private TextView time;
    private TextView bz;
    private TextView be;
    private TextView basal;
    private TextView bolus;
    private ImageView note;
    private LinearLayout layout;
    public SugarCompWidget(Context context)
    {
        super(context);
    }
    public SugarCompWidget(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //Dem Component wird das im blutzucker_component definierte Layout übertragen
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.blutzucker_component, this,true);
        setUpViewItems();
        setVisibility(GONE);
    }
    private void setUpViewItems()
    {
        //Die Component Elemente für Die Zeit, den Blutzucker, den BEWert,den basalWert, den bolusWert,
        //eine Notiz und den Hintergrund werden definiert.

        time = (TextView)findViewById(R.id.sugarComp_time);
        bz = (TextView)findViewById(R.id.sugarComp_bz);
        be = (TextView)findViewById(R.id.sugarComp_be);
        basal = (TextView)findViewById(R.id.sugarComp_basal);
        bolus = (TextView)findViewById(R.id.sugarComp_bolus);
        note = (ImageView)findViewById(R.id.sugarComp_note);
        layout = (LinearLayout)findViewById(R.id.sugarComp);
    }

    /**
     * setTime erlaubt es die darzustellende Zeit dem Container zu übergenben
     * @param time
     */
    public void setTime(String time) {
        this.time.setText(time);
    }

    /**
     * setBz erlaubt es den Blutzuckerwert dem Container zu übergeben
     * @param bz
     */
    public void setBz(String bz)
    {
        this.bz.setText(bz);
    }

    /**
     * setBe erlaubt es den BrotEinheitwert dem Container zu übergeben
     * @param be
     */
    public void setBe(String be){
        this.be.setText(be);
    }

    /**
     * setBasal erlaubt es den Basalwert dem Container zu übergeben
     * @param basal
     */
    public void setBasal(String basal)
    {
        this.basal.setText(basal);
    }

    /**
     * setBolus erlaubt es den Boluswert dem Cotainer zu übergeben
     * @param bolus
     */
    public void setBolus(String bolus)
    {
        this.bolus.setText(bolus);
    }

    /**
     * setVisibility eralubt es die Sichtbarkeit des Containers zu verändern
     * @param visibility
     */
    public void setVisibility(boolean visibility)
    {
        if(visibility)
        {
            this.setVisibility(VISIBLE);
        }
        else
        {
            this.setVisibility(INVISIBLE);
        }

    }

    /**
     * setBgColr erlaubt es die Hintergrundfarbe des Containers zu verändern
     * @param color
     */
    public void setBgColor(String color)
    {
        layout.setBackgroundColor(Color.parseColor(color));
    }
}
