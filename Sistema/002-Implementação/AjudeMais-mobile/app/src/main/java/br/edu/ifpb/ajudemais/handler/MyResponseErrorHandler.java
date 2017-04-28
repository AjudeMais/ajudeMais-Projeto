package br.edu.ifpb.ajudemais.handler;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

import br.edu.ifpb.ajudemais.dto.MessageErrorDTO;
import br.edu.ifpb.ajudemais.exceptions.RemoteAccessErrorException;

/**
 * <p>
 * <b>{@link MyResponseErrorHandler}</b>
 * </p>
 * <p>
 * <p>
 * Handler ReponseError Spring
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class MyResponseErrorHandler implements ResponseErrorHandler {


    private ResponseErrorHandler myErrorHandler = new DefaultResponseErrorHandler();

    public boolean hasError(ClientHttpResponse response) throws IOException {
        return myErrorHandler.hasError(response);
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        String body = IOUtils.toString(response.getBody()).replace("[", "").replace("]", "");

        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            body = "Nome de usuário ou senha inválido";
        }
        throw new RestClientException(body);
    }

}
