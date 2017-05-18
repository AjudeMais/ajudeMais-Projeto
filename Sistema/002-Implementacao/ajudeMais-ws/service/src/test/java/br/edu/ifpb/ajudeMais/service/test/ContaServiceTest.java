package br.edu.ifpb.ajudeMais.service.test;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.ContaService;

/**
 * 
 * <p>
 * {@link ContaServiceTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes referentes a {@link ContaService}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@RunWith(SpringRunner.class)
public class ContaServiceTest {

	/**
	 * 
	 */
	@Mock
	private ContaService mockContaService;

	private Conta conta;

	/**
	 * 
	 * <p>
	 * </p>
	 */
	@Before
	public void setUp() {

		mockContaService = mock(ContaService.class);

		conta = new Conta();
		conta.setUsername("zeze");
		conta.setSenha("zezeze");
		conta.setAtivo(true);
		conta.setEmail("ze@mail.com");

	}

	/**
	 * 
	 * <p>
	 * </p>
	 * @throws AjudeMaisException
	 */
	@Test
	public void saveTest() throws AjudeMaisException {
		mockContaService.save(conta);
		verify(mockContaService).save(conta);
	}
	

}
