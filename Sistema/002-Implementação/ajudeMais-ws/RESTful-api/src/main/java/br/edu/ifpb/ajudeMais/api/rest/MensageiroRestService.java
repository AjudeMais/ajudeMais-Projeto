
package br.edu.ifpb.ajudeMais.api.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroService;

/**
 * 
 * <p>
 * <b> MensageiroRestService.java </b>
 * </p>
 *
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@RestController
@RequestMapping("/mensageiro")
public class MensageiroRestService {

	@Autowired
	private MensageiroService mensageiroService;

	/**
	 * End point para cadastro de um mensageiro no sistema.
	 * @param mensageiro
	 * @return
	 * @throws AjudeMaisException
	 */
	@PreAuthorize("hasAnyRole('INSTITUICAO','ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Mensageiro> save(@Valid @RequestBody Mensageiro mensageiro) throws AjudeMaisException {
		Mensageiro mensageiroSaved = mensageiroService.save(mensageiro);
		return new ResponseEntity<Mensageiro>(mensageiroSaved, HttpStatus.OK);
	}
	
	/**
	 * End point para atualizar informações do mensageiro.
	 * @param mensageiro
	 * @return Mensageiro
	 * @throws AjudeMaisException 
	 */
	@PreAuthorize("hasAnyRole('INSTITUICAO','ADMIN')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Mensageiro> update(@Valid @RequestBody Mensageiro mensageiro) throws AjudeMaisException {
		mensageiro = mensageiroService.update(mensageiro);
		return new ResponseEntity<Mensageiro>(mensageiro, HttpStatus.OK);

	}
	
	
	/**
	 * End point para listar todos os mensageiros cadastros no sistema.
	 * @return Mensageiro
	 */
	@PreAuthorize("hasAnyRole('INSTITUICAO','ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Mensageiro>> findAll(){
		List<Mensageiro> mensageiros = mensageiroService.findAll();
		return new ResponseEntity<List<Mensageiro>>(mensageiros, HttpStatus.OK);
	}
	
	/**
	 * End point para buscar um mensageiro por seu identificador.
	 * @return  Mensageiro
	 */
	@PreAuthorize("hasAnyRole('INSTITUICAO','ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Mensageiro> findById(Long id){
		Mensageiro mensageiro = mensageiroService.findById(id);
		return new ResponseEntity<Mensageiro>(mensageiro, HttpStatus.OK);
	}
	
	
	

}
