/**
 * <p>
 * Ajude Mais - Módulo Web Service
 * </p>
 * 
 * <p>
 * Sistema para potencializar o processo de doação.
 * </p>
 * 
 * <a href="https://github.com/AjudeMais/AjudeMais">Ajude Mais</a>
 * <a href="https://franckaj.github.io">Franck Aragão"></a>
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;

/**
 * 
 * <p>
 * {@link MensageiroAssociadoRepository}
 * </p>
 * 
 * <p>
 * Interaface utilizada para operação de pesistência de
 * {@link MensageiroAssociado}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface MensageiroAssociadoRepository extends JpaRepository<MensageiroAssociado, Long> {

	/**
	 * 
	 * <p>
	 * Filtra um mensageiro por sua localização considerando bairro, cidade e
	 * estado.
	 * </p>
	 * 
	 * @param bairro
	 * @param localidade
	 * @param idInstituicao
	 * @param uf
	 * @return
	 */
	List<Object[]> filterMensageirosCloserToBairro(@Param("bairro") String bairro,
			@Param("localidade") String localidade, @Param("uf") String uf, @Param("idInstituicao") Long idInstituicao);

	/**
	 * 
	 * <p>
	 * Filtra um mensageiro por sua localização, considerando cidade e estado.
	 * </p>
	 * 
	 * @param localidade
	 * @param idInstituicao
	 * @param uf
	 * @return
	 */
	List<Object[]> filterMensageirosCloserToCidade(@Param("localidade") String localidade, @Param("uf") String uf,
			@Param("idInstituicao") Long idInstituicao);

	/**
	 * 
	 * <p>
	 * 
	 * Busca mensageiros associados pela insituição, filtrando por sua conta.
	 * </p>
	 * 
	 * @param conta
	 */
	List<MensageiroAssociado> findByInstituicaoCaridadeConta(Conta conta);

	/**
	 * 
	 * @return
	 */
	List<MensageiroAssociado> findByInstituicaoCaridade(InstituicaoCaridade instituicaoCaridade);

	/**
	 * 
	 * <p>
	 * 
	 * Busca mensageiros associados pelo id do mensageiro.
	 * </p>
	 * 
	 * @param conta
	 */
	List<MensageiroAssociado> findByMensageiroId(Long id);

	/**
	 * 
	 * <p>
	 * Busca mensageiro associado por ID do mensageiro.
	 * </p>
	 * 
	 * @param id
	 *            a ser busacado
	 * @return um optional contento o resultado da query.
	 */
	Optional<MensageiroAssociado> findByMensageiroAndInstituicaoCaridade(Mensageiro mensageiro,
			InstituicaoCaridade instituicao);

	/**
	 * 
	 * @param status
	 * @return
	 */
	Long countByStatusAndInstituicaoCaridadeId(boolean status, Long idInstituicao);

}
