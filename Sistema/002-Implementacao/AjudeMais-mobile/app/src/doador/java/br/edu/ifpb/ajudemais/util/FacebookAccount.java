package br.edu.ifpb.ajudemais.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Collections;

import br.edu.ifpb.ajudemais.activities.CreateAccountHelperActivity;
import br.edu.ifpb.ajudemais.domain.Doador;

/**
 * Created by amsv on 26/04/17.
 */

public class FacebookAccount {

    private static Doador doador = new Doador();

    /**
     * Método responsável por obter dados de acesso de um usuário do facebook.
     * Dados como: Nome, e-mail e username
     * @return
     *          Um objeto do tipo doador
     */
    public static void userFacebookData(LoginResult loginResult, final Activity activity) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            doador = new Doador();
                            doador.setNome(Profile.getCurrentProfile().getName());
                            doador.setFacebookID(Profile.getCurrentProfile().getId());
                            doador.setCampanhas(null);
                            doador.getConta().setEmail(object.optString("email"));
                            doador.getConta().setUsername(Profile.getCurrentProfile().getId());
                            doador.getConta().setSenha(Profile.getCurrentProfile().getId());
                            doador.getConta().setGrupos(Collections.singletonList("ROLE_DOADOR"));
							doador.getConta().setAtivo(true);
                            if (doador != null) {
                                goToFacebookAccountHelperActivity(activity, doador);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private static void goToFacebookAccountHelperActivity(Activity activity, Doador doador) {
        Intent intent = new Intent(activity, CreateAccountHelperActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Doador", doador);
        activity.startActivity(intent);
    }
}
