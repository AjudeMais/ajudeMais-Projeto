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

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * <p>
 * {@link CategoriaService}
 * </p>
 * 
 * <p>
 * Interface para definição de operações de serviços de {@link Categoria}.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface CategoriaService extends Service<Categoria, Long> {

	/**
	 * Busca categoria por instituição de caridade
	 * <p>
	 * </p>
	 * 
	 * @param instituicaoCaridade
	 * @return
	 */
	List<Categoria> findByInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade);

	/**
	 * <p>
	 * Busca todas as categorias ativas ou inativas de uma instituição
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	List<Categoria> findByAtivoAndInstituicaoCaridadeId(Boolean ativo, Long id);

	/**
	 * 
	 * <p>
	 * Busca categorias de uma instituição, filtrando pelo nome
	 * </p>
	 * 
	 * @param instituicaoCaridade
	 * @param nome
	 * @return
	 */
	List<Categoria> findByInstituicaoCaridadeAndNome(InstituicaoCaridade instituicaoCaridade, String nome);

}