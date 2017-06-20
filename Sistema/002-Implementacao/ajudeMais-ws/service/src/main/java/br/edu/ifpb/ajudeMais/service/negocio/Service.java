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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;

/**
 * 
 * <p>
 * {@link Service}
 * </p>
 * 
 * <p>
 * Classe utilizada para definição de métodos comuns implementados na camada de
 * serviço.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 * @param <T>
 *            - Tipo de da Classe
 * @param <K>
 *            - chave primaria (ID)
 */
public interface Service<T, K> {

	/**
	 * 
	 * <p>
	 * Salvar uma entidade
	 * </p>
	 * 
	 * @param entity
	 * @return
	 * @throws AjudeMaisException
	 */
	T save(@Valid @NotNull T entity) throws AjudeMaisException;

	/**
	 * 
	 * <p>
	 * Atualizar entidade existente
	 * </p>
	 * 
	 * @param entity
	 * @return
	 * @throws AjudeMaisException
	 */
	T update(@Valid @NotNull T entity) throws AjudeMaisException;

	/**
	 * 
	 * <p>
	 * Buscar todas entidades
	 * </p>
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * 
	 * <p>
	 * Buscar por ID
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	T findById(@NotNull K id);

	/**
	 * 
	 * <p>
	 * Remover uma entidade existente
	 * </p>
	 * 
	 * @param entity
	 */
	void remover(@NotNull T entity) throws AjudeMaisException;
}
