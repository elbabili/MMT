package fr.mosquee_mirail_toulouse.mmt.FireBase;

/**
 * Created by moham on 13/10/2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.mosquee_mirail_toulouse.mmt.MainActivity;
import fr.mosquee_mirail_toulouse.mmt.R;

import static fr.mosquee_mirail_toulouse.mmt.MainActivity.mainDataBase;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "Android News App";
    private static final String TITLE = "Message urgent de votre Mosquée : ";
    private static final String MASJID = "MOSQUEE DU MIRAIL";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //It is optional
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        System.out.println("From: " + remoteMessage.getFrom());

        Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        System.out.println("Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    //This method is only generating push notification
    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

        int begin = messageBody.indexOf('/');
        //if(begin == -1) begin = messageBody.indexOf('\');
        int end = messageBody.indexOf('/',begin+1);
        String dte = "";
        if((begin > 0) && (end > 0) && (messageBody.length() > (end + 2))){   //si user a bien saisi la date alors on peut la récupérer
            dte = messageBody.substring(begin-2 , end+5);
            /*
            dte = messageBody.substring(begin-2 , end+2);
            //String century = dte.substring(end,2);
            if(century.equals("20") == false) { // cas d'une date pas complète sur l'année 17 au lieu de 2017
                String year = dte.substring(6,8);
                String y = "20" + year;
                StringBuilder sb = new StringBuilder(dte);
                sb.replace(6,8,y);
                dte = sb.substring(0,10);
            }*/
        }
        else {  // sinon on met la date du jour + 2 / message supprimé dans 48h
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_WEEK,2);
            date = cal.getTime();

            dte = formatter.format(date);
        }
        //insertion en base de cette nouvelle notification
        mainDataBase.insertNotif(messageBody,MASJID,dte);
    }
}