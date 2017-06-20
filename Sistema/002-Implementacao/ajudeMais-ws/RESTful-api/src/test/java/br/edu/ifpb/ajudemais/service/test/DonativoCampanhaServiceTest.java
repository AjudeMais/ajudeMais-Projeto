package br.edu.ifpb.ajudemais.service.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.DonativoCampanha;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoCampanhaService;

/**
 * 
 * 
 * <p>
 * { @link DonativoCampanhaServiceTest }
 * </p>
 * 
 * <p>
 * 	Classe para testar os serviços oferecidos em {@link DonativoCampanhaService}
 * </p>
 *
 * <pre>
 * </pre>
 *
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class DonativoCampanhaServiceTest {
	
	private DonativoCampanha donativoCampanha;
	
	
	@Mock
	private DonativoCampanhaService mockDonativoCampanhaService;
	
	
	/**
	 * 
	 * <p>
	 * Configura ações, antes de executar unidades de testes.
	 * </p>
	 */
	@Before
	public void setUp() {
		mockDonativoCampanhaService = mock(DonativoCampanhaService.class);
		getDonativoCampanha();
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void saveOk() throws Exception {
		mockDonativoCampanhaService.save(donativoCampanha);
		verify(mockDonativoCampanhaService).save(donativoCampanha);
	}
	
	private DonativoCampanha getDonativoCampanha() {
		donativoCampanha = new DonativoCampanha();
		donativoCampanha.setCampanha(new Campanha());
		donativoCampanha.setDonativo(new Donativo());
		return donativoCampanha;
	}

}
