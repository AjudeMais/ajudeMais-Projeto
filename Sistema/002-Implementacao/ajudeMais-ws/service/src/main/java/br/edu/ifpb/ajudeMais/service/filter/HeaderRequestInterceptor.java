package br.edu.ifpb.ajudeMais.service.filter;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

/**
 * 
 * <p>
 * {@link HeaderRequestInterceptor}
 * </p>
 * 
 * <p>
 * Filtro utilizado para adição de informações em cabeçalho de uma requição
 * HTTP.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

	/**
	 * 
	 */
	private final String headerName;

	/**
	 * 
	 */
	private final String headerValue;

	/**
	 * @param headerName
	 * @param headerValue
	 */
	public HeaderRequestInterceptor(String headerName, String headerValue) {
		this.headerName = headerName;
		this.headerValue = headerValue;
	}

	/**
	 * 
	 */
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		HttpRequest wrapper = new HttpRequestWrapper(request);
		wrapper.getHeaders().set(headerName, headerValue);
		return execution.execute(wrapper, body);

	}

}
