
package br.edu.ifpb.ajudeMais.service.maps;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

import br.edu.ifpb.ajudeMais.data.repository.MensageiroRepository;

/**
 * 
 * <p>
 * <b> TesteResponseGoogle.java </b>
 * </p>
 *
 * <p>
 * Entidade ...
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */
public class GoogleMapsResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Value("${google.api.maps.key}")
	private String key;

	private GeoApiContext apiContext;

	public GoogleMapsResponse() {
		apiContext =  new GeoApiContext().setApiKey(key);
	}

	/**
	 * Método que consulta distância entre um ponto de origem e varios outros pontos.
	 * @param origem
	 * @param destinations
	 * @return
	 * @throws Exception
	 */
	public DistanceMatrix consultaDistancia(String origem, String[] destinations) throws Exception {

		DistanceMatrix matrix = DistanceMatrixApi.newRequest(apiContext)
		        .origins(origem)
		        .destinations(destinations)
		        .mode(TravelMode.DRIVING)
		        .language("pt-BR")
		        .avoid(RouteRestriction.TOLLS)
		        .units(Unit.IMPERIAL)
		        .departureTime(new DateTime().plusMinutes(2))
		        .await();
		
		return matrix;
	
	}
	
	
}
