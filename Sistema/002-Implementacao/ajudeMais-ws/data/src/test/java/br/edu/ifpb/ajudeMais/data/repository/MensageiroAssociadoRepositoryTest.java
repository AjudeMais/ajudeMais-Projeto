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
 * 
 * AJUDE MAIS - 2017®
 * 
 */
package br.edu.ifpb.ajudeMais.data.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

import br.edu.ifpb.ajudeMais.domain.entity.Conta;
import br.edu.ifpb.ajudeMais.domain.entity.InstituicaoCaridade;
import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;
import br.edu.ifpb.ajudeMais.domain.entity.MensageiroAssociado;

/**
 * 
 * <p>
 * {@link MensageiroAssociadoRepositoryTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes de unidade referentes ao repositório
 * {@link MensageiroAssociadoRepository}
 * </p>
 *
 * <pre>
 * </pre
 *
 * @author <a href="https://franckaj.github.io">Franck Aragão</a>
 *
 */
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@RunWith(SpringRunner.class)
@DataJpaTest
@DatabaseSetup("/mensageiro-associado-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/mensageiro-associado-entries.xml" })
@DirtiesContext
public class MensageiroAssociadoRepositoryTest {

	/**
	 * 
	 */
	@Autowired
	private MensageiroAssociadoRepository mensageiroAssociadoRepository;

	/**
	 * 
	 */
	@Autowired
	private MensageiroRepository mensageiroRepository;

	/**
	 * 
	 */
	@Autowired
	private InstituicaoCaridadeRepository instituicaoCaridadeRepository;

	/**
	 * 
	 */
	private Conta conta;

	/**
	 * 
	 * <p>
	 * Configuração executada antes da execução de cada teste.
	 * </p>
	 */
	@Before
	public void setUp() {

		conta = new Conta();
		conta.setId(1l);
	}

	/**
	 * 
	 * <p>
	 * Exercita método de busca de associação por instituição filtrando por
	 * conta.
	 * </p>
	 */
	@Test
	public void findByInstituicaoCaridadeContaTest() {

		List<MensageiroAssociado> mensageiros = mensageiroAssociadoRepository
				.findByInstituicaoCaridadeConta(this.conta);

		assertFalse("", mensageiros.isEmpty());

	}

	/**
	 * 
	 * <p>
	 * Exercita método de busca de mensgeiro associado, filtrando por mensageiro
	 * e instituição não associado.
	 * </p>
	 */
	@Test
	public void findByMensageiroAndInstituicaoNotFoundTest() {
		Mensageiro mensageiro = mensageiroRepository.findOne(80l);
		InstituicaoCaridade instituicao = instituicaoCaridadeRepository.findOne(80l);
		Optional<MensageiroAssociado> mensageiroAssOp = mensageiroAssociadoRepository
				.findByMensageiroAndInstituicaoCaridade(mensageiro, instituicao);

		assertFalse(mensageiroAssOp.isPresent());
	}
	
	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização
	 * </p>
	 */
	@Test
	public void filterMensageirosCloserToCidadeWithEqualValueTest() {
		List<Object[]> result = mensageiroAssociadoRepository.filterMensageirosCloserToBairro("centro", "Ouro velho",
				"PB", 1l);
		assertTrue(result.size() == 2);
	}
	
	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização
	 * </p>
	 */
	@Test
	public void filterMensageirosCloserToBairroTest() {
		List<Object[]> result = mensageiroAssociadoRepository.filterMensageirosCloserToBairro("centro", "Ouro velho",
				"PB", 1l);
		assertTrue(result.size() == 2);
	}
	
	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização, passando apenas localidade
	 * </p>
	 */
	@Test
	public void filterMensageirosCloserToBairroValidOnlyAddressTest() {
		List<Object[]> mensageiros = mensageiroAssociadoRepository.filterMensageirosCloserToBairro("", "", "", 1l);
		assertFalse(mensageiros.size() > 0);
	}

	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização passando paramentros nulos
	 * </p>
	 */
	@Test
	public void filterMensageirosCloserToBairroNullParamsTest() {
		List<Object[]> mensageiros = mensageiroAssociadoRepository.filterMensageirosCloserToBairro(null, null, null, null);
		assertFalse(mensageiros.size() > 0);
	}

	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização
	 * </p>
	 */
	@Test
	public void filterMensageirosCloserToBairroWithEqualValueTest() {
		List<Object[]> result = mensageiroAssociadoRepository.filterMensageirosCloserToBairro("centro", "Ouro velho",
				"PB", 1l);
		
		assertTrue(result.size() == 2);
	}
	
	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização
	 * </p>
	 */
	@Test
	public void filterMensageirosCloserToCidadeTest() {
		List<Object[]> result = mensageiroAssociadoRepository.filterMensageirosCloserToCidade("Ouro velho",
				"PB", 1l);
		assertTrue(result.size() == 2);
	}
	
	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização, passando apenas localidade
	 * </p>
	 */
	@Test
	public void filterMensageirosCloserToCidadeValidOnlyAddressTest() {
		List<Object[]> mensageiros = mensageiroAssociadoRepository.filterMensageirosCloserToCidade("", "", 1l);
		assertFalse(mensageiros.size() > 0);
	}

	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização passando paramentros nulos
	 * </p>
	 */
	@Test
	public void filterMensageirosCloserToCidadeNullParamsTest() {
		List<Object[]> mensageiros = mensageiroAssociadoRepository.filterMensageirosCloserToCidade(null, null, null);
		assertFalse(mensageiros.size() > 0);
	}

	
	/**
	 * 
	 * <p>
	 * Exercita método de busca de mensgeiro associado, filtrando por mensageiro
	 * e instituição.
	 * </p>
	 */
	@Test
	public void findByMensageiroAndInstituicaoTest() {
		Mensageiro mensageiro = mensageiroRepository.findOne(1l);
		InstituicaoCaridade instituicao = instituicaoCaridadeRepository.findOne(1l);
		Optional<MensageiroAssociado> mensageiroAssOp = mensageiroAssociadoRepository
				.findByMensageiroAndInstituicaoCaridade(mensageiro, instituicao);

		assertTrue(mensageiroAssOp.isPresent());
	}

}
