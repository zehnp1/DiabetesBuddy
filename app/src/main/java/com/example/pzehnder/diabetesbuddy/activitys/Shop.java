package com.example.pzehnder.diabetesbuddy.activitys;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.pzehnder.diabetesbuddy.R;
import com.example.pzehnder.diabetesbuddy.components.ShopCompWidget;
import com.example.pzehnder.diabetesbuddy.data.AsynchNetwork;
import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;

import java.util.ArrayList;

/**
 * Log:
 * Erstellt von Michael Heeb.
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * In der Shop Activity kann der User mit seinen in den Spielen gewonnenen Bananen Artikel kaufen.
 *
 */
public class Shop extends Activity {

    // Instanziert die drei im Shop vorhandnen Elemente. In Zukunft sollen diese dynamisch instanziert
    // werden. Dies funktioniert aber noch nicht.
    private ShopCompWidget shopCompWidget1;
    private ShopCompWidget shopCompWidget2;
    private ShopCompWidget shopCompWidget3;
    private DatabaseHandler dbHandler;
    private static TextView anzahlBanana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        ArrayList<String[]> shopList = new ArrayList<String[]>();

        //Instanziert das Feld wo die Anzahl vorhandnen Bananen angezeigt wird
        anzahlBanana = (TextView)findViewById(R.id.anzahlBananas);
        anzahlBanana.setText(Login.bananas + "");

        //Lädt Datenbank
        dbHandler = Login.getDb();
        //Öffnet Datenbank
        dbHandler.open();
        //Lädt die Informationen aus der Datenbank welche Artikel vom Eingeloggten user Noch nicht gekauft wurden.
        Cursor shopData = dbHandler.returnShopData(Login.user +"");
        if (shopData.moveToFirst()) {
            do {
                    shopList.add(new String[]{shopData.getString(0),shopData.getString(1),shopData.getString(2),shopData.getString(3)});
            } while (shopData.moveToNext());
        }
        //schliesst datenbank
        dbHandler.close();


        //Zeig den ersten Artikel aus der Datenbank an wenn dieser noch nicht gekauft wurde.
        shopCompWidget1 = (ShopCompWidget)findViewById(R.id.shopcomp1);
        shopCompWidget1.setPriceText(shopList.get(0)[1]);
        shopCompWidget1.setArticleImage(shopList.get(0)[0]);
        if(shopList.get(0)[2] != null)
        {
            //Verbirgt den Artikel falls dieser bereits gekauft wurde.
            shopCompWidget1.setVisability(false);
        }

        //Zeig den zweiten Artikel aus der Datenbank an wenn dieser noch nicht gekauft wurde.
        shopCompWidget2 = (ShopCompWidget)findViewById(R.id.shopcomp2);
        shopCompWidget2.setPriceText(shopList.get(1)[1]);
        shopCompWidget2.setArticleImage(shopList.get(1)[0]);
        if(shopList.get(1)[2]!= null)
        {
            //Verbirgt den Artikel falls dieser bereits gekauft wurde.
            shopCompWidget2.setVisability(false);
        }

        //Zeig den dritten Artikel aus der Datenbank an wenn dieser noch nicht gekauft wurde.
        shopCompWidget3 = (ShopCompWidget)findViewById(R.id.shopcomp3);
        shopCompWidget3.setPriceText(shopList.get(2)[1]);
        shopCompWidget3.setArticleImage(shopList.get(2)[0]);
        if(shopList.get(2)[2] != null)
        {
            //Verbirgt den Artikel falls dieser bereits gekauft wurde.
            shopCompWidget3.setVisability(false);
        }

    }

    /**
     * set Bananas erlaubt es den Anderen Klassen die anzeige der Anzahl Bananen zu verändern
     * @param bananas
     */
    public static void setBananas(int bananas)
    {
        anzahlBanana.setText(bananas + "");
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
