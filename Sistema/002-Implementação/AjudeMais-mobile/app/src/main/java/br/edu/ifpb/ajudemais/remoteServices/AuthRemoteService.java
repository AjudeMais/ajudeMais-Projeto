package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;

import org.springframework.http.ResponseEntity;

import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.JwtToken;
import br.edu.ifpb.ajudemais.storage.SharedPrefManager;

/**
 * Classe que fornece serviços relacionados a autenticação do usuário no app.
 *
 * @author Rafael / Franck
 */

public class AuthRemoteService extends AbstractRemoteService{


    /**
     * @param context
     */
    public AuthRemoteService(Context context) {
        super(context);
    }

    /**
     *
     * @param conta
     * @return
     */
    public Conta createAuthenticationToken(Conta conta){
        JwtToken token = restTemplate.postForObject(API+"/auth/login", conta, JwtToken.class);
        if(token != null) {
            storageToken(token.getToken());
        }
        return getUser();
    }

    /**
     *
     * @return
     */
    public Conta getUser(){
        final ResponseEntity<Conta> responseEntity = restTemplate.getForEntity(API+"/auth/user", Conta.class);
        Conta conta = responseEntity.getBody();
        if (conta != null) {
            SharedPrefManager.getInstance(context).storeUser(conta);
        }
        return conta;

    }

    /**
     *
     * @return
     */
    public Boolean isAuth() {
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(API+"/auth/valida", Boolean.class);
        Boolean isValid = responseEntity.getBody();
        if(isValid == null) {
            return false;
        }
        return isValid;
    }

    /**
     *
     * @return
     */
    public boolean logged() {
        if(SharedPrefManager.getInstance(context).getToken() == null) {
            return false;
        } else if(SharedPrefManager.getInstance(context).getUser() == null) {
            return false;

        }else if(!isAuth()) {
            return false;
        }
        return true;
    }

    private void storageToken(String token) {
        SharedPrefManager.getInstance(context).storeToken(token);
    }

}
