package com.example.pzehnder.diabetesbuddy.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLData;

/**
 * Created by Ivan on 30.11.2014.
 */
public class DatabaseHandler
{
    //Database
    private static final String DATABASE_NAME = "diabetsbuddy_db";
    private static final int DATABASE_VERSION = 1;

    //shop Table
    private static final String SHOP_TABLE = "shop";
        private final static String ARTICLE_NAME = "article_name";
        private final static String ARTICLE_PRICE = "price";

    //user Table
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



    //userShop Table
    private static final String USER_SHOP_TABLE = "user_shop";
        //Zwischentabelle mit shop.article_name und user.user_name

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
            String crateShopTable = "CREATE TABLE IF NOT EXISTS " + SHOP_TABLE + " ("
                    + ARTICLE_NAME + " TEXT  PRIMARY KEY,"
                    + ARTICLE_PRICE + " INTEGER)";

            String crateUser_ShopTable = "CREATE TABLE IF NOT EXISTS " + USER_SHOP_TABLE + " ("
                    + ARTICLE_NAME + " TEXT,"
                    + USER_NAME + " TEXT, PRIMARY KEY("+ARTICLE_NAME+", "+USER_NAME+"))";

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
                    + BANANAS + " INTEGER"
                    + ")";

            Log.d("bal",crateUser_ShopTable);
            try
            {
                db.execSQL(crateShopTable);
                db.execSQL(crateUser_ShopTable);
                db.execSQL(crateUserTable);
            }
            catch (Exception e)
            {
                Log.d("bal","test");
                e.printStackTrace();
            }

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + SHOP_TABLE);
            onCreate(db);
        }
    }
    public DatabaseHandler open()
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        dbHelper.close();
    }
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

    public long insertUserData(String user_name, String name, String vorname, String mail, String password, Float weight, String birthDate, String gender)
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
    public Cursor returnShopData(String user)
    {
        final String MY_QUERY = "SELECT * FROM shop LEFT JOIN user_shop ON user_shop.user_name =? AND shop.article_name = user_shop.article_name ";
        return db.rawQuery(MY_QUERY,new String[]{user});
    }
    public Cursor returnUserData(String user)
    {
        final String MY_QUERY = "SELECT * FROM user WHERE user_name =?";
        return db.rawQuery(MY_QUERY,new String[]{user});
    }

}
