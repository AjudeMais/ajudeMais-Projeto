package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

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
	T save(T entity);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	T update(T entity);
	
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
