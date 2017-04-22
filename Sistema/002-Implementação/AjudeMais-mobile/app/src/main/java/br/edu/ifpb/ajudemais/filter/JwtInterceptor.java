package br.edu.ifpb.ajudemais.filter;

import android.content.Context;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;
import java.util.Arrays;

import br.edu.ifpb.ajudemais.storage.SharedPrefManager;

/**
 * Created by Franck on 4/22/17.
 */

public class JwtInterceptor implements ClientHttpRequestInterceptor {

    private Context context;

    public JwtInterceptor(Context context) {
        this.context = context;
    }

    /**
     *
     * @param request
     * @param body
     * @param execution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {

        String token = SharedPrefManager.getInstance(this.context).getToken();
        HttpRequest wrapper = new HttpRequestWrapper(request);

        if(token != null) {
            wrapper.getHeaders().set("Authorization", token);
        }

        wrapper.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        wrapper.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return execution.execute(wrapper, body);
    }
}
