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
import static org.junit.Assert.assertEquals;
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
import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DoadorService;

/**
 * 
 * <p>
 * {@link DoadorServiceTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes referentes a services de {@link DoadorService}
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
public class DoadorServiceTest {

	/**
	 * 
	 */
	private Doador doador;

	/**
	 * 
	 */
	@Autowired
	private DoadorService doadorService;

	/**
	 * 
	 */
	@Mock
	private DoadorService mockDoadorService;

	/**
	 * 
	 * <p>
	 * Configura ações, antes de executar unidades de testes.
	 * </p>
	 */
	@Before
	public void setUp() {
		mockDoadorService = mock(DoadorService.class);
		getDoador();
	}

	/**
	 * 
	 * <p>
	 * Teste para salvar um doador;
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void saveOk() throws AjudeMaisException {
		mockDoadorService.save(doador);
		verify(mockDoadorService).save(doador);

	}

	/**
	 * 
	 * <p>
	 * Teste para salvar um doador passando nome null;
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveDoadorWithNomeNull() throws AjudeMaisException {
		doador.setNome(null);
		doadorService.save(doador);

	}

	/**
	 * 
	 * <p>
	 * Teste para salvar um doador passando telefone null;
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveDoadorWithTelefoneNull() throws AjudeMaisException {
		doador.setTelefone(null);
		doadorService.save(doador);

	}

	/**
	 * 
	 * <p>
	 * Testa atualização de um doador;
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
				Doador doador = (Doador) args[0];
				doador.setNome("Pedro");
				return null;
			}
		}).when(mockDoadorService).update(any(Doador.class));

		mockDoadorService.update(this.doador);
		verify(mockDoadorService).update(this.doador);

		assertThat(this.doador.getNome(), equalTo("Pedro"));

	}

	/**
	 * Teste para remoção de um doador.
	 * @throws AjudeMaisException 
	 */
	@Test
	public void removeDoadorOk() throws AjudeMaisException {
		mockDoadorService.remover(doador);
		verify(mockDoadorService).remover(doador);
	}

	/**
	 * Testa remoção de um Doador nulo.
	 * @throws AjudeMaisException 
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void removeDoadorNull() throws AjudeMaisException {
		doadorService.remover(null);
	}

	/**
	 * Teste buscar todos os doadores.
	 */
	@Test
	public void findAllDoadores() {
		List<Doador> doadores = new ArrayList<>();
		doadores.addAll(Arrays.asList(doador, doador));

		when(mockDoadorService.findAll()).thenReturn(doadores);
		List<Doador> mockedDoadores = mockDoadorService.findAll();

		assertThat(mockedDoadores, hasItems(doador, doador));
	}
	
	/**
	 * Teste buscar todos os doadores.
	 */
	@Test
	public void findByContaUsernameTest() {
		Doador doadore = getDoador();

		when(mockDoadorService.findByContaUsername(doadore.getConta().getUsername())).thenReturn(doadore);
		Doador mockedDoador = mockDoadorService.findByContaUsername(doadore.getConta().getUsername());

		assertEquals(mockedDoador, doadore);
	}

	/**
	 * 
	 * <p>
	 * Cria uma instancia de doador para ser utilizado nos testes.
	 * </p>
	 * 
	 * @return novo doador
	 */
	private Doador getDoador() {
		doador = new Doador();
		doador.setNome("Jão Miguel");
		doador.setTelefone("8396463738");

		final Conta conta = new Conta();
		conta.setUsername("doadorX");
		conta.setSenha("doadorX");
		conta.setGrupos(Arrays.asList("ROLE_DOADOR"));
		conta.setEmail("doadorX@gmail.com");
		conta.setAtivo(true);
		doador.setConta(conta);

		return doador;
	}
}
