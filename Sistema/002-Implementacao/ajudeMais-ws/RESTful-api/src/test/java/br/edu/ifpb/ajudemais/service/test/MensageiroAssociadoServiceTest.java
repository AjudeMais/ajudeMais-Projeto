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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.Imagem;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.MensageiroAssociadoService;

/**
 * 
 * <p>
 * {@link MensageiroAssociadoServiceTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para realização de testes de unidade referentes as operações
 * de {@link MensageiroAssociadoService}
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
public class MensageiroAssociadoServiceTest {

	/**
	 * 
	 */
	private MensageiroAssociado mensageiroAssociado;

	/**
	 * 
	 */
	@Mock
	private MensageiroAssociadoService mockMensageiroAssService;

	/**
	 * 
	 */
	@Autowired
	private MensageiroAssociadoService mensageiroAssociadoService;

	/**
	 * 
	 * <p>
	 * Configuração executada antes da execução de cada teste.
	 * </p>
	 */
	@Before
	public void setUp() {
		mockMensageiroAssService = mock(MensageiroAssociadoService.class);

		mensageiroAssociado = getMensageiroAssociado();
	}

	/**
	 * 
	 * <p>
	 * Executa mock para teste de salva uma associação.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void saveTest() throws AjudeMaisException {
		mockMensageiroAssService.save(mensageiroAssociado);
		verify(mockMensageiroAssService).save(mensageiroAssociado);
	}

	/**
	 * 
	 * <p>
	 * Tenta criar uma associação passando mensageiro nula. Deveria lançar uma
	 * exceção.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void saveNullMensageiroTest() throws AjudeMaisException {
		mensageiroAssociado.setMensageiro(null);
		mensageiroAssociadoService.save(mensageiroAssociado);
	}
	

	/**
	 * 
	 * <p>
	 * Tenta criar uma associação passando instituição nula. Deveria lançar uma
	 * exceção.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void saveNullInstituicaoTest() throws AjudeMaisException {
		mensageiroAssociado.setInstituicaoCaridade(null);
		mensageiroAssociadoService.save(mensageiroAssociado);
	}
	
	/**
	 * 
	 * <p>
	 * Executa mock para teste de atualização de uma associação.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void updateTest() throws AjudeMaisException {
		mockMensageiroAssService.update(mensageiroAssociado);
		verify(mockMensageiroAssService).update(mensageiroAssociado);
	}
	
	/**
	 * 
	 * <p>
	 * Executa teste sobre método de remoção de uma associação.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void removeTest() throws AjudeMaisException {
		mockMensageiroAssService.remover(mensageiroAssociado);
		verify(mockMensageiroAssService).remover(mensageiroAssociado);
	}
	
	/**
	 * 
	 * <p>
	 * Teste para buscar associação por instituição.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 * @throws IOException
	 */
	@Test
	public void findByInstituicaoTest() throws AjudeMaisException, IOException {
		Conta contaInst = mensageiroAssociado.getInstituicaoCaridade().getConta();
		List<MensageiroAssociado> mockAssociacoes = Arrays.asList(mensageiroAssociado);
		
		when(mockMensageiroAssService.findByInstituicaoConta(contaInst)).thenReturn(mockAssociacoes);
		assertEquals(mockMensageiroAssService.findByInstituicaoConta(contaInst), mockAssociacoes);
	}
	
	/**
	 * 
	 * <p>
	 * Teste para buscar associação por ID.
	 * </p>
	 * 
	 * @throws AjudeMaisException
	 * @throws IOException
	 */
	@Test
	public void findByIdTest() throws AjudeMaisException, IOException {
		MensageiroAssociado mockMensageiro = new MensageiroAssociado();
		mockMensageiro.setId(1l);
		when(mockMensageiroAssService.findById(1l)).thenReturn(mockMensageiro);
		assertEquals(mockMensageiroAssService.findById(1l), mockMensageiro);
	}

	/**
	 * 
	 * <p>
	 * cria instancias necessárias para execução dos testes.
	 * </p>
	 * 
	 * @return
	 */
	private MensageiroAssociado getMensageiroAssociado() {

		MensageiroAssociado mensageiroAssociado = new MensageiroAssociado();

		mensageiroAssociado.setData(new Date());
		mensageiroAssociado.setStatus(true);

		InstituicaoCaridade instituicao = new InstituicaoCaridade();
		instituicao.setNome("ONG XPTO");
		instituicao.setDescricao("ONG visa algo.");
		instituicao.setTelefone("8399273464");
		instituicao.setDocumento("107.345.123-40");

		Conta conta = new Conta();
		conta.setUsername("rajesh");
		conta.setSenha("euFaloComMulher");
		conta.setGrupos(Arrays.asList("ROLE_INSTITUICAO"));
		conta.setEmail("raj@gmail.com");

		Endereco endereco = new Endereco();
		endereco.setLogradouro("Rua Maira Nunes");
		endereco.setBairro("Centro");
		endereco.setCep("58560-0000");
		endereco.setNumero("s/n");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");

		instituicao.setConta(conta);
		instituicao.setEndereco(endereco);

		Mensageiro mensageiro = new Mensageiro();
		mensageiro.setCpf("127.547.642-24");
		mensageiro.setEnderecos(Arrays.asList(endereco));
		mensageiro.setNome("MENSAGEIRO 1");
		mensageiro.setTelefone("83996885898");

		Imagem imagem = new Imagem();
		mensageiro.setFoto(imagem);

		Conta contaM = new Conta();
		contaM.setAtivo(true);
		contaM.setUsername("msg1");
		contaM.setSenha("msg1");
		contaM.setGrupos(Arrays.asList("ROLE_MENSAGEIRO"));
		contaM.setEmail("msg1@gmail.com");
		mensageiro.setConta(contaM);

		mensageiroAssociado.setInstituicaoCaridade(instituicao);
		mensageiroAssociado.setMensageiro(mensageiro);

		return mensageiroAssociado;
	}

}
