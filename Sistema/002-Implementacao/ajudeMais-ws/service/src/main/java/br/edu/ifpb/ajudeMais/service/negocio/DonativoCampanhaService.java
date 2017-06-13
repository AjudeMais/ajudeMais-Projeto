
/**
 * 
 * <p>
 * <b> DonativoCampanhaService.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;

/**
 * 
 * <p>
 * <b>{@link DonativoCampanhaService} </b>
 * </p>
 *
 * <p>
 * Interface para service de donativo doado para uma campanha
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public interface DonativoCampanhaService {
	
	/**
	 * Busca os donativos da camapanha com id passado.
	 * @param id
	 * @return
	 */
	List<Donativo> findDonativoByCampanhaId(Long id);


	/**
	 * Busca todos os donativos doados para um campanha com o estado estado depois de aceito.
	 * @param id
	 * @return
	 */
	List<DonativoCampanha> filterDonativoByEstadoAfterAceito(Long id);

}
