package br.edu.ifpb.ajudeMais.data.repository;

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
	 * Busca donativos com base na localização
	 * </p>
	 * 
	 * @return lista de donativos
	 */
	List<Donativo> filterDonativoByLocal(@Param("localidade") String localidade, @Param("uf") String uf);
}
