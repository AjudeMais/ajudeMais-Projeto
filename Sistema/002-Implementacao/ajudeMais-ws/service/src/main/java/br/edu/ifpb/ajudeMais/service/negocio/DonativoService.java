package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.List;

import org.springframework.data.repository.query.Param;

import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;

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
 * @author rafaelfeitosa
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
	 * Busca e retorna todos os donativos doados por um determinado de acordo com id do Doador passado
	 * 
	 * @param idDoador
	 * @return
	 */
	List<Donativo> findByDoadorId(Long idDoador);

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
	
	/**
	 * <p>
	 * Busca donativos com estado passado e id da instituicao passada
	 * </p>
	 * 
	 * @return lista de donativos
	 */ 
	List<Donativo> filterDonativoByEstadoAndInstituicao(@Param("idInstituicao") Long idInstitucao, @Param("estado") Estado estado);
}
