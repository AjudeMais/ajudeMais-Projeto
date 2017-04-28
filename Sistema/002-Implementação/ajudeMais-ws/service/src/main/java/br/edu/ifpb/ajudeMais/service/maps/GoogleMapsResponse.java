
package br.edu.ifpb.ajudeMais.service.maps;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;


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
	 * Método que consulta distância entre um ponto de origem e varios outros pontos de destino.
	 * @param origem
	 * @param destinations
	 * @return
	 * @throws Exception
	 */
	public DistanceMatrix findDistanceBetweenAddress(String origem, String[] destinations) throws Exception {

		DistanceMatrix matrix = DistanceMatrixApi.newRequest(apiContext)
		        .origins(origem)
		        .destinations(destinations)
		        .mode(TravelMode.DRIVING)
		        .language("pt-BR")
		        .units(Unit.METRIC)
		        .await();
		
		return matrix;
	
	}
	
	public GeocodingResult[] converteLatitudeAndLongitudeInAddress(double latitude, double logitude) throws ApiException, InterruptedException, IOException{
		GeocodingResult[] results = GeocodingApi.newRequest(apiContext)
		        .latlng(new LatLng(latitude, logitude)).await();	
		
		return results;
		
	
	}
	
	
}
