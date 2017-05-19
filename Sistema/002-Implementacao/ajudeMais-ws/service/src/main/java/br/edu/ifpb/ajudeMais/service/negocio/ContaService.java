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

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;

/**
 * 
 * <p>
 * {@link ContaService}
 * </p>
 * 
 * <p>
 * Interface para definição de operações de serviços de {@link Conta}.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface ContaService extends Service<Conta, Long> {

	/**
	 * 
	 * <p>
	 * Método responsável por alter a senha de um usuário existente
	 * </p>
	 * 
	 * @param password
	 *            - nova senha
	 */
	void changePassword(String password, String newPassword) throws AjudeMaisException;

	/**
	 * 
	 * <p>
	 * Método responsável por alter a senha de um usuário existente. Utilizado
	 * para primeiro acesso de usuários do módulo web.
	 * </p>
	 * 
	 * @param password
	 */
	void changePasswordInit(String password);

}
