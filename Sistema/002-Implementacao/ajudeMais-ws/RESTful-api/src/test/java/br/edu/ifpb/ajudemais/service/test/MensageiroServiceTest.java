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
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.Imagem;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroService;

/**
 * Classe utilizada para realizar teste referente a services de
 * {@link MensageiroService}
 * 
 * @author elson <br/>
 *         <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class MensageiroServiceTest {

	/**
	 * 
	 */
	private Mensageiro mensageiro;

	/**
	 * 
	 */
	@Autowired
	private MensageiroService mensageiroService;

	/**
	 * 
	 */
	@Mock
	private MensageiroService mockMensageiroService;

	/**
	 * metodo que prepara para as unidades de teste;
	 */
	@Before
	public void setUp() {
		mockMensageiroService = mock(MensageiroService.class);
		getMensageiro();
	}

	/**
	 * teste para salvar um mensageiro
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void saveMensageiroOk() throws AjudeMaisException {
		mockMensageiroService.save(mensageiro);
		verify(mockMensageiroService).save(mensageiro);

	}

	/**
	 * teste para salvar um mensageiro com o nome null
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveMensageiroWithNomeFNull() throws AjudeMaisException {
		mensageiro.setNome(null);
		;
		mensageiroService.save(mensageiro);

	}

	/**
	 * teste para salvar um mensageiro com o telefone null
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveMensageiroWithTelefoneNull() throws AjudeMaisException {
		mensageiro.setTelefone(null);
		;
		mensageiroService.save(mensageiro);

	}

	/**
	 * teste para salvar um mensageiro com o CPF null
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveMensageiroWithCPFNull() throws AjudeMaisException {
		mensageiro.setCpf(null);
		mensageiroService.save(mensageiro);

	}

	/**
	 * Testa a atualização de um mensageiro
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void updateMensageiroOk() throws AjudeMaisException {
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Mensageiro mensageiro = (Mensageiro) args[0];
				mensageiro.setNome("mensageiro123");
				return null;
			}
		}).when(mockMensageiroService).update(any(Mensageiro.class));

		mockMensageiroService.update(this.mensageiro);
		verify(mockMensageiroService).update(this.mensageiro);

		assertThat(this.mensageiro.getNome(), equalTo("mensageiro123"));

	}

	/**
	 * Teste para remoção de um mensageiro.
	 * @throws AjudeMaisException 
	 */
	@Test
	public void removeMensageiroOk() throws AjudeMaisException {
		mockMensageiroService.remover(mensageiro);
		verify(mockMensageiroService).remover(mensageiro);
	}

	/**
	 * testa a remoção de um mensageiro null
	 * @throws AjudeMaisException 
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void removeMensageiroNull() throws AjudeMaisException {
		mensageiroService.remover(null);

	}

	/**
	 * Teste buscar todos os mensageiros.
	 */
	@Test
	public void findAllMensageiros() {
		List<Mensageiro> mensageiros = new ArrayList<>();
		mensageiros.addAll(Arrays.asList(mensageiro, mensageiro));

		when(mockMensageiroService.findAll()).thenReturn(mensageiros);
		List<Mensageiro> mockedMensageiros = mockMensageiroService.findAll();

		assertThat(mockedMensageiros, hasItems(mensageiro, mensageiro));
	}

	/**
	 * Teste buscar mensageiros filtrando por parte/email.
	 */
	@Test
	public void findMensageirosByEmail() {
		List<Mensageiro> mensageiros = new ArrayList<>();
		mensageiros.addAll(Arrays.asList(mensageiro, mensageiro));

		when(mockMensageiroService.findByContaEmail("msg1")).thenReturn(mensageiros);
		List<Mensageiro> mockedMensageiros = mockMensageiroService.findByContaEmail("msg1");

		assertThat(mockedMensageiros, hasItems(mensageiro, mensageiro));
	}

	/**
	 * Teste buscar mensageiro por conta, filtrando por username.
	 */
	@Test
	public void findByContaUsername() {
		Mensageiro mensageiro = getMensageiro();

		when(mockMensageiroService.findByContaUsername(mensageiro.getConta().getUsername())).thenReturn(mensageiro);
		Mensageiro mockedMensageiro = mockMensageiroService.findByContaUsername(mensageiro.getConta().getUsername());

		assertEquals(mockedMensageiro, mensageiro);
	}

	/**
	 * metodo para criar um mensageiro
	 * 
	 * @return
	 */
	private Mensageiro getMensageiro() {
		mensageiro = new Mensageiro();
		mensageiro.setNome("MENSAGEIRO 1");
		mensageiro.setCpf("127.547.642-24");
		mensageiro.setEnderecos(getEndereco());
		mensageiro.setTelefone("83996885898");

		Imagem imagem = new Imagem();
		mensageiro.setFoto(imagem);

		Conta conta = new Conta();
		conta.setUsername("mensageiro");
		conta.setSenha("mensageiro");
		conta.setGrupos(Arrays.asList("ROLE_MENSAGEIRO"));
		conta.setEmail("msg1@gmail.com");
		conta.setAtivo(true);
		mensageiro.setConta(conta);
		return mensageiro;

	}

	/**
	 * cria um endereço
	 * 
	 * @return
	 */
	private List<Endereco> getEndereco() {
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua dos mensageiros");
		endereco.setBairro("Centro");
		endereco.setCep("58500-000");
		endereco.setNumero("s/n");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");
		List<Endereco> enderecos = new ArrayList<>();
		enderecos.add(endereco);
		return enderecos;

	}

}
