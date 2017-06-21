
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;

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
	 * 
	 * <p>
	 * Salvar uma DonativoCampanha
	 * </p>
	 * 
	 * @param entity
	 * @return
	 * @throws AjudeMaisException
	 */
	DonativoCampanha save(@Valid @NotNull DonativoCampanha entity) throws AjudeMaisException;
	
	/**
	 * Busca os donativos da camapanha com id passado.
	 * @param id
	 * @return
	 */
	List<DonativoCampanha> findByCampanhaId(Long id);
	
	/**
	 * Busca DonativoCampanha com base no id do donativo.
	 * @param id
	 * @return
	 */
	public DonativoCampanha findByDonativoId(Long id);
	


	/**
	 * Busca todos os donativos doados para um campanha com o estado estado depois de aceito.
	 * @param id
	 * @return
	 */
	List<DonativoCampanha> filterDonativoByEstadoAfterAceito(Long id);

}
