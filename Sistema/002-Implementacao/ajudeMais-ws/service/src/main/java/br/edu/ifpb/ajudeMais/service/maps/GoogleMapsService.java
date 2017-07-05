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
package br.edu.ifpb.ajudeMais.service.maps;

import java.util.List;

import com.google.maps.model.DistanceMatrix;

import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;

/**
 * 
 * <p>
 * <b> GoogleMapsServiceImlp.java </b>
 * </p>
 *
 * <p>
 * Interface para serviços do Google maps
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public interface GoogleMapsService {

	Endereco converteLatitudeAndLongitudeInAddress(double latitude, double logitude) throws AjudeMaisException;

	DistanceMatrix findByDistanceBetweenAddress(String origem, String[] destinations) throws AjudeMaisException;

	List<Mensageiro> validateAddressMensageiros(List<Object[]> selectedMensageiros) throws AjudeMaisException;
}
