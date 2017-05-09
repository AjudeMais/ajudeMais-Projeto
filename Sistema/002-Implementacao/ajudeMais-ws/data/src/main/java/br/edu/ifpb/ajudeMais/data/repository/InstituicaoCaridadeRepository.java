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
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Repository
public interface InstituicaoCaridadeRepository extends JpaRepository<InstituicaoCaridade, Long>{
	
	/**
	 * 
	 * @param documento
	 * @return
	 */
	Optional<InstituicaoCaridade> findOneByDocumento(String documento);
	
	/**
	 * 
	 * <p>
	 * </p>
	 * @param conta
	 * @return
	 */
	Optional<InstituicaoCaridade> findOneByConta(Conta conta);
	
	/**
	 * 
	 * @param localidade
	 * @param uf
	 * @return
	 */
    public List<InstituicaoCaridade> filtersInstituicaoCaridadeClose(@Param("localidade") String localidade, @Param("uf") String uf);


}
