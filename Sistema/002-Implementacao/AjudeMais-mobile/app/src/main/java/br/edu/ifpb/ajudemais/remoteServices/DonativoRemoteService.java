package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import br.edu.ifpb.ajudemais.domain.Donativo;
import br.edu.ifpb.ajudemais.domain.DonativoCampanha;

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
     *
     * @param donativo
     * @return
     */
    public Donativo saveDonativo(Donativo donativo) {
        donativo = restTemplate.postForObject(API + "/donativo", donativo, Donativo.class);
        return donativo;
    }

    /**
     * Salva DOnativo com campanha via requisão post Http um novo Donativo no banco.
     *
     * @param donativo
     * @return
     */
    public DonativoCampanha saveDonativoCampanha(DonativoCampanha donativo) {
        return restTemplate.postForObject(API + "/donativo/save/donativocampanha", donativo, DonativoCampanha.class);
    }

    /**
     * Atualiza informações do donativo
     *
     * @param donativo
     * @return
     */
    public Donativo updateDonativo(Donativo donativo) {
        HttpEntity<Donativo> requestUpdate = new HttpEntity<>(donativo);
        HttpEntity<Donativo> response = restTemplate.exchange(API + "/donativo", HttpMethod.PUT, requestUpdate, Donativo.class);
        return response.getBody();
    }


    /**
     * Busca donativos com base no nome do doador.
     *
     * @param doadorName
     * @return
     */
    public List<Donativo> findByDoadorNome(String doadorName) {
        ResponseEntity<Donativo[]> responseEntity = restTemplate.getForEntity(API + "/donativo/filter/doadorNome?doadorNome={doadorNome}", Donativo[].class, doadorName);

        return Arrays.asList(responseEntity.getBody());
    }

    /**
     * Busca donativos com base no Id doador passado
     *
     * @param IdDoador
     * @return
     */
    public List<Donativo> findByDoadorId(Long IdDoador) {
        ResponseEntity<Donativo[]> responseEntity = restTemplate.getForEntity(API + "/donativo/filter/{id}", Donativo[].class, IdDoador);

        return Arrays.asList(responseEntity.getBody());
    }

    /**
     * Busca DonativoCampanha com base no Id donativo passado
     *
     * @param idDonativo
     * @return
     */
    public DonativoCampanha findDonativoCampanhaByDonativoId(Long idDonativo) {

        ResponseEntity<DonativoCampanha> responseEntity = restTemplate.getForEntity(API + "/donativo/filter/donativocampanha/{id}", DonativoCampanha.class, idDonativo);

        return responseEntity.getBody();
    }
}

