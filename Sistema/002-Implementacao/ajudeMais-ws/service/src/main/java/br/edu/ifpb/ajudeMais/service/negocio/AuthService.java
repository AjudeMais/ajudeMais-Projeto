/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.service.negocio;

import org.springframework.mobile.device.Device;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.security.jwt.JwtToken;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
public interface AuthService {
	
	/**
	 * 
	 * @param conta
	 * @return
	 */
	JwtToken criaAutenticao(Conta conta, Device device);
	
	/**
	 * 
	 * @return
	 */
	JwtToken atualizaAutenticacao(JwtToken tokenAtual);
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	Conta getContaPorToken(JwtToken token);
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	Boolean autenticacaoValida(JwtToken token);
	
	/**
	 * 
	 * <p>
	 * </p>
	 * @return
	 */
	String getCurrentUserLogin(); 
	
	/**
	 * 
	 * <p>
	 * </p>
	 * @return
	 */
	Conta getCurrentUser();

}
