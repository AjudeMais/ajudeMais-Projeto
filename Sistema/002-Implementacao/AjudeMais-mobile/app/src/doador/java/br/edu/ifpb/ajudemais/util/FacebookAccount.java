package br.edu.ifpb.ajudemais.util;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Doador;

/**
 * Created by amsv on 26/04/17.
 */

public class FacebookAccount {

    private static String email;

    public static Doador userFacebookData(LoginResult loginResult) {
        Profile profile = Profile.getCurrentProfile();
        String facebookId = profile.getId();
        String nome = profile.getName();
        String username = nome.toLowerCase().trim();
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            email = object.getString("email");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email");
        request.setParameters(parameters);
        request.executeAsync();

        List<String> grupos = new ArrayList<>();
        grupos.add("ROLE_DOADOR");

        return new Doador(nome, facebookId, new Conta(username, email, grupos));
    }
}
