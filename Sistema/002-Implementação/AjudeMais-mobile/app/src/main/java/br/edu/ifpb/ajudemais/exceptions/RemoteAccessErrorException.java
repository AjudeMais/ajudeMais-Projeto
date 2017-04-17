package br.edu.ifpb.ajudemais.exceptions;

import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * <p>
 * <b>{@link RemoteAccessErrorException}</b>
 * </p>
 * <p>
 * <p>
 * Exception para erros ao acessar serviços do WS ajude mais.
 * </p>
 *
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */


public class RemoteAccessErrorException extends IOException {

    private HttpStatus statusCode;

    private String body;

    public RemoteAccessErrorException(String msg) {
        super(msg);
    }

    public RemoteAccessErrorException(HttpStatus statusCode, String body, String msg) {
        super(msg);
        this.statusCode = statusCode;
        this.body = body;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
