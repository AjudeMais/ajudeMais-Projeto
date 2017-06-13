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

import br.edu.ifpb.ajudeMais.domain.entity.Doador;

/**
 * 
 * <p>
 * <b> DoadorService </b>
 * </p>
 * 
 * <p>
 * Interface para definição de operações de serviços de {@link Doador}.
 * </p>
 *
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
public interface DoadorService extends Service<Doador, Long> {

	/**
	 * 
	 * <p>
	 * Busca Daodor por conta, filtrando por username
	 * </p>
	 * 
	 * @param username
	 *            nome de usuário a ser buscado;
	 * @return Doador encontrado.
	 */
	Doador findByContaUsername(String username);
	
}
