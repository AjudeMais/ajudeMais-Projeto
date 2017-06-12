
/**
 * 
 * <p>
 * <b> DonativoCampanhaServiceImpl.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.service.negocio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ajudeMais.data.repository.DonativoCampanhaRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoCampanhaService;

/**
 * 
 * <p>
 * <b> {@link DonativoCampanhaServiceImpl}</b>
 * </p>
 *
 * <p>
 * Serviços para donativos relacionados a uma campanha.
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@Service
public class DonativoCampanhaServiceImpl implements DonativoCampanhaService{

	/**
	 * 
	 */
	@Autowired
	DonativoCampanhaRepository donativoCampanhaRespository;
	
	/**
	 * Busca todos os donativos doados para um campanha com base em seu ID.
	 */
	@Override
	public List<Donativo> findDonativoByCampanhaId(Long id) {
		List<Donativo> donativos = donativoCampanhaRespository.findDonativoByCampanhaId(id);
		return donativos;
	}

}
