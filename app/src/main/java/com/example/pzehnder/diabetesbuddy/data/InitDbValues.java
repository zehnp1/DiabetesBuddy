package com.example.pzehnder.diabetesbuddy.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.pzehnder.diabetesbuddy.activitys.Login;

/**
 * Created by Ivan on 03.12.2014.
 */
public class InitDbValues {
    private static DatabaseHandler dbHandler;
    public InitDbValues()
    {}
    public static void init(Context context)
    {
        dbHandler = Login.getDb();
        dbHandler.open();
        dbHandler.insertShopData("sonnenbrille", 17);
        dbHandler.insertShopData("board",20);
        dbHandler.insertShopData("zufall",30);
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
        dbHandler.insertBeData("apfel", "0 BE", "1 BE", "2 BE", 2, "BE");
        dbHandler.insertBeData("schokokuchen","1-2 BE","3-4 BE","5-6 BE",2,"BE");
        dbHandler.insertBeData("pizza","2-4 BE","5-7 BE","8-10 BE",3,"BE");
        dbHandler.close();
    }
}
