package br.edu.ifpb.ajudemais.remoteServices;

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
 * Created by rafaelfeitosa on 10/04/17.
 * Faz comunicação com API RestFul para os serviços relacionandos a doador.
 */

public class DoadorRemoteService {


    private static final String URL = "http://192.168.0.106:8080/doador";
    private RestTemplate restTemplate;

    public DoadorRemoteService() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setErrorHandler(new MyResponseErrorHandler());

    }


    /**
     * Salva via requisão post Http um novo doador no banco.
     *
     * @param doador
     * @return
     */
    public Doador saveDoador(Doador doador){
        doador = restTemplate.postForObject(URL, doador, Doador.class);
        return doador;
    }



}