package br.edu.ifpb.ajudemais.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by rafaelfeitosa on 14/04/17.
 * Classe responsável por lidar com permisões de acesso do aplicativo.
 */

public class PrefsUtils {

    /**
     *
     * @param context
     * @return
     */
    public static boolean isCheckPushOn(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("PREF_CHECK_PUSH", false);
    }
}
