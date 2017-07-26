/**
 * Ajude Mais - Módulo Web Service
 * 
 * Sistema para potencializar o processo de doação.
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * <p>
 * {@link CategoriaRepository}
 * </p>
 * 
 * <p>
 * Interface de pesistencia para {@link Categoria}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author Elson / Franck
 *
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	/**
	 * 
	 * <p>
	 * Busca itens doaveis por sua localidade
	 * </p>
	 * 
	 * @param instituicaoCaridade
	 * @return
	 */
	List<Categoria> findByInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade);

	/**
	 * <p>
	 * Busca itens doaveis ativos ou inativos da instituição com ID passado.
	 * </p>
	 * 
	 * @param idInstituicao
	 * @return
	 */
	public List<Categoria> findByAtivoAndInstituicaoCaridadeId(Boolean ativo, Long id);

	/**
	 * 
	 * <p>
	 * Busca as categorias de uma instituição filtrando por nome.
	 * </p>
	 * 
	 * @param instituicao
	 * @param nome
	 * @return
	 */
	List<Categoria> findByInstituicaoCaridadeAndNomeIgnoreCaseContaining(InstituicaoCaridade instituicao, String nome);

	/**
	 * 
	 * @param instituicaoCaridade
	 * @param ativo
	 * @return
	 */
	Long countByInstituicaoCaridadeAndAtivo(InstituicaoCaridade instituicaoCaridade, boolean ativo);

}
