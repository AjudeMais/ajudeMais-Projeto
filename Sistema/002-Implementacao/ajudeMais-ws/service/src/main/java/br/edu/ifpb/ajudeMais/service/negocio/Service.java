package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 * @param <T>
 * @param <K>
 */
public interface Service<T, K> {

	/**
	 * 
	 * @param entity
	 * @return
	 */
	T save(T entity) throws AjudeMaisException;

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws AjudeMaisException
	 */
	T update(T entity) throws AjudeMaisException;

	/**
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * 
	 * @param id
	 * @return
	 */
	T findById(K id);

	/**
	 * 
	 * @param entity
	 */
	void remover(T entity);
}
