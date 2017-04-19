package br.edu.ifpb.ajudemais.remoteServices;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import br.edu.ifpb.ajudemais.domain.Conta;
import br.edu.ifpb.ajudemais.domain.JwtToken;
import br.edu.ifpb.ajudemais.exceptions.RemoteAccessErrorException;
import br.edu.ifpb.ajudemais.handler.MyResponseErrorHandler;

/**
 * Created by rafaelfeitosa on 12/04/17.
 * Classe que fornece serviços relacionados a autenticação do usuário no app.
 */

public class AuthRemoteService {

    private static final String URL = "http://192.168.0.106:8080/auth/login";
    private static final String URL2 = "http://192.168.0.106:8080/auth/user";
    private RestTemplate restTemplate;

    public AuthRemoteService() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setErrorHandler(new MyResponseErrorHandler());
    }


    /**
     * Autentica usuário no sistema.
     *
     * @param conta
     * @return
     */
    public JwtToken createAuthenticationToken(Conta conta){
        return restTemplate.postForObject(URL, conta, JwtToken.class);
    }

    public Conta getUsuario(String token){

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        final HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
        final ResponseEntity<Conta> responseEntity = restTemplate.exchange(URL2, HttpMethod.GET, httpEntity, Conta.class);

       return responseEntity.getBody();
    }
}
