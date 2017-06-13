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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.maps.GoogleMapsService;
import br.edu.ifpb.ajudeMais.service.maps.dto.LatLng;
import br.edu.ifpb.ajudeMais.service.negocio.DoadorService;

/**
 * 
 * <p>
 * <b> {@link DoadorRestService} </b>
 * </p>
 *
 * <p>
 * Classe define serviços disponibilizados de um doador.
 * </p>
 * 
 * @author <a href="https://github.com/FranckAJ">Franck Aragão</a>
 */
@RestController
@RequestMapping(value = "/doador")
public class DoadorRestService {

	/**
	 * 
	 */
	@Autowired
	private DoadorService doadorService;

	/**
	 * 
	 */
	@Autowired
	private GoogleMapsService googleMapsService;

	/**
	 * 
	 * <p>
	 * POST /doador/ : Método disponibiliza recurso para salvar um doador. ROLE:
	 * PUBLIC
	 * </p>
	 * 
	 * @param doador
	 * @return Htpp 201, caso cadastro tenha occorido com sucesso
	 * @throws AjudeMaisException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@Valid @RequestBody Doador doador) throws AjudeMaisException {
		Doador doadorCriado = doadorService.save(doador);
		return new ResponseEntity<>(doadorCriado, HttpStatus.CREATED);
	}

	/**
	 * 
	 * <p>
	 * PUT /doador/ : Método disponibiliza recurso para atualizar um doador.
	 * ROLE: DOADOR
	 * </p>
	 * 
	 * @param doador
	 *            doador a ser atualizado.
	 * @return 201 caso sucesso.
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Doador> update(@Valid @RequestBody Doador doador) throws AjudeMaisException {

		Doador pacienteAtualizado = doadorService.update(doador);

		return new ResponseEntity<Doador>(pacienteAtualizado, HttpStatus.CREATED);
	}

	/**
	 * 
	 * <p>
	 * PUT /doador/localizacao : Atualiza localização atual de um doador. <br/>
	 * ROLE: DOADOR
	 * </p>
	 * 
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.PUT, value = "/localizacao")
	public ResponseEntity<Doador> updateLocalizacao(@RequestParam("doadorId") Long doadorId, @RequestBody LatLng latLng)
			throws AjudeMaisException {

		Doador doador = doadorService.findById(doadorId);
		Endereco endereco = null;

		try {
			endereco = googleMapsService.converteLatitudeAndLongitudeInAddress(latLng.getLatitude(),
					latLng.getLongitude());
		} catch (AjudeMaisException e) {
			e.printStackTrace();
		}

		doador.setEnderecoAtual(endereco);
		doadorService.update(doador);

		return new ResponseEntity<Doador>(doador, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /doador/ : Método disponibiliza recurso obter doadores cadastrados.
	 * ROLE: DOADOR
	 * </p>
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Doador>> findAll() {

		List<Doador> doador = doadorService.findAll();

		return new ResponseEntity<List<Doador>>(doador, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /doador/id : Busca um doador pelo deu ID. Caso doador não exista um
	 * NOT FOUNT será lançado para o cliente. <br/>
	 * ROLE: DOADOR
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Doador> findById(@PathVariable Long id) {

		Doador doador = doadorService.findById(id);

		if (doador == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Doador>(doador, HttpStatus.OK);
	}

	/**
	 * 
	 * <p>
	 * GET /doador/id : Busca um doador pela conta, filtrando pelo nome de
	 * usuário. Caso doador não exista um NOT FOUNT será lançado para o cliente.
	 * <br/>
	 * ROLE: DOADOR
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasRole('DOADOR')")
	@RequestMapping(method = RequestMethod.GET, value = "/filter/username")
	public ResponseEntity<Doador> findByContaUsername(@RequestParam String username) {

		Doador doador = doadorService.findByContaUsername(username);
		return new ResponseEntity<Doador>(doador, HttpStatus.OK);
	}
}
