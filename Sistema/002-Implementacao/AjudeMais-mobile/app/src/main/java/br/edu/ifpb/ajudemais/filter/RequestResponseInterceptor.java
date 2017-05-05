package br.edu.ifpb.ajudemais.filter;

import android.content.Context;

import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;

import br.edu.ifpb.ajudemais.storage.SharedPrefManager;

/**
 * <p>
 * <b>RequestResponseInterceptor</b>
 * </p>
 * <p>
 * <p>
 *
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public class RequestResponseInterceptor implements ClientHttpRequestInterceptor {

    private Context context;

    public RequestResponseInterceptor(Context context) {
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

        interceptRequest(request, body);

        ClientHttpResponse clientHttpResponse = execution.execute(request, body);

        interceptResponse(clientHttpResponse);

        return clientHttpResponse;
    }

    /**
     *
     * @param response
     */
    private void interceptResponse(ClientHttpResponse response) {
        String currentToken = response.getHeaders().getAuthorization();

        if(currentToken != null || !(StringUtils.isEmpty(currentToken))) {
            SharedPrefManager.getInstance(context).storeToken(currentToken);
        }
    }

    /**
     *
     * @param request
     * @param body
     */
    private void interceptRequest(HttpRequest request, byte[] body) {

        String token = SharedPrefManager.getInstance(this.context).getToken();
        HttpRequest wrapper = new HttpRequestWrapper(request);

        if(token != null) {
            wrapper.getHeaders().set("Authorization", token);
        }

        wrapper.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        wrapper.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    }
}
