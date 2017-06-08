
/**
 * 
 * <p>
 * <b> LocalizacaoRestService.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
package br.edu.ifpb.ajudeMais.api.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.maps.GoogleMapsService;
import br.edu.ifpb.ajudeMais.service.maps.dto.LatLng;
import br.edu.ifpb.ajudeMais.service.maps.impl.GoogleMapsServiceImpl;

/**
 * 
 * <p>
 * <b>{@link LocalizacaoRestService}</b>
 * </p>
 *
 * <p>
 * Classe utilizada para serviços providos referentes a {@link GoogleMapsService}
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
@RestController
@RequestMapping(value = "/localizacao")
public class LocalizacaoRestService {

	/**
	 * 
	 */
	@Autowired
	private GoogleMapsServiceImpl googleMapsResponse;
	
	
	/**
	 * 
	 * <p>
	 * GET /localizacao/ : Método que transforma a latitude e logintude em Objeto Endereço.
	 * ROLE: DOADOR,MENSAGEIRO 
	 * </p>
	 * 
	 * @return
	 * @throws AjudeMaisException 
	 */
	@PreAuthorize("hasAnyRole ('MENSAGEIRO,DOADOR')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Endereco> findByEnderecoActual(@RequestBody LatLng latLng) throws AjudeMaisException {

		Endereco endereco = googleMapsResponse.converteLatitudeAndLongitudeInAddress(latLng.getLatitude(), latLng.getLongitude());

		return new ResponseEntity<Endereco>(endereco, HttpStatus.OK);
	}

	
}
