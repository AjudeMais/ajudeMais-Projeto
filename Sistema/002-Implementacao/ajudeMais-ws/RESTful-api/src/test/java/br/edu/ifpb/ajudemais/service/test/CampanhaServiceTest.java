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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.domain.entity.Campanha;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.CampanhaService;

@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CampanhaServiceTest {

	private Campanha campanha;

	@Autowired
	private CampanhaService campanhaService;

	private CampanhaService mockCampanhaService;

	/**
	 * metodo que prepara para as unidades de teste;
	 */
	@Before
	public void setUp() {
		mockCampanhaService = mock(CampanhaService.class);
		getCampanha();
	}

	/**
	 * Testa para salvar uma campanha.
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void saveCampanha() throws AjudeMaisException {
		mockCampanhaService.save(campanha);
		verify(mockCampanhaService).save(campanha);
	}

	/**
	 * Testa para remover uma campanha.
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void removerCampanha() throws AjudeMaisException {
		mockCampanhaService.remover(campanha);
		verify(mockCampanhaService).remover(campanha);
	}

	/**
	 * Testa para atualizar uma campanha.
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void updateCampanha() throws AjudeMaisException {
		mockCampanhaService.update(campanha);
		verify(mockCampanhaService).update(campanha);
	}

	/**
	 * teste para salvar uma campanha com o nome null
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveCampanhaWithNomeNull() throws AjudeMaisException {
		campanha.setNome(null);

		campanhaService.save(campanha);

	}

	/**
	 * teste para salvar uma campanha com a descrição null
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveMensageiroWithDescricaoNull() throws AjudeMaisException {
		campanha.setDescricao(null);

		campanhaService.save(campanha);

	}

	/**
	 * Testa a atualização de uma campanha.
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void updateCampanhaOk() throws AjudeMaisException {
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Campanha campanha = (Campanha) args[0];
				campanha.setNome("Natal feliz 2");
				return null;
			}
		}).when(mockCampanhaService).update(any(Campanha.class));

		mockCampanhaService.update(this.campanha);
		verify(mockCampanhaService).update(this.campanha);

		assertThat(this.campanha.getNome(), equalTo("Natal feliz 2"));

	}

	/**
	 * Teste para remoção de uma campanha.
	 * @throws AjudeMaisException 
	 */
	@Test
	public void removeCampanhaOk() throws AjudeMaisException {
		mockCampanhaService.remover(campanha);
		verify(mockCampanhaService).remover(campanha);
	}

	/**
	 * Teste para remoção de uma campanha nula.
	 * @throws AjudeMaisException 
	 */
	@Test
	public void removeCampanhaNull() throws AjudeMaisException {
		mockCampanhaService.remover(null);
	}

	/**
	 * Teste para buscar todas as campanhas.
	 */
	@Test
	public void findAllCampanhas() {
		List<Campanha> campanhas = new ArrayList<>();
		campanhas.addAll(Arrays.asList(campanha, campanha));

		when(mockCampanhaService.findAll()).thenReturn(campanhas);
		List<Campanha> mockedMensageiros = mockCampanhaService.findAll();

		assertThat(mockedMensageiros, hasItems(campanha, campanha));
	}

	/**
	 * Teste para buscar uma campanha por seu identificador.
	 */
	@Test
	public void findCampanhasById() {

		Campanha campanha = getCampanha();
		when(mockCampanhaService.findById(campanha.getId())).thenReturn(campanha);
		Campanha mockedMensageiro = mockCampanhaService.findById(campanha.getId());

		assertEquals(mockedMensageiro, campanha);
	}

	/**
	 * teste para buscar uma campanha atraves de uma instituição.
	 * 
	 * @throws AjudeMaisException
	 * @throws IOException
	 */
	@Test
	public void findByInstituicaoTest() throws AjudeMaisException, IOException {
		InstituicaoCaridade instituicao = campanha.getInstituicaoCaridade();
		List<Campanha> mockCampanhas = Arrays.asList(campanha);

		when(mockCampanhaService.findByInstituicaoCaridade(instituicao)).thenReturn(mockCampanhas);
		assertEquals(mockCampanhaService.findByInstituicaoCaridade(instituicao), mockCampanhas);
	}

	/**
	 * cria e retorna uma campanha.
	 * 
	 */
	private Campanha getCampanha() {
		campanha = new Campanha();
		campanha.setNome("Natal sem fome");
		campanha.setDescricao("campanha para arrecadar alimentos para os moradores de rua");
		campanha.setDataInicio(new Date(1497225600000l));
		campanha.setDataFim(new Date(1499817600000l));

		InstituicaoCaridade instituicao = new InstituicaoCaridade();
		instituicao.setId(1l);
		instituicao.setNome("ONG XPTO");
		instituicao.setDescricao("ONG visa algo.");
		instituicao.setTelefone("8399273464");
		instituicao.setDocumento("107.345.123-40");

		Conta conta = new Conta();
		conta.setUsername("rajesh");
		conta.setSenha("euFaloComMulher");
		conta.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		conta.setEmail("raj@gmail.com");

		instituicao.setConta(conta);
		
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua Maira Nunes");
		endereco.setBairro("Centro");
		endereco.setCep("58560-0000");
		endereco.setNumero("s/n");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");
		instituicao.setEndereco(endereco);
		campanha.setInstituicaoCaridade(instituicao);
		return campanha;
	}
}
