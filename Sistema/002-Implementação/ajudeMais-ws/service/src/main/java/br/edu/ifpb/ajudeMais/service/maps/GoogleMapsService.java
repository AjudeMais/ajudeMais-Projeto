
package br.edu.ifpb.ajudeMais.service.maps;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

import br.edu.ifpb.ajudeMais.domain.entity.Endereco;

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

@Service
public class GoogleMapsService implements Serializable, GoogleMapsServiceImlp {

	private static final long serialVersionUID = 1L;

	@Value("${google.api.maps.key}")
	private String key;

	private GeoApiContext apiContext;
	private Endereco endereco;

	/**
	 * Método que consulta distância entre um ponto de origem e varios outros
	 * pontos de destino.
	 * 
	 * @param origem
	 * @param destinations
	 * @return
	 * @throws Exception
	 */
	public DistanceMatrix findDistanceBetweenAddress(String origem, String[] destinations) throws Exception {
		apiContext = new GeoApiContext().setApiKey(key);
		DistanceMatrix matrix = DistanceMatrixApi.newRequest(apiContext).origins(origem).destinations(destinations)
				.mode(TravelMode.DRIVING).language("pt-BR").units(Unit.METRIC).await();

		return matrix;

	}

	public Endereco converteLatitudeAndLongitudeInAddress(double latitude, double logitude)
			throws ApiException, InterruptedException, IOException {
		System.out.println("KEY:" + key + "-----" + latitude + ":" + logitude);
		endereco = new Endereco();

		apiContext = new GeoApiContext().setApiKey(key);

		GeocodingResult[] results = GeocodingApi.newRequest(apiContext).latlng(new LatLng(latitude, logitude))
				.language("pt-BR").await();

		endereco.setLogradouro(results[0].addressComponents[1].longName);
		endereco.setLocalidade(results[0].addressComponents[3].longName);
		endereco.setUf(results[0].addressComponents[5].longName);
		endereco.setCep(results[4].addressComponents[0].longName);
		endereco.setBairro(results[0].addressComponents[2].longName);

		return endereco;

	}

}
