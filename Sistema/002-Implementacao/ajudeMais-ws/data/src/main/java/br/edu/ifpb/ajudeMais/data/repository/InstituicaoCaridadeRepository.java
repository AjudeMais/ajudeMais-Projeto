package br.edu.ifpb.ajudeMais.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
	 * @param localidade
	 * @param uf
	 * @return
	 */
    public List<InstituicaoCaridade> filtersInstituicaoCaridadeClose(@Param("localidade") String localidade, @Param("uf") String uf);


}
