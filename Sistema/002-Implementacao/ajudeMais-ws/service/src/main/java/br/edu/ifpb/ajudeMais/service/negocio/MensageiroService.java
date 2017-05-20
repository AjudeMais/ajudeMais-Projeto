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

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;

/**
 * 
 * <p>
 * <b> {@link MensageiroService} </b>
 * </p>
 * 
 * Interface para definição de operações de serviços de {@link Mensageiro}.
 *
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public interface MensageiroService extends Service<Mensageiro, Long> {

	/**
	 * Busca todos os mensageiros e seu endereço que é proximo a localidade do
	 * endereço passado
	 * 
	 * @param logradouro
	 * @param bairro
	 * @param localidade
	 * @param uf
	 * @return
	 */
	List<Object[]> filtersMensageiroCloser(String logradouro, String bairro, String localidade, String uf);

	/**
	 * 
	 * <p>
	 * Busca um mensageiro por conta, filtrando por email.
	 * </p>
	 * 
	 * @param email
	 * @return
	 */
	List<Mensageiro> findByContaEmail(String email);
	
	/**
	 * 
	 * <p>
	 * Busca Mensageiro por conta, filtrando por username
	 * </p>
	 * 
	 * @param username
	 *            nome de usuário a ser buscado;
	 * @return Mensageiro encontrado.
	 */
	Mensageiro findByContaUsername(String username);

}
