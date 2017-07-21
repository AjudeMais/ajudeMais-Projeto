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
package br.edu.ifpb.ajudeMais.api.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.data.repository.MensageiroAssociadoRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.negocio.InstituicaoCaridadeService;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroAssociadoService;

/**
 * 
 * <p>
 * {@link MensageiroAssociadoRestService}
 * </p>
 * 
 * <p>
 * Classe utilizada para disponibilização de recursos relacionados a
 * {@link MensageiroAssociado}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@RestController
@RequestMapping("/associacao")
public class MensageiroAssociadoRestService {

	/**
	 * 
	 */
	@Autowired
	private MensageiroAssociadoService mensageiroAssociadoService;

	/**
	 * 
	 */
	@Autowired
	private AuthService authService;

	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeService instituicaoCaridadeService;

	/**
	 * 
	 */
	@Autowired
	private MensageiroAssociadoRepository mensageiroAssociadoRepository;

	/**
	 * 
	 * <p>
	 * POST /associacao : Método disponibiliza recurso para criar uma associação
	 * entre mensageiro e insituição logada.. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @param mensageiroAssociado
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<MensageiroAssociado> save(@RequestBody MensageiroAssociado mensageiroAssociado)
			throws AjudeMaisException {

		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoCaridadeService.findOneByConta(conta);

		if (instituicaoOp.isPresent()) {
			mensageiroAssociado.setInstituicaoCaridade(instituicaoOp.get());
		} else {
			return new ResponseEntity<MensageiroAssociado>(HttpStatus.FORBIDDEN);
		}
		MensageiroAssociado mensageiro = mensageiroAssociadoService.save(mensageiroAssociado);
		return new ResponseEntity<MensageiroAssociado>(mensageiro, HttpStatus.CREATED);
	}

	/**
	 * 
	 * <p>
	 * PUT /associacao : Método disponibiliza recurso para atualizar associação
	 * entre mensageiro e instituição logada. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @param mensageiroAssociado
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<MensageiroAssociado> update(@RequestBody MensageiroAssociado mensageiroAssociado)
			throws AjudeMaisException {

		MensageiroAssociado mensageiro = mensageiroAssociadoService.update(mensageiroAssociado);
		return new ResponseEntity<MensageiroAssociado>(mensageiro, HttpStatus.CREATED);
	}

	/**
	 * 
	 * <p>
	 * GET /associacao/filter/instituicao : Método disponibiliza recurso para
	 * obtenção de associações de mensageiro da instituição logada. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/filter/instituicao")
	public ResponseEntity<List<MensageiroAssociado>> findByInsituicao() {

		List<MensageiroAssociado> mensageiros = mensageiroAssociadoService
				.findByInstituicaoConta(authService.getCurrentUser());

		return new ResponseEntity<List<MensageiroAssociado>>(mensageiros, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /count/id : Obtém a quantidade de mensageiros associados de uma
	 * instituição de caridade que estã ativos. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/count/{id}")
	public ResponseEntity<Long> countByInsituicao(@PathVariable Long id) {
		Long count = mensageiroAssociadoRepository.countByStatusAndInstituicaoCaridadeId(true, id);
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}

}
