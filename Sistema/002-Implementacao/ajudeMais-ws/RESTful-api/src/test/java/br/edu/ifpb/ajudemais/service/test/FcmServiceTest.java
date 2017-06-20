package br.edu.ifpb.ajudemais.service.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.service.fcm.FcmService;
import br.edu.ifpb.ajudeMais.service.fcm.dto.Notification;
import br.edu.ifpb.ajudeMais.service.fcm.dto.Push;

/**
 * 
 * <p>
 * {@link FcmServiceTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes referentes a {@link FcmService}
 * </p>
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class FcmServiceTest {

	private FcmService mockedFcmService;

	/**
	 * metodo que prepara para as unidades de teste;
	 */
	@Before
	public void setUp() {
		mockedFcmService = mock(FcmService.class);
	}

	/**
	 * 
	 * <p>
	 * verifica comportamento ao enviar notificação;
	 * </p>
	 */
	@Test
	public void sendNotification() {
		Notification notification = new Notification("default", "Nova Campanha", "Campanha Test");
		Push push = new Push("high", notification, new ArrayList<String>());

		mockedFcmService.sendNotification(push);
		verify(mockedFcmService).sendNotification(push);
	}

}
