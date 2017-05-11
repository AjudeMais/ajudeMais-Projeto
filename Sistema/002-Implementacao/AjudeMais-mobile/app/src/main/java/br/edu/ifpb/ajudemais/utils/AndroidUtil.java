package br.edu.ifpb.ajudemais.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * <p>
 * <b>{@link AndroidUtil}</b>
 * </p>
 * <p>
 * Classe com alguns métodos utilitários.
 * </p>
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class AndroidUtil {

    private Context context;

    public AndroidUtil(Context context){
        this.context = context;
    }


    /**
     * Verifica se o device possui alguma conexão com a internet.
     * @return
     */
    public boolean isOnline() {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("AJUDEMAIS", e.getMessage());
        }
        return connected;
    }

}
