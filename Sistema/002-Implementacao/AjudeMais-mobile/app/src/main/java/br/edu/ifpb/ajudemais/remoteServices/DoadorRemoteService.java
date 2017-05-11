package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;

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

public class DoadorRemoteService extends AbstractRemoteService{


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
    public Doador saveDoador(Doador doador){
        doador = restTemplate.postForObject(API+"/doador", doador, Doador.class);
        return doador;
    }
}