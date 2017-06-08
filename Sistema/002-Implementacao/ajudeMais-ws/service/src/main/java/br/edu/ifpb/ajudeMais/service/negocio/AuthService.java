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
 * <p>
 * {@link AuthService}
 * </p>
 * 
 * <p>
 * interface para definição de operações de serviços de autenticação.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface AuthService {

	/**
	 * Cria autenticação para uma conta
	 * 
	 * @param conta
	 * @return
	 */
	JwtToken criaAutenticao(Conta conta, Device device);

	/**
	 * atualiza o token de acesso de um usuário
	 * 
	 * @return
	 */
	JwtToken atualizaAutenticacao(JwtToken tokenAtual);

	/**
	 * recupera conta de usuário de acordo com token
	 * 
	 * @param token
	 * @return
	 */
	Conta getContaPorToken(JwtToken token);

	/**
	 * Verifica autenticação de usuário
	 * 
	 * @param token
	 * @return
	 */
	Boolean autenticacaoValida(JwtToken token);

	/**
	 * 
	 * <p>
	 * Busca login de usuário
	 * </p>
	 * 
	 * @return
	 */
	String getCurrentUserLogin();

	/**
	 * 
	 * <p>
	 * recupera conta de usuário que faz requisição
	 * </p>
	 * 
	 * @return
	 */
	Conta getCurrentUser();

}
