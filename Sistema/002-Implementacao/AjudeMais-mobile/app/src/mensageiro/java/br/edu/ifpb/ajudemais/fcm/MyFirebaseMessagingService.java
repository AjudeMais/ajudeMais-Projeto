package br.edu.ifpb.ajudemais.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import br.edu.ifpb.ajudemais.R;
import br.edu.ifpb.ajudemais.activities.MainActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Intent resultIntent;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        getDataNotification(remoteMessage);
    }


    public void getDataNotification(RemoteMessage remoteMessage) {

        String tipoAs = remoteMessage.getData().get("tipo");
        String idAs = remoteMessage.getData().get("id");
        Long id = Long.parseLong(idAs);

        switch (tipoAs) {
            case "CAMPANHA":

                break;
            case "DOACAO":

                break;
            default:
                executeToDo(remoteMessage);
                break;
        }
    }

    /**
     * Componente de notificação
     *
     * @param remoteMessage
     */
    private void notifyComponent(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
        long[] v = {500, 500};
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_doador));
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setContentTitle(remoteMessage.getNotification().getTitle());
        mBuilder.setContentText(remoteMessage.getNotification().getBody());
        mBuilder.setVibrate(v);
        mBuilder.setSound(uri);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }

    /**
     * Recupera campanha de notificação.
     *
     * @param remoteMessage
     */
    private void executeToDo(final RemoteMessage remoteMessage) {
        resultIntent = new Intent(getBaseContext(), MainActivity.class);
        //resultIntent.putExtra("campanha", campanha);
        notifyComponent(remoteMessage);
    }
}
