package br.edu.ifpb.ajudeMais.service.fcm;

import br.edu.ifpb.ajudeMais.service.fcm.dto.FirebaseResponse;
import br.edu.ifpb.ajudeMais.service.fcm.dto.Push;

/**
 * 
 * <p>
 * {@link FcmService}
 * </p>
 * 
 * <p>
 * Classe utilizada para serviços comuns referentes a comunicação com o firebase
 * cloud message.
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
public interface FcmService {

	/**
	 * 
	 * <p>
	 * Envia push para servidor do firebase.
	 * </p>
	 * 
	 * @param push
	 * @return
	 */
	FirebaseResponse sendNotification(Push push);

}
