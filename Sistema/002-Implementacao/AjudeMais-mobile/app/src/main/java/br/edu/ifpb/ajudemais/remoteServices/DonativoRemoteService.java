package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;

import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.Donativo;

/**
 * <p>
 * <b>{@link DonativoRemoteService}</b>
 * </p>
 * <p>
 * <p>
 * Acessa serviços Rest relacionados a Donativo
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class DonativoRemoteService extends AbstractRemoteService {



    /**
     * construtor
     *
     * @param context
     */
    public DonativoRemoteService(Context context) {
        super(context);
    }

    /**
     * Salva via requisão post Http um novo Donativo no banco.
     * @param donativo
     * @return
     */
    public Donativo saveDoador(Donativo donativo) {
        donativo = restTemplate.postForObject(API + "/donativo", donativo, Donativo.class);
        return donativo;
    }
}

