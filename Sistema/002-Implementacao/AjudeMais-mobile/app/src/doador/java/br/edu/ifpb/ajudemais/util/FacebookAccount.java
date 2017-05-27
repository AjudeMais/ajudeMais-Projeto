package br.edu.ifpb.ajudemais.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import br.edu.ifpb.ajudemais.activities.CreateAccountHelperActivity;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.Grupo;

/**
 * Created by amsv on 26/04/17.
 */

public class FacebookAccount {

    private static Bitmap picBitMap;
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
                            doador.setNome(Profile.getCurrentProfile().getName());
                            doador.setFacebookID(Profile.getCurrentProfile().getId());
                            doador.setCampanhas(null);
                            doador.getConta().setEmail(object.optString("email"));
                            doador.getConta().setUsername(object.optString("email"));
                            doador.getConta().setSenha(Profile.getCurrentProfile().getId());
                            doador.getConta().setGrupos(Collections.singletonList(Grupo.DOADOR.toString()));
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

    /**
     * Pega a foto do perfil do usuário no facebook, após o mesmo ter se autenticado
     *
     * @return
     *      bitmap contendo a foto do perfil
     */
    public static Bitmap getProfilePictureUser() {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                AccessToken.getCurrentAccessToken().getUserId()+"/picture?type=large",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response.getJSONObject().has("picture")) {
                            JSONObject object = response.getJSONObject();
                            try {
                                String profilePicURL = object.getJSONObject("picture").getJSONObject("data").getString("url");

                                URL imageURL = new URL(profilePicURL);

                                HttpURLConnection connection = (HttpURLConnection) imageURL
                                        .openConnection();
                                connection.setDoInput(true);
                                connection.connect();

                                InputStream input = connection.getInputStream();
                                picBitMap = BitmapFactory.decodeStream(input);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
        ).executeAsync();

        return picBitMap;
    }

    private static void goToFacebookAccountHelperActivity(Activity activity, Doador doador) {
        Intent intent = new Intent(activity, CreateAccountHelperActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Doador", doador);
        activity.startActivity(intent);
    }
}