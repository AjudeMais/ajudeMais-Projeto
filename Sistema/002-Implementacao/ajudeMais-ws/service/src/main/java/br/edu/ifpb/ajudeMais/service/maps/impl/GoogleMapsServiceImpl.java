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
package br.edu.ifpb.ajudeMais.service.maps.impl;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.service.maps.GoogleMapsService;

/**
 * 
 * <p>
 * <b> TesteResponseGoogle.java </b>
 * </p>
 *
 * <p>
 * Serviços de localização geográfica fornecidos por google maps
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

@Service
public class GoogleMapsServiceImpl implements Serializable, GoogleMapsService {

	private static final long serialVersionUID = 1L;

	@Value("${google.api.maps.key}")
	private String key;

	private GeoApiContext apiContext;
	private Endereco endereco;
	private static final String MAP_ROUTE = "route";
	private static final String MAP_POLITICAL = "political";
	private static final String MAP_ADMINISTRATIVE_AREA_LEVEL_2 = "administrative_area_level_2";
	private static final String MAP_ADMINISTRATIVE_AREA_LEVEL_1 = "administrative_area_level_1";
	private static final String MAP_POSTAL_CODE = "postal_code";

	
	
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
	
	/**
	 * Recebe a latitude e logintude de um localização e converte em um Objeto do tipo Endereço.
	 * @param latitude
	 * @param logitude
	 * @return
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public Endereco converteLatitudeAndLongitudeInAddress(double latitude, double logitude)
			throws ApiException, InterruptedException, IOException {

		apiContext = new GeoApiContext().setApiKey(key);

		GeocodingResult[] results = GeocodingApi.newRequest(apiContext).latlng(new LatLng(latitude, logitude))
				.language("pt-BR").await();

		return setResultEnderecoGoogleMaps(results);

	}

	/**
	 * Recebe o resultado da consulta do google e converte em um objeto Endereço
	 * @param results
	 * @return
	 */
	private Endereco setResultEnderecoGoogleMaps(GeocodingResult[] results) {
		endereco = new Endereco();

		for (AddressComponent addressComponent : results[0].addressComponents) {

			if (addressComponent.types[0].toString().trim().equals(MAP_ROUTE)) {

				endereco.setLogradouro(addressComponent.longName);
			}
			if (addressComponent.types[0].toString().trim().equals(MAP_POLITICAL)) {
				endereco.setBairro(addressComponent.longName);
			}

			if (addressComponent.types[0].toString().trim().equals(MAP_ADMINISTRATIVE_AREA_LEVEL_2)) {
				endereco.setLocalidade(addressComponent.longName);
			}

			if (addressComponent.types[0].toString().trim().equals(MAP_ADMINISTRATIVE_AREA_LEVEL_1)) {
				endereco.setUf(addressComponent.shortName);
			}

			if (addressComponent.types[0].toString().trim().equals(MAP_POSTAL_CODE)) {
				endereco.setCep(addressComponent.longName);

			}
		}
		
		return endereco;
	}

}
