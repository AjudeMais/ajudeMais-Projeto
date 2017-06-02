package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;

/**
 * 
 * 
 * <p>
 * <b> {@link DonativoService} </b>
 * </p>
 *
 * <p>
 * Interface para definição de operações de serviços de {@link Donativo}.
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
public interface DonativoService extends Service<Donativo, Long> {

	/**
	 * Busca um donativo pelo nome
	 * 
	 * @param nome
	 *            nome do donativo
	 * @return
	 */
	List<Donativo> findByNome(String nome);

	/**
	 * Busca e retorna todos os donativos doados por um determinado doador
	 * 
	 * @param nomeDoador
	 * @return
	 */
	List<Donativo> findByDoadorNome(String nomeDoador);

	/**
	 * 
	 * <p>
	 * Busca donativos por categoria, filtrando por instituição.
	 * </p>
	 * 
	 * @param instituicao
	 * @return
	 */
	List<Donativo> findByCategoriaInstituicaoCaridade(InstituicaoCaridade instituicao);
}
