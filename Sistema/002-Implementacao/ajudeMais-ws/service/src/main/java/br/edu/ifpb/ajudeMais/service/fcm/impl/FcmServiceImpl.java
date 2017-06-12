package br.edu.ifpb.ajudeMais.service.fcm.impl;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ifpb.ajudeMais.service.fcm.FcmService;
import br.edu.ifpb.ajudeMais.service.fcm.dto.FirebaseResponse;
import br.edu.ifpb.ajudeMais.service.fcm.dto.Push;
import br.edu.ifpb.ajudeMais.service.filter.HeaderRequestInterceptor;

/**
 * 
 * <p>
 * {@link FcmServiceImpl}
 * </p>
 * 
 * <p>
 * Implementação de {@link FcmService}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@Service
public class FcmServiceImpl implements FcmService {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FcmServiceImpl.class);

	/**
	 * 
	 */
	@Value("${google.api.fcm.key}")
	private String fcmKey;

	/**
	 * 
	 */
	private static final String FCM_API = "https://fcm.googleapis.com/fcm/send";

	/**
	 * Envia notificação para firebase.
	 */
	@Override
	public FirebaseResponse sendNotification(Push push) {
		HttpEntity<Push> request = new HttpEntity<>(push);

		CompletableFuture<FirebaseResponse> pushNotification = this.send(request);
		CompletableFuture.allOf(pushNotification).join();

		FirebaseResponse firebaseResponse = null;

		try {
			firebaseResponse = pushNotification.get();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.debug("Erro ao enviar notificação " + e.getMessage());
		}
		return firebaseResponse;
	}

	/**
	 * Envia notificação push para API do firebase
	 * 
	 * Método utiliza CompletableFuture com @Async para realizar chamada
	 * assicrona na API.
	 * 
	 * @param entity
	 * @return
	 */
	@Async
	private CompletableFuture<FirebaseResponse> send(HttpEntity<Push> entity) {

		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + fcmKey));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		FirebaseResponse firebaseResponse = restTemplate.postForObject(FCM_API, entity, FirebaseResponse.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}

}
