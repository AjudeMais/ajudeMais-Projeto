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

import br.edu.ifpb.ajudeMais.domain.entity.Mensageiro;

/**
 * 
 * <p>
 * {@link MensageiroRepositoryTest}
 * </p>
 * 
 * <p>
 * Classe utilizada para testes de {@link MensageiroRepositoryTest}
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
@DatabaseSetup("/mensageiro-entries.xml")
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { "/mensageiro-entries.xml" })
@DirtiesContext
public class MensageiroRepositoryTest {

	/**
	 * 
	 */
	@Autowired
	private MensageiroRepository mensageiroRepository;

	/**
	 * 
	 * <p>
	 * Teste para busca de mensageiro por conta, filtrando por email.
	 * </p>
	 */
	@Test
	public void findByMensageirosEmailTest() {
		List<Mensageiro> mensageiros = mensageiroRepository.findByContaEmailIgnoreCaseContaining("jao@mail.com");
		assertTrue(mensageiros.size() > 0);

	}

	/**
	 * 
	 * <p>
	 * Teste para busca de mensageiro por conta, filtrando por email não
	 * existente.
	 * </p>
	 */
	@Test
	public void findByMensageirosEmailNotPresentTest() {
		List<Mensageiro> mensageiros = mensageiroRepository.findByContaEmailIgnoreCaseContaining("jajaja@mail.com");
		assertFalse(mensageiros.size() > 0);

	}

	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização
	 * </p>
	 */
	@Test
	public void filtersMensageiroCloserTest() {
		List<Object[]> mensageiros = mensageiroRepository.filtersMensageiroCloser("Rua ai", "centro", "Ouro velho",
				"PB");
		assertTrue(mensageiros.size() > 0);
	}

	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização, passando apenas localidade
	 * </p>
	 */
	@Test
	public void filtersMensageiroCloserValidOnlyAddressTest() {
		List<Object[]> mensageiros = mensageiroRepository.filtersMensageiroCloser("Rua ai", "", "", "");
		assertFalse(mensageiros.size() > 0);
	}

	/**
	 * 
	 * <p>
	 * Filtra mensageiro pela localização passando paramentros nulos
	 * </p>
	 */
	@Test
	public void filtersMensageiroCloserNullParamsTest() {
		List<Object[]> mensageiros = mensageiroRepository.filtersMensageiroCloser(null, null, null, null);
		assertFalse(mensageiros.size() > 0);
	}
}
