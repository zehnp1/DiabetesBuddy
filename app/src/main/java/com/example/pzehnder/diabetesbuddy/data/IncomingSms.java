package com.example.pzehnder.diabetesbuddy.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.pzehnder.diabetesbuddy.activitys.Blutzucker;
import com.example.pzehnder.diabetesbuddy.activitys.Login;

import java.util.ArrayList;

/**
 * Log:
 * Erstellt von Ivan Wissler 24.12.2014
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Reagiert auf empfangen eines SMS der Bezugsperson mit dem Codewort "Zucker" und snedet alle gespeicherten Werte des
 * aktuellen Tages zurück
 */
public class IncomingSms extends BroadcastReceiver {

    // Instanzieren des SMS Manager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent)
    {

        final Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    //Speichert sender Nummer aus dem Intent
                    String senderNum = phoneNumber;
                    //Speichert Nachricht aus dem Intent
                    String message = currentMessage.getDisplayMessageBody();

                    //überprüft ob die Nummer der Bezugsperson entspricht und die Nachricht das Codewort
                    //"Zucker" ist.
                    if(phoneNumber.contains(Login.phonenumber)&&message.equals("Zucker"))
                    {
                        //Wenn Ja werden die Blutzuckerwerte des Tages aus der Datenbank gelsesen
                        ArrayList<String[]> values = Blutzucker.getValues();
                       //Nachricht der Tageswerte wird zusammengestellt
                        String valueString = "";
                        for (String[] entry:values) {
                            valueString = valueString + "Zeit:" + entry[0] +"\n";
                            valueString = valueString + "BZ:" + entry[1] +"\n";
                            valueString = valueString + "BE:" + entry[2] +"\n";
                            valueString = valueString + "Basal:" + entry[3] +"\n";
                            valueString = valueString + "Bolus:" + entry[4] +"\n";
                        }
                            //Nachricht der Tageswerte wird gesendet
                           sendSMS(Login.phonenumber, "Werte von "+Login.user+":\n"+valueString);
                    }
                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
    private void sendSMS(String phoneNumber, String message)
    {
        //Senden einer SMS and die Numer: phoneNumber mit der Nachricht:Message
        SmsManager sms = SmsManager.getDefault();
        //Wenn die Nachricht aller Blutzuckerwerte grösser ist als die Maximal länge eines SMS
        //wird die Nachricht in mehrere Nachrichten aufgeteilt
        ArrayList<String> messages = sms.divideMessage(message);
        for(String entry:messages) {
            sms.sendTextMessage(phoneNumber, null, entry, null, null);
        }
    }
}
