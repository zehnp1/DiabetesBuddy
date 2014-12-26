package com.example.pzehnder.diabetesbuddy.components;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pzehnder.diabetesbuddy.activitys.Login;
import com.example.pzehnder.diabetesbuddy.R;
import com.example.pzehnder.diabetesbuddy.activitys.Shop;
import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;

/**
 * Log:
 * Erstellt von Ivan Wissler 29.11.2014
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Die Klasse ShopCompWidget bildet den benutzerdefinierten Container für die Shop elemente
 */
public class ShopCompWidget extends LinearLayout {

    private ShopCompWidget shopComp;
    private String articleName;
    private TextView priceText;
    private ImageView articleImage;
    private String price;
    private String picture;
    public ShopCompWidget(Context context)
    {
        super(context);
    }
    public ShopCompWidget(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        //Dem Component wird das im shop_component definierte Layout übertragen
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.shop_component, this,true);
        setUpViewItems();
        shopComp = this;
    }

    private void setUpViewItems()
    {
        //Die Component Elemente für den Preis, das Bild und den Kaufen Button werden definiert
        priceText = (TextView) findViewById(R.id.price);
        articleImage =(ImageView) findViewById(R.id.imageArticle);

       //Der Kaufen Button öffnet einen Dialog der anzeig ob man genügend bananen hat um das element zu kaufen
        Button btn = (Button)findViewById(R.id.kaufen);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Datenbank wird geladen
                DatabaseHandler db = Login.getDb();
                //Datenbank wird geöffnet
                db.open();
                //Der Dialog zum anzeigen ob man genügend Bananen hat wird Instanziert
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                //Der Ok Button des Dialogs schliesst den diealog
                Button button = (Button)dialog.findViewById(R.id.dialogButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Dialog wird geschlossen
                        dialog.hide();
                    }
                });
                TextView tittle = (TextView)dialog.findViewById(R.id.dialogTitle);
                ImageView image = (ImageView)dialog.findViewById(R.id.dialogImage);
                TextView dialogText = (TextView)dialog.findViewById(R.id.dialogText);
               //Es wird überprüft ob man genügend Bananen hat
                if(Login.bananas >= Integer.parseInt(price)) {
                    //Wen Ja wird der Dialog entsprechend Dargestellt
                    tittle.setText("Glückwunsch!");
                    int id = getResources().getIdentifier(articleName, "drawable", "com.example.pzehnder.diabetesbuddy");
                    image.setImageResource(id);
                    dialogText.setText("Du hast gekauft: " + articleName);
                    db.insertUser_ShopData(Login.user,articleName);
                    shopComp.setVisability(false);
                    //Der Artikel wird gekauft und die Kosten an Banenen werden von den Bananen abgezogen
                    Login.bananas = Login.bananas - Integer.parseInt(price);
                    Shop.setBananas(Login.bananas);
                    //Die neue Antzahl an Bananen wird in die Datanbank gespeichert
                    db.updateUserBanana(Login.bananas,Login.user);
                }
                else
                {
                    //Falls der user nicht gengend Bananen besitzt wird der Dialog entsprechentd angezeigt
                    tittle.setText("Sorry!");
                    image.setImageResource(R.drawable.buddy_sad);
                    dialogText.setText("Du hast nicht genügend Bananen");
                }
                //Der Dialog wird angezeigt
                dialog.show();
                //Die Datenbank wird geschlossen
                db.close();
            }
        });
    }

    /**
     * SetPriceTex erlaub es dem Container einen Preis zu übergeben
     * @param priceText
     */
    public void setPriceText(String priceText) {
        this.price = priceText;
        this.priceText.setText(priceText);
    }

    /**
     * SetArticleImage erlaubt es dem Container ein Bild des Artikels zu übergeben
     * @param articleName
     */
    public void setArticleImage(String articleName) {
        this.articleName = articleName;
        int id = getResources().getIdentifier(articleName,"drawable","com.example.pzehnder.diabetesbuddy");
        this.articleImage.setImageResource(id);
    }

    /**
     * Set Visability erlaubt es die Sichtbarkeit des Componenten zu verändern
     * @param visability
     */
    public void setVisability(boolean visability)
    {
        if(visability)
        {
            this.setVisibility(VISIBLE);
        }
        else
        {
            this.setVisibility(GONE);
        }
    }
}
