package br.edu.ifpb.ajudemais.fcm;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.edu.ifpb.ajudemais.storage.SharedPrefManager;


/**
 * <p>
 * <b>MyFirebaseInstanceIDService</b>
 * </p>
 * Classe responsável por obter token da API do Firebase.
 * <p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    /**
     *
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        storageToken(refreshedToken);
    }

    /**
     * Storage token refreshed in shared prefs
     *
     * @param token
     */
    private void storageToken(String token) {
        SharedPrefManager.getInstance(getApplicationContext()).storageFcmToken(token);
    }



}
