package br.edu.ifpb.ajudeMais.data.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;

/**
 * 
 * <p>
 * <b> {@link DonativoRepository}</b>
 * </p>
 *
 * <p>
 * Interface de persistência de um {@link Donativo}
 * </p>
 * 
 * @author Franck
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
public interface DonativoRepository extends JpaRepository<Donativo, Long> {

	/**
	 * 
	 * @param nome
	 * @return
	 */
	List<Donativo> findByDoadorNome(String nome);

	/**
	 * Busca os donativos do doador com o id informado.
	 * 
	 * @param nome
	 * @return
	 */
	List<Donativo> findByDoadorIdOrderByDataDesc(Long id);

	/**
	 * 
	 * <p>
	 * Busca os donativos filtrando pelo mensageiro e ordenando por data de
	 * criação.
	 * </p>
	 * 
	 * @param mensageiro
	 * @return
	 */
	List<Donativo> findByMensageiroOrderByDataDesc(Mensageiro mensageiro);

	/**
	 * 
	 * @param nome
	 * @return
	 */
	List<Donativo> findByNome(String nome);

	/**
	 * 
	 * <p>
	 * Busca donativos por categoria filtrando por instituição.
	 * </p>
	 * 
	 * @param instituicaoCaridade
	 * @return
	 */
	List<Donativo> findByCategoriaInstituicaoCaridadeOrderByDataDesc(InstituicaoCaridade instituicaoCaridade);

	/**
	 * Conta quantos donativos com a categoria passada na instituição com id
	 * passado existem.
	 * 
	 * @param nome
	 * @return
	 */
	Long countByCategoriaAndCategoriaInstituicaoCaridadeId(Categoria categoria, Long id);

	/**
	 * <p>
	 * Busca donativos e retorna lista ordenada por data de criação
	 * </p>
	 * 
	 * @return
	 */
	List<Donativo> findAllByOrderByDataDesc();

	/**
	 * <p>
	 * Busca donativos com estado passado e id da instituicao passada
	 * </p>
	 * 
	 * @return lista de donativos
	 */
	List<Donativo> filterDonativoByEstadoAndInstituicao(@Param("idInstituicao") Long idInstitucao,
			@Param("estado") Estado estado);

	/**
	 * <p>
	 * Busca donativos filtrando por mensageiro e estado
	 * </p>
	 * 
	 * @return lista de donativos
	 */
	List<Donativo> filterDonativoByMensageiroAndEstado(@Param("idMensageiro") Long idMensageiro,
			@Param("estado") Estado estado);

	/**
	 * <p>
	 * Busca donativos com base na localização
	 * </p>
	 * 
	 * @return lista de donativos
	 */
	List<Donativo> filterDonativoByLocal(@Param("localidade") String localidade, @Param("uf") String uf,
			@Param("idInstituicao") Long idInstitucao, @Param("estado") Estado estado);

	/**
	 * 
	 * <p>
	 * Conta a quantidade de doações efetuadas a partir do estado recolhido
	 * </p>
	 * 
	 * @return
	 */
	Long filterCountByEstadoRecolhido();

	/**
	 * 
	 * <p>
	 * Conta a quantidade de doações feitas para a instituição com id efetuadas
	 * a partir do estado recolhido
	 * </p>
	 * 
	 * @return
	 */
	Long filterCountByEstadoRecolhidoAndInstituicaoId(@Param("idInstituicao") Long idInstitucao);

	/**
	 * 
	 * <p>
	 * Busca a quantidade de donativos por data;
	 * </p>
	 * 
	 * @param data
	 * @return
	 */
	Long filterCountByEstadoRecolhidoAndDateBetween(@Param("startDate") Date date1, @Param("endDate") Date date2);

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @param idInst
	 * @return
	 */
	Long filterCountByEstadoRecolhidoAndDateBetweenAndInst(@Param("startDate") Date date1, @Param("endDate") Date date2,
			@Param("idInst") Long idInst);

	/**
	 * 
	 * <p>
	 * Busca a quantidade de donativos por data e estado.
	 * </p>
	 * 
	 * @param date1
	 * @param date2
	 * @param estado
	 * @return
	 */
	Long filterCountDonativoByEstadoAndDateBetween(@Param("startDate") Date date1, @Param("endDate") Date date2,
			@Param("estado") Estado estado);

	/**
	 * 
	 * <p>
	 * </p>
	 * 
	 * @param date1
	 * @param date2
	 * @param estado
	 * @param idInst
	 * @return
	 */
	Long filterCountDonativoByEstadoAndDateBetweenAndInst(@Param("startDate") Date date1, @Param("endDate") Date date2,
			@Param("estado") Estado estado, @Param("idInst") Long idInst);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Long countByCategoriaInstituicaoCaridadeId(Long id);

	/**
	 * 
	 * @param mensageiro
	 * @return
	 */
	Long filterCountByMensageiroAndEstado(@Param("mensageiro") Mensageiro mensageiro);

	/**
	 * 
	 * @param instituicaoCaridade
	 * @return
	 */
	List<Donativo> findFirst10ByCategoriaInstituicaoCaridadeOrderByDataDesc(InstituicaoCaridade instituicaoCaridade);
}
