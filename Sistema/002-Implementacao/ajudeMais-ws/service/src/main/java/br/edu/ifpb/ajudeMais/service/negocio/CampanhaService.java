package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;


public interface CampanhaService extends Service<Campanha, Long>{
	
	/**
	 * Busca campanhas por instituição
	 * @param instituicaoCaridade
	 * @return
	 */
	List<Campanha> findByInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade);

}
