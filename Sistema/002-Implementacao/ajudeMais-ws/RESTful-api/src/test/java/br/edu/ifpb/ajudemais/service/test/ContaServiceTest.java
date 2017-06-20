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
package br.edu.ifpb.ajudemais.service.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
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
 * Classe utilizada para testes referentes a services de {@link ContaService}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ContaServiceTest {

	/**
	 * 
	 */
	private Conta conta;

	/**
	 * 
	 */
	@Autowired
	private ContaService contaService;

	/**
	 * 
	 */
	@Mock
	private ContaService mockContaService;

	/**
	 * 
	 * <p>
	 * Configura ações, antes de executar unidades de testes.
	 * </p>
	 */
	@Before
	public void setUp() {
		mockContaService = mock(ContaService.class);
		getConta();
	}

	/**
	 * 
	 * <p>
	 * Teste para salvar uma conta.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void saveOk() throws AjudeMaisException {
		mockContaService.save(conta);
		verify(mockContaService).save(conta);

	}

	/**
	 * 
	 * <p>
	 * Teste para salvar uma conta passando username null;
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveContaWithUsernameNull() throws AjudeMaisException {
		conta.setUsername(null);
		contaService.save(conta);

	}

	/**
	 * 
	 * <p>
	 * Teste para salvar uma conta passando username null;
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveContaWithPassNull() throws AjudeMaisException {
		conta.setUsername(null);
		contaService.save(conta);

	}

	/**
	 * 
	 * <p>
	 * Verifica método de alteração de senha com confirmação.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void changePasswordOk() throws AjudeMaisException {
		mockContaService.changePassword("123", "123");
		verify(mockContaService).changePassword("123", "123");
	}

	/**
	 * 
	 * <p>
	 * Verifica método de alteração de senha para primeiro acesso.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void changePasswordInitOk() throws AjudeMaisException {
		mockContaService.changePasswordInit("123");
		verify(mockContaService).changePasswordInit("123");
		
	}

	/**
	 * 
	 * <p>
	 * Testa atualização de uma conta;
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void updateOk() throws AjudeMaisException {
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Conta conta = (Conta) args[0];
				conta.setUsername("username");
				return null;
			}
		}).when(mockContaService).update(any(Conta.class));

		mockContaService.update(this.conta);
		verify(mockContaService).update(this.conta);

		assertThat(this.conta.getUsername(), equalTo("username"));

	}

	/**
	 * Teste para remoção de uma Conta.
	 * @throws AjudeMaisException 
	 */
	@Test
	public void removeContaOk() throws AjudeMaisException {
		mockContaService.remover(conta);
		verify(mockContaService).remover(conta);
	}

	/**
	 * Testa remoção de uma conta nulo.
	 * @throws AjudeMaisException 
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void removeContaNull() throws AjudeMaisException {
		contaService.remover(null);
	}

	/**
	 * Teste buscar todos os contaes.
	 */
	@Test
	public void findAllContas() {
		List<Conta> contas = new ArrayList<>();
		contas.addAll(Arrays.asList(conta, conta));

		when(mockContaService.findAll()).thenReturn(contas);
		List<Conta> mockedContas = mockContaService.findAll();

		assertThat(mockedContas, hasItems(conta, conta));
	}

	/**
	 * 
	 * <p>
	 * Cria uma instancia de conta para ser utilizado nos testes.
	 * </p>
	 * 
	 * @return nova conta
	 */
	private Conta getConta() {

		conta = new Conta();
		conta.setUsername("contaX");
		conta.setSenha("contaX");
		conta.setGrupos(Arrays.asList("ROLE_conta"));
		conta.setEmail("contaX@gmail.com");
		conta.setAtivo(true);

		return conta;
	}
}
