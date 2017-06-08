package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import br.edu.ifpb.ajudemais.domain.Doador;


/**
 * <p>
 * <b>{@link DoadorRemoteService}</b>
 * </p>
 * <p>
 * <p>
 * Faz comunicação com API RestFul para os serviços relacionados a doador.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

public class DoadorRemoteService extends AbstractRemoteService {


    /**
     * @param context
     */
    public DoadorRemoteService(Context context) {
        super(context);
    }

    /**
     * Salva via requisão post Http um novo doador no banco.
     *
     * @param doador
     * @return
     */
    public Doador saveDoador(Doador doador) {
        doador = restTemplate.postForObject(API + "/doador", doador, Doador.class);
        return doador;
    }


    /**
     * Atualiza informações do Doador
     * @param doador
     * @return
     */
    public Doador updateDoador(Doador doador){
        HttpEntity<Doador> requestUpdate = new HttpEntity<>(doador);
        HttpEntity<Doador> response = restTemplate.exchange(API + "/doador", HttpMethod.PUT, requestUpdate, Doador.class);
        return response.getBody();
    }

    /**
     * Recupera Doador pelo username de sua conta.
     *
     * @param username
     * @return
     */
    public Doador getDoador(String username) {
        return restTemplate.getForObject(API + "/doador/filter/username?username={username}", Doador.class, username);
    }
}