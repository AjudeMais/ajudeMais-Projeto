package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.Doador;
import br.edu.ifpb.ajudemais.exceptions.RemoteAccessErrorException;
import br.edu.ifpb.ajudemais.handler.MyResponseErrorHandler;


/**
 * <p>
 * <b>{@link DoadorRemoteService}</b>
 * </p>
 * <p>
 * <p>
 * Faz comunicação com API RestFul para os serviços relacionandos a doador.
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