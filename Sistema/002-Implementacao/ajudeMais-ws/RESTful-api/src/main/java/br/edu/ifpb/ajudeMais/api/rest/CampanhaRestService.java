package br.edu.ifpb.ajudeMais.api.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.data.repository.InstituicaoCaridadeRepository;
import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.AuthService;
import br.edu.ifpb.ajudeMais.service.negocio.CampanhaService;
/**
 * Classe utilizada para criar os endpoints de campanha 
 * @author elson
 *
 */
@RestController
@RequestMapping("/campanha")
public class CampanhaRestService {
	/**
	 * 
	 */
	@Autowired
	private CampanhaService CampanhaService;
	
	/**
	 * 
	 */
	@Autowired
	private AuthService authService;

	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeRepository instituicaoRepository;
	
	
	/**
	 * <p>
	 * POST /campanha : endpoint criado para cadastro de uma campanha no sistema. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @param campanha
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Campanha> save(@Valid @RequestBody Campanha campanha, HttpServletRequest request)throws AjudeMaisException {

		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoRepository.findOneByConta(conta);

		if (instituicaoOp.isPresent()) {
			campanha.setInstituicaoCaridade(instituicaoOp.get());
		} 

		Campanha campanhaSalva = CampanhaService.save(campanha);
		return new ResponseEntity<>(campanhaSalva, HttpStatus.CREATED);
	}

	/**
	 * <p>
	 * PUT /campanha : endpoint criado para atualização dos dados de uma campanha. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @param campanha
	 * @return
	 * @throws AjudeMaisException
	 */

	@PreAuthorize("hasRole ('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Campanha> update(@Valid @RequestBody Campanha campanha) throws AjudeMaisException {
		Campanha campanhaAtualizada = CampanhaService.update(campanha);
		return new ResponseEntity<>(campanhaAtualizada, HttpStatus.OK);
	}
	/**
	 * <p>
	 * GET /campanha : endpoint criado para recuperar todas as campanhas. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @return
	 * @throws AjudeMaisException
	 */

	@PreAuthorize("hasRole ('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Campanha>> findAll() throws AjudeMaisException {
		List<Campanha> campanhas = CampanhaService.findAll();
		return new ResponseEntity<>(campanhas, HttpStatus.OK);
	}
	
	/**
	 * <p>
	 * GET /campanha : endpoint criado para recuperar uma campanha pelo id. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @throws AjudeMaisException
	 */

	@PreAuthorize("hasRole ('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Campanha> findById(@PathVariable Long id) throws AjudeMaisException {
		Campanha campanha = CampanhaService.findById(id);
		return new ResponseEntity<>(campanha, HttpStatus.OK);
	}
	
	/**
	 * <p>
	 * GET /campanha/instituicao : busca campanhas por insituição. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole ('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.GET, value = "/instituicao")
	public ResponseEntity<List<Campanha>> findByInstituicao() {

		Conta conta = authService.getCurrentUser();
		Optional<InstituicaoCaridade> instituicaoOp = instituicaoRepository.findOneByConta(conta);

		List<Campanha> campanhas = new ArrayList<>();
		if (instituicaoOp.isPresent()) {
			campanhas = CampanhaService.findByInstituicaoCaridade(instituicaoOp.get());
		}

		return new ResponseEntity<>(campanhas, HttpStatus.OK);
	}
	
	/**
	 * <p>
	 * DELETE /campanha/id : exclui uma campanha pesquisada pelo identificador. <br>
	 * ROLE: INSTITUICAO
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole ('INSTITUICAO')")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Campanha> delete(@PathVariable Long id) {

		Campanha campanhaEncontrada = CampanhaService.findById(id);

		if (campanhaEncontrada == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		CampanhaService.remover(campanhaEncontrada);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
