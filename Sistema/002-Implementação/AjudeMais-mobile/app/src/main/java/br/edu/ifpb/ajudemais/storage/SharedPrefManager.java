package br.edu.ifpb.ajudemais.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Grupo;

/**
 * Classe utilitaria para armazenamento em preferências do app.
 *
 * Created by Franck Aragão
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "authPrefs";

    private static final String KEY_ACCESS_TOKEN = "authToken";
    private static final  String USER_SESSION_USERNAME = "userSessionUsername";
    private static final  String USER_SESSION_MAIL = "userSessionUsername";

    private static Context context;

    private static SharedPrefManager instance;

    /**
     * construtor padrão
     *
     * @param context
     */
    public SharedPrefManager(Context context){
        this.context = context;
    }

    /**
     *
     * @param context
     * @return
     */
    public static  synchronized SharedPrefManager getInstance(Context context) {
        if(instance == null)
            instance = new SharedPrefManager(context);
        return instance;

    }

    /**
     * Salva token no arquivo de configuraçeõs do app.
     *
     * @param token
     * @return
     */
    public boolean storeToken(String token) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.apply();
        Log.d("LOGIN", "guardando token.." + token);
        return true;
    }

    /**
     *
     * @param conta
     * @return
     */
    public boolean storeUser(Conta conta) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_SESSION_USERNAME, conta.getUsername());
        editor.putString(USER_SESSION_MAIL, conta.getEmail());
        editor.apply();
        Log.d("LOGIN", "guardando user..");
        return true;
    }

    /**
     * Obtem token
     *
     * @return token salvo nas preferencias do app.
     */
    public String getToken() {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    /**
     *
     * @return
     */
    public Conta getUser() {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(USER_SESSION_USERNAME, null);
        String mail = sharedPreferences.getString(USER_SESSION_MAIL, null);

        Conta conta = new Conta();
        conta.setUsername(username);
        conta.setEmail(mail);
        return conta;
    }

    /**
     *
     */
    public void clearSharedPrefs() {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
