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
package br.edu.ifpb.ajudeMais.service.negocio;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;

/**
 * 
 * <p>
 * {@link MensageiroAssociadoService}
 * </p>
 * 
 * <p>
 * Classe utilizada para operações de associação de {@link Mensageiro} com
 * {@link InstituicaoCaridade}.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface MensageiroAssociadoService extends Service<MensageiroAssociado, Long> {

	/**
	 * 
	 * <p>
	 * Busca mensageiros associados a intituição, filtrando pela conta da
	 * insituição.
	 * </p>
	 * 
	 * @param conta
	 * @return
	 */
	List<MensageiroAssociado> findByInstituicaoConta(Conta conta);

	/**
	 * 
	 * <p>
	 * Busca mensageiro Mais proximo, considerando mensageiros do bairro com
	 * base no enderenco passado e no id da instituição insituição.
	 * </p>
	 * 
	 * @param conta
	 * @return
	 * @throws AjudeMaisException
	 * @throws Exception
	 */
	List<Mensageiro> filterMensageirosCloserToBairro(Endereco endereco, Long idInstituicao) throws AjudeMaisException;

	/**
	 * 
	 * <p>
	 * Busca mensageiro Mais proximo, considerando mensageiros da cidade com
	 * base no enderenco passado e no id da instituição insituição.
	 * </p>
	 * 
	 * @param conta
	 * @return
	 * @throws AjudeMaisException
	 * @throws Exception
	 */
	List<Mensageiro> filterMensageirosCloserToCidade(Endereco endereco, Long idInstituicao) throws AjudeMaisException;

	/**
	 * 
	 * @param instituicaoCaridade
	 * @return
	 */
	LinkedHashMap<MensageiroAssociado, Integer> getRanking(InstituicaoCaridade instituicaoCaridade);
	
	

}
