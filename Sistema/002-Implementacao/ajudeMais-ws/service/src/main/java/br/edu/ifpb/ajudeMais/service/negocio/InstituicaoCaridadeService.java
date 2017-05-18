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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.google.maps.errors.ApiException;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.maps.dto.LatLng;

/**
 * 
 * <p>
 * {@link InstituicaoCaridadeService}
 * </p>
 * 
 * <p>
 * Interface para definição de operações de serviços de
 * {@link InstituicaoCaridade}.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface InstituicaoCaridadeService extends Service<InstituicaoCaridade, Long> {

	/**
	 * Busca todos as instituições e seu endereço que é proximo a localidade do
	 * endereço passado
	 * 
	 * @param logradouro
	 * @param bairro
	 * @param localidade
	 * @param uf
	 * @return
	 */
	List<InstituicaoCaridade> filtersInstituicoesForAddress(Endereco endereco);

	/**
	 * Busca todos as instituições e seu endereço que é proximo a localidade do
	 * endereço passado
	 * 
	 * @param logradouro
	 * @param bairro
	 * @param localidade
	 * @param uf
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ApiException
	 * @throws NumberFormatException
	 */
	List<InstituicaoCaridade> filtersInstituicaoCloseForLatAndLng(LatLng latLng);

	/**
	 * Busca instituição por conta, filtrando por conta ativa.
	 * 
	 * @param ativo
	 * @return
	 */
	List<InstituicaoCaridade> findByContaAtivo(boolean ativo);

	/**
	 * 
	 * <p>
	 * Busca instituição por conta.
	 * </p>
	 * 
	 * @param conta
	 * @return
	 */
	Optional<InstituicaoCaridade> findOneByConta(Conta conta);

}
