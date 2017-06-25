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
import br.edu.ifpb.ajudemais.activities.CampanhaActivity;
import br.edu.ifpb.ajudemais.asyncTasks.AsyncResponse;
import br.edu.ifpb.ajudemais.asyncTasks.GetCampanhaByIdTask;
import br.edu.ifpb.ajudemais.domain.Campanha;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Campanha campanha;
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
                executeLoadingCampanhaByIdTask(id, remoteMessage);
                break;
            case "DOACAO":

                break;
            default:

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
     * @param campanhaId
     * @param remoteMessage
     */
    private void executeLoadingCampanhaByIdTask(Long campanhaId, final RemoteMessage remoteMessage) {
        GetCampanhaByIdTask getCampanhaByIdTask = new GetCampanhaByIdTask(getApplicationContext(), campanhaId);
        getCampanhaByIdTask.delegate = new AsyncResponse<Campanha>() {
            @Override
            public void processFinish(Campanha output) {
                campanha = output;
                resultIntent = new Intent(getBaseContext(), CampanhaActivity.class);
                resultIntent.putExtra("campanha", campanha);
                notifyComponent(remoteMessage);
            }
        };
        getCampanhaByIdTask.execute();
    }
}
