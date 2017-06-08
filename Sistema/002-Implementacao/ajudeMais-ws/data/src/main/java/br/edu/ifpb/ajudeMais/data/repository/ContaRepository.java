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
 * <p>
 * {@link ContaRepository}
 * </p>
 * 
 * <p>
 * Classe utilizada para acesso a dados relacionados a uma {@link Conta}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface ContaRepository extends JpaRepository<Conta, Long> {

	/**
	 * Método busca uma conta pelo username e pelo seu estado=ativo/inativo
	 * 
	 * @param username
	 *            nome de usuário
	 */
	Optional<Conta> findOneByUsernameAndAtivo(String username, Boolean ativo);

	/**
	 * 
	 * <p>
	 * Método busca uma conta pelo nome de usuário
	 * </p>
	 * 
	 * @param username
	 *            nome do usuário
	 * @return Optional de uma conta
	 */
	Optional<Conta> findOneByUsername(String username);

	/**
	 * Busca uma conta por email
	 * 
	 * @param email
	 *            - email a ser pesquisado
	 * @return um Optional de uma conta
	 */
	Optional<Conta> findOneByEmail(String email);

}
