package br.edu.ifpb.ajudeMais.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
/**
 * 
 * Interface de percistecia para {@link Campanha}
 * @author elson
 *
 */
public interface CampanhaRepository extends JpaRepository<Campanha, Long> {
	
	/**
	 * Busca campanhas por instituição
	 * @param instituicaoCaridade
	 * @return
	 */
	List<Campanha> findByInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade);

}
