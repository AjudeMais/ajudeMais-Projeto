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
package br.edu.ifpb.ajudeMais.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;

/**
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 *
 */
public interface ContaRepository extends JpaRepository<Conta, Long>{
	
	/**
	 * 
	 * @param username
	 */
	Optional<Conta> findOneByUsernameAndAtivo(String username, Boolean ativo);
	
	/**
	 * 
	 * <p>
	 * </p>
	 * @param username
	 * @return
	 */
	Optional<Conta> findOneByUsername(String username);
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	Optional<Conta> findOneByEmail(String email);

}
