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
import br.edu.ifpb.ajudeMais.domain.entity.Donativo;
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
	@Test(expected = TransactionSystemException.class)
	public void saveDonativoWithNameNull() throws AjudeMaisException {
		donativo.setNome(null);
		donativoService.save(donativo);

	}
	
	/**
	 * teste para salvar um donativo com descrição nulo
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveDonativoWithDescricaoNull() throws AjudeMaisException {
		donativo.setDescricao(null);
		donativoService.save(donativo);

	}
	
	/**
	 * teste para salvar um donativo com quantidade nula
	 * 
	 * @throws AjudeMaisException
	 */
	@Test(expected = TransactionSystemException.class)
	public void saveDonativoWithQuantidadeNull() throws AjudeMaisException {
		donativo.setQuantidade(null);
		donativoService.save(donativo);

	}
	
	/**
	 * teste para remover um donativo nulo
	 * 
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void removeDonativoNull() {
		donativoService.remover(null);

	}
	
	/**
	 * Testa a atualização de uma campanha.
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
}
