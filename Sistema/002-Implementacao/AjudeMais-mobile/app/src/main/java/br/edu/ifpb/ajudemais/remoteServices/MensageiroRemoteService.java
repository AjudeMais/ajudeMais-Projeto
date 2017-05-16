package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;

import br.edu.ifpb.ajudemais.domain.Mensageiro;

/**
 * <p>
 * <b>{@link MensageiroRemoteService}</b>
 * </p>
 * <p>
 * <p>
 * Faz comunicação com API RestFul para os serviços relacionados a mensageiro.
 * </p>
 *
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 */
public class MensageiroRemoteService extends AbstractRemoteService {

    /**
     * construtor
     *
     * @param context
     */
    public MensageiroRemoteService(Context context) {
        super(context);
    }


    /**
     *
     * Método que faz uma requisição HTTP na API e salva um mensageiro
     * no banco de dados da aplicação
     *
     * @param mensageiro
     *      mensageiro a ser salvo no banco
     *
     * @return
     *      mensageiro salvo
     *      
     */
    public Mensageiro saveMensageiro(Mensageiro mensageiro) {
        mensageiro = restTemplate.postForObject(API + "/mensageiro", mensageiro, Mensageiro.class);
        return mensageiro;
    }
}
