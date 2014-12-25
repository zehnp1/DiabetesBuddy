package com.example.pzehnder.diabetesbuddy.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.pzehnder.diabetesbuddy.activitys.BlutzuckerEssen;
import com.example.pzehnder.diabetesbuddy.activitys.Login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ivan on 24.12.2014.
 */
public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent)
    {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    if(phoneNumber.contains(Login.phonenumber)&&message.equals("Zucker"))
                    {
                        ArrayList<String[]> values = BlutzuckerEssen.getValues();
                        String valueString = "";
                        for (String[] entry:values) {
                            valueString = valueString + "Zeit:" + entry[0] +"\n";
                            valueString = valueString + "BZ:" + entry[1] +"\n";
                            valueString = valueString + "BE:" + entry[2] +"\n";
                            valueString = valueString + "Basal:" + entry[3] +"\n";
                            valueString = valueString + "Bolus:" + entry[4] +"\n";
                        }

                           sendSMS(Login.phonenumber, "Werte von "+Login.user+":\n"+valueString);

                    }
                    Log.d("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> messages = sms.divideMessage(message);
        for(String entry:messages) {
            sms.sendTextMessage(phoneNumber, null, entry, null, null);
        }
    }
}
