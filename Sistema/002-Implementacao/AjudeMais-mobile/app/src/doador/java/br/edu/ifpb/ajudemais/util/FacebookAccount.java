package br.edu.ifpb.ajudemais.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Grupo;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;

/**
 * Created by amsv on 26/04/17.
 */

public class FacebookAccount {

    private static String email = "";
    private static String username = "";
    private static Conta conta = new Conta();
    private static Bitmap picBitMap;

    /**
     * Método responsável por obter dados de acesso de um usuário do facebook.
     * Dados como: Nome, e-mail e username
     * @return
     *          Um objeto do tipo conta
     */
    public static Conta userFacebookData(LoginResult loginResult, final Context context) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            if(object != null){
                                email = object.optString("email");
                                username = email;
                                conta.setUsername(username);
                                conta.setSenha(username);
                                conta.setEmail(email);
                                conta.setGrupos(Collections.singletonList(Grupo.DOADOR.toString()));
                                SharedPrefManager.getInstance(context).storeUser(conta);
                            }
                            else {
                                System.out.println("json vazio");
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
        return conta;
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
}
