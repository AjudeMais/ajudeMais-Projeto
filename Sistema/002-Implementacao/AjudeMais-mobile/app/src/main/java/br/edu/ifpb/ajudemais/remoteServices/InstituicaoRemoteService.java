package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;

import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.domain.InstituicaoCaridade;
import br.edu.ifpb.ajudemais.dto.LatLng;

/**
 * Created by Franck on 10/04/17.
 */

public class InstituicaoRemoteService extends AbstractRemoteService{


    /**
     * @param context
     */
    public InstituicaoRemoteService(Context context) {

        super(context);
    }

    public List<InstituicaoCaridade> getInstituicoes() {

        ResponseEntity<InstituicaoCaridade[]> responseEntity = restTemplate.getForEntity(API+"/instituicao", InstituicaoCaridade[].class);

        return Arrays.asList(responseEntity.getBody());
    }

    public List<InstituicaoCaridade> postInstituicoesForLocation(LatLng latLng) {

        ResponseEntity<InstituicaoCaridade[]> responseEntity = restTemplate.postForEntity(API+"/instituicao/filterGeoCoordinates", latLng, InstituicaoCaridade[].class);

        return Arrays.asList(responseEntity.getBody());
    }




    }