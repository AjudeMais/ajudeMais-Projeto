package br.edu.ifpb.ajudemais.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.edu.ifpb.ajudeMais.AjudeMaisApplication;
import br.edu.ifpb.ajudeMais.domain.entity.Categoria;
import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.Doador;
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
import br.edu.ifpb.ajudeMais.domain.entity.Endereco;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.enumerations.Estado;
import br.edu.ifpb.ajudeMais.service.exceptions.AjudeMaisException;
import br.edu.ifpb.ajudeMais.service.negocio.DonativoService;

/**
 * 
 * 
 * <p>
 * <b> {@link DonativoServiceTest} </b>
 * </p>
 *
 * <p>
 *		Classe para testar os serviços oferecidos na parte de donativos
 * </p>
 * 
 * @author <a href="https://github.com/amslv">Ana Silva</a>
 *
 */
@SpringBootTest(classes = AjudeMaisApplication.class)
@ActiveProfiles(profiles = { "test" })
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class DonativoServiceTest {

	
	private Donativo donativo;
	
	@Autowired
	private DonativoService donativoService;
	
	private DonativoService mockDonativoService;
	
	/**
	 * metodo que prepara para as unidades de teste;
	 */
	@Before
	public void setUp() {
		mockDonativoService = mock(DonativoService.class);
		getDonativo();
	}
	
	/**
	 * Testa para salvar um donativo.
	 * @throws AjudeMaisException
	 */
	@Test
	public void saveDonativo() throws AjudeMaisException {
		mockDonativoService.save(donativo);
		verify(mockDonativoService).save(donativo);
	}
	
	/**
	 * Testa para atualizar um donativo.
	 * @throws AjudeMaisException
	 */
	@Test
	public void updateDonativo() throws AjudeMaisException {
		mockDonativoService.update(donativo);
		verify(mockDonativoService).update(donativo);
	}
	
	/**
	 * Testa para remover um donativo.
	 * @throws AjudeMaisException
	 */
	@Test
	public void removeDonativo() throws AjudeMaisException {
		mockDonativoService.remover(donativo);
		verify(mockDonativoService).remover(donativo);
	}
	
	/**
	 * teste para salvar um donativo com nome nulo
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = NullPointerException.class)
	public void saveDonativoWithNameNull() throws AjudeMaisException {
		donativo.setNome(null);
		donativoService.save(donativo);

	}
	
	/**
	 * teste para salvar um donativo com descrição nulo
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = NullPointerException.class)
	public void saveDonativoWithDescricaoNull() throws AjudeMaisException {
		donativo.setDescricao(null);
		donativoService.save(donativo);

	}
	
	/**
	 * teste para salvar um donativo com quantidade nula
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = NullPointerException.class)
	public void saveDonativoWithQuantidadeNull() throws AjudeMaisException {
		donativo.setQuantidade(null);
		donativoService.save(donativo);

	}
	
	/**
	 * teste para remover um donativo nulo
	 * @throws AjudeMaisException 
	 * 
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void removeDonativoNull() throws AjudeMaisException {
		donativoService.remover(null);

	}
	
	/**
	 * Testa a atualização de um Donativo.
	 * @throws AjudeMaisException
	 */
	@Test
	public void updateDonativoOk() throws AjudeMaisException {
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Donativo donativo = (Donativo) args[0];
				donativo.setNome("Roupas de super heróis");
				return null;
			}
		}).when(mockDonativoService).update(any(Donativo.class));

		mockDonativoService.update(this.donativo);
		verify(mockDonativoService).update(this.donativo);

		assertThat(this.donativo.getNome(), equalTo("Roupas de super heróis"));

	}
	
	/**
	 * Teste para buscar todos os donativos.
	 */
	@Test
	public void findAllDonativos() {
		List<Donativo> donativos = new ArrayList<>();
		donativos.addAll(Arrays.asList(donativo, donativo));

		when(mockDonativoService.findAll()).thenReturn(donativos);
		List<Donativo> mockedDonativos = mockDonativoService.findAll();

		assertThat(mockedDonativos, hasItems(donativo, donativo));
	}
	
	/**
	 *  Teste para encontrar um donativo com base no id
	 */
	@Test
	public void findDonativosById() {
		
		Donativo donativo = getDonativo();
		when(mockDonativoService.findById(donativo.getId())).thenReturn(donativo);
		Donativo mockedDonativo = mockDonativoService.findById(donativo.getId());

		assertEquals(mockedDonativo, donativo);
	}
	
	
	/**
	 *  Teste para encontrar donativos com base no Id de doador
	 */
	@Test
	public void findByDoadorId() {
		
		Donativo donativo = getDoadorWithDonativo();
		List<Donativo> donativos = new ArrayList<>();
		donativos.addAll(Arrays.asList(donativo));

		when(mockDonativoService.findByDoadorId(donativo.getDoador().getId())).thenReturn(donativos);
		List<Donativo> mockedDonativos = mockDonativoService.findByDoadorId(donativo.getDoador().getId());

		assertEquals(mockedDonativos, donativos);
	}
	
	/**
	 *  Teste de falha para encontrar donativos com base no Id de doador
	 */
	@Test
	public void findByDoadorIdWithIdDoadorNotValid() {
		
		Donativo donativo = getDoadorWithDonativo();
		List<Donativo> donativos = new ArrayList<>();
		donativos.addAll(Arrays.asList(donativo));

		when(mockDonativoService.findByDoadorId(donativo.getDoador().getId())).thenReturn(donativos);
		List<Donativo> mockedDonativos = mockDonativoService.findByDoadorId(2l);

		assertNotNull(mockedDonativos);
	}
	
	/**
	 * 
	 */
	@Test
	public void findByNomeOk() {
		Donativo donativo = getDonativo();
		
		List<Donativo> donativos = new ArrayList<>();
		donativos.addAll(Arrays.asList(donativo));
		
		when(mockDonativoService.findByNome("Roupas")).thenReturn(donativos);
		List<Donativo> mockDonativos = mockDonativoService.findByNome("Roupas");
		
		assertTrue(mockDonativos.size() > 0);
	}
	
	/**
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void findByCategoriaInstituicaoCaridadeOk() throws AjudeMaisException {
		Donativo donativo = getDoadorWithDonativo();
		List<Donativo> donativos = new ArrayList<>();
		donativos.addAll(Arrays.asList(donativo));
		when(mockDonativoService.findByCategoriaInstituicaoCaridade(donativo.getCategoria().getInstituicaoCaridade())).thenReturn(donativos);
		List<Donativo> mockedDonativos = mockDonativoService.findByCategoriaInstituicaoCaridade(donativo.getCategoria().getInstituicaoCaridade());
		assertNotNull(mockedDonativos);
	}
	
	/**
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void findByMensageiroOk() throws AjudeMaisException {
		Donativo donativo = getDoadorWithDonativo();
		Mensageiro mensageiro = getMensageiro();
		donativo.setMensageiro(mensageiro);
		List<Donativo> donativos = new ArrayList<>();
		donativos.addAll(Arrays.asList(donativo));
		when(mockDonativoService.findByMensageiro(mensageiro)).thenReturn(donativos);
		List<Donativo> mockedDonativos = mockDonativoService.findByMensageiro(mensageiro);
		assertNotNull(mockedDonativos);
	}
	
	/**
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void findByMensageiroNull() throws AjudeMaisException {
		Donativo donativo = getDoadorWithDonativo();
		Mensageiro mensageiro = getMensageiro();
		donativo.setMensageiro(mensageiro);
		List<Donativo> donativos = new ArrayList<>();
		donativos.addAll(Arrays.asList(donativo));
		when(mockDonativoService.findByMensageiro(null)).thenReturn(null);
		List<Donativo> mockedDonativos = mockDonativoService.findByMensageiro(mensageiro);
		assertThat(mockedDonativos).isEmpty();;
	}
	
	/**
	 * 
	 * @throws AjudeMaisException
	 */
	@Test
	public void filterDonativoByMensageiroAndEstado() throws AjudeMaisException {
		Donativo donativo = getDoadorWithDonativo();
		Mensageiro mensageiro = getMensageiro();
		donativo.setMensageiro(mensageiro);
		List<Donativo> donativos = new ArrayList<>();
		donativos.addAll(Arrays.asList(donativo));
		when(mockDonativoService.filterDonativoByMensageiroAndEstado(mensageiro, Estado.DISPONIBILIZADO)).thenReturn(donativos);
		List<Donativo> mockedDonativos = mockDonativoService.filterDonativoByMensageiroAndEstado(mensageiro, Estado.DISPONIBILIZADO);
		assertNotNull(mockedDonativos);
	}
	

	/**
	 * Cria um donativo qualquer para ser utilizado durante os testes
	 * @return
	 * 		mock de donativo
	 */
	private Donativo getDonativo() {
		donativo = new Donativo();
		
		donativo.setNome("Roupas");
		donativo.setDescricao("Algumas roupas velhas, porém, em bom estado");
		donativo.setQuantidade(10);
		
		
		
		return donativo;
		
	}
	
	/**
	 * Cria um doador com donativos qualquer para ser utilizado durante os testes
	 * @return
	 * 		mock de donativo
	 */
	private Donativo getDoadorWithDonativo() {
		Doador doador = new Doador();
		doador.setNome("fulano");
		doador.setId(1l);
		
		donativo.setNome("Roupas");
		donativo.setDescricao("Algumas roupas velhas, porém, em bom estado");
		donativo.setQuantidade(10);
		
		Endereco endereco = new Endereco();
		endereco.setBairro("Centro");
		endereco.setCep("58500000");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");
		endereco.setLogradouro("Rua Leopoldino José Da Silva");
		donativo.setEndereco(endereco);
		donativo.setDoador(doador);
		
		InstituicaoCaridade instituicaoCaridade = new InstituicaoCaridade();
		instituicaoCaridade.setId(1l);
		instituicaoCaridade.setConta(new Conta());
		instituicaoCaridade.setNome("TESTE");
		
		Categoria categoria = new Categoria();
		categoria.setAtivo(true);
		categoria.setInstituicaoCaridade(instituicaoCaridade);
		
		donativo.setCategoria(categoria);
		
		return donativo;
	}
	
	private Mensageiro getMensageiro() {
		Mensageiro mensageiro = new Mensageiro();
		mensageiro.setId(2l);
		mensageiro.setConta(new Conta());
		mensageiro.setCpf("263.799.558-01");
		mensageiro.setNome("Josué da Silva");
		mensageiro.setTelefone("(83)99999-1232");
		
		Endereco endereco = new Endereco();
		endereco.setBairro("Centro");
		endereco.setCep("58500000");
		endereco.setLocalidade("Monteiro");
		endereco.setUf("PB");
		endereco.setLogradouro("Rua Leopoldino José Da Silva");
		List<Endereco> enderecos = new ArrayList<>();
		enderecos.add(endereco);
		mensageiro.setEnderecos(enderecos);
		
		return mensageiro;
	}
}
