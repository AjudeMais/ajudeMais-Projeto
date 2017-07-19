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

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * <p>
 * {@link InstituicaoCaridadeRepository}
 * </p>
 * 
 * <p>
 * Classe utilizada para acesso a dados no BD de uma {@link InstituicaoCaridade}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Repository
public interface InstituicaoCaridadeRepository extends JpaRepository<InstituicaoCaridade, Long> {

	/**
	 * 
	 * <p>
	 * Busca uma insituição filtrando pelo seu documento CPF ou CNPJ
	 * </p>
	 * 
	 * @param documento
	 *            CPF/CNPJ
	 * @return um Optional de Instituição
	 */
	Optional<InstituicaoCaridade> findOneByDocumento(String documento);

	/**
	 * 
	 * <p>
	 * Busca uma insituição por sua conta
	 * </p>
	 * 
	 * @param conta
	 *            a ser pesquisada
	 * @return um Optional de uma insituição
	 */
	Optional<InstituicaoCaridade> findOneByConta(Conta conta);
	
	
	
	/**
	 * 
	 * <p>
	 * Busca instituições  ativas pela localidade passada.
	 * </p>
	 * 
	 * @param localidade
	 *            localidade a ser pesquisada
	 * @param uf
	 *            estado a ser pesquisada
	 * @return lista de insituições
	 */
	public List<InstituicaoCaridade> filtersInstituicaoCaridadeClose(@Param("localidade") String localidade,
			@Param("uf") String uf);
	

	/**
	 * Lista todas instituições com a situação ativa ou inativa. 
	 * @return
	 */
	public List<InstituicaoCaridade> findByContaAtivo(boolean ativo);
	
}
