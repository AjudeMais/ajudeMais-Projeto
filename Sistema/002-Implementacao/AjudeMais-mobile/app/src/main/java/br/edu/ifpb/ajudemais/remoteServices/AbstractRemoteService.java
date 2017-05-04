package br.edu.ifpb.ajudemais.remoteServices;

import android.content.Context;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ajudemais.filter.RequestResponseInterceptor;
import br.edu.ifpb.ajudemais.handler.MyResponseErrorHandler;

/**
 * Created by Franck on 4/22/17.
 */

public abstract class AbstractRemoteService {

    protected static final String API = "http://192.168.0.106:8080";
    protected RestTemplate restTemplate;
    protected Context context;

    /**
     *
     * @param context
     */
    public AbstractRemoteService(Context context) {
        this.context = context;
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setErrorHandler(new MyResponseErrorHandler());
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new RequestResponseInterceptor(context));
        restTemplate.setInterceptors(interceptors);
    }
}
