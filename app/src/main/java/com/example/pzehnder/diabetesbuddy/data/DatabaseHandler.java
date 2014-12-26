package com.example.pzehnder.diabetesbuddy.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLData;

/**
 * Log:
 * Erstellt von Ivan Wissler 30.11.2014
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Die DatabaseHandler Klasse erlaubt es eine Datenbank zu erstellen und zu laden.
 * Es werden alle Tabellen definiert und erstellt.
 * Es werden alle methoden zu verfügung gestellt um einträge aus den Tabellen zu lesen und zu schreiben.
 */
public class DatabaseHandler
{
    //Datenbank Name und Version
    private static final String DATABASE_NAME = "diabetsbuddy_db";
    private static final int DATABASE_VERSION = 1;

    //shop Tabelle
    private static final String SHOP_TABLE = "shop";
        private final static String ARTICLE_NAME = "article_name";
        private final static String ARTICLE_PRICE = "price";

    //user Tabelle
    private static final String USER_TABLE = "user";
        private final static String USER_NAME = "user_name";
        private final static String NAME = "name";
        private final static String VORNAME = "vorname";
        private final static String MAIL = "mail";
        private final static String PASSWORD = "password";
        private final static String WEIGHT = "weight";
        private final static String BIRTHDATE = "birthdate";
        private final static String GENDER = "gender";
        private final static String LANGUAGE = "language";
        private final static String BANANAS = "bananas";
        private final static String TELFON ="telefon";

    //userShop Tabelle
    private static final String USER_SHOP_TABLE = "user_shop";
        //Zwischentabelle mit shop.article_name und user.user_name

    //quiz Tabelle
    private static final String QUIZ_TABLE = "quiz";
        private final static String QUIZ_FRAGE = "quiz_frage";
        private final static String QUIZ_ANTWORT1 = "quiz_antwort1";
        private final static String QUIZ_ANTWORT2 = "quiz_antwort2";
        private final static String QUIZ_ANTWORT3 = "quiz_antwort3";
        private final static String QUIZ_ANTWORT4 = "quiz_antwort4";
        private final static String QUIZ_CORRECT = "quiz_correct";
        private final static String QUIZ_LANGUAGE = "quiz_language";

    //be_shätzen Tabelle
    private static final String BE_TABLE = "be";
    private final static String NAHRUNGSMITTEL = "nahrungsmittel";
    private final static String BE_ANTWORT1 = "be_antwort1";
    private final static String BE_ANTWORT2 = "be_antwort2";
    private final static String BE_ANTWORT3 = "be_antwort3";
    private final static String BE_CORRECT = "be_correct";
    private final static String BE_EINHEIT = "einheit";

    //Blutzucke Werte Tabelle
    private static final String BS_VALUES = "bs_values";
    private final static String BS_DATE = "bs_date";
    private final static String BS_TIME = "bs_time";
    private final static String BS_VALUE = "bs_value";
    private final static String BS_BEVALUE = "bs_bevalue";
    private final static String BS_BOLUS = "bolus";
    private final static String BS_BASAL = "basal";
    private final static String BS_NOTE = "bs_note";
    private final static String BS_USER = "bs_user";


    DatabaseHelper dbHelper;
    Context context;
    SQLiteDatabase db;
    public DatabaseHandler(Context context)
    {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            //Erstellen der Shop Tabelle
            String crateShopTable = "CREATE TABLE IF NOT EXISTS " + SHOP_TABLE + " ("
                    + ARTICLE_NAME + " TEXT  PRIMARY KEY,"
                    + ARTICLE_PRICE + " INTEGER)";

            //Erstellen der User Shop Tabelle
            String crateUser_ShopTable = "CREATE TABLE IF NOT EXISTS " + USER_SHOP_TABLE + " ("
                    + ARTICLE_NAME + " TEXT,"
                    + USER_NAME + " TEXT, PRIMARY KEY("+ARTICLE_NAME+", "+USER_NAME+"))";

            //Erstellen der User Tabelle
            String crateUserTable = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + " ("
                    + USER_NAME + " TEXT PRIMARY KEY,"
                    + NAME + " TEXT, "
                    + VORNAME + " TEXT, "
                    + MAIL + " TEXT, "
                    + PASSWORD + " TEXT, "
                    + WEIGHT + " REAL, "
                    + BIRTHDATE + " TEXT, "
                    + GENDER + " TEXT, "
                    + LANGUAGE + " TEXT, "
                    + TELFON + " TEXT, "
                    + BANANAS + " INTEGER"
                    + ")";

            //Erstellen der Qizz Tabelle
            String crateQuizTable = "CREATE TABLE IF NOT EXISTS " + QUIZ_TABLE + " ("
                    + QUIZ_FRAGE + " TEXT PRIMARY KEY,"
                    + QUIZ_ANTWORT1 + " TEXT, "
                    + QUIZ_ANTWORT2 + " TEXT, "
                    + QUIZ_ANTWORT3 + " TEXT, "
                    + QUIZ_ANTWORT4 + " TEXT, "
                    + QUIZ_CORRECT + " INTEGER, "
                    + QUIZ_LANGUAGE + " TEXT"
                    + ")";

            //Erstellen der BE Tabelle
            String crateBeTable = "CREATE TABLE IF NOT EXISTS " + BE_TABLE + " ("
                    + NAHRUNGSMITTEL + " TEXT PRIMARY KEY,"
                    + BE_ANTWORT1 + " TEXT, "
                    + BE_ANTWORT2 + " TEXT, "
                    + BE_ANTWORT3 + " TEXT, "
                    + BE_CORRECT + " INTEGER, "
                    + BE_EINHEIT + " TEXT"
                    + ")";

            //Erstellen der Blutzuckerwerte Tabelle
            String crateBs_valuesTable = "CREATE TABLE IF NOT EXISTS " + BS_VALUES + " ("
                    + BS_DATE + " TEXT,"
                    + BS_TIME + " TEXT,"
                    + BS_VALUE + " REAL,"
                    + BS_BEVALUE + " INTEGER,"
                    + BS_BASAL + " INTEGER,"
                    + BS_BOLUS+ " INTEGER,"
                    + BS_NOTE + " TEXT,"
                    + BS_USER + " TEXT, PRIMARY KEY("+BS_DATE+", "+BS_TIME+", "+BS_USER+"))";

            try
            {
                //Ausführen der Create Querys
                db.execSQL(crateShopTable);
                db.execSQL(crateUser_ShopTable);
                db.execSQL(crateUserTable);
                db.execSQL(crateQuizTable);
                db.execSQL(crateBeTable);
                db.execSQL(crateBs_valuesTable);
            }
            catch (Exception e)
            {
                Log.d("Table Create Error",e.toString());
                e.printStackTrace();
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + SHOP_TABLE);
            onCreate(db);
        }
    }

    /**
     * Erlaubt es die Datenbank zu öffnen
     * @return
     */
    public DatabaseHandler open()
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * schliesst die Datenbank
     */
    public void close()
    {
        dbHelper.close();
    }

    /**
     * Erlaubt es werte in die Shop Tabelle zu schreiben
     * @param name
     * @param price
     * @return
     */
    public long insertShopData(String name, int price)
    {
        ContentValues content = new ContentValues();
        content.put(ARTICLE_NAME,name);
        content.put(ARTICLE_PRICE,price);
        try{
            return db.insertOrThrow(SHOP_TABLE,null,content);
        }
        catch (Exception e)
        {
            Log.d("DB insert Error",e.toString());
            return 0;
        }
    }

    /**
     * Eralubt es Werte in die User__Shop Tabelle zu schreiben
     * @param user_name
     * @param article_name
     * @return
     */
    public long insertUser_ShopData(String user_name, String article_name)
    {
        ContentValues content = new ContentValues();
        content.put(ARTICLE_NAME,article_name);
        content.put(USER_NAME,user_name);
        try{
            return db.insertOrThrow(USER_SHOP_TABLE,null,content);
        }
        catch (Exception e)
        {
            Log.d("DB insert Error",e.toString());
            return 0;
        }
    }

    /**
     * Erlaubt es Werte in die Quiz Tabelle zu schreiben
     * @param frage
     * @param antwort1
     * @param antwort2
     * @param antwort3
     * @param antwort4
     * @param correct
     * @param language
     * @return
     */
    public long insertQuizData(String frage, String antwort1, String antwort2, String antwort3, String antwort4, int correct, String language)
    {
        ContentValues content = new ContentValues();
        content.put(QUIZ_FRAGE,frage);
        content.put(QUIZ_ANTWORT1,antwort1);
        content.put(QUIZ_ANTWORT2,antwort2);
        content.put(QUIZ_ANTWORT3,antwort3);
        content.put(QUIZ_ANTWORT4,antwort4);
        content.put(QUIZ_CORRECT,correct);
        content.put(QUIZ_LANGUAGE,language);
        try{
            return db.insertOrThrow(QUIZ_TABLE,null,content);
        }
        catch (Exception e)
        {
            Log.d("DB insert Error",e.toString());
            return 0;
        }
    }

    /**
     * Erlaubt es Werte in die Be Tablle zu schreiben
     * @param nahrungsmittel
     * @param antwort1
     * @param antwort2
     * @param antwort3
     * @param correct
     * @param einheit
     * @return
     */
    public long insertBeData(String nahrungsmittel, String antwort1, String antwort2, String antwort3, int correct, String einheit)
    {
        ContentValues content = new ContentValues();
        content.put(NAHRUNGSMITTEL,nahrungsmittel);
        content.put(BE_ANTWORT1,antwort1);
        content.put(BE_ANTWORT2,antwort2);
        content.put(BE_ANTWORT3,antwort3);
        content.put(BE_CORRECT,correct);
        content.put(BE_EINHEIT,einheit);
        try{
            return db.insertOrThrow(BE_TABLE,null,content);
        }
        catch (Exception e)
        {
            Log.d("DB insert Error",e.toString());
            return 0;
        }
    }

    /**
     * Erlaubt es Werte in die User Tabelle zu schreiben
     * @param user_name
     * @param name
     * @param vorname
     * @param mail
     * @param password
     * @param weight
     * @param birthDate
     * @param gender
     * @param telefon
     * @return
     */
    public long insertUserData(String user_name, String name, String vorname, String mail, String password, Float weight, String birthDate, String gender,String telefon)
    {
        ContentValues content = new ContentValues();
        content.put(USER_NAME,user_name);
        content.put(NAME,name);
        content.put(VORNAME,vorname);
        content.put(MAIL,mail);
        content.put(PASSWORD,password);
        content.put(WEIGHT,weight);
        content.put(BIRTHDATE,birthDate);
        content.put(GENDER,gender);
        content.put(LANGUAGE,"Deutsch");
        content.put(TELFON, telefon);
        content.put(BANANAS,0);
        try{
            return db.insertOrThrow(USER_TABLE,null,content);
        }
        catch (Exception e)
        {
            Log.d("DB insert Error",e.toString());
            return 0;
        }
    }

    /**
     * Erlaubt es die Anzahl der Bananen in der Usertabelle zu verändern
     * @param bananas
     * @param user_name
     */
    public void updateUserBanana(int bananas,String user_name)
    {
        try {

            db.execSQL("UPDATE user SET bananas =? WHERE user_name=?",new Object[]{bananas,user_name});
        }
        catch (Exception e)
        {
            Log.d("DB update Error",e.toString());
        }
    }

    /**
     * Erlaubt es Werte in die Blutzuckerwerte Tabelle zu schreiben
     * @param date
     * @param time
     * @param bs_value
     * @param be_value
     * @param basal
     * @param bolus
     * @param note
     * @param user
     * @return
     */
    public long insertBs_valuesData(String date, String time,Float bs_value, Integer be_value, Integer basal, Integer bolus, String note, String user )
    {
        ContentValues content = new ContentValues();
        content.put(BS_DATE,date);
        content.put(BS_TIME,time);
        content.put(BS_VALUE,bs_value);
        content.put(BS_BEVALUE,be_value);
        content.put(BS_BASAL,basal);
        content.put(BS_BOLUS,bolus);
        content.put(BS_NOTE,note);
        content.put(BS_USER,user);
        try{
            return db.insertOrThrow(BS_VALUES,null,content);
        }
        catch (Exception e)
        {
            Log.d("DB insert Error",e.toString());
            return 0;
        }
    }

    /**
     * Gibt Alle Shop elemente Zurück, mit der Information ob der Angegebene user diese bereits
     * gekauft hat oder nicht
     * @param user
     * @return
     */
    public Cursor returnShopData(String user)
    {
        final String MY_QUERY = "SELECT * FROM shop LEFT JOIN user_shop ON user_shop.user_name =? AND shop.article_name = user_shop.article_name ";
        return db.rawQuery(MY_QUERY,new String[]{user});
    }

    /**
     * Gibt alle Informationen aus der User Tabelle zum angegebenen User zurück
     * @param user
     * @return
     */
    public Cursor returnUserData(String user)
    {
        final String MY_QUERY = "SELECT * FROM user WHERE user_name =?";
        return db.rawQuery(MY_QUERY,new String[]{user});
    }

    /**
     * Gibt alle Quizfragen mit der Angegebenen Sprache zurück
     * @param language
     * @return
     */
    public Cursor returnQuizData(String language)
    {
        final String MY_QUERY = "SELECT * FROM quiz WHERE quiz_language =?";
        return  db.rawQuery(MY_QUERY,new String[]{language});
    }

    /**
     * Gibt alle BE Fragen mit der Angegebenen einheit zurück
     * @param einheit
     * @return
     */
    public Cursor returnBeData(String einheit)
    {
        final String MY_QUERY = "SELECT * FROM be WHERE einheit =?";
        return  db.rawQuery(MY_QUERY,new String[]{einheit});
    }

    /**
     * Gibt alle Blutzuckerwerte des Angegebenen Datums und des Angegebenen users zurück
     * @param date
     * @param user
     * @return
     */
    public Cursor returnBsValuesData(String date, String user)
    {
        final String MY_QUERY = "SELECT * FROM bs_values WHERE bs_date =? AND bs_user =? ORDER BY bs_time DESC";
        return  db.rawQuery(MY_QUERY,new String[]{date,user});
    }

}
