package br.edu.ifpb.ajudemais.remoteServices;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.edu.ifpb.ajudemais.domain.Conta;

/**
 * Created by rafaelfeitosa on 10/04/17.
 * Faz comunicação com API RestFul para os serviços relacionandos a doador.
 */

public class DonorRemoteService {


    private static final String URL = "http://192.168.0.105:8080/doador";
    private RestTemplate restTemplate;

    public DonorRemoteService() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }


    /**
     * Salva via requisão post Http um novo doador no banco.
     *
     * @param doador
     * @return
     */
    public Conta saveDonor(Conta doador) {
        doador = restTemplate.postForObject(URL, doador, Conta.class);
        return doador;
    }
}
