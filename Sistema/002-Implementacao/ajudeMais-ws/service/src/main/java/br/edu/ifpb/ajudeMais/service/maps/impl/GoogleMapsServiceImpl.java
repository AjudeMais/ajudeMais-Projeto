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
import java.util.ArrayList;
import java.util.List;

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
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
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
	private static final String PT_BR = "pt-BR";

	/**
	 * Método que consulta distância entre um ponto de origem e varios outros
	 * pontos de destino.
	 * 
	 * @param origem
	 * @param destinations
	 * @return
	 * @throws Exception
	 */
	public DistanceMatrix findByDistanceBetweenAddress(String origem, String[] destinations) throws AjudeMaisException {

		try {
			apiContext = new GeoApiContext().setApiKey(key);
			DistanceMatrix matrix = DistanceMatrixApi.newRequest(apiContext).origins(origem).destinations(destinations)
					.mode(TravelMode.DRIVING).language(PT_BR).units(Unit.METRIC).await();

			return matrix;

		} catch (ApiException e) {
			throw new AjudeMaisException(e.getMessage());
		} catch (InterruptedException e) {
			throw new AjudeMaisException("Sem conexão com a internet.");
		} catch (IOException e) {
			throw new AjudeMaisException(e.getMessage());
		}

	}

	/**
	 * Recebe a latitude e logintude de um localização e converte em um Objeto
	 * do tipo Endereço.
	 * 
	 * @param latitude
	 * @param logitude
	 * @return
	 * @throws AjudeMaisException
	 */
	public Endereco converteLatitudeAndLongitudeInAddress(double latitude, double logitude) throws AjudeMaisException {

		apiContext = new GeoApiContext().setApiKey(key);

		try {
			GeocodingResult[] results;

			results = GeocodingApi.newRequest(apiContext).latlng(new LatLng(latitude, logitude)).language(PT_BR)
					.await();

			return setResultEnderecoGoogleMaps(results);

		} catch (ApiException | IOException e) {
			throw new AjudeMaisException(e.getMessage());
		} catch (InterruptedException e) {
			throw new AjudeMaisException("Sem conexão com a internet.");
		}

	}

	/**
	 * Recebe um endereço e valida via Google Maps. do tipo Endereço.
	 * 
	 * @param endereco
	 * @return
	 * @throws AjudeMaisException
	 */
	public Boolean validateAddress(Endereco endereco) throws AjudeMaisException {

		apiContext = new GeoApiContext().setApiKey(key);

		try {
			GeocodingResult[] results;

			results = GeocodingApi.newRequest(apiContext).address(endereco.getLogradouro() + "," + endereco.getBairro()
					+ "," + endereco.getLocalidade() + "," + endereco.getUf()).language(PT_BR).await();

			return setResultEnderecoGoogleMaps(results) != null;

		} catch (ApiException | IOException e) {
			throw new AjudeMaisException(e.getMessage());
		} catch (InterruptedException e) {
			throw new AjudeMaisException("Sem conexão com a internet.");
		}

	}

	/**
	 * Recebe o resultado da consulta do google e converte em um objeto Endereço
	 * 
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

	/**
	 * Verificar qual o mensageiro cadastrado mais próximo do endereço passado.
	 * 
	 * @param endereco
	 * @return
	 * @throws AjudeMaisException
	 */
	public List<Mensageiro> validateAddressMensageiros(List<Object[]> selectedMensageiros) throws AjudeMaisException {

		List<Mensageiro> mensageirosAptos = new ArrayList<>();

		if (selectedMensageiros != null && !selectedMensageiros.isEmpty()) {
			for (int i = 0; i < selectedMensageiros.size(); i++) {

				if (validateAddress((Endereco) selectedMensageiros.get(i)[1])) {
					mensageirosAptos.add((Mensageiro) selectedMensageiros.get(i)[0]);
				}
			}
			return mensageirosAptos;

		} else {
			return mensageirosAptos;
		}

	}

}