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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

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
	 * filtra mensageiro por email
	 * </p>
	 */
	@Test
	public void findByContaEmailIgnoreCaseContainingTest() {
		List<Mensageiro> mensageiros = mensageiroRepository.findByContaEmailIgnoreCaseContaining("jao");
		assertTrue(mensageiros.size() > 0);
	}

	/**
	 * 
	 * <p>
	 * filtra mensageiro por conta, filtrando por username.
	 * </p>
	 */
	@Test
	public void findByMensageiroIdTest() {
		Mensageiro mensageiro = mensageiroRepository.findOneByContaUsername("joao");
		assertNotNull(mensageiro);

	}

	/**
	 * 
	 * <p>
	 * Teste para busca de um mensageiro pelo CPF.
	 * </p>
	 */
	@Test
	public void findOneByCpfTest() {
		Optional<Mensageiro> mensageiroOp = mensageiroRepository.findOneByCpf("102.106.104-50");
		assertTrue(mensageiroOp.isPresent());
	}
}
