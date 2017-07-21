
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
	 * Busca os donativosCampanha da camapanha com id passado.
	 * @param id
	 * @return
	 */
	List<DonativoCampanha> findByCampanhaId(Long id);
	
	
	/**
	 * Busca os donativosCampanha com base no donativo id passado.
	 * @param id
	 * @return
	 */
	DonativoCampanha findOneByDonativoId(Long id);
	
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

	/**
	 * 
	 * <p>
	 * Conta a quantidade de donativos com o estado depois de aceito que estão ativas numa camapanha com id e id da categoria passados 
	 * </p>
	 * 
	 * @param idCampanha
	 * @param uf
	 * @return
	 */
	Long filterCountByEstadoAndCategoriaAfterAceito(@Param("idcampanha") Long idCampanha, @Param("idCategoria") Long idCategoria);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Long countByDonativoCategoriaInstituicaoCaridadeId(Long id);
}
