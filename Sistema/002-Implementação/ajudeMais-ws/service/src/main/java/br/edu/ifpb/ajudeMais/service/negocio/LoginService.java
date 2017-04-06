package br.edu.ifpb.ajudeMais.service.negocio;

import br.edu.ifpb.ajudeMais.domain.entity.Usuario;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
public interface LoginService {
	
	/**
	 * 
	 * @param login
	 * @param senha
	 * @return
	 */
	Usuario efetuarLogin(String login, String senha);

}
