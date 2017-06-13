
/**
 * 
 * <p>
 * <b> DonativoCampanhaRepository.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;

/**
 * 
 * <p>
 * <b> {@link DonativoCampanhaRepository} </b>
 * </p>
 *
 * <p>
 * Entidade para lidar com donativos doados para um campanha
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public interface DonativoCampanhaRepository extends JpaRepository<DonativoCampanha, Long>{
	
	/**
	 * Busca os donativos da camapanha com id passado.
	 * @param id
	 * @return
	 */
	List<Donativo> findDonativoByCampanhaId(Long id);
	
	/**
	 * 
	 * <p>
	 * Busca os donativos com estado depois de aceito que estão ativas numa camapanha com id passado 
	 * </p>
	 * 
	 * @param idCampanha
	 * @param uf
	 * @return
	 */
	List<DonativoCampanha> filterDonativoByEstadoAfterAceito(@Param("idcampanha") Long idCampanha);


}
