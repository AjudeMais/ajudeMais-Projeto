package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.maps.dto.LatLng;

/**
 * 
 * <p>
 * {@link CampanhaService}
 * </p>
 * 
 * <p>
 * Classe utilizada para definição de operações de campanha.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface CampanhaService extends Service<Campanha, Long> {

	/**
	 * Busca campanhas por instituição
	 * 
	 * @param instituicaoCaridade
	 * @return
	 */
	List<Campanha> findByInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade);

	/**
	 * 
	 * <p>
	 * Busca campanha por localização da instituição.
	 * </p>
	 * 
	 * @param latLng
	 * @return
	 * @throws AjudeMaisException 
	 */
	List<Campanha> filterByInstituicaoLocal(LatLng latLng) throws AjudeMaisException;
	
	/**
	 * 
	 * <p>
	 * Busca campanhas por stattus
	 * </p>
	 * @param status
	 * @return
	 */
	List<Campanha> findByStatus(boolean status);

	List<Campanha> findByInstituicaoCaridadeIdAndStatus(Long id);

}
