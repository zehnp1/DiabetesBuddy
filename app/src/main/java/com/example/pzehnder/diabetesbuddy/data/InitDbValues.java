package com.example.pzehnder.diabetesbuddy.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.pzehnder.diabetesbuddy.activitys.Login;

/**
 * Log:
 * Erstellt von Ivan Wissler 03.12.2014
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Die Klasse InitDBVales führt alle Datenbankeinträge aus die beim erstmaligen Starten der Applikation
 * ausgeführt wrden müssen
 */
public class InitDbValues {
    private static DatabaseHandler dbHandler;
    public InitDbValues()
    {}
    public static void init(Context context)
    {
        //Lädt Datenbank
        dbHandler = Login.getDb();
        //öffnet Datenbank
        dbHandler.open();
        //Einfügen der Shop Elemente in Datenbank
        dbHandler.insertShopData("sonnenbrille", 17);
        dbHandler.insertShopData("board",20);
        dbHandler.insertShopData("zufall",30);

        //Einfügen der Basis Quizfragen in die Datenbank
        dbHandler.insertQuizData(
                "Wie heissen die Zellhaufen die Insulin produzieren?"
                , "Lagerhans-Inslen"
                , "Hasall-Körperchen"
                , "Aschoff-Geipel-Riesenzellen"
                , "Meissnersche Tastkörper"
                , 1, "Deutsch");
        dbHandler.insertQuizData(
                "Insulin senkt den Zuckergehalt im Blut. Wie aber heißt das Hormon, das genau das Gegenteil bewirkt?"
                ,"Östrogen"
                ,"Adrenalin"
                ,"Glykogen"
                ,"Glukagon"
                ,4,"Deutsch");
        dbHandler.insertQuizData(
                "Welches Organ unseres Körpers kann sich nur von Zucker ernähren??"
                ,"die Leber"
                ,"das Gehirn"
                ,"die Muskeln"
                ,"das Fettgeweber"
                ,2,"Deutsch");

        //Einfügen der Basis BEfragen in die Datenbank
        dbHandler.insertBeData("apfel", "0 BE", "1 BE", "2 BE", 2, "BE");
        dbHandler.insertBeData("schokokuchen","1-2 BE","3-4 BE","5-6 BE",2,"BE");
        dbHandler.insertBeData("pizza","2-4 BE","5-7 BE","8-10 BE",3,"BE");
       //Schliessen der Datenbank
        dbHandler.close();
    }
}
