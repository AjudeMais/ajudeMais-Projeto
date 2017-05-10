package br.edu.ifpb.ajudemais.util;

import android.os.Bundle;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Collections;

import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.Grupo;

/**
 * Created by amsv on 26/04/17.
 */

public class FacebookAccount {

    private static String email = "";
    private static String nome = "";
    private static String username = "";
    private static String facebookId = "";
    private static Doador doador = new Doador();
    private static Conta conta = new Conta();

    /**
     * Método responsável por obter dados de acesso de um usuário do facebook.
     * Dados como: Nome, e-mail e username
     * @return
     *          Um objeto do tipo doador
     */
    public static Doador userFacebookData(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            if(object != null){
                                email = object.optString("email");
                                facebookId = object.optString("id");
                                nome = object.optString("name");
                                username = email;
                                doador.setNome(nome);
                                doador.setFacebookID(facebookId);
                                doador.setNomeUsuario(username);
                                doador.setTelefone("1212345-8989");
                                conta.setUsername(username);
                                conta.setSenha(username);
                                conta.setEmail(email);
                                conta.setGrupos(Collections.singletonList(Grupo.DOADOR.toString()));
                                doador.setConta(conta);

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
        return doador;
    }
}
