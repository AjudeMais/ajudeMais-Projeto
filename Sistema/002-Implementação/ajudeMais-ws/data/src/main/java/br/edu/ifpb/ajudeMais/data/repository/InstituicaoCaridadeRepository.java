package br.edu.ifpb.ajudeMais.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Repository
public interface InstituicaoCaridadeRepository extends JpaRepository<InstituicaoCaridade, Long>{
	
	

}
